# MIPS Converter

My first Java project. This is based on an in-class assignment in which we were asked to convert MIPS assembly instructions to hex code by hand. It is designed to take text files containing Mips instructions, binary code, or hex code and convert the contents to the corresponding output. 

## Input File Format

The input file should be a plain text file (.txt) containing MIPS assembly instructions, binary instructions, or hex instructions. The following screenshot shows the supported line format as well as all currently supported MIPS instructions:

![Supported format and instructions](/supported-format-instructions.png?raw=true "Optional Title")

Example MIPS input file:
```
add $s1, $t2, $t8
andi $s2, $v1, 8
lb $t5, 3($a0)
mult $s2, $a3
mflo $t6
sb $zero, 16($t2)
xor $t0, $s2, $t9
```

Example binary input file:
```
00000001010110001000100000100000
00110000011100100000000000001000
10000000100011010000000000000011
00000010010001110000000000011000
00000000000000000111000000010010
10100001010000000000000000010000
00000010010110010100000000100110
```

Example hex input file:
```
0x01588820
0x30720008
0x808D0003
0x02470018
0x00007012
0xA1400010
0x02594026
```

## Installation

Currently, you can find the .jar file of the application in /MIPS-Converter/out/artifacts/MIPS-Converter.jar

## Usage

This program can perform three main tasks: Convert MIPS instructions to hexadecimal instructions, convert MIPS instructions to binary instructions, and perform small conversions between decimal, binary, and hexadecimal values. 

## Additional Info

For the purposes of this project, I have coded the algorithms for converting decimal, binary, and hexadecimal values myself. 

## Contributing

I am not currently looking for any outside contributions, this is simply a personal project to get my feet in the water.
