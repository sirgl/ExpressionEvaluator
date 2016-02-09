import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class LexerTestSimple {
    private final Lexeme lexeme;
    private final Lexer lexer;

    public LexerTestSimple(Params params) {
        this.lexeme = params.lexeme;
        lexer = new Lexer(new StringReader(params.text));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {new Params("+", new Lexeme("+", LexemeType.PLUS))},
                {new Params("-", new Lexeme("-", LexemeType.MINUS))},
                {new Params("*", new Lexeme("*", LexemeType.MULTIPLY))},
                {new Params("/", new Lexeme("/", LexemeType.DIVIDE))},
                {new Params("(", new Lexeme("(", LexemeType.LEFT_BRACKET))},
                {new Params(")", new Lexeme(")", LexemeType.RIGHT_BRACKET))},
                {new Params("123", new Lexeme("123", LexemeType.NUMBER))},
                {new Params("1", new Lexeme("1", LexemeType.NUMBER))},
                {new Params("123(", new Lexeme("123", LexemeType.NUMBER))},
                {new Params("123+", new Lexeme("123", LexemeType.NUMBER))},
                {new Params("123 ", new Lexeme("123", LexemeType.NUMBER))},
                {new Params("", new Lexeme("eof", LexemeType.EOF))},
                {new Params(" ", new Lexeme("eof", LexemeType.EOF))},
                {new Params("^", new Lexeme("^", LexemeType.POWER))}
        });
    }

    @Test
    public void testGetLexeme() throws Exception {
        assertThat(lexer.getLexeme())
                .isEqualTo(lexeme);
    }

    public static class Params {
        public String text;
        public Lexeme lexeme;

        public Params(String text, Lexeme lexeme) {
            this.text = text;
            this.lexeme = lexeme;
        }
    }
}