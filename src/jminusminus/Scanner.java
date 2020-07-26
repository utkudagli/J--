// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;

import static jminusminus.TokenKind.*;

/**
 * A lexical analyzer for j--, that has no backtracking mechanism.
 * 
 * When you add a new token to the scanner, you must also add an entry in the
 * TokenKind enum in TokenInfo.java specifying the kind and image of the new
 * token.
 */

class Scanner {

    /** End of file character. */
    public final static char EOFCH = CharReader.EOFCH;

    /** Keywords in j--. */
    private Hashtable<String, TokenKind> reserved;

    /** Source characters. */
    private CharReader input;

    /** Next unscanned character. */
    private char ch;

    /** Whether a scanner error has been found. */
    private boolean isInError;

    /** Source file name. */
    private String fileName;

    /** Line number of current token. */
    private int line;

    /**
     * Construct a Scanner object.
     * 
     * @param fileName
     *            the name of the file containing the source.
     * @exception FileNotFoundException
     *                when the named file cannot be found.
     */

    public Scanner(String fileName) throws FileNotFoundException {
        this.input = new CharReader(fileName);
        this.fileName = fileName;
        isInError = false;

        // Keywords in j--
        reserved = new Hashtable<String, TokenKind>();
        reserved.put(ABSTRACT.image(), ABSTRACT);
        reserved.put(BOOLEAN.image(), BOOLEAN);
        reserved.put(CHAR.image(), CHAR);
        reserved.put(CLASS.image(), CLASS);
        reserved.put(ELSE.image(), ELSE);
        reserved.put(EXTENDS.image(), EXTENDS);
        reserved.put(FALSE.image(), FALSE);
        reserved.put(IF.image(), IF);
        reserved.put(IMPORT.image(), IMPORT);
        reserved.put(INSTANCEOF.image(), INSTANCEOF);
        reserved.put(INT.image(), INT);
        reserved.put(NEW.image(), NEW);
        reserved.put(NULL.image(), NULL);
        reserved.put(PACKAGE.image(), PACKAGE);
        reserved.put(PRIVATE.image(), PRIVATE);
        reserved.put(PROTECTED.image(), PROTECTED);
        reserved.put(PUBLIC.image(), PUBLIC);
        reserved.put(RETURN.image(), RETURN);
        reserved.put(STATIC.image(), STATIC);
        reserved.put(SUPER.image(), SUPER);
        reserved.put(THIS.image(), THIS);
        reserved.put(TRUE.image(), TRUE);
        reserved.put(VOID.image(), VOID);
        reserved.put(CONTINUE.image(), CONTINUE);
        reserved.put(FOR.image(), FOR);
        reserved.put(SWITCH.image(), SWITCH);
        reserved.put(ASSERT.image(), ASSERT);
        reserved.put(DEFAULT.image(), DEFAULT);
        reserved.put(SYNCHRONIZED.image(), SYNCHRONIZED);
        reserved.put(DO.image(), DO);
        reserved.put(GOTO.image(), GOTO);
        reserved.put(BREAK.image(), BREAK);
        reserved.put(DOUBLE.image(), DOUBLE);
        reserved.put(IMPLEMENTS.image(), IMPLEMENTS);
        reserved.put(THROW.image(), THROW);
        reserved.put(BYTE.image(), BYTE);
        reserved.put(THROWS.image(), THROWS);
        reserved.put(CASE.image(), CASE);
        reserved.put(ENUM.image(), ENUM);
        reserved.put(CATCH.image(), CATCH);
        reserved.put(SHORT.image(), SHORT);
        reserved.put(TRY.image(), TRY);
        reserved.put(FINAL.image(), FINAL);
        reserved.put(INTERFACE.image(), INTERFACE);
        reserved.put(VOLATILE.image(), VOLATILE);
        reserved.put(STRICTFP.image(), STRICTFP);
        reserved.put(LONG.image(), LONG);
        reserved.put(FINALLY.image(), FINALLY);
        reserved.put(CONST.image(), CONST);
        reserved.put(FLOAT.image(), FLOAT);
        reserved.put(NATIVE.image(), NATIVE);
        
        // Prime the pump.
        nextCh();
    }

