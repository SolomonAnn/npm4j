package cn.ayz.measurement;

import cn.ayz.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class DelayNLossMeasurement {
  private int count;
  private int size;
  private final static String cmd = "echo %s | sudo -S mtr -r -c %d -s %d 202.108.22.5";

  public DelayNLossMeasurement(int count, int size) {
    this.count = count <= 0 ? Constant.DEFAULT_DELAYNLOSS_COUNT : count;
    this.size = size <= 0 ? Constant.DEFAULT_PACKET_SIZE : size;
  }

  public String getDelayNLoss() {
    Runtime runtime = Runtime.getRuntime();
    BufferedReader bufferedReader = null;
    InputStreamReader inputStreamReader;
    String result = null;

    try {
      String[] cmds = {"/bin/zsh", "-c", String.format(cmd, Constant.PASSWORD, count, size)};
      Process process = runtime.exec(cmds);
      if (process == null) {
        return Constant.RUNTIME_ERROR;
      }
      inputStreamReader = new InputStreamReader(process.getInputStream());
      bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      List<String> records = new LinkedList<>();
      while ((line = bufferedReader.readLine()) != null) {
        records.add(line);
      }
      result = parseResults(records);
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

  private String parseResults(List<String> records) {
    String result = "<html><body>Average Delay: %.2fms<br>Average Loss: %.2f%%";
    double delay = 0;
    double loss = 0;
    List<String> items;
    int hostCount = 0;

    for (int i = 2; i < records.size(); i++) {
      items = filterItems(records.get(i).split(" "));
      if ("???".equals(items.get(1))) {
        continue;
      }
      hostCount += 1;
      loss += Double.parseDouble(items.get(2).substring(0, items.get(2).length() - 1));
      delay += Double.parseDouble(items.get(4));
    }

    return String.format(result, delay / hostCount, loss / hostCount);
  }

  private List<String> filterItems(String[] items) {
    List<String> newItems = new LinkedList<>();

    for (String item : items) {
      if (item.length() != 0) {
        newItems.add(item);
      }
    }

    return newItems;
  }
}
