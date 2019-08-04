package org.dubok.classfile.signature;
import org.dubok.classfile.signature.tree.*;

public interface SignatureVisitor {
    void enter(SignatureTree ctx);
    void exit(SignatureTree ctx);
    void visit(SignatureTree ctx);

    void enterClassBound(ClassBound ctx);
    void enterClassSignature(ClassSignature ctx);
    void enterClassTypeSignature(ClassTypeSignature ctx);
    void enterInterfaceBound(InterfaceBound ctx);
    void enterPackageSpecifier(PackageSpecifier ctx);
    void enterReferenceTypeSignature(ReferenceTypeSignature ctx);
    void enterSimpleClassTypeSignature(SimpleClassTypeSignature ctx);
    void enterSuperclassSignature(SuperclassSignature ctx);
    void enterSuperinterfaceSignature(SuperinterfaceSignature ctx);
    void enterTypeArgument(TypeArgument ctx);
    void enterTypeArguments(TypeArguments ctx);
    void enterTypeVariableSignature(TypeVariableSignature ctx);
    void enterTypeParameter(TypeParameter ctx);
    void enterTypeParameters(TypeParameters ctx);

    void exitClassBound(ClassBound ctx);
    void exitClassSignature(ClassSignature ctx);
    void exitClassTypeSignature(ClassTypeSignature ctx);
    void exitInterfaceBound(InterfaceBound ctx);
    void exitPackageSpecifier(PackageSpecifier ctx);
    void exitReferenceTypeSignature(ReferenceTypeSignature ctx);
    void exitSimpleClassTypeSignature(SimpleClassTypeSignature ctx);
    void exitSuperclassSignature(SuperclassSignature ctx);
    void exitSuperinterfaceSignature(SuperinterfaceSignature ctx);
    void exitTypeArgument(TypeArgument ctx);
    void exitTypeArguments(TypeArguments ctx);
    void exitTypeVariableSignature(TypeVariableSignature ctx);
    void exitTypeParameter(TypeParameter ctx);
    void exitTypeParameters(TypeParameters ctx);

    void visitIdentifier(Identifier ctx);

    void enterClassTypeSignatureSuffix(ClassTypeSignatureSuffix ctx);
    void enterFieldDescriptor(FieldDescriptor ctx);
    void enterFieldSignature(FieldSignature ctx);
    void enterFieldType(FieldType ctx);
    void exitClassTypeSignatureSuffix(ClassTypeSignatureSuffix ctx);
    void exitFieldDescriptor(FieldDescriptor ctx);
    void exitFieldSignature(FieldSignature ctx);
    void exitFieldType(FieldType ctx);
    void visitBaseType(BaseType ctx);

    void enterArrayType(ArrayType ctx);
    void enterComponentType(ComponentType ctx);
    void exitComponentType(ComponentType ctx);
    void exitArrayType(ArrayType ctx);

    void visitObjectType(ObjectType ctx);

    void enterJavaTypeSignature(JavaTypeSignature ctx);
    void enterMethodDescriptor(MethodDescriptor ctx);
    void enterMethodSignature(MethodSignature ctx);
    void enterParameterDescriptor(ParameterDescriptor ctx);
    void enterResult(Result ctx);
    void enterReturnDescriptor(ReturnDescriptor ctx);

    void exitJavaTypeSignature(JavaTypeSignature ctx);
    void exitMethodDescriptor(MethodDescriptor ctx);
    void exitMethodSignature(MethodSignature ctx);
    void exitParameterDescriptor(ParameterDescriptor ctx);
    void exitResult(Result ctx);
    void exitReturnDescriptor(ReturnDescriptor ctx);

    void visitVoidDescriptor(VoidDescriptor ctx);

}
