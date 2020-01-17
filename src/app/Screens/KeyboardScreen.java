package app.Screens;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import app.Adb;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

//ro.product.cpu.abi
//ro.product.brand
//ro.product.model

public class KeyboardScreen {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JFrame frame;
    private String deviceID;
    private int keyCode;

    public KeyboardScreen() {}

    public void Init() {
        final int sizeWidth = 800;
        final int sizeHeight = 600;
        final int locationX = (screenSize.width - sizeWidth) / 2;
        final int locationY = (screenSize.height - sizeHeight) / 2;
        
        deviceID = Adb.GetAdbDevice();

        frame = new JFrame("Keyboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel deviceIDLabel = new JLabel("Device ID: " + deviceID);
        deviceIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(deviceIDLabel); 

        JLabel deviceManufacturerLabel = new JLabel("Device manufacturer: " + Adb.AdbDeviceManufacturer(deviceID));
        deviceManufacturerLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(deviceManufacturerLabel); 

        JLabel deviceBrandLabel = new JLabel("Device brand: " + Adb.AdbDeviceBrand(deviceID));
        deviceBrandLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(deviceBrandLabel); 

        JLabel deviceModelLabel = new JLabel("Device model: " + Adb.AdbDeviceModel(deviceID));
        deviceModelLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(deviceModelLabel); 

        JLabel deviceCpuLabel = new JLabel("Device CPU architecture: " + Adb.AdbDeviceCpu(deviceID));
        deviceCpuLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(deviceCpuLabel); 

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public class KeyboardClient {
        public KeyboardClient() {}

        public void StartClient() {
            KeyboardClientThread clientThread = new KeyboardClientThread();
            clientThread.start();
        }
        
        private class KeyboardClientThread extends Thread {
            @Override
            public void run() {
                super.run();
                try {
                    System.out.println("Keyboard client initializing");
                    Socket socket = new Socket("localhost", 7777);

                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                    System.out.println("Keyboard client initialized!");

                    while(!socket.isOutputShutdown()) {
                        if (keyCode != -1) {
                            
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}