import java.io.*;

public class Pass1asm {

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);

        final int max = 100;
        String line = null;
        // line_count to iterate over the array (line_count) times, lc will store value
        // besides START (e.g. 100)
        int line_count = 0, lc = 0, symbolTableLine = 0, opcodeTableLine = 0, literalTableLine = 0, poolTableLine = 0;

        String opcodeTable[][] = new String[max][3]; // mnemonics, class, info
        String symbolTable[][] = new String[max][3]; // symbol, address, length
        String literalTable[][] = new String[max][2]; // literal, address
        int poolTable[] = new int[max]; // literal

        // ---- Loops begin ----

        while ((line = br.readLine()) != null) {
            // System.out.println(line); //shall print the input code in line
            String tokens[] = line.split("\t");

            // printing input code
            if (line_count == 0) {
                lc = Integer.parseInt(tokens[1]);

                for (int i = 0; i < tokens.length; i++)
                    System.out.print(tokens[i] + "\t");

                System.out.println("");
                line_count++;
            }

            else {
                for (int i = 0; i < tokens.length; i++)
                    System.out.print(tokens[i] + "\t");

                System.out.println("");

                // Input display complete !

                // Logic to insert data into table using token array.

                if (tokens[0].charAt(0) == '=') // if first character is =,
                {
                    literalTable[literalTableLine][0] = tokens[0];
                    literalTable[literalTableLine][1] = Integer.toString(lc);
                    literalTableLine++;
                }

                // if length is 3 , as everything except label ends in 2 elements for tokens. So
                // add in SYMBOL TABLE
                else if (tokens.length == 3 && tokens[0] != null) {
                    symbolTable[symbolTableLine][0] = tokens[0];
                    symbolTable[symbolTableLine][1] = Integer.toString(lc);
                    symbolTable[symbolTableLine][2] = "1";
                    symbolTableLine++;
                }

                // for opcode table,
                if ((tokens.length == 2 || tokens.length == 1) && !(tokens[0].charAt(0) == '=')) {
                    opcodeTable[opcodeTableLine][0] = tokens[0];

                    if (tokens[0].equalsIgnoreCase("START") || tokens[0].equalsIgnoreCase("END")
                            || tokens[0].equalsIgnoreCase("ORIGIN") || tokens[0].equalsIgnoreCase("EQU")
                            || tokens[0].equalsIgnoreCase("LTORG")) {
                        opcodeTable[opcodeTableLine][1] = "AD";
                        opcodeTable[opcodeTableLine][2] = "R 11";
                    } else if (tokens[0].equalsIgnoreCase("DS") || tokens[0].equalsIgnoreCase("DC")) {
                        opcodeTable[opcodeTableLine][1] = "DS";
                        opcodeTable[opcodeTableLine][2] = "R 7";
                    } else {
                        opcodeTable[opcodeTableLine][1] = "IS";
                        opcodeTable[opcodeTableLine][2] = "(04, 1)";
                    }
                    opcodeTableLine++;
                } else if (tokens.length == 3) {
                    opcodeTable[opcodeTableLine][0] = tokens[1];

                    if (tokens[1].equalsIgnoreCase("START") || tokens[1].equalsIgnoreCase("END")
                            || tokens[1].equalsIgnoreCase("ORIGIN") || tokens[1].equalsIgnoreCase("EQU")
                            || tokens[1].equalsIgnoreCase("LTORG")) {
                        opcodeTable[opcodeTableLine][1] = "AD";
                        opcodeTable[opcodeTableLine][2] = "R 11";
                    } else if (tokens[1].equalsIgnoreCase("DS") || tokens[1].equalsIgnoreCase("DC")) {
                        opcodeTable[opcodeTableLine][1] = "DS";
                        opcodeTable[opcodeTableLine][2] = "R 7";
                    } else {
                        opcodeTable[opcodeTableLine][1] = "IS";
                        opcodeTable[opcodeTableLine][2] = "(04, 1)";
                    }
                    opcodeTableLine++;
                }

            }
            lc++;
            line_count++;

        }

        // POOL TABLE
        for (int i = 0; i < literalTableLine; i++) {
            if (literalTable[i][0] != null && literalTable[i + 1][0] != null) {
                if (i == 0) {
                    poolTable[poolTableLine] = i + 1;
                    poolTableLine++;
                } else if (Integer.parseInt(literalTable[i][1]) < (Integer.parseInt(literalTable[i + 1][1])) - 1) {
                    poolTable[poolTableLine] = i + 2;
                    poolTableLine++;
                }
            }
        }

        // display

        // literal table
        System.out.println("\nLITERAL TABLE :\n");
        System.out.println("--------------------------");
        System.out.println("Literal \t Address");
        System.out.println("--------------------------");
        for (int i = 0; i < literalTableLine; i++) {
            System.out.println(literalTable[i][0] + "\t\t" + literalTable[i][1] + "\t");
        }
        System.out.println("--------------------------");

        // symbol table
        System.out.println("\n\nSYMBOL TABLE :\n");
        System.out.println("------------------------------------");
        System.out.println("Symbol    Address    Length");
        System.out.println("------------------------------------");
        for (int i = 0; i < symbolTableLine; i++) {
            System.out.println(symbolTable[i][0] + "\t\t" + symbolTable[i][1] + "\t\t" + symbolTable[i][2]);
        }
        System.out.println("------------------------------------");

        // opcode table
        System.out.println("\nOPCODE TABLE :\n");
        System.out.println("------------------------------------");
        System.out.println("Mnemonics    Class    Info");
        System.out.println("------------------------------------");
        for (int i = 0; i < opcodeTableLine; i++) {
            System.out.println(opcodeTable[i][0] + "\t\t" + opcodeTable[i][1] + "\t" + opcodeTable[i][2]);
        }
        System.out.println("------------------------------------");

        // pool table
        System.out.println("\nPOOL TABLE :\n");
        System.out.println("--------------------------");
        System.out.println("Literal Number");
        System.out.println("--------------------------");
        for (int i = 0; i < poolTableLine; i++)
            System.out.println(poolTable[i]);
        System.out.println("--------------------------");

        br.close();
    }
}