import java.io.*;

public class InputStreamDo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream in = System.in;
		
		try {
			while(true) {
				int i = in.read();
				if(i==-1) break;
				char c = (char)i;
				System.out.print(c);
			}
		} catch (IOException ie) {
			// TODO: handle exception
			System.out.println("Is it Exception? Cause : "+ie);
		}
	}

}