    /**
     * Scan the next token from input.
     * 
     * @return the the next scanned token.
     */

    public TokenInfo getNextToken() {
        StringBuffer buffer;
        boolean moreWhiteSpace = true;
        while (moreWhiteSpace) {
            while (isWhitespace(ch)) {
                nextCh();
            }
            if (ch == '/') {
                nextCh();
                if (ch == '/') {
                    // CharReader maps all new lines to '\n'
                    while (ch != '\n' && ch != EOFCH) {
                        nextCh();
                    }
                }
                else if (ch == '=') {
                	return new TokenInfo(DIVA, line);
                }
                else if (ch == '*'){
                		nextCh();
                		while (ch != '/' &&  ch != EOFCH) {
                			nextCh();
                		}
                }else {
                    return new TokenInfo(DIV, line);
                }
            } else {
                moreWhiteSpace = false;
            }
        }
        line = input.line();
        switch (ch) {
        case '~':
        	nextCh();
        	return new TokenInfo(BITWISECOMP, line);
        case '?':
        	nextCh();
        	return new TokenInfo(COND, line);
        case '(':
            nextCh();
            return new TokenInfo(LPAREN, line);
        case ')':
            nextCh();
            return new TokenInfo(RPAREN, line);
        case '{':
            nextCh();
            return new TokenInfo(LCURLY, line);
        case '}':
            nextCh();
            return new TokenInfo(RCURLY, line);
        case '[':
            nextCh();
            return new TokenInfo(LBRACK, line);
        case ']':
            nextCh();
            return new TokenInfo(RBRACK, line);
        case ';':
            nextCh();
            return new TokenInfo(SEMI, line);
        case ':':
        	nextCh();
        	return new TokenInfo(COL, line);
        case ',':
            nextCh();
            return new TokenInfo(COMMA, line);
        case '|':
        	nextCh();
        	if (ch =='|') {
        		return new TokenInfo(LOR, line);
        	}else if (ch == '=') {
        		return new TokenInfo(BIOR, line);
        	}else {
        		return new TokenInfo(BOR, line);
        	}	
        case '=':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new TokenInfo(EQUAL, line);
            } else {
                return new TokenInfo(ASSIGN, line);
            }
        case '!':
            nextCh();
            if (ch == '=') {
            	return new TokenInfo(LNOTA, line);
            }else {
            	return new TokenInfo(LNOT, line);
            }
        case '*':
            nextCh();
            if (ch == '=') {
            	return new TokenInfo(STAR_ASSIGN, line);
            }else{
            	return new TokenInfo(STAR, line);
            }
        case '%':
        	nextCh();
        	if ( ch == '=') {
        		return new TokenInfo(MODA, line);
        	}else {
        	return new TokenInfo(MOD, line);
        	}
        case '+':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new TokenInfo(PLUS_ASSIGN, line);
            } else if (ch == '+') {
                nextCh();
                return new TokenInfo(INC, line);
            } else {
                return new TokenInfo(PLUS, line);
            }
        case '-':
            nextCh();
            if (ch == '-') {
                nextCh();
                return new TokenInfo(DEC, line);
            }else if (ch == '=') {
            	return new TokenInfo(MINUS_ASSIGN, line);
            }else if (ch == '>') {
            	return new TokenInfo(ARROW, line);
            }else {
                return new TokenInfo(MINUS, line);
            }
        case '&':
            nextCh();
            if (ch == '&') {
                nextCh();
                return new TokenInfo(LAND, line);
            }else if (ch == '=') {
            	return new TokenInfo(BANDA, line);
            }
            else {
                return new TokenInfo(BAND, line);
            }
        case '^':
        	nextCh();
        	if (ch == '=') {
        		nextCh();
        		return new TokenInfo(BXORA, line);
        	}else {
        		return new TokenInfo(BXOR, line);
        	}
        case '>':
            nextCh();
            if (ch == '=') {
            	return new TokenInfo(GE, line);
            }else if (ch == '>') {
            	nextCh();
            	if (ch == '=') {
            		return new TokenInfo(SRSE, line);
            	}else if (ch == '>') {
            		nextCh();
            		if(ch == '=') {
            			return new TokenInfo(ZFRSE, line);
            		}
            		else {
            			return new TokenInfo(ZFRS, line);
            		}
            	}else {
            		return new TokenInfo(SRS, line);
            	}
            }else {
            return new TokenInfo(GT, line);
            }
        case '<':
            nextCh();
            if (ch == '=') {
                nextCh();
                return new TokenInfo(LE, line);
            }else if (ch =='<') {
            	nextCh();
            	if(ch == '=') {
            		return new TokenInfo(SLSE, line);
            	}
            	else {
            		return new TokenInfo(SLS, line);
            	}
            }else{
                return new TokenInfo(LT, line);
            }
        case '\'':
            buffer = new StringBuffer();
            buffer.append('\'');
            nextCh();
            if (ch == '\\') {
                nextCh();
                buffer.append(escape());
            } else {
                buffer.append(ch);
                nextCh();
            }
            if (ch == '\'') {
                buffer.append('\'');
                nextCh();
                return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
            } else {
                // Expected a ' ; report error and try to
                // recover.
                reportScannerError(ch
                        + " found by scanner where closing ' was expected.");
                while (ch != '\'' && ch != ';' && ch != '\n') {
                    nextCh();
                }
                return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
            }
        case '"':
            buffer = new StringBuffer();
            buffer.append("\"");
            nextCh();
            while (ch != '"' && ch != '\n' && ch != EOFCH) {
                if (ch == '\\') {
                    nextCh();
                    buffer.append(escape());
                } else {
                    buffer.append(ch);
                    nextCh();
                }
            }
            if (ch == '\n') {
                reportScannerError("Unexpected end of line found in String");
            } else if (ch == EOFCH) {
                reportScannerError("Unexpected end of file found in String");
            } else {
                // Scan the closing "
                nextCh();
                buffer.append("\"");
            }
            return new TokenInfo(STRING_LITERAL, buffer.toString(), line);
        case '.':
            nextCh();
            return new TokenInfo(DOT, line);
        case EOFCH:
            return new TokenInfo(EOF, line);
        case '0':
        	buffer = new StringBuffer();
        	buffer.append(ch);
            nextCh();
            if (ch == '.') {
            	buffer.append(ch);
            	nextCh();
            	while(isDigit(ch)) {
            		buffer.append(ch);
            		nextCh();
            	}
            	if(ch == 'f'|| ch == 'F') {
            		buffer.append(ch);
            		nextCh();
            		return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
            	}else 
            		return new TokenInfo(DOUBLE_LITERAL, buffer.toString(),line); 
            }else if(ch == 'x'|| ch == 'X'){
            	buffer = new StringBuffer();
            	nextCh();
            	while(isHexDigit(ch) || ch == '_') {
            		if(ch == '_') {
            			nextCh();
            			continue;
            		}
            		buffer.append(ch);
            		nextCh();
            	}
            	int temp = 0;
            	for (int i = buffer.length()-1; i>=0; i--) {
            		int num;
            		if (buffer.charAt(i) == 'A' || buffer.charAt(i) == 'a') 
            			num = 10;
            		else if (buffer.charAt(i) == 'B' || buffer.charAt(i) == 'b')
            			num = 11;
            		else if (buffer.charAt(i)== 'C' || buffer.charAt(i) == 'c')
            			num = 12;
            		else if (buffer.charAt(i) == 'D' || buffer.charAt(i) == 'd')
            			num = 13;
            		else if (buffer.charAt(i) == 'E' || buffer.charAt(i) == 'e')
            			num = 14;
            		else if (buffer.charAt(i) == 'F' || buffer.charAt(i) == 'f')
            			num = 15;
            		else 
            			num = Integer.parseInt("" + buffer.charAt(i));
            		
            		temp += ((int) Math.pow(16, buffer.length()-1-i)) * num;
            	}
            	if (ch == '1' || ch == 'L') {
            		buffer.append(ch);
            		String s = new String();
            		s = "" + ch;
            		nextCh();
            		return new TokenInfo(LONG_LITERAL, Integer.toString(temp), line);
            	}
            	return new TokenInfo(INT_LITERAL, Integer.toString(temp), line);
            }else if (ch == 'b' || ch == 'B') {
            	buffer = new StringBuffer();
            	nextCh();
            	while(isBinaryDigit(ch) || ch == '_'){
            		if ( ch == '_') {
            			nextCh();
            			continue;
            		}
            		buffer.append(ch);
            		nextCh();
            	}int temp = 0;
            	for(int i = buffer.length()-1; 	i>=0; i--) {
            		int num;
            		num = Integer.parseInt("" + buffer.charAt(i));
            		temp += ((int) Math.pow(2, buffer.length()-1-i)) * num;
            	}
            	if (ch == '1' || ch == 'L') {
            		buffer.append(ch);
            		String s = new String();
            		s ="" + ch;
            		nextCh();
            		return new TokenInfo(LONG_LITERAL, Integer.toString(temp) + s, line);
            	}
            	return new TokenInfo(INT_LITERAL, Integer.toString(temp), line);
            	
            }else {
            	while(isOctalDigit(ch) || ch == '_') {
            		if (ch == '_') {
            			nextCh();
            			continue;
            		}
            		buffer.append(ch);
            		nextCh();
            	}
            }
            int temp = 0;
            for (int i = buffer.length() -1; i >= 0; i--) {
            	int num;
            	num = Integer.parseInt(""+buffer.charAt(i));
            	temp += ((int)Math.pow(8, buffer.length()-1-i)) *num;
            }
            if (ch == '1' || ch == 'L') {
            	buffer.append(ch);
            	String s = new String();
            	s = "" +ch;
            	nextCh();
            	return new TokenInfo(LONG_LITERAL, Integer.toString(temp)+s, line);
            }
            return new TokenInfo(INT_LITERAL, Integer.toString(temp), line);
        
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            buffer = new StringBuffer();
            boolean doubleFlag = false;
            while(isDigit(ch) || ch == '.'){
                if (ch == '.' && doubleFlag != true){
                    doubleFlag = true;
                }
                buffer.append(ch);
                nextCh();
            }
            if(doubleFlag){
                if(ch == 'f' || ch == 'F'){
                    buffer.append(ch);
                    nextCh();
                    return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                }
                if(ch == 'd')
                    buffer.append(ch);
                nextCh();
                return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
            }
            if(ch == 'l' || ch == 'L'){
                buffer.append(ch);
                nextCh();
                return new TokenInfo(LONG_LITERAL, buffer.toString() , line);
            }
            return new TokenInfo(INT_LITERAL, buffer.toString(), line);
        default:
            if (isIdentifierStart(ch)) {
                buffer = new StringBuffer();
                while (isIdentifierPart(ch)) {
                    buffer.append(ch);
                    nextCh();
                }
                String identifier = buffer.toString();
                if (reserved.containsKey(identifier)) {
                    return new TokenInfo(reserved.get(identifier), line);
                } else {
                    return new TokenInfo(IDENTIFIER, identifier, line);
                }
            } else {
                reportScannerError("Unidentified input token: '%c'", ch);
                nextCh();
                return getNextToken();
            }
        }
    }

    /**
     * Scan and return an escaped character.
     * 
     * @return escaped character.
     */

    private String escape() {
        switch (ch) {
        case 'b':
            nextCh();
            return "\\b";
        case 't':
            nextCh();
            return "\\t";
        case 'n':
            nextCh();
            return "\\n";
        case 'f':
            nextCh();
            return "\\f";
        case 'r':
            nextCh();
            return "\\r";
        case '"':
            nextCh();
            return "\"";
        case '\'':
            nextCh();
            return "\\'";
        case '\\':
            nextCh();
            return "\\\\";
        default:
            reportScannerError("Badly formed escape: \\%c", ch);
            nextCh();
            return "";
        }
    }

    /**
     * Advance ch to the next character from input, and update the line number.
     */

    private void nextCh() {
        line = input.line();
        try {
            ch = input.nextChar();
        } catch (Exception e) {
            reportScannerError("Unable to read characters from input");
        }
    }

    /**
     * Report a lexcial error and record the fact that an error has occured.
     * This fact can be ascertained from the Scanner by sending it an
     * errorHasOccurred() message.
     * 
     * @param message
     *            message identifying the error.
     * @param args
     *            related values.
     */

    private void reportScannerError(String message, Object... args) {
        isInError = true;
        System.err.printf("%s:%d: ", fileName, line);
        System.err.printf(message, args);
        System.err.println();
    }
    private boolean isDigit(char c) {
    	return (c >= '0' && c <= 9);
    }
    private boolean isHexDigit(char c) {
        return ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F'));
    }
    
    private boolean isBinaryDigit(char c) {
        return (c >= '0' && c <= '1') ;
    }
    
    private boolean isOctalDigit(char c) {
        return (c >= '0' && c <= '7');
    }

    /**
     * Return true if the specified character is a digit (0-9); false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    

    /**
     * Return true if the specified character is a whitespace; false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isWhitespace(char c) {
        switch (c) {
        case ' ':
        case '\t':
        case '\n': // CharReader maps all new lines to '\n'
        case '\f':
            return true;
        }
        return false;
    }

    /**
     * Return true if the specified character can start an identifier name;
     * false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$');
    }

    /**
     * Return true if the specified character can be part of an identifier name;
     * false otherwise.
     * 
     * @param c
     *            character.
     * @return true or false.
     */

    private boolean isIdentifierPart(char c) {
        return (isIdentifierStart(c) || isDigit(c));
    }

    /**
     * Has an error occurred up to now in lexical analysis?
     * 
     * @return true or false.
     */

    public boolean errorHasOccurred() {
        return isInError;
    }

    /**
     * The name of the source file.
     * 
     * @return name of the source file.
     */

    public String fileName() {
        return fileName;
    }

}

