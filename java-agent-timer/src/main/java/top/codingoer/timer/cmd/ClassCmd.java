package top.codingoer.timer.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.codingoer.timer.core.TimerParam;

/**
 * Description：类命令 @C-com.ayg.service.OrderClass
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:51 下午
 */
public class ClassCmd implements TimerCmd {

    private static final Logger logger = LoggerFactory.getLogger(ClassCmd.class);

    private static final int CMD_MIN_LEN = 4;
    private static final String CMD_PREFIX = "@C-";
    private final String className;

    private ClassCmd(String className) {
        this.className = className;
        printInit();
    }

    public static ClassCmd makeCmd(String cmdStr) {
        String cmdTrim = cmdStr.trim();

        if (cmdTrim.length() < CMD_MIN_LEN) {
            return null;
        }

        if (!cmdTrim.startsWith(CMD_PREFIX)) {
            return null;
        }

        return new ClassCmd(cmdTrim.substring(3));
    }

    @Override
    public boolean execTime(TimerParam timerParam) {
        return className.equals(timerParam.getClassName());
    }

    @Override
    public void printInit() {
        logger.info("类:{}所有的方法将计算执行时长", className);
    }
}
