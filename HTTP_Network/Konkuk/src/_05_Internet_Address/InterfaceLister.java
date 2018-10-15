package _05_Internet_Address;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InterfaceLister {
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while(interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            System.out.println(networkInterface);
        }
    }
}
