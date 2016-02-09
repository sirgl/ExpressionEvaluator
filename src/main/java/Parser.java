import java.io.IOException;

public class Parser {
    private final Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int parse() throws IOException {
        currentLexeme = lexer.getLexeme();
        return parseExpression();
    }

    private int parseExpression() throws IOException {
        int left = parseTerm();
        LexemeType type = currentLexeme.getType();
        while (type == LexemeType.MINUS || type == LexemeType.PLUS) {
            currentLexeme = lexer.getLexeme();
            int right = parseTerm();
            if(type == LexemeType.MINUS) {
                left -= right;
            } else {
                left += right;
            }
            type = currentLexeme.getType();
        }
        return left;
    }

    private int parseTerm() throws IOException {
        int left = parseFactor();
        LexemeType type = currentLexeme.getType();
        while (type == LexemeType.MULTIPLY || type == LexemeType.DIVIDE) {
            currentLexeme = lexer.getLexeme();
            int right = parseFactor();
            if(type == LexemeType.MULTIPLY) {
                left *= right;
            } else {
                left /= right;
            }
            type = currentLexeme.getType();
        }
        return left;
    }

    private int parseFactor() throws IOException {
        LexemeType type = currentLexeme.getType();
        if(type == LexemeType.LEFT_BRACKET) {
            currentLexeme = lexer.getLexeme();
            int value = parseExpression();
            if(currentLexeme.getType() != LexemeType.RIGHT_BRACKET) {
                throw new ParserException(") expected but found " + currentLexeme.getType());
            }
            currentLexeme = lexer.getLexeme();
            if(currentLexeme.getType() == LexemeType.POWER) {
                currentLexeme = lexer.getLexeme();
                return (int) Math.round(Math.pow(value, parseFactor()));
            }
            return value;
        }
        int power = parsePower();
        if(currentLexeme.getType() == LexemeType.POWER) {
            currentLexeme = lexer.getLexeme();
            return (int) Math.round(Math.pow(power, parseFactor()));
        }
        return power;
    }

    private int parsePower() throws IOException {
        try {
            if (currentLexeme.getType() == LexemeType.MINUS) {
                currentLexeme = lexer.getLexeme();
                return -Integer.parseInt(currentLexeme.getLexeme());
            }
            return Integer.parseInt(currentLexeme.getLexeme());
        } finally {
            currentLexeme = lexer.getLexeme();
        }
    }
}
