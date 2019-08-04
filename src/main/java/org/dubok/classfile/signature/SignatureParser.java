package org.dubok.classfile.signature;

import org.dubok.classfile.signature.tree.*;

public final class SignatureParser {

    public SignatureParser(String signature) {
        this.signature = signature.toCharArray();
        position = 0;
    }

    public ClassSignature parseClassSignature() {
        errorMessage = "Invalid class signature: " + new String(signature);
        ClassSignature classSignature = new ClassSignature();
        if(look()=='<') {
            classSignature.children.add(parseTypeParameters(classSignature));
        }

        classSignature.children.add(parseSuperclassSignature(classSignature));

        while(!end()) {
            classSignature.children.add(parseSuperinterfaceSignature(classSignature));
        }

        return classSignature;
    }

    public FieldSignature parseFieldSignature() {
        errorMessage = "Invalid class signature: " + new String(signature);
        FieldSignature fieldSignature = new FieldSignature();
        fieldSignature.children.add(parseReferenceTypeSignature(fieldSignature));
        return fieldSignature;
    }

    public MethodSignature parseMethodSignature() {
        errorMessage = "Invalid method signature: " + new String(signature);
        MethodSignature methodSignature = new MethodSignature();
        if(look()=='<') {
            methodSignature.children.add(parseTypeParameters(methodSignature));
        }
        eat('(');
        while(')'!= look()) {
            methodSignature.children.add(parseJavaTypeSignature(methodSignature));
        }
        eat(')');
        methodSignature.children.add(parseResult(methodSignature));
        if(!end()) {
            methodSignature.children.add(parseThrowsSignature(methodSignature));
        }

        return methodSignature;
    }

    public FieldDescriptor parseFieldDescriptor() {
        errorMessage = "Invalid field descriptor: " + new String(signature);
        FieldDescriptor fieldDescriptor = new FieldDescriptor();
        SignatureTree fieldType = parseFieldType(fieldDescriptor);
        fieldDescriptor.children.add(fieldType);
        return fieldDescriptor;
    }

    public SignatureTree parseMethodDescriptor() {
        errorMessage = "Invalid method descriptor: " + new String(signature);
        try {
            MethodDescriptor methodDescriptor = new MethodDescriptor();
            eat('(');
            while (look() != ')') {
                methodDescriptor.children.add(parseParameterDescriptor(methodDescriptor));
            }
            eat(')');
            methodDescriptor.children.add(parseReturnDescriptor(methodDescriptor));
            return methodDescriptor;
        } catch (Throwable ex) {
            if(ex instanceof ClassFormatError) throw(ex);
            throw new ClassFormatError("" + position + errorMessage + ex.getStackTrace());
        }
    }

    private int position;
    private final char[] signature;
    private String errorMessage;

    private ArrayType parseArrayType(SignatureTree parent) {
        ArrayType arrayType = new ArrayType(parent);
        eat('[');
        arrayType.children.add(parseComponentType(arrayType));
        return arrayType;
    }

    private ArrayTypeSignature parseArrayTypeSignature(SignatureTree parent) {
        ArrayTypeSignature arrayTypeSignature = new ArrayTypeSignature(parent);
        eat('[');
        arrayTypeSignature.children.add(parseJavaTypeSignature(arrayTypeSignature));
        return arrayTypeSignature;
    }

    private BaseType parseBaseType(SignatureTree parent) {
        BaseType baseType = new BaseType(parent);
        baseType.type = "" + look();
        advance();
        return baseType;
    }

    private ClassBound parseClassBound(SignatureTree parent) {
        ClassBound classBound = new ClassBound(parent);
        eat(':');
        classBound.children.add(parseReferenceTypeSignature(classBound));
        return classBound;
    }

    private ClassTypeSignature parseClassTypeSignature(SignatureTree parent) {
        ClassTypeSignature classTypeSignature = new ClassTypeSignature(parent);
        eat('L');

        int oldPosition = position;
        parseIdentifier(classTypeSignature); //lookahead
        char ch = look();
        position = oldPosition;

        if(ch=='/') {
            classTypeSignature.children.add(parsePackageSpecifier(classTypeSignature));
        }

        classTypeSignature.children.add(parseSimpleClassTypeSignature(classTypeSignature));

        while(look()=='.') {
            classTypeSignature.children.add(parseClassTypeSignatureSuffix(classTypeSignature));
        }

        eat(';');
        return classTypeSignature;
    }

