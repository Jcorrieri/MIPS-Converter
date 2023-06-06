package converter.mips;

public class Format {

    public static final Format R_FORMAT = new Format('R');
    public static final Format I_FORMAT = new Format('I');
    public static final Format J_FORMAT = new Format('J');

    private String[] instructionFormat;
    char type;

    public Format(char type) {

        switch (type) {
            case 'R' -> {
                instructionFormat = registerType();
                this.type = 'R';
            }
            case 'I' -> {
                instructionFormat = immediateType();
                this.type = 'I';
            }
            case 'J' -> {
                instructionFormat = jumpType();
                this.type = 'J';
            }
        }
    }

    private static String[] registerType() {
        return new String[]{
                "000000",
                "rs",
                "rt",
                "rd",
                "shift",
                "function"
        };
    }

    private static String[] immediateType() {
        return new String[]{
                "opcode",
                "rs",
                "rt",
                "immediate/offset"
        };
    }

    private static String[] jumpType() {
        return new String[]{
                "opcode",
                "address"
        };
    }

    public void setOpcode(int opcode) {
        switch (this.type) {
            case 'J', 'I' -> this.instructionFormat[0] = "" + opcode;
            case 'R' -> this.instructionFormat[5] = "" + opcode;
            default -> {}
        }
    }

    public void setRegisters(Register rd, Register rs, Register rt) {
        switch(this.type) {
            case 'I' -> {
                this.instructionFormat[1] = rs.val() + "";
                this.instructionFormat[2] = rt.val() + "";
            }
            case 'R' -> {
                this.instructionFormat[1] = (rs == null) ? "0" : rs.val() + "";
                this.instructionFormat[2] = (rt == null) ? "0" : rt.val() + "";
                this.instructionFormat[3] = (rd == null) ? "0" : rd.val() + "";
            }
            default -> {}
        }
    }

    public void setOther(String data) {
        switch(this.type) {
            case 'R' -> this.instructionFormat[4] = data;
            case 'I' -> this.instructionFormat[3] = data;
            case 'J' -> this.instructionFormat[1] = data;
            default -> {}
        }
    }

    public String[] getInstructionFormat() { return instructionFormat; }

    public char type() { return type; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{|");
        for (String str : instructionFormat)
            builder.append(str).append("|");
        builder.append("}");

        return builder.toString();
    }
}
