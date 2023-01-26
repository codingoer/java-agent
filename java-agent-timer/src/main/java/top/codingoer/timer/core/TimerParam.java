package top.codingoer.timer.core;

/**
 * Description：执行的参数
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:52 下午
 */
public class TimerParam {

    private String className;
    private String classAno;
    private String methodName;
    private String methodAno;

    public TimerParam(String className, String classAno, String methodName, String methodAno) {
        this.className=className;
        this.classAno=classAno;
        this.methodName=methodName;
        this.methodAno=methodAno;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassAno() {
        return classAno;
    }

    public void setClassAno(String classAno) {
        this.classAno = classAno;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodAno() {
        return methodAno;
    }

    public void setMethodAno(String methodAno) {
        this.methodAno = methodAno;
    }

    public static TimerParam Vf(String className, String classAno, String methodName, String methodAno){
        return new TimerParam( className,  classAno,  methodName, methodAno);
    }
}
