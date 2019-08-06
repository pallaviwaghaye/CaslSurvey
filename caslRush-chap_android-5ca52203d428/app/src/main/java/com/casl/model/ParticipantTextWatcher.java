package com.casl.model;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;

import com.casl.caslsurvey.R;

/**
 * Created by kang on 8/25/2017.
 */

public class ParticipantTextWatcher implements TextWatcher {
    EditText mEditText;
    Participant participant;
    Spinner spinner;

    public ParticipantTextWatcher(EditText mEditText, Participant participant) {
        this.mEditText = mEditText;
        this.participant = participant;
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        String text = s.toString();
        switch (mEditText.getId()) {
            case R.id.city:
                participant.getHome().setCity(text);
                break;
            case R.id.state:
                participant.getHome().setState(text);
                break;
            case R.id.homePhone:
                participant.getHome().setHomePhone(text);
                break;
            case R.id.cellPhone:
                participant.setCellPhone(text);
                break;
            case R.id.streetName:
                participant.getHome().setStreetName(text);
                break;
            /*case R.id.spinnerStreetType:
                participant.getHome().setStreetType(text);
                break;*/
            case R.id.streetNumber:
                participant.getHome().setStreetNumber(text);
                break;
           /* case R.id.spinnerDirection:
                participant.getHome().setStreetDirection(text);
                break;*/
            case R.id.email:
                participant.setEmail(text);
                break;
            case R.id.gender:
                participant.setGender(text);
                break;
            case R.id.workPhone:
                participant.setWorkPhone(text);
                break;
            /*case R.id.bornCountry:
                participant.setBornCountry(text);
                break;
            case R.id.recruitment:
                participant.setRecruitment(text);
                break;*/
            case R.id.group:
                participant.setGroup(text);
                break;
            case R.id.comment:
                participant.setComment(text);
                break;

        }
    }
}
