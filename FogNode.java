import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


public class FogNode extends Thread {
	
	static long queueDelay = 0; //fognode delay
	static int maximumResponseTime; //Maximumresponse time of the fognode
	static String myIPAddress; //IPaddress of the fognode
	static int myUDP; //UDP port number of fognode
	static int myTCP; //TCP port number of fognode
	static ArrayList neighbors = new ArrayList(); //Neighbours of fognode
	static HashMap incomingQueuingDelay = new HashMap(); //Incoming queuing delay from neighboring fognodes
	static Map sortedDelay; //Sorted queuing delay
	static Queue<String[]> incomingRequest = new LinkedList<String[]>(); //Incoming requests
	static Queue<String[]> processingQueue = new LinkedList<String[]>(); //Processing request
	static Queue<String[]> forwardingQueue = new LinkedList<String[]>(); //Requests to be forward to the best neighbor
	static Queue<String[]> responseQueue = new LinkedList<String[]>(); //Response to be send to IoT node
	//static Queue<String[]> processedRequest = new LinkedList<String[]>(); //Processed request
	static Queue<String[]> cloudProcessingQueue = new LinkedList<String[]>();
	static DatagramSocket ds;
	//static Socket clientTCPSocket;
	static ServerSocket serverTCPSocket;
	
	
	public static long getQueueDelay() {
		return queueDelay;
	}

	public static void setQueueDelay(long delay) {
		queueDelay = queueDelay + delay;
	}

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws InterruptedException, UnknownHostException
	{
		maximumResponseTime = Integer.parseInt(args[0]);
		myIPAddress = args[1];
		myUDP = Integer.parseInt(args[2]);
		myTCP = Integer.parseInt(args[3]);
		for(int i=5;i<args.length;i=i+2)
		{
			neighbors.add(Integer.parseInt(args[i]));
		}
		System.out.println("Maximum Response time of this fognode is: "+maximumResponseTime);
		System.out.println("IP address of this fognode is: "+myIPAddress);
		System.out.println("UDP port number for this fognode is: "+myUDP);
		System.out.println("TCP port number for this fognode is: "+myTCP);
		for(int i=0;i<neighbors.size();i++)
		{
			System.out.println("IP Address of neighbour "+(i+1)+" is "+myIPAddress+" and port number is "+neighbors.get(i));
		}
		RequestIoT r1 = new RequestIoT();
		CalculateDelay q1 = new CalculateDelay();
		InsertQueue insertQueue = new InsertQueue();
		ProcessRequest processRequest = new ProcessRequest();
		ResponseIoT responseIoT = new ResponseIoT();
		CalculateBestNeighbour calculateBestNeighbour = new CalculateBestNeighbour();
		PacketFromNeighbor packetFromNeighbor = new PacketFromNeighbor();
		SendToNeighbor sendToNeighbor = new SendToNeighbor();
		Cloud cloud = new Cloud();
		r1.setPriority(10);
		responseIoT.setPriority(10);
		cloud.setPriority(8);
		r1.start();
		System.out.println("Listening incoming requests...");
		q1.start();
		insertQueue.start();
		packetFromNeighbor.start();
		calculateBestNeighbour.start();
		sendToNeighbor.start();
		processRequest.start();
		responseIoT.start();
		cloud.start();
	}

	
}
