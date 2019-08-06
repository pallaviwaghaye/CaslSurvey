package com.casl.caslsurvey;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.casl.model.Answer;
import com.casl.model.AnswerDao;
import com.casl.model.Choice;
import com.casl.model.CustomTextWatcher;
import com.casl.model.Participant;
import com.casl.model.Question;
import com.casl.model.TranslateBase;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.CheckedInputStream;

/**
 * Created by kang on 8/11/2017.
 */

public class QuestionRecyAdapter extends RecyclerView.Adapter<QuestionRecyAdapter.ViewHolder> {
    private final Participant pt;
    private final int iid;
    private List<Question> mDataset;
    private Context context;
    private LayoutInflater inflater;
    private AnswerDao answerDao;
    private ViewGroup parent;
    private SharedPreferences sp;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public EditText cmtEdittext;
        public TextView mHelpText;
        public TextView cmtTitle;
        public boolean visibility;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            cmtEdittext = (EditText) v.findViewById(R.id.questionComment);
            mHelpText = (TextView) v.findViewById(R.id.help_text);
            cmtTitle = (TextView) v.findViewById(R.id.commentText);
        }


    }
    public class ViewHolder0 extends QuestionRecyAdapter.ViewHolder {
        public MultiRowsRadioGroup rgp;
        public ViewHolder0(View v) {
            super(v);
            rgp = (MultiRowsRadioGroup) v.findViewById(R.id.radiogroup);
        }
    }

    public class ViewHolder1 extends QuestionRecyAdapter.ViewHolder {
        public FlexboxLayout fl;
        public ViewHolder1(View v) {
            super(v);
            fl = (FlexboxLayout) v.findViewById(R.id.checkboxgroup);
        }
    }

    public class ViewHolder2 extends QuestionRecyAdapter.ViewHolder {
        public EditText qText;
        public ViewHolder2(View v) {
            super(v);
            qText = (EditText) v.findViewById(R.id.editText);
        }
    }

    public class ViewHolder3 extends QuestionRecyAdapter.ViewHolder {
        public ViewHolder3(View v) {
            super(v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionRecyAdapter(List<Question> myDataset, AnswerDao answerDao, Participant pt, int iid) {
        this.mDataset = myDataset;
        this.answerDao = answerDao;
        this.pt = pt;
        this.iid = iid;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = null;
        this.parent = parent;
        context = parent.getContext();
        inflater = LayoutInflater.from(parent.getContext());
        ViewHolder vh = null;

        /*sp = context.getSharedPreferences("global", Context.MODE_PRIVATE);
        TranslateBase.sp = sp;
        TranslateBase.context = context.getApplicationContext();*/

        switch (viewType){
            case -1:
                v = inflater.inflate(R.layout.empty_layout, parent, false);
                vh = new ViewHolder3(v);
                break;
            case 0:
                v = inflater.inflate(R.layout.question_choice, parent, false);
                v.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                vh = new ViewHolder0(v);
                break;
            case 1:
                v = inflater.inflate(R.layout.question_choice, parent, false);
                v.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                vh = new ViewHolder1(v);
                break;
            default:
                v = inflater.inflate(R.layout.question_txt, parent, false);
                v.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                vh = new ViewHolder2(v);
        }
        //vh.setIsRecyclable(false);
        if (v.getVisibility()==View.GONE){
            v.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressWarnings("WrongConstant")
    @Override
    public void onBindViewHolder(final QuestionRecyAdapter.ViewHolder holder, int position) {

        final Question question = mDataset.get(position);

        /*if(question.getType() == 0)
        {
            Choice dk = new Choice();
            dk.setCodeId("88");
            dk.setTriggersub(0);
            dk.setQuestionId(question.getId());
            dk.setChContent("不知道");
            dk.setContent("DK");
            Choice rf = new Choice();
            rf.setCodeId("99");
            rf.setTriggersub(0);
            rf.setQuestionId(question.getId());
            rf.setContent("RF");
            rf.setChContent("拒絕");
            question.getMemChoiceList().add(dk);
            question.getMemChoiceList().add(rf);
        }
        mDataset.add(question);*/

        if (holder.getItemViewType()!=-1) {
            holder.mTextView.setText(Html.fromHtml(question.toString()));
            holder.mHelpText.setText(Html.fromHtml(question.getHelpTrans()));
        }
        if (holder.cmtEdittext!=null&&holder.cmtTitle!=null){
            if (question.getHasComment() == 0) {
                holder.cmtEdittext.setVisibility(View.GONE);
                holder.cmtTitle.setVisibility(View.GONE);
            } else {
                holder.cmtEdittext.setVisibility(View.VISIBLE);
                holder.cmtTitle.setVisibility(View.VISIBLE);
                holder.cmtEdittext.setId((int)question.getId());
                holder.cmtEdittext.setTag(2);
                holder.cmtEdittext.setText(question.getComment());
                holder.cmtEdittext.addTextChangedListener(new CustomTextWatcher(holder.cmtEdittext,question));
                holder.cmtEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.cmtEdittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
                holder.cmtEdittext.setTextColor(context.getResources().getColor(android.R.color.black));
            }
        }
        switch (holder.getItemViewType()){
            case -1:
                break;
            case 0:
                final ViewHolder0 holder0 = (ViewHolder0)holder;
                holder0.setIsRecyclable(true);
                holder0.rgp.removeAllViews();
                holder0.rgp.setId((int)question.getId());
                addRadioChoices(question,holder0.rgp,inflater);
                if (question.getCheckedChoice()!=null) {
                    ((RadioButton)holder0.rgp.findViewById((int) question.getCheckedChoice().getId().longValue())).setChecked(true);
                    clickTrigger(question, (int) question.getCheckedChoice().getId().longValue(), true);
                }
                break;
            case 1:
                ViewHolder1 holder1 = (ViewHolder1)holder;
                holder1.setIsRecyclable(true);
                holder1.fl.removeAllViews();
                holder1.fl.setId((int)question.getId());
                addCheckBoxes(question, holder1.fl);
                break;
            default:
                ViewHolder2 holder2 = (ViewHolder2)holder;
                holder2.setIsRecyclable(false);
                holder2.qText.setId((int)question.getId());
                holder2.qText.setTag(1);
                holder2.qText.setText(question.getAnswer());
                holder2.qText.addTextChangedListener(new CustomTextWatcher(holder2.qText,question));
                holder2.qText.setInputType(InputType.TYPE_CLASS_TEXT);
                holder2.qText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position){
        return mDataset.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position).getVisible()==View.GONE){
            return -1;
        }
        return mDataset.get(position).getType();
    }

    private void addRadioChoices(final Question q, MultiRowsRadioGroup rgp, LayoutInflater inflater) {
        /*
        List<Answer> answers = getAnswerByQuestion(q, pt, iid);
        String answerStr="";
        if (answers!=null && answers.size()>0) {
            try {
                answerStr = answers.get(0).getAnswer();//db.getAnswerId(pt.getId(),iid,q.ID);
                q.setAnswer(answerStr);
            }catch (NumberFormatException e){

            }
        }*/
        FlexboxLayout ll  = (FlexboxLayout)inflater.inflate(R.layout.radio_flexgroup, rgp, false);
        Choice checkedChoice = q.getCheckedChoice();
        for (Choice choice : q.getChoiceList()) {
            //choice = q.getChoices().get(iid);
            final RadioButton radioButton = new RadioButton(rgp.getContext());
            radioButton.setId((int)choice.getId().longValue());
            radioButton.setTag(R.id.trigger,choice.getTriggersub());
            radioButton.setText(choice.toString());
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.questionChoiceSize));
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setBackgroundResource(R.drawable.segment_button);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setPadding(30,30,30,30);
            radioButton.setTextAppearance(rgp.getContext(),R.style.TextAppearance_AppCompat_Large);
            radioButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    clickTrigger(q,v.getId(),false);
                }
            });
            if (choice.getCodeId().equals(q.getAnswer())&&(choice.getCodeId().equals("88")||choice.getCodeId().equals("99"))){
                radioButton.setChecked(true);

                //radioButton.setVisibility(View.VISIBLE);
            }
            else if (checkedChoice != null && choice.getId() == checkedChoice.getId()) {
                radioButton.setChecked(true);
                //radioButton.setVisibility(View.VISIBLE);
            }
            FlexboxLayout.LayoutParams rprms = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            //rprms.setFlexBasisPercent(0.25f);
            rprms.rightMargin=(int)context.getResources().getDimension(R.dimen.choice_right_margin);
            rprms.bottomMargin=(int)context.getResources().getDimension(R.dimen.choice_bottom_margin);
            radioButton.setTextColor(context.getResources().getColor(android.R.color.black));
            ll.addView(radioButton, rprms);
        }
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.MATCH_PARENT,FlexboxLayout.LayoutParams.WRAP_CONTENT);
        rgp.addView(ll, layoutParams);
    }

    private void addCheckBoxes(final Question q, FlexboxLayout ll) {
        //List<MulAnswer> answers = getMulAnswerByQuestion(q,pt,iid);
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
            checkBox.setPadding(30,30,30,30);
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
            //rprms.setFlexBasisPercent(0.3f);
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

    public void clickTrigger(Question q, int checkedId, boolean init){
        q.setAnswer(checkedId+"");
        Choice checkedChoice = q.getCheckedChoice();
        int visibility=0;
        if (checkedChoice!=null) {
            if (checkedChoice.getTriggersub() == 1) {
                visibility = View.VISIBLE;
            } else if (checkedChoice.getTriggersub() == 0) {
                visibility = View.GONE;
            }
            /*else if(checkedChoice.getTriggersub() == 0 && checkedChoice.getCodeId().equalsIgnoreCase("88"))
            {
                visibility = View.VISIBLE;
            }*/
            /*else if(checkedChoice.getCodeId().equalsIgnoreCase("88"))
            {
                visibility = View.GONE;

            }
            else if(checkedChoice.getCodeId().equalsIgnoreCase("99"))
            {
                visibility = View.VISIBLE;
            }*/
            setSubVisible(q, visibility);
            if (!init) {
                notifyItemChanged((int) q.getId());
            }
        }
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
                    if (checkedRbt != null && (int) checkedRbt.getTriggersub() == 1) { //if selected yes then only show the sub questions
                        setSubVisible(qSub, visible);
                    }
                }
            }
        }
    }

    private ArrayList<Question> getSubQuestion(Question q){
        ArrayList<Question> qList = new ArrayList<>();
        for (Question qSub : mDataset){
            if (qSub.getPid() == q.getId()){
                qList.add(qSub);
            }
        }
        return qList;
    }
}


