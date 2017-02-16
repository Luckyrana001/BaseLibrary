package managers;

import android.content.Context;


import java.io.File;

import androidluckyguys.baselibrary.BaseFragment;
import listeners.IResponseReceivedNotifyInterface;
import utils.SettingServices;

/**
 * Created by Sanka on 11/14/16.
 */
public class AppHandler {

    public static long sessionLaseStartTime = 0;

    public static void calSessionActionTime(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, BaseFragment baseFragment){

        if (sessionLaseStartTime == 0){
            sessionLaseStartTime = System.currentTimeMillis();
        }
        else {
            long now = System.currentTimeMillis();
            long duration = sessionLaseStartTime - now / 1000;
            long hours = duration / 3600;
            long minutes = (duration - hours * 3600) / 60;

            if (minutes > 15){
                InsuredDataManager.getDataManager().sessionPulse(iResponseReceivedNotifyInterface, SettingServices.getInstance().getUserName(baseFragment.getBaseActivity()));
            }
        }
    }

    public static void Logout(Context context){

        InsuredDataManager.getDataManager().cleanDataManager();
        SettingServices.getInstance().clearToken(context);
        deleteCache(context);
    }

    public static void deleteCache(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    //Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
