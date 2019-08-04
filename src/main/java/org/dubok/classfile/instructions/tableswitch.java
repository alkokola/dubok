package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class tableswitch extends Instruction {
    public final int branchOffset;
    public String branchLabel;

    public final int lowValue;
    public final int highValue;

    public final int[] offsets;
    public String labels[];

    public tableswitch(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 170;

        comment =  "Operand Stack: ..., index -> ..., result";

        int i = offset + 1;
        // i += i % 4;

        if(i%4 != 0) {i++;length++;dataInput.readByte();}
        if(i%4 != 0) {i++;length++;dataInput.readByte();}
        if(i%4 != 0) {i++;length++;dataInput.readByte();}

        branchOffset = dataInput.readInt();
        lowValue = dataInput.readInt();
        highValue = dataInput.readInt();
        length +=12;

        int numberOfOffsets = highValue - lowValue + 1;
        offsets = new int[numberOfOffsets];
        labels = new String[numberOfOffsets];

        for (int k = 0; k< offsets.length; k++ ) {
            offsets[k] = dataInput.readInt(); length+=4;
        }
        instructions.add(this);
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        super.print(printStream, indent);
        String indent1 = getIndent();
        printStream.println(indent+ indent1 + "// defaultOffset=" + branchOffset +", lowValue=" + lowValue + ", highValue=" + highValue);
        for(int x: offsets) {
            printStream.println(indent+ indent1 + "// offset=" + x +" (to instruction at offset " + (x + offset) + ")" );
        }

    }

}
