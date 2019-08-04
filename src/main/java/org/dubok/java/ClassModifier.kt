package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.ClassFile.*
import java.io.PrintStream

class ClassModifier(val classFile: ClassFile): Printable {
    override fun print(printStream: PrintStream, indent: String) {
        printStream.println("$indent//${this.javaClass.name}")

        //ClassModifier:
        //(one of)
        //Annotation public protected private
        //        abstract static final strictfp

        Annotation(classFile).print(printStream,indent)

        with(classFile) {
            val public = accessFlags and ACC_PUBLIC == 1
            val abstract = accessFlags and ACC_ABSTRACT == 1
            val final = accessFlags and ACC_FINAL == 1


            if(public) print("public ")
            if(abstract) print("abstract ")
            if(final) print("final ")
        }



    }
}