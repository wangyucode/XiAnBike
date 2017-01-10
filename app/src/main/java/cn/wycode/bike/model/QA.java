package cn.wycode.bike.model;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class QA {

    private String question;
    private String answer;
    private boolean isShowAnswer;

    public QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isShowAnswer() {
        return isShowAnswer;
    }

    public void setShowAnswer(boolean showAnswer) {
        isShowAnswer = showAnswer;
    }
}
