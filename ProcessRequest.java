
public class ProcessRequest extends Thread{

	public void run()
	{
		StringBuilder responseString = new StringBuilder();
		while(true)
		{
			if(FogNode.processingQueue.isEmpty())
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
				responseString.setLength(0);
				try 
				{
					//FogNode.processedRequest.add(FogNode.processingQueue.peek());
					for(String s : FogNode.processingQueue.peek()) 
					{
				    	 responseString.append(s);
				    	 responseString.append(" ");
					}
					responseString.append("Proceesed by "+FogNode.myIPAddress+" FogNode.");
					String[] p=responseString.toString().split(" ");
					FogNode.responseQueue.add(p);
					Thread.sleep((long) Integer.parseInt((FogNode.processingQueue.poll())[1]));
					
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
