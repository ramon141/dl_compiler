package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    private static final char EOF_CHAR = (char) -1;

    private static int line = 1;
    private BufferedReader reader;
    private char peek;
    private Hashtable<String, Tag> keywords;

    public Lexer(File file) {
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (Exception err) {
            err.printStackTrace();
        }

        this.peek = ' ';
        keywords = new Hashtable<>();
        keywords.put("programa", Tag.PROGRAM);
        keywords.put("inicio", Tag.BEGIN);
        keywords.put("fim", Tag.END);
    }

    public static int line() {
        return line;
    }

    public char nextChar() {
        if (peek == '\n' || peek == '\r')
            line++;

        try {
            peek = (char) reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peek;
    }

    private static boolean isWhitespace(int c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public Token nextToken() {
        while (isWhitespace(peek))
            nextChar();

        switch (peek) {
            case '=':
                nextChar();
                return new Token(Tag.ASSIGN, "=");
            case '+':
                nextChar();
                return new Token(Tag.SUM, "+");
            case '-':
                nextChar();
                return new Token(Tag.SUB, "-");
            case '*':
                nextChar();
                return new Token(Tag.MUL, "*");
            case '|':
                nextChar();
                return new Token(Tag.OR, "|");
            case '<':
                nextChar();
                if (peek == '=') {
                    nextChar();
                    return new Token(Tag.LE, "<=");
                }
                return new Token(Tag.LT, "<");
            case '>':
                nextChar();
                return new Token(Tag.LT, ">");
            case '/':
                nextChar();
                if (peek == '/') {
                    while (peek != '\n' || peek != '\r') {
                        nextChar();
                    }
                    return new Token(Tag.COMMENTS, "/");
                }
                return new Token(Tag.DIV, "/");
            case EOF_CHAR:
                nextChar();
                return new Token(Tag.EOF, "");
            default:
                if (Character.isDigit(peek)) {
                    boolean isFloat = false;
                    StringBuilder builder = new StringBuilder();
                    while (Character.isDigit(peek) || peek == '.') {
                        if (!isFloat)
                            isFloat = peek == '.';
                        builder.append(peek);
                        nextChar();
                    }

                    return new Token(isFloat ? Tag.LIT_REAL : Tag.LIT_INT, builder.toString());
                }

                if (Character.isJavaIdentifierStart(peek)) {
                    StringBuilder builder = new StringBuilder(peek);
                    while (Character.isJavaIdentifierPart(peek)) {
                        builder.append(peek);
                        nextChar();
                    }

                    String id = builder.toString();
                    Tag tag = keywords.get(id);
                    if (tag != null) {
                        return new Token(tag, id);
                    }

                    return new Token(Tag.ID, id);
                }

        }

        String unk = String.valueOf(peek);
        nextChar();
        return new Token(Tag.UNK, unk);
    }

}
