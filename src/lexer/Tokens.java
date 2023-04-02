package lexer;

public class Tokens {

  public static final Token[] TOKENS = {
      new Token(Tag.ASSIGN, "="),
      new Token(Tag.SUM, "+"),
      new Token(Tag.SUB, "-"),
      new Token(Tag.MUL, "*"),
      new Token(Tag.OR, "|"),
      new Token(Tag.LE, "<="),
      new Token(Tag.LT, "<"),
      new Token(Tag.LT, ">"),
      new Token(Tag.DIV, "/"),
      new Token(Tag.LE, "<="),
      new Token(Tag.LT, "<"),
      new Token(Tag.EOF, "")
  };

  public static Token getTokenByLexem(char lexem) {
    return getTokenByLexem(String.valueOf(lexem));
  }

  public static Token getTokenByLexem(String lexem) {
    for (Token token : TOKENS) {
      if (token.lexema().equals(lexem))
        return token;
    }
    return new Token(Tag.UNK, lexem);
  }
}