/**
 * A buffered character reader. Abstracts out differences between platforms,
 * mapping all new lines to '\n'. Also, keeps track of line numbers where the
 * first line is numbered 1.
 */

class CharReader {

    /** A representation of the end of file as a character. */
    public final static char EOFCH = (char) -1;

    /** The underlying reader records line numbers. */
    private LineNumberReader lineNumberReader;

    /** Name of the file that is being read. */
    private String fileName;

    /**
     * Construct a CharReader from a file name.
     * 
     * @param fileName
     *            the name of the input file.
     * @exception FileNotFoundException
     *                if the file is not found.
     */

    public CharReader(String fileName) throws FileNotFoundException {
        lineNumberReader = new LineNumberReader(new FileReader(fileName));
        this.fileName = fileName;
    }

    /**
     * Scan the next character.
     * 
     * @return the character scanned.
     * @exception IOException
     *                if an I/O error occurs.
     */

    public char nextChar() throws IOException {
        return (char) lineNumberReader.read();
    }

    /**
     * The current line number in the source file, starting at 1.
     * 
     * @return the current line number.
     */

    public int line() {
        // LineNumberReader counts lines from 0.
        return lineNumberReader.getLineNumber() + 1;
    }

    /**
     * Return the file name.
     * 
     * @return the file name.
     */

    public String fileName() {
        return fileName;
    }

    /**
     * Close the file.
     * 
     * @exception IOException
     *                if an I/O error occurs.
     */

    public void close() throws IOException {
        lineNumberReader.close();
    }

}
