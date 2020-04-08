package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class GenerateCallGraph {
    public static JSONObject generateCallGraph(String methodName, String apkfile) throws IOException {
        // python.exe scripts\\app.py --methodname getIntent --apkfile C:\\Users\\sshiv\\Downloads\\editor.apk

        Process process = Runtime.getRuntime().exec("python.exe G:\\nodejs\\android-security\\scripts\\app.py --methodname "+methodName+" --apkfile "+apkfile);

        File file = new File("G:\\nodejs\\android-security\\data.json");

        String line = "";
        String data = "";
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                data = data + line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return new JSONObject(data);
    }
}
