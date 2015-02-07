package monitors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * BufferMonitor is the monitor that is protecting the shared buffer between the Consumer and
 * Producer threads. The implementation uses locks to ensure mutual exclusion and synchronization.
 * Conditional variables and a number of counting variables are used to keep track of the state
 * of the buffer. The buffer used in the monitor is an array but it is implemented to mimick a
 * circular buffer by using "front" and "rear" variables to keep track where each end is.
 * 
 * @author bensoer
 *
 */
public class BufferMonitor {

	private final int buffer[];
	private final int bufferSize;
	
	private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    
    /** count - keeps track how many items are in the buffer **/
    private int count = 0;
    /** front - keeps track of the index of the front of the buffer **/
    private int front = 0;
    /** rear - keeps track of the index of the end of the buffer **/
    private int rear = 0;
    
    public BufferMonitor(int bufferSize){
    	buffer = new int[bufferSize];
    	this.bufferSize = bufferSize;
    }
	
    /**
     * addToBuffer adds the parameter passed value to the end of the buffer ensuring mutual exclusion
     * with locks and synchronization with await() and signal(). This method is meant to be used by
     * the producer
     * @param value the int value to be placed at the front of the buffer
     * @throws InterruptedException
     */
	public void addToBuffer(int value) throws InterruptedException{
		lock.lock();
		
		
		try{
			while(count == bufferSize){
				notFull.await();
			}
			buffer[rear] = value;
			System.out.println("Producer: placed value " + value + " in buffer position " + (rear));
            rear = (rear + 1) % bufferSize;
            count++;
            
            

            notEmpty.signal();
		}finally{
			lock.unlock();
			
		}
		
	}
	/**
	 * fetchFromBuffer fetches the next item from the front of the buffer ensuring mutual exclusion
	 * with locks and synchronization with await() and signal(). This method is meant to be used by
	 * the consumer
	 * @return and int value from the buffer
	 * @throws InterruptedException
	 */
	public int fetchFromBuffer() throws InterruptedException{
		lock.lock();
		
		try{
			while(count == 0){
				notEmpty.await();
			}
			
			int result = buffer[front];
			System.out.println("Consumer: got value " + result + " in buffer position " + (front));
            front = (front + 1) % bufferSize;
            count--;
            
            notFull.signal();

            return result;
		}finally{
			lock.unlock();
		}
	}
}