    private ClassTypeSignatureSuffix parseClassTypeSignatureSuffix (SignatureTree parent) {
        ClassTypeSignatureSuffix classTypeSignatureSuffix = new ClassTypeSignatureSuffix(parent);
        eat('.');
        classTypeSignatureSuffix.children.add(parseSimpleClassTypeSignature(classTypeSignatureSuffix));
        return classTypeSignatureSuffix;
    }

    private ComponentType parseComponentType(SignatureTree parent) {
        ComponentType componentType = new ComponentType(parent);
        componentType.children.add(parseFieldType(componentType));
        return componentType;
    }

    private FieldType parseFieldType(SignatureTree parent) {
        FieldType fieldType = new FieldType(parent);
        switch (look()) {
            case 'L': fieldType.children.add(parseObjectType(fieldType));break ;
            case '[': fieldType.children.add(parseArrayType(fieldType));break;
            case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'Z': case 'S':
                fieldType.children.add(parseBaseType(fieldType)); break;
            default: System.out.println(look());error();
        }
        return fieldType;
    }

    private Identifier parseIdentifier(SignatureTree parent) {
        final Identifier identifier = new Identifier(parent);
        final String terminators = ".;[/<>:";
        final StringBuilder id = new StringBuilder();
        for(;-1== terminators.indexOf(look());advance()) {
            id.append(look());
        }
        identifier.identifier = id.toString();
        return identifier;
    }

    private InterfaceBound parseInterfaceBound(SignatureTree parent) {
        InterfaceBound interfaceBound = new InterfaceBound(parent);
        interfaceBound.children.add(parseReferenceTypeSignature(interfaceBound));
        return interfaceBound;
    }

    private JavaTypeSignature parseJavaTypeSignature(SignatureTree parent) {
        JavaTypeSignature javaTypeSignature = new JavaTypeSignature(parent);
        switch (look()) {
            case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z':
                javaTypeSignature.children.add(parseBaseType(javaTypeSignature));
                break;
            default:
                javaTypeSignature.children.add(parseReferenceTypeSignature(javaTypeSignature));
        }
        return javaTypeSignature;
    }

    private ObjectType parseObjectType(SignatureTree parent) {
        ObjectType objectType = new ObjectType(parent);
        advance();
        StringBuilder type = new StringBuilder();
        for (;look()!=';';advance()) {
            type.append(signature[position]);
        }
        eat(';');
        objectType.type = type.toString();
        return objectType;
    }

    private PackageSpecifier parsePackageSpecifier(SignatureTree parent) {
        PackageSpecifier packageSpecifier = new PackageSpecifier(parent);

        while(true) {
            int oldPosition = position;
            Identifier identifier = parseIdentifier(packageSpecifier);
            if(look()=='/') {
                eat('/');
                packageSpecifier.children.add(identifier);
            } else {
                position = oldPosition;
                break;
            }
        }
        return packageSpecifier;
    }

    private ParameterDescriptor parseParameterDescriptor(SignatureTree parent) {
        ParameterDescriptor parameterDescriptor = new ParameterDescriptor(parent);
        parameterDescriptor.children.add(parseFieldType(parameterDescriptor));
        return parameterDescriptor;
    };

    private ReferenceTypeSignature parseReferenceTypeSignature(SignatureTree parent) {
        ReferenceTypeSignature referenceTypeSignature = new ReferenceTypeSignature(parent);
        switch (look()) {
            case 'L': referenceTypeSignature.children.add(parseClassTypeSignature(referenceTypeSignature)); break;
            case 'T': referenceTypeSignature.children.add(parseTypeVariableSignature(referenceTypeSignature)); break;
            case '[': referenceTypeSignature.children.add(parseArrayTypeSignature(referenceTypeSignature)); break;
            default: error();
        }
        return referenceTypeSignature;
    }

    private ReturnDescriptor parseReturnDescriptor(SignatureTree parent) {
        ReturnDescriptor returnDescriptor = new ReturnDescriptor();
        if(look()=='V') {
            returnDescriptor.children.add(parseVoidDescriptor(returnDescriptor));
        } else {
            returnDescriptor.children.add(parseFieldType(returnDescriptor));
        }
        return returnDescriptor;
    };

    private SignatureTree parseResult(SignatureTree parent) {
        Result result = new Result(parent);
        if(look()=='V') {
            result.children.add(parseVoidDescriptor(result));
        } else {
            result.children.add(parseJavaTypeSignature(result));
        }
        return result;
    };

