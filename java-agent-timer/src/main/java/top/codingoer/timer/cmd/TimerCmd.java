package top.codingoer.timer.cmd;

import top.codingoer.timer.core.TimerParam;

/**
 * Description：命令接口
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:46 下午
 */
public interface TimerCmd {

    String COM = ",";
    String DOLLAR = "\\$";

    boolean execTime(TimerParam timerParam);

    void printInit();
}
