package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class KonkukAliases {
    public static void main(String[] args) {
        try {
            InetAddress konkuk = InetAddress.getByName("www.konkuk.ac.kr");
            InetAddress winter = InetAddress.getByName("winter.konkuk.ac.kr");

            if (konkuk.equals(winter)) {
                System.out.println("same");
            } else {
                System.out.println("not same");
            }
        } catch (UnknownHostException ex) {}
    }
}
