package org.dubok.java

import org.dubok.classfile.ClassFile
import org.dubok.classfile.signature.SignatureBaseVisitor
import org.dubok.classfile.signature.tree.*
import java.io.PrintStream

open class SignatureBaseJavaVisitor(val classFile: ClassFile, val printStream: PrintStream, val indent: String) : SignatureBaseVisitor() {

    // brackets
    override fun enterTypeParameters(ctx: TypeParameters) = printStream.print("<")
    override fun exitTypeParameters(ctx: TypeParameters) = printStream.print(">")
    override fun enterTypeArguments(ctx: TypeArguments) = printStream.print("<")
    override fun exitTypeArguments(ctx: TypeArguments) = printStream.print(">")

    // base types
    override fun visitBaseType(ctx: BaseType) {
        when(ctx.type) {
            "B" -> printStream.print("byte")
            "C" -> printStream.print("char")
            "D" -> printStream.print("double")
            "F" -> printStream.print("float")
            "I" -> printStream.print("int")
            "J" -> printStream.print("long")
            "S" -> printStream.print("long")
            "S" -> printStream.print("short")
            "Z" -> printStream.print("boolean")
        }
    }

    // array
    override fun exitArrayType(ctx: ArrayType) {
        printStream.print("[]")
    }

    // ref type
    override fun visitObjectType(ctx: ObjectType) {
        printStream.print(ctx.type.replace('/','.'))
    }
}