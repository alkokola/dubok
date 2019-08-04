package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class InnerClasses extends Attribute {

    private static final int ACC_PUBLIC = 0x0001;      // Marked or implicitly public in source.
    private static final int ACC_PRIVATE = 0x0002;     // Marked private in source.
    private static final int ACC_PROTECTED = 0x0004;   // Marked protected in source.
    private static final int ACC_STATIC = 0x0008;      // Marked or implicitly static in source.
    private static final int ACC_FINAL = 0x0010;       // Marked or implicitly final in source.
    private static final int ACC_INTERFACE = 0x0200;   // Was an interface in source.
    private static final int ACC_ABSTRACT = 0x0400;    // Marked or implicitly abstract in source.
    private static final int ACC_SYNTHETIC = 0x1000;   // Declared synthetic; not present in the source code.
    private static final int ACC_ANNOTATION = 0x2000;  // Declared as an annotation type.
    private static final int ACC_ENUM = 0x4000;        // Declared as an enum type.

    class InnerClass implements Printable {
        int innerClassInfoIndex;
        int outerClassInfoIndex;
        int innerNameInfoIndex;
        int innerClassAccessFlags;

        InnerClass(final DataInput dataInput) throws  IOException{
            innerClassInfoIndex = dataInput.readUnsignedShort();
            outerClassInfoIndex = dataInput.readUnsignedShort();
            innerNameInfoIndex = dataInput.readUnsignedShort();
            innerClassAccessFlags = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "innerClass innerClassInfoIndex = " + innerClassInfoIndex + " outerClassInfoIndex = " + outerClassInfoIndex
                    + " innerNameInfoIndex = " + innerNameInfoIndex
                    + " innerClassAccessFlags = " + innerClassAccessFlags + " " + Integer.toString(innerClassAccessFlags, 16));
            indent = indent + "  ";
            constantPool[innerClassInfoIndex].print(printStream, indent);

            if( (innerClassAccessFlags & ACC_PUBLIC) != 0) {
                printStream.println(indent + "ACC_PUBLIC     0x0001 Marked or implicitly public in source.");
            }
            if( (innerClassAccessFlags & ACC_PRIVATE) != 0) {
                printStream.println(indent + "ACC_PRIVATE    0x0002 Marked private in source.");
            }
            if( (innerClassAccessFlags & ACC_PROTECTED) != 0) {
                printStream.println(indent + "ACC_PROTECTED  0x0004 Marked protected in source.");
            }
            if( (innerClassAccessFlags & ACC_STATIC) != 0) {
                printStream.println(indent + "ACC_STATIC     0x0008 Marked or implicitly static in source.");
            }
            if( (innerClassAccessFlags & ACC_FINAL) != 0) {
                printStream.println(indent + "ACC_FINAL      0x0010 Marked or implicitly final in source.");
            }
            if( (innerClassAccessFlags & ACC_INTERFACE) != 0) {
                printStream.println(indent + "ACC_INTERFACE  0x0200 Was an interface in source.");
            }
            if( (innerClassAccessFlags & ACC_ABSTRACT) != 0) {
                printStream.println(indent + "ACC_ABSTRACT   0x0400 Marked or implicitly abstract in source.\n");
            }
            if( (innerClassAccessFlags & ACC_SYNTHETIC) != 0) {
                printStream.println(indent + "ACC_SYNTHETIC  0x1000 Declared synthetic; not present in the source code.\n");
            }
            if( (innerClassAccessFlags & ACC_ANNOTATION) != 0) {
                printStream.println(indent + "ACC_ANNOTATION 0x2000 Declared as an annotation type.\n");
            }
            if( (innerClassAccessFlags & ACC_ENUM) != 0) {
                printStream.println(indent + "ACC_ENUM       0x4000 Declared as an enum type.");
            }

            if(outerClassInfoIndex!=0) {
                constantPool[outerClassInfoIndex].print(printStream, indent);
            }
            if(0 != innerNameInfoIndex) {
                constantPool[innerNameInfoIndex].print(printStream, indent);
            }
        }
    }
    private InnerClass[] innerClasses;

    InnerClasses(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        innerClasses = new InnerClass[dataInput.readUnsignedShort()];
        for (int i=0; i< innerClasses.length; i++) {
            innerClasses[i] = new InnerClass(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (InnerClasses) attributeNameIndex = " + attributeNameIndex);
        indent = dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "innerClasses [" + innerClasses.length + "]" ) ;
        for (Printable x : innerClasses) {
            x.print(printStream, indent + dumpIndent);
        }
    }

}
