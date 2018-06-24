package com.shiger.pingworld;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


import static android.content.ContentValues.TAG;


public class PingUtils {

    public  void pingRun(String address){

        String returnParam = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ping -c 10 "+address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
        LineNumberReader returnData = new LineNumberReader(inputStreamReader);

        String returnMessage="";
        String line = "";
        try {
            while ((line = returnData.readLine()) != null) {
                returnMessage = returnMessage + line + "\n";
                Log.d(TAG, "pingRun: " + returnMessage);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "pingRun: " + returnMessage);


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
                Log.d(TAG, "pingRun: " + returnMessage);

            }else if(line1.contains("时间=")){
                int index = line1.indexOf("时间=");
                int end = line1.indexOf("ms") + 2;
                time = line1.substring(index + "时间=".length(),end);

                returnParam = returnParam + "时间: " + time;
                Log.d(TAG, "pingRun: " + returnMessage);

            }else{
                returnParam = returnParam + line1;
                Log.d(TAG, "pingRun: " + returnMessage);

                continue;
            }
        }

    }

    public  String isPingSuccess(int pingNum, String ipAddress) {
        StringBuffer tv_PingInfo = new StringBuffer();
        try {

            Process p = Runtime.getRuntime().exec("/system/bin/ping -c " + pingNum + " " + ipAddress); // 10.83.50.111
// m_strForNetAddress
            int status = p.waitFor();
            String result ="";
            if (status == 0) {
                result = "success";
            } else {
                result = "failed";
            }
            String lost = new String();
            String delay = new String();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            Log.d(TAG, "isPingSuccess: bufferedReader =" + bufferedReader);
            String str = new String();
// 读出全部信息并显示
            while ((str = bufferedReader.readLine()) != null) {
                str = str + "\r\n";
                Log.d(TAG, "isPingSuccess: str = " + str);
                tv_PingInfo.append(str);
            }
            return tv_PingInfo.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String extractNumAverage(String stringIn) {
//        stringIn = "rtt min/avg/max/mdev = 3.412/24.719/39.086/9.172 ms";
        String[] array = stringIn.split("/");
        int arrayLenth = array.length;
        Log.d(TAG, "extractNum: array =" + array);
        Log.d(TAG, "extractNum: arrayLenth =" + arrayLenth);
        for (int i = 0; i < arrayLenth; i++) {
            Log.d(TAG, "extractNum:  " + " i=" + String.valueOf(i) + " = " + array[i]);
        }
        String outAverage = "";
        if(arrayLenth> 6){
            outAverage = array[5];
        } else {
            outAverage = "time out!";
        }
        Log.d(TAG, "onCreate: pingAverage" + outAverage);
        return outAverage;
    }

}  