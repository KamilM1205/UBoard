package app.Screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.Adb;

public class InitScreen {

    public InitScreen() {
    }

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JTextArea loger;
    private JFrame frame;
    private JButton okButton;

    public class RestartActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent arg0) {
            println("Killing adb server");
            Adb.AdbStop();
            loger.setText("");
            frame.dispose();
            Init();
        }
    }

    public class OkActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            frame.dispose();
            KeyboardScreen keyboardScreen = new KeyboardScreen();
            keyboardScreen.Init();
        }
    }

    private void createInitFrame() {
        final int sizeWidth = 300;
        final int sizeHeight = 300;
        final int locationX = (screenSize.width - sizeWidth) / 2;
        final int locationY = (screenSize.height - sizeHeight) / 2;

        frame = new JFrame();
        frame.setTitle("UBoard version 0.1");
        frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);

        final JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());

        final JLabel title = new JLabel("UBoard");
        title.setSize(150, 60);
        panel.add(title, BorderLayout.NORTH);

        loger = new JTextArea();
        loger.setEditable(false);
        panel.add(loger);

        panel.add(new JScrollPane(loger));
        panel.add(new JScrollPane(loger));

        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        panel.add(panel2, BorderLayout.SOUTH);

        final JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new RestartActionListener());
        panel2.add(restartButton);

        okButton = new JButton("Ok");
        okButton.setEnabled(false);
        okButton.addActionListener(new OkActionListener());
        panel2.add(okButton);

        frame.setVisible(true);
    }

    public void Init() {
        createInitFrame();

        // Start adb
        println("Starting adb server");
        Adb.AdbStart();
        println("Checking devices");
        final boolean isDevices = Adb.CheckAdbDevice();
        if (!isDevices) {
            println("Please connect your android device with usb debugging enabled.");
            return;
        }
        println("Forwarding ports");
        Adb.AdbForward("tcp:7777", "tcp:7777");
        
        //Ending init
        okButton.setEnabled(true);
    }

    private void println(final String text) {
        loger.append(text + "\n");
    }
}