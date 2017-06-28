package dowload.com.numberprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by BoBoZhu on 2017/6/23.
 */

public class HProgress extends View {

    int hProgress_outline_color = 0xFFFFFFFF;//外边框颜色
    int hProgress_color = 0xFFFFFFFF; //进度条颜色
    int hProgress_circle_color = 0xFFD1F4DE;  //圆圈颜色
    int hProgress_text_color = 0xFFFFFFFF;  //进度字体颜色
    int hProgress_text_bg_color = 0x50FFFFFF;  //进度背景颜色
    int hProgress_circle_height = 56;  //圆圈高度
    int hProgress_height = 25;  //progress高度
    int hProgress_bar_height = 15;  //progress进度条高度
    int hProgress_text_height = 40;  //进度文字高度
    int hProgress_text_paddingH = 40;  //进度文字左右padding
    int hProgress_text_paddingV = 0;  //进度文字上下pading
    int hProgress_text_size = 38;  //进度文字字体大小
    int maxProgress = 100;
    int hProgress_progress_bar = 0;

    Paint outLinePaint;
    Paint progressPaint;
    Paint cirClePaint;
    Paint textPain;
    Paint textBgPain;

    public HProgress(Context context) {
        this(context, null);
    }

    public HProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefStyleAttr(attrs);
        outLinePaint = new Paint();
        outLinePaint.setColor(hProgress_outline_color);
        outLinePaint.setAntiAlias(true);
        outLinePaint.setStrokeWidth(2);
        outLinePaint.setStyle(Paint.Style.STROKE);//设置空心   p.setStyle(Paint.Style.STROKE);//设置空心

        progressPaint = new Paint();
        progressPaint.setColor(hProgress_color);
        progressPaint.setAntiAlias(true);

        cirClePaint = new Paint();
        cirClePaint.setColor(hProgress_circle_color);
        cirClePaint.setAntiAlias(true);

        textBgPain = new Paint();
        textBgPain.setColor(hProgress_text_bg_color);
        textBgPain.setAntiAlias(true);


        textPain = new Paint();
        textPain.setColor(hProgress_text_color);
        textPain.setAntiAlias(true);
        textPain.setTextSize(hProgress_text_size);
        textPain.setTextAlign(Paint.Align.CENTER);
        textPain.setTextSize(hProgress_text_size);

    }


    private void initDefStyleAttr(AttributeSet attrs) {

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.HProgress);
        hProgress_outline_color = attributes.getColor(R.styleable.HProgress_hProgress_outline_color, hProgress_outline_color);
        hProgress_color = attributes.getColor(R.styleable.HProgress_hProgress_color, hProgress_color);
        hProgress_circle_color = attributes.getColor(R.styleable.HProgress_hProgress_circle_color, hProgress_circle_color);
        hProgress_text_color = attributes.getColor(R.styleable.HProgress_hProgress_text_color, hProgress_text_color);
        hProgress_text_bg_color = attributes.getColor(R.styleable.HProgress_hProgress_text_bg_color, hProgress_text_bg_color);
        hProgress_circle_height = (int) attributes.getDimension(R.styleable.HProgress_hProgress_circle_height, hProgress_circle_height);
        hProgress_height = (int) attributes.getDimension(R.styleable.HProgress_hProgress_height, hProgress_height);
        hProgress_bar_height = (int) attributes.getDimension(R.styleable.HProgress_hProgress_bar_height, hProgress_bar_height);
        hProgress_text_height = (int) attributes.getDimension(R.styleable.HProgress_hProgress_text_height, hProgress_text_height);
        hProgress_text_size = (int) attributes.getDimension(R.styleable.HProgress_hProgress_text_size, hProgress_text_size);
        hProgress_text_paddingH = (int) attributes.getDimension(R.styleable.HProgress_hProgress_text_paddingH, hProgress_text_paddingH);
        hProgress_text_paddingV = (int) attributes.getDimension(R.styleable.HProgress_hProgress_text_paddingV, hProgress_text_paddingV);
        hProgress_progress_bar = attributes.getInteger(R.styleable.HProgress_hProgress_progress_bar, hProgress_progress_bar);
        maxProgress = attributes.getInteger(R.styleable.HProgress_hProgress_maxProgress, maxProgress);

    }

    int progressWidth;

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        progressWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(progressWidth, (hProgress_text_height + hProgress_circle_height));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        textPain.getTextBounds(getProgress() + "", 0, (getProgress() + "").length(), rect);


        int w = rect.width();
        int h = rect.height();

        int moveX = hProgress_circle_height / 2 + (progressWidth - hProgress_circle_height) * getProgress() / maxProgress;
        int startX;
        int endX;
        if (moveX - w / 2 - hProgress_text_paddingH < 0) {
            startX = 0;
            endX = startX + w + hProgress_text_paddingH * 2;
        } else if (moveX + w / 2 + hProgress_text_paddingH >= progressWidth) {
            startX = progressWidth - hProgress_text_paddingH * 2 - w;
            endX = progressWidth;
        } else {
            startX = moveX - w / 2 - hProgress_text_paddingH;
            endX = moveX + w / 2 + hProgress_text_paddingH;
        }

        //画进度字背景框
        RectF rectF2 = new RectF(startX,
                hProgress_circle_height,
                endX,
                hProgress_circle_height + hProgress_text_height);
        canvas.drawRoundRect(rectF2, hProgress_text_height / 2, hProgress_text_height / 2, textBgPain);


        Paint.FontMetrics fontMetrics = textPain.getFontMetrics();
        canvas.drawText(getProgress() + "",
                (startX + endX) / 2,
                hProgress_circle_height + hProgress_text_height / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent,
                textPain);


        int spaceA = (hProgress_circle_height - hProgress_height) / 2;
        int spaceB = (hProgress_height - hProgress_bar_height) / 2;
        //画progressBar 外边
        RectF rectF = new RectF(hProgress_circle_height / 2,
                spaceA,
                progressWidth - hProgress_circle_height / 2, hProgress_height + spaceA);
        canvas.drawRoundRect(rectF, hProgress_height / 2, hProgress_height / 2, outLinePaint);

        //画progressBar 内进度条
        RectF rectF1 = new RectF(hProgress_circle_height / 2 + spaceB,
                spaceA + spaceB,
                hProgress_bar_height + spaceB + (progressWidth - spaceA - spaceB) * getProgress() / maxProgress,
                hProgress_bar_height + spaceB + spaceA);

        canvas.drawRoundRect(rectF1, hProgress_bar_height / 2, hProgress_bar_height / 2, progressPaint);

        //画圆
        canvas.drawCircle(moveX,
                hProgress_circle_height / 2,
                hProgress_circle_height / 2, cirClePaint);


    }


    public int getProgress() {
        return hProgress_progress_bar;
    }

    public void setProgress(int progress) {
        if (progress > maxProgress) {
            throw new RuntimeException("progress mast less than  maxProgress");
        }
        hProgress_progress_bar = progress;
        postInvalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
