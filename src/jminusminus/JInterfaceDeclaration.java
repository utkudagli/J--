package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

class JInterfaceDeclaration extends JAST implements JTypeDecl{
	private ArrayList<String> mods;
	private String name;
	private ArrayList<JMember> classBlock;
	private Type superType;
	private Type thisType;
    private ClassContext context;
    private ArrayList<JFieldDeclaration> instanceFieldInitializations;
    private ArrayList<JFieldDeclaration> staticFieldInitializations;

    public JInterfaceDeclaration(int line, ArrayList<String> mods, String name,Type superType, ArrayList<JMember> classBlock) {
        super(line);
        this.mods = mods;
        this.name = name;
        this.superType = superType;
        this.classBlock = classBlock;
        instanceFieldInitializations = new ArrayList<JFieldDeclaration>();
        staticFieldInitializations = new ArrayList<JFieldDeclaration>();
    }

    public String name() {
        return name;
    }

    public Type superType() {
        return superType;
    }

    public Type thisType() {
        return thisType;
    }

    public ArrayList<JFieldDeclaration> instanceFieldInitializations() {
        return instanceFieldInitializations;
    }

    public void declareThisType(Context context) {
        String qualifiedName = JAST.compilationUnit.packageName() == "" ? name
                : JAST.compilationUnit.packageName() + "/" + name;
        CLEmitter partial = new CLEmitter(false);
        partial.addClass(mods, qualifiedName, Type.OBJECT.jvmName(), null,
                false); // Object for superClass, just for now
        thisType = Type.typeFor(partial.toClass());
        context.addType(line, thisType);
    }

    public void preAnalyze(Context context) {
        
    }

    public JAST analyze(Context context) {
        
        return this;
    }

    public void codegen(CLEmitter output) {
        
    }

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JInterfaceDeclaration line=\"%d\" name=\"%s\""
                + " super=\"%s\">\n", line(), name, superType.toString());
        p.indentRight();
        if (context != null) {
            context.writeToStdOut(p);
        }
        if (mods != null) {
            p.println("<Modifiers>");
            p.indentRight();
            for (String mod : mods) {
                p.printf("<Modifier name=\"%s\"/>\n", mod);
            }
            p.indentLeft();
            p.println("</Modifiers>");
        }
        if (classBlock != null) {
            p.println("<InterfaceBlock>");
            for (JMember member : classBlock) {
                ((JAST) member).writeToStdOut(p);
            }
            p.println("</InterfaceBlock>");
        }
        p.indentLeft();
        p.println("</JInterfaceDeclaration>");
    }

    private void codegenClassInit(CLEmitter output) {
        
}
}