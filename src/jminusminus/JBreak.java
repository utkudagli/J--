package jminusminus;

import static jminusminus.CLConstants.*;

class JBreak extends JStatement{
    public JBreak (int line){
        super(line);
    }
    public JBreak analyze(Context context){
        return this;
    }
    public void codegen(CLEmitter output) {
    }
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBreak line=\"%d\">\n", line());
        p.printf("</JBreak>\n");
    }
}