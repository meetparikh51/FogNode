
public class Cloud extends Thread{

	public void run()
	{
		StringBuilder stringBuilder = new StringBuilder();
		while(true)
		{
			if(FogNode.cloudProcessingQueue.isEmpty())
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
				stringBuilder.setLength(0);
				try 
				{
					System.out.println("Processing by cloud......");
					//FogNode.processedRequest.add(FogNode.processingQueue.peek());
					for(String s : FogNode.cloudProcessingQueue.peek()) 
					{
				    	 stringBuilder.append(s);
				    	 stringBuilder.append(" ");
					}
					stringBuilder.append("Proceesed by cloud");
					String[] p = stringBuilder.toString().split(" ");
					FogNode.responseQueue.add(p);
					
					Thread.sleep((long) Integer.parseInt((FogNode.cloudProcessingQueue.poll())[1])/100);
					
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
