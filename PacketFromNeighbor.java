import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class PacketFromNeighbor extends Thread {

	public void run()
	{
		try {
			FogNode.serverTCPSocket = new ServerSocket(FogNode.myTCP);
			String incomingPacket;
			Socket server;
			while(true)
			{
				server = FogNode.serverTCPSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				incomingPacket = in.readUTF();
				if(incomingPacket.length() < 10)
				{
					String[] p = incomingPacket.split(" ");
					FogNode.incomingQueuingDelay.put(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
				}
				else
				{
					System.out.println("Request packet arrived from neighbor");
					String[] p = incomingPacket.split(" ");
					p[2] = String.valueOf(Integer.parseInt(p[2])-1);
					//String[] inputdata = new String[p.length]; 
		            // for(String i : p)
		            
		            FogNode.incomingRequest.add(p);
				}
				Thread.sleep(1000);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
