package com.pyj.videodownload.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.pyj.videodownload.R;

public class LoadingSuccessView extends RelativeLayout {
    private Context mContext;
    private FrameLayout.LayoutParams params;
    private View loadingLayout;
    private View noDataLayout;
    private View networkErrorLayout;

    private LinearLayout rootLoading;
    private LinearLayout rootNoData;
    private LinearLayout rootNetworkError;

    private LottieAnimationView animationView;

    public LoadingSuccessView(Context context) {
        this(context, null);
    }

    public LoadingSuccessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingSuccessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        int loadingLayoutId = R.layout.layout_loading;
        int noDataLayoutId = R.layout.layout_nodata;
        int networkErrorLayoutId = R.layout.layout_network_error;

        LayoutInflater.from(context).inflate(R.layout.custom_loadingsuccess, this);

        rootLoading = findViewById(R.id.rootLoading);
        rootNoData = findViewById(R.id.rootNoData);
        rootNetworkError = findViewById(R.id.rootNetworkError);

        if (attrs != null) {
            TypedArray loadigSuccess = context.obtainStyledAttributes(attrs,
                    R.styleable.LoadingSuccessView);

            try {
                loadingLayoutId = loadigSuccess.getResourceId(R.styleable.LoadingSuccessView_loadingLayout, loadingLayoutId);
                noDataLayoutId = loadigSuccess.getResourceId(R.styleable.LoadingSuccessView_noDataLayout, noDataLayoutId);
                networkErrorLayoutId = loadigSuccess.getResourceId(R.styleable.LoadingSuccessView_networkErrorLayout, networkErrorLayoutId);
            } finally {
                loadigSuccess.recycle();
            }
        }
        loadingLayout = inflate(context, loadingLayoutId, null);
        noDataLayout = inflate(context, noDataLayoutId, null);
        networkErrorLayout = inflate(context, networkErrorLayoutId, null);

        rootLoading.addView(loadingLayout);
        rootNoData.addView(noDataLayout);
        rootNetworkError.addView(networkErrorLayout);

        animationView = loadingLayout.findViewById(R.id.animatorView);
    }

    public void loadingStrat() {
        rootLoading.setVisibility(VISIBLE);
        rootNoData.setVisibility(GONE);
        rootNetworkError.setVisibility(GONE);

        if (animationView != null ) {
            animationView.playAnimation();
        }
    }

    public void loadNoData() {
        rootLoading.setVisibility(GONE);
        rootNoData.setVisibility(VISIBLE);
        rootNetworkError.setVisibility(GONE);

        if (animationView != null && animationView.isAnimating()) {
            animationView.cancelAnimation();
        }
    }

    public void loadSuccess() {
        rootLoading.setVisibility(VISIBLE);
        rootNoData.setVisibility(GONE);
        rootNetworkError.setVisibility(GONE);

        if (animationView != null && animationView.isAnimating()) {
            animationView.cancelAnimation();
        }
    }

    public void loadNetworkError() {
        rootLoading.setVisibility(GONE);
        rootNoData.setVisibility(GONE);
        rootNetworkError.setVisibility(VISIBLE);

        if (animationView != null && animationView.isAnimating()) {
            animationView.cancelAnimation();
        }
    }
}
