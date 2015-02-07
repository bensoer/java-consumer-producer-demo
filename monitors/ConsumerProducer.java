package monitors;

/**
 * ConsumerProducer is the main entry point for running the the Producer Consumer program. Passing a first
 * argument to the program sets the time in seconds the program will run. Passing a second argument will set
 * the buffer size used in the program. If neither are supplied, the default buffer size of 20 and time of
 * infinite will be used
 * 
 * @author bensoer
 *
 */
public class ConsumerProducer {

	
	public static void main(String[]args){
	
		int bufferSize;
		try{
			bufferSize = Integer.parseInt(args[1]);
		}catch(ArrayIndexOutOfBoundsException aioobe ){
			System.out.println("Main Thread: Using default buffer size of 20 units");
			bufferSize = 20; //if no parameter passed 20 is the default value
		}
		
		int time;
		try{
			time = Integer.parseInt(args[0]) * 1000; // convert to milliseconds
		}catch(ArrayIndexOutOfBoundsException aioobe ){
			System.out.println("Main Thread: Using default time of infinite");
			time = -1; //if no parameter passed then time is unlimited
		}
		
		long currentTime = System.currentTimeMillis();
		
		
		
		BufferMonitor bm = new BufferMonitor(bufferSize);
		System.out.println("Main Thread: Creating consumer thread");
		Thread consumer = new Consumer(bm, time, currentTime);
		System.out.println("Main Thread: Creating producer thread");
		Thread producer = new Producer(bm, time, currentTime);
		
		producer.start();
		consumer.start();
		
		
		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
			
		
		
		
		System.out.println("Main Thread: Terminating Thread");
		
		
		
		
	}
}
