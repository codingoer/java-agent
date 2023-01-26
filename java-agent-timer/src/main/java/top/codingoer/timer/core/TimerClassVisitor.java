package top.codingoer.timer.core;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Description：定义计数器扫描待修改类的visitor
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:44 下午
 */
public class TimerClassVisitor extends ClassVisitor {

    private final String className;
    private String annotationDesc;
    private final TimerCmdBroker timerCmdBroker;

    public TimerClassVisitor(ClassVisitor cv, String className, TimerCmdBroker timerCmdBroker) {
        super(Opcodes.ASM5, cv);
        this.className = className;
        this.timerCmdBroker = timerCmdBroker;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        this.annotationDesc = desc;
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, final String name, final String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv == null || name.equals("<init>") ||name.equals("<clinit>")) {
            return mv;
        }

        if(className == null){
            return mv;
        }

        if(!timerCmdBroker.execTime(TimerParam.Vf(className.replace("/", "."),
                annotationDesc, name, ""))){
            return mv;
        }

        final String key = className + name + desc;
        return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
            //方法进入时获取开始时间
            @Override public void onMethodEnter() {
                this.visitLdcInsn(key);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "top/codingoer/timer/core/TimerExecute", "start", "(Ljava/lang/String;)V", false);
            }

            //方法退出时获取结束时间并计算执行时间
            @Override public void onMethodExit(int opcode) {
                this.visitLdcInsn(key);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "top/codingoer/timer/core/TimerExecute", "end", "(Ljava/lang/String;)V", false);
                //向栈中压入类名称
                this.visitLdcInsn(className);
                //向栈中压入方法名
                this.visitLdcInsn(name);
                //向栈中压入方法描述
                this.visitLdcInsn(desc);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "top/codingoer/timer/core/TimerExecute", "execTime", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                        false);
            }
        };
    }
}
