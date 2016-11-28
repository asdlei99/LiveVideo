package com.jorkyin.livevideo;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.jorkyin.livevideo.events.VideoEventListener;

/**
 * Created by YinJian on 2016/11/28.
 */

public class Player extends AppCompatActivity implements VideoEventListener {
    // Used to load the 'native-lib' library on application startup.
    //http://www.jianshu.com/p/56de0e463ef4
    static {
        System.loadLibrary("native-lib");
    }

    private Handler mainHandler = new Handler();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                new Thread(new Player.Play()).start();
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {    }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {    }
        });
    }

    @Override
    public void onResolutionChange(final int width, final int height) {
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        mainHandler.post(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
                params.height = (size.x)*height/width;
                surfaceView.setLayoutParams(params);
            }
        });
    }

    class Play implements Runnable {

        @Override
        public void run() {
            //获取文件路径，这里将文件放置在手机根目录下
            String folderurl = Environment.getExternalStorageDirectory().getPath();
            String inputurl = folderurl+"/a.mp4";
            play(inputurl, surfaceViewHolder.getSurface());
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void play(String url, Surface surface);

}
