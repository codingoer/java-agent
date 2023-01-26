package top.codingoer.timer.core;

import top.codingoer.timer.cmd.TimerCmd;

import java.util.List;

/**
 * Description：执行命令的Broker
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:49 下午
 */
public class TimerCmdBroker {

    private final List<TimerCmd> cmdList;

    public TimerCmdBroker(List<TimerCmd> cmdList) {
        this.cmdList = cmdList;
    }

    public boolean execTime(TimerParam timerParam) {
        for (TimerCmd cmd : cmdList) {
            if (cmd.execTime(timerParam)) {
                return true;
            }
        }

        return false;
    }
}
