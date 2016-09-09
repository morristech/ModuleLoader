package io.github.hotstu.common.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class LoadMoreListView extends ListView {
    //private LinearLayout footView;// 加载更多视图（底部视图）
    private ViewHolder viewHolder;//底部视图的包装类
    private boolean hasMoreData = true; // 是否还有更多数据
    final private int footHeight;
    private OnGetMoreListener onLoadMoreListener;
    private boolean isLoadMoreing;

    public LoadMoreListView(Context context) {
        this(context, null);
        onFinishInflate();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        footHeight = (int) ((getResources().getDisplayMetrics().density * 40)  + .5f);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    //初始化
    private void init() {
        //底部
    	LinearLayout footView = new LinearLayout(getContext());
        footView.setOrientation(LinearLayout.VERTICAL);
        footView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, footHeight));
        footView.setVisibility(View.GONE);
        //footView.setBackgroundResource(R.drawable.ic_launcher);
        addFooterView(footView);
        viewHolder = new ViewHolder(footView);
        // 滑动监听
        setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            	Log.e("setOnScrollListener", "onScroll: " + canLoadMore());
                if (!canLoadMore()) {
                    return;
                }
                onPreLoadMore();
            }
        });
    }


    //是否允许加载更多
    private boolean canLoadMore() {
        if (onLoadMoreListener == null) {
            return false;
        }
        if (isLoadMoreing) {
            return false;
        }
        if (!hasMoreData) {
            return false;
        }
        if (getAdapter() == null) {
            return false;
        }
        if (!canScroll(1) && !canScroll(-1)) {
            return false;
        }
        if (getLastVisiblePosition() != getAdapter().getCount() - 1) {
            return false;
        }
        return true;
    }

    //判断ListView是否可以滑动(针对ListView未铺满屏幕的情况)
    private boolean canScroll(int direction) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        final int firstPosition = getFirstVisiblePosition();
        final int listPaddingTop = getPaddingTop();
        final int listPaddingBottom = getPaddingBottom();
        final int itemCount = getAdapter().getCount();

        if (direction > 0) {
            final int lastBottom = getChildAt(childCount - 1).getBottom();
            final int lastPosition = firstPosition + childCount;
            return lastPosition < itemCount || lastBottom > getHeight() - listPaddingBottom;
        } else {
            final int firstTop = getChildAt(0).getTop();
            return firstPosition > 0 || firstTop < listPaddingTop;
        }
    }

    //准备加载更多
    private void onPreLoadMore() {
        isLoadMoreing = true;
        showLoading();
        if (onLoadMoreListener != null) {
            onLoadMoreListener.onGetMore();
        }
    }

    //显示加载视图
    private void showLoading() {
        viewHolder.setVisibility(View.VISIBLE);
        viewHolder.setText("正在加载...");
    }

    //隐藏加载视图
    private void dismissLoading() {
        viewHolder.setVisibility(View.GONE);
        viewHolder.setText("加载完成");
    }

    //移除加载视图
    private void removeLoading() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float fraction = animator.getAnimatedFraction();
                int padding = (int) (-footHeight * fraction);
                viewHolder.setPadding(0, padding, 0, 0);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismissLoading();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
    
    /*暴露的：设置加载完成*/
    public void setLoadMoreComplete() {
        isLoadMoreing = false;
        if (hasMoreData) {
            dismissLoading();
        } else {
            removeLoading();
        }
    }

    //设置回调监听
    public void setOnGetMoreListener(OnGetMoreListener getMoreListener) {
        this.onLoadMoreListener = getMoreListener;
    }

    //设置是否还有更多数据
    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (hasMoreData ) {
            viewHolder.setPadding(0, 0, 0, 0);
        }
    }

    //获取加载状态
    public boolean isLoadMoreing() {
        return isLoadMoreing;
    }

    // 回调接口
    public interface OnGetMoreListener {
        void onGetMore();
    }

    //底部加载视图的包装类
   private static final class ViewHolder {
    	private final View footGroup;
        ViewHolder(View view) {
        	this.footGroup = view;
        }
        public void setText(CharSequence text) {
        	Log.e("ViewHolder", "setText:" + text);
        }
        
        public void setVisibility(int visible) {
        	Log.e("ViewHolder", "setVisibility:" + visible);
        	this.footGroup.setVisibility(visible);
        }
        
        public void setPadding(int left, int top, int right, int bottom) {
        	this.footGroup.setPadding(left, top, right, bottom);
        }
    }

}
