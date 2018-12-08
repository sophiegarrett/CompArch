// Main class
package vmtranslator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;

public class VMTranslator {

    public static void main(String[] args) {
        
        String filepath = args[0];
        System.out.println(filepath);
        ArrayList<String> inputFiles = new ArrayList<>();
        String outputFile;
        if (filepath.contains(".vm")) {
            inputFiles.add(filepath);
            outputFile = filepath.split(".vm")[0];
        }
        else {
            File directory = new File(filepath);
            File[] allFiles = directory.listFiles();
            for (File file : allFiles) {
                String path = file.getPath();
                if (path.contains(".vm")) {
                    inputFiles.add(path);
                }
            }
            outputFile = filepath;
        }
        System.out.println(outputFile);
        
        CodeWriter c = new CodeWriter(outputFile);
        
        for (String input : inputFiles) {
            Path inputFile = Paths.get(input);
            Parser p = new Parser(inputFile);
            Boolean moreCommands = p.hasMoreCommands();
            c.setFileName(input.split(".vm")[0]);
            
            while (moreCommands == true) {
                p.readLine();
                p.parseLine();
                c.translate(p.getCommand());
                moreCommands = p.hasMoreCommands();
                p.advance();
            }
        }
        
        c.close();
    }
}
