package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

public class PingCmd {
    public static void main(String [] args)
    {
        System.out.println("Hello World");
        pingRun("www.baidu.com");
    }


    public static void pingRun(String address){

        String returnParam = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ping  "+address);
        } catch (IOException e) {
            System.out.println("ping Exception......");
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), Charset.forName("GB2312"));
        LineNumberReader returnData = new LineNumberReader(inputStreamReader);

        String returnMessage="";
        String line = "";
        try {
            while ((line = returnData.readLine()) != null) {
                returnMessage = returnMessage + line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(returnMessage);


        if("".equals(returnParam)){
            returnParam = "ping 地址：" + address;
        }else{
            returnParam = returnParam + "                            " + "ping 地址：" + address;
        }
        String[] lines = returnMessage.split("\n");
        String time;
        for (String line1 : lines) {
            if (line1.contains("time=")){
                int index = line1.indexOf("time=");
                int end = line1.indexOf("ms") + 2;
                time = line1.substring(index + "time=".length(),end);

                returnParam = returnParam + "time: " + time;
                System.out.println(returnParam);
            }else if(line1.contains("时间=")){
                int index = line1.indexOf("时间=");
                int end = line1.indexOf("ms") + 2;
                time = line1.substring(index + "时间=".length(),end);

                returnParam = returnParam + "时间: " + time;
                System.out.println(returnParam);
            }else{
                returnParam = returnParam + line1;
                System.out.println(returnParam);
                continue;
            }
        }

    /*    if(runCount >= 2){
            map.put("pingIP",returnParam);
//            MobclickAgent.onEvent(mContext , "TimeOut", map);
            System.out.println("最后参数：" + returnParam);
        }
*/

    }

}

