package jminusminus;

import static jminusminus.CLConstants.*;

class JEnhancedForStatement extends JStatement{
	private JVariableDeclaration forInit;
	private JStatement body;
	private JExpression expression;
	private JVariableDeclaration update;

	public JEnhancedForStatement(int line, JVariableDeclaration forInit, JExpression expression, JStatement body, JVariableDeclaration update){
		super(line);
		this.forInit = forInit;
		this.expression = expression;
		this.body = body;
		this.update = update;
	}
	
	public JStatement analyze (Context context){
		return this;
	}
	public void codegen(CLEmitter output){

	}
	public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JEnhancedForStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<TestExpression>\n");
        p.indentRight();
        forInit.writeToStdOut(p);
        expression.writeToStdOut(p);
        update.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JEnhancedForStatement>\n");
    }
}