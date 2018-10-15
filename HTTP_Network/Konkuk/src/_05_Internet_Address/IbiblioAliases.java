package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IbiblioAliases {
    public static void main(String args[]) {
        try {
            InetAddress ibiblio = InetAddress.getByName("www.ibiblio.org");
            InetAddress helios = InetAddress.getByName("www.cafeaulait.org");

            if (ibiblio.equals(helios)) {
                System.out.println("www.ibiblio.org is the same as www.cafeaulait.org");
            } else {
                System.out.println("www.ibiblio.org is not the same as www.cafeaulait.org");
            }
        } catch (UnknownHostException e) {
            System.out.println("Host lookup failed.");
        }
    }
}
