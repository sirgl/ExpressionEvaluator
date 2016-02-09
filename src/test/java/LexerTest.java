import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerTest {
    @Test
    public void testParseMultipleLexemes() throws Exception {
        Lexer lexer = new Lexer(new StringReader("-+"));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("-", LexemeType.MINUS));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("+", LexemeType.PLUS));
    }

    @Test
    public void testParseNumberNotRemoveLastCharEof() throws Exception {
        Lexer lexer = new Lexer(new StringReader("123"));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("123", LexemeType.NUMBER));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("eof", LexemeType.EOF));
    }

    @Test
    public void testParseNumberNotRemoveLastChar() throws Exception {
        Lexer lexer = new Lexer(new StringReader("123+"));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("123", LexemeType.NUMBER));
        assertThat(lexer.getLexeme()).isEqualTo(new Lexeme("+", LexemeType.PLUS));
    }
}
