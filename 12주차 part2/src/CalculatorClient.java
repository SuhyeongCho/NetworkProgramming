import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class CalculatorClient {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: Classname ServerName ServiceName");
			System.exit(1);
		}
		
		String mServer = args[0];
		String mServName = args[1];
		
		System.out.println("Remote Method Invocate to "+mServer+", Service name : "+mServName);
		
		try {
			Calculator c = (Calculator)Naming.lookup("rmi://"+mServer+"/"+mServName);
			System.out.println("Minus(4, 3) : "+ c.sub(4, 3));
			System.out.println("Plus(4, 5) : "+ c.add(4, 5));
			System.out.println("Multiply(3, 6) : "+ c.mul(3, 6));
			System.out.println("Division(9, 3) : "+ c.div(9, 3));
			
			int[] arr = {1,2,3,4,5,6,7,8,9,10};
			System.out.println("Val([1,2,3,4,5,6,7,8,9,10]) : "+ c.var(arr));
			int a1 = 1;
			int b1 = -4;
			int c1 = 3;
			System.out.println("Qua(X^2 - 4*X + 3) : "+ c.qua1(a1, b1, c1)+","+ c.qua2(a1, b1, c1));

		} catch (MalformedURLException mue) {
			System.out.println("MalformedURLException : "+mue);
		} catch (RemoteException re) {
			System.out.println("RemoteException : "+re);
		} catch (NotBoundException nbe) {
			System.out.println("NotBoundException : "+nbe);
		} catch (java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException : "+ae);
		}
	}
	
	
}