package com.zhide.app.view.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.zhide.app.utils.UIUtils;


/**
 * Created by admin.
 * @author Tim
 */

public class CircleProgressBar extends View {

	private RectF mColorWheelRectangle = new RectF();
	private Paint mDefaultWheelPaint;
	private Paint mColorWheelPaint;
	private Paint textPaint;
	private float mColorWheelRadius;
	private float circleStrokeWidth;
	private float pressExtraStrokeWidth;
	private String mText;
	private int mCount;
	private float mSweepAnglePer;
	private float mSweepAngle;
	private int mTextSize;
	BarAnimation anim;
	public CircleProgressBar(Context context) {
		super(context);
		init(null, 0);
	}

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {

		circleStrokeWidth = UIUtils.dipToPx(getContext(), 2);
		pressExtraStrokeWidth = UIUtils.dipToPx(getContext(), 2);
		mTextSize = UIUtils.dipToPx(getContext(), 40);

		mColorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mColorWheelPaint.setColor(0xFFFFFFFF); //圆环进度颜色
		mColorWheelPaint.setStyle(Style.STROKE);
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);

		mDefaultWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDefaultWheelPaint.setColor(0xFF5F52A0);  //外表圆环颜色
		mDefaultWheelPaint.setStyle(Style.STROKE);
		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
		textPaint.setColor(0x00000000);  //圆中间文字颜色
		textPaint.setStyle(Style.FILL_AND_STROKE);
		textPaint.setTextAlign(Align.LEFT);
		textPaint.setTextSize(mTextSize);

		mText = "0";
		mSweepAngle = 0;

		anim = new BarAnimation();
		anim.setDuration(1500);

	}
	Rect bounds = new Rect();

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawArc(mColorWheelRectangle, -90, 360, false, mDefaultWheelPaint);
		canvas.drawArc(mColorWheelRectangle, -90, mSweepAnglePer, false, mColorWheelPaint);
		String textstr = mCount + "";
		textPaint.getTextBounds(textstr, 0, textstr.length(), bounds);
		canvas.drawText(textstr + "", (mColorWheelRectangle.centerX()) - (textPaint.measureText(textstr) / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2, textPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int min = Math.min(width, height);
		setMeasuredDimension(min, min);
		mColorWheelRadius = min - circleStrokeWidth - pressExtraStrokeWidth;

		mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth, circleStrokeWidth + pressExtraStrokeWidth, mColorWheelRadius,
				mColorWheelRadius);
	}

	@Override
	public void setPressed(boolean pressed) {

		if (pressed) {
			mColorWheelPaint.setColor(0xFF165da6);
			textPaint.setColor(0xFF070707);
			mColorWheelPaint.setStrokeWidth(circleStrokeWidth + pressExtraStrokeWidth);
			mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth + pressExtraStrokeWidth);
			textPaint.setTextSize(mTextSize - pressExtraStrokeWidth);
		} else {
			mColorWheelPaint.setColor(0xFF29a6f6);
			textPaint.setColor(0xFF333333);
			mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
			mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
			textPaint.setTextSize(mTextSize);
		}
		super.setPressed(pressed);
		this.invalidate();
	}

	public void startCustomAnimation(Animation.AnimationListener animationListener) {
		this.startAnimation(anim);
		anim.setAnimationListener(animationListener);
	}
	public void clearCustomAnimation() {
		this.clearAnimation();
		mSweepAnglePer=0;
	}
	public void setText(String text) {
		mText = text;
		this.startAnimation(anim);
	}
	public void setSweepAngle(float sweepAngle) {
		mSweepAngle = sweepAngle;

	}

	public class BarAnimation extends Animation {
		/**
		 * Initializes expand collapse animation, has two types, collapse (1) and expand (0).
		 * 1 will collapse view and set to gone
		 */
		public BarAnimation() {

		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				mSweepAnglePer = interpolatedTime * mSweepAngle;
				mCount = (int) (interpolatedTime * Float.parseFloat(mText));
			} else {
				mSweepAnglePer = mSweepAngle;
				mCount = Integer.parseInt(mText);
			}
			postInvalidate();
		}

	}

}
