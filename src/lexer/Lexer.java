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
        if (Character.isSpaceChar(EOF_CHAR))
            if (peek == '\n' || peek == '\r')
                line++;

        try {
            peek = (char) reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peek;
    }

    private void ignoreCommentsMultiline() {
        char lastCharacter = ' ';
        while (lastCharacter == '*' && peek == '/') {
            lastCharacter = peek;
            nextChar();
        }
    }

    private void ignoreCommentsSingleline() {
        while (peek != '\n' && peek != '\r')
            nextChar();
    }

    private Token getTokenIgnoringComments() {
        if (peek == '/') {
            ignoreCommentsSingleline();
            return nextToken();
        } else if (peek == '*') {
            ignoreCommentsMultiline();
            return nextToken();
        }

        return new Token(Tag.DIV, "/");
    }

    public Token nextToken() {
        while (Character.isWhitespace(peek))
            nextChar();

        switch (peek) {
            case '=':
            case '+':
            case '-':
            case '*':
            case '|':
            case '>':
                nextChar();
                return Tokens.getTokenByLexem(peek);
            case '<':
                nextChar();
                if (peek == '=') {
                    nextChar();
                    return Tokens.getTokenByLexem("<=");
                }
                return Tokens.getTokenByLexem("<");
            case '/':
                nextChar();
                return getTokenIgnoringComments();
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
