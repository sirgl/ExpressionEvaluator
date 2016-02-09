public class Lexeme {
    private final String lexeme;
    private final LexemeType type;

    public Lexeme(String lexeme, LexemeType type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lexeme lexeme1 = (Lexeme) o;

        if (!lexeme.equals(lexeme1.lexeme)) return false;
        return type == lexeme1.type;

    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexeme='" + lexeme + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode() {
        int result = lexeme.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public String getLexeme() {
        return lexeme;
    }

    public LexemeType getType() {
        return type;
    }
}
