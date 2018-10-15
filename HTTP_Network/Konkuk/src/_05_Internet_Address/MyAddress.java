package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String dottedQuad = address.getHostAddress();
            System.out.println("My Address is " + dottedQuad);
            // local loopback (자기 자신)
            // localhost, 127.0.0.1

            System.out.println(address);
        } catch (UnknownHostException ex) {
            System.out.println("could not find address");
        }
    }
}
