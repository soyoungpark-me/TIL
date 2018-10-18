package _05_Internet_Address;

import java.net.InetAddress;

public class AddressVersionTests {
    public static int getVersion(InetAddress ia) {
        byte[] address = ia.getAddress();
        if (address.length == 4) return 4;          // IPv4
        else if (address.length == 16) return 6;    // IPv6
        else return -1;
    }
}
