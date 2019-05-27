import java.io.*;

public class OutputStreamDo {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		OutputStream out = (System.out); //per byte
		OutputStreamWriter out = new OutputStreamWriter(System.out); //per char

		
		char out1 = 'A';
		char out2 = '가';
	
		try {
			out.write(out1);
			out.write(out2);
			
			out.flush();
			out.close();
		} catch (IOException ie) {
			// TODO: handle exception
			System.out.println("Is it Exception? Cause : "+ie);
		}
	}
}
