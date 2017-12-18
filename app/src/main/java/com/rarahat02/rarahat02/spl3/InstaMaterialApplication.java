package com.rarahat02.rarahat02.spl3;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.binjar.prefsdroid.Preference;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class InstaMaterialApplication extends MultiDexApplication
{

    private RefWatcher mRefWatcher;


    public static RefWatcher getRefWatcher(Context context) {
        return ((InstaMaterialApplication) context.getApplicationContext()).mRefWatcher;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        if (LeakCanary.isInAnalyzerProcess(this))
        {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Preference.load().using(this).prepare();
    }

}
