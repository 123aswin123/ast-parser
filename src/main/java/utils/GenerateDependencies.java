package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class GenerateDependencies
{
    public static JSONArray execute(String gradleFile) throws IOException
    {
        Process process = Runtime.getRuntime().exec("node.exe G:\\nodejs\\android-security\\gradle2js.js "+gradleFile);
        File file = new File("G:\\nodejs\\android-security\\gradle.json");

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

        return new JSONArray(data);
    }
}
