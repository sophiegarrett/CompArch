package assembler;

public class Command {
    
    CommandType type;
    String symbol, dest, comp, jump;
    
    public Command() {
        
    }
    
    public void setType(CommandType c) {
        this.type = c;
    }
    
    public CommandType getType() {
        return type;
    }
    
    public void setSymbol(String s) {
        this.symbol = s;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setDest(String d) {
        this.dest = d;
    }
    
    public String getDest() {
        return dest;
    }
    
    public void setComp(String c) {
        this.comp = c;
    }
    
    public String getComp() {
        return comp;
    }
    
    public void setJump(String j) {
        this.jump = j;
    }
    
    public String getJump() {
        return jump;
    }
}
