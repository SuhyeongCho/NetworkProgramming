import java.io.*;

public class FileReaderDo{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileReader fr = new FileReader("./bin/data/software_read.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String s;
			while((s=br.readLine())!=null) {
				System.out.println(s);
			}
			fr.close();
			
		}catch(IOException ie) {
			System.out.println("Is it Exception? Cause : "+ie);
		}

	}

}
