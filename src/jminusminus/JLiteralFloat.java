package jminusminus;	

import static jminusminus.CLConstants.*;

class JLiteralFloat extends JExpression{
	private String text;

	public JLiteralFloat(int line, String text){
		super(line);
		this.text = text;
	}

	public JExpression analyze(Context Context){
		return this;
	}
	public void codegen(CLEmitter output){
		

	}

	public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JLiteralFloat line=\"%d\" type=\"%s\" " + "value=\"%s\"/>\n",
                line(), ((type == null) ? "" : type.toString()), text);
    }
}