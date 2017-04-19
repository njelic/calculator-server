package javaserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaServer {

    static int number1;
    static int number2;

    public static void main(String[] args) {

        while(true){
        try (DatagramSocket s = new DatagramSocket(3500);) {

            byte[] buff = new byte[128];
            DatagramPacket p = new DatagramPacket(buff, buff.length);
            s.receive(p);

            String req = new String(p.getData(), 0, p.getLength());
            String req1 = req.split(" ")[0];
            String req2 = req.split(" ")[1];

            number1 = Integer.parseInt(req1);
            number2 = Integer.parseInt(req2);
            
            int res = number1 + number2;

            String a = Integer.toString(res);

            buff = a.getBytes();
            p = new DatagramPacket(buff, buff.length, p.getAddress(), p.getPort());
            s.send(p);

        } catch (IOException ex) {
            Logger.getLogger(JavaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

}
