package converter.mips;

import java.util.Objects;

import static converter.mips.Format.*;

public record Instruction(String name, Format format, int opcode) {

    // R-Types
    private static final Instruction ADD = new Instruction("add", R_FORMAT, 32);
    private static final Instruction AND = new Instruction("and", R_FORMAT, 36);
    private static final Instruction DIV = new Instruction("div", R_FORMAT, 26);
    private static final Instruction JR = new Instruction("jr", R_FORMAT, 8);
    private static final Instruction MFHI = new Instruction("mfhi", R_FORMAT, 16);
    private static final Instruction MFLO = new Instruction("mflo", R_FORMAT, 18);
    private static final Instruction MULT = new Instruction("mult", R_FORMAT, 24);
    private static final Instruction OR = new Instruction("or", R_FORMAT, 37);
    private static final Instruction SLL = new Instruction("sll", R_FORMAT, 0);
    private static final Instruction SLT = new Instruction("slt", R_FORMAT, 42);
    private static final Instruction SRA = new Instruction("sra", R_FORMAT, 3);
    private static final Instruction SRL = new Instruction("srl", R_FORMAT, 2);
    private static final Instruction SUB = new Instruction("sub", R_FORMAT, 34);
    private static final Instruction XOR = new Instruction("xor", R_FORMAT, 38);

    // I-Types
    private static final Instruction ADDI = new Instruction("addi", I_FORMAT, 8);
    private static final Instruction ANDI = new Instruction("andi", I_FORMAT, 12);
    private static final Instruction BEQ = new Instruction("beq", I_FORMAT, 4);
    private static final Instruction BNE = new Instruction("bne", I_FORMAT, 5);
    private static final Instruction LB = new Instruction("lb", I_FORMAT, 32);
    private static final Instruction LH = new Instruction("lh", I_FORMAT, 33);
    private static final Instruction LW = new Instruction("lw", I_FORMAT, 35);
    private static final Instruction ORI = new Instruction("ori", I_FORMAT, 13);
    private static final Instruction SB = new Instruction("sb", I_FORMAT, 40);
    private static final Instruction SH = new Instruction("sh", I_FORMAT, 41);
    private static final Instruction SLTI = new Instruction("slti", I_FORMAT, 10);
    private static final Instruction SW = new Instruction("sw", I_FORMAT, 43);
    private static final Instruction XORI = new Instruction("xori", I_FORMAT, 14);

    // J-Types
    private static final Instruction J = new Instruction("j", J_FORMAT, 2);
    private static final Instruction JAL = new Instruction("jal", J_FORMAT, 3);

    public static final Instruction[] INSTRUCTIONS = {
            ADD, ADDI, AND, ANDI, BEQ, BNE, DIV, J, JAL, JR, LB, LH, LW, MFHI, MFLO,
            MULT, OR, ORI, SB, SH, SLL, SLT, SLTI, SRA, SRL, SUB, SW, XOR, XORI
    };

    @Override
    public String toString() {
        return name + " " + format.toString() + " " + opcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Instruction) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.format, that.format) &&
                this.opcode == that.opcode;
    }

}
