package networksCourseWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PingTime {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("google.com");
		list.add("google.co.uk");
		list.add("google.com.au");
		list.add("youtube.com");
		list.add("instagram.com");
		list.add("yandex.ru");
		list.add("wikipedia.org");
		list.add("stackoverflow.com");
		list.add("linkedin.com");
		list.add("yahoo.com");
		list.add("yahoo.co.uk");
		list.add("yahoo.com.au");
		list.add("facebook.com");
		list.add("netflix.com");
		list.add("ebay.co.uk");
		list.add("bbc.co.uk");
		list.add("www.bbc.co.uk");
		list.add("reddit.com");
		list.add("github.com");
		list.add("taobao.com");
		list.add("wikia.com");
		list.add("loopsofzen.uk");
		list.add("iinet.net.au");

		for (String site : list) {
			String ip = site;
			String pingResult = "";
			System.out.println("***********************" + site + " IPv4 ***********************");
			String pingCmd = "ping -n 9 " + ip + " -4";
			try {
				Runtime r = Runtime.getRuntime();
				Process p = r.exec(pingCmd);

				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					pingResult += inputLine;
				}
				in.close();

			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println();
			
			System.out.println("***********************" + site + " IPv6 ***********************");
			pingCmd = "ping -n 9 " + ip + " -6";
			try {
				Runtime r = Runtime.getRuntime();
				Process p = r.exec(pingCmd);

				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					pingResult += inputLine;
				}
				in.close();

			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println();
			System.out.println();
		}

	}

}

