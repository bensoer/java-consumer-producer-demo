package semaphores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class ConsumerProducer {

	public static void main(String[]args){
		
		List<Integer> buffer = new ArrayList<Integer>();
		Semaphore bufferFull = new Semaphore(20);
		Semaphore bufferEmpty = new Semaphore(20);
		Mutex access = new Mutex();
		
		Thread consumer = new Consumer(buffer, bufferFull, bufferEmpty, access);
		Thread producer = new Producer(buffer, bufferFull, bufferEmpty, access);
		
		producer.start();
		consumer.start();
		
		
		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
