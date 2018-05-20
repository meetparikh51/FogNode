import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class SendToNeighbor extends Thread {
	
	public void run()
	{
		
		while(true)
		{
			boolean var1 = false;
			
			if(!(FogNode.neighbors.isEmpty()))
			{
				
				try
				{
					StringBuilder stringBuilder = new StringBuilder();
					System.out.println("Sending delay to neighbors");
					for(int i=0;i<FogNode.neighbors.size();i++)
					{
						stringBuilder.setLength(0);
						stringBuilder.append(FogNode.myTCP);
						stringBuilder.append(" ");
						stringBuilder.append(FogNode.queueDelay);
						System.out.println("Sending delay to neighbor "+FogNode.neighbors.get(i));
						send(InetAddress.getByName(FogNode.myIPAddress), (int) FogNode.neighbors.get(i), stringBuilder.toString(), null);
					}
					Thread.sleep(1000);
				}
				 catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				var1 = FogNode.forwardingQueue.isEmpty();	
				if(var1 == false )
				{
					
					//FogNode.forwardingQueue.peek()[2] = String.valueOf(Integer.parseInt(FogNode.forwardingQueue.peek()[2])-1);
					
					if(Integer.parseInt(FogNode.forwardingQueue.peek()[2]) == 1)
					{
						System.out.println("Sending request to Cloud...");
						FogNode.cloudProcessingQueue.add(FogNode.forwardingQueue.poll());
					}
					
					else
					{
						StringBuilder stringBuilder = new StringBuilder();
						for(int i=0;i<FogNode.forwardingQueue.peek().length;i++)
						{
							stringBuilder.append(FogNode.forwardingQueue.peek()[i]);
							stringBuilder.append(" ");
						}
					
						try {
							int port;
							port = (int) (FogNode.sortedDelay.keySet().toArray())[0];
							stringBuilder.append("Forwarding-request-to"+FogNode.myIPAddress+"-on-port-number-"+port+"-by-"+FogNode.myIPAddress+"-with-"+FogNode.myTCP+"-port-nummber..");
							System.out.println("port on which request is sending is ");
							System.out.println("Forwarding-request-to"+FogNode.myIPAddress+"-on-port-number-"+port+"-by-"+FogNode.myIPAddress+"-with-"+FogNode.myTCP+"-port-nummber..");
							FogNode.forwardingQueue.poll();
							send(InetAddress.getByName(FogNode.myIPAddress), port, null, stringBuilder.toString());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				}
				
			}
			
			
			else
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	private void send(InetAddress ipAddress, int port, String delay, String forwardingRequest) throws IOException
	{
		Socket client = new Socket(ipAddress, port);
		OutputStream outToServer = client.getOutputStream();
		DataOutputStream out = new DataOutputStream(outToServer);
		if(forwardingRequest == null)
		{
			System.out.println("Sending delay...");
			out.writeUTF(delay);
		}
		else
		{
			out.writeUTF(forwardingRequest);
		}
		client.close();
	}
}
