

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPDownloadTest {
    private static final int BUFFER_SIZE = 4096;
 
    public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.out.println("Usage: Classname download_url save_dir");
			System.exit(1);
		}
		
		/* you can use below
		java HTTPDownloadTest http://www.fun2code.de/downloads/articles/multicasting_images.zip . [enter]
		
		if you want to some file, change args[0] to yours
		*/
		
		String fileurl = args[0];
		String savedir = args[1];

    	System.out.println("Hey yo! Start download file....\n");
		downloadFile(fileurl, savedir);
    }
    
	public static void downloadFile(String fileURL, String saveDir) throws IOException{
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
		int responseCode = httpConn.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();
			
			if (disposition != null) { 
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}
			
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);
			
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;
			
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			
			outputStream.close();
			inputStream.close();
			
			System.out.println("File downloaded");
			
		} else {
			System.out.println("No file to download. Server replied HTTP code : " + responseCode);
		}
		httpConn.disconnect();
	}
}