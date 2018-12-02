package assembler;
import java.util.HashMap;

public class SymbolTable {
    
    HashMap<String, Integer> symbols;
    int nextAddress = 16;
    
    public SymbolTable() {
        symbols = new HashMap<>();
        this.addDefaults();
        System.out.println(symbols);
    }
    
    public void addEntry(String symbol, int address) {
        symbols.put(symbol, address);
    }
    
    public boolean contains(String symbol) {
        return symbols.containsKey(symbol);
    }
    
    public int getAddress(String symbol) {
        return symbols.get(symbol);
    }
    
    void addDefaults() {
        addEntry("SP", 0);
        addEntry("LCL", 1);
        addEntry("ARG", 2);
        addEntry("THIS", 3);
        addEntry("THAT", 4);
        
        for (int i = 0; i <= 15; i++) {
            addEntry(("R" + i), i);
        }
        
        addEntry("SCREEN", 16384);
        addEntry("KBD", 24576);
    }
    
    public int getNextAddress() {
        return this.nextAddress;
    }
    
    public void increment() {
        this.nextAddress++;
    }
}
