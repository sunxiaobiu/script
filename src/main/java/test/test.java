package test;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class test {


    public static void main(String[] args) {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();

            System.out.println("The mac Address of this machine is :"
                    + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("The mac address is : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : ""));
            }

            System.out.println(sb.toString());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
