package jminusminus;

import static jminusminus.CLConstants.*;

class JContinue extends JStatement{
    public JContinue (int line){
        super(line);
    }
    public JContinue analyze(Context context){
        return this;
    }
    public void codegen(CLEmitter output) {
    }
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JContinue line=\"%d\">\n", line());
        p.printf("</JContinue>\n");
    }
}