package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.constantpool.ConstantUtf8;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class Signature extends Attribute {

    final int signatureIndex;

    public String getSignature() {
        return ((ConstantUtf8)constantPool[signatureIndex]).utf8Value;
    }

    Signature(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        signatureIndex = dataInput.readUnsignedShort();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (Signature) attributeNameIndex = " + attributeNameIndex + " signatureIndex = " + signatureIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        constantPool[signatureIndex].print(printStream, indent);
    }

}
