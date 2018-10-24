package _03_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

// IP 주소의 특성을 검사한다.
public class IPCharacteristics {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName(args[0]);

            if (address.isAnyLocalAddress()) {
                System.out.println(address + " is a wildcard address.");
            }
            if (address.isLoopbackAddress()) {
                System.out.println(address + " is loopback address.");
            }
            if (address.isLinkLocalAddress()) {
                System.out.println(address + " is a link0local address.");
            } else if (address.isSiteLocalAddress()) {
                System.out.println(address + " is a link-local address.");
            } else {
                System.out.println(address + " is a global address.");
            }

            if (address.isMulticastAddress()) {
                if (address.isMCGlobal()) {
                    System.out.println(address + " is a global multicast address.");
                } else if (address.isMCOrgLocal()) {
                    System.out.println(address + " is a organization wild multicast address.");
                } else if (address.isMCSiteLocal()) {
                    System.out.println(address + " is a subnet wide multicast address.");
                } else if (address.isMCLinkLocal()) {
                    System.out.println(address + "is a subnet wide multicast address.");
                } else if (address.isMCNodeLocal()) {
                    System.out.println(address + "is an interface-local multicast address");
                } else {
                    System.out.println(address + " is an unknown multicast address type.");
                }
            } else {
                System.out.println(address + " is a unicast address");
            }
        } catch (UnknownHostException ex) {
            System.err.println("Could not resolve " + args[0]);
        }
    }
}
