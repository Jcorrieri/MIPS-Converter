package converter.mips;

import converter.Conversions;

public class Register {

    private final static int RET_REG_BASE = 2, SAVED_REG_BASE = 16, TEMP_REG_BASE = 8, TEMP_UPPER_REG_BASE = 16,
            ARG_REG_BASE = 4, RES_REG_BASE = 26;

    private final static Register[] SPECIAL_REGS = {
            new Register("$zero", 0),
            new Register("$at", 1),
            new Register("$gp", 28),
            new Register("$sp", 29),
            new Register("$fp", 30),
            new Register("$ra", 31)
    };

    private String name;
    private int val;

    public Register(String name) {

        for (Register specialReg : SPECIAL_REGS) {
            if (specialReg.name().equals(name)) {
                this.name = specialReg.name();
                this.val = specialReg.val();
                return;
            }
        }

        if (name.length() != 3)
            return;

        // $a0 -- 'a' is at index 1, '0' is at index 2
        int valueOffset = name.charAt(2) - Conversions.ASCII_DIGIT_OFFSET;
        switch (name.charAt(1)) {
            case 'v' -> {
                this.name = name;
                this.val = RET_REG_BASE + valueOffset;
            }
            case 's' -> {
                this.name = name;
                this.val = SAVED_REG_BASE + valueOffset;
            }
            case 't' -> {
                this.name = name;
                this.val = (valueOffset > 7) ? (TEMP_UPPER_REG_BASE + valueOffset) : (TEMP_REG_BASE + valueOffset);
            }
            case 'a' -> {
                this.name = name;
                this.val = ARG_REG_BASE + valueOffset;
            }
            case 'k' -> {
                this.name = name;
                this.val = RES_REG_BASE + valueOffset;
            }
            default -> {}
        }
    }

    public Register(int val) {

        if (val < 0 || val > 31)
            return;

        this.val = val;
        for (Register r : SPECIAL_REGS) {
            if (r.val() == val) {
                name = r.name();
                return;
            }
        }

        if (val < 4) {
            name = "$v" + (val - RET_REG_BASE);
        } else if (val < 8) {
            name = "$a" + (val - ARG_REG_BASE);
        } else if (val < 16) {
            name = "$t" + (val - TEMP_REG_BASE);
        } else if (val < 24) {
            name = "$s" + (val - SAVED_REG_BASE);
        } else if (val < 26) {
            name = "$t" + (val - TEMP_UPPER_REG_BASE);
        } else {
            name = "$k" + (val - RES_REG_BASE);
        }
    }

    public Register(String name, int val) { this.name = name; this.val = val; }

    public int val() { return this.val; }

    public String name() { return this.name; }
}
