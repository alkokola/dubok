package org.dubok.classfile.attributes;

import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class Path implements Printable {

    public final int typePathKind;
    public final int typeArgumentIndex;

    Path(DataInput dataInput) throws IOException {
        typePathKind = dataInput.readUnsignedByte();
        typeArgumentIndex = dataInput.readUnsignedByte();
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.print(indent + "Path typePathKind = " + typePathKind + " typeArgumentIndex = " + typeArgumentIndex);
    }
}
