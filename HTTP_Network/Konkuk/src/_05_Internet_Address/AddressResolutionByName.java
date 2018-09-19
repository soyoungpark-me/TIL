package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressResolutionByName {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            System.out.println(address);
            System.out.println(address.getHostName());
            System.out.println(address.getCanonicalHostName());
            // toString 메소드를 오버라이딩 하고 있어 바로 print 할 수 있다

            InetAddress address2 = InetAddress.getByName("13.125.78.77");
            // getByName은 hostname을 넣어줘도 되고, ip를 string으로 넣어줘도 된다.
            // ip 주소를 넣어도 이를 InetAddress 객체로 만들어서 반납한다.
            // 이럴 경우 hostname은 빈 칸이 된다. hostname이 굳이 필요 없는 상황이기 때문.
            // 궁극적으로 필요한 건 ip주소지, hostname이 아니다.

            System.out.println(address2);

            InetAddress[] addresses = InetAddress.getAllByName("dna.soyoungpark.me");
            for (InetAddress address3: addresses)
                System.out.println(address3);

            byte[] address4 = {107, 23, (byte)216, (byte)196};
            // 127이 넘어가는 숫자는 byte로 형변환 해줘야 한다.
            InetAddress ia = InetAddress.getByAddress(address4);
            InetAddress iaNamed = InetAddress.getByAddress("named.com", address4);
            System.out.println(ia);
            System.out.println(iaNamed);
        } catch (UnknownHostException ex) {
            System.out.println("Coud not find address");
        }
    }
}
