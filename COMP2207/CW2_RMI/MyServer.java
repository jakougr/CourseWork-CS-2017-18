import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Random;
import java.net.*;

public class MyServer extends java.rmi.server.UnicastRemoteObject implements CiphertextInterface {
	private static int p = 191;
	private static int x; 
	private static int a;
	private static int y;
	private static int g = 131;

	Random rn = new Random();
	Registry ctreg;
	String cipher;
	int port= 3235;
	Registry registry;
	private static int key;
	String address;
	CiphertextInterface ctstub;

	public MyServer() throws RemoteException, NotBoundException {

		try {
	///////////// SETTING UP THE SERVER//////////////////
			address = (InetAddress.getLocalHost()).toString();
		} catch (Exception e) {
			throw new RemoteException("Address Error");
		}
		System.out.println("My address: " + address + " on port: " + port);
		try {
			registry = LocateRegistry.createRegistry(port);
			registry.rebind("rmiServer", this);
			ctreg = LocateRegistry.getRegistry("svm-tjn1f15-comp2207.ecs.soton.ac.uk", 12345);
			ctstub = (CiphertextInterface) ctreg.lookup("CiphertextProvider");

		} catch (RemoteException e) {
			throw e;
		}
		a = rn.nextInt(3) + 1; // In case a=0;
	}
	////////////////////// ENDED SETUP////////////////////

	static public void main(String args[]) {
		try {

			MyServer theServer = new MyServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	///////////////// COMMUNACATION METHOD ////////////////
	@Override
	public String get(String uid, int keyClient) throws RemoteException {

		if (keyClient == -1) {
			x = (g ^ a) % p;
			return (g + "/" + p + "/" + x).toString();
		}
		if (keyClient == -2) {

			y = Integer.parseInt(uid);

			key = (y ^ a) % p;

		}

		if (keyClient == key) {
			cipher = ctstub.get(uid, key);
			return cipher;
		} else {
			return null;
		}

	}
}
