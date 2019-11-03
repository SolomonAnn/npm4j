package cn.ayz.measurement;

import cn.ayz.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectivityMeasurement {
  private int count;
  private int timeOut;
  private final static String cmd = "ping -c %d -W %d 202.108.22.5";

  public ConnectivityMeasurement(int count, int timeOut) {
    this.count = count <= 0 ? Constant.DEFAULT_CONNECTIVITY_COUNT : count;
    this.timeOut = timeOut <= 0 ? Constant.DEFAULT_TIMEOUT : timeOut;
  }

  public boolean isConnective() {
    Runtime runtime = Runtime.getRuntime();
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader;
    int successCount = 0;

    try {
      Process process = runtime.exec(String.format(cmd, count, timeOut));
      if (process == null) {
        return false;
      }
      inputStreamReader = new InputStreamReader(process.getInputStream());
      bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        successCount += line.toLowerCase().contains("ttl") ? 1 : 0;
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

    return successCount == count;
  }
}
