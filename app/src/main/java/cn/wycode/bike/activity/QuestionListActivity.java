package cn.wycode.bike.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;
import cn.wycode.bike.adapter.QuestionAdapter;
import cn.wycode.bike.model.QA;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class QuestionListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_question)
    ListView lvQuestion;

    private String[] questions;
    private String[] answers;

    private QuestionAdapter adapter;
    private ArrayList<QA> qas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.activity_question_list, "常见问题");
    }

    @Override
    protected void initView() {
        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        qas = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            qas.add(new QA(questions[i], answers[i]));
        }

        adapter = new QuestionAdapter(this, qas, R.layout.item_question);
        lvQuestion.setAdapter(adapter);
        lvQuestion.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        QA qa = qas.get(position);
        qa.setShowAnswer(!qa.isShowAnswer());
        adapter.notifyDataSetChanged();
    }
}
