package cn.ayz.gui;

import cn.ayz.constant.Constant;
import cn.ayz.measurement.BandwidthMeasurement;
import cn.ayz.measurement.ConnectivityMeasurement;
import cn.ayz.measurement.DelayNLossMeasurement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class NPMWindow {
  private JFrame jFrame;

  public NPMWindow() {

  }

  public void start() {
    initWindow();
    setConnectivityComponent();
    setDelayNLossComponent();
    setBandwidthComponent();
    showWindow();
  }

  private void initWindow() {
    jFrame = new JFrame("npm4j");
    jFrame.setBounds(50, 50, 1000, 800);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setResizable(false);
    jFrame.setLayout(null);
  }

  private void setConnectivityComponent() {
    JLabel connectivityLabel = new JLabel("Connectivity", JLabel.CENTER);
    JLabel countLabel = new JLabel("Count", JLabel.CENTER);
    JLabel timeoutLabel = new JLabel("Timeout (ms)", JLabel.CENTER);
    JTextField countText = new JTextField();
    JTextField timeoutText = new JTextField();
    JButton connectivityButton = new JButton("Run");

    connectivityLabel.setBounds(50,  100, 200, 50);
    countLabel.setBounds(250, 50, 200, 50);
    timeoutLabel.setBounds(500, 50, 200, 50);
    countText.setBounds(250, 100, 200, 50);
    timeoutText.setBounds(500, 100, 200, 50);
    connectivityButton.setBounds(750, 100, 200, 50);

    connectivityButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame popupWindow = new JFrame();

        try {
          int count = Integer.parseInt(
              countText.getText().length() == 0 ? "0" : countText.getText());
          int timeout = Integer.parseInt(
              timeoutText.getText().length() == 0 ? "0" : timeoutText.getText());
          ConnectivityMeasurement connectivityMeasurement =
              new ConnectivityMeasurement(count, timeout);
          if (connectivityMeasurement.isConnective()) {
            popupWindow.add(new JLabel(Constant.CONNECTIVITY_SUCCESS, JLabel.CENTER));
          } else {
            popupWindow.add(new JLabel(Constant.CONNECTIVITY_FAILURE, JLabel.CENTER));
          }
        } catch (NumberFormatException e1) {
          popupWindow.add(new JLabel(Constant.INVALID_ARGUMENT, JLabel.CENTER));
        }

        popupWindow.setBounds(350, 350, 400, 100);
        popupWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
      }
    });

    jFrame.getContentPane().add(connectivityLabel);
    jFrame.getContentPane().add(countLabel);
    jFrame.getContentPane().add(timeoutLabel);
    jFrame.getContentPane().add(countText);
    jFrame.getContentPane().add(timeoutText);
    jFrame.getContentPane().add(connectivityButton);
  }

  private void setDelayNLossComponent() {
    JLabel delayNLossLabel = new JLabel("Delay & Loss", JLabel.CENTER);
    JLabel countLabel = new JLabel("Count", JLabel.CENTER);
    JLabel sizeLabel = new JLabel("Packet Size (Bytes)", JLabel.CENTER);
    JTextField countText = new JTextField();
    JTextField sizeText = new JTextField();
    JButton delayNLossButton = new JButton("Run");

    delayNLossLabel.setBounds(50,  300, 200, 50);
    countLabel.setBounds(250, 250, 200, 50);
    sizeLabel.setBounds(500, 250, 200, 50);
    countText.setBounds(250, 300, 200, 50);
    sizeText.setBounds(500, 300, 200, 50);
    delayNLossButton.setBounds(750, 300, 200, 50);

    delayNLossButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame popupWindow = new JFrame();

        try {
          int count = Integer.parseInt(
              countText.getText().length() == 0 ? "0" : countText.getText());
          int size = Integer.parseInt(
              sizeText.getText().length() == 0 ? "0" : sizeText.getText());
          DelayNLossMeasurement delayNLossMeasurement =
              new DelayNLossMeasurement(count, size);
          String result = delayNLossMeasurement.getDelayNLoss();
          if (result != null) {
            popupWindow.add(new JLabel(result, JLabel.CENTER));
          } else {
            popupWindow.add(new JLabel(Constant.RUNTIME_ERROR, JLabel.CENTER));
          }
        } catch (NumberFormatException e1) {
          popupWindow.add(new JLabel(Constant.INVALID_ARGUMENT, JLabel.CENTER));
        }

        popupWindow.setBounds(350, 350, 400, 100);
        popupWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
      }
    });

    jFrame.getContentPane().add(delayNLossLabel);
    jFrame.getContentPane().add(countLabel);
    jFrame.getContentPane().add(sizeLabel);
    jFrame.getContentPane().add(countText);
    jFrame.getContentPane().add(sizeText);
    jFrame.getContentPane().add(delayNLossButton);
  }

  private void setBandwidthComponent() {
    JLabel bandwidthLabel = new JLabel("Bandwidth", JLabel.CENTER);
    JLabel serverLabel = new JLabel("Server", JLabel.CENTER);
    JLabel clientLabel = new JLabel("Client", JLabel.CENTER);
    JLabel ipLabel = new JLabel("IP", JLabel.CENTER);
    JLabel portLabel = new JLabel("Port", JLabel.CENTER);
    JLabel durationLabel = new JLabel("Time", JLabel.CENTER);
    JTextField ipText = new JTextField();
    JTextField portText = new JTextField();
    JTextField durationText = new JTextField();
    JButton serverButton = new JButton("Run");
    JButton clientButton = new JButton("Run");

    bandwidthLabel.setBounds(50,  563, 200, 50);
    serverLabel.setBounds(250, 450, 200, 50);
    clientLabel.setBounds(250, 550, 200, 50);
    ipLabel.setBounds(250, 625, 200, 50);
    portLabel.setBounds(500, 625, 200, 50);
    durationLabel.setBounds(750, 625, 200, 50);
    ipText.setBounds(250, 675, 200, 50);
    portText.setBounds(500, 675, 200, 50);
    durationText.setBounds(750, 675, 200, 50);
    serverButton.setBounds(500, 450, 200, 50);
    clientButton.setBounds(500, 550, 200, 50);

    serverButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame popupWindow = new JFrame();
        BandwidthMeasurement bandwidthMeasurement = new BandwidthMeasurement();

        if (bandwidthMeasurement.startServer()) {
          popupWindow.add(new JLabel(Constant.SERVER_SUCCESS, JLabel.CENTER));
        } else {
          popupWindow.add(new JLabel(Constant.SERVER_FAILURE, JLabel.CENTER));
        }

        popupWindow.setBounds(350, 350, 400, 100);
        popupWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
      }
    });

    clientButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame popupWindow = new JFrame();

        try {
          String ip = Pattern.matches(
              "((1\\d{2}|25[0-5]|2[0-4]\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)",
              ipText.getText()) ? ipText.getText() : null;
          int port = Integer.parseInt(
              portText.getText().length() == 0 ? "0" : portText.getText());
          int duration = Integer.parseInt(
              durationText.getText().length() == 0 ? "0" : durationText.getText());
          if (ip == null) {
            popupWindow.add(new JLabel(Constant.INVALID_ARGUMENT, JLabel.CENTER));
          } else {
            BandwidthMeasurement bandwidthMeasurement =
                new BandwidthMeasurement(ip, port, duration);
            String result = bandwidthMeasurement.getBandwidth();
            if (result != null) {
              popupWindow.add(new JLabel(result, JLabel.CENTER));
            } else {
              popupWindow.add(new JLabel(Constant.CLIENT_FAILURE, JLabel.CENTER));
            }
          }
        } catch (NumberFormatException e1) {
          popupWindow.add(new JLabel(Constant.INVALID_ARGUMENT, JLabel.CENTER));
        }

        popupWindow.setBounds(350, 350, 400, 100);
        popupWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
      }
    });

    jFrame.getContentPane().add(bandwidthLabel);
    jFrame.getContentPane().add(serverLabel);
    jFrame.getContentPane().add(clientLabel);
    jFrame.getContentPane().add(ipLabel);
    jFrame.getContentPane().add(portLabel);
    jFrame.getContentPane().add(durationLabel);
    jFrame.getContentPane().add(ipText);
    jFrame.getContentPane().add(portText);
    jFrame.getContentPane().add(durationText);
    jFrame.getContentPane().add(serverButton);
    jFrame.getContentPane().add(clientButton);
  }

  private void showWindow() {
    jFrame.setVisible(true);
  }
}
