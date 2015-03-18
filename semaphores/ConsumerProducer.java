package semaphores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
/**
 * ConsumerProducer is the main entry point of the program and initializes all components
 * of the producer-consumer problem in this simulation. The ConsumerProducer class creates
 * the buffer, semaphores and mutex and initializes them to thier initial values. It then creates
 * two threads running as the consumer and producer objects, passing them the shared buffer,
 * semaphores and mutex and then calls them to start running.
 * @author bensoer
 *
 */
public class ConsumerProducer {

	public static void main(String[]args){
		
		/** buffer storing data between producer and comsumer **/
		List<Integer> buffer = new ArrayList<Integer>();
		/** bufferFull semaphore. Keeps track how many spots are taken **/
		Semaphore bufferFull = new Semaphore(20);
		/** bufferEmpty semaphore. Keeps track how many spots are empty **/
		Semaphore bufferEmpty = new Semaphore(20);
		/** access mutex, enforces mutual exclusion to read/write the buffer **/
		Mutex access = new Mutex();
		
		//need to set empty buffer to be full since its the bufferEmpty semaphore
		//this semaphore needs to be blocking when the buffer is empty so that the
		//consumer does not consume on an empty buffer
		try {
			bufferEmpty.acquire(20);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Thread consumer = new Consumer(buffer, bufferFull, bufferEmpty, access);
		Thread producer = new Producer(buffer, bufferFull, bufferEmpty, access);
		
		producer.start();
		consumer.start();
		
		
		try {
			//forces these threads to terminate before ConsumerProducer terminates
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
