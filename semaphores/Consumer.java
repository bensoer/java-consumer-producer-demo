package semaphores;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
/**
 * Consumer is the consumer thread that takes the last element from the buffer and consumes
 * it in the producer-consumer problem. It will first check bufferEmpty to determine whether
 * the buffer has content, then it will acquire access with the access mutex to check if it can
 * access the buffer. It will then consume the value by printing it to the screen
 * @author bensoer
 *
 */
public class Consumer extends Thread{

	private final List<Integer> sharedBuffer;
	private final Semaphore bufferFull;
	private final Semaphore bufferEmpty;
	private final Mutex access;
	
	private final Random sleepGenerator = new Random();
	
	public Consumer(List<Integer> buffer, Semaphore bufferFull, Semaphore bufferEmpty, Mutex access ){
		this.sharedBuffer = buffer;
		this.bufferEmpty = bufferEmpty;
		this.bufferFull = bufferFull;
		this.access = access;
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(sleepGenerator.nextInt(1000));
				bufferEmpty.acquire();
				access.acquire();
				//int lastIndex = sharedBuffer.size()-1;
				int number = sharedBuffer.remove(sharedBuffer.size()-1);
				access.release();
				bufferFull.release();
				
				System.out.println("Consumer: got value " + number);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
