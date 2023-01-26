package top.codingoer.timer.core;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Description：方法执行时长字节码转换器
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:44 下午
 */
public class TimerTransformer implements ClassFileTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(TimerTransformer.class);

    private final TimerCmdBroker timerBroker;

    public TimerTransformer(TimerCmdBroker timerBroker) {
        this.timerBroker = timerBroker;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        try {
            //第一步：读取类的字节码流
            ClassReader reader = new ClassReader(classFileBuffer);
            //第二步：创建操作字节流值对象，ClassWriter.COMPUTE_MAXS:表示自动计算栈大小
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            //第三步：接受一个ClassVisitor子类进行字节码修改
            reader.accept(new TimerClassVisitor(writer, className, timerBroker), ClassReader.EXPAND_FRAMES);
            //第四步：返回修改后的字节码流
            return writer.toByteArray();
        } catch (Throwable e) {
            LOG.error("Agent timer transform error", e);
            throw e;
        }
    }
}
