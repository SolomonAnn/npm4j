package cn.ayz.measurement;

import cn.ayz.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BandwidthMeasurement {
  private String ip;
  private int port;
  private int duration;
  private final static String serverCmd = "iperf -s";
  private final static String clientCmd = "iperf -c %s -p %d -t %d";

  public BandwidthMeasurement() {

  }

  public BandwidthMeasurement(String ip, int port, int duration) {
    this.ip = ip;
    this.port = port <= 0 ? Constant.DEFAULT_PORT : port;
    this.duration = duration <= 0 ? Constant.DEFAULT_DURATION : duration;
  }

  public boolean startServer() {
    Runtime runtime = Runtime.getRuntime();
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader;

    try {
      Process process = runtime.exec(serverCmd);
      if (process == null) {
        return false;
      }
      inputStreamReader = new InputStreamReader(process.getInputStream());
      bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains("Server listening on")) {
          return true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

    return false;
  }

  public String getBandwidth() {
    Runtime runtime = Runtime.getRuntime();
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader;
    String result = null;

    try {
      Process process = runtime.exec(String.format(clientCmd, ip, port, duration));
      if (process == null) {
        return Constant.RUNTIME_ERROR;
      }
      inputStreamReader = new InputStreamReader(process.getInputStream());
      bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      boolean success = false;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains("Client connecting to")) {
          success = true;
        }
        result = line;
      }
      if (success) {
        String[] items = result.split(" ");
        result = String.format("Bandwidth: %s %s",
            items[items.length - 2], items[items.length - 1]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

    return result;
  }
}
