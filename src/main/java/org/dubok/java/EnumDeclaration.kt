package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import java.io.PrintStream

class EnumDeclaration(val classFile: ClassFile) : Printable {


    override fun print(printStream: PrintStream, indent: String) {

        printStream.println("$indent//${this.javaClass.name}")
        //ClassModifier(classFile).print(printStream, indent)
        //printStream.print("class ")
        //TypeIdentifier(classFile).print(printStream, indent)
        // [TypeParameters]  [Superclass] [Superinterfaces]
        //SignatureTree.walk(classFile.classSignature, NormalClassDeclaration.NormalClassSignatureVisitor(classFile, printStream, indent));
        //printStream.print(" ")
        //ClassBody(classFile).print(printStream,indent)
    }}