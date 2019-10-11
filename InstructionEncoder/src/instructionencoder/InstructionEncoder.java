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
            BufferedWriter write = new BufferedWriter(new FileWriter(args[0].substring(0, args[0].length()-4) + "_mds.txt"));
            BufferedWriter writeBin = new BufferedWriter(new FileWriter(args[0].substring(0, args[0].length()-4) + "_bin.txt"));
            initialize(write);
            for (String linha = read.readLine(); linha != null; linha = read.readLine()) {
                String[] parameters = linha.split(" ");
                Instruction inst = new Instruction(parameters[0], parameters[1], parameters[2], Maps.cicles.get(parameters[0].toLowerCase()));
                write.append("force -freeze sim:/proc/DIN 16'b" + inst.opcode() + " 0");
                writeBin.append(inst.opcode());
                write.newLine();
                writeBin.newLine();
                if (parameters[0].equalsIgnoreCase("mvi")) {
                    write.append("run");
                    write.newLine();
                    write.append("force -freeze sim:/proc/DIN 16'b" + inst.getImmediate() + " 0");
                    writeBin.append(inst.getImmediate());
                    write.newLine();
                    writeBin.newLine();
                    for (int i = 0; i < inst.getClock() - 1; i++) {
                        write.append("run");
                        write.newLine();
                    }
                } else {
                    for (int i = 0; i < inst.getClock(); i++) {
                        write.append("run");
                        write.newLine();
                    }
                }
            }
            write.close();
            writeBin.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo fornecido não encontrado");
        }

    }

    protected static void initialize(BufferedWriter write) throws IOException {
        for (String name : initials) {
            write.append(name);
            write.newLine();
        }
    }

    protected static String[] initials = new String[]{
        "add wave -position end  sim:/proc/Clock",
        "add wave -position end  sim:/proc/DIN",
        "add wave -position end  sim:/proc/Resetn",
        "add wave -position end  sim:/proc/Run",
        "add wave -position end  sim:/proc/ControlULA",
        "add wave -position end  sim:/proc/I",
        "add wave -position end  sim:/proc/Xreg",
        "add wave -position end  sim:/proc/Yreg",
        "add wave -position end  sim:/proc/Tstep_Q",
        "add wave -position end  sim:/proc/R0",
        "add wave -position end  sim:/proc/R1",
        "add wave -position end  sim:/proc/R2",
        "add wave -position end  sim:/proc/R3",
        "add wave -position end  sim:/proc/R4",
        "add wave -position end  sim:/proc/R5",
        "add wave -position end  sim:/proc/R6",
        "add wave -position end  sim:/proc/R7",
        "add wave -position end  sim:/proc/A",
        "add wave -position end  sim:/proc/G",
        "add wave -position end  sim:/proc/Gout",
        "force -freeze sim:/proc/Clock 1 0, 0 {50 ns} -r 100",
        "force -freeze sim:/proc/Resetn 1'h0 0",
        "force -freeze sim:/proc/Run 1'h0 0",
        "run",
        "force -freeze sim:/proc/Resetn 1'h1 0",
        "force -freeze sim:/proc/Run 1'h1 0",
        "run",
        "run",
        "run",
        "run"};
}
