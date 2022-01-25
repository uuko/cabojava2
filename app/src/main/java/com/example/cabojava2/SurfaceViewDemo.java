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
import android.view.SurfaceHolder;
import android.view.SurfaceView;


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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        System.out.println("=========surfaceCreated======== width: " + screenWidth + " height: " + screenHeight);
//        mapYpos = -1 * screenHeight;
        xRightBorder=screenWidth;
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Canvas mCanvas = mSurfaceHolder.lockCanvas();

                drawCabo(mCanvas);
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                mHandler.postDelayed(this, 500);
            }
        }, 500);

    }

    boolean isWide=false;
    boolean isJump=false;
    int a=0;
    private void drawCabo(Canvas mCanvas) {
        a++;
        System.out.println("============draw======== y:" + mapYpos + " ==x: " + xPos);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Bitmap mapBit = BitmapFactory.decodeResource(getResources(), R.drawable.img111_1858);
        mapBit = getResizedBitmap(mapBit, (int) screenWidth, (int) screenHeight);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.capoo_2);
        Bitmap leftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.capoo_3);

        // LIST

//         C=1;
//         LIST X,Y
        //  X LEFT 500 RIGHT 700 Y 1080
        // LIST.GET(a).GETlEFT();



        mCanvas.drawBitmap(mapBit, 0, mapYpos = mapYpos + 25, paint);
        if (xPos+leftBitmap.getWidth() < xRightBorder && !isWide) {
            if (isJump){
                isJump=false;
                mCanvas.drawBitmap(leftBitmap, xPos = xPos + 50, screenHeight - leftBitmap.getHeight()+5, paint);
            }
            else {
                isJump=true;
                mCanvas.drawBitmap(leftBitmap, xPos = xPos + 50, screenHeight - leftBitmap.getHeight()-5, paint);
            }

        } else {
            if (isJump){
                isJump=false;
                mCanvas.drawBitmap(bitmap, xPos = xPos -
                        50, screenHeight - bitmap.getHeight()+5, paint);
            }
            else {
                isJump=true;
                mCanvas.drawBitmap(bitmap, xPos = xPos -
                        50, screenHeight - bitmap.getHeight()-5, paint);
            }

        }
        if (xPos+bitmap.getWidth()>=xRightBorder)isWide=true;
        if (xPos<=xLftBorder)isWide=false;


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
