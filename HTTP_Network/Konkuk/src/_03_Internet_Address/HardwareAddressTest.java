package _03_Internet_Address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HardwareAddressTest {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("IP address: " + address.getHostAddress());

        NetworkInterface ni = NetworkInterface.getByInetAddress(address); // 주소로 직접 인터페이스 가져오기
        System.out.println("MAC address: " + getMACIdentifier(ni));
    }

    public static String getMACIdentifier(NetworkInterface ni) {
        StringBuilder identifier = new StringBuilder();

        try {
            byte[] macBuffer = ni.getHardwareAddress();
            if (macBuffer != null) { // 버퍼가 null이 아니라면
                for (int i=0; i<macBuffer.length; i++) {
                    identifier.append(String.format("%02X%s", macBuffer[i], (i < macBuffer.length - 1) ? "-": ""));
                    // 맞춰서 인코딩해준다.
                }
            } else {
                return "---";
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return identifier.toString();
    }
}
