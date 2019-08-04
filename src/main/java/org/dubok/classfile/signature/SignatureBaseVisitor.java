package org.dubok.classfile.signature;

import org.dubok.classfile.signature.tree.*;

public class SignatureBaseVisitor implements SignatureVisitor {

    // convinience stuff
    @Override
    public void visit(SignatureTree signatureTree) {
        final String simpleName = signatureTree.getClass().getSimpleName();
        System.out.println("void visit" + simpleName + "(" + simpleName + " ctx);");
        System.out.println("public void visit" + simpleName + "(" + simpleName + " ctx) {}");
    }
    @Override
    public void enter(SignatureTree signatureTree) {
        final String simpleName = signatureTree.getClass().getSimpleName();
        System.out.println("void enter" + simpleName + "(" + simpleName + " ctx);");
        System.out.println("public void enter" + simpleName + "(" + simpleName + " ctx) {}");
    }
    @Override
    public void exit(SignatureTree signatureTree) {
        final String simpleName = signatureTree.getClass().getSimpleName();
        System.out.println("void exit" + simpleName + "(" + simpleName + " ctx);");
        System.out.println("public void exit" + simpleName + "(" + simpleName + " ctx) {}");
    }

    // base visitors
    @Override
    public void enterClassBound(ClassBound ctx) {}

    @Override
    public void enterClassSignature(ClassSignature ctx) {}

    @Override
    public void enterClassTypeSignature(ClassTypeSignature ctx) {}

    @Override
    public void enterInterfaceBound(InterfaceBound ctx) {}

    @Override
    public void enterPackageSpecifier(PackageSpecifier ctx) {}

    @Override
    public void enterReferenceTypeSignature(ReferenceTypeSignature ctx) {}

    @Override
    public void enterSimpleClassTypeSignature(SimpleClassTypeSignature ctx) {}

    @Override
    public void enterSuperclassSignature(SuperclassSignature ctx) {}

    @Override
    public void enterSuperinterfaceSignature(SuperinterfaceSignature ctx) {}

    @Override
    public void enterTypeArgument(TypeArgument ctx) {}

    @Override
    public void enterTypeArguments(TypeArguments ctx) {}

    @Override
    public void enterTypeParameters(TypeParameters ctx) {}

    @Override
    public void enterTypeParameter(TypeParameter ctx) {}

    @Override
    public void enterTypeVariableSignature(TypeVariableSignature ctx) {}

    @Override
    public void exitClassBound(ClassBound ctx) {}

    @Override
    public void exitClassSignature(ClassSignature ctx) {}

    @Override
    public void exitClassTypeSignature(ClassTypeSignature ctx) {}

    @Override
    public void exitInterfaceBound(InterfaceBound ctx) {}

    @Override
    public void exitPackageSpecifier(PackageSpecifier ctx) {}

    @Override
    public void exitReferenceTypeSignature(ReferenceTypeSignature ctx) {}

    @Override
    public void exitSimpleClassTypeSignature(SimpleClassTypeSignature ctx) {}

    @Override
    public void exitSuperclassSignature(SuperclassSignature ctx) {}

    @Override
    public void exitSuperinterfaceSignature(SuperinterfaceSignature ctx) {}

    @Override
    public void exitTypeArgument(TypeArgument ctx) {}

    @Override
    public void exitTypeArguments(TypeArguments ctx) {}

    @Override
    public void exitTypeParameter(TypeParameter ctx) {}

    @Override
    public void exitTypeParameters(TypeParameters ctx) {}

    @Override
    public void exitTypeVariableSignature(TypeVariableSignature ctx) {}

    @Override
    public void visitIdentifier(Identifier ctx) {}

    @Override
    public void enterClassTypeSignatureSuffix(ClassTypeSignatureSuffix ctx) {}

    @Override
    public void enterFieldDescriptor(FieldDescriptor ctx) {}

    @Override
    public void enterFieldSignature(FieldSignature ctx) {}

    @Override
    public void enterFieldType(FieldType ctx) {}

    @Override
    public void exitClassTypeSignatureSuffix(ClassTypeSignatureSuffix ctx) {}

    @Override
    public void exitFieldDescriptor(FieldDescriptor ctx) {}

    @Override
    public void exitFieldSignature(FieldSignature ctx) {}

    @Override
    public void exitFieldType(FieldType ctx) {}

    @Override
    public void visitBaseType(BaseType ctx) {}

    @Override
    public void enterArrayType(ArrayType ctx) {}

    @Override
    public void enterComponentType(ComponentType ctx) {}

    @Override
    public void exitComponentType(ComponentType ctx) {}

    @Override
    public void exitArrayType(ArrayType ctx) {}

    @Override
    public void visitObjectType(ObjectType ctx) {}

    @Override
    public void enterJavaTypeSignature(JavaTypeSignature ctx) {}

    @Override
    public void enterMethodDescriptor(MethodDescriptor ctx) {}

    @Override
    public void enterMethodSignature(MethodSignature ctx) {}

    @Override
    public void enterParameterDescriptor(ParameterDescriptor ctx) {}

    @Override
    public void enterResult(Result ctx) {}

    @Override
    public void enterReturnDescriptor(ReturnDescriptor ctx) {}

    @Override
    public void exitJavaTypeSignature(JavaTypeSignature ctx) {}

    @Override
    public void exitMethodDescriptor(MethodDescriptor ctx) {}

    @Override
    public void exitMethodSignature(MethodSignature ctx) {}

    @Override
    public void exitParameterDescriptor(ParameterDescriptor ctx) {}

    @Override
    public void exitResult(Result ctx) {}

    @Override
    public void exitReturnDescriptor(ReturnDescriptor ctx) {}

    @Override
    public void visitVoidDescriptor(VoidDescriptor ctx) { }
}
