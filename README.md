# ColorFilterView

Android 图片处理之颜色滤镜

``` java

public class ColorFilterView extends View {

    private Paint mBitmapPaint;

    private Bitmap mBitmap;
    private Matrix mResizeMatrix;
    private ColorMatrix mColorMatrix;

    public ColorFilterView(Context context) {
        super(context);
        initPaint();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        // 构建Paint时直接加上去锯齿属性
        Paint mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 直接使用mColorPaint的属性构建mBitmapPaint
        mBitmapPaint = new Paint(mColorPaint);
        mColorMatrix = new ColorMatrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lake);
            if (mBitmap != null) {//缩放图片大小以适应控件大小
                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();
                mResizeMatrix = new Matrix();
                float scaleWidth = ((float) getWidth() / width);
                float scaleHeight = ((float) getHeight() / height);
                mResizeMatrix.postScale(scaleWidth, scaleHeight);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mResizeMatrix, mBitmapPaint);
    }

    public void setArgb(float alpha, float red, float green, float blue) {
        mColorMatrix = new ColorMatrix(new float[] {
                red, 0, 0, 0, 0,
                0, green, 0, 0, 0,
                0, 0, blue, 0, 0,
                0, 0, 0, alpha, 0,
        });
        postInvalidate();
    }

    @Override
    public void postInvalidate() {
        mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        super.postInvalidate();
    }

    public void setColorMatrixArray(float[] array) {
        mColorMatrix = new ColorMatrix(array);
        postInvalidate();
    }

    public void setColorMatrix(ColorMatrix colorMatrix) {
        mColorMatrix = colorMatrix;
        postInvalidate();
    }

    public ColorMatrix getColorMatrix() {
        return mColorMatrix;
    }
}

```


 - ![image](https://github.com/maiwenchang/ColorFilterView/raw/master/art/colorFilter_01.png)
 
 - ![image](https://github.com/maiwenchang/ColorFilterView/raw/master/art/colorFilter_02.png)
