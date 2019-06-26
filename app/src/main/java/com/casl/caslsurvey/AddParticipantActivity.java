package com.casl.caslsurvey;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddParticipantActivity extends MainActivity implements View.OnClickListener {
    private Spinner spinnerGender;
    private Spinner spinnerDirection;
    private Spinner spinnerStreettype;
    private Spinner spinnerState;
    //private Spinner spinnerResidence;
    //private Spinner spinnerCountryofBirth;
    private Spinner spinnerLanguage;
    private Spinner spinnerRecruitment;

    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextCname;
    private EditText editTextDOB;
    private EditText editTextEmail;
    private EditText editTextCellPhone;
    private EditText editTextHomePhone;
    private EditText editTextWorkPhone;
    private EditText editTextAddressNumber;
    private EditText editTextStreetName;
    private EditText editTextAptUnit;
    private EditText editTextCityTown;
    private EditText editTextZipcode;
    private EditText editTextRecruitDate;
    private EditText editTextComment;
    private Button buttonAdd;
    Calendar myCalendar;
    //int mYear;
    //int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_add_participant,null);
        //cl.addView(childLayout);
        zoomView.addView(childLayout);
        //setContentView(R.layout.activity_add_participant);

        initViews();

        spinnerGender = (Spinner)findViewById(R.id.spinnerGender);
        String[] gender = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this,R.layout.spinner_item,gender);
        spinnerGender.setAdapter(adapterGender);

        //foodadapter.setDropDownViewResource(R.layout.spinner_layout);

        spinnerDirection = (Spinner)findViewById(R.id.spinnerDirection);
        String[] direction = getResources().getStringArray(R.array.streetDirection);
        ArrayAdapter<String> adapterDirection = new ArrayAdapter<String>(this,R.layout.spinner_item,direction);
        spinnerDirection.setAdapter(adapterDirection);

        spinnerStreettype = (Spinner)findViewById(R.id.spinnerStreettype);
        String[] streetType = getResources().getStringArray(R.array.streetType);
        ArrayAdapter<String> adapterStreetType = new ArrayAdapter<String>(this,R.layout.spinner_item,streetType);
        spinnerStreettype.setAdapter(adapterStreetType);

        spinnerState = (Spinner)findViewById(R.id.spinnerState);
        String[] state = getResources().getStringArray(R.array.state);
        ArrayAdapter<String> adapterState= new ArrayAdapter<String>(this,R.layout.spinner_item,state);
        spinnerState.setAdapter(adapterState);

        spinnerLanguage = (Spinner)findViewById(R.id.spinnerLanguage);
        String[] language = getResources().getStringArray(R.array.language);
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(this,R.layout.spinner_item,language);
        spinnerLanguage.setAdapter(adapterLanguage);

        spinnerRecruitment = (Spinner)findViewById(R.id.spinnerRecruitment);
        String[] recruitment = getResources().getStringArray(R.array.recruitment);
        ArrayAdapter<String> adapterRecruitment = new ArrayAdapter<String>(this,R.layout.spinner_item,recruitment);
        spinnerRecruitment.setAdapter(adapterRecruitment);

        setTitle(getResources().getString(R.string.addParticipant));

    }

    private void initViews() {
        editTextFname = (EditText) findViewById(R.id.editTextFname);
        editTextCname = (EditText) findViewById(R.id.editTextCname);
        editTextLname = (EditText) findViewById(R.id.editTextLname);

        editTextDOB = (EditText) findViewById(R.id.editTextDOB);
        editTextDOB.setOnClickListener(this);
        editTextRecruitDate = (EditText) findViewById(R.id.editTextRecruitDate);
        editTextRecruitDate.setOnClickListener(this);

        myCalendar = Calendar.getInstance();
        /*mYear = myCalendar.get(Calendar.YEAR);
        myCalendar.set(Calendar.YEAR,mYear-9);
*/
        /*month = myCalendar.get(Calendar.MONTH);
        myCalendar.set(Calendar.MONTH,month-101);*/

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextCellPhone = (EditText) findViewById(R.id.editTextCellPhone);
        editTextHomePhone = (EditText) findViewById(R.id.editTextHomePhone);
        editTextWorkPhone = (EditText) findViewById(R.id.editTextWorkPhone);
        editTextAddressNumber = (EditText) findViewById(R.id.editTextAddressNumber);
        editTextStreetName = (EditText) findViewById(R.id.editTextStreetName);
        editTextAptUnit = (EditText) findViewById(R.id.editTextAptUnit);
        editTextCityTown = (EditText) findViewById(R.id.editTextCityTown);
        editTextZipcode = (EditText) findViewById(R.id.editTextZipcode);
        editTextComment = (EditText) findViewById(R.id.editTextComment);

        editTextComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextComment.setRawInputType(InputType.TYPE_CLASS_TEXT);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTextRecruitDate:
                Date d=new Date();
                Calendar cal=Calendar.getInstance();
                int cyear = cal.get(Calendar.YEAR);
                int cmonth = cal.get(Calendar.MONTH);
                int cday = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dlg1 = new DatePickerDialog(AddParticipantActivity.this, dateRecruit, cyear, cmonth, cday);
                dlg1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dlg1.show();
                break;
            case R.id.editTextDOB:
                Date d1=new Date();
                Calendar cal1=Calendar.getInstance();
                cal1.set(2010, 11, 31, 0, 0);
                d1.setTime(cal1.getTimeInMillis());
                Calendar c = Calendar.getInstance();
                int cyear1 = c.get(Calendar.YEAR);
                int cmonth1 = c.get(Calendar.MONTH);
                int cday1 = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dlg = new DatePickerDialog(AddParticipantActivity.this, dateOfBirth, cyear1,cmonth1,cday1);
                dlg.getDatePicker().setMaxDate(d1.getTime());
                dlg.show();
                break;

                case R.id.buttonAdd:
                    if (isValidEmailAddress(editTextEmail.getText().toString().trim())) {
                        Intent intent = new Intent(AddParticipantActivity.this,SummaryActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Email address is not valid!!",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    break;

        }
    }
    DatePickerDialog.OnDateSetListener dateOfBirth = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDOBLabel();
        }
    };
    DatePickerDialog.OnDateSetListener dateRecruit = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateRecruitDateLabel();
        }
    };

    private void updateDOBLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editTextDOB.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateRecruitDateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        editTextRecruitDate.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
