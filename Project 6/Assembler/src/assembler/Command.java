package assembler;

public class Command {
    
    CommandType type;
    String symbol, dest, comp, jump;
    
    public Command() {
        
    }
    
    public CommandType commandType() {
        return type;
    }
    
    public String symbol() {
        return symbol;
    }
    
    public String dest() {
        return dest;
    }
    
    public String comp() {
        return comp;
    }
    
    public String jump() {
        return jump;
    }
}
