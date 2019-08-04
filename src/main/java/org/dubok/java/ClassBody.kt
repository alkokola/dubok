package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import java.io.PrintStream

class ClassBody(val classFile: ClassFile): Printable {
    override fun print(printStream: PrintStream, indent: String) {
        val newIndent = indent + Printable.javaIndent
        println(" {")

        // ClassBodyDeclaration:
        //  ClassMemberDeclaration
        //  InstanceInitializer
        //  StaticInitializer
        //  ConstructorDeclaration
        //  ClassMemberDeclaration:
        //  FieldDeclaration
        //  MethodDeclaration
        //  ClassDeclaration
        //  InterfaceDeclaration
        //;

        with(classFile) {

            for(x in fields) {
                FieldDeclaration(classFile,x).print(printStream,newIndent)
            }

            for(x in methods) {
                MethodDeclaration(classFile,x).print(printStream,newIndent)
            }
        }
        println("$indent};")
    }
}