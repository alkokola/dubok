
package org.dubok.classfile.instructions;

import org.dubok.Printable;
import org.dubok.classfile.constantpool.Constant;

import java.io.DataInput;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Instruction implements Printable {

    public final int offset; // the Instruction's offset
    public final int opcode; // opcode
    public int length;       // Instruction length in bytes.
    public String mmemonic;  // mnemonic
    public String comment;  // short description. It is needed in order to produce extremely nice bytecode listing.
                             // a number of instructions have extra comment

    public final Constant[] constantPool; // reference to classfile's constant pool, for convenience

    // The following two fields are used by print()
    // in order to produce extremely nice-formatted bytecode listing.
    // It is a waste of memory but nobody cares about it
    public int maxOffset;
    public int maxMnemonicLength;

    final List<Instruction> instructions; // reference to method's bytecode

    // labels
    private static int labelCount = 1;
    String LABEL;

    public int push() {
        return 0;
    }
    public int pop() {
        return 0;
    }

    protected static String setLabel(int offset, List<Instruction> instructions) {
        for(Instruction l : instructions) {
            if (l.offset == offset) {
                if(l.LABEL == null) {
                    l.LABEL = "LABEL" + Instruction.labelCount;
                    l.LABEL.intern();
                    Instruction.labelCount++;
                }
                return l.LABEL;
            }
        }
        return null;
    }

    protected static void setLabels(List<Instruction> instructions) {
        // convert offsets to labels
        for(Instruction x : instructions) {
            String className = x.getClass().getSimpleName();
            if(className.startsWith("if") || className.startsWith("jsr") || className.startsWith("goto_") || className.endsWith("switch")) {
                try {
                    Field field;
                    int offset;

                    if(className.endsWith("switch")) {
                        field = x.getClass().getDeclaredField("offsets");
                        field.setAccessible(true);
                        int offsets[] = (int[])field.get(x);
                        field = x.getClass().getDeclaredField("labels");
                        field.setAccessible(true);
                        String labels[] = (String[])field.get(x);

                        for (int i = 0; i < offsets.length; i++) {
                            labels[i] = setLabel(x.offset + offsets[i], instructions);
                        }
                    }

                    field = x.getClass().getDeclaredField("branchOffset");
                    field.setAccessible(true);
                    offset = field.getInt(x);
                    field = x.getClass().getDeclaredField("branchLabel");
                    field.setAccessible(true);
                    field.set(x, setLabel(x.offset+ offset,instructions));

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    protected static void setInvoke(List<Instruction> instructions) {

        // We not need to distinguish between invokedynamic and invokeinterface for further decompilation

        for(Instruction x : instructions) {
            String className = x.getClass().getSimpleName();
            if(className.startsWith("if") || className.startsWith("jsr") || className.startsWith("goto_") || className.endsWith("switch")) {
                try {
                    Field field;
                    int offset;

                    if(className.endsWith("switch")) {
                        field = x.getClass().getDeclaredField("offsets");
                        field.setAccessible(true);
                        int offsets[] = (int[])field.get(x);
                        field = x.getClass().getDeclaredField("labels");
                        field.setAccessible(true);
                        String labels[] = (String[])field.get(x);

                        for (int i = 0; i < offsets.length; i++) {
                            labels[i] = setLabel(x.offset + offsets[i], instructions);
                        }
                    }

                    field = x.getClass().getDeclaredField("branchOffset");
                    field.setAccessible(true);
                    offset = field.getInt(x);
                    field = x.getClass().getDeclaredField("branchLabel");
                    field.setAccessible(true);
                    field.set(x, setLabel(x.offset+ offset,instructions));

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    */
    protected Instruction(int offset, int opcode, final Constant[] constantPool, List<Instruction> instructions) throws IOException {
        this.offset = offset;
        this.instructions = instructions;
        this.opcode = opcode;
        this.constantPool = constantPool;

        // the following two fields are reassigned in subclasses
        this.length = 1;
        this.mmemonic = getClass().getSimpleName();

        maxOffset = offset;
        maxMnemonicLength = mmemonic.length();
    }

    // gets length bytes of bytecode out of DataInput into List<>
    public static List<Instruction> getBytecode(Constant[] constantPool, int length, DataInput dataInput) throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        for(int offset = 0; offset < length;) {
            Instruction instruction = Instruction.newInstruction(offset, constantPool,instructions, dataInput);
            offset += instruction.length;
        }

        int maxOffset = 0;
        int maxMnemonicLength = 0;

        for (Instruction x : instructions) {
            if(maxOffset < x.maxOffset) maxOffset = x.maxOffset;
            if(maxMnemonicLength < x.maxMnemonicLength) maxMnemonicLength = x.maxMnemonicLength;
        }

        for(Instruction x : instructions) {
            x.maxOffset = maxOffset;
            x.maxMnemonicLength = maxMnemonicLength;
        }

        setLabels(instructions);
        //setInvoke(instructions);

        return instructions;
    }

    // the factory which produces instructions out of DataInput
    private static Instruction newInstruction(int offset, Constant[] constantPool, List<Instruction> instructions,final DataInput dataInput) throws IOException {
        int opcode = dataInput.readUnsignedByte();
        switch (opcode) {
            case 50: return new aaload(offset, opcode, constantPool, instructions);
            case 83: return new aastore(offset, opcode, constantPool, instructions);
            case 1:  return new aconst_null(offset, opcode,constantPool, instructions);
            case 25: return new aload(offset, opcode, constantPool, instructions, dataInput);
            case 42: case 43: case 44: case 45: return new aload_n(offset, opcode, constantPool, instructions);
            case 189: return new anewarray(offset, opcode, constantPool, instructions, dataInput);
            case 176: return new areturn(offset, opcode,constantPool, instructions);
            case 190: return new arraylength(offset, opcode, constantPool, instructions);
            case 58: return new astore(offset,opcode,constantPool, instructions, dataInput);
            case 75: case 76: case 77: case 78: return new astore_n(offset,opcode,constantPool, instructions);
            case 191: return new athrow(offset,opcode,constantPool, instructions);
            case 51: return new baload(offset,opcode,constantPool, instructions);
            case 84: return new bastore(offset,opcode,constantPool, instructions);
            case 16: return new bipush(offset,opcode,constantPool, instructions, dataInput);
            case 52: return new caload(offset,opcode,constantPool, instructions);
            case 85: return new castore(offset,opcode,constantPool, instructions);
            case 192: return new checkcast(offset,opcode,constantPool, instructions, dataInput);
            case 144: return new d2f(offset,opcode,constantPool, instructions);
            case 142: return new d2i(offset,opcode,constantPool, instructions);
            case 143: return new d2l(offset,opcode,constantPool, instructions);
            case 99: return new dadd(offset,opcode,constantPool, instructions);
            case 49: return new daload(offset,opcode,constantPool, instructions);
            case 82: return new dastore(offset,opcode,constantPool, instructions);
            case 152: return new dcmpg(offset,opcode,constantPool, instructions);
            case 151: return new dcmpg(offset,opcode,constantPool, instructions);
            case 14: case 15: return new dconst_d(offset,opcode,constantPool, instructions);
            case 111: return new ddiv(offset,opcode,constantPool, instructions);
            case 24: return new dload(offset,opcode,constantPool, instructions, dataInput);
            case 38: case 39: case 40: case 41: return new dload_n(offset,opcode,constantPool, instructions);
            case 107: return new dmul(offset,opcode,constantPool, instructions, dataInput);
            case 119: return new dneg(offset,opcode,constantPool, instructions);
            case 115: return new drem(offset,opcode,constantPool, instructions);
            case 175: return new dreturn(offset,opcode,constantPool, instructions);
            case 57: return new dstore(offset,opcode,constantPool, instructions, dataInput);
            case 71: case 72: case 73: case 74: return new dstore_n(offset,opcode,constantPool, instructions);
            case 103: return new dsub(offset,opcode,constantPool, instructions);
            case 89: return new dup(offset,opcode,constantPool, instructions);
            case 90: return new dup_x1(offset,opcode,constantPool, instructions, dataInput);
            case 91: return new dup_x2(offset,opcode,constantPool, instructions, dataInput);
            case 92: return new dup2(offset,opcode,constantPool, instructions);
            case 93: return new dup2_x1(offset,opcode,constantPool, instructions, dataInput);
            case 94: return new dup2_x2(offset,opcode,constantPool, instructions, dataInput);
            case 141: return new f2d(offset,opcode,constantPool, instructions, dataInput);
            case 139: return new f2i(offset,opcode,constantPool, instructions, dataInput);
            case 140: return new f2l(offset,opcode,constantPool, instructions, dataInput);
            case 98: return new fadd(offset,opcode,constantPool, instructions, dataInput);
            case 48: return new faload(offset,opcode,constantPool, instructions, dataInput);
            case 81: return new fastore(offset,opcode,constantPool, instructions, dataInput);
            case 150: return new fcmpg(offset,opcode,constantPool, instructions, dataInput);
            case 149: return new fcmpl(offset,opcode,constantPool, instructions, dataInput);
            case 11: case 12: case 13: return new fconst_f(offset,opcode,constantPool, instructions, dataInput);
            case 110: return new fdiv(offset,opcode,constantPool, instructions, dataInput);
            case 23: return new fload(offset,opcode,constantPool, instructions, dataInput);
            case 34: case 35: case 36: case 37: return new fload_n(offset,opcode,constantPool, instructions, dataInput);
            case 106: return new fmul(offset,opcode,constantPool, instructions, dataInput);
            case 118: return new fneg(offset,opcode,constantPool, instructions, dataInput);
            case 114: return new frem(offset,opcode,constantPool, instructions, dataInput);
            case 174: return new freturn(offset,opcode,constantPool, instructions, dataInput);
            case 56: return new fstore(offset,opcode,constantPool, instructions, dataInput);
            case 67: case 68: case 69: case 70: return new fstore_n(offset,opcode,constantPool, instructions, dataInput);
            case 102: return new fsub(offset,opcode,constantPool, instructions, dataInput);
            case 180: return new getfield(offset,opcode,constantPool, instructions, dataInput);
            case 178: return new getstatic(offset,opcode,constantPool, instructions, dataInput);
            case 167: return new goto_(offset,opcode,constantPool, instructions, dataInput);
            case 200: return new goto_w(offset,opcode,constantPool, instructions, dataInput);
            case 145: return new i2b(offset,opcode,constantPool, instructions, dataInput);
            case 146: return new i2c(offset,opcode,constantPool, instructions, dataInput);
            case 135: return new i2d(offset,opcode,constantPool, instructions, dataInput);
            case 134: return new i2f(offset,opcode,constantPool, instructions, dataInput);
            case 133: return new i2l(offset,opcode,constantPool, instructions, dataInput);
            case 147: return new i2l(offset,opcode,constantPool, instructions, dataInput);
            case 96: return new iadd(offset,opcode,constantPool, instructions, dataInput);
            case 46: return new iaload(offset,opcode,constantPool, instructions, dataInput);
            case 126: return new iand(offset,opcode,constantPool, instructions, dataInput);
            case 79: return new iastore(offset,opcode,constantPool, instructions, dataInput);
            case 2: case 3: case 4: case 5: case 6: case 7: case 8: return new iconst_i(offset,opcode,constantPool, instructions, dataInput);
            case 108: return new idiv(offset,opcode,constantPool, instructions, dataInput);
            case 165: return new if_acmpeq(offset,opcode,constantPool, instructions, dataInput);
            case 166: return new if_acmpne(offset,opcode,constantPool, instructions, dataInput);
            case 159: return new if_icmpeq(offset,opcode,constantPool, instructions, dataInput);
            case 160: return new if_icmpne(offset,opcode,constantPool, instructions, dataInput);
            case 161: return new if_icmplt(offset,opcode,constantPool, instructions, dataInput);
            case 162: return new if_icmpge(offset,opcode,constantPool, instructions, dataInput);
            case 163: return new if_icmpgt(offset,opcode,constantPool, instructions, dataInput);
            case 164: return new if_icmple(offset,opcode,constantPool, instructions, dataInput);
            case 153: return new ifeq(offset,opcode,constantPool, instructions, dataInput);
            case 154: return new ifne(offset,opcode,constantPool, instructions, dataInput);
            case 155: return new iflt(offset,opcode,constantPool, instructions, dataInput);
            case 156: return new ifge(offset,opcode,constantPool, instructions, dataInput);
            case 157: return new ifgt(offset,opcode,constantPool, instructions, dataInput);
            case 158: return new ifle(offset,opcode,constantPool, instructions, dataInput);
            case 199: return new ifnonnull(offset,opcode,constantPool, instructions, dataInput);
            case 198: return new ifnull(offset,opcode,constantPool, instructions, dataInput);
            case 132: return new iinc(offset,opcode,constantPool, instructions, dataInput);
            case 21: return new iload(offset,opcode,constantPool, instructions, dataInput);
            case 26: case 27: case 28: case 29: return new iload_n(offset,opcode,constantPool, instructions, dataInput);
            case 104: return new imul(offset,opcode,constantPool, instructions, dataInput);
            case 116: return new ineg(offset,opcode,constantPool, instructions, dataInput);
            case 193: return new instanceof_(offset,opcode,constantPool, instructions, dataInput);
            case 186: return new invokedynamic(offset,opcode,constantPool, instructions, dataInput);
            case 185: return new invokeinterface(offset,opcode,constantPool, instructions, dataInput);
            case 183: return new invokespecial(offset,opcode,constantPool, instructions, dataInput);
            case 184: return new invokestatic(offset,opcode,constantPool, instructions, dataInput);
            case 182: return new invokevirtual(offset,opcode,constantPool, instructions, dataInput);
            case 128: return new ior(offset,opcode,constantPool, instructions, dataInput);
            case 112: return new irem(offset,opcode,constantPool, instructions, dataInput);
            case 172: return new ireturn(offset,opcode,constantPool, instructions, dataInput);
            case 120: return new ishl(offset,opcode,constantPool, instructions, dataInput);
            case 122: return new ishr(offset,opcode,constantPool, instructions, dataInput);
            case 54: return new istore(offset,opcode,constantPool, instructions, dataInput);
            case 59: case 60: case 61: case 62: return new istore_n(offset,opcode,constantPool, instructions, dataInput);
            case 100: return new isub(offset,opcode,constantPool, instructions, dataInput);
            case 124: return new iushr(offset,opcode,constantPool, instructions, dataInput);
            case 130: return new ixor(offset,opcode,constantPool, instructions, dataInput);
            case 168: return new jsr(offset,opcode,constantPool, instructions, dataInput);
            case 201: return new jsr_w(offset,opcode,constantPool, instructions, dataInput);
            case 138: return new l2d(offset,opcode,constantPool, instructions, dataInput);
            case 137: return new l2f(offset,opcode,constantPool, instructions, dataInput);
            case 136: return new l2i(offset,opcode,constantPool, instructions, dataInput);
            case 97: return new ladd(offset,opcode,constantPool, instructions, dataInput);
            case 47: return new laload(offset,opcode,constantPool, instructions, dataInput);
            case 127: return new land(offset,opcode,constantPool, instructions, dataInput);
            case 80: return new lastore(offset,opcode,constantPool, instructions, dataInput);
            case 148: return new lcmp(offset,opcode,constantPool, instructions, dataInput);
            case 9: case 10: return new lconst_l(offset,opcode,constantPool, instructions, dataInput);
            case 18: return new ldc(offset,opcode,constantPool, instructions, dataInput);
            case 19: return new ldc_w(offset,opcode,constantPool, instructions, dataInput);
            case 20: return new ldc2_w(offset,opcode,constantPool, instructions, dataInput);
            case 109: return new ldiv(offset,opcode,constantPool, instructions, dataInput);
            case 22: return new lload(offset,opcode,constantPool, instructions, dataInput);
            case 30: case 31: case 32: case 33: return new lload_n(offset,opcode,constantPool, instructions, dataInput);
            case 105: return new lmul(offset,opcode,constantPool, instructions, dataInput);
            case 117: return new lneg(offset,opcode,constantPool, instructions, dataInput);
            case 171: return new lookupswitch(offset,opcode,constantPool, instructions, dataInput);
            case 129: return new lor(offset,opcode,constantPool, instructions, dataInput);
            case 113: return new lrem(offset,opcode,constantPool, instructions, dataInput);
            case 173: return new lreturn(offset,opcode,constantPool, instructions, dataInput);
            case 121: return new lshl(offset,opcode,constantPool, instructions, dataInput);
            case 123: return new lshr(offset,opcode,constantPool, instructions, dataInput);
            case 55: return new lstore(offset,opcode,constantPool, instructions, dataInput);
            case 63: case 64: case 65: case 66: return new lstore_n(offset,opcode,constantPool, instructions, dataInput);
            case 101: return new isub(offset,opcode,constantPool, instructions, dataInput);
            case 125: return new lushr(offset,opcode,constantPool, instructions, dataInput);
            case 131: return new lxor(offset,opcode,constantPool, instructions, dataInput);
            case 194: return new monitorenter(offset,opcode,constantPool, instructions, dataInput);
            case 195: return new monitorexit(offset,opcode,constantPool, instructions, dataInput);
            case 197: return new multianewarray(offset,opcode,constantPool, instructions, dataInput);
            case 187: return new new_(offset,opcode,constantPool, instructions, dataInput);
            case 188: return new newarray(offset,opcode,constantPool, instructions, dataInput);
            case 0: return new nop(offset,opcode,constantPool, instructions, dataInput);
            case 87: return new pop(offset,opcode,constantPool, instructions, dataInput);
            case 88: return new pop2(offset,opcode,constantPool, instructions, dataInput);
            case 181: return new putfield(offset,opcode,constantPool, instructions, dataInput);
            case 179: return new putstatic(offset,opcode,constantPool, instructions, dataInput);
            case 169: return new ret(offset,opcode,constantPool, instructions, dataInput);
            case 177: return new return_(offset,opcode,constantPool, instructions, dataInput);
            case 53: return new saload(offset,opcode,constantPool, instructions, dataInput);
            case 86: return new sastore(offset,opcode,constantPool, instructions, dataInput);
            case 17: return new sipush(offset,opcode,constantPool, instructions, dataInput);
            case 95: return new sipush(offset,opcode,constantPool, instructions, dataInput);
            case 170: return new tableswitch(offset,opcode,constantPool, instructions, dataInput);
            case 196:  return new iinc(offset,opcode,constantPool, instructions, dataInput);
            default: {
                throw new ClassFormatError("Invalid opcode: " + opcode );
            }
        }
    }

    public String getIndent() {
        String offsetFormat   = "%-" + (("" + maxOffset).length() + maxMnemonicLength) + "s ";
        return String.format(offsetFormat, "") + "    ";
    }

    @Override
    public void print(PrintStream printStream, String indent) {
        if(LABEL != null) {
            printStream.println(indent + LABEL + ":");
        }
        String offsetFormat   = "%-" + (("" + maxOffset).length()) + "s ";
        String mnemonicFormat = "%-" + maxMnemonicLength + "s";
        String s = String.format(offsetFormat, offset) + ": " + String.format(mnemonicFormat,  mmemonic) + "  // " + comment;
        printStream.println(indent + s);
    }
}
