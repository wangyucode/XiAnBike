package cn.wycode.bike.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import cn.wycode.bike.R;
import cn.wycode.bike.adapter.baseadapter.WyCommonAdapter;
import cn.wycode.bike.adapter.baseadapter.WyViewHolder;
import cn.wycode.bike.model.QA;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class QuestionAdapter extends WyCommonAdapter<QA> {

    public QuestionAdapter(Context context, List<QA> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(WyViewHolder holder, QA qa) {
        holder.setTextViewText(R.id.tv_question, qa.getQuestion());
        holder.setTextViewText(R.id.tv_answer, qa.getAnswer());
        holder.setViewVisibility(R.id.ll_answer, qa.isShowAnswer() ? View.VISIBLE : View.GONE);
    }
}
