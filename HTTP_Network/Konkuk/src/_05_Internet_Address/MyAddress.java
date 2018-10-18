package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyAddress {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            String dottedQuad = address.getHostAddress(); // getHostAddress는 IP주소 반환
            System.out.println("My Address is " + dottedQuad);
            // local loopback (자기 자신)
            // localhost, 127.0.0.1

            System.out.println(address); // getLocalHost는 호스트네임/IP주소 반환
        } catch (UnknownHostException ex) {
            System.out.println("could not find address");
        }
    }
}
