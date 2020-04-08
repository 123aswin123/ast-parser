package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Scanner {

    public JSONObject callgraphData;
    public JSONArray astData;
    public JSONArray dependenciesData;

    public Scanner(String[] args)
    {
        String methodName = args[0+1];
        String className = args[1+1];
        String apkFile = args[2+1];
        String sourceMethod = args[3+1];
        String sourceClass = args[4+1];

        String saveFileName = args[5+1];
        String flowFileName = args[6+1];
        String sourceDir = args[7+1];

        String gradleFile = args[8+1];

        try {
            this.callgraphData = GenerateCallGraph.generateCallGraph(
                    methodName,
                    apkFile
            );

            String[] astArguments = new String[3];
            astArguments[0] =  flowFileName;
            astArguments[1] = sourceDir;
            astArguments[2] =  saveFileName;

            this.astData = GenerateAST.start(astArguments);
            this.dependenciesData = GenerateDependencies.execute(gradleFile);
            System.out.println(this.dependenciesData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {


    }
}
