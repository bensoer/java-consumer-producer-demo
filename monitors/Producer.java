package monitors;

import java.util.Random;
/**
 * Producer is the producer thread class in the Consumer Producer problem. It will consume the first item
 * in the buffer. Thread.sleep is used to immitate inconsistent timing of the producer as would be in an
 * operating system
 * 
 * @author bensoer
 *
 */
public class Producer extends Thread{

	private final BufferMonitor sharedBuffer;
	private final Random timeGenerator = new Random();
	private final Random numberGenerator = new Random();
	
	private final int durationTime;
	private final long startTime;
	
	public Producer(BufferMonitor buffer, int time, long currentTime){
		this.sharedBuffer = buffer;
		this.durationTime = time;
		this.startTime = currentTime;
	}
	
	public void run(){
		System.out.println("Producer: Alive and Running");
		while(thereIsStillTime()){
			int sleepTime = timeGenerator.nextInt(1000); //number between 0 and 1000 milliseconds
			
			try {
				//System.out.println("Producer: sleeping for: " + sleepTime + " milliseconds");
				Thread.sleep(sleepTime);
				int number = numberGenerator.nextInt();
				
				sharedBuffer.addToBuffer(number);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		System.out.println("Producer: Terminating Thread");
	}
	/**
     * thereIsStillTime determines if there is still time to continue running in the program
     * or if it should terminate
     * @return boolean - true means there is still time, false means there is not and the
     * thread should terminate
     */
	private boolean thereIsStillTime(){
		// default set value to mean infinite time was selected
		if(durationTime == -1){
    		return true;
    	}
    	long timeNow = System.currentTimeMillis();
    	if((timeNow - startTime) >= durationTime){
    		return false;
    	}else{
    		return true;
    	}
    }
}
