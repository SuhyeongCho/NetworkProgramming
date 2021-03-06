
class ThreadDo extends Thread{
	String name;
	public ThreadDo(String str, int prio) {
		name = str;
		setPriority(prio);
	}
 
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println(name+": "+getPriority());
		}
		System.out.println(name+": finished");
	}
}

public class ThreadPriorityDo {
	public static void main(String[] args) {
//		ThreadDo thread1 = new ThreadDo("Thread1",Thread.MAX_PRIORITY);
//		ThreadDo thread2 = new ThreadDo("thread2", Thread.MIN_PRIORITY);
		
		ThreadDo thread1 = new ThreadDo("Thread1",Thread.MIN_PRIORITY);
		ThreadDo thread2 = new ThreadDo("thread2", Thread.MAX_PRIORITY);
		
		thread1.start();
		thread2.start();
	}
}