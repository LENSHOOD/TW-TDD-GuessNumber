package zxh.demo.tw.guess.number;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * GuessResult:
 * @author zhangxuhai
 * @date 2020/1/20
*/
@Getter
public class GuessResult {
    private List<Integer> a = Lists.newArrayList();
    private List<Integer> b = Lists.newArrayList();
    private boolean invalid;

    public static GuessResult createInvalidResult() {
        GuessResult result = new GuessResult();
        result.invalid = true;
        return result;
    }

    void addA(int i) {
        a.add(i);
    }

    void addB(int i) {
        b.add(i);
    }

    boolean isInvalid() {
        return invalid;
    }
}
