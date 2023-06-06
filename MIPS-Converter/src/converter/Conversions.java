package converter;

import converter.mips.Format;

public final class Conversions {

    public static final int REGISTER_BITS = 5, SHIFT_BITS = 5, OPCODE_BITS = 6, FUNCTION_BITS = 6;
    public static final int OFFSET_BITS = 16, ADDRESS_BITS = 26, INTEGER_BITS = 32, ASCII_DIGIT_OFFSET = 48;

    public static String lineToHex(Format format) { return binaryToHex( lineToBinary(format) ); }

    public static String lineToBinary(Format format){

        StringBuilder result = new StringBuilder();
        String[] values = format.getInstructionFormat();

        if (format.type() == 'R') {
            result.append( "000000" );
            result.append( decimalToBinary(Integer.parseInt(values[1]), REGISTER_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[2]), REGISTER_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[3]), REGISTER_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[4]), SHIFT_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[5]), FUNCTION_BITS) );
        } else if (format.type() == 'I') {
            result.append( decimalToBinary(Integer.parseInt(values[0]), OPCODE_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[1]), REGISTER_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[2]), REGISTER_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[3]), OFFSET_BITS) );
        } else {
            result.append( decimalToBinary(Integer.parseInt(values[0]), OPCODE_BITS) );
            result.append( decimalToBinary(Integer.parseInt(values[1]), ADDRESS_BITS) );
        }

        return result.toString();
    }

    private static String decimalToBinary(int x, int bits) {
        StringBuilder result = new StringBuilder();

        int binaryPositionVal = (int)Math.pow(2, bits - 1);
        while (x > 0) {

            if (x >= binaryPositionVal) {
                result.append("1");
                x -= binaryPositionVal;
            }
            else {
                result.append("0");
            }
            binaryPositionVal /= 2;
        }
        while (result.length() < bits)
            result.append("0");

        return result.toString();
    }

    public static String decimalToBinary(int x) {
        StringBuilder result = new StringBuilder();

        if (x == Integer.MAX_VALUE) {
            result.append("1".repeat(31));
            return result.toString();
        }

        int binaryPositionVal = (int)Math.pow(2, INTEGER_BITS - 2);
        while (x < binaryPositionVal)
            binaryPositionVal /= 2;

        while (x > 0) {
            if (x >= binaryPositionVal) {
                result.append("1");
                x -= binaryPositionVal;
            }
            else {
                result.append("0");
            }
            binaryPositionVal /= 2;
        }

        while (binaryPositionVal > 0) {
            result.append("0");
            binaryPositionVal /= 2;
        }

        while (result.length() % 4 != 0)
            result.insert(0, "0");

        return result.toString();
    }

    public static String decimalToHex(int x) { return binaryToHex( decimalToBinary(x) ); }

    public static String binaryToHex(String x) {

        StringBuilder result = new StringBuilder("0x");

        StringBuilder xBuilder = new StringBuilder(x);
        while (xBuilder.length() % 4 != 0)
            xBuilder.insert(0, "0");

        x = xBuilder.toString();

        int pos = 0;
        while (pos < x.length()) {
            String group = x.substring(pos, pos + 4);
            int y = binaryGroupToDecimal(group);
            if (y < 10)
                result.append(y);
            else {
                switch (y) {
                    case 10 -> result.append('A');
                    case 11 -> result.append('B');
                    case 12 -> result.append('C');
                    case 13 -> result.append('D');
                    case 14 -> result.append('E');
                    case 15 -> result.append('F');
                }
            }
            pos += 4;
        }

        return result.toString();
    }

    private static int binaryGroupToDecimal(String group) {

        int eightPlace = (group.charAt(0) - ASCII_DIGIT_OFFSET) * 8;
        int fourPlace = (group.charAt(1) - ASCII_DIGIT_OFFSET) * 4;
        int twoPlace = (group.charAt(2) - ASCII_DIGIT_OFFSET) * 2;
        int onePlace = (group.charAt(3) - ASCII_DIGIT_OFFSET);
        return eightPlace + fourPlace + twoPlace + onePlace;
    }

    public static String hexToBinary(String x) {

        StringBuilder result = new StringBuilder();
        if (x.contains("0x")) { x = x.substring(x.indexOf("0x") + 2); }

        for (int i = 0; i < x.length(); i++) {
            if (Character.isDigit(x.charAt(i))) {
                result.append( decimalToBinary(x.charAt(i) - ASCII_DIGIT_OFFSET, 4) );
            } else {
                switch ( Character.toUpperCase(x.charAt(i)) ) {
                    case 'A' -> result.append( decimalToBinary(10, 4) );
                    case 'B' -> result.append( decimalToBinary(11, 4) );
                    case 'C' -> result.append( decimalToBinary(12, 4) );
                    case 'D' -> result.append( decimalToBinary(13, 4) );
                    case 'E' -> result.append( decimalToBinary(14, 4) );
                    case 'F' -> result.append( decimalToBinary(15, 4) );
                }
            }
        }
        return result.toString();
    }

    public static int binaryToDecimal(String x) {

        int result = 0;
        if (x.indexOf('1') > -1)
            x = x.substring(x.indexOf('1'));

        int bits = x.length();
        for (int i = 0; i < x.length(); i++) {
            result += (x.charAt(i) - ASCII_DIGIT_OFFSET) * Math.pow(2, --bits);
        }

        return result;
    }

    public static int hexToDecimal(String x) { return binaryToDecimal( hexToBinary(x) ); }

    public static boolean isHex(String x) {

        for (int i = 0; i < x.length(); i++)
            if (Character.isLetter(x.charAt(i)))
                return true;

        return false;
    }
}
