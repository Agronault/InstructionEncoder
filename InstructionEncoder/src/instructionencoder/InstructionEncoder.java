package instructionencoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author xande
 */
public class InstructionEncoder {

    public static void main(String[] args) throws IOException {
        Maps.init();
        try {
            BufferedReader read = new BufferedReader(new FileReader(args[0]));
            BufferedWriter write = new BufferedWriter(new FileWriter(args[0] + "_mds.txt"));
            write.append(InstructionEncoder.initials);
            for (String linha = read.readLine(); linha != null; linha = read.readLine()) {
                String[] parameters = linha.split(" ");
                Instruction inst = new Instruction(parameters[0], parameters[1], parameters[2], Maps.cicles.get(parameters[0].toLowerCase()));
                write.append("force -freeze sim:/proc/DIN 16'b" + inst.opcode() + " 0\n");
                if (parameters[0].equalsIgnoreCase("mvi")) {
                    write.append("run\n");
                    write.append("force -freeze sim:/proc/DIN 16'b" + inst.getImmediate() + " 0\n");
                    for (int i = 0; i < inst.getClock()-1; i++) {
                    write.append("run\n");
                    }
                }else{
                for (int i = 0; i < inst.getClock(); i++) {
                    write.append("run\n");
                }
                }
            }
            write.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo fornecido não encontrado");
        }

    }

    protected static String initials = new String(
            "add wave -position end  sim:/proc/Clock\n"
            + "add wave -position end  sim:/proc/DIN\n"
            + "add wave -position end  sim:/proc/Resetn\n"
            + "add wave -position end  sim:/proc/Run\n"
            + "add wave -position end  sim:/proc/ControlULA\n"
            + "add wave -position end  sim:/proc/I\n"
            + "add wave -position end  sim:/proc/Xreg\n"
            + "add wave -position end  sim:/proc/Yreg\n"
            + "add wave -position end  sim:/proc/Tstep_Q\n"
            + "add wave -position end  sim:/proc/R0\n"
            + "add wave -position end  sim:/proc/R1\n"
            + "add wave -position end  sim:/proc/R2\n"
            + "add wave -position end  sim:/proc/R3\n"
            + "add wave -position end  sim:/proc/R4\n"
            + "add wave -position end  sim:/proc/R5\n"
            + "add wave -position end  sim:/proc/R6\n"
            + "add wave -position end  sim:/proc/R7\n"
            + "add wave -position end  sim:/proc/A\n"
            + "add wave -position end  sim:/proc/G\n"
            + "add wave -position end  sim:/proc/Gout\n"
            + "force -freeze sim:/proc/Clock 1 0, 0 {50 ns} -r 100\n"
            + "force -freeze sim:/proc/Resetn 1'h0 0\n"
            + "force -freeze sim:/proc/Run 1'h0 0\n"
            + "run\n"
            + "force -freeze sim:/proc/Resetn 1'h1 0\n"
            + "force -freeze sim:/proc/Run 1'h1 0\n"
            + "run\n"
            + "run\n"
            + "run\n"
            + "run\n");
}
