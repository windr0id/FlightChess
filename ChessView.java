package cc.windroid.flightchess;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class ChessView extends View {
    private String mExampleString = "Hello droidwen!"; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 64; // TODO: use a default from R.dimen...

    private Bitmap background = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.background);
    private Bitmap chess = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.chess);
    private Paint paint = new Paint();
    Canvas canvas;
    int chess_size;
    int windowWidth;

    final double CHESS_RATE = 0.05;

    public ChessView(Context context) {
        super(context);
    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        windowWidth = contentWidth;
        canvas.drawBitmap(zoomImg(background,windowWidth ,windowWidth), 0, 0, paint);

        for(int i=0; i<1; i++){
            drawChess(Player.R, Position.c_r[i]);
            drawChess(Player.B, Position.c_b[i]);
            drawChess(Player.G, Position.c_g[i]);
            drawChess(Player.Y, Position.c_y[i]);
        }
    }

    private void drawChess(int color, int index){
        chess_size = (int)(windowWidth*CHESS_RATE);
            canvas.drawBitmap(zoomImg(getChess(color), chess_size, chess_size),
                    formatPx(Position.get_x(color, index)),
                    formatPx(Position.get_y(color, index)),
                    paint);
    }

    private int formatPx(int px){
        return px*windowWidth/1000-chess_size/2;
    }

    //G R Y B
    public Bitmap getChess(int index){
        Bitmap temp = null;
        switch (index){
            case Player.B://B
                temp = Bitmap.createBitmap(chess, 0, 0, chess.getWidth()/2, chess.getHeight()/2);
                break;
            case Player.G://G
                temp = Bitmap.createBitmap(chess, chess.getWidth()/2, 0, chess.getWidth()/2, chess.getHeight()/2);
                break;
            case Player.Y://Y
                temp = Bitmap.createBitmap(chess, 0, chess.getHeight()/2, chess.getWidth()/2, chess.getHeight()/2);
             break;
            case Player.R://R
                temp = Bitmap.createBitmap(chess, chess.getWidth()/2, chess.getHeight()/2, chess.getWidth()/2, chess.getHeight()/2);
                break;
        }
        return temp;
    }


    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
