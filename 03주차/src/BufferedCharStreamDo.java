import java.io.*;

public class BufferedCharStreamDo {
	public static void main(String[] args) {
		int i,len = 0;
		String crSet = "_ansi";
		
		String sourFile = "./bin/data/base"+crSet+".txt";
		String destFile = "./bin/data/output_char"+crSet+".txt";
		
		System.out.println("Source Name : " + sourFile);
		System.out.println("Target Name : " + destFile);

		try {
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			br = new BufferedReader(new FileReader(new File(sourFile)));
			bw = new BufferedWriter(new FileWriter(destFile));
			
			while((i=br.read())!= -1) {
				bw.write(i);
				
				len+=1;
				System.out.println("Read Data["+i+","+len+"]");
			}
			br.close();
			bw.close();
			System.out.println("finished");
			
			
		}catch(IOException e) {
			System.out.println(e);
		}

	}
}
