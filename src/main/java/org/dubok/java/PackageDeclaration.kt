package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.constantpool.ConstantClass
import java.io.PrintStream

class PackageDeclaration(val classFile: ClassFile) : Printable {
    override fun print(printStream: PrintStream, indent: String) {
        with(classFile.constantPool[classFile.thisClass] as ConstantClass) {
            val binaryName = binaryClassOrInterfaceName
            if('/' in binaryName) {
                val _package = binaryName.split('/').dropLast(1).joinToString(".")
                println("${indent}package ${_package};\n")
            }
        }
    }
}