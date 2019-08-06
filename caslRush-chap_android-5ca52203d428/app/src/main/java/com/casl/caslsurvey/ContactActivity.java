package com.casl.caslsurvey;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.casl.Utility.MappingData;
import com.casl.Utility.ReverseMapping;
import com.casl.Utility.SharedPreferenceManager;
import com.casl.caslsurvey.InterviewActivity;
import com.casl.caslsurvey.MainActivity;
import com.casl.caslsurvey.R;
import com.casl.model.Contact;
import com.casl.model.HomeDao;
import com.casl.model.Participant;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ContactActivity extends MainActivity implements View.OnClickListener {
    private static final String ARG_PID = "pid";
    private static final String ARG_IID = "iid";

    private Participant pt;
    private int iid;
    // private ParticipantDao participantDao;
    private HomeDao homeDao;

    private EditText contactDate;
    private EditText contactTime;
    private Spinner spinnerTypeofContact;
    private Spinner spinnerPurposeofContact;
    private Button buttonSubmit;
    private Button buttonGotohome;
    Calendar myCalendar;

    private TextView textViewReluctanceYes;
    private TextView textViewReluctanceNo;
    private LinearLayout linearLayoutReluctanceYesSelected;

    private TextView textViewContactYes;
    private TextView textViewContactNo;
    private LinearLayout linearLayoutContactNoSelected;
    private Spinner spinnerNoContact;

    private TextView textViewContactPersonYes;
    private TextView textViewContactPersonNo;

    private TextView textViewDeathYes;
    private TextView textViewDeathNo;
    private TextView textViewDeathDK;
    private TextView textViewDeathRF;
    private LinearLayout linearLayoutDeathYesSelected;

    private Spinner spinnerCauseofDeath;
    private EditText editTextWhenDie;
    private TextView textViewPriorDeathYes;
    private TextView textViewPriorDeathNo;
    private TextView textViewPriorDeathDK;
    private TextView textViewPriorDeathRF;

    private Spinner spinnerWhereWhenDied;
    private Spinner spinnerState;
    private Spinner spinnerWithWhome;

    private EditText editTextComment;
    private TextView textViewExpressYes;
    private TextView textViewExpressNo;

    private EditText editTextFormComment;

    private TextView textViewAnytimeYes;
    private TextView textViewAnytimeNo;


    private LinearLayout linearLayoutAnytimeYesSelected;
    private Spinner spinnerOpinion;
    private EditText editTextRecordWords;
    private TextView textViewDiscussSituationNo;
    private TextView textViewDiscussSituationYes;
    private EditText editTextRecordWords_2;
    private Spinner spinnerExpressPerson;
    private LinearLayout linearLayoutExpressYesSelected;

    private EditText edittextScheduledDate;
    private EditText edittextScheduledTime;
    private Spinner spinnerInterviewMethod;
    private LinearLayout linearLayoutPurposeofContact;

    //Contact contact;
    Contact contact1;

    String selectedTypeOfContact;
    String selectedPurposeOfContact;
    String selectedNoContact;
    String selectedCauseOfDeath;
    String selectedWhereWhenDied;
    String selectedState;
    String selectedWhomConversation;
    String selectedOpinion;
    String selectedExpressPerson;
    String selectedInterviewMethod;
    private String[] typeofContactList;
    private String[] purposeofContact;
    private String[] noContact;
    private String[] causeOfDeath;
    private String[] whereWhenDied;
    private String[] state;
    private String[] withWhome;
    private String[] opinion;
    private String[] expressPerson;
    private String[] interviewMethod;
    private Button buttonReset;
    MappingData mappingData; // = new MappingData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_contact, null);
        //cl.addView(childLayout);
        zoomView.addView(childLayout);
        //setContentView(R.layout.activity_contact);
        initView();
        //contact = new Contact();
        mappingData = new MappingData();

        setSpinnerData();
        SharedPreferenceManager.setApplicationContext(ContactActivity.this);
        Intent intent = getIntent();
        pt = intent.getParcelableExtra(InterviewActivity.PID);
        iid = intent.getIntExtra(InterviewActivity.IID, -1);
        boolean isReset = intent.getBooleanExtra("ISRESET", false);

        contact1 = SharedPreferenceManager.getContactByPIdAndIId(iid, pt.getId());

        if (isReset) {
            contact1 = null;
        }

        if (contact1 != null) {
            if (contact1.isUploaded()) {
                contact1 = new Contact();
            } else {
                setDataFromSharedPref();
            }
        } else {
            contact1 = new Contact();
        }


        setTitle(getResources().getString(R.string.contactform));
    }

    private void setDataFromSharedPref() {

        ReverseMapping reverseMapping = new ReverseMapping();

        contactDate.setText(contact1.getContactDate());
        contactTime.setText(contact1.getContactTime());
        edittextScheduledDate.setText(contact1.getSchDate());
        edittextScheduledTime.setText(contact1.getSchTime());
        editTextWhenDie.setText(contact1.getDieDate());
        editTextComment.setText(contact1.getDieComm());
        editTextRecordWords.setText(contact1.getPartrel3());
        editTextRecordWords_2.setText(contact1.getFamrel3());
        editTextFormComment.setText(contact1.getFormComm());

        if (reverseMapping.getYesNo(contact1.getPartcont()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewContactYes.setText(reverseMapping.getYesNo(contact1.getPartcont()));
            textViewContactYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewContactNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            linearLayoutContactNoSelected.setVisibility(View.GONE);

        } else if (reverseMapping.getYesNo(contact1.getPartcont()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewContactNo.setText(reverseMapping.getYesNo(contact1.getPartcont()));
            textViewContactNo.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewContactYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            linearLayoutContactNoSelected.setVisibility(View.VISIBLE);


        } else {
            textViewContactYes.setText(getResources().getString(R.string.yes));
            textViewContactNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.getYesNo(contact1.getOthrcontact()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewContactPersonYes.setText(reverseMapping.getYesNo(contact1.getOthrcontact()));
            textViewContactPersonYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewContactPersonNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.getYesNo(contact1.getOthrcontact()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewContactPersonNo.setText(reverseMapping.getYesNo(contact1.getOthrcontact()));
            textViewContactPersonNo.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewContactPersonYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else {
            textViewContactPersonYes.setText(getResources().getString(R.string.yes));
            textViewContactPersonNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.get_Death_NursHome(contact1.getDeath()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewDeathYes.setText(reverseMapping.get_Death_NursHome(contact1.getDeath()));
            linearLayoutDeathYesSelected.setVisibility(View.VISIBLE);
            textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.get_Death_NursHome(contact1.getDeath()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            linearLayoutDeathYesSelected.setVisibility(View.GONE);
            textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathNo.setText(reverseMapping.get_Death_NursHome(contact1.getDeath()));
        } else if (reverseMapping.get_Death_NursHome(contact1.getDeath()).equalsIgnoreCase(getResources().getString(R.string.dontknow))) {
            linearLayoutDeathYesSelected.setVisibility(View.GONE);
            textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathDK.setText(reverseMapping.get_Death_NursHome(contact1.getDeath()));
        } else if (reverseMapping.get_Death_NursHome(contact1.getDeath()).equalsIgnoreCase(getResources().getString(R.string.refusal))) {
            linearLayoutDeathYesSelected.setVisibility(View.GONE);
            textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDeathRF.setText(reverseMapping.get_Death_NursHome(contact1.getDeath()));
        } else {
            textViewDeathYes.setText(getResources().getString(R.string.yes));
            textViewDeathNo.setText(getResources().getString(R.string.no));
            textViewDeathDK.setText(getResources().getString(R.string.dontknow));
            textViewDeathRF.setText(getResources().getString(R.string.refusal));
        }

        if (reverseMapping.getYesNo(contact1.getReluctance()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewReluctanceYes.setText(reverseMapping.getYesNo(contact1.getReluctance()));
            linearLayoutReluctanceYesSelected.setVisibility(View.VISIBLE);
            textViewReluctanceYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewReluctanceNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.getYesNo(contact1.getReluctance()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewReluctanceNo.setText(reverseMapping.getYesNo(contact1.getReluctance()));
            linearLayoutReluctanceYesSelected.setVisibility(View.GONE);
            textViewReluctanceYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewReluctanceNo.setBackgroundColor(getResources().getColor(R.color.brown));
        } else {
            textViewReluctanceYes.setText(getResources().getString(R.string.yes));
            textViewReluctanceNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.getYesNo(contact1.getPartrel1()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewAnytimeYes.setText(reverseMapping.getYesNo(contact1.getPartrel1()));
            linearLayoutAnytimeYesSelected.setVisibility(View.VISIBLE);
            textViewAnytimeYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewAnytimeNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.getYesNo(contact1.getPartrel1()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewAnytimeNo.setText(reverseMapping.getYesNo(contact1.getPartrel1()));
            linearLayoutAnytimeYesSelected.setVisibility(View.GONE);
            textViewAnytimeYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewAnytimeNo.setBackgroundColor(getResources().getColor(R.color.brown));
        } else {
            textViewAnytimeYes.setText(getResources().getString(R.string.yes));
            textViewAnytimeNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.getYesNo(contact1.getFamrel1()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewExpressYes.setText(reverseMapping.getYesNo(contact1.getFamrel1()));
            linearLayoutExpressYesSelected.setVisibility(View.VISIBLE);
            textViewExpressYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewExpressNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.getYesNo(contact1.getFamrel1()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewExpressNo.setText(reverseMapping.getYesNo(contact1.getFamrel1()));
            linearLayoutExpressYesSelected.setVisibility(View.GONE);
            textViewExpressYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewExpressNo.setBackgroundColor(getResources().getColor(R.color.brown));
        } else {
            textViewExpressYes.setText(getResources().getString(R.string.yes));
            textViewExpressNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.getYesNo(contact1.getFamrel4()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewDiscussSituationYes.setText(reverseMapping.getYesNo(contact1.getFamrel4()));
            textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.getYesNo(contact1.getFamrel4()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewDiscussSituationNo.setText(reverseMapping.getYesNo(contact1.getFamrel4()));
            textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else {
            textViewDiscussSituationYes.setText(getResources().getString(R.string.yes));
            textViewDiscussSituationNo.setText(getResources().getString(R.string.no));
        }

        if (reverseMapping.get_Death_NursHome(contact1.getNursHome()).equalsIgnoreCase(getResources().getString(R.string.yes))) {
            textViewPriorDeathYes.setText(reverseMapping.get_Death_NursHome(contact1.getNursHome()));
            textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.get_Death_NursHome(contact1.getNursHome()).equalsIgnoreCase(getResources().getString(R.string.no))) {
            textViewPriorDeathNo.setText(reverseMapping.get_Death_NursHome(contact1.getNursHome()));
            textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.get_Death_NursHome(contact1.getNursHome()).equalsIgnoreCase(getResources().getString(R.string.dontknow))) {
            textViewPriorDeathDK.setText(reverseMapping.get_Death_NursHome(contact1.getNursHome()));
            textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else if (reverseMapping.get_Death_NursHome(contact1.getNursHome()).equalsIgnoreCase(getResources().getString(R.string.refusal))) {
            textViewPriorDeathRF.setText(reverseMapping.get_Death_NursHome(contact1.getNursHome()));
            textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.brown));
            textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
            textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
        } else {
            textViewPriorDeathYes.setText(getResources().getString(R.string.yes));
            textViewPriorDeathNo.setText(getResources().getString(R.string.no));
            textViewPriorDeathDK.setText(getResources().getString(R.string.dontknow));
            textViewPriorDeathRF.setText(getResources().getString(R.string.refusal));
        }


        //set spinner data......................
//contact.setContactType(spinnerTypeofContact.);
        //contact.setNopartcont(spinnerNoContact);
        //contact.setPurpose(spinnerPurposeofContact);
        //contact.setIntMethod(spinnerInterviewMethod);
        //contact.setDieCause(spinnerCauseofDeath);
        //contact.setDiePlace(spinnerWhereWhenDied);
        //contact.setDieState(spinnerState);
        //contact.setDieWhom(spinnerWithWhome);
        //contact.setPartrel2(spinnerOpinion);
        //contact.setFamrel2(spinnerExpressPerson);
        List<String> listContact = Arrays.asList(typeofContactList);
        List<String> noContactList = Arrays.asList(noContact);
        List<String> purposeofContactList = Arrays.asList(purposeofContact);
        List<String> interviewMethodList = Arrays.asList(interviewMethod);
        List<String> causeOfDeathList = Arrays.asList(causeOfDeath);
        List<String> whereWhenDiedList = Arrays.asList(whereWhenDied);
        List<String> stateList = Arrays.asList(state);
        List<String> withWhomeList = Arrays.asList(withWhome);
        List<String> opinionList = Arrays.asList(opinion);
        List<String> expressPersonList = Arrays.asList(expressPerson);

        if (contact1.getContactType() != null) {
            int index = listContact.indexOf(reverseMapping.getTypeOfContact(contact1.getContactType()));
            setSpinnerSelection(index, spinnerTypeofContact);
        } else {
            setSpinnerSelection(0, spinnerTypeofContact);
        }

        //
        if (contact1.getNopartcont() != null) {
            int index = noContactList.indexOf(reverseMapping.getNoContactMethod(contact1.getNopartcont()));
            setSpinnerSelection(index, spinnerNoContact);
        } else {
            setSpinnerSelection(0, spinnerNoContact);
        }

        //
        if (contact1.getPurpose() != null) {
            int index = purposeofContactList.indexOf(reverseMapping.getPurposeOfContact(contact1.getPurpose()));
            setSpinnerSelection(index, spinnerPurposeofContact);
        } else {
            setSpinnerSelection(0, spinnerPurposeofContact);
        }

        //
        if (contact1.getIntMethod() != null) {
            int index = interviewMethodList.indexOf(reverseMapping.getIntMethod(contact1.getIntMethod()));
            setSpinnerSelection(index, spinnerInterviewMethod);
        } else {
            setSpinnerSelection(0, spinnerInterviewMethod);
        }

        //
        if (contact1.getDieCause() != null) {
            int index = causeOfDeathList.indexOf(reverseMapping.getDieCause(contact1.getDieCause()));
            setSpinnerSelection(index, spinnerCauseofDeath);
        } else {
            setSpinnerSelection(0, spinnerCauseofDeath);
        }

        //
        if (contact1.getDiePlace() != null) {
            int index = whereWhenDiedList.indexOf(reverseMapping.getDiePlace(contact1.getDiePlace()));
            setSpinnerSelection(index, spinnerWhereWhenDied);
        } else {
            setSpinnerSelection(0, spinnerWhereWhenDied);
        }

        //
        if (contact1.getDieState() != null) {
            int index = stateList.indexOf(reverseMapping.getDieState(contact1.getDieState()));
            setSpinnerSelection(index, spinnerState);
        } else {
            setSpinnerSelection(0, spinnerState);
        }

        //
        if (contact1.getDieWhom() != null) {
            int index = withWhomeList.indexOf(reverseMapping.getDieWhome(contact1.getDieWhom()));
            setSpinnerSelection(index, spinnerWithWhome);
        } else {
            setSpinnerSelection(0, spinnerWithWhome);
        }

        //
        if (contact1.getPartrel2() != null) {
            int index = opinionList.indexOf(reverseMapping.getPartRel2_FamRel2(contact1.getPartrel2()));
            setSpinnerSelection(index, spinnerOpinion);
        } else {
            setSpinnerSelection(0, spinnerOpinion);
        }

        //
        if (contact1.getFamrel2() != null) {
            int index = expressPersonList.indexOf(reverseMapping.getPartRel2_FamRel2(contact1.getFamrel2()));
            setSpinnerSelection(index, spinnerExpressPerson);
        } else {
            setSpinnerSelection(0, spinnerExpressPerson);
        }


    }

    public void setSpinnerSelection(int position, Spinner spinner) {
        spinner.setSelection(position, false);
    }

    private void setSpinnerData() {


        typeofContactList = getResources().getStringArray(R.array.TypeofContact);
        ArrayAdapter<String> adapterTypeofContact = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, typeofContactList);
        spinnerTypeofContact.setAdapter(adapterTypeofContact);

        purposeofContact = getResources().getStringArray(R.array.PurposeofContact);
        ArrayAdapter<String> adapterPurposeofContact = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, purposeofContact);
        spinnerPurposeofContact.setAdapter(adapterPurposeofContact);

        noContact = getResources().getStringArray(R.array.NoContact);
        ArrayAdapter<String> adapterNoContact = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, noContact);
        spinnerNoContact.setAdapter(adapterNoContact);

        causeOfDeath = getResources().getStringArray(R.array.CauseofDeath);
        ArrayAdapter<String> adapterCauseofDeath = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, causeOfDeath);
        spinnerCauseofDeath.setAdapter(adapterCauseofDeath);

        whereWhenDied = getResources().getStringArray(R.array.WhereWhenDied);
        ArrayAdapter<String> adapterWhereWhenDied = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, whereWhenDied);
        spinnerWhereWhenDied.setAdapter(adapterWhereWhenDied);

        state = getResources().getStringArray(R.array.state);
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, state);
        spinnerState.setAdapter(adapterState);

        withWhome = getResources().getStringArray(R.array.WhomeConversation);
        ArrayAdapter<String> adapterWithWhome = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, withWhome);
        spinnerWithWhome.setAdapter(adapterWithWhome);

        opinion = getResources().getStringArray(R.array.Opinion_OR_ExpressPerson);
        ArrayAdapter<String> adapterOpinion = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, opinion);
        spinnerOpinion.setAdapter(adapterOpinion);

        expressPerson = getResources().getStringArray(R.array.Opinion_OR_ExpressPerson);
        ArrayAdapter<String> adapterExpressPerson = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, expressPerson);
        spinnerExpressPerson.setAdapter(adapterExpressPerson);

        interviewMethod = getResources().getStringArray(R.array.InterviewMethod);
        ArrayAdapter<String> adapterInterviewMethod = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_spinner_dropdown_item, interviewMethod);
        spinnerInterviewMethod.setAdapter(adapterInterviewMethod);

        spinnerPurposeofContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                //int itemPosition = adapterView.getSelectedItemPosition() + 1 ;
                //Toast.makeText(ContactActivity.this,position,Toast.LENGTH_LONG).show();
                if (position == 2) {
                    linearLayoutPurposeofContact.setVisibility(View.VISIBLE);
                } else {
                    edittextScheduledDate.setText("");
                    edittextScheduledTime.setText("");
                    spinnerInterviewMethod.setSelection(0, false);
                    linearLayoutPurposeofContact.setVisibility(View.GONE);
                }
                if (position != 0) {

                    selectedPurposeOfContact = (String) adapterView.getItemAtPosition(position);
                    selectedPurposeOfContact = mappingData.getPurposeOfContact(selectedPurposeOfContact);
                } else {
                    selectedPurposeOfContact = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTypeofContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedTypeOfContact = (String) adapterView.getItemAtPosition(position);
                    selectedTypeOfContact = mappingData.getTypeOfContact(selectedTypeOfContact);

                } else {
                    selectedTypeOfContact = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerNoContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    selectedNoContact = (String) adapterView.getItemAtPosition(position);
                    selectedNoContact = mappingData.getNoContactMethod(selectedNoContact);
                } else {
                    selectedNoContact = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCauseofDeath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedCauseOfDeath = (String) adapterView.getItemAtPosition(position);
                    selectedCauseOfDeath = mappingData.getDieCause(selectedCauseOfDeath);
                } else {
                    selectedCauseOfDeath = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerWhereWhenDied.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedWhereWhenDied = (String) adapterView.getItemAtPosition(position);
                    selectedWhereWhenDied = mappingData.getDiePlace(selectedWhereWhenDied);
                } else {
                    selectedWhereWhenDied = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedState = (String) adapterView.getItemAtPosition(position);
                    selectedState = mappingData.getDieState(selectedState);
                } else {
                    selectedState = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerWithWhome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedWhomConversation = (String) adapterView.getItemAtPosition(position);
                    selectedWhomConversation = mappingData.getDieWhome(selectedWhomConversation);
                } else {
                    selectedWhomConversation = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerOpinion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedOpinion = (String) adapterView.getItemAtPosition(position);
                    selectedOpinion = mappingData.getPartRel2_FamRel2(selectedOpinion);
                } else {
                    selectedOpinion = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerExpressPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedExpressPerson = (String) adapterView.getItemAtPosition(position);
                    selectedExpressPerson = mappingData.getPartRel2_FamRel2(selectedExpressPerson);
                } else {
                    selectedExpressPerson = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerInterviewMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {

                    selectedInterviewMethod = (String) adapterView.getItemAtPosition(position);
                    selectedInterviewMethod = mappingData.getIntMethod(selectedInterviewMethod);
                } else {
                    selectedInterviewMethod = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void initView() {
        contactDate = (EditText) findViewById(R.id.contactDate);
        contactDate.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        contactTime = (EditText) findViewById(R.id.contactTime);
        contactTime.setOnClickListener(this);
        spinnerTypeofContact = (Spinner) findViewById(R.id.spinnerTypeofContact);
        spinnerPurposeofContact = (Spinner) findViewById(R.id.spinnerPurposeofContact);
        editTextFormComment = (EditText) findViewById(R.id.editTextFormComment);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        buttonGotohome = (Button) findViewById(R.id.buttonGotohome);
        buttonGotohome.setOnClickListener(this);

        textViewContactYes = (TextView) findViewById(R.id.textViewContactYes);
        textViewContactYes.setOnClickListener(this);
        textViewContactNo = (TextView) findViewById(R.id.textViewContactNo);
        textViewContactNo.setOnClickListener(this);
        linearLayoutContactNoSelected = (LinearLayout) findViewById(R.id.linearLayoutContactNoSelected);
        spinnerNoContact = (Spinner) findViewById(R.id.spinnerNoContact);

        textViewContactPersonYes = (TextView) findViewById(R.id.textViewContactPersonYes);
        textViewContactPersonNo = (TextView) findViewById(R.id.textViewContactPersonNo);
        textViewContactPersonYes.setOnClickListener(this);
        textViewContactPersonNo.setOnClickListener(this);

        textViewDeathYes = (TextView) findViewById(R.id.textViewDeathYes);
        textViewDeathNo = (TextView) findViewById(R.id.textViewDeathNo);
        textViewDeathYes.setOnClickListener(this);
        textViewDeathNo.setOnClickListener(this);
        textViewDeathDK = (TextView) findViewById(R.id.textViewDeathDK);
        textViewDeathRF = (TextView) findViewById(R.id.textViewDeathRF);
        textViewDeathDK.setOnClickListener(this);
        textViewDeathRF.setOnClickListener(this);
        linearLayoutDeathYesSelected = (LinearLayout) findViewById(R.id.linearLayoutDeathYesSelected);

        textViewReluctanceYes = (TextView) findViewById(R.id.textViewReluctanceYes);
        textViewReluctanceNo = (TextView) findViewById(R.id.textViewReluctanceNo);
        textViewReluctanceYes.setOnClickListener(this);
        textViewReluctanceNo.setOnClickListener(this);
        linearLayoutReluctanceYesSelected = (LinearLayout) findViewById(R.id.linearLayoutReluctanceYesSelected);

        spinnerCauseofDeath = (Spinner) findViewById(R.id.spinnerCauseofDeath);
        spinnerWhereWhenDied = (Spinner) findViewById(R.id.spinnerWhereWhenDied);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        spinnerWithWhome = (Spinner) findViewById(R.id.spinnerWithWhome);

        editTextComment = (EditText) findViewById(R.id.editTextComment);
        editTextWhenDie = (EditText) findViewById(R.id.editTextWhenDie);
        editTextWhenDie.setOnClickListener(this);

        textViewPriorDeathYes = (TextView) findViewById(R.id.textViewPriorDeathYes);
        textViewPriorDeathYes.setOnClickListener(this);
        textViewPriorDeathNo = (TextView) findViewById(R.id.textViewPriorDeathNo);
        textViewPriorDeathNo.setOnClickListener(this);
        textViewPriorDeathDK = (TextView) findViewById(R.id.textViewPriorDeathDK);
        textViewPriorDeathDK.setOnClickListener(this);
        textViewPriorDeathRF = (TextView) findViewById(R.id.textViewPriorDeathRF);
        textViewPriorDeathRF.setOnClickListener(this);

        textViewExpressYes = (TextView) findViewById(R.id.textViewExpressYes);
        textViewExpressYes.setOnClickListener(this);
        textViewExpressNo = (TextView) findViewById(R.id.textViewExpressNo);
        textViewExpressNo.setOnClickListener(this);

        textViewAnytimeYes = (TextView) findViewById(R.id.textViewAnytimeYes);
        textViewAnytimeYes.setOnClickListener(this);
        textViewAnytimeNo = (TextView) findViewById(R.id.textViewAnytimeNo);
        textViewAnytimeNo.setOnClickListener(this);

        linearLayoutAnytimeYesSelected = (LinearLayout) findViewById(R.id.linearLayoutAnytimeYesSelected);
        linearLayoutExpressYesSelected = (LinearLayout) findViewById(R.id.linearLayoutExpressYesSelected);

        spinnerOpinion = (Spinner) findViewById(R.id.spinnerOpinion);
        spinnerExpressPerson = (Spinner) findViewById(R.id.spinnerExpressPerson);

        editTextRecordWords = (EditText) findViewById(R.id.editTextRecordWords);
        editTextRecordWords_2 = (EditText) findViewById(R.id.editTextRecordWords_2);

        textViewDiscussSituationNo = (TextView) findViewById(R.id.textViewDiscussSituationNo);
        textViewDiscussSituationNo.setOnClickListener(this);
        textViewDiscussSituationYes = (TextView) findViewById(R.id.textViewDiscussSituationYes);
        textViewDiscussSituationYes.setOnClickListener(this);

        linearLayoutPurposeofContact = (LinearLayout) findViewById(R.id.linearLayoutPurposeofContact);

        spinnerInterviewMethod = (Spinner) findViewById(R.id.spinnerInterviewMethod);
        edittextScheduledDate = (EditText) findViewById(R.id.edittextScheduledDate);
        edittextScheduledDate.setOnClickListener(this);
        edittextScheduledTime = (EditText) findViewById(R.id.edittextScheduledTime);
        edittextScheduledTime.setOnClickListener(this);


        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contactDate:
                DatePickerDialog dlg = new DatePickerDialog(ContactActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dlg.getDatePicker().setMaxDate(System.currentTimeMillis());
                dlg.show();
                break;
            case R.id.contactTime:
                int currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = myCalendar.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                /*String amPm;
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }*/
                                contactTime.setText(hourOfDay + ":" + minute); //+ amPm);
                            }
                        }, currentHour, currentMinute, false);
                timePickerDialog.show();


                break;
            case R.id.edittextScheduledDate:
                DatePickerDialog dlg1 = new DatePickerDialog(ContactActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dlg1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dlg1.show();
                break;
            case R.id.edittextScheduledTime:
                int currentHour1 = myCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute1 = myCalendar.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                /*String amPm;
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                } else {
                                    amPm = "AM";
                                }*/
                                edittextScheduledTime.setText(hourOfDay + ":" + minute);// + " " + amPm);
                            }
                        }, currentHour1, currentMinute1, false);
                timePickerDialog1.show();
                break;
            case R.id.editTextWhenDie:
                DatePickerDialog dlg2 = new DatePickerDialog(ContactActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dlg2.getDatePicker().setMaxDate(System.currentTimeMillis());
                dlg2.show();
                break;

            case R.id.textViewContactNo:
                textViewContactNo.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewContactYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                linearLayoutContactNoSelected.setVisibility(View.VISIBLE);
                contact1.setPartcont(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewContactNo.getText().toString()));
                break;
            case R.id.textViewContactYes:
                textViewContactYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewContactNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                linearLayoutContactNoSelected.setVisibility(View.GONE);
                spinnerNoContact.setSelection(0, false);
                contact1.setPartcont(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewContactYes.getText().toString()));
                break;

            case R.id.textViewContactPersonYes:
                textViewContactPersonYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewContactPersonNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setOthrcontact(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewContactPersonYes.getText().toString()));
                break;
            case R.id.textViewContactPersonNo:
                textViewContactPersonNo.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewContactPersonYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setOthrcontact(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewContactPersonNo.getText().toString()));
                break;

            case R.id.textViewDeathYes:
                linearLayoutDeathYesSelected.setVisibility(View.VISIBLE);
                textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setDeath(mappingData.get_Death_NursHome(textViewDeathYes.getText().toString()));
                break;
            case R.id.textViewDeathNo:
                linearLayoutDeathYesSelected.setVisibility(View.GONE);
                textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setDeath(mappingData.get_Death_NursHome(textViewDeathNo.getText().toString()));

                spinnerCauseofDeath.setSelection(0, false);
                editTextWhenDie.setText("");
                spinnerWhereWhenDied.setSelection(0, false);
                spinnerState.setSelection(0, false);
                spinnerWithWhome.setSelection(0, false);
                editTextComment.setText("");
                textViewPriorDeathYes.setText(getResources().getString(R.string.yes));
                textViewPriorDeathNo.setText(getResources().getString(R.string.no));
                textViewPriorDeathDK.setText(getResources().getString(R.string.dontknow));
                textViewPriorDeathRF.setText(getResources().getString(R.string.refusal));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                break;
            case R.id.textViewDeathDK:
                linearLayoutDeathYesSelected.setVisibility(View.GONE);
                textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setDeath(mappingData.get_Death_NursHome(textViewDeathDK.getText().toString()));

                spinnerCauseofDeath.setSelection(0, false);
                editTextWhenDie.setText("");
                spinnerWhereWhenDied.setSelection(0, false);
                spinnerState.setSelection(0, false);
                spinnerWithWhome.setSelection(0, false);
                editTextComment.setText("");
                textViewPriorDeathYes.setText(getResources().getString(R.string.yes));
                textViewPriorDeathNo.setText(getResources().getString(R.string.no));
                textViewPriorDeathDK.setText(getResources().getString(R.string.dontknow));
                textViewPriorDeathRF.setText(getResources().getString(R.string.refusal));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                break;
            case R.id.textViewDeathRF:
                linearLayoutDeathYesSelected.setVisibility(View.GONE);
                textViewDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDeathRF.setBackgroundColor(getResources().getColor(R.color.brown));
                contact1.setDeath(mappingData.get_Death_NursHome(textViewDeathRF.getText().toString()));

                spinnerCauseofDeath.setSelection(0, false);
                editTextWhenDie.setText("");
                spinnerWhereWhenDied.setSelection(0, false);
                spinnerState.setSelection(0, false);
                spinnerWithWhome.setSelection(0, false);
                editTextComment.setText("");
                textViewPriorDeathYes.setText(getResources().getString(R.string.yes));
                textViewPriorDeathNo.setText(getResources().getString(R.string.no));
                textViewPriorDeathDK.setText(getResources().getString(R.string.dontknow));
                textViewPriorDeathRF.setText(getResources().getString(R.string.refusal));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                break;

            case R.id.textViewReluctanceYes:
                linearLayoutReluctanceYesSelected.setVisibility(View.VISIBLE);
                textViewReluctanceYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewReluctanceNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setReluctance(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewReluctanceYes.getText().toString()));
                break;
            case R.id.textViewReluctanceNo:
                linearLayoutReluctanceYesSelected.setVisibility(View.GONE);
                textViewReluctanceYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewReluctanceNo.setBackgroundColor(getResources().getColor(R.color.brown));
                contact1.setReluctance(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewReluctanceNo.getText().toString()));

                textViewAnytimeYes.setText(getResources().getString(R.string.yes));
                textViewAnytimeNo.setText(getResources().getString(R.string.no));
                textViewAnytimeYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewAnytimeNo.setBackgroundColor(getResources().getColor(R.color.offwhite));

                textViewExpressYes.setText(getResources().getString(R.string.yes));
                textViewExpressNo.setText(getResources().getString(R.string.no));
                textViewExpressYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewExpressNo.setBackgroundColor(getResources().getColor(R.color.offwhite));


                spinnerOpinion.setSelection(0, false);
                editTextRecordWords.setText("");
                spinnerExpressPerson.setSelection(0, false);
                editTextRecordWords.setText("");
                editTextRecordWords_2.setText("");


                textViewDiscussSituationYes.setText(getResources().getString(R.string.yes));
                textViewDiscussSituationNo.setText(getResources().getString(R.string.no));
                textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.offwhite));

                linearLayoutAnytimeYesSelected.setVisibility(View.GONE);
                linearLayoutExpressYesSelected.setVisibility(View.GONE);

                break;

            case R.id.textViewAnytimeYes:
                linearLayoutAnytimeYesSelected.setVisibility(View.VISIBLE);
                textViewAnytimeYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewAnytimeNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setPartrel1(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewAnytimeYes.getText().toString()));
                break;
            case R.id.textViewAnytimeNo:
                linearLayoutAnytimeYesSelected.setVisibility(View.GONE);
                textViewAnytimeYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewAnytimeNo.setBackgroundColor(getResources().getColor(R.color.brown));
                contact1.setPartrel1(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewAnytimeNo.getText().toString()));

                spinnerOpinion.setSelection(0, false);
                editTextRecordWords.setText("");
                break;

            case R.id.textViewExpressYes:
                linearLayoutExpressYesSelected.setVisibility(View.VISIBLE);
                textViewExpressYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewExpressNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setFamrel1(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewExpressYes.getText().toString()));
                break;
            case R.id.textViewExpressNo:
                linearLayoutExpressYesSelected.setVisibility(View.GONE);
                textViewExpressYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewExpressNo.setBackgroundColor(getResources().getColor(R.color.brown));
                contact1.setFamrel1(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewExpressNo.getText().toString()));

                spinnerExpressPerson.setSelection(0, false);
                editTextRecordWords_2.setText("");
                textViewDiscussSituationYes.setText(getResources().getString(R.string.yes));
                textViewDiscussSituationNo.setText(getResources().getString(R.string.no));
                textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                break;

            case R.id.textViewDiscussSituationYes:
                textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setFamrel4(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewDiscussSituationYes.getText().toString()));
                break;
            case R.id.textViewDiscussSituationNo:
                textViewDiscussSituationYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewDiscussSituationNo.setBackgroundColor(getResources().getColor(R.color.brown));
                contact1.setFamrel4(mappingData.get_OthrContact_reluctance_partrel1_famrel1_famrel4(textViewDiscussSituationNo.getText().toString()));
                break;


            case R.id.textViewPriorDeathYes:
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setNursHome(mappingData.get_Death_NursHome(textViewPriorDeathYes.getText().toString()));
                break;
            case R.id.textViewPriorDeathNo:
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setNursHome(mappingData.get_Death_NursHome(textViewPriorDeathNo.getText().toString()));
                break;
            case R.id.textViewPriorDeathDK:
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setNursHome(mappingData.get_Death_NursHome(textViewPriorDeathDK.getText().toString()));
                break;
            case R.id.textViewPriorDeathRF:
                textViewPriorDeathRF.setBackgroundColor(getResources().getColor(R.color.brown));
                textViewPriorDeathYes.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathNo.setBackgroundColor(getResources().getColor(R.color.offwhite));
                textViewPriorDeathDK.setBackgroundColor(getResources().getColor(R.color.offwhite));
                contact1.setNursHome(mappingData.get_Death_NursHome(textViewPriorDeathRF.getText().toString()));
                break;

            case R.id.buttonSubmit:

                //some yes-no textview values are set into switch case.......
                long time= System.currentTimeMillis();

                contact1.setParticipantId(pt.getId().toString());
                contact1.setInterviewId(iid);
                contact1.setTran_date(time);
                //contact1.setTran_user();
                if (contactDate.getText().toString().length() > 0) {
                    contact1.setContactDate(contactDate.getText().toString());
                } else {
                    Toast.makeText(ContactActivity.this, "Please select contact date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contactTime.getText().toString().length() > 0) {
                    contact1.setContactTime(contactTime.getText().toString());
                } else {
                    Toast.makeText(ContactActivity.this, "Please select contact time", Toast.LENGTH_SHORT).show();
                    return;
                }

                contact1.setContactType(selectedTypeOfContact);
                if (contact1.getPartcont().equalsIgnoreCase(getResources().getString(R.string.no))) {
                    contact1.setNopartcont(selectedNoContact);
                } else {
                    contact1.setNopartcont(null);
                }

                if (contact1.getDeath().equalsIgnoreCase(getResources().getString(R.string.yes))) {
                    contact1.setDieDate(editTextWhenDie.getText().toString());
                    contact1.setDieCause(selectedCauseOfDeath);
                    contact1.setNursHome(contact1.getNursHome());
                    contact1.setDiePlace(selectedWhereWhenDied);
                    contact1.setDieState(selectedState);
                    contact1.setDieWhom(selectedWhomConversation);
                    contact1.setDieComm(editTextComment.getText().toString());

                } else {
                    contact1.setNursHome("");
                    contact1.setDieDate("");
                    contact1.setDieCause(null);
                    contact1.setDiePlace(null);
                    contact1.setDieState(null);
                    contact1.setDieWhom(null);
                    contact1.setDieComm("");
                }

                if (contact1.getReluctance().equalsIgnoreCase(getResources().getString(R.string.yes))) {
                    if (contact1.getPartrel1().equalsIgnoreCase(getResources().getString(R.string.yes))) {
                        contact1.setPartrel2(selectedOpinion);
                        contact1.setPartrel3(editTextRecordWords.getText().toString());
                    } else {
                        contact1.setPartrel2(null);
                        contact1.setPartrel3("");
                    }

                    if (contact1.getFamrel1().equalsIgnoreCase(getResources().getString(R.string.yes))) {
                        contact1.setFamrel2(selectedExpressPerson);
                        contact1.setFamrel3(editTextRecordWords_2.getText().toString());
                        contact1.setFamrel4(contact1.getFamrel4());
                    } else {
                        contact1.setFamrel2(null);
                        contact1.setFamrel3("");
                        contact1.setFamrel4("");
                    }
                } else {
                    contact1.setPartrel1("");
                    contact1.setPartrel2(null);
                    contact1.setPartrel3("");
                    contact1.setFamrel1("");
                    contact1.setFamrel2(null);
                    contact1.setFamrel3("");
                    contact1.setFamrel4("");
                }


                contact1.setPurpose(selectedPurposeOfContact);
                if(linearLayoutPurposeofContact.getVisibility() == View.VISIBLE) {
                    contact1.setSchDate(edittextScheduledDate.getText().toString());
                    contact1.setSchTime(edittextScheduledTime.getText().toString());
                    contact1.setIntMethod(selectedInterviewMethod);
                } else {

                }

                contact1.setFormComm(editTextFormComment.getText().toString());
                contact1.setUploaded(false);

                //SharedPreferenceManager.storeContactObject(contact);
                SharedPreferenceManager.saveContactByPIdAndIId(contact1);

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Contact Uploaded.", Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.dark_green));
                tv.setTextSize(17);
                //tv.setGravity(0);
                snackbarView.setBackgroundColor(Color.parseColor("#FFB0D9B9"));
                snackbar.show();


                break;

            case R.id.buttonGotohome:
                onBackPressed();
                break;

            case R.id.buttonReset:
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra(InterviewActivity.PID, pt);
                intent.putExtra(InterviewActivity.IID, iid);
                intent.putExtra("position", -1);
                intent.putExtra("ISRESET", true);
                startActivity(intent);
                finish();
                break;

        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        contactDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    private void updateLabel1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        edittextScheduledDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editTextWhenDie.setText(sdf.format(myCalendar.getTime()));
    }

    /*public static ContactFragment create(String pid, int iid){
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PID, pid);
        args.putInt(ARG_IID, iid);
        Log.d("pInterview",iid+"");
        contactFragment.setArguments(args);
        return contactFragment;
    }*/
}
