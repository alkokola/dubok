package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.FieldInfo
import org.dubok.classfile.constantpool.ConstantUtf8
import org.dubok.classfile.signature.SignatureTree
import java.io.PrintStream

class FieldDeclaration (val classFile: ClassFile, val fieldInfo: FieldInfo) : Printable {

    override fun print(printStream: PrintStream, indent: String) {
        //{FieldModifier} UnannType VariableDeclaratorList ;
        with(fieldInfo) {
            printStream.print(indent)
            SignatureTree.walk(fieldDescriptorOrSignature, SignatureBaseJavaVisitor(classFile, printStream, indent));
            printStream.print(" ")
            val constantUtf8 = classFile.constantPool[nameIndex] as ConstantUtf8
            printStream.print(constantUtf8.utf8Value)
            printStream.println(";")
        }

    }
}