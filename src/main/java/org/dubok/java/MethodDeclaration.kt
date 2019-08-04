package org.dubok.java

import org.dubok.Printable
import org.dubok.classfile.ClassFile
import org.dubok.classfile.MethodInfo
import org.dubok.classfile.constantpool.ConstantUtf8
import org.dubok.classfile.signature.SignatureTree
import org.dubok.classfile.signature.tree.*
import org.dubok.engine.StackMachine

import java.io.PrintStream

class MethodDeclaration(val classFile: ClassFile, val methodInfo: MethodInfo) : Printable {

    override fun print(printStream: PrintStream, indent: String) {
        with(methodInfo.methodDescriptorOrSignature) {

            val visitor = SignatureBaseJavaVisitor(classFile, printStream, indent)

            fun walk(list : ArrayList<SignatureTree>, delemiter : String) {
                for(x in list) {
                    SignatureTree.walk(x,visitor)
                    if(x!=list.last()) {
                        printStream.print(delemiter);
                    }
                }
            }


            printStream.print(indent)

            //MethodDescriptor:
            // ( {ParameterDescriptor} ) ReturnDescriptor

            //MethodSignature:
            //[TypeParameters] ( {JavaTypeSignature} ) Result {ThrowsSignature}


            //[TypeParameters]
            for(x in children) {
                if(x is TypeParameters) {
                    SignatureTree.walk(x,visitor);
                    printStream.print(" ");
                }
            }

            // ReturnDescriptor or Result
            for(x in children) {
                if(x is ReturnDescriptor || x is Result) {
                    SignatureTree.walk(x,visitor);
                    printStream.print(" ");
                }
            }

            // method name
            val constantUtf8 = classFile.constantPool[methodInfo.nameIndex] as ConstantUtf8
            printStream.print(constantUtf8.utf8Value)

            // Parameters
            var i = 1;
            val elements = children.filter { it is Element } as ArrayList<Element>
            printStream.print("(")
            for(x in children) {
                if(x is ParameterDescriptor || x is JavaTypeSignature) {

                    SignatureTree.walk(x,visitor);
                    printStream.print(" ");
                    printStream.print("param" + i++);
                    if(x != elements.last()) {
                        printStream.print(", ")
                    }
                }
            }
            printStream.print(")")

            // ThrowsSignature
            val throwSignatures = children.filter {it is ThrowsSignature }
            for(x in throwSignatures) {
                if(x==throwSignatures[0]) {
                    printStream.print(" throws ")
                }
                SignatureTree.walk(x,visitor);
                if(x!= throwSignatures.last()) {
                    printStream.print(", ")
                }
            }

            //SignatureTree.walk(methodDescriptorOrSignature, SignatureBaseJavaVisitor(classFile, printStream, indent));
            printStream.println("{");
            val engine = StackMachine(classFile, methodInfo);
            engine.walk()
            printStream.println("$indent};");
        }
    }
}