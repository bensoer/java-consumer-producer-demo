package monitors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferMonitor {

	private final int buffer[];
	private final int bufferSize;
	
	private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    
    private int count = 0;
    private int front = 0;
    private int rear = 0;
    
    public BufferMonitor(int bufferSize){
    	buffer = new int[bufferSize];
    	this.bufferSize = bufferSize;
    }
	
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
			//System.out.println("ADD TO BUFFER COMPLETE");
			//System.out.println("count is: " + count + "front is: " + front + "rear is: " + rear);
			lock.unlock();
			
		}
		
	}
	
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
			//System.out.println("FETCH FROM BUFFER COMPLETE");
			//System.out.println("count is: " + count + "front is: " + front + "rear is: " + rear);
			lock.unlock();
		}
	}
}
