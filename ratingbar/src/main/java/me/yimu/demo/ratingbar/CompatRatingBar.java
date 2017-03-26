package me.yimu.demo.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by linwei on 2017/3/20.
 *
 * 原生RatingBar在部分华为手机上会出现评分错位的bug
 * 做了一个简单的RatingBar实现
 *
 * @attr ref R.styleable#CompatRatingBar_crbNumStars
 * @attr ref R.styleable#CompatRatingBar_crbValue
 * @attr ref R.styleable#CompatRatingBar_crbBackgroundOn
 * @attr ref R.styleable#CompatRatingBar_crbBackgroundOff
 */
public class CompatRatingBar extends LinearLayout {

    private int mNumStars;
    private int mRating;
    private Drawable mBackgroundOn;
    private Drawable mBackgroundOff;
    protected OnRatingBarChangeListener mListener;

    public interface OnRatingBarChangeListener {
        void onRatingChanged(CompatRatingBar ratingBar, int rating);
    }

    public CompatRatingBar(Context context) {
        this(context, null);
    }

    public CompatRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CompatRatingBar);
        try {
            mNumStars = a.getInteger(R.styleable.CompatRatingBar_crbNumStars, 0);
            mRating = a.getInteger(R.styleable.CompatRatingBar_crbRating, 0);
            mBackgroundOn = a.getDrawable(R.styleable.CompatRatingBar_crbBackgroundOn);
            mBackgroundOff = a.getDrawable(R.styleable.CompatRatingBar_crbBackgroundOff);
        } finally {
            a.recycle();
        }
        setOrientation(HORIZONTAL);
        updateStars();
    }

    protected void updateStars() {
        removeAllViews();
        for (int i = 0; i < mNumStars; i++) {
            ImageView item = new ImageView(getContext());
            if (i < mRating) {
                item.setBackgroundDrawable(mBackgroundOn);
            } else {
                item.setBackgroundDrawable(mBackgroundOff);
            }
            addView(item);
        }
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
        mListener = listener;
    }

    public void setNumStars(int num) {
        mNumStars = num;
        updateStars();
    }

    public int getNumStars() {
        return mNumStars;
    }

    public void setRating(int value) {
        mRating = value;
        updateStars();
    }

    public int getRating() {
        return mRating;
    }

    private void calcPosition(float ex) {
        if (mNumStars > 0) {
            int value = (int) Math.ceil((ex - getPaddingLeft()) /
                    ((getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / mNumStars));
            if (value > mNumStars) {
                value = mNumStars;
            } else if (value < 0) {
                value = 0;
            }
            if (value != mRating) {
                mRating = value;
                updateStars();
                if (mListener != null) {
                    mListener.onRatingChanged(this, mRating);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        final float ex = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                calcPosition(ex);
                break;
            case MotionEvent.ACTION_MOVE:
                calcPosition(ex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                calcPosition(ex);
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return true;
    }
}
