import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class ResponseIoT extends Thread {
	
	public void run() 
	{
		try
		{
			byte[] sendData = new byte[64];
			DatagramPacket sendPacket;
			while(true)
			{
				if(FogNode.responseQueue.isEmpty())
				{
					Thread.sleep(1000);
				}
				else
				{
					System.out.println("Sending response to the "+(FogNode.responseQueue.peek()[3])+" using "+(FogNode.responseQueue.peek()[4])+" port...");
					sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName((FogNode.responseQueue.peek()[3])), Integer.parseInt((FogNode.responseQueue.peek()[4])));
					sendData = Arrays.toString(FogNode.responseQueue.poll()).getBytes();
					//sendData = FogNode.responseQueue.poll().getBytes();
					FogNode.ds.send(sendPacket);
					
				} 
	        }
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
