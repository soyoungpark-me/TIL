package _05_Internet_Address;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReverseTest {
    public static void main(String[] args) {
        try {
//            InetAddress ia = InetAddress.getByName("172.217.161.36");
                        InetAddress ia = InetAddress.getByName("konkuk.ac.kr");
            System.out.println(ia.getCanonicalHostName());
            System.out.println(ia.getHostName());
            // getCanocicalHostName은 가능하면 DNS에 요청해 정보를 가져오며
            // 이미 저장된 호스트 네임이 있는 경우 갱신한다.
        } catch (UnknownHostException ex) {}
    }
}
