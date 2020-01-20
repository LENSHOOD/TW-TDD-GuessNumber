package zxh.demo.tw.guess.number;

import org.junit.jupiter.api.Test;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * zxh.demo.tw.guess.number.GuessNumberTest:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumberTest {
    @Test
    public void should_generate_4_digits_number_when_build() {
        GuessNumber guessNumber = new GuessNumber();
        assertEquals(4, guessNumber.generateSecretNumber().length);
    }

    @Test
    public void should_generate_4_digits_number_every_digit_from_0_to_9() {
        GuessNumber guessNumber = new GuessNumber();

        int[] guessed = guessNumber.generateSecretNumber();

        IntPredicate isFrom0To9 = i -> i >= 0 && i <= 9;
        IntStream.of(guessed).forEach(i -> assertTrue(isFrom0To9.test(i)));
    }

    @Test
    public void should_generate_4_digits_number_with_no_duplicate_digit() {
        GuessNumber guessNumber = new GuessNumber();

        int[] guessed = guessNumber.generateSecretNumber();

        assertEquals(4, IntStream.of(guessed).distinct().toArray().length);
    }
}
