package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.ClassFile.*
import java.io.PrintStream

class CompilationUnit(val classFile : ClassFile) : Printable {
    override fun print(printStream: PrintStream, indent: String) {

        with(classFile) {

            if (accessFlags and ACC_MODULE != 0) {
                ModularCompilationUnit(classFile).print(printStream, indent)
            } else { // OrdinaryCompilationUnit
                PackageDeclaration(classFile).print(printStream, indent)
                if(accessFlags and ACC_INTERFACE != 0) { // InterfaceDeclaration
                    if(accessFlags and ACC_ANNOTATION != 0) {
                        AnnotationTypeDeclaration(classFile).print(printStream,indent)
                    } else {
                        NormalInterfaceDeclaration(classFile).print(printStream,indent)
                    }
                } else { // ClassDeclaration
                    if(accessFlags and ACC_ENUM != 0) { // EnumDeclaration
                        EnumDeclaration(classFile).print(printStream,indent)
                    } else { // NormalClassDeclaration
                        NormalClassDeclaration(classFile).print(printStream,indent)
                    }
                }
            }
        }
    }
}