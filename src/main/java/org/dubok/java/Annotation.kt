package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import java.io.PrintStream

class Annotation(val classFile: ClassFile) : Printable {

    override fun print(printStream: PrintStream, indent: String) {
        printStream.println("$indent//${this.javaClass.name}")
    }
}