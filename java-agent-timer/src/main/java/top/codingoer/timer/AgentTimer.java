package top.codingoer.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.codingoer.timer.core.TimerCmdBroker;
import top.codingoer.timer.core.TimerCmdBuilder;
import top.codingoer.timer.core.TimerTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Description：代理启动类
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:40 下午
 */
public class AgentTimer {

    private static final Logger LOG = LoggerFactory.getLogger(AgentTimer.class);

    public static void premain(String args, Instrumentation instrumentation) {
        LOG.info("Agent timer premain start, param {}", args);
        try {
            TimerCmdBroker cmdBroker = TimerCmdBuilder.buildExeCmd(args);
            if(cmdBroker == null) {
                return;
            }
            instrumentation.addTransformer(new TimerTransformer(cmdBroker));
        } catch (Exception e) {
            LOG.error("Agent timer premain error, {}", e.getMessage(), e);
        }
    }
}
