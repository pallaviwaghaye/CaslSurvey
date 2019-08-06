package com.casl.caslsurvey;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.casl.model.HomeDao;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ContactFragment extends Fragment {

    private static final String ARG_PID = "pid" ;
    private static final String ARG_IID = "iid";

    private Participant pt;
    private int iid;
   // private ParticipantDao participantDao;
    private HomeDao homeDao;
    View view;

    private EditText contactDate;
    private EditText contactTime;
    private Spinner spinnerTypeofContact;
    private Spinner spinnerPurposeofContact;
    private EditText formComment;
    private Button buttonSubmit;
    private Button buttonReset;
    Calendar myCalendar;


    public static ContactFragment create(String pid, int iid){
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PID, pid);
        args.putInt(ARG_IID, iid);
        Log.d("pInterview",iid+"");
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        contactDate = (EditText)view.findViewById(R.id.contactDate);
        contactTime = (EditText)view.findViewById(R.id.contactTime);
        spinnerTypeofContact = (Spinner)view.findViewById(R.id.spinnerTypeofContact);
        spinnerPurposeofContact = (Spinner)view.findViewById(R.id.spinnerPurposeofContact);
        formComment = (EditText)view.findViewById(R.id.formComment);
        buttonSubmit = (Button)view.findViewById(R.id.buttonSubmit);
        buttonReset = (Button)view.findViewById(R.id.buttonReset);


        String[] typeofContact = getResources().getStringArray(R.array.TypeofContact);
        ArrayAdapter<String> adapterTypeofContact = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,typeofContact);
        spinnerTypeofContact.setAdapter(adapterTypeofContact);

        String[] purposeofContact = getResources().getStringArray(R.array.PurposeofContact);
        ArrayAdapter<String> adapterPurposeofContact = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,purposeofContact);
        spinnerPurposeofContact.setAdapter(adapterPurposeofContact);

        contactDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dlg = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dlg.getDatePicker().setMaxDate(System.currentTimeMillis());
                dlg.show();
            }
        });

        return view;
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
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        contactDate.setText(sdf.format(myCalendar.getTime()));
    }



}
