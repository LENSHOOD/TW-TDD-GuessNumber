package zxh.demo.tw.guess.number;

import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * zxh.demo.tw.guess.number.GuessNumber:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumber {
    private static final Random RANDOM = new Random();
    public static final int SECRET_LEN = 4;
    private int[] secretNumber;

    void prepareToGuess() {
        secretNumber = generateSecretNumber();
    }

    int[] generateSecretNumber() {
        Set<Integer> secretSet = Sets.newTreeSet();
        do {
            secretSet.add(RANDOM.nextInt(10));
        } while (secretSet.size() != SECRET_LEN);

        return secretSet.stream().mapToInt(i -> i).toArray();
    }

    public GuessResult compareWith(int[] guessed) {
        GuessResult result = new GuessResult();
        Set<Integer> ignoreForB = Sets.newHashSet();
        for (int i = 0; i < SECRET_LEN; i++) {
            if (guessed[i] == secretNumber[i]) {
                result.increaseA();
                ignoreForB.add(guessed[i]);
            }
        }

        int distinctLength = IntStream.concat(IntStream.of(guessed), IntStream.of(secretNumber))
                .filter(i -> !ignoreForB.contains(i))
                .distinct()
                .toArray()
                .length;

        result.setB(guessed.length + secretNumber.length - ignoreForB.size() * 2 - distinctLength);
        return result;
    }
}
