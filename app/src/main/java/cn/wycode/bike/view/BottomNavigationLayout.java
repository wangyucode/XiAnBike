package cn.wycode.bike.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wycode.bike.R;


/**
 * 底部导航栏，自动和ViewPager联动
 * Created by wy on 2015/11/13.
 */
public class BottomNavigationLayout extends LinearLayout implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    private static final String TAG = "BottomNavigationLayout";
    private ViewPager mViewPager;
    private SparseArray<Tab> mTabs;
    private int selectedTextColor = 0xFF0A8C09;
    private int unselectedTextColor = 0xFF737373;
    private Context mContext;
    LayoutInflater inflater;


    int selectedAlpha;
    int selectedRed;
    int selectedGreen;
    int selectedBlue;

    int unselectedAlpha;
    int unselectedRed;
    int unselectedGreen;
    int unselectedBlue;

    private OnTabSelectedListener mListener;

    private int lastSelectedPosition;


    public BottomNavigationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationLayout);
        selectedTextColor = a.getColor(R.styleable.BottomNavigationLayout_text_color_selected, selectedTextColor);
        unselectedTextColor = a.getColor(R.styleable.BottomNavigationLayout_text_color_unselected, unselectedTextColor);
        a.recycle();

        selectedAlpha = selectedTextColor >>> 24;
        selectedRed = selectedTextColor >> 16 & 0xFF;
        selectedGreen = selectedTextColor >> 8 & 0xFF;
        selectedBlue = selectedTextColor & 0xFF;

        unselectedAlpha = unselectedTextColor >>> 24;
        unselectedRed = unselectedTextColor >> 16 & 0xFF;
        unselectedGreen = unselectedTextColor >> 8 & 0xFF;
        unselectedBlue = unselectedTextColor & 0xFF;

    }

    public void setupWithTitle(String[] titles) {
        mTabs = new SparseArray<>(titles.length);
        for (int i = 0; i < titles.length; i++) {
            Tab tab = new Tab();
            View tabView = inflater.inflate(R.layout.tabs_main, this, false);
            tab.tabView = tabView;
            this.addView(tabView);
            tab.tabView = getChildAt(i);
            tab.tabView.setOnClickListener(this);
            tab.tabView.setTag(i);
            tab.ivSelected = (ImageView) tab.tabView.findViewById(R.id.iv_tab_main_selected);
            tab.ivUnSelected = (ImageView) tab.tabView.findViewById(R.id.iv_tab_main_unselected);
            tab.title = (TextView) tab.tabView.findViewById(R.id.tv_tab_main);
            tab.title.setText(titles[i]);
            mTabs.put(i, tab);
        }
        setIconSelected(lastSelectedPosition);
    }

    private void setIconSelected(int position) {
        for (int i = 0; i < mTabs.size(); i++) {
            Tab tab = mTabs.get(i);
            if (i == position) {
                tab.ivSelected.setVisibility(VISIBLE);
                tab.ivUnSelected.setVisibility(INVISIBLE);
                tab.title.setTextColor(selectedTextColor);
            }else{
                tab.ivSelected.setVisibility(INVISIBLE);
                tab.ivUnSelected.setVisibility(VISIBLE);
                tab.title.setTextColor(unselectedTextColor);
            }
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount() - 1);
        mTabs = new SparseArray<>();
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            Tab tab = new Tab();
            View tabView = inflater.inflate(R.layout.tabs_main, this, false);
            tab.tabView = tabView;
            this.addView(tabView);
            tab.tabView = getChildAt(i);
            tab.tabView.setOnClickListener(this);
            tab.tabView.setTag(i);
            tab.ivSelected = (ImageView) tab.tabView.findViewById(R.id.iv_tab_main_selected);
            tab.ivUnSelected = (ImageView) tab.tabView.findViewById(R.id.iv_tab_main_unselected);
            tab.title = (TextView) tab.tabView.findViewById(R.id.tv_tab_main);
            tab.title.setText(mViewPager.getAdapter().getPageTitle(i));
            mTabs.put(i, tab);
        }
//        onPageScrollStateChanged(lastSelectedPosition);
        onPageSelected(lastSelectedPosition);
    }


    /**
     * 设置选中与非选中状态的图片
     *
     * @param unSelectedImages 不选中的图片
     * @param selectedImages   不选中的图片
     */
    public void setTabImage(@DrawableRes int[] unSelectedImages, @DrawableRes int[] selectedImages) {
        if (unSelectedImages.length != selectedImages.length) {
            throw new IllegalArgumentException("非选中图片数量和选中图片数量不相等");
        }
        if (unSelectedImages.length != mTabs.size()) {
            throw new IllegalArgumentException("图片数量和Pager数量不相等");
        }
        for (int i = 0; i < unSelectedImages.length; i++) {
            Tab tab = mTabs.get(i);
            tab.ivUnSelected.setImageResource(unSelectedImages[i]);
            tab.ivSelected.setImageResource(selectedImages[i]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d(TAG, position + "---" + positionOffset);

        int alphaOffset = (int) ((selectedAlpha - unselectedAlpha) * positionOffset);
        int redOffset = (int) ((selectedRed - unselectedRed) * positionOffset);
        int greenOffset = (int) ((selectedGreen - unselectedGreen) * positionOffset);
        int blueOffset = (int) ((selectedBlue - unselectedBlue) * positionOffset);

        int color1 = Color.argb(selectedAlpha - alphaOffset, selectedRed - redOffset, selectedGreen - greenOffset, selectedBlue - blueOffset);
        int color2 = Color.argb(unselectedAlpha + alphaOffset, unselectedRed + redOffset, unselectedGreen + greenOffset, unselectedBlue + blueOffset);

        Tab tabCurrent = mTabs.get(position);

        tabCurrent.ivSelected.setAlpha(1 - positionOffset);
        tabCurrent.ivUnSelected.setAlpha(positionOffset);
        tabCurrent.title.setTextColor(color1);
        //相邻变色
        if (position != mTabs.size() - 1) {
            Tab tabTo = mTabs.get(position + 1);

            tabTo.ivSelected.setAlpha(positionOffset);
            tabTo.ivUnSelected.setAlpha(1 - positionOffset);
            tabTo.title.setTextColor(color2);
        }
        //点击变色
        if (position != lastSelectedPosition) {
            Tab tabTo = mTabs.get(lastSelectedPosition);

            tabTo.ivSelected.setAlpha(positionOffset);
            tabTo.ivUnSelected.setAlpha(1 - positionOffset);
            tabTo.title.setTextColor(color2);
        }
        //记录点击位置
        lastSelectedPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
//        Log.d(TAG, position + "****");
        if (mListener != null) {
            mListener.onTabSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, false);
        } else {
            setIconSelected(position);
            if (mListener != null) {
                mListener.onTabSelected(position);
            }
        }
    }

    class Tab {
        public View tabView;
        public TextView title;
        public ImageView ivSelected;
        public ImageView ivUnSelected;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        mListener = listener;
    }
}
