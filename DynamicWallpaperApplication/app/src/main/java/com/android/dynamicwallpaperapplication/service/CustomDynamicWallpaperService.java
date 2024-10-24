package com.android.dynamicwallpaperapplication.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import androidx.core.content.ContextCompat;

import com.android.dynamicwallpaperapplication.R;

public class CustomDynamicWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    class MyEngine extends Engine {
        private Bitmap bitmap1, bitmap2, bitmap3;
        private float angle = 0.0f; // 旋转的角度
        private final int FRAME_RATE_MS = 16; // 约60FPS
        private boolean isRunning = true; // 控制线程的标志位

        // 提前声明Matrix对象以减少每次创建的开销
        private Matrix matrix1 = new Matrix();
        private Matrix matrix2 = new Matrix();
        private Matrix matrix3 = new Matrix();
        private Handler handler = new Handler(Looper.getMainLooper()); // 使用Handler在主线程绘制

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            // 解码位图，只需一次
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper7);
            bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper6);
            bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper8);

            // 开启绘制任务，使用Handler确保在主线程运行
            startDrawing(holder);
        }

        private void startDrawing(SurfaceHolder holder) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (isRunning) {
                        drawFrame(holder);
                        handler.postDelayed(this, FRAME_RATE_MS);
                    }
                }
            });
        }

        private void drawFrame(SurfaceHolder holder) {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas == null) return;

                // 清屏，画布背景设为黑色
                canvas.drawColor(ContextCompat.getColor(getApplicationContext(), R.color.shallow_blue));

                // 计算画布的宽度和高度
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();

                // 画一个黑色圆盘作为背景
                Paint circlePaint = new Paint();
                circlePaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.more_shallow_blue));
                circlePaint.setStyle(Paint.Style.FILL);  // 填充

                // 画圆盘，确保圆盘的半径是画布最小边的一半
                float radius = Math.min(canvasWidth, canvasHeight) / 2.7f;
                float centerX = canvasWidth / 2.0f;
                float centerY = canvasHeight / 2.0f;

                // 绘制圆盘
                canvas.drawCircle(centerX, centerY, radius, circlePaint);

                // 更新旋转角度
                angle += 1.0f;  // 每帧旋转1度



                // 绘制 bitmap2
                drawBitmapWithMatrix(canvas, matrix2, bitmap2, canvasWidth, canvasHeight, 0.48f);

                // 绘制 bitmap1
                drawBitmapWithMatrix(canvas, matrix1, bitmap1, canvasWidth, canvasHeight, 0.63f);

                // 绘制 bitmap3


                matrix3.reset();
                matrix3.setScale(0.5f, 0.5f);
                int bitmapWidth = (int) (bitmap3.getWidth() * 0.5f);
                int bitmapHeight = (int) (bitmap3.getHeight() * 0.5f);

                // 计算平移，确保图片居中
                float xTranslation = (canvasWidth - bitmapWidth) / 1.4f;
                float yTranslation = (canvasHeight - bitmapHeight) / 3.6f;

                matrix3.postTranslate(xTranslation, yTranslation);


                canvas.drawBitmap(bitmap3, matrix3, null);
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }

        // 辅助方法，用于通过Matrix绘制位图
        private void drawBitmapWithMatrix(Canvas canvas, Matrix matrix, Bitmap bitmap, int canvasWidth, int canvasHeight, float scale) {
            drawBitmapWithMatrix(canvas, matrix, bitmap, canvasWidth, canvasHeight, scale, 2.0f, 2.0f);
        }

        private void drawBitmapWithMatrix(Canvas canvas, Matrix matrix, Bitmap bitmap, int canvasWidth, int canvasHeight, float scale, float xFactor, float yFactor) {
            matrix.reset();
            matrix.setScale(scale, scale);
            int bitmapWidth = (int) (bitmap.getWidth() * scale);
            int bitmapHeight = (int) (bitmap.getHeight() * scale);

            // 计算平移，确保图片居中
            float xTranslation = (canvasWidth - bitmapWidth) / xFactor;
            float yTranslation = (canvasHeight - bitmapHeight) / yFactor;

            matrix.postTranslate(xTranslation, yTranslation);
            matrix.postRotate(angle, canvasWidth / 2.0f, canvasHeight / 2.0f);

            canvas.drawBitmap(bitmap, matrix, null);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            isRunning = false; // 停止线程
            handler.removeCallbacksAndMessages(null); // 停止绘制任务

            // 释放位图资源
            recycleBitmaps();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            isRunning = false; // 停止绘制
            recycleBitmaps(); // 确保资源释放
        }

        private void recycleBitmaps() {
            if (bitmap1 != null) {
                bitmap1.recycle();
                bitmap1 = null;
            }
            if (bitmap2 != null) {
                bitmap2.recycle();
                bitmap2 = null;
            }
            if (bitmap3 != null) {
                bitmap3.recycle();
                bitmap3 = null;
            }
        }
    }
}

