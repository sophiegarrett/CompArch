package assembler;

public class Code {
    
    public Code() {
        
    }
    
    public void translate(Command c) {
        
        String s;
        
        if (c.getType() == CommandType.A_COMMAND) {
            s = c.getSymbol();
        }
        
        else if (c.getType() == CommandType.C_COMMAND) {
            String dest, comp, jump;
            dest = dest(c.getDest());
            comp = comp(c.getComp());
            jump = jump(c.getJump());
            s = dest + comp + jump;
        }
        
        else if (c.getType() == CommandType.L_COMMAND) {
            s = c.getSymbol();
        }
        
        else {
            s = null;
        }
        
        if (s != null) {
            System.out.println(s);
        }
    }
    
    public String dest(String d) {
        if (d != null) {
            return d;
        }
        else {
            return "000";
        }
    }
    
    public String comp(String c) {
        if (c != null) {
            return c;
        }
        else {
            return "0000000";
        }
    }
    
    public String jump(String j) {
        if (j != null) {
            return j;
        }
        else {
            return "000";
        }
    }
}
