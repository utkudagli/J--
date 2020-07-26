package jminusminus;

import static jminusminus.CLConstants.*;

class JDoWhileStatement extends JStatement{
	private JStatement condition;
	private JStatement body;
	
	public JDoWhileStatement(int line, JStatement condition, JStatement body){
		super(line);
		this.condition = condition;
		this.body = body;
	}
	public JDoWhileStatement analyze(Context context){
		return this;
	}
	public void codegen(CLEmitter output){

	}
	public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JDoWhileStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("</JWhileStatement>\n");
    }
}