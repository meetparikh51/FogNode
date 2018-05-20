import java.util.concurrent.TimeUnit;


public class InsertQueue extends Thread{
	
	int delay;
	public void run()
	{
		while(true)
		{
			if(FogNode.incomingRequest.isEmpty())
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			else
			{
	           try
	           {
	        	   delay = Integer.parseInt((FogNode.incomingRequest.peek())[1]);
	        	   if(delay + FogNode.queueDelay <= FogNode.maximumResponseTime)
	        	   {
		        	   System.out.println("Inserting request into processing queue with request Type "+(FogNode.incomingRequest.peek())[1]);
		        	   FogNode.processingQueue.add(FogNode.incomingRequest.poll());

	        	   }
	        	   else
	        	   {
		        	   System.out.println("Inserting request into forwarding queue with request Type "+(FogNode.incomingRequest.peek())[1]);
		        	   FogNode.forwardingQueue.add(FogNode.incomingRequest.poll());
	        	   }
				
	        	   Thread.sleep(500);
				} 
	           	catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
