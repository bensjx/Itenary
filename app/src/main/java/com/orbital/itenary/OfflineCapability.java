package com.orbital.itenary;

import com.google.firebase.database.FirebaseDatabase;

public class OfflineCapability extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}