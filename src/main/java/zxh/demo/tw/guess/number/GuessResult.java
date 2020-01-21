package zxh.demo.tw.guess.number;

import lombok.Getter;

/**
 * GuessResult:
 * @author zhangxuhai
 * @date 2020/1/20
*/
@Getter
public class GuessResult {
    private int a;
    private int b;

    void increaseA() {
        a++;
    }

    void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a + "A" + b + "B";
    }
}
