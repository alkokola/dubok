package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.signature.SignatureTree
import org.dubok.classfile.signature.tree.*
import java.io.PrintStream

class NormalClassDeclaration(val classFile: ClassFile) : Printable {

    class NormalClassSignatureVisitor(classFile: ClassFile, printStream: PrintStream,indent: String) : SignatureBaseJavaVisitor(classFile, printStream, indent) {

        override fun enterTypeParameter(ctx: TypeParameter) {
            printStream.print("@Annotaion ")
            with(ctx) {
                with(children[0] as Identifier) {
                    print(identifier);
                }
            }
        }
        override fun exitTypeParameter(ctx: TypeParameter) {
            val elements = ctx.parent.children.filter { it is Element }
            if(ctx != elements.last()) {
                print(", ")
            }
        }
        override fun exitTypeArgument(ctx: TypeArgument) {
            val elements = ctx.parent.children.filter { it is Element }
            if(ctx != elements.last()) {
                print(", ")
            }
        }

        override fun enterClassBound(ctx: ClassBound) {
            printStream.print(" extends ")
        }

        override fun enterInterfaceBound(ctx: InterfaceBound) {
            val elements = ctx.parent.children.filter { it is Element }
            if(ctx == elements[0]) {
                printStream.print(" extends ")
            } else {
                printStream.print(" & ")
            }
        }

        override fun enterTypeVariableSignature(ctx: TypeVariableSignature) {
            printStream.print("@Annotation ")
            with(ctx.children[0] as Identifier) {
                printStream.print(identifier)
            }
        }

        override fun enterSuperinterfaceSignature(ctx: SuperinterfaceSignature) {
            if (ctx.parent.children.filter { it is SuperinterfaceSignature }.get(0) == ctx) {
                printStream.print(" implements ")
            }
        }

        override fun exitSuperinterfaceSignature(ctx: SuperinterfaceSignature) {
            val interfaces = ctx.parent.children.filter { it is SuperinterfaceSignature } as List<SuperinterfaceSignature>
            if (interfaces.last() != ctx) {
                print(", ")
            }
        }

        override fun enterSuperclassSignature(ctx: SuperclassSignature) {
            printStream.print(" extends ")
        }

        override fun enterPackageSpecifier(ctx: PackageSpecifier) {
            val identifiers = ctx.children.filter { it is Identifier } as List<Identifier>
            val parts = identifiers.map { it.identifier }
            printStream.print(parts.joinToString(".", postfix = "."))
        }

        override fun enterSimpleClassTypeSignature(ctx: SimpleClassTypeSignature) {
            with(ctx.children[0] as Identifier) {
                printStream.print(identifier)
            }
        }
    }
    override fun print(printStream: PrintStream, indent: String) {

        printStream.println("$indent//${this.javaClass.name}")
        ClassModifier(classFile).print(printStream, indent)
        printStream.print("class ")
        TypeIdentifier(classFile).print(printStream, indent)
        // [TypeParameters]  [Superclass] [Superinterfaces]
        SignatureTree.walk(classFile.classSignature, NormalClassSignatureVisitor(classFile, printStream, indent));
        printStream.print(" ")
        ClassBody(classFile).print(printStream,indent)
    }
}