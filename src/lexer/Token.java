package lexer;

public class Token {
    private final Tag tag;
    private final String lexema;

    public Token(Tag tag, String lexema) {
        this.tag = tag;
        this.lexema = lexema;
    }

    public Tag tag() {
        return tag;
    }

    public String lexema() {
        return lexema;
    }

    @Override
    public String toString() {
        return String.format("<%s, \"%s\">", tag, lexema);
    }

}
