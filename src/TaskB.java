import java.util.ArrayList;
import java.util.List;

public class TaskB implements Runnable {

	private Counter cnt;
	
	public TaskB(Counter cnt)
	{
		this.cnt = cnt;
	}

	@Override
	public void run() {
		for(int i=0; i<1000000; i++) {
			cnt.safeIncrement();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int runs = 1;
		int NThreads = 0;
		while (runs < 6) {
		
		switch(runs) {
		case 1:
			NThreads = 2;
			break;
		case 2:
			NThreads = 4;
			break;
		case 3:
			NThreads = 8;
			break;
		case 4:
			NThreads = 16;
			break;
		case 5:
			NThreads = 36;
			break;
		}
		
		long lStartTime = System.nanoTime();
		Counter cnt = new Counter();
		List<Thread> ThreadList = new ArrayList<Thread>();
		for (int i = 0; i < NThreads; i++) {
				          ThreadList.add(new Thread(new TaskB(cnt)));			          
		}
		
		for(Thread t : ThreadList) {
			t.start();
		}
		for(Thread t: ThreadList) {
			try {
	        	  t.join();
	          }
	          catch (InterruptedException e) {
	        	  e.printStackTrace();
	          }
		}
		long lEndTime = System.nanoTime();
		long output = lEndTime - lStartTime;
		System.out.println("Number of Threads: " + NThreads);
		System.out.println("Final Counter = " + cnt.getCounter());
		System.out.println("Elapsed time in milliseconds: " + output / 1000000);
		runs++;
		}
		
		/* Thread t1 = new Thread(new TaskA(cnt));
		Thread t2 = new Thread(new TaskA(cnt));
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Final Counter = " + cnt.getCounter()); */

	}

}

