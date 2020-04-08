package testcase;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeserializationVulnerableAction extends BaseAction {

    public DeserializationVulnerableAction(String[] args)
    {
        super(args);
    }

    @Override
    public void doRun(JSONObject flow, JSONArray dependencies, JSONArray astgraph, JSONObject manifest) {
        // security engineer or developer logic to compare the vulnerabilities here
        //super.exec();
    }

    public static void main(String[] args) {
        // getIntent Activity  onCreate Activity C:\\Users\\sshiv\\Downloads\\result.json G:\\nodejs\\android-security\\scripts\\data.json G:\\nodejs\\android-security\\public\\source\\920-Text-Editor-old\\src

        String[] arguments = new String[10];

        arguments[0] = "";
        arguments[1] = "getIntent";
        arguments[2] = "Activity";
        arguments[3] = "G:\\nodejs\\android-security\\public\\source\\text-editor\\apks\\deserialization_receiver.apk";
        arguments[4] = "onCreate";
        arguments[5] = "Activity";
        arguments[6] = "C:\\Users\\sshiv\\Downloads\\result.json";
        arguments[7] = "C:\\Users\\sshiv\\IdeaProjects\\android-security\\data.json";
        arguments[8] = "G:\\nodejs\\android-security\\public\\source\\text-editor\\deserialization_receiver\\app\\src";
        arguments[9] = "G:\\nodejs\\android-security\\public\\source\\text-editor\\deserialization_receiver\\app\\build.gradle";

        DeserializationVulnerableAction deserializationVulnerableAction = new DeserializationVulnerableAction(arguments);
        deserializationVulnerableAction.execute(arguments[3]);
    }
}
