// Main class
package assembler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Assembler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Path inputFile = Paths.get("..\\Test Files\\add\\Add.asm");
        Path outputFile;
        
        Parser p = new Parser(inputFile);
        Code c = new Code();
        SymbolTable s = new SymbolTable();
        
    }
}
