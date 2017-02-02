package com.example.daniel.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class CirclesIndicator extends LinearLayout {

    private static final String TAG = CirclesIndicator.class.getSimpleName();

    private int selectedColor;
    private int unselectedColor;
    private int selectedSize;
    private int unselectedSize;

    private int currentPosition;
    private ViewPager viewPager;

    public CirclesIndicator(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CirclesIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CirclesIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CirclesIndicator, defStyle, 0);

        this.selectedColor = a.getColor(R.styleable.CirclesIndicator_circleColorSelected,
                ContextCompat.getColor(context, R.color.colorAccent));
        this.unselectedColor = a.getColor(R.styleable.CirclesIndicator_circleColorUnselected,
                ContextCompat.getColor(context, R.color.colorPrimary));
        this.selectedSize = a.getDimensionPixelSize(R.styleable.CirclesIndicator_circleSizeSelected,
                getResources().getDimensionPixelSize(R.dimen.circle_indicator_unselected));
        this.unselectedSize = a.getDimensionPixelSize(R.styleable.CirclesIndicator_circleSizeUnselected,
                getResources().getDimensionPixelSize(R.dimen.circle_indicator_unselected));

        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        if (this.viewPager == null) {
            return;
        }

        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buildCircles();
    }

    public void setCurrentPosition(int position) {
        setIndicator(position);
    }

    private void buildCircles() {
        Log.d(TAG, "buildCircles()");

        removeAllViews();

        int padding = DensityUtils.dpToPx(3);
        int circlesCount = viewPager.getAdapter().getCount();

        for (int i = 0; i < circlesCount; i++) {
            View circle = new View(getContext());

            circle.setBackgroundResource(R.drawable.circle);

            ((GradientDrawable) circle.getBackground()).setColor(unselectedColor);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(unselectedSize, unselectedSize);

            params.setMargins(padding, 0, padding, 0);
            circle.setLayoutParams(params);
            addView(circle);
        }

        setMinimumHeight(selectedSize);

        setIndicator(0);
    }

    private void setIndicator(int newPosition) {
        Log.d(TAG, "setIndicator()");

        if (viewPager == null) {
            return;
        }

        if (newPosition < viewPager.getAdapter().getCount()) {
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                final View circle = getChildAt(i);
                final int size;

                if (i == newPosition) {
                    ((GradientDrawable) circle.getBackground()).setColor(selectedColor);
                    ValueAnimator animator = ValueAnimator.ofInt(unselectedSize, selectedSize);
                    animator.setDuration(200);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int newRadius = (int) animation.getAnimatedValue();

                            LayoutParams params = (LayoutParams) circle.getLayoutParams();

                            params.width = newRadius;
                            params.height = newRadius;

                            circle.setLayoutParams(params);
                        }
                    });
                    animator.start();
                } else {
                    ((GradientDrawable) circle.getBackground()).setColor(unselectedColor);

                    if (i == currentPosition) {
                        ValueAnimator animator = ValueAnimator.ofInt(selectedSize, unselectedSize);
                        animator.setDuration(200);
                        animator.setInterpolator(new DecelerateInterpolator());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int newRadius = (int) animation.getAnimatedValue();

                                LayoutParams params = (LayoutParams) circle.getLayoutParams();

                                params.width = newRadius;
                                params.height = newRadius;

                                circle.setLayoutParams(params);
                            }
                        });
                        animator.start();
                    } else {
                        size = unselectedSize;

                        LayoutParams params = (LayoutParams) circle.getLayoutParams();

                        params.width = size;
                        params.height = size;

                        circle.setLayoutParams(params);
                    }
                }
            }
        }

        currentPosition = newPosition;
    }
}
