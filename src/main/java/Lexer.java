import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    private final PushbackReader reader;
    private static final Map<LexemeType, Lexeme> lexemeMap;

    static {
        lexemeMap = new HashMap<LexemeType, Lexeme>();
        lexemeMap.put(LexemeType.MINUS, new Lexeme("-", LexemeType.MINUS));
        lexemeMap.put(LexemeType.PLUS, new Lexeme("+", LexemeType.PLUS));
        lexemeMap.put(LexemeType.DIVIDE, new Lexeme("/", LexemeType.DIVIDE));
        lexemeMap.put(LexemeType.MULTIPLY, new Lexeme("*", LexemeType.MULTIPLY));
        lexemeMap.put(LexemeType.POWER, new Lexeme("^", LexemeType.POWER));
        lexemeMap.put(LexemeType.LEFT_BRACKET, new Lexeme("(", LexemeType.LEFT_BRACKET));
        lexemeMap.put(LexemeType.RIGHT_BRACKET, new Lexeme(")", LexemeType.RIGHT_BRACKET));
        lexemeMap.put(LexemeType.EOF, new Lexeme("eof", LexemeType.EOF));
    }

    public Lexer(Reader reader) {
        this.reader = new PushbackReader(reader);
    }

    public Lexeme getLexeme() throws IOException {
        int ch = getCharSkippingSpaces();
        if(Character.isDigit(ch)){
            return parseNumber(ch);
        }
        switch (ch) {
            case '+':
                return lexemeMap.get(LexemeType.PLUS);
            case '-':
                return lexemeMap.get(LexemeType.MINUS);
            case '*':
                return lexemeMap.get(LexemeType.MULTIPLY);
            case '/':
                return lexemeMap.get(LexemeType.DIVIDE);
            case '^':
                return lexemeMap.get(LexemeType.POWER);
            case '(':
                return lexemeMap.get(LexemeType.LEFT_BRACKET);
            case ')':
                return lexemeMap.get(LexemeType.RIGHT_BRACKET);
            case -1:
                return lexemeMap.get(LexemeType.EOF);
            default:
                throw new LexerException("Bad lexeme");
        }
    }

    private Lexeme parseNumber(int ch) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append((char)ch);
        while (true) {
            ch = reader.read();
            if(!Character.isDigit(ch)) {
                if (ch != -1) {
                    reader.unread(ch);
                }
                break;
            }
            char ch1 = (char) ch;
            builder.append(ch1);
        }
        return new Lexeme(builder.toString(), LexemeType.NUMBER);
    }

    private int getCharSkippingSpaces() throws IOException {
        int ch;
        do {
            ch = reader.read();
        } while (Character.isSpaceChar(ch));
        return ch;
    }
}
