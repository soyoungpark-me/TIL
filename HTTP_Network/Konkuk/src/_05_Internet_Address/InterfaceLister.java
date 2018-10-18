package _05_Internet_Address;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

// 현재 로컬에 연결된 모든 네트워크 인터페이스를 나열한다.
public class InterfaceLister {
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while(interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            System.out.println(networkInterface);
        }
    }
}
