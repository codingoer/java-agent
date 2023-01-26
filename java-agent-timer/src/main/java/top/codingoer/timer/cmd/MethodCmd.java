package top.codingoer.timer.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.codingoer.timer.core.TimerParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Description：方法命令 @M-com.ayg.service.OrderClass$foo,bar
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:49 下午
 */
public class MethodCmd implements TimerCmd {

    private static final Logger logger = LoggerFactory.getLogger(MethodCmd.class);

    private static final int CMD_MIN_LEN = 7;
    private static final String CMD_PREFIX = "@M-";
    private final String className;
    private final Set<String> methodNameSet;

    private MethodCmd(String className, Set<String> methodNameSet){
        this.className = className;
        this.methodNameSet= methodNameSet;
        printInit();
    }

    public static MethodCmd makeCmd(String cmdStr){
        String trimCmd = cmdStr.trim();
        if(trimCmd.length() < CMD_MIN_LEN){
            return null;
        }

        if(!trimCmd.startsWith(CMD_PREFIX)){
            return null;
        }

        String classMethod = trimCmd.trim().substring(3);

        String[] classMethods = classMethod.split(DOLLAR);
        if(classMethods.length != 2){
            return null;
        }

        String[] methodArr = classMethods[1].split(COM);
        return new MethodCmd(classMethods[0], new HashSet<>(Arrays.asList(methodArr)));
    }

    @Override
    public boolean execTime(TimerParam timerParam) {
        return className.equals(timerParam.getClassName()) && methodNameSet.contains(timerParam.getMethodName());
    }

    @Override
    public void printInit() {
        StringBuilder methods = new StringBuilder();
        methodNameSet.forEach(m-> methods.append(",").append(m));
        logger.info("类:{}的方法{}将计算执行时长", className, methods.substring(1));

    }
}
