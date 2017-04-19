package javacalculator;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaCalculator {
    
    public static TextField tf1;
    public static TextField tf2;
    public static Label rezultat;
    public static Label greska;
    public static String praz = "                                                                           ";
    
    public static void main(String[] args) {
        GridBagLayout gb = new GridBagLayout();
        Frame f = new Frame("Serverski Kalkulator");
        f.setLayout(gb);
        GridBagConstraints gbc = new GridBagConstraints();
        
        Label l1 = new Label("Unesite prvi broj:");
        tf1 = new TextField();
        Label l2 = new Label("Unesite drugi broj:");
        tf2 = new TextField();
        Label l3 = new Label("+");
        Button b = new Button("Izracunaj!");
        Label l4 = new Label("Rezultat je:");
        Label razmak = new Label(" ");
        Label razmak2 = new Label(" ");
        rezultat = new Label("0");
        greska = new Label(praz);
        gbc.gridx = 0;
        gbc.gridy = 0;
        f.add(l1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        f.add(tf1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        f.add(l3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        f.add(l2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        f.add(tf2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        f.add(razmak, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        f.add(b, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        f.add(razmak2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        f.add(l4, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        f.add(rezultat, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        f.add(greska, gbc);
        
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        tf1.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                rezultat.setText("0");
            }
        });
        
        tf2.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                rezultat.setText("0");
            }
        });
        
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*                boolean b = true;
                try {
                    
                    InetSocketAddress sa = new InetSocketAddress("localhost", 3500);
                    DatagramSocket ss = new DatagramSocket();
                    byte[] poru = new byte[128];
                    poru = "5 2".getBytes();
                    DtagramPacket p = new DatagramPacket(poru, poru.length, sa);
                    ss.send(p);
                } catch (Exception y) {
                    b = false;
                    greska.setText("Server je van funkcije!");
                }
                if (b) {
                 */ try (DatagramSocket cs = new DatagramSocket();) {
                    
                    try {
                        byte[] poruka = new byte[128];
                        String n = tf1.getText() + " " + tf2.getText();
                        int number1 = Integer.parseInt(tf1.getText());
                        int number2 = Integer.parseInt(tf2.getText());
                        
                        poruka = n.getBytes();
                        
                        InetSocketAddress ep = new InetSocketAddress("localhost", 3500);
                        DatagramPacket p = new DatagramPacket(poruka, poruka.length, ep);
                        cs.send(p);
                        
                        poruka = new byte[128];
                        p = new DatagramPacket(poruka, poruka.length, ep);
                        try {
                            cs.setSoTimeout(3);
                            cs.receive(p);
                            
                            String pr = new String(p.getData(), 0, p.getLength());
                            rezultat.setText(pr);
                            greska.setText(praz);
                        } catch (SocketTimeoutException z) {
                            greska.setText("Nema odgovora od servera!");
                        }
                        
                    } catch (NumberFormatException x) {
                        String v = "Dozvoljeno je uneti samo brojeve";
                        greska.setText(v);
                    }
                    
                } catch (IOException ex) {
                    greska.setText("Nema odgovora od servera!");
                }
                //}

            }
        });
        
        f.setSize(400, 250);
        f.setVisible(true);
    }
    
}
