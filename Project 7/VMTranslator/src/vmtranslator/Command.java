package vmtranslator;

public class Command {
    CommandType type;
    String arg1;
    int arg2;
    
    public Command() {
        
    }
    
    public void setType(CommandType c) {
        this.type = c;
    }
    
    public CommandType getType() {
        return type;
    }
    
    public void setArg1(String s) {
        this.arg1 = s;
    }
    
    public String getArg1() {
        return this.arg1;
    }
    
    public void setArg2(int x) {
        this.arg2 = x;
    }
    
    public int getArg2() {
        return this.arg2;
    }
}
