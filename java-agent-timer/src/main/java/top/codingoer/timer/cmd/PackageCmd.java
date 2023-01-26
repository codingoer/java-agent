package top.codingoer.timer.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.codingoer.timer.core.TimerParam;

/**
 * Description：包命令 @P-com.ayg.service
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:51 下午
 */
public class PackageCmd implements TimerCmd {

    private static final Logger logger = LoggerFactory.getLogger(PackageCmd.class);

    private static final int CMD_MIN_LEN = 4;
    private static final String CMD_PREFIX = "@P-";
    private final String packageName;

    private PackageCmd(String packageName){
        this.packageName = packageName;
        printInit();
    }

    public static PackageCmd makeCmd(String cmdStr){
        String cmdTrim = cmdStr.trim();

        if(cmdTrim.length() < CMD_MIN_LEN){
            return null;
        }

        if(!cmdTrim.startsWith(CMD_PREFIX)){
            return null;
        }

        return new PackageCmd(cmdTrim.substring(3).concat("."));
    }

    @Override
    public boolean execTime(TimerParam execParam) {
        return execParam.getClassName().startsWith(packageName);
    }

    @Override
    public void printInit() {
        logger.info("包:{}其下所有类的方法将计算执行时长", packageName);
    }
}
