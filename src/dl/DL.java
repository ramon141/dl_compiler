package dl;

import java.io.File;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class DL {
    public static void main(String[] args) {
        Lexer l = new Lexer(new File("prog.dl"));

        Token t = l.nextToken();
        while (t.tag() != Tag.EOF) {
            System.out.println(t);
            t = l.nextToken();
        }
    }
}
