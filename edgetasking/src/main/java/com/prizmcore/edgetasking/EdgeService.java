package com.prizmcore.edgetasking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.os.IBinder;
import android.view.WindowManager;
import android.graphics.PixelFormat;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.Gravity;
import android.util.Log;
import android.widget.ImageView;

public class EdgeService extends Service {

    private WindowManager.LayoutParams mParams2;
    private WindowManager mWindowManager;
    private ImageView image;

    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;
    private int flag = 0;
    private int x, y;

    private OnTouchListener mViewTouchListener = new OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("down","down");
                    if(MAX_X == -1)
                        setMaxPosition();
                    START_X = event.getRawX();
                    START_Y = event.getRawY();
                    PREV_X = mParams2.x;
                    PREV_Y = mParams2.y;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    Log.d("move","move");
                    x = (int)(event.getRawX() - START_X);
                    y = (int)(event.getRawY() - START_Y);

                    if(x < 20 || y < 20)
                    {
                        flag = 1;
                    }
                    else
                    {
                        Log.d("flag",Integer.toString(flag));
                        flag = 0;
                    }

//                    mParams2.x = PREV_X + x;
                    mParams2.y = PREV_Y + y;
                    optimizePosition();
                    mWindowManager.updateViewLayout(image, mParams2);
                    Log.d("x", Integer.toString(x));
                    Log.d("y", Integer.toString(y));
                    return true;
                case MotionEvent.ACTION_UP:
                    Log.d("up","up");
                    x = Math.abs(x);
                    y = Math.abs(y);
                    if(y < 20)
                    {
                        mWindowManager.removeView(image);
                        stopSelf();
                        Context context = getApplicationContext();
                        Intent intent = new Intent(context, edgelist.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
    /*                    boolean temp = isRunningProcess(getApplicationContext(), "pe.sbk.alwaysontop/.MainActivity");
                        if(temp)
                        {
                            aActivity.finish();
                        }
                        Context context = getApplicationContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                        flag = 0; */
                    }
                    return true;
                default :


            }

            return true;
        }
    };

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();


        image = new ImageView(this);
        image.setImageResource(R.drawable.ic_launcher);
        image.setAlpha(127);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setOnTouchListener(mViewTouchListener);


        mParams2 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mParams2.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(image,  mParams2);



    }

    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(matrix);

        MAX_X = matrix.widthPixels - image.getWidth();
        MAX_Y = matrix.heightPixels - image.getHeight();
    }


    private void optimizePosition() {

        if( mParams2.x > MAX_X)  mParams2.x = MAX_X;
        if( mParams2.y > MAX_Y)  mParams2.y = MAX_Y;
        if( mParams2.x < 0)  mParams2.x = 0;
        if( mParams2.y < 0)  mParams2.y = 0;
    }






}