    private SimpleClassTypeSignature parseSimpleClassTypeSignature(SignatureTree parent) {
        SimpleClassTypeSignature simpleClassTypeSignature = new SimpleClassTypeSignature(parent);
        simpleClassTypeSignature.children.add(parseIdentifier(simpleClassTypeSignature));
        if(look()=='<') {
            simpleClassTypeSignature.children.add(parseTypeArguments(simpleClassTypeSignature));
        }
        return simpleClassTypeSignature;
    }

    private Star parseStar(SignatureTree parent) {
        Star star = new Star(parent);
        eat('*');
        return star;
    };

    private SuperclassSignature parseSuperclassSignature(SignatureTree parent) {
        SuperclassSignature superclassSignature = new SuperclassSignature(parent);
        superclassSignature.children.add(parseClassTypeSignature(superclassSignature));
        return superclassSignature;
    }

    private SuperinterfaceSignature parseSuperinterfaceSignature(SignatureTree parent) {
        SuperinterfaceSignature superinterfaceSignature = new SuperinterfaceSignature(parent);
        superinterfaceSignature.children.add(parseClassTypeSignature(superinterfaceSignature));
        return superinterfaceSignature;
    }

    private ThrowsSignature parseThrowsSignature(SignatureTree parent) {
        ThrowsSignature throwsSignature = new ThrowsSignature(parent);
        eat('^');
        while(!end()) {
            switch (look()) {
                case 'T': throwsSignature.children.add(parseTypeVariableSignature(throwsSignature)); break;
                default:
                    throwsSignature.children.add(parseClassTypeSignature(throwsSignature));
            }
        }
        return throwsSignature;
    };

    private TypeArgument parseTypeArgument(SignatureTree parent) {
        TypeArgument typeArgument = new TypeArgument(parent);
        switch (look()) {
            case '*': typeArgument.children.add(parseStar(typeArgument));return typeArgument;
            case '+': case '-': typeArgument.children.add(parseWildcardIndicator(parent));
        }
        typeArgument.children.add(parseReferenceTypeSignature(parent));
        return typeArgument;
    }

    private TypeArguments parseTypeArguments(SignatureTree parent) {
        TypeArguments typeArguments = new TypeArguments(parent);
        eat('<');
        do {
            typeArguments.children.add(parseTypeArgument(typeArguments));
        } while(look()!='>');
        eat('>');
        return typeArguments;
    }

    private TypeParameter parseTypeParameter(SignatureTree parent) {
        TypeParameter typeParameter = new TypeParameter(parent);
        typeParameter.children.add(parseIdentifier(typeParameter));

        int oldPosition = position;
        eat(':');
        char ch = look();
        position = oldPosition;

        if(ch != ':') {
            typeParameter.children.add(parseClassBound(typeParameter));
        } else {
            eat(':');
        }

        while(':' == look()) {
            eat(':');
            typeParameter.children.add(parseInterfaceBound(typeParameter));
        }
        return typeParameter;
    }

    private TypeParameters parseTypeParameters(SignatureTree parent) {
        TypeParameters typeParameters = new TypeParameters(parent);
        eat('<');
        boolean first = true;
        do {
            typeParameters.children.add(parseTypeParameter(typeParameters));
        } while (look()!='>');
        eat('>');
        return typeParameters;
    };

    private TypeVariableSignature parseTypeVariableSignature(SignatureTree parent) {
        TypeVariableSignature typeVariableSignature = new TypeVariableSignature(parent);
        eat('T');
        typeVariableSignature.children.add(parseIdentifier(typeVariableSignature));
        eat(';');
        return typeVariableSignature;
    };

    private VoidDescriptor parseVoidDescriptor(SignatureTree parent) {
        VoidDescriptor voidDescriptor = new VoidDescriptor(parent);
        eat('V');
        return voidDescriptor;
    };

    private WildcardIndicator parseWildcardIndicator(SignatureTree parent) {
        WildcardIndicator wildcardIndicator= new WildcardIndicator(parent);
        wildcardIndicator.indicator = "" + look();
        advance();
        return wildcardIndicator;
    }

    private char look() {
        return signature[position];
    }
    private void error() {
        throw new ClassFormatError(" " + position + " " +errorMessage);
    }
    boolean end() {
        return  position == signature.length;
    }
    private void eat(char ch) {
        if(look() != ch) error();
        advance();
    }
    private void advance() {
        position++;
        if (position > signature.length) error();
    }
}
