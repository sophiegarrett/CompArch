package assembler;

public class Code {
    
    public Code() {
        
    }
    
    public String translate(Command c) {
        
        String s;
        
        if (c.getType() == CommandType.A_COMMAND) {
            int num = Integer.parseInt(c.getSymbol());
            String str = String.format("%15s", Integer.toBinaryString(num)).replace(' ', '0');
            s = "0" + str;
        }
        
        else if (c.getType() == CommandType.C_COMMAND) {
            String dest, comp, jump;
            dest = dest(c.getDest());
            comp = comp(c.getComp());
            jump = jump(c.getJump());
            s = "111" + comp + dest + jump;
        }
        
        else if (c.getType() == CommandType.L_COMMAND) {
            s = c.getSymbol();
        }
        
        else {
            s = null;
        }
        
        if (s != null) {
            System.out.println(s);
            return s;
        }
        
        return "";
    }
    
    public String dest(String d) {
        if (d != null) {
            switch(d) {
                case "M": return "001";
                case "D": return "010";
                case "MD": return "011";
                case "A": return "100";
                case "AM": return "101";
                case "AD": return "110";
                case "AMD": return "111";
            }
            return "error"; // There should be no case in which d is not null but is also none of the above statements.
        }
        else {
            return "000";
        }
    }
    
    public String comp(String c) {
        if (c != null) {
            switch(c) {
                case "0": return "0101010";
                case "1": return "0111111";
                case "-1": return "0111010";
                case "D": return "0001100";
                case "!D": return "0001101";
                case "-D": return "0001111";
                case "D+1": return "0011111";
                case "D-1": return "0001110";
            }
            
            String a = "0";
            if (c.contains("M")) {
                a = "1";
            }
            
            switch(c) {
                case "A": case "M": return a + "110000";
                case "!A": case "!M": return a + "110001";
                case "-A": case "-M": return a + "110011";
                case "A+1": case "M+1": return a + "110111";
                case "A-1": case "M-1": return a + "110010";
                case "D+A": case "D+M": return a + "000010";
                case "D-A": case "D-M": return a + "010011";
                case "A-D": case "M-D": return a + "000111";
                case "D&A": case "D&M": return a + "000000";
                case "D|A": case "D|M": return a + "010101";
            }
            return "error"; // There should be no case in which c is not null but is also none of the above statements.
        }
        else {
            return "0000000";
        }
    }
    
    public String jump(String j) {
        if (j != null) {
            switch(j) {
                case "JGT": return "001";
                case "JEQ": return "010";
                case "JGE": return "011";
                case "JLT": return "100";
                case "JNE": return "101";
                case "JLE": return "110";
                case "JMP": return "111";
            }
            return "error"; // There should be no case in which j is not null but is also none of the above statements.
        }
        else {
            return "000";
        }
    }
}
