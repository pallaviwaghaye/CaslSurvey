package com.casl.model;

import android.text.TextWatcher;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.casl.caslsurvey.R;

/**
 * Created by kang on 8/9/2017.
 */

public class CustomTextWatcher implements TextWatcher {
    private EditText mEditText;
    Question question;

    public CustomTextWatcher(EditText e, Question question) {
        mEditText = e;
        this.question = question;
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        String text = s.toString();
        switch ((int)mEditText.getTag()) {
            case 2:
                question.setComment(text);
                Log.d("cmt",text);
                break;
            case 1:
                question.setAnswer(text);
                Log.d("cmt",text);
                break;
        }
    }
}
