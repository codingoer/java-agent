package top.codingoer.timer.core;

import top.codingoer.timer.cmd.ClassCmd;
import top.codingoer.timer.cmd.MethodCmd;
import top.codingoer.timer.cmd.PackageCmd;
import top.codingoer.timer.cmd.TimerCmd;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：执行命令的建造者
 *
 * @author Lionel
 * @date Created in 2023/1/26 3:45 下午
 */
public class TimerCmdBuilder {

    private static final String SP = "\\|\\|";
    private static final String NULL_STR = "";

    public static TimerCmdBroker buildExeCmd(String cmdParam){
        if(cmdParam == null || NULL_STR.equals(cmdParam.trim())){
            return null;
        }

        String[] cmdArray = cmdParam.trim().split(SP);
        List<TimerCmd> cmdList = new ArrayList<>();
        for(String cmdStr : cmdArray){
            if(NULL_STR.equals(cmdStr.trim())){
                continue;
            }

            //类
            TimerCmd cmd = ClassCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
                continue;
            }

            //包
            cmd = PackageCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
                continue;
            }

            //方法
            cmd = MethodCmd.makeCmd(cmdStr);
            if(cmd != null){
                cmdList.add(cmd);
            }
        }

        if(cmdList.isEmpty()){
            return null;
        }

        return new TimerCmdBroker(cmdList);
    }
}
