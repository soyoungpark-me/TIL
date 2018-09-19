package _05_Internet_Address;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.UnexpectedException;

public class ReachabilityTest {
    public static void main(String[] args) {
        try {
            byte[] addr = {(byte)202, 30, 38, 108};
            InetAddress address = InetAddress.getByAddress(addr);

//            String addr = "www.konkuk.ac.kr";
              //InetAddress address = InetAddress.getByName(addr);

            int timeout = 5000;
            int ttl = 10;

            if (address.isReachable(timeout)) {
                System.out.println(address.getHostName() + " can be reached");
            } else {
                System.out.println(address.getHostName() + " cannot be reached");
            }
        } catch (UnexpectedException ex){
        } catch (IOException e) {}
    }
}
