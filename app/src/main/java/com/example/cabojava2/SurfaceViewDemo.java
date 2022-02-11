package com.example.cabojava2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint paint;
    private Handler mHandler;
    private int xPos = 500;
    private float mapYpos = 0;
    private float screenWidth, screenHeight;
    private float xLftBorder=0;
    private float xRightBorder=0;
    public SurfaceViewDemo(Context context) {
        this(context, null, 0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        this.setKeepScreenOn(true);

    }

    double[] border = new double[]{0.3, 0.54, 0.45, 0.7, 0.3, 0.58, 0.28, 0.68, -1};
    double[] list_x = new double[]{0.01, 0.01, 0.005, 0.01, 0.02, 0.01, 0.01, 0.01};
    double[] list_y = new double[]{0.006, 0.006, 0.003, 0.006, 0.012, 0.006, 0.006, 0.006};
    int flag = 0;
    boolean isWide = false;
    boolean isJump = false;
    int a = 0;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        xPos = (int) (screenWidth * 0.535);
        System.out.println("=========surfaceCreated======== width: " + screenWidth + " height: " + screenHeight);
//        mapYpos = -1 * screenHeight;
        xRightBorder = screenWidth;
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Canvas mCanvas = mSurfaceHolder.lockCanvas();

                drawCabo(mCanvas);
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                mHandler.postDelayed(this, 20);
            }
        }, 20);

    }

    private void drawCabo(Canvas mCanvas) {
        if (a % 2 == 0) {
            xLftBorder = (float) (screenWidth * border[a]);
            xRightBorder = screenWidth;
        } else {
            xLftBorder = 0;
            xRightBorder = (float) (screenWidth * border[a]);
        }

        Log.d("taggg", "borderL:" + xLftBorder + ",borderR:" + xRightBorder);
        System.out.println("============dr" +
                "aw======== y:" + mapYpos + " ==x: " + xPos);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Bitmap mapBit = BitmapFactory.decodeResource(getResources(), R.drawable.img111_1858);
        mapBit = getResizedBitmap(mapBit, (int) screenWidth, (int) screenHeight);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.capoo_2);
        Bitmap leftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.capoo_3);
        if (border[a] != -1) {
            if (isWide && xPos > xLftBorder && flag == 0) {
                a += 1;
                flag = 1;
                Log.d("taggg", "leftt");
            } else if (!isWide && (xPos + bitmap.getWidth()) >= xRightBorder && flag == 0) {
                a += 1;
                flag = 1;
                Log.d("taggg", "rightt");
            }
        } else {
            Log.d("taggg", "finish");
            mCanvas.drawBitmap(bitmap, xPos = (int) (screenWidth * border[a - 1]),
                    screenHeight - bitmap.getHeight(), paint);
        }
        // LIST

//         C=1;
//         LIST X,Y
        //  X LEFT 500 RIGHT 700 Y 1080
        // LIST.GET(a).GETlEFT();

        //圖片往下
        mCanvas.drawBitmap(mapBit, 0, mapYpos =
                (float) (mapYpos + screenHeight * list_y[a]), paint);
        //咖波左(移動+跳)
        if (xPos + leftBitmap.getWidth() < xRightBorder && isWide) {
            if (isJump) {
                isJump = false;
                mCanvas.drawBitmap(leftBitmap, xPos = (int) (xPos + screenWidth * list_x[a]),
                        screenHeight - leftBitmap.getHeight() + 5, paint);
            } else {
                isJump = true;
                mCanvas.drawBitmap(leftBitmap, xPos = (int) (xPos + screenWidth * list_x[a]),
                        screenHeight - leftBitmap.getHeight() - 5, paint);
            }

        }
        //咖波右(移動+跳)
        else {
            if (isJump) {
                isJump = false;
                mCanvas.drawBitmap(bitmap, xPos = (int) (xPos - screenWidth * list_x[a])
                        , screenHeight - bitmap.getHeight() + 5, paint);
            } else {
                isJump = true;
                mCanvas.drawBitmap(bitmap, xPos = (int) (xPos - screenWidth * list_x[a])
                        , screenHeight - bitmap.getHeight() - 5, paint);
            }

        }
        if (xPos + bitmap.getWidth() >= xRightBorder) {
            isWide = false;//到右邊界轉左
            flag = 0;
        }
        if (xPos <= xLftBorder) {
            isWide = true;//到左邊界轉右
            flag = 0;
        }


    }

    private void drawView() {
//        this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//先清空屏幕
        // this.canvas.drawBitmap(this.bgBitmap,this.mBgX,0,null);//绘制图片
        // switch (this.state){//判断现在是向左还是向右移动
        // case LEFT: this.mBgX-=this.MOVE_SIZE;//向左移动 break;
        // case RIGHT: this.mBgX+=this.MOVE_SIZE;//向右移动 break;
        // default: break; } //如果向左移动了1/2，那么更改为向右移动，本身图片宽度只有3/2都移动了1/2显然已经移动完了
        // if(this.mBgX<=-this.screenWidht/2){ this.state=State.RIGHT; } //如果X坐标大于0，向左移动
        // if(this.mBgX>=0){ this.state=State.LEFT; }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("=========surfaceDestroyed========");
    }
}
