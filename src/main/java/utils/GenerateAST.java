package utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GenerateAST {
    private static String line;

    public static JSONArray start(String[] args) {
        System.out.println(Arrays.toString(args));
        String saveFileName = args[2];
        String filename = args[0];
        File file = new File(filename);

        Scanner myReader = null;
        String data = "";

        ArrayList<String> parsedClassName = new ArrayList<>();
        ArrayList<String> parsedMethodName = new ArrayList<>();


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


        if(!data.equals(""))
        {
            JSONObject nodes = new JSONObject(data);
            JSONArray flows = nodes.getJSONArray("flows");
            for (int i=0; i < flows.length(); i++)
            {
                JSONObject flowsObj = flows.getJSONObject(i);
                JSONArray jsonArray = flowsObj.getJSONArray("nodes");

                for (int j=0; j < jsonArray.length(); j++)
                {
                  //  System.out.println(jsonArray.getString(j));
                    String nodepath = jsonArray.getString(j);
                    String[] className = nodepath.split(";");
                    String[] classname = className[0].split("/");
                    System.out.println("ClassName======>"+classname[classname.length-1]);
                    parsedClassName.add(classname[classname.length-1]);

                    String parsedMethod = className[1].substring(2).split("\\(")[0];
                    System.out.println(parsedMethod);
                    parsedMethodName.add(parsedMethod);
                }
            }
            File projectDir = new File(args[1]);
            ArrayList<SourceMetaInfo> sourceMetaInfos = listMethodCalls(projectDir, parsedMethodName);

            JSONArray sourceInfo = new JSONArray();

            for(int i=0; i < sourceMetaInfos.size(); i++)
            {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("path", sourceMetaInfos.get(i).filePath);
                jsonObject.put("method",sourceMetaInfos.get(i).methodName);
                jsonObject.put("linenumber",sourceMetaInfos.get(i).LineNumber);

                sourceInfo.put(jsonObject);
            }

            try {
                System.out.println(sourceInfo.toString());
                String str = sourceInfo.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileName));
                writer.write(str);
                writer.flush();
                return sourceInfo;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {

        }
        return null;
    }


    public static ArrayList<SourceMetaInfo> listMethodCalls(File projectDir, ArrayList<String> parsedMethodName) {

        ArrayList<SourceMetaInfo> lineNumberMethod = new ArrayList<SourceMetaInfo>();

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        if(parsedMethodName.contains(n.getNameAsString().split("\\(")[0]))
                        {
                            System.out.println("Found--------------------");
                            System.out.println(" [L " + n.getBegin().get().line + "] " + n);
                            SourceMetaInfo sourceMetaInfo = new SourceMetaInfo();
                            sourceMetaInfo.setLineNumber(n.getBegin().get().line+"");
                            sourceMetaInfo.setMethodName(n.getNameAsString().split("\\(")[0]);
                            sourceMetaInfo.setFilePath(path);

                            lineNumberMethod.add(sourceMetaInfo);
                        }
                       //
                    }
                }.visit(StaticJavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).explore(projectDir);

        return lineNumberMethod;
    }

     static class SourceMetaInfo {
        String methodName;
        String LineNumber;
        String filePath;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getLineNumber() {
            return LineNumber;
        }

        public void setLineNumber(String lineNumber) {
            LineNumber = lineNumber;
        }
    }
}
