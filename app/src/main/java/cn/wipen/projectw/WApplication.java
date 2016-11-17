package cn.wipen.projectw;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wipenHan on 2016/11/17.
 */
public class WApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
