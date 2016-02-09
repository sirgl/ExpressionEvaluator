import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ParserTest {
    private final int value;
    private final Parser parser;
    private final String text;

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                {new Params("12", 12)},
                {new Params("12 + 2", 14)},
                {new Params("12 + 2 + 4", 18)},
                {new Params("12 - 2", 10)},
                {new Params("12 - 2 + 2", 12)},
                {new Params("12 - 2 - 3", 7)},
                {new Params("12 - - 2 - 3", 11)},
                {new Params("-2 + 3", 1)},

                {new Params("12 / 2", 6)},
                {new Params("12 * 3", 36)},
                {new Params("12 * 3 / 4", 9)},
                {new Params("12 + (6 / 2)", 15)},
                {new Params("12 + (6 / 2)", 15)},
                {new Params("(12 + 6) / 3", 6)},

                {new Params("2 ^ 4", 16)},
                {new Params("(2 + 3) ^ (4 - 4 / 2)", 25)},
                {new Params("(2 + 3) ^ ((4 - 4 / 2) / 2) + 3", 8)}
        });
    }

    public ParserTest(Params params) {
        value = params.value;
        text = params.text;
        parser = new Parser(new Lexer(new StringReader(text)));
    }

    @Test
    public void test() throws Exception {
        assertThat(parser.parse())
                .as("for " + text)
                .isEqualTo(value);
    }

    public static class Params {
        public String text;
        public int value;

        public Params(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }
}