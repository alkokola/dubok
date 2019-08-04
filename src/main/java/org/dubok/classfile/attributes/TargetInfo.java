package org.dubok.classfile.attributes;
import org.dubok.Printable;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;

public final class TargetInfo implements Printable {

    public final int targetType;

    public int typeParameterIndex; // type_parameter_target, type_parameter_bound_target
    public int supertypeIndex;    // supertype_target
    public int boundIndex;        // type_parameter_bound_target
    public int formalParameterIndex; // formal_parameter_target
    public int throwsTypeIndex; // throws_target
    public int exceptionTableIndex; // catch_target
    public int offset; // offset_target, type_argument_target
    public int typeArgumentIndex; // type_argument_index

    public class LocalVar implements Printable  {
        int startPc;
        int length;
        int index;

        LocalVar(DataInput dataInput) throws IOException {
            startPc = dataInput.readUnsignedShort();
            length = dataInput.readUnsignedShort();
            index = dataInput.readUnsignedShort();
        }

        @Override
        public void print(PrintStream printStream, String indent) {
            printStream.print(indent + "LocalVar startPc = " + startPc + " length = " + length + " index = " + index);
        }
    }

    LocalVar[] table; // localvar_target

    TargetInfo(int targetType, DataInput dataInput) throws IOException {
        this.targetType = targetType;
        switch (targetType) {
            case 0x00: // type parameter declaration of generic class or interface
            case 0x01: // type parameter declaration of generic method or constructor
                // type_parameter_target
                typeParameterIndex = dataInput.readUnsignedByte();
            break;
            case 0x10: // type in extends or implements clause
                       // of class declaration (including the direct
                       // superclass or direct superinterface of
                       // an anonymous class declaration), or in
                       //extends clause of interface declaration
                // supertype_target
                supertypeIndex = dataInput.readUnsignedShort();
            break;
            case 0x11: // type in bound of type parameter declaration of generic class or interface
            case 0x12: // type in bound of type parameter declaration of generic method or constructor
                // type_parameter_bound_target
                typeParameterIndex = dataInput.readUnsignedByte();
                boundIndex = dataInput.readUnsignedByte();
            break;
            case 0x13: // type in field declaration
            case 0x14: // return type of method, or type of newly constructed object
            case 0x15: // receiver type of method or constructor
                // empty_target
            break;
            case 0x16: // type in formal parameter declaration of
                       // method, constructor, or lambda expression
                // formal_parameter_target
                formalParameterIndex = dataInput.readUnsignedByte();
            break;
            case 0x17: // type in throws clause of method or constructor
                // throws_target
                throwsTypeIndex = dataInput.readUnsignedShort();
            break;
            case 0x40: // type in local variable declaration
            case 0x41: // type in resource variable declaration
                // localvar_target
                table = new LocalVar[dataInput.readUnsignedShort()];
                for(int i=0; i< table.length; i++) {
                    table[i] = new LocalVar(dataInput);
                }
            break;
            case 0x42: // type in exception parameter declaration
                // catch_target
                exceptionTableIndex = dataInput.readUnsignedShort();
            break;
            case 0x43: // type in instanceof expression
            case 0x44: // type in new expression
            case 0x45: // type in method reference expression using ::new
            case 0x46: // type in method reference expression using ::Identifier
                // offset_target
                offset = dataInput.readUnsignedShort();
            break;
            case 0x47: // type in cast expression
            case 0x48: // type argument for generic constructor in new
                       // expression or explicit constructor invocation
                       // statement
            case 0x49: // type argument for generic method in method
                       // invocation expression
            case 0x4a: // type argument for generic constructor in method
                       // reference expression using ::new

            case 0x4b: // type argument for generic method in method
                       // reference expression using ::Identifier
                // type_argument_target
                offset = dataInput.readUnsignedShort();
                typeArgumentIndex = dataInput.readUnsignedByte();
            break;
            default:
                throw new ClassFormatError("Unknown targetType =" + targetType);

        }
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        printStream.println(indent + "TargetInfo targetType = " + targetType);
        indent += dumpIndent;
        switch (targetType) {
            case 0x00: case 0x01:
                printStream.println(indent + "typeParameterIndex = " + typeParameterIndex);
                break;
            case 0x10:
                printStream.println(indent + "supertypeIndex = " + supertypeIndex);
                break;
            case 0x11: case 0x12:
                printStream.println(indent + "typeParameterIndex = " + typeParameterIndex + " boundIndex = " + boundIndex);
                break;
            case 0x13: case 0x14: case 0x15: break;
            case 0x16:
                printStream.println(indent + "formalParameterIndex = " + formalParameterIndex);
                break;
            case 0x17:
                printStream.println(indent + "throwsTypeIndex = " + throwsTypeIndex);
                break;
            case 0x40: case 0x41:
                printStream.println(indent + "table [" + table.length +"]" );
                for (int i = 0; i < table.length; i++) {
                    table[i].print(printStream, indent + dumpIndent);
                }
                break;
            case 0x42:
                printStream.println(indent + "exceptionTableIndex = " + exceptionTableIndex);
                break;
            case 0x43: case 0x44: case 0x45: case 0x46:
                printStream.println(indent + "offset = " + offset);
                break;
            case 0x47: case 0x48: case 0x49: case 0x4a: case 0x4b:
                printStream.println(indent + "offset = " + offset + " typeArgumentIndex = " + typeArgumentIndex);
                break;
        }
    }
}
