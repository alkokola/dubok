package org.dubok.java

import org.dubok.classfile.ClassFile
import org.dubok.Printable
import java.io.PrintStream

class ModularCompilationUnit(val classFile: ClassFile) : Printable {

    override fun print(printStream: PrintStream, indent: String) {
        printStream.println("$indent//${this.javaClass.name}")
    }
}