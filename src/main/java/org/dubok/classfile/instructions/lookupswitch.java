package org.dubok.classfile.instructions;

import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

public class lookupswitch extends Instruction {
    public final int branchOffset;
    public String branchLabel;

    public final int npairs;
    public final int[] offsets;
    public String labels[];
    public final int[] matches;

    public lookupswitch(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions, DataInput dataInput) throws IOException {
        super(offset, opcode, constantPool, instructions);
        assert opcode == 171;
        comment =  "Operand Stack: ..., key -> ..., result";

        int i = offset + 1;
        while (i%4 != 0) {i++;length++;dataInput.readByte();}

        branchOffset = dataInput.readInt();
        npairs = dataInput.readInt();
        length +=8;
        offsets = new int[npairs];
        labels = new String[npairs];
        matches = new int[npairs];
        for (int k = 0; k< offsets.length; k++ ) {
            matches[k] = dataInput.readInt(); length+=4;
            offsets[k] = dataInput.readInt(); length +=4;
        }
    }

}
