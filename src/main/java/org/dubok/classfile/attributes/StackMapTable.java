package org.dubok.classfile.attributes;

import org.dubok.classfile.constantpool.Constant;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class StackMapTable extends Attribute {
    public static final int ITEM_Top = 0;
    public static final int ITEM_Integer = 1;
    public static final int ITEM_Float = 2;
    public static final int ITEM_Null = 5;
    public static final int ITEM_UninitializedThis = 6;
    public static final int ITEM_Long = 4;
    public static final int ITEM_Double = 3;
    public static final int ITEM_Object = 7;
    public static final int ITEM_Uninitialized = 8;

    class VerificationTypeInfo implements Printable{
        int type;
        int constantPoolIndex;
        int offset;

        VerificationTypeInfo(DataInput dataInput) throws IOException {
            type = dataInput.readUnsignedByte();
            switch (type) {
                case 0: // ITEM_Top
                case 1: // ITEM_Integer
                case 2: // ITEM_Float
                case 5: // ITEM_Null
                case 6: // ITEM_UninitializedThis
                case 4: // ITEM_Long
                case 3: // ITEM_Double

                    break;
                case 7: // ITEM_UninitializedThis
                    constantPoolIndex = dataInput.readUnsignedShort();
                    break;
                case 8: // ITEM_Uninitialized
                    offset = dataInput.readUnsignedShort();
                    break;
                default:
                    throw new ClassFormatError("VerificationTypeInfo tag = " + type);
            }

        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.println(indent + "VerificationTypeInfo type = " + type);
            indent = indent + dumpIndent;
            if(type == 7) {
                constantPool[constantPoolIndex].print(printStream, indent);
            }
            if(type == 8) {
                printStream.println(indent + "offset = " + offset);
            }
        }
    }


    class StackMapFrame implements Printable {
        int frameType;
        int offsetDelta;
        VerificationTypeInfo stack[];
        VerificationTypeInfo locals[];
        StackMapFrame(DataInput dataInput) throws IOException {
           frameType = dataInput.readUnsignedByte();

            //noinspection StatementWithEmptyBody
            if (frameType <= 63 ) { // SAME

           } else if (frameType <= 127) { // SAME_LOCALS_1_STACK_ITEM
               stack = new VerificationTypeInfo[1];
               stack[0] = new VerificationTypeInfo(dataInput);
           } else if (frameType <= 246) { // Reserved
               throw  new ClassFormatError("Invalid frameType " + frameType + " in the StackMapTable");
           } else if (frameType == 247) { // SAME_LOCALS_1_STACK_ITEM_EXTENDED
               offsetDelta = dataInput.readUnsignedShort();
               stack = new VerificationTypeInfo[1];
               stack[0] = new VerificationTypeInfo(dataInput);
           } else if (frameType <= 250) { // CHOP
               offsetDelta = dataInput.readUnsignedShort();
           } else if (frameType == 251) { // SAME_FRAME_EXTENDED
               offsetDelta = dataInput.readUnsignedShort();
           } else if (frameType <= 254) { // APPEND
               offsetDelta = dataInput.readUnsignedShort();
               locals = new VerificationTypeInfo[frameType - 251];
               for (int i=0; i< locals.length; i++) {
                   locals[i] = new VerificationTypeInfo(dataInput);
               }
           } else // FULL_FRAME
               if (frameType == 255) {
                   offsetDelta = dataInput.readUnsignedShort();
                   locals = new VerificationTypeInfo[dataInput.readUnsignedShort()];
                   for (int i=0; i< locals.length; i++) {
                       locals[i] = new VerificationTypeInfo(dataInput);
                   }
                   stack = new VerificationTypeInfo[dataInput.readUnsignedShort()];
                   for (int i=0; i< stack.length; i++) {
                       stack[i] = new VerificationTypeInfo(dataInput);
                   }
           }
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.print(indent + "stackMapFrame type = " + frameType);
            if(frameType > 246) {
                printStream.print(" offsetDelta = " + offsetDelta);
            }
            printStream.println();
            indent += dumpIndent;

            if( (frameType > 63 & frameType <= 127) | (frameType == 247) | (frameType == 255)) {
                printStream.println(indent + "stack [" + stack.length + "]");
                for (VerificationTypeInfo x : stack) {
                    x.print(printStream, indent + dumpIndent);
                }
            }

            if(frameType > 251) {
                printStream.println(indent + "locals [" + locals.length + "]");
                for (VerificationTypeInfo x : locals) {
                    x.print(printStream, indent + dumpIndent);
                }
            }
        }
    }

    private StackMapFrame[] entries;

    StackMapTable(Constant[] constantPool, int attributeNameIndex, final DataInput dataInput) throws IOException {
        super(constantPool, attributeNameIndex, dataInput.readInt());
        entries = new StackMapFrame[dataInput.readUnsignedShort()];
        for(int i=0; i< entries.length; i++) {
            entries[i] = new StackMapFrame(dataInput);
        }
    }

    @Override
    public void print(PrintStream printStream, String indent)  {
        printStream.println(indent + "Attribute (StackMapTable) attributeNameIndex = " + attributeNameIndex);
        indent += dumpIndent;
        constantPool[attributeNameIndex].print(printStream, indent);
        printStream.println(indent + "entries [" + entries.length + "]" );
        indent +=dumpIndent;
        for (Printable x : entries) {
            x.print(printStream, indent);
        }
    }
}
