package testcase;

import com.sun.tools.jdeprscan.scan.Scan;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class BaseAction {

    JSONObject flow;
    JSONArray dependencies;
    JSONArray astgraph;
    JSONObject manifest;

    public BaseAction(String[] args)
    {
        Scanner scanner = new Scanner(args);
        this.flow = scanner.callgraphData;
        this.astgraph = scanner.astData;
        this.dependencies = scanner.dependenciesData;
    }

    public void execute(String apkFile)
    {
        this.installAPK(apkFile);
        this.doRun(flow, dependencies, astgraph, manifest);
    }

    public void exec(String deviceId,String subcommand, String command) throws IOException {
        Process process = null;
        String commandString;
        if(deviceId != null) {
            commandString = String.format("%s", "adb -s " + deviceId + " "+subcommand+" " + command);
        }else
            commandString = String.format("%s", "adb " + subcommand + " " + command);
            System.out.print("Command is "+commandString+"\n");
        try
        {
            process = Runtime.getRuntime().exec(commandString);
        }
        catch (IOException e) {
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.print(line+"\n");
        }
    }

    public void reportResult(boolean isSuccessful)
    {

    }

    public abstract void doRun(JSONObject flow, JSONArray dependencies, JSONArray astgraph, JSONObject manifest);

    public void installAPK(String apkfile)
    {
        try {
            this.exec(null, "install", apkfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
