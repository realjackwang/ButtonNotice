package com.button.notice.Notice.what_is_not_activity;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bzdell on 2018/3/21.
 */

public class myToast {
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
}
