package com.andronblog.presentationonvirtualdisplay;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class DemoPresentation extends Presentation {

    private static final String TAG = "DemoPresentation";

    private int mDefaultDisplayOrientation = Surface.ROTATION_0;
    private Handler mTimerHandler;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"副屏正在跑跑跑");
            handler.postDelayed(runnable,1000);
        }
    };

    public DemoPresentation(Context context, Display display) {
        super(context, display);
        mTimerHandler = new Handler();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            display = activity.getWindowManager().getDefaultDisplay();
            mDefaultDisplayOrientation = display.getRotation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.my_presentation);

        TextView tv = (TextView) findViewById(R.id.textView);
        Animation myRotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotator);
        tv.startAnimation(myRotation);


        final TextView timeTextView = (TextView) findViewById(R.id.tv_time);
        final long startTime = System.currentTimeMillis();
        final Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timeTextView.setText(String.format("%d:%02d", minutes, seconds));
                mTimerHandler.postDelayed(this, 500);
            }
        };
        mTimerHandler.postDelayed(timerRunnable, 0);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.i(TAG, "onDismiss");
                mTimerHandler.removeCallbacks(timerRunnable);
            }
        });


        handler.postDelayed(runnable,0);
    }

}
