package com.nyakokishi.zhihu.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckUtil {

	/**
	 * (phoneNum is null return false)
	 * yes return true; no return false;
	 * @param phoneNum
	 * @return boolean
	 */
	public static boolean isMobile(String phoneNum) {
		if(phoneNum != null){
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(phoneNum);
			return m.matches();
		}else{
			return false;
		}
	}
	/**
	 * 验证是不是有效的qq号码
	 * yes return true; no return false;
	 * @param qqNum qq号码
	 * @return boolean
	 */
	public static boolean isQQNumber(String qqNum) {
		if(qqNum != null){
			Pattern p = Pattern.compile("^[1-9][0-9]{4,14}$");
			Matcher m = p.matcher(qqNum);
			return m.matches();
		}else{
			return false;
		}
	}

    public static boolean isIDcard(String idCard){
        if (idCard != null){
            Pattern p = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
            Matcher m = p.matcher(idCard);
            return m.matches();
        }else{
            return false;
        }
    }

	/**
	 * 是不是数字（只有0-9，没有符号）
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}



	/** true : net Connection;  false: net disconnect */
	public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
	
	
	 /** true : s = number | Chinese | English  false: ,.* */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
	
	
	/**
	 * String data is json ; yes return true, no return false;
	 */
	public static boolean isJson(String json) {
		if(json == null || json.equals("")){
			return false;
		}
		json = json.trim();
		if("{".equals(json.substring(0,1))&&"}".equals(json.substring(json.length()-1))){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * To show whether the String is null or is an empty String
	 */
	public static boolean isNullOrNil(String str) {
		return str == null || "".equals(str);
	}
	
	
	/** APP is not runing true:false  */
	public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            //判断所有的app进程,报名是否等于该app
        	if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true; //"处于后台"
                } else {
                    return false;//"处于前台"
                }
            }
        }
        return false;
    }
}
