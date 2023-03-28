package lexer;

public enum Tag {
    // Assign
    ASSIGN("ASSIGN"),

    // Identifier
    ID("ID"),

    // Arithmetical Operators
    OR("OR"),

    // Logical Operators
    LT("LT"), LE("LE"), GT("GT"),

    // Others
    EOF("EOF"), UNK("UNK"),

    // Math Operations
    MUL("MUL"), SUM("SUM"), SUB("SUB"),

    LIT_INT("LIT_INT"), LIT_REAL("LIT_REAL"),

    PROGRAM("PROGRAM"), BEGIN("BEGIN"), END("END"),

    COMMENTS("COMMENTS"), DIV("DIV");

    private final String name;

    private Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Tag nameToTag(char name) {
        return nameToTag(Character.toString(name));
    }

    public static Tag nameToTag(String name) {
        for (Tag tag : Tag.values()) {
            if (tag.name.equals(name))
                return tag;
        }

        return Tag.UNK;
    }

}
