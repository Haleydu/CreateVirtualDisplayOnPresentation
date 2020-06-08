package com.andronblog.presentationonvirtualdisplay;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.Log;
import android.view.Display;

import java.util.ArrayList;

/**
 * Created by zx315476228 on 17-3-10.
 */

public class SysUtil {
    private static String ipRegex = "^(1\\d{2}|2[0-4 ]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\ d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\ d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\ d|25[0-5]|[1-9]\\d|\\d)$";

    /**
     * 获取手机高宽密度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 手机屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 手机屏幕DPI
     *
     * @param context
     * @return
     */
    public static int getScreenDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;

    }

    public static int getVersionCode() {
        return Build.VERSION.SDK_INT;
    }
    public static boolean isIpAddress(String ip){
        return ip.matches(ipRegex);
    }

    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(300);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    public static Display getDisplayStartWidth(Context mcontext, String nameprefix){
        Display finddisplay = null;
        DisplayManager manager = (DisplayManager) mcontext.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();

        if(displays.length > 1) {
            for(Display display:displays){
                Log.d("SysUtil","display.getName():"+display.getName());
                if(display.getName().startsWith(nameprefix)){
                    finddisplay = display;
                    break;
                }
            }
        }

        return finddisplay;
    }

    public static Display getDisplayContains(Context mcontext, String contains){
        Display finddisplay = null;
        DisplayManager manager = (DisplayManager) mcontext.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();

        if(displays.length > 1) {
            for(Display display:displays){
                if(display.getName().contains(contains)){
                    finddisplay = display;
                    break;
                }
            }
        }

        return finddisplay;
    }

    public static Display getDisplayByName(Context mcontext, String name){
        Display finddisplay = null;
        DisplayManager manager = (DisplayManager) mcontext.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();

        if(displays.length > 1) {
            for(Display display:displays){
                Log.d("DDD", "display.getName():"+display.getName());
                if(display.getName().equals(name)){
                    finddisplay = display;
                    break;
                }
            }
        }

        return finddisplay;
    }

    public static long mlastFrameTime = 0;

    public static float frameRateCalculate(boolean clear){
        long time = System.currentTimeMillis();

        if(clear)SysUtil.mlastFrameTime=0;

        if(SysUtil.mlastFrameTime == 0) {
            SysUtil.mlastFrameTime = time;
            return 0;
        }


        float framerate = 1000.0f  / (time - SysUtil.mlastFrameTime);

        SysUtil.mlastFrameTime = time;

        return framerate;
    }

    public static int mframecount = 0;
    public static long mframecountstarttick = 0;
    public static float frameRateCalculateByDuration(boolean clear,int seconds){
        if(mframecount == 0 || clear) {
            mframecount = 0;
            mframecountstarttick = System.currentTimeMillis();
        }

        mframecount++;

        if(System.currentTimeMillis() - mframecountstarttick >= seconds*1000 ) {
            float framecount = mframecount;
            mframecount = 0;
            return framecount/seconds;
        }

        return -1;
    }
}
