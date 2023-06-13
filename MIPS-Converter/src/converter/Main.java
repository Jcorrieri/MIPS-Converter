package converter;

import com.formdev.flatlaf.FlatLightLaf;
import converter.gui.CustomJFrame;
import converter.mips.Format;
import converter.mips.Instruction;
import converter.mips.Register;

import java.util.ArrayList;

@SuppressWarnings("DuplicatedCode")
public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        CustomJFrame base = new CustomJFrame();
        base.setLocationRelativeTo(null);
        base.setVisible(true);
    }

    public static ArrayList<String> convertMipsToHex(ArrayList<String> data) {

        long start = System.currentTimeMillis();
        ArrayList<String> outputData = new ArrayList<>();

        for (String datum : data) {

            Instruction ins = translateLine(datum.split(" "));
            if (ins == null)
                continue;

            outputData.add( Conversions.lineToHex(ins.format()) );
        }

        System.out.println("Time to convert Mips to Hex: " + (System.currentTimeMillis() - start) + "ms");
        return outputData;
    }

    public static ArrayList<String> convertMipsToBinary(ArrayList<String> data) {

        long start = System.currentTimeMillis();
        ArrayList<String> outputData = new ArrayList<>();

        for (String datum : data) {

            Instruction ins = translateLine(datum.split(" "));
            if (ins == null)
                continue;

            outputData.add( Conversions.lineToBinary(ins.format()) );
        }

        System.out.println("Time to convert Mips to Binary: " + (System.currentTimeMillis() - start) + "ms");
        return outputData;
    }

    public static ArrayList<String> convertHexToMips(ArrayList<String> data) {
        ArrayList<String> binaryData = new ArrayList<>();

        for (String datum : data)
            binaryData.add( Conversions.hexToBinary(datum) );

        return convertBinaryToMips(binaryData);
    }

    public static ArrayList<String> convertBinaryToMips(ArrayList<String> data) {
        ArrayList<String> output = new ArrayList<>();

        for (String datum : data) {
            if (datum.length() < Conversions.OPCODE_BITS)
                continue;

            // First six bits are always the opcode
            String opcode = datum.substring(0, Conversions.OPCODE_BITS);
            boolean registerType = false;

            if (opcode.equals("000000")) {// Is an R type, need last six bits
                opcode = datum.substring(datum.length() - Conversions.OPCODE_BITS);
                registerType = true;
            }

            Instruction insToProcess = null;
            for (Instruction ins : Instruction.INSTRUCTIONS) {
                if (!registerType && ins.format() == Format.R_FORMAT)
                    continue;
                else if (registerType && ins.format() != Format.R_FORMAT)
                    continue;

                if (ins.opcode() == Conversions.binaryToDecimal(opcode)) {
                    insToProcess = new Instruction(ins.name(), new Format(ins.format().type()), ins.opcode());
                    break;
                }
            }

            if (insToProcess == null)
                continue;
            output.add( binaryLineToMips(datum, insToProcess) );
        }
        return output;
    }

    private static String binaryLineToMips(String line, Instruction ins) {

        String output = ins.name() + " ";

        if (ins.format().type() == 'R') {
            line = line.substring(Conversions.OPCODE_BITS);
            Register rs = new Register(Conversions.binaryToDecimal( line.substring(0, Conversions.REGISTER_BITS) ));

            line = line.substring(Conversions.REGISTER_BITS);
            Register rt = new Register(Conversions.binaryToDecimal( line.substring(0, Conversions.REGISTER_BITS) ));

            line = line.substring(Conversions.REGISTER_BITS);
            Register rd  = new Register(Conversions.binaryToDecimal( line.substring(0, Conversions.REGISTER_BITS) ));

            line = line.substring(Conversions.REGISTER_BITS);
            int shift = Conversions.binaryToDecimal( line.substring(0, Conversions.SHIFT_BITS) );

            switch (ins.name()) {
                case "mfhi", "mflo" -> output += rd.name();
                case "div", "mult" -> output += rs.name() + ", " + rt.name();
                case "sra", "srl", "sll" -> output += rd.name() + ", " + rt.name() + ", " + shift;
                case "jr" -> output += rs.name();
                default -> output += rd.name() + ", " + rs.name() + ", " + rt.name();
            }
        } else if (ins.format().type() == 'I') {
            line = line.substring(Conversions.OPCODE_BITS);
            Register rs = new Register(Conversions.binaryToDecimal( line.substring(0, Conversions.REGISTER_BITS) ));

            line = line.substring(Conversions.REGISTER_BITS);
            Register rt = new Register(Conversions.binaryToDecimal( line.substring(0, Conversions.REGISTER_BITS) ));

            line = line.substring(Conversions.REGISTER_BITS);
            int immediate = Conversions.binaryToDecimal( line.substring(0, Conversions.OFFSET_BITS) );

            if (ins.name().length() > 2)
                output += rt.name() + ", " + rs.name() + ", " + immediate;
            else
                output += rt.name() + ", " + immediate + "(" + rs.name() + ")";
        } else {
            output += Conversions.binaryToHex( line.substring(Conversions.OPCODE_BITS) );
        }

        return output;
    }

    private static Instruction translateLine(String[] lineData) {

        Instruction insToProcess = null;
        for (Instruction ins : Instruction.INSTRUCTIONS)
            if (ins.name().equals(lineData[0])) {
                insToProcess = new Instruction(lineData[0], new Format(ins.format().type()), ins.opcode());
                break;
            }

        if (insToProcess == null) {
            return null;
        }

        switch (insToProcess.format().type()) {
            case 'R' -> translateLineRType(lineData, insToProcess);
            case 'I' -> translateLineIType(lineData, insToProcess);
            case 'J' -> translateLineJType(lineData, insToProcess);
        }
        return insToProcess;
    }

    private static void translateLineRType(String[] data, Instruction ins) {

        Register rd, rs, rt;
        if (data.length == 4) {
            rd = new Register(data[1].substring(0, data[1].indexOf(',')));
            rs = new Register(data[2].substring(0, data[2].indexOf(',')));
            rt = new Register(data[3]);
        } else if (data.length == 2) {
            rd = new Register(data[1]);
            rs = null;
            rt = null;
        } else {
            rd = null;
            rs = new Register(data[1].substring(0, data[1].indexOf(',')));
            rt = new Register(data[2]);
        }

        if (ins.name().equals("sll") || ins.name().equals("sra") || ins.name().equals("srl")) {
            ins.format().setOther("" + data[3]);
            rt = rs;
            rs = null;
        } else {
            ins.format().setOther("000000");
        }

        ins.format().setRegisters(rd, rs, rt);
        ins.format().setOpcode(ins.opcode());
    }

    private static void translateLineIType(String[] data, Instruction ins) {

        Register rs, rt;
        String offset;
        if (data.length == 4) {
            rt = new Register(data[1].substring(0, data[1].indexOf(',')));
            rs = new Register(data[2].substring(0, data[2].indexOf(',')));
            offset = data[3];
        } else {
            rt = new Register(data[1].substring(0, data[1].indexOf(',')));
            rs = new Register(data[2].substring(data[2].indexOf('$'), data[2].indexOf(')')));
            offset = data[2].substring(0, data[2].indexOf('('));
        }

        ins.format().setRegisters(null, rs, rt);
        ins.format().setOpcode(ins.opcode());
        ins.format().setOther(offset);
    }

    private static void translateLineJType(String[] data, Instruction ins) {
        ins.format().setOpcode(ins.opcode());

        if (Conversions.isHex(data[1]))
            data[1] = "" + Conversions.hexToDecimal(data[1]);

        ins.format().setOther(data[1]);
    }
}
