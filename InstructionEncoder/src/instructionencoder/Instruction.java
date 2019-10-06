package instructionencoder;

/**
 *
 * @author xande
 */
public class Instruction {

    private final String call;
    private final String op1;
    private final String op2;

    public String getCall() {
        return call;
    }

    public int getClock() {
        return clock;
    }
    private int clock;

    public Instruction(String call, String op1, String op2, int clock) {
        this.call = call;
        this.op1 = op1;
        this.op2 = op2;
        this.clock = clock;
    }

    public String opcode() {
        String call = "0000";
        String op1 = "000";
        String op2 = "000";
        if (this.call.equalsIgnoreCase("mv")) {
            call="0000";
        }else if (this.call.equalsIgnoreCase("mvi")) {
            call="0001";
        }else if (this.call.equalsIgnoreCase("add")) {
            call="0010";
        }else if (this.call.equalsIgnoreCase("sub")) {
            call="0011";
        }else if (this.call.equalsIgnoreCase("and")) {
            call="0100";
        }else if (this.call.equalsIgnoreCase("slt")) {
            call="0101";
        }else if (this.call.equalsIgnoreCase("sll")) {
            call="0110";
        }else if (this.call.equalsIgnoreCase("srl")) {
            call="0111";
        }else if (this.call.equalsIgnoreCase("ld")) {
            call="1000";
        }else if (this.call.equalsIgnoreCase("st")) {
            call="1001";
        }else if (this.call.equalsIgnoreCase("mvnz")) {
            call="1010";
        }
        
        
        op1 = Integer.toBinaryString(Integer.valueOf(this.op1.substring(1)));
        op1 = String.format("%3s", op1.replace(' ', '0'));
        op1 = op1.replace(' ', '0');
        
        op2 = Integer.toBinaryString(Integer.valueOf(this.op2.substring(1)));
        op2 = String.format("%3s", op2.replace(' ', '0'));
        op2 = op2.replace(' ', '0');
        
        
        return "000000"+op2+op1+call; 
    }
    
    public String getImmediate(){
        String resp = Integer.toBinaryString(Integer.valueOf(this.op2.substring(1)));
        String formated = String.format("%16s", resp.replace(' ', '0'));
        return formated.replace(' ', '0');
    }

}
