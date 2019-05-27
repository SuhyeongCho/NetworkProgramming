
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import com.google.gson.Gson;
 
public class WiseNLUExample {
    static public class Morpheme {
        final String text;
        final String type;
        Integer count;
        public Morpheme (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
    static public class NameEntity {
        final String text;
        final String type;
        Integer count;
        public NameEntity (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
 
	@SuppressWarnings({"unchecked", "rawtypes"})
	static public void main(String[] args) {
		String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
		String accessKey = "140487fa-2244-4292-98f2-0f4997b4679c";
		String analysisCode = "ner";
		String text = "";
		Gson gson = new Gson();
		
		text += "그에 대한 숭실대학의 태도는 매우 명쾌하고도 단호한 것이었다.\r\n"+
				"설사 학교의 문을 닫는 한이 있더라도 신사참배 강요에는 단호히 반대한다는 것이었다."+
				"105인 사건을 위시하여 국민회 사건에 깊이 관여하였고, 3.1독립운동, 광주학생운동에서 선도적인 역할을 했던 숭실의 선각자들은 일제의 신사참배 요구에 맞서 단장의 아픔을 스스로 결단하기에 이른다" +
				"1938년 3월 4일 숭실대학은 마지막 졸업식을 끝으로 대학과정을 시작한지 39년만에 폐교라는 역사적인 결단을 내리게 되었다."+
				"그것은 하나님의 민족에 대한 성실성의 표현으로서 불의에도 타협하지 않는 숭실정신의 발로였다.";
		
		Map<String, Object> request = new HashMap<>();
		Map<String, String> argument = new HashMap<>();
		
		argument.put("analysis_code", analysisCode);
		argument.put("text", text);
		
		request.put("access_key", accessKey);
		request.put("argument", argument);
		
		URL url;
		Integer responseCode = null;
		String responseBodyJson = null;
		Map<String, Object> responseBody = null;
		
		try {
			url = new URL(openApiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(gson.toJson(request).getBytes("UTF-8"));
			wr.flush();
			wr.close();
			
			responseCode = con.getResponseCode();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			
			String inputLine = "";
			while((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			responseBodyJson = sb.toString();
			
			if (responseCode != 200) {
				System.out.println("[error] "+responseBodyJson);
				return;
			}
			
			responseBody = gson.fromJson(responseBodyJson, Map.class);
			Integer result = ((Double)responseBody.get("result")).intValue();
			Map<String, Object> returnObject;
			List<Map> sentences;
			
			if (result != 0) {
				System.out.println("[error] "+responseBody.get("result"));
				return;
			}

 
            // 분석 결과 활용
            returnObject = (Map<String, Object>) responseBody.get("return_object");
            sentences = (List<Map>) returnObject.get("sentence");
 
            Map<String, Morpheme> morphemesMap = new HashMap<String, Morpheme>();
            Map<String, NameEntity> nameEntitiesMap = new HashMap<String, NameEntity>();
            List<Morpheme> morphemes = null;
            List<NameEntity> nameEntities = null;
 
            for( Map<String, Object> sentence : sentences ) {
                // 형태소 분석기 결과 수집 및 정렬
                List<Map<String, Object>> morphologicalAnalysisResult = (List<Map<String, Object>>) sentence.get("morp");
                for( Map<String, Object> morphemeInfo : morphologicalAnalysisResult ) {
                    String lemma = (String) morphemeInfo.get("lemma");
                    Morpheme morpheme = morphemesMap.get(lemma);
                    if ( morpheme == null ) {
                        morpheme = new Morpheme(lemma, (String) morphemeInfo.get("type"), 1);
                        morphemesMap.put(lemma, morpheme);
                    } else {
                        morpheme.count = morpheme.count + 1;
                    }
                }
 
                // 개체명 분석 결과 수집 및 정렬
                List<Map<String, Object>> nameEntityRecognitionResult = (List<Map<String, Object>>) sentence.get("NE");
                for( Map<String, Object> nameEntityInfo : nameEntityRecognitionResult ) {
                    String name = (String) nameEntityInfo.get("text");
                    NameEntity nameEntity = nameEntitiesMap.get(name);
                    if ( nameEntity == null ) {
                        nameEntity = new NameEntity(name, (String) nameEntityInfo.get("type"), 1);
                        nameEntitiesMap.put(name, nameEntity);
                    } else {
                        nameEntity.count = nameEntity.count + 1;
                    }
                }
            }
 
            if ( 0 < morphemesMap.size() ) {
                morphemes = new ArrayList<Morpheme>(morphemesMap.values());
                morphemes.sort( (morpheme1, morpheme2) -> {
                    return morpheme2.count - morpheme1.count;
                });
            }
 
            if ( 0 < nameEntitiesMap.size() ) {
                nameEntities = new ArrayList<NameEntity>(nameEntitiesMap.values());
                nameEntities.sort( (nameEntity1, nameEntity2) -> {
                    return nameEntity2.count - nameEntity1.count;
                });
            }
 
            // 형태소들 중 '명사'들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
            morphemes
                .stream()
                .filter(morpheme -> {
                    return morpheme.type.equals("NNG") ||
                            morpheme.type.equals("NNP") ||
                            morpheme.type.equals("NNB");
                })
                .limit(5)
                .forEach(morpheme -> {
                    System.out.println("[명사] " + morpheme.text + " ("+morpheme.count+")" );
                });
 
            // 형태소들 중 '동사'들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
            System.out.println("");
            morphemes
                .stream()
                .filter(morpheme -> {
                    return morpheme.type.equals("VV");
                })
                .limit(5)
                .forEach(morpheme -> {
                    System.out.println("[동사] " + morpheme.text + " ("+morpheme.count+")" );
                });
 
            // 인식된 '개채명'들 많이 노출된 순으로 출력 ( 최대 5개 )
            System.out.println("");
            nameEntities
                .stream()
                .limit(5)
                .forEach(nameEntity -> {
                    System.out.println("[개체명] " + nameEntity.text + " ("+nameEntity.count+")" );
                });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}