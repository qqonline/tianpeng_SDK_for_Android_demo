package com.tianpeng.tp_adsdk.tpadmobsdk.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.tianpeng.tp_adsdk.R;

/**
 * Created by YuHong on 2019/1/7 0007.
 */
public class DonutProgress extends View {
    private Paint c;
    private Paint d;
    private Paint e;
    protected Paint a;
    protected Paint b;
    private RectF f;
    private RectF g;
    private int h;
    private boolean i;
    private float j;
    private int k;
    private int l;
    private float m;
    private int n;
    private int o;
    private int p;
    private int q;
    private float r;
    private float s;
    private int t;
    private String u;
    private String v;
    private String w;
    private float x;
    private String y;
    private float z;
    private final float A;
    private final int B;
    private final int C;
    private final int D;
    private final int E;
    private final int F;
    private final int G;
    private final int H;
    private final float I;
    private final float J;
    private final int K;

    public DonutProgress(Context var1) {
        this(var1, (AttributeSet)null);
    }

    public DonutProgress(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public DonutProgress(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.f = new RectF();
        this.g = new RectF();
        this.h = 0;
        this.m = 0.0F;
        this.u = "";
        this.v = "%";
        this.w = null;
        this.B = Color.rgb(66, 145, 241);
        this.C = Color.rgb(204, 204, 204);
        this.D = Color.rgb(66, 145, 241);
        this.E = Color.rgb(66, 145, 241);
        this.F = 0;
        this.G = 100;
        this.H = 0;
        this.I = ResourceUtil.scaledDensityFloat(this.getResources(), 18.0F);
        this.K = (int)ResourceUtil.densityFloat(this.getResources(), 100.0F);
        this.A = ResourceUtil.densityFloat(this.getResources(), 10.0F);
        this.J = ResourceUtil.scaledDensityFloat(this.getResources(), 18.0F);
        TypedArray var4 = var1.getTheme().obtainStyledAttributes(var2, R.styleable.DonutProgress, var3, 0);
        this.a(var4);
        var4.recycle();
        this.a();
    }

    protected void a() {
        if (this.i) {
            this.a = new TextPaint();
            this.a.setColor(this.k);
            this.a.setTextSize(this.j);
            this.a.setAntiAlias(true);
            this.b = new TextPaint();
            this.b.setColor(this.l);
            this.b.setTextSize(this.x);
            this.b.setAntiAlias(true);
        }

        this.c = new Paint();
        this.c.setColor(this.o);
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setAntiAlias(true);
        this.c.setStrokeWidth(this.r);
        this.d = new Paint();
        this.d.setColor(this.p);
        this.d.setStyle(Paint.Style.STROKE);
        this.d.setAntiAlias(true);
        this.d.setStrokeWidth(this.s);
        this.e = new Paint();
        this.e.setColor(this.t);
        this.e.setAntiAlias(true);
    }

    protected void a(TypedArray var1) {
        this.o = var1.getColor(R.styleable.DonutProgress_donut_finished_color, this.B);
        this.p = var1.getColor(R.styleable.DonutProgress_donut_unfinished_color, this.C);
        this.i = var1.getBoolean(R.styleable.DonutProgress_donut_show_text, true);
        this.h = var1.getResourceId(R.styleable.DonutProgress_donut_inner_drawable, 0);
        this.setMax(var1.getInt(R.styleable.DonutProgress_donut_max, 100));
        this.setProgress(var1.getFloat(R.styleable.DonutProgress_donut_progress, 0.0F));
        this.r = var1.getDimension(R.styleable.DonutProgress_donut_finished_stroke_width, this.A);
        this.s = var1.getDimension(R.styleable.DonutProgress_donut_unfinished_stroke_width, this.A);
        if (this.i) {
            if (var1.getString(R.styleable.DonutProgress_donut_prefix_text) != null) {
                this.u = var1.getString(R.styleable.DonutProgress_donut_prefix_text);
            }

            if (var1.getString(R.styleable.DonutProgress_donut_suffix_text) != null) {
                this.v = var1.getString(R.styleable.DonutProgress_donut_suffix_text);
            }

            if (var1.getString(R.styleable.DonutProgress_donut_text) != null) {
                this.w = var1.getString(R.styleable.DonutProgress_donut_text);
            }

            this.k = var1.getColor(R.styleable.DonutProgress_donut_text_color, this.D);
            this.j = var1.getDimension(R.styleable.DonutProgress_donut_text_size, this.I);
            this.x = var1.getDimension(R.styleable.DonutProgress_donut_inner_bottom_text_size, this.J);
            this.l = var1.getColor(R.styleable.DonutProgress_donut_inner_bottom_text_color, this.E);
            this.y = var1.getString(R.styleable.DonutProgress_donut_inner_bottom_text);
        }

        this.x = var1.getDimension(R.styleable.DonutProgress_donut_inner_bottom_text_size, this.J);
        this.l = var1.getColor(R.styleable.DonutProgress_donut_inner_bottom_text_color, this.E);
        this.y = var1.getString(R.styleable.DonutProgress_donut_inner_bottom_text);
        this.q = var1.getInt(R.styleable.DonutProgress_donut_circle_starting_degree, 0);
        this.t = var1.getColor(R.styleable.DonutProgress_donut_background_color, 0);
    }

    public void invalidate() {
        this.a();
        super.invalidate();
    }

    public void setShowText(boolean var1) {
        this.i = var1;
    }

    public float getFinishedStrokeWidth() {
        return this.r;
    }

    public void setFinishedStrokeWidth(float var1) {
        this.r = var1;
        this.invalidate();
    }

    public float getUnfinishedStrokeWidth() {
        return this.s;
    }

    public void setUnfinishedStrokeWidth(float var1) {
        this.s = var1;
        this.invalidate();
    }

    private float getProgressAngle() {
        return this.getProgress() / (float)this.n * 360.0F;
    }

    public float getProgress() {
        return this.m;
    }

    public void setProgress(float var1) {
        this.m = var1;
        if (this.m > (float)this.getMax()) {
            this.m %= (float)this.getMax();
        }

        this.invalidate();
    }

    public int getMax() {
        return this.n;
    }

    public void setMax(int var1) {
        if (var1 > 0) {
            this.n = var1;
            this.invalidate();
        }

    }

    public float getTextSize() {
        return this.j;
    }

    public void setTextSize(float var1) {
        this.j = var1;
        this.invalidate();
    }

    public int getTextColor() {
        return this.k;
    }

    public void setTextColor(int var1) {
        this.k = var1;
        this.invalidate();
    }

    public int getFinishedStrokeColor() {
        return this.o;
    }

    public void setFinishedStrokeColor(int var1) {
        this.o = var1;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return this.p;
    }

    public void setUnfinishedStrokeColor(int var1) {
        this.p = var1;
        this.invalidate();
    }

    public String getText() {
        return this.w;
    }

    public void setText(String var1) {
        this.w = var1;
        this.invalidate();
    }

    public String getSuffixText() {
        return this.v;
    }

    public void setSuffixText(String var1) {
        this.v = var1;
        this.invalidate();
    }

    public String getPrefixText() {
        return this.u;
    }

    public void setPrefixText(String var1) {
        this.u = var1;
        this.invalidate();
    }

    public int getInnerBackgroundColor() {
        return this.t;
    }

    public void setInnerBackgroundColor(int var1) {
        this.t = var1;
        this.invalidate();
    }

    public String getInnerBottomText() {
        return this.y;
    }

    public void setInnerBottomText(String var1) {
        this.y = var1;
        this.invalidate();
    }

    public float getInnerBottomTextSize() {
        return this.x;
    }

    public void setInnerBottomTextSize(float var1) {
        this.x = var1;
        this.invalidate();
    }

    public int getInnerBottomTextColor() {
        return this.l;
    }

    public void setInnerBottomTextColor(int var1) {
        this.l = var1;
        this.invalidate();
    }

    public int getStartingDegree() {
        return this.q;
    }

    public void setStartingDegree(int var1) {
        this.q = var1;
        this.invalidate();
    }

    public int getAttributeResourceId() {
        return this.h;
    }

    public void setAttributeResourceId(int var1) {
        this.h = var1;
    }

    protected void onMeasure(int var1, int var2) {
        this.setMeasuredDimension(this.a(var1), this.a(var2));
        this.z = (float)(this.getHeight() - this.getHeight() * 3 / 4);
    }

    private int a(int var1) {
        int var3 = MeasureSpec.getMode(var1);
        int var4 = MeasureSpec.getSize(var1);
        int var2;
        if (var3 == 1073741824) {
            var2 = var4;
        } else {
            var2 = this.K;
            if (var3 == -2147483648) {
                var2 = Math.min(var2, var4);
            }
        }

        return var2;
    }

    protected void onDraw(Canvas var1) {
        super.onDraw(var1);
        float var2 = Math.max(this.r, this.s);
        this.f.set(var2, var2, (float)this.getWidth() - var2, (float)this.getHeight() - var2);
        this.g.set(var2, var2, (float)this.getWidth() - var2, (float)this.getHeight() - var2);
        float var3 = ((float)this.getWidth() - Math.min(this.r, this.s) + Math.abs(this.r - this.s)) / 2.0F;
        var1.drawCircle((float)this.getWidth() / 2.0F, (float)this.getHeight() / 2.0F, var3, this.e);
        var1.drawArc(this.f, (float)this.getStartingDegree(), this.getProgressAngle(), false, this.c);
        var1.drawArc(this.g, (float)this.getStartingDegree() + this.getProgressAngle(), 360.0F - this.getProgressAngle(), false, this.d);
        if (this.i) {
            String var4 = this.w != null ? this.w : this.u + this.m + this.v;
            float var5;
            if (!TextUtils.isEmpty(var4)) {
                var5 = this.a.descent() + this.a.ascent();
                var1.drawText(var4, ((float)this.getWidth() - this.a.measureText(var4)) / 2.0F, ((float)this.getWidth() - var5) / 2.0F, this.a);
            }

            if (!TextUtils.isEmpty(this.getInnerBottomText())) {
                this.b.setTextSize(this.x);
                var5 = (float)this.getHeight() - this.z - (this.a.descent() + this.a.ascent()) / 2.0F;
                var1.drawText(this.getInnerBottomText(), ((float)this.getWidth() - this.b.measureText(this.getInnerBottomText())) / 2.0F, var5, this.b);
            }
        }

        if (this.h != 0) {
            Bitmap var6 = BitmapFactory.decodeResource(this.getResources(), this.h);
            var1.drawBitmap(var6, (float)(this.getWidth() - var6.getWidth()) / 2.0F, (float)(this.getHeight() - var6.getHeight()) / 2.0F, (Paint)null);
        }

    }

    protected Parcelable onSaveInstanceState() {
        Bundle var1 = new Bundle();
        var1.putParcelable("saved_instance", super.onSaveInstanceState());
        var1.putInt("text_color", this.getTextColor());
        var1.putFloat("text_size", this.getTextSize());
        var1.putFloat("inner_bottom_text_size", this.getInnerBottomTextSize());
        var1.putFloat("inner_bottom_text_color", (float)this.getInnerBottomTextColor());
        var1.putString("inner_bottom_text", this.getInnerBottomText());
        var1.putInt("inner_bottom_text_color", this.getInnerBottomTextColor());
        var1.putInt("finished_stroke_color", this.getFinishedStrokeColor());
        var1.putInt("unfinished_stroke_color", this.getUnfinishedStrokeColor());
        var1.putInt("max", this.getMax());
        var1.putInt("starting_degree", this.getStartingDegree());
        var1.putFloat("progress", this.getProgress());
        var1.putString("suffix", this.getSuffixText());
        var1.putString("prefix", this.getPrefixText());
        var1.putString("text", this.getText());
        var1.putFloat("finished_stroke_width", this.getFinishedStrokeWidth());
        var1.putFloat("unfinished_stroke_width", this.getUnfinishedStrokeWidth());
        var1.putInt("inner_background_color", this.getInnerBackgroundColor());
        var1.putInt("inner_drawable", this.getAttributeResourceId());
        return var1;
    }

    protected void onRestoreInstanceState(Parcelable var1) {
        if (var1 instanceof Bundle) {
            Bundle var2 = (Bundle)var1;
            this.k = var2.getInt("text_color");
            this.j = var2.getFloat("text_size");
            this.x = var2.getFloat("inner_bottom_text_size");
            this.y = var2.getString("inner_bottom_text");
            this.l = var2.getInt("inner_bottom_text_color");
            this.o = var2.getInt("finished_stroke_color");
            this.p = var2.getInt("unfinished_stroke_color");
            this.r = var2.getFloat("finished_stroke_width");
            this.s = var2.getFloat("unfinished_stroke_width");
            this.t = var2.getInt("inner_background_color");
            this.h = var2.getInt("inner_drawable");
            this.a();
            this.setMax(var2.getInt("max"));
            this.setStartingDegree(var2.getInt("starting_degree"));
            this.setProgress(var2.getFloat("progress"));
            this.u = var2.getString("prefix");
            this.v = var2.getString("suffix");
            this.w = var2.getString("text");
            super.onRestoreInstanceState(var2.getParcelable("saved_instance"));
        } else {
            super.onRestoreInstanceState(var1);
        }
    }

    public void setDonut_progress(String var1) {
        if (!TextUtils.isEmpty(var1)) {
            this.setProgress((float)Integer.parseInt(var1));
        }

    }
}

