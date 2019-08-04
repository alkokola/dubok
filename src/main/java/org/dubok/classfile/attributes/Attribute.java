package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.classfile.constantpool.ConstantUtf8;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public class Attribute implements Printable {
    public final int attributeNameIndex;
    public final int attributeLength;
    public final Constant constantPool[];

    public String getName() {
        return ((ConstantUtf8)constantPool[attributeNameIndex]).utf8Value;
    }

    private final byte info[];
    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    static public Attribute newAttribute(final Constant[] constantPool, final DataInput dataInput) throws IOException {
        int localAttributeNameIndex = dataInput.readUnsignedShort();


        String attribute = "";
        Constant cpEntry = constantPool[localAttributeNameIndex];
        if (cpEntry instanceof ConstantUtf8) {
           attribute = ((ConstantUtf8)constantPool[localAttributeNameIndex]).utf8Value;
        }

        switch (attribute) {
            case "ConstantValue": return new ConstantValue(constantPool, localAttributeNameIndex, dataInput);
            case "Code": return new Code(constantPool, localAttributeNameIndex, dataInput);
            case "StackMapTable" : return new StackMapTable(constantPool, localAttributeNameIndex, dataInput);
            case "Exceptions":  return new Exceptions(constantPool, localAttributeNameIndex, dataInput);
            case "InnerClasses": return new InnerClasses(constantPool, localAttributeNameIndex, dataInput);
            case "EnclosingMethod": return new EnclosingMethod(constantPool, localAttributeNameIndex, dataInput);
            //case "Synthetic": return new AttrEnclosingMethod(constantPool, localAttributeNameIndex, dataInput);
            case "Signature": return new Signature(constantPool, localAttributeNameIndex, dataInput);
            case "SourceFile": return new SourceFile(constantPool, localAttributeNameIndex, dataInput);
            //case "SourceDebugExtension": return new AttrSourceFile(constantPool, localAttributeNameIndex, dataInput);
            case "LineNumberTable": return new LineNumberTable(constantPool, localAttributeNameIndex, dataInput);
            case "LocalVariableTable": return new LocalVariableTable(constantPool, localAttributeNameIndex, dataInput);
            case "LocalVariableTypeTable": return new LocalVariableTypeTable(constantPool, localAttributeNameIndex, dataInput);
            case "Deprecated": return new Deprecated(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeVisibleAnnotations": return new RuntimeVisibleAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeInvisibleAnnotations": return new RuntimeInvisibleAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeVisibleParameterAnnotations": return new RuntimeVisibleParameterAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeInvisibleParameterAnnotations": return new RuntimeInvisibleParameterAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeVisibleTypeAnnotations": return new RuntimeVisibleTypeAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "RuntimeInvisibleTypeAnnotations": return new RuntimeInvisibleTypeAnnotations(constantPool, localAttributeNameIndex, dataInput);
            case "AnnotationDefault": return new AnnotationDefault(constantPool, localAttributeNameIndex, dataInput);
            case "BootstrapMethods": return new BootstrapMethods(constantPool, localAttributeNameIndex, dataInput);
            //case "MethodParameters": return new AttrLocalVariableTable(constantPool, localAttributeNameIndex, dataInput);
            case "Module": return new Module(constantPool, localAttributeNameIndex, dataInput);
            case "ModulePackages": return new ModulePackages(constantPool, localAttributeNameIndex, dataInput);
            //case "ModuleMainClass": return new AttrLocalVariableTable(constantPool, localAttributeNameIndex, dataInput);
            default:
                return new Attribute(constantPool, localAttributeNameIndex, dataInput);
        }
    }
    protected Attribute(Constant[] constantPool, int attributeNameIndex, int attributeLength) { // dummy
        this.constantPool = constantPool;
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        info = null;

    }
    private Attribute(final Constant[] constantPool, final int index, final DataInput dataInput) throws IOException {
        this.constantPool = constantPool;
        this.attributeNameIndex = index;
        attributeLength = dataInput.readInt();
        info =  new byte[attributeLength];
        dataInput.readFully(info);
    }

    @Override
    public void print(PrintStream printStream, String indent) {

        printStream.print(indent + "Attribute (Unknown) attributeNameIndex = " + attributeNameIndex + " value = "  );
        indent += dumpIndent;
        for (int i = 0; i < info.length; i++) {
            if(i!=0) {
                printStream.print(":");
            }
            int k = info[i] & 0xff;
            printStream.print(hexDigits[k >>> 4]);
            printStream.print(hexDigits[k & 0xf]);
        }

        printStream.println(" length = " + info.length);
        constantPool[attributeNameIndex].print(printStream,indent);
    }
}
