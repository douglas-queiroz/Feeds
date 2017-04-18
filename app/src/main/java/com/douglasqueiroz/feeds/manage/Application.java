package com.douglasqueiroz.feeds.manage;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by @douglas
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
