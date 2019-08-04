package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.constantpool.ConstantClass
import org.dubok.classfile.constantpool.ConstantUtf8
import java.io.PrintStream

class TypeIdentifier(val classFile: ClassFile) : Printable {

    override fun print(printStream: PrintStream, indent: String) {
        with(classFile.constantPool[classFile.thisClass] as ConstantClass) {
            with(classFile.constantPool[this.nameIndex] as ConstantUtf8) {
                print(utf8Value.replace('/','.'))
            }
        }
    }
}