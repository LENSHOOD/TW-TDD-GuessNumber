package zxh.demo.tw.guess.number;

import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

/**
 * zxh.demo.tw.guess.number.GuessNumber:
 * @author zhangxuhai
 * @date 2020/1/20
*/
public class GuessNumber {
    private static final Random RANDOM = new Random();
    private int[] secretNumber;

    public GuessNumber() {
        secretNumber = generateSecretNumber();
    }

    int[] generateSecretNumber() {
        Set<Integer> secretSet = Sets.newTreeSet();
        do {
            secretSet.add(RANDOM.nextInt(10));
        } while (secretSet.size() != 4);

        return secretSet.stream().mapToInt(i -> i).toArray();
    }
}
