import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
	

	private static final long serialVersionUID = 1L;

	public CalculatorImpl() throws RemoteException {
		super();
	}
	
	public long add(long a, long b) throws RemoteException {
		return a + b;
	}
	public long sub(long a, long b) throws RemoteException {
		return a - b;
	}
	public long mul(long a, long b) throws RemoteException {
		return a * b;
	}
	public long div(long a, long b) throws RemoteException {
		return a / b;
	}

	public double var(int[] a) throws RemoteException {
		int len = a.length;
		int sum = 0;
		double dsum = 0.0;
		double mean = 0;
		for (int i=0;i<len;i++) {
			sum += a[i];
		}
		mean = (double)sum/len;
		
		for (int i=0;i<len;i++) {
			double tmp = a[i] - mean;
			dsum = tmp*tmp;
		}
		
		return dsum/len;
	}
	
	public double qua1(int a, int b, int c) throws RemoteException {
		int D = b*b - 4*a*c;
		if(D >= 0) {
			return (-b + Math.sqrt(D)) / (2*a);
		} else {
			return 0.0;
		}
	}
	
	public double qua2(int a, int b, int c) throws RemoteException {
		int D = b*b - 4*a*c;
		if(D >= 0) {
			return (-b - Math.sqrt(D)) / (2*a);
		} else {
			return 0.0;
		}
	}
}