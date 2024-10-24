package com.android.dynamicwallpaperapplication.sustom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.dynamicwallpaperapplication.R;

public class MusicPlayerView  extends View {

    private Bitmap bitmap1,bitmap2,bitmap3;
    private float angle = 0.0f; // 旋转的角度
    private Handler handler;
    private Runnable updateRunnable;
    public MusicPlayerView(Context context) {
        super(context);
        init();
    }

    public MusicPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MusicPlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MusicPlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public  void  init(){
        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, 16);
            }
        };
        handler.post(updateRunnable);
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper7);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper6);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper8);

        // 清屏，画布背景设为黑色
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.shallow_blue));

        // 计算画布的宽度和高度
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        // 画一个黑色圆盘作为背景
        Paint circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(getContext(),R.color.more_shallow_blue));
        circlePaint.setStyle(Paint.Style.FILL);  // 填充

        // 画圆盘，确保圆盘的半径是画布最小边的一半
        float radius = Math.min(canvasWidth, canvasHeight) / 2.80f;
        float centerX = canvasWidth / 2.0f;
        float centerY = canvasHeight / 2.0f;

        // 绘制圆盘
        canvas.drawCircle(centerX, centerY, radius, circlePaint);



        // 创建一个 Matrix 对象用于旋转和缩放
        Matrix matrix2 = new Matrix();
        float scaleX2 = 0.11f;  // 在 X 轴上的缩放因子
        float scaleY2 = 0.11f;  // 在 Y 轴上的缩放因子

        // 设置缩放
        matrix2.setScale(scaleX2, scaleY2);

        // 计算图片的宽度和高度
        int bitmapWidth2 = (int) (bitmap2.getWidth() * scaleX2);
        int bitmapHeight2 = (int) (bitmap2.getHeight() * scaleY2);

        // 计算平移，确保图片居中
        float xTranslation2 = (canvasWidth - bitmapWidth2) / 2.0f;
        float yTranslation2 = (canvasHeight - bitmapHeight2) / 2.0f;

        // 更新旋转角度
        angle += 1.0f;  // 每帧旋转1度，您可以根据需要调整速度

        // 设置平移
        matrix2.postTranslate(xTranslation2, yTranslation2);

        // 设置旋转，旋转中心为画布中心
        matrix2.postRotate(angle, canvasWidth / 2, canvasHeight / 2);

        // 在 Canvas 上绘制缩放与旋转后的 Bitmap
        canvas.drawBitmap(bitmap2, matrix2, null);

        // 创建一个 Matrix 对象用于旋转和缩放
        Matrix matrix = new Matrix();
        float scaleX = 0.12f;  // 在 X 轴上的缩放因子
        float scaleY = 0.12f;  // 在 Y 轴上的缩放因子

        // 设置缩放
        matrix.setScale(scaleX, scaleY);

        // 计算图片的宽度和高度
        int bitmapWidth = (int) (bitmap1.getWidth() * scaleX);
        int bitmapHeight = (int) (bitmap1.getHeight() * scaleY);

        // 计算平移，确保图片居中
        float xTranslation = (canvasWidth - bitmapWidth) / 2.0f;
        float yTranslation = (canvasHeight - bitmapHeight) / 2.0f;



        // 设置平移
        matrix.postTranslate(xTranslation, yTranslation);

        // 设置旋转，旋转中心为画布中心
        matrix.postRotate(angle, canvasWidth / 2, canvasHeight / 2);

        // 在 Canvas 上绘制缩放与旋转后的 Bitmap
        canvas.drawBitmap(bitmap1, matrix, null);

        // 创建一个 Matrix 对象用于旋转和缩放
        Matrix matrix3 = new Matrix();
        float scaleX3 = 0.12f;  // 在 X 轴上的缩放因子
        float scaleY3 = 0.12f;  // 在 Y 轴上的缩放因子

        // 设置缩放
        matrix3.setScale(scaleX3, scaleY3);

        // 计算图片的宽度和高度
        int bitmapWidth3 = (int) (bitmap3.getWidth() * scaleX3);
        int bitmapHeight3 = (int) (bitmap3.getHeight() * scaleY3);

        // 计算平移，确保图片居中
        float xTranslation3 = (canvasWidth - bitmapWidth3) / 1.4f;
        float yTranslation3 = (canvasHeight - bitmapHeight3) / 4.2f;



        // 设置平移
        matrix3.postTranslate(xTranslation3, yTranslation3);



        // 在 Canvas 上绘制缩放与旋转后的 Bitmap
        canvas.drawBitmap(bitmap3, matrix3, null);


    }
}
