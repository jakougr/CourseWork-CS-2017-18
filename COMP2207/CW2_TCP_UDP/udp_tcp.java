
import java.net.DatagramPacket;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.net.Socket;
import java.io.BufferedWriter;
import java.net.InetAddress;
import java.net.DatagramSocket;

public class test {

	private static Socket socket;

	public static void main(String[] args) {
		
		System.out.print("hi");

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("wsn.ecs.soton.ac.uk");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = "ik5g15";
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5005);
		clientSocket.send(sendPacket);
		System.out.println("UDP : Message sent to the server : " + sentence);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("UDP Response : FROM SERVER:" + modifiedSentence);
		clientSocket.close();

	}
}