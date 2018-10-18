package _05_Internet_Address;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.UnexpectedException;

// ttl : 몇 홉 만에 도달할 수 있는지, timeout : 특정 시간 내에 도달할 수 있는지
public class ReachabilityTest {
    public static void main(String[] args) {
        try {
            byte[] addr = {(byte)202, 30, 38, 108};
            InetAddress address = InetAddress.getByAddress(addr);

//            String addr = "www.konkuk.ac.kr";
              //InetAddress address = InetAddress.getByName(addr);

            int timeout = 5000;
            int ttl = 10;

            if (address.isReachable(timeout)) { // 도달할 수 있을 경우 true, 불가능하면 false 리턴
                System.out.println(address.getHostName() + " can be reached");
            } else {
                System.out.println(address.getHostName() + " cannot be reached");
            }
        } catch (UnexpectedException ex){
        } catch (IOException e) {}
    }
}
