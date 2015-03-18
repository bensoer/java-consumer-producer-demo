package semaphores;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
/**
 * Producer is the producer thread that will produce items to place on the shared buffer
 * in the conumer-producer problem. It will first acquire a spot on the bufferFull semaphore
 * so as to ensure that the shared buffer is not full. It will then acquire the access mutex
 * so as to make sure that nothing else is currently reading / writing to the shared buffer.
 * It will then produce a random integer which it will place on the buffer. After which it will
 * release the access mutex and release on the bufferEmpty semaphore so as to signal to the
 * consumer that there is an item available in the buffer
 * @author bensoer
 *
 */
public class Producer extends Thread{
	
	private final List<Integer> sharedBuffer;
	private final Semaphore bufferFull;
	private final Semaphore bufferEmpty;
	private final Mutex access;
	
	private final Random numberGenerator = new Random();
	private final Random sleepGenerator = new Random();
	
	public Producer(List<Integer> buffer, Semaphore bufferFull, Semaphore bufferEmpty, Mutex access ){
		this.sharedBuffer = buffer;
		this.bufferEmpty = bufferEmpty;
		this.bufferFull = bufferFull;
		this.access = access;
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(sleepGenerator.nextInt(1000));
				int number = numberGenerator.nextInt();
				bufferFull.acquire();
				access.acquire();
				sharedBuffer.add(0, number);
				access.release();
				bufferEmpty.release();
				
				System.out.println("Producer: placed value " + number);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
