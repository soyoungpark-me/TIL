package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OreillyByName {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.oreilly.com");
            System.out.println(address);
        } catch (UnknownHostException e) {
            System.out.println("Could not find www.oreilly.com");
        }
    }
}
