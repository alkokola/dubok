package org.dubok.engine

import org.dubok.classfile.ClassFile
import org.dubok.classfile.MethodInfo
import org.dubok.classfile.attributes.Code


class StackMachine(val classFile: ClassFile, val methodInfo: MethodInfo) {

    companion object {
        fun execute(classFile: ClassFile, methodInfo: MethodInfo) {
            var variables = ArrayList<Variable>()
            variables[5] = Variable();

        }
    }

    fun walk() {
        val code = methodInfo.attributes.filter {it is Code}.last() as Code
        val bytecode = code.bytecode ?:return

        for(x in bytecode) {
            println("        " + x.offset)
            x.print(System.out,"        ")
        }
    }
}