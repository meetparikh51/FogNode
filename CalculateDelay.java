import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class CalculateDelay extends Thread{
	
		Iterator iterator;
		int delay;
		String[] par;
		public void run()
		{
			while(true)
			{
				try
				{
					if(FogNode.processingQueue.isEmpty())
					{
						Thread.sleep(1000);
					}
					else
					{
						iterator = FogNode.processingQueue.iterator();
						while(iterator.hasNext())
						{
							System.out.println("Calculating Delay....");
							par = (String[]) iterator.next();
							delay = Integer.parseInt(par[1]);
							FogNode.setQueueDelay(delay); 
							TimeUnit.SECONDS.sleep((long) 0.5);
						}
						Thread.sleep(1000);
					}
				}
			
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}

	}


