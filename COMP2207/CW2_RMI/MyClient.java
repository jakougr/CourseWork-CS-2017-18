import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.net.*;
import java.net.UnknownHostException;

public class MyClient {
	private static int key;
	private static String cipher;
	private static int p;
	private static int g;
	private static int b; 
	private static int y; 
	private static int x; 
	CiphertextInterface rmiServer;
	Registry registry;

	public void secureKey() throws RemoteException {

		Random rn = new Random();
		b = rn.nextInt(3) + 1;
		String temp = (rmiServer.get("", -1));
		
		String[] nums = temp.split("/");
		g = Integer.parseInt(nums[0]);
		p = Integer.parseInt(nums[1]);
		x = Integer.parseInt(nums[2]);

		y = (g ^ b) % p;
		key = (x ^ b) % p;

		rmiServer.get(Integer.toString(y), -2);

	}

	static public void main(String args[]) {
		MyClient clientObject = new MyClient(args[0], args[1]);
	}

	public MyClient(String address, String name) {
		String serverAddress = address;
		String serverPort = "3235";
		String username = name;
		try {
			System.setProperty("java.security.policy", "mypolicy");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
			rmiServer = (CiphertextInterface) (registry.lookup("rmiServer"));
			secureKey();
			cipher = rmiServer.get(username, key);
			System.out.println("ORIGINAL TEXT: " + cipher);
			System.out.println();
			System.out.println("DECRYPT : " + decrypt(cipher));

			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}
	public String decrypt(String chunk) {
		
		for (int i = 0; i < 2; i++) {
			chunk = (substitution(chunk));
			
		}
		chunk = new StringBuffer(chunk).reverse().toString();
		for (int i = 0; i < 2; i++) {
			chunk = transporting((chunk));
			
		}
		chunk = new StringBuffer(chunk).reverse().toString();
		return chunk;
	}

	public String transporting(String temp) {

		int rotateTimes = (key % 8);
		ArrayList<String> list = new ArrayList<String>();
		String[] chunk = temp.split("(?<=\\G........)");
		for (String s : chunk) {
			char[] x = s.toCharArray();
			for (int i = 0; i < rotateTimes; i++) {

				for (int j = x.length - 1; j > 0; j--) {
					char tranTemp = x[j];
					x[j] = x[j - 1];
					x[j - 1] = tranTemp;
				}
			}
			list.add(String.valueOf(x));
		}

		temp = String.join("", list);
		return temp;
	}

	String substitution(String msg) {
		String s = "";
		int len = msg.length();
		int shift = key % 26;
		for (int x = 0; x < len; x++) {
			char c = (char) (msg.charAt(x) - shift);
			if (c > 'Z' || c < 'A')
				s += (char) (msg.charAt(x) + (26 - shift));
			else
				s += (char) (msg.charAt(x) - shift);
		}
		return s;
	}


}