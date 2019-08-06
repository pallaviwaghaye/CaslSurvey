/*
package com.casl.caslsurvey;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.Choice;
import com.casl.model.CustomTextWatcher;
import com.casl.model.Participant;
import com.casl.model.Question;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.attr.focusable;
import static android.R.attr.resource;

*
 * Created by kang on 8/9/2017.


public class QuestionAdapter extends BaseAdapter {
    private static final int SINGLE_CHOICE = 0;
    private static final int MULTI_CHOICE = 1;

    private List<Question> mData = new ArrayList<>();
    private List<Question> qList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private AnswerDao answerDao;
    private Participant pt;
    private int iid;
    public QuestionAdapter(@NonNull Context context, List<Question> list, MulAnswerDao mulAnswerDao, AnswerDao answerDao, Participant pt, int iid) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = list;
        this.mulAnswerDao = mulAnswerDao;
        this.answerDao = answerDao;
        this.pt = pt;
        this.iid = iid;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Question getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Question q = getItem(position);
        int type = q.getType();
        if (q.getVisible()==View.GONE){
            convertView = mInflater.inflate(R.layout.empty_layout, parent,false);
            convertView.setVisibility(View.GONE);
            return convertView;
        }
        switch (type) {
            case SINGLE_CHOICE:
                convertView = mInflater.inflate(R.layout.question_choice, parent,false);
                MultiRowsRadioGroup rgp = (MultiRowsRadioGroup) convertView.findViewById(R.id.radiogroup);
                rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        clickTrigger(q,checkedId,parent);
                    }
                });
                addRadioChoices(q, rgp, mInflater);
                break;
            case MULTI_CHOICE:
                convertView = mInflater.inflate(R.layout.question_choice, parent,false);
                FlexboxLayout ll = (FlexboxLayout) convertView.findViewById(R.id.checkboxgroup);
                addCheckBoxes(q, ll);
                break;
            default:
                convertView = mInflater.inflate(R.layout.question_txt, parent,false);
                EditText et = (EditText)convertView.findViewById(R.id.editText);
                et.setText(q.getAnswer());
                et.addTextChangedListener(new CustomTextWatcher(et,q));
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
        }

        TextView textView = (TextView) convertView.findViewById(R.id.info_text);
        TextView textView1 = (TextView) convertView.findViewById(R.id.help_text);
        TextView commentText = (TextView) convertView.findViewById(R.id.commentText);
        EditText commentBox = (EditText) convertView.findViewById(R.id.questionComment);
        textView.setText(Html.fromHtml(q.toString()));
        textView1.setText(Html.fromHtml(q.getHelpTrans()));
        convertView.setVisibility(q.getVisible());
        convertView.setTag("parent"+q.getPid());
        convertView.setTag(R.id.question,(int)q.getId());
        convertView.setTag(R.id.position,position);
        if (commentText != null && commentBox != null) {
            commentBox.addTextChangedListener(new CustomTextWatcher(commentBox, q));
            commentBox.setText(q.getComment());
            commentBox.setInputType(InputType.TYPE_CLASS_TEXT);
            commentBox.setImeOptions(EditorInfo.IME_ACTION_DONE);
            commentBox.setTextColor(context.getResources().getColor(android.R.color.black));
            commentBox.setFocusable(true);
            if (q.getHasComment() == 0) {
                commentText.setVisibility(View.GONE);
                commentBox.setVisibility(View.GONE);
            } else {
                commentText.setVisibility(View.VISIBLE);
                commentBox.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    private void addRadioChoices(Question q,  MultiRowsRadioGroup rgp, LayoutInflater inflater) {

        List<Answer> answers = getAnswerByQuestion(q, pt, iid);
        long answerId=-2;
        String answerStr="";
        if (answers!=null && answers.size()>0) {
            try {
                answerStr = answers.get(0).getAnswer();//db.getAnswerId(pt.getId(),iid,q.ID);
                answerId = Long.parseLong(answerStr);
            }catch (NumberFormatException e){

            }
        }
        FlexboxLayout ll  = (FlexboxLayout)inflater.inflate(R.layout.radio_flexgroup, null, false);
        for (Choice choice : q.getChoiceList()) {
            //Choice choice = q.getChoices().get(id);
            final RadioButton radioButton = new RadioButton(rgp.getContext());
            radioButton.setId((int)choice.getId().longValue());
            radioButton.setTag(R.id.trigger,choice.getTriggersub());
            radioButton.setText(choice.toString());
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.questionChoiceSize));
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setBackgroundResource(R.drawable.segment_button);
            radioButton.setGravity(Gravity.CENTER);
            //radioButton.setPadding(10,10,10,10);
            radioButton.setTextAppearance(rgp.getContext(),R.style.TextAppearance_AppCompat_Large);
            Choice checkedChoice = q.getCheckedChoice();
            if (checkedChoice!=null&&(choice.getCodeId().equals(checkedChoice.getCodeId()) || choice.getId()==checkedChoice.getId())) {
                q.setAnswer(choice.getId() + "");
                radioButton.setChecked(true);
            }
            FlexboxLayout.LayoutParams rprms = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            rprms.setFlexBasisPercent(0.25f);
            rprms.rightMargin=(int)context.getResources().getDimension(R.dimen.choice_right_margin);
            rprms.bottomMargin=(int)context.getResources().getDimension(R.dimen.choice_bottom_margin);
            radioButton.setTextColor(context.getResources().getColor(android.R.color.black));
            ll.addView(radioButton, rprms);
        }
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.MATCH_PARENT,FlexboxLayout.LayoutParams.MATCH_PARENT);
        rgp.addView(ll, layoutParams);
    }

    private void addCheckBoxes(final Question q, FlexboxLayout ll) {
        Set<String> answers = q.getCheckedMultiple(); //getMulAnswerByQuestion(q, pt, iid);
        for (Choice choice : q.getChoiceList()) {
            CheckBox checkBox = new CheckBox(ll.getContext());
            checkBox.setTag(R.id.question,(int)choice.getId().longValue());
            checkBox.setTag(R.id.trigger,choice.getTriggersub());
            checkBox.setText(choice.toString());
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.questionChoiceSize));
            checkBox.setButtonDrawable(android.R.color.transparent);
            checkBox.setBackgroundResource(R.drawable.segment_button);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setPadding(10,10,10,10);
            checkBox.setTextColor(context.getResources().getColor(android.R.color.black));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        q.getCheckedMultiple().add(buttonView.getTag(R.id.question)+"");
                    }
                    else{
                        q.getCheckedMultiple().remove(buttonView.getTag(R.id.question)+"");
                    }
                }
            });
            if (choiceInMulAnswer(choice.getId(),answers)){
                checkBox.setChecked(true);
            }
            FlexboxLayout.LayoutParams rprms = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.MATCH_PARENT);
            rprms.setFlexBasisPercent(0.3f);
            rprms.rightMargin=(int)context.getResources().getDimension(R.dimen.choice_right_margin);
            rprms.bottomMargin=(int)context.getResources().getDimension(R.dimen.choice_bottom_margin);
            ll.addView(checkBox, rprms);
            ll.setTag("cbg"+q.getId());

        }
    }

    private static boolean choiceInMulAnswer(long choiceId, Set<String> answers){
        if (answers!=null) {
            for (String answerStr : answers) {
                long answerId = -2;
                if (answerStr != null && answerStr != "") {
                    try {
                        answerId = Long.parseLong(answerStr);
                    } catch (NumberFormatException e) {
                    }
                }
                if (answerId == choiceId) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Answer> getAnswerByQuestion(Question q, Participant pt, int iid) {
        List<Answer> answers = answerDao.queryBuilder().where(
                AnswerDao.Properties.ParticipantId.eq(pt.getId()),
                AnswerDao.Properties.InterviewId.eq(iid),
                AnswerDao.Properties.QuestionId.eq(q.getId())).list();
        return answers;
    }

    private void clickTrigger(Question q, int checkedId, ViewGroup parent){
        q.setAnswer(checkedId+"");
        Choice checkedChoice = q.getCheckedChoice();
        int visibility=0;
        if (checkedChoice.getTriggersub()==1){
            visibility=View.VISIBLE;
        }
        else if (checkedChoice.getTriggersub()==0){
            visibility=View.GONE;
        }
        setSubVisible(q, visibility);
        notifyDataSetChanged();
    }

    private void setSubVisible(Question q, int visible){
        ArrayList<Question> qList = getSubQuestion(q);
        if (qList!=null && qList.size()>0){
            for (Question qSub: qList) {
                qSub.setVisible(visible);
                if (visible == View.GONE) {
                    setSubVisible(qSub, visible);
                }
                Choice checkedRbt = qSub.getCheckedChoice();
                if (checkedRbt != null) {
                    if (checkedRbt != null && (int) checkedRbt.getTriggersub() == 1) {
                        setSubVisible(qSub, visible);
                    }
                }
            }
        }
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

    private ArrayList<Question> getSubQuestion(Question q){
        ArrayList<Question> qList = new ArrayList<>();
        for (Question qSub : mData){
            if (qSub.getPid() == q.getId()){
                qList.add(qSub);
            }
        }
        return qList;
    }

}
*/
