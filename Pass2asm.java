import java.io.*;
import java.util.*;

public class Pass2asm {
    private static Map<String, String> symbolTable = new HashMap<>();
    private static Map<String, String> opcodeTable = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Give Intermediate as well as output file !");
            return;
        }

        String intermediateFile = args[0];
        String outputFile = args[1];

        // Initialize opcode table
        initializeOpcodeTable();

        try (BufferedReader reader = new BufferedReader(new FileReader(intermediateFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String address = parts[0];
                String label = parts[1];
                String opcode = parts[2];
                String operand = parts.length > 3 ? parts[3] : "";

                String objectCode = generateObjectCode(opcode, operand);
                writer.write(address + " " + objectCode);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeOpcodeTable() {
        opcodeTable.put("LDA", "00");
        opcodeTable.put("STA", "0C");
        opcodeTable.put("LDX", "04");
        opcodeTable.put("STX", "10");
       
    }

    private static String generateObjectCode(String opcode, String operand) {
        String opcodeHex = opcodeTable.get(opcode);
        String operandAddress = symbolTable.getOrDefault(operand, "0000");
        return opcodeHex + operandAddress;
    }
}