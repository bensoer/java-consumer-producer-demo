package monitors;

import java.util.Random;

public class Consumer extends Thread{

	private final BufferMonitor sharedBuffer; 
	private final Random timeGenerator = new Random();
	
	private final int durationTime;
	private final long startTime;
	
    public Consumer(BufferMonitor buffer, int time, long currentTime){
    	this.sharedBuffer = buffer;
    	this.durationTime = time;
    	this.startTime = currentTime;
    }
    
    public void run() {
    	
    	System.out.println("Consumer: Alive and Running");
    	while(thereIsStillTime()){
			int sleepTime = timeGenerator.nextInt(1000); //number between 0 and 1000 milliseconds
			
			try {
				//System.out.println("Consumer is sleeping for: " + sleepTime + " milliseconds");
				Thread.sleep(sleepTime);
				
				sharedBuffer.fetchFromBuffer();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
    	System.out.println("Consumer: Terminating Thread");
    }
    
    private boolean thereIsStillTime(){
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
