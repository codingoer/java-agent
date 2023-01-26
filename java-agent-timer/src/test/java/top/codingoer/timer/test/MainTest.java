package top.codingoer.timer.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Description：测试类
 *
 * @author Lionel
 * @date Created in 2023/1/26 5:05 下午
 */
public class MainTest {

    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void testApp()
    {
        sleep("me");
        logger.warn("test OK!");
        assertTrue( true );
    }

    private void sleep(String who){
        logger.info("who"+who);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
