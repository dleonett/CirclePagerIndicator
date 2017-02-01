package com.example.daniel.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

    private void buildCircles() {
        Log.d(TAG, "buildCircles()");

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (2 * scale + 0.5f);

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            View circle = new View(getContext());

            circle.setBackgroundResource(R.drawable.circle);

            ((GradientDrawable) circle.getBackground()).setColor(unselectedColor);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(unselectedSize, unselectedSize);

            params.setMargins(padding, 0, padding, 0);
            circle.setLayoutParams(params);
            addView(circle);
        }

        setIndicator(0);
    }

    private void setIndicator(int index) {
        Log.d(TAG, "setIndicator()");

        if (index < viewPager.getAdapter().getCount()) {
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                View circle = getChildAt(i);
                int size;

                if (i == index) {
                    ((GradientDrawable) circle.getBackground()).setColor(selectedColor);
                    size = selectedSize;
                } else {
                    ((GradientDrawable) circle.getBackground()).setColor(unselectedColor);
                    size = unselectedSize;
                }

                LayoutParams params = (LayoutParams) circle.getLayoutParams();

                params.width = size;
                params.height = size;

                circle.setLayoutParams(params);
            }
        }
    }
}
