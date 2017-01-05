package cn.wycode.bike.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import cn.wycode.bike.activity.fragement.BaseFragment;


/**
 * Created by huangyi on 16/2/26.
 * 首页的ViewPager适配器
 */
public class PagerFragmentAdapter extends FragmentStatePagerAdapter {
    ArrayList<BaseFragment> list;
    ArrayList<String> tittleArray;

    public PagerFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> list, ArrayList<String> tittleArray) {
        super(fm);
        this.list = list;
        this.tittleArray = tittleArray;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tittleArray.get(position);
    }
}
