package top.codingoer.timer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：时间记录执行器
 *
 * @author Lionel
 * @date Created in 2023/1/26 4:46 下午
 */
public class TimerExecute {

    private static final Logger LOG = LoggerFactory.getLogger(TimerExecute.class);

    private static final ThreadLocal<Map<String, Long>> costTimeLocal = new ThreadLocal<Map<String, Long>>();
    private static final String ST_FIX = "ST_"; //开始时间
    private static final String ET_FIX = "ET_"; //结束时间

    private TimerExecute() {
        //just for private
    }

    public static void start(String key) {
        try {
            Map<String, Long> map = costTimeLocal.get();
            if (map == null) {
                map = new HashMap<>();
                costTimeLocal.set(map);
            }
            map.put(ST_FIX + key, System.currentTimeMillis());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static void end(String key) {
        try {
            costTimeLocal.get().put(ET_FIX + key, System.currentTimeMillis());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static void execTime(String className, String methodName, String methodDesc) {
        try {
            String key = className + methodName + methodDesc;
            long costTime = costTimeLocal.get().get(ET_FIX + key) - costTimeLocal.get().get(ST_FIX + key);
            LOG.info(className.replace("/", ".") + "." + methodName + " cost:" + costTime + "ms");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

}
