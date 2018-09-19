package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            // local loopback (자기 자신)

            System.out.println(address);
        } catch (UnknownHostException ex) {
            System.out.println("could not find address");
        }
    }
}
