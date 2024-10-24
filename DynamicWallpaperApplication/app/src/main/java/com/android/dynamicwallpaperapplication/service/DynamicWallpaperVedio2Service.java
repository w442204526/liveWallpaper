package com.android.dynamicwallpaperapplication.service;





import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.android.dynamicwallpaperapplication.R;


public class DynamicWallpaperVedio2Service extends WallpaperService {
    public final String TAG = this.getClass().getSimpleName();


    @Override
    public Engine onCreateEngine() {
        Log.d(TAG, "->:onCreateEngine()");
        // 1: 这里返回实现的动画引擎
        return new VideoEngine();
    }



    public class VideoEngine extends Engine {
        public final String TAG = this.getClass().getSimpleName();

        private MediaPlayer mp;
        public VideoEngine() {
            super();
        }



        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            // Engine对象被创建时回调，这里可以做一些初始化的工作（例如 注册广播）
            Log.d(TAG, "->:VideoEngine -- onCreate()");

            mp=MediaPlayer.create(getApplicationContext(), R.raw.video10);
            mp.setLooping(true); // 循环播放
            mp.setVolume(0.0f, 0.0f);// 静音
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            //Engine对象被销毁时回调，这里可以做一些回收释放的操作（例如 注销广播）
            Log.d(TAG, "->:VideoEngine -- onDestroy()");
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            //显示、隐藏时切换，在桌面时为显示，跳转到别的App页面时为隐藏 ：这里做视频的暂停和恢复播放
            Log.d(TAG, "->:VideoEngine -- onVisibilityChanged() visible:" + visible);
            if (mp != null) {
                if (visible) {
                    mp.start();
                    Log.d(TAG, "->:VideoEngine -- onVisibilityChanged() mp->start");
                } else {
                    mp.pause();
                    Log.d(TAG, "->:VideoEngine -- onVisibilityChanged() mp->pause");
                }
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            //SurfaceView创建时回调，视频MediaPlayer对象播放的视频输出在这个surface上
            Log.d(TAG, "->:VideoEngine -- onSurfaceCreated()");
            mp.setSurface(holder.getSurface());
            mp.start();
            Log.d(TAG, "->:VideoEngine -- onSurfaceCreated() start!");
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            //Surface销毁时回调，这里销毁MediaPlayer，回收MediaPlayer
            if (mp != null) {
                Log.d(TAG, "->:VideoEngine -- onSurfaceDestroyed()");
                mp.stop();
                mp.release();
                mp = null;
            }
        }
    }

}