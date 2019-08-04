package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;


public final class Module extends Attribute {
    private int moduleNameIndex;
    private int moduleFlags;
    private int moduleVersionIndex;

    class Requires implements Printable {
        int reqiuresIndex;
        int requiresFlags;
        int requiresVersionIndex;

        Requires(final DataInput dataInput) throws  IOException {
            reqiuresIndex = dataInput.readUnsignedShort();
            requiresFlags = dataInput.readUnsignedShort();
            requiresVersionIndex = dataInput.readUnsignedShort();
        }
        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "requires reqiuresIndex = " + reqiuresIndex + " requiresFlags = " + Integer.toString(requiresFlags, 16) + " requiresVersionIndex = " + requiresVersionIndex);
            indent = indent + "  ";
            constantPool[reqiuresIndex].print(printStream, indent);

            if(requiresFlags == 0) {
                printStream.println(indent + "ACC_NONE         0x0000 Flags are not set on this requires.");
            }
            if((requiresFlags & 0x0020) != 0) {
                printStream.println(indent + "ACC_TRANSITIVE   0x0020 Indicates that any module which depends on the current module, implicitly declares a dependence on the module indicated by this entry.");
            }
            if((requiresFlags & 0x0040) != 0) {
                printStream.println(indent + "ACC_STATIC_PHASE 0x0040 Indicates that this dependence was not explicitly or implicitly declared in the source of the module declaration.");
            }
            if((requiresFlags & 0x1000) != 0) {
                printStream.println(indent + "ACC_SYNTHETIC    0x1000 Indicates that this dependence was not explicitly or implicitly declared in the source of the module declaration.");
            }
            if((requiresFlags & 0x8000) != 0) {
                printStream.println(indent + "ACC_MANDATED     0x8000 Indicates that this dependence was implicitly declared in the source of the module declaration.");
            }
            if(0 != requiresVersionIndex) {
                constantPool[requiresVersionIndex].print(printStream, indent);
            }
        }
    }
    private Requires[] requires;

    class Exports implements Printable {
        int exportsIndex;
        int exportsFlags;
        int expotrsToIndices[];

        Exports(final DataInput dataInput) throws  IOException {
            exportsIndex = dataInput.readUnsignedShort();
            exportsFlags = dataInput.readUnsignedShort();
            expotrsToIndices = new int[dataInput.readUnsignedShort()];
            for(int i = 0; i < expotrsToIndices.length; i++) {
                expotrsToIndices[i] = dataInput.readUnsignedShort();
            }
        }
        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "requires exportsIndex = " + exportsIndex + " exportsFlags = " + Integer.toString(exportsFlags, 16));
            indent = indent + "  ";
            constantPool[exportsIndex].print(printStream, indent);
            if(exportsFlags == 0) {
                printStream.println(indent + "ACC_NONE         0x0000 Flags are not set on this requires.");
            }
            if((exportsFlags & 0x1000) != 0) {
                printStream.println(indent + "ACC_SYNTHETIC    0x1000 Indicates that this export was not explicitly or implicitly declared in  the source of the module declaration.");
            }
            if((exportsFlags & 0x8000) != 0) {
                printStream.print(indent + "ACC_MANDATED     0x8000 Indicates that this export was implicitly declared in the source of the module declaration.");
            }
            printStream.println(indent + "expotrsToIndices [" + expotrsToIndices.length + "]");
            for(int x : expotrsToIndices) {
                constantPool[x].print(printStream, indent + "  ");
            }
        }
    }
    private Exports[] exports;

    class Opens implements Printable {
        int opensIndex;
        int opensFlags;
        int opensToIndices[];

        Opens(final DataInput dataInput) throws  IOException {
            opensIndex = dataInput.readUnsignedShort();
            opensFlags = dataInput.readUnsignedShort();
            opensToIndices = new int[dataInput.readUnsignedShort()];
            for(int i = 0; i < opensToIndices.length; i++) {
                opensToIndices[i] = dataInput.readUnsignedShort();
            }
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "opening opensIndex = " + opensIndex + " opensFlags = " + Integer.toString(opensFlags, 16));
            indent = indent + "  ";
            constantPool[opensIndex].print(printStream, indent);
            if(opensFlags == 0) {
                printStream.println(indent + "ACC_NONE         0x0000 Flags are not set on this opening.");
            }
            if((opensFlags & 0x1000) != 0) {
                printStream.println(indent + "ACC_SYNTHETIC    0x1000 Indicates that this opening was not explicitly or implicitly declared in the source of the module declaration.");
            }
            if((opensFlags & 0x8000) != 0) {
                printStream.println(indent + "ACC_MANDATED     0x8000 Indicates that this opening was implicitly declared in the source of the module declaration.");
            }
            printStream.println(indent + "opensToIndices [" + opensToIndices.length + "]");
            for(int x : opensToIndices) {
                constantPool[x].print(printStream, indent + "  ");
            }
        }
    }
    private Opens[] opens;
    private int[] usesIndices;

    class Provides implements Printable {
        int providesIndex;
        int provideWithIndices[];
        Provides(final DataInput dataInput) throws  IOException {
            providesIndex = dataInput.readUnsignedShort();
            provideWithIndices = new int[dataInput.readUnsignedShort()];
            for(int i = 0; i < provideWithIndices.length; i++) {
                provideWithIndices[i] = dataInput.readUnsignedShort();
            }
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "provides providesIndex = " + providesIndex);
            indent = indent + "  ";
            constantPool[providesIndex].print(printStream, indent);
            printStream.println(indent + "provideWithIndices [" + provideWithIndices.length + "]");
            for(int x : provideWithIndices) {
                constantPool[x].print(printStream, indent + "  ");
            }
        }
    }
    private Provides[] provides;

    Module(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        moduleNameIndex = dataInput.readUnsignedShort();
        moduleFlags = dataInput.readUnsignedShort();
        moduleVersionIndex = dataInput.readUnsignedShort();

        requires = new Requires[dataInput.readUnsignedShort()];
        for(int i=0; i < requires.length; i++) {
            requires[i] = new Requires(dataInput);
        }
        exports = new Exports[dataInput.readUnsignedShort()];
        for(int i=0; i < exports.length; i++) {
            exports[i] = new Exports(dataInput);
        }
        opens = new Opens[dataInput.readUnsignedShort()];
        for(int i=0; i < opens.length; i++) {
            opens[i] = new Opens(dataInput);
        }
        usesIndices = new int[dataInput.readUnsignedShort()];
        for(int i=0; i < usesIndices.length; i++) {
            usesIndices[i] = dataInput.readUnsignedShort();
        }
        provides = new Provides[dataInput.readUnsignedShort()];
        for(int i=0; i < provides.length; i++) {
            provides[i] = new Provides(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "Attribute (Module) attributeNameIndex = " + attributeNameIndex + " moduleNameIndex = " + moduleNameIndex + " moduleFlags = " + moduleFlags + " moduleVersionIndex = " + moduleVersionIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        constantPool[moduleNameIndex].print(printStream, indent);
        if(moduleFlags == 0) {
            printStream.println(indent + "ACC_NONE      0x0000 Flags are not set on this module.");
        }
        if((moduleFlags & 0x0200) != 0) {
            printStream.print(indent + "ACC_OPEN      0x0200 Indicates that this module is open.");
        }
        if((moduleFlags & 0x1000) != 0) {
            printStream.print(indent + "ACC_SYNTHETIC 0x1000 Indicates that this module was not explicitly or implicitly declared.");
        }
        if((moduleFlags & 0x8000) != 0) {
            printStream.print(indent + "ACC_MANDATED  0x8000 Indicates that this module was implicitly declared..");
        }
        if(0 != moduleVersionIndex) {
            constantPool[moduleVersionIndex].print(printStream, indent);
        }
        printStream.println(indent + "requires [" + requires.length + "]");
        for (Printable x : requires) {
            x.print(printStream, indent + dumpIndent);
        }
        printStream.println(indent + "opens [" + opens.length + "]");
        for (Printable x : opens) {
            x.print(printStream, indent + dumpIndent);
        }
        printStream.println(indent + "usesIndices [" + usesIndices.length + "]");
        for (int x : usesIndices) {
            constantPool[x].print(printStream, indent + dumpIndent);
        }
        printStream.println(indent + "provides [" + provides.length + "]");
        for (Printable x : provides) {
            x.print(printStream, indent + dumpIndent);
        }
    }

}
