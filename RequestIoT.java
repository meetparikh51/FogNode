import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;


public class RequestIoT extends Thread 
{
	
	public void run() 
	{
		try
		{
			FogNode.ds = new DatagramSocket(FogNode.myUDP);
			byte[] receiveData = new byte[64];
			byte[] sendData = new byte[64];
			DatagramPacket sendPacket;
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			while(true)
			{
				 FogNode.ds.receive(receivePacket);
	             String packetData = new String(receivePacket.getData(),0,receivePacket.getLength());
	             //System.out.println(packetData);
	             String[] p=packetData.split(" ");
	             String[] inputdata = new String[p.length]; 
	            // for(String i : p)
	            for(int i =0; i<p.length;i++)
	            {
	            		 inputdata[i]= p[i].substring(p[i].lastIndexOf(":") + 1);
	            }
	            
	            FogNode.incomingRequest.add(inputdata);
	            System.out.println("Incoming request with request Type "+inputdata[1]);
	            
	            
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
}
