package com.casl.caslsurvey;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.casl.caslsurvey.databinding.ParticipantLayoutBinding;
import com.casl.caslsurvey.databinding.UsParticipantBinding;
import com.casl.model.HomeDao;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;
import com.casl.model.ParticipantTextWatcher;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kang on 7/25/2017.
 */

public class ParticipantFragment extends Fragment {
    private static final String ARG_PID = "pid" ;
    private static final String ARG_IID = "iid";
    private Participant pt;
    private int iid;
    private ParticipantDao participantDao;
    private HomeDao homeDao;
    View view;
    Spinner spinnerDirection;
    Spinner spinnerState;
    Spinner spinnerStreetType;
    Spinner spinnerRecruitment;
    Spinner spinnerBornCountry;

    HashMap<String ,String> direction = new HashMap<String,String>();


    public static ParticipantFragment create(String pid, int iid){
        ParticipantFragment fragment = new ParticipantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PID, pid);
        args.putInt(ARG_IID, iid);
        Log.d("pInterview",iid+"");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        participantDao = ((App)getActivity().getApplication()).getDaoSession().getParticipantDao();
        homeDao = ((App)getActivity().getApplication()).getDaoSession().getHomeDao();
        if (getArguments() != null) {
            String pid = getArguments().getString(ARG_PID);
            pt = participantDao.load(pid);
            iid = getArguments().getInt(ARG_IID);
            //Log.d("ptStreet",pt.getMemHome().getStreet());
            if (pt==null) {
                pt = new Participant();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //String selectedGender = pt.getGender();

        if (iid==948 || iid==949){
            ParticipantLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.participant_layout,container,false);
            view = binding.getRoot();

            spinnerDirection = (Spinner)view.findViewById(R.id.spinnerDirection);
            String[] direction = getResources().getStringArray(R.array.streetDirection);
            ArrayAdapter<String> adapterDirection = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,direction);
            spinnerDirection.setAdapter(adapterDirection);

            spinnerState = (Spinner)view.findViewById(R.id.spinnerState);
            String[] state = getResources().getStringArray(R.array.state);
            ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,state);
            spinnerState.setAdapter(adapterState);

            spinnerStreetType = (Spinner)view.findViewById(R.id.spinnerStreetType);
            String[] streetType = getResources().getStringArray(R.array.streetType);
            ArrayAdapter<String> adapterStreetType = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,streetType);
            spinnerStreetType.setAdapter(adapterStreetType);



            int spinnerPosition;
            switch(pt.getHome().getStreetDirection())
            {
                case "e" :
                    spinnerPosition = adapterDirection.getPosition(direction[0]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    break;
                case "s" :
                    spinnerPosition = adapterDirection.getPosition(direction[1]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    break;
                case "w" :
                    spinnerPosition = adapterDirection.getPosition(direction[2]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    break;
                case "n" :
                    spinnerPosition = adapterDirection.getPosition(direction[3]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    break;
            }
            spinnerDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //pt.getHome().getStreetDirection() = spinnerDirection.getSelectedItem().toString();
                    if(position == 0) {
                        String e = "e";
                        //e = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(e);
                    }
                    else if(position == 1) {
                        String s = "s";
                        //s = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(s);
                    }
                    else if(position == 2) {
                        String w = "w";
                        //w = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(w);
                    }
                    else if(position == 3) {
                        String n = "n";
                        pt.getHome().setStreetDirection(n);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            int spinnerStatePosition;
            switch(pt.getHome().getState())
            {
                /*<option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>*/
                case "AL" :
                    spinnerStatePosition = adapterState.getPosition(state[0]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AK" :
                    spinnerStatePosition = adapterState.getPosition(state[1]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AZ" :
                    spinnerStatePosition = adapterState.getPosition(state[2]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AR" :
                    spinnerStatePosition = adapterState.getPosition(state[3]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "CA" :
                    spinnerStatePosition = adapterState.getPosition(state[4]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                    /*<option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>*/
                case "CO" :
                    spinnerStatePosition = adapterState.getPosition(state[5]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "CT" :
                    spinnerStatePosition = adapterState.getPosition(state[6]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "DE" :
                    spinnerStatePosition = adapterState.getPosition(state[7]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "FL" :
                    spinnerStatePosition = adapterState.getPosition(state[8]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "GA" :
                    spinnerStatePosition = adapterState.getPosition(state[9]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                    /*<option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>*/
                case "HI" :
                    spinnerStatePosition = adapterState.getPosition(state[10]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ID" :
                    spinnerStatePosition = adapterState.getPosition(state[11]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IL" :
                    spinnerStatePosition = adapterState.getPosition(state[12]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IN" :
                    spinnerStatePosition = adapterState.getPosition(state[13]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IA" :
                    spinnerStatePosition = adapterState.getPosition(state[14]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                     /*<option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>*/

                case "KS" :
                    spinnerStatePosition = adapterState.getPosition(state[15]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "KY" :
                    spinnerStatePosition = adapterState.getPosition(state[16]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "LA" :
                    spinnerStatePosition = adapterState.getPosition(state[17]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ME" :
                    spinnerStatePosition = adapterState.getPosition(state[18]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MD" :
                    spinnerStatePosition = adapterState.getPosition(state[19]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                   /*  <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>*/

                case "MA" :
                    spinnerStatePosition = adapterState.getPosition(state[20]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MI" :
                    spinnerStatePosition = adapterState.getPosition(state[21]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MN" :
                    spinnerStatePosition = adapterState.getPosition(state[22]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MS" :
                    spinnerStatePosition = adapterState.getPosition(state[23]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MO" :
                    spinnerStatePosition = adapterState.getPosition(state[24]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                    /*<option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>*/
                case "MT" :
                    spinnerStatePosition = adapterState.getPosition(state[25]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NE" :
                    spinnerStatePosition = adapterState.getPosition(state[26]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NV" :
                    spinnerStatePosition = adapterState.getPosition(state[27]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NH" :
                    spinnerStatePosition = adapterState.getPosition(state[28]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NJ" :
                    spinnerStatePosition = adapterState.getPosition(state[29]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                     /*<option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>*/
                case "NM" :
                    spinnerStatePosition = adapterState.getPosition(state[30]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NY" :
                    spinnerStatePosition = adapterState.getPosition(state[31]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NC" :
                    spinnerStatePosition = adapterState.getPosition(state[32]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ND" :
                    spinnerStatePosition = adapterState.getPosition(state[33]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "OH" :
                    spinnerStatePosition = adapterState.getPosition(state[34]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                     /*<option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>*/
                case "OK" :
                    spinnerStatePosition = adapterState.getPosition(state[35]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "OR" :
                    spinnerStatePosition = adapterState.getPosition(state[36]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "PA" :
                    spinnerStatePosition = adapterState.getPosition(state[37]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "RI" :
                    spinnerStatePosition = adapterState.getPosition(state[38]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "SC" :
                    spinnerStatePosition = adapterState.getPosition(state[39]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                   /*  <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>*/

                case "SD" :
                    spinnerStatePosition = adapterState.getPosition(state[40]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "TN" :
                    spinnerStatePosition = adapterState.getPosition(state[41]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "TX" :
                    spinnerStatePosition = adapterState.getPosition(state[42]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "UT" :
                    spinnerStatePosition = adapterState.getPosition(state[43]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "VT" :
                    spinnerStatePosition = adapterState.getPosition(state[44]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                  /*   <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>*/
                case "VA" :
                    spinnerStatePosition = adapterState.getPosition(state[45]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WA" :
                    spinnerStatePosition = adapterState.getPosition(state[46]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WV" :
                    spinnerStatePosition = adapterState.getPosition(state[47]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WI" :
                    spinnerStatePosition = adapterState.getPosition(state[48]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WY" :
                    spinnerStatePosition = adapterState.getPosition(state[49]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
            }

            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //pt.getHome().getStreetDirection() = spinnerDirection.getSelectedItem().toString();
                    switch(position)
                    {
                        case 0 :
                            String AL = "AL";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AL);break;
                        case 1 :
                            String AK = "AK";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AK);break;
                        case 2 :
                            String AZ = "AZ";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AZ);break;
                        case 3 :
                            String AR = "AR";
                            pt.getHome().setState(AR);break;
                        case 4:
                            String CA = "CA";
                            pt.getHome().setState(CA);break;
                        case 5:
                            String CO = "CO";
                            pt.getHome().setState(CO);break;
                        case 6:
                            String CT = "CT";
                            pt.getHome().setState(CT);break;
                        case 7:
                            String DE = "DE";
                            pt.getHome().setState(DE);break;
                        case 8:
                            String FL = "FL";
                            pt.getHome().setState(FL);break;
                        case 9 :
                            String GA = "GA";
                            pt.getHome().setState(GA);break;
                        case 10:
                            String HI = "HI";
                            pt.getHome().setState(HI);break;
                        case 11:
                            String ID = "ID";
                            pt.getHome().setState(ID);break;
                        case 12:
                            String IL = "IL";
                            pt.getHome().setState(IL);break;
                        case 13:
                            String IN = "IN";
                            pt.getHome().setState(IN);break;
                        case 14:
                            String IA = "IA";
                            pt.getHome().setState(IA);break;
                        case 15:
                            String KS = "KS";
                            pt.getHome().setState(KS);break;
                        case 16:
                            String KY = "KY";
                            pt.getHome().setState(KY);break;
                        case 17 :
                            String LA = "LA";
                            pt.getHome().setState(LA);break;
                        case 18:
                            String ME = "ME";
                            pt.getHome().setState(ME);break;
                        case 19:
                            String MD = "MD";
                            pt.getHome().setState(MD);break;
                        case 20:
                            String MA = "MA";
                            pt.getHome().setState(MA);break;
                        case 21:
                            String MI = "MI";
                            pt.getHome().setState(MI);break;
                        case 22:
                            String MN = "MN";
                            pt.getHome().setState(MN);break;
                        case 23:
                            String MS = "MS";
                            pt.getHome().setState(MS);break;
                        case 24:
                            String MO = "MO";
                            pt.getHome().setState(MO);break;
                        case 25 :
                            String MT = "MT";
                            pt.getHome().setState(MT);break;
                        case 26:
                            String NE = "NE";
                            pt.getHome().setState(NE);break;
                        case 27:
                            String NV = "NV";
                            pt.getHome().setState(NV);break;
                        case 28:
                            String NH = "NH";
                            pt.getHome().setState(NH);break;
                        case 29:
                            String NJ = "NJ";
                            pt.getHome().setState(NJ);break;
                        case 30 :
                            String NM = "NM";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NM);break;
                        case 31 :
                            String NY = "NY";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NY);break;
                        case 32 :
                            String NC = "NC";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NC);break;
                        case 33 :
                            String ND = "ND";
                            pt.getHome().setState(ND);break;
                        case 34:
                            String OH = "OH";
                            pt.getHome().setState(OH);break;
                        case 35:
                            String OK = "OK";
                            pt.getHome().setState(OK);break;
                        case 36:
                            String OR = "OR";
                            pt.getHome().setState(OR);break;
                        case 37:
                            String PA = "PA";
                            pt.getHome().setState(PA);break;
                        case 38:
                            String RI = "RI";
                            pt.getHome().setState(RI);break;
                        case 39 :
                            String SC = "SC";
                            pt.getHome().setState(SC);break;
                        case 40:
                            String SD = "SD";
                            pt.getHome().setState(SD);break;
                        case 41:
                            String TN = "TN";
                            pt.getHome().setState(TN);break;
                        case 42:
                            String TX = "TX";
                            pt.getHome().setState(TX);break;
                        case 43:
                            String UT = "UT";
                            pt.getHome().setState(UT);break;
                        case 44:
                            String VT = "VT";
                            pt.getHome().setState(VT);break;
                        case 45:
                            String VA = "VA";
                            pt.getHome().setState(VA);break;
                        case 46:
                            String WA = "WA";
                            pt.getHome().setState(WA);break;
                        case 47 :
                            String WV = "WV";
                            pt.getHome().setState(WV);break;
                        case 48:
                            String WI = "WI";
                            pt.getHome().setState(WI);break;
                        case 49:
                            String WY = "WY";
                            pt.getHome().setState(WY);break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            int spinnerStreetTypePos;
            switch(pt.getHome().getStreetType())
            {
                /*<option value="" selected="selected"></option>
                   <option value="ave">Avenue</option>
                   <option value="st">Street</option>
                   <option value="blvd">Boulevard</option>
                   <option value="rd">Road</option>
                   <option value="pl">Place</option>
                   <option value="cir">Circle</option>
                   <option value="hwy">Highway</option>
                   <option value="ln">Lane</option>
                   <option value="PWY">Parkway</option>
                   <option value="ter">Terrace</option>
                   <option value="trl">Trail</option>
                   <option value="ct">Court</option>
                   <option value="dr">Drive</option>
*/
                case "ave" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[0]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "st" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[1]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "blvd" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[2]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;
                case "rd" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[3]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "pl" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[4]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "cir" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[5]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "hwy" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[6]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;
                case "ln" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[7]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "PWY" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[8]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "ter" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[9]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "trl" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[10]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;

                case "ct" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[11]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "dr" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[12]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;

            }

            spinnerStreetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0:
                            String ave = "ave";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(ave);
                            break;
                        case 1:
                            String st = "st";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(st);
                            break;
                        case 2:
                            String blvd = "blvd";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(blvd);
                            break;
                        case 3:
                            String rd = "rd";
                            pt.getHome().setStreetType(rd);
                            break;
                        case 4:
                            String pl = "pl";
                            pt.getHome().setStreetType(pl);
                            break;
                        case 5:
                            String cir = "cir";
                            pt.getHome().setStreetType(cir);
                            break;
                        case 6:
                            String hwy = "hwy";
                            pt.getHome().setStreetType(hwy);
                            break;
                        case 7:
                            String ln = "ln";
                            pt.getHome().setStreetType(ln);
                            break;
                        case 8:
                            String PWY = "PWY";
                            pt.getHome().setStreetType(PWY);
                            break;
                        case 9:
                            String ter = "ter";
                            pt.getHome().setStreetType(ter);
                            break;
                        case 10:
                            String trl = "trl";
                            pt.getHome().setStreetType(trl);
                            break;
                        case 11:
                            String ct = "ct";
                            pt.getHome().setStreetType(ct);
                            break;
                        case 12:
                            String dr = "dr";
                            pt.getHome().setStreetType(dr);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            binding.setPt(pt);
            binding.executePendingBindings();
        }else{
            UsParticipantBinding binding = DataBindingUtil.inflate(inflater, R.layout.us_participant,container,false);
            view = binding.getRoot();

            spinnerDirection = (Spinner)view.findViewById(R.id.spinnerDirection);
            String[] direction = getResources().getStringArray(R.array.streetDirection);
            ArrayAdapter<String> adapterDirection = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,direction);
            spinnerDirection.setAdapter(adapterDirection);

            spinnerState = (Spinner)view.findViewById(R.id.spinnerState);
            String[] state = getResources().getStringArray(R.array.state);
            ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,state);
            spinnerState.setAdapter(adapterState);

            spinnerStreetType = (Spinner)view.findViewById(R.id.spinnerStreetType);
            String[] streetType = getResources().getStringArray(R.array.streetType);
            ArrayAdapter<String> adapterStreetType = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,streetType);
            spinnerStreetType.setAdapter(adapterStreetType);

            spinnerRecruitment = (Spinner)view.findViewById(R.id.spinnerRecruitment);
            String[] recruitment = getResources().getStringArray(R.array.recruitment);
            ArrayAdapter<String> adapterRecruitment = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,recruitment);
            spinnerRecruitment.setAdapter(adapterRecruitment);

            spinnerBornCountry = (Spinner)view.findViewById(R.id.spinnerBornCountry);
            String[] bornCountry = getResources().getStringArray(R.array.bornCountry);
            ArrayAdapter<String> adapterBornCountry = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,bornCountry);
            spinnerBornCountry.setAdapter(adapterBornCountry);

            final int spinnerPosition;
            switch(pt.getHome().getStreetDirection())
            {

                case "e" :
                    spinnerPosition = adapterDirection.getPosition(direction[0]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    /*String e = "e";
                    //e = spinnerDirection.getSelectedItem().toString();
                    pt.getHome().setStreetDirection(e);*/
                    break;
                case "s" :
                    spinnerPosition = adapterDirection.getPosition(direction[1]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    /*String s = "s";
                    //s = spinnerDirection.getSelectedItem().toString();
                    pt.getHome().setStreetDirection(s);*/
                    break;
                case "w" :
                    spinnerPosition = adapterDirection.getPosition(direction[2]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    /*String w = "w";
                    //w = spinnerDirection.getSelectedItem().toString();
                    pt.getHome().setStreetDirection(w);*/
                    break;
                case "n" :
                    spinnerPosition = adapterDirection.getPosition(direction[3]);
                    spinnerDirection.setSelection(spinnerPosition);
                    spinnerDirection.getSelectedItem().toString();
                    /*String n = "n";
                    //n = spinnerDirection.getSelectedItem().toString();
                    pt.getHome().setStreetDirection(n);*/

                    break;

            }
            //spinnerDirection.getSelectedItem().toString();
           // pt.getHome().setStreetDirection(spinnerDirection.getSelectedItem().toString());

            spinnerDirection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //pt.getHome().getStreetDirection() = spinnerDirection.getSelectedItem().toString();
                    if(position == 0) {
                        String e = "e";
                        //e = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(e);
                    }
                    else if(position == 1) {
                        String s = "s";
                        //s = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(s);
                    }
                    else if(position == 2) {
                        String w = "w";
                        //w = spinnerDirection.getSelectedItem().toString();
                        pt.getHome().setStreetDirection(w);
                    }
                    else if(position == 3) {
                        String n = "n";
                        pt.getHome().setStreetDirection(n);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            int spinnerStatePosition;
            switch(pt.getHome().getState())
            {
                /*<option value="AL">Alabama</option>
                    <option value="AK">Alaska</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="CA">California</option>*/
                case "AL" :
                    spinnerStatePosition = adapterState.getPosition(state[0]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AK" :
                    spinnerStatePosition = adapterState.getPosition(state[1]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AZ" :
                    spinnerStatePosition = adapterState.getPosition(state[2]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "AR" :
                    spinnerStatePosition = adapterState.getPosition(state[3]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "CA" :
                    spinnerStatePosition = adapterState.getPosition(state[4]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                    /*<option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>*/
                case "CO" :
                    spinnerStatePosition = adapterState.getPosition(state[5]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "CT" :
                    spinnerStatePosition = adapterState.getPosition(state[6]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "DE" :
                    spinnerStatePosition = adapterState.getPosition(state[7]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "FL" :
                    spinnerStatePosition = adapterState.getPosition(state[8]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "GA" :
                    spinnerStatePosition = adapterState.getPosition(state[9]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                   break;
                    /*<option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>*/
                case "HI" :
                    spinnerStatePosition = adapterState.getPosition(state[10]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ID" :
                    spinnerStatePosition = adapterState.getPosition(state[11]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IL" :
                    spinnerStatePosition = adapterState.getPosition(state[12]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IN" :
                    spinnerStatePosition = adapterState.getPosition(state[13]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "IA" :
                    spinnerStatePosition = adapterState.getPosition(state[14]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                     /*<option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MD">Maryland</option>*/

                case "KS" :
                    spinnerStatePosition = adapterState.getPosition(state[15]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "KY" :
                    spinnerStatePosition = adapterState.getPosition(state[16]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "LA" :
                    spinnerStatePosition = adapterState.getPosition(state[17]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ME" :
                    spinnerStatePosition = adapterState.getPosition(state[18]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MD" :
                    spinnerStatePosition = adapterState.getPosition(state[19]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                   /*  <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>*/

                case "MA" :
                    spinnerStatePosition = adapterState.getPosition(state[20]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MI" :
                    spinnerStatePosition = adapterState.getPosition(state[21]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MN" :
                    spinnerStatePosition = adapterState.getPosition(state[22]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MS" :
                    spinnerStatePosition = adapterState.getPosition(state[23]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "MO" :
                    spinnerStatePosition = adapterState.getPosition(state[24]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                    /*<option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>*/
                case "MT" :
                    spinnerStatePosition = adapterState.getPosition(state[25]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NE" :
                    spinnerStatePosition = adapterState.getPosition(state[26]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NV" :
                    spinnerStatePosition = adapterState.getPosition(state[27]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NH" :
                    spinnerStatePosition = adapterState.getPosition(state[28]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NJ" :
                    spinnerStatePosition = adapterState.getPosition(state[29]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                   break;

                     /*<option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="OH">Ohio</option>*/
                case "NM" :
                    spinnerStatePosition = adapterState.getPosition(state[30]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NY" :
                    spinnerStatePosition = adapterState.getPosition(state[31]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "NC" :
                    spinnerStatePosition = adapterState.getPosition(state[32]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "ND" :
                    spinnerStatePosition = adapterState.getPosition(state[33]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "OH" :
                    spinnerStatePosition = adapterState.getPosition(state[34]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                     /*<option value="OK">Oklahoma</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SC">South Carolina</option>*/
                case "OK" :
                    spinnerStatePosition = adapterState.getPosition(state[35]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "OR" :
                    spinnerStatePosition = adapterState.getPosition(state[36]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "PA" :
                    spinnerStatePosition = adapterState.getPosition(state[37]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "RI" :
                    spinnerStatePosition = adapterState.getPosition(state[38]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "SC" :
                    spinnerStatePosition = adapterState.getPosition(state[39]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                   /*  <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>*/

                case "SD" :
                    spinnerStatePosition = adapterState.getPosition(state[40]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "TN" :
                    spinnerStatePosition = adapterState.getPosition(state[41]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "TX" :
                    spinnerStatePosition = adapterState.getPosition(state[42]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "UT" :
                    spinnerStatePosition = adapterState.getPosition(state[43]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "VT" :
                    spinnerStatePosition = adapterState.getPosition(state[44]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;

                  /*   <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>*/
                case "VA" :
                    spinnerStatePosition = adapterState.getPosition(state[45]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WA" :
                    spinnerStatePosition = adapterState.getPosition(state[46]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WV" :
                    spinnerStatePosition = adapterState.getPosition(state[47]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WI" :
                    spinnerStatePosition = adapterState.getPosition(state[48]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
                case "WY" :
                    spinnerStatePosition = adapterState.getPosition(state[49]);
                    spinnerState.setSelection(spinnerStatePosition);
                    spinnerState.getSelectedItem().toString();
                    break;
            }

            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //pt.getHome().getStreetDirection() = spinnerDirection.getSelectedItem().toString();
                    switch(position)
                    {
                        case 0 :
                            String AL = "AL";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AL);break;
                        case 1 :
                            String AK = "AK";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AK);break;
                        case 2 :
                            String AZ = "AZ";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(AZ);break;
                        case 3 :
                            String AR = "AR";
                            pt.getHome().setState(AR);break;
                        case 4:
                            String CA = "CA";
                            pt.getHome().setState(CA);break;
                        case 5:
                            String CO = "CO";
                            pt.getHome().setState(CO);break;
                        case 6:
                            String CT = "CT";
                            pt.getHome().setState(CT);break;
                        case 7:
                            String DE = "DE";
                            pt.getHome().setState(DE);break;
                        case 8:
                            String FL = "FL";
                            pt.getHome().setState(FL);break;
                        case 9 :
                            String GA = "GA";
                            pt.getHome().setState(GA);break;
                        case 10:
                            String HI = "HI";
                            pt.getHome().setState(HI);break;
                        case 11:
                            String ID = "ID";
                            pt.getHome().setState(ID);break;
                        case 12:
                            String IL = "IL";
                            pt.getHome().setState(IL);break;
                        case 13:
                            String IN = "IN";
                            pt.getHome().setState(IN);break;
                        case 14:
                            String IA = "IA";
                            pt.getHome().setState(IA);break;
                        case 15:
                            String KS = "KS";
                            pt.getHome().setState(KS);break;
                        case 16:
                            String KY = "KY";
                            pt.getHome().setState(KY);break;
                        case 17 :
                            String LA = "LA";
                            pt.getHome().setState(LA);break;
                        case 18:
                            String ME = "ME";
                            pt.getHome().setState(ME);break;
                        case 19:
                            String MD = "MD";
                            pt.getHome().setState(MD);break;
                        case 20:
                            String MA = "MA";
                            pt.getHome().setState(MA);break;
                        case 21:
                            String MI = "MI";
                            pt.getHome().setState(MI);break;
                        case 22:
                            String MN = "MN";
                            pt.getHome().setState(MN);break;
                        case 23:
                            String MS = "MS";
                            pt.getHome().setState(MS);break;
                        case 24:
                            String MO = "MO";
                            pt.getHome().setState(MO);break;
                        case 25 :
                            String MT = "MT";
                            pt.getHome().setState(MT);break;
                        case 26:
                            String NE = "NE";
                            pt.getHome().setState(NE);break;
                        case 27:
                            String NV = "NV";
                            pt.getHome().setState(NV);break;
                        case 28:
                            String NH = "NH";
                            pt.getHome().setState(NH);break;
                        case 29:
                            String NJ = "NJ";
                            pt.getHome().setState(NJ);break;
                        case 30 :
                            String NM = "NM";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NM);break;
                        case 31 :
                            String NY = "NY";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NY);break;
                        case 32 :
                            String NC = "NC";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setState(NC);break;
                        case 33 :
                            String ND = "ND";
                            pt.getHome().setState(ND);break;
                        case 34:
                            String OH = "OH";
                            pt.getHome().setState(OH);break;
                        case 35:
                            String OK = "OK";
                            pt.getHome().setState(OK);break;
                        case 36:
                            String OR = "OR";
                            pt.getHome().setState(OR);break;
                        case 37:
                            String PA = "PA";
                            pt.getHome().setState(PA);break;
                        case 38:
                            String RI = "RI";
                            pt.getHome().setState(RI);break;
                        case 39 :
                            String SC = "SC";
                            pt.getHome().setState(SC);break;
                        case 40:
                            String SD = "SD";
                            pt.getHome().setState(SD);break;
                        case 41:
                            String TN = "TN";
                            pt.getHome().setState(TN);break;
                        case 42:
                            String TX = "TX";
                            pt.getHome().setState(TX);break;
                        case 43:
                            String UT = "UT";
                            pt.getHome().setState(UT);break;
                        case 44:
                            String VT = "VT";
                            pt.getHome().setState(VT);break;
                        case 45:
                            String VA = "VA";
                            pt.getHome().setState(VA);break;
                        case 46:
                            String WA = "WA";
                            pt.getHome().setState(WA);break;
                        case 47 :
                            String WV = "WV";
                            pt.getHome().setState(WV);break;
                        case 48:
                            String WI = "WI";
                            pt.getHome().setState(WI);break;
                        case 49:
                            String WY = "WY";
                            pt.getHome().setState(WY);break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            int spinnerStreetTypePos;
            switch(pt.getHome().getStreetType())
            {
                /*<option value="" selected="selected"></option>
                   <option value="ave">Avenue</option>
                   <option value="st">Street</option>
                   <option value="blvd">Boulevard</option>
                   <option value="rd">Road</option>
                   <option value="pl">Place</option>
                   <option value="cir">Circle</option>
                   <option value="hwy">Highway</option>
                   <option value="ln">Lane</option>
                   <option value="PWY">Parkway</option>
                   <option value="ter">Terrace</option>
                   <option value="trl">Trail</option>
                   <option value="ct">Court</option>
                   <option value="dr">Drive</option>
*/
                case "ave" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[0]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "st" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[1]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "blvd" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[2]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;
                case "rd" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[3]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "pl" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[4]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "cir" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[5]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "hwy" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[6]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;
                case "ln" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[7]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "PWY" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[8]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "ter" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[9]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "trl" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[10]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;

                case "ct" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[11]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();
                    break;
                case "dr" :
                    spinnerStreetTypePos = adapterStreetType.getPosition(streetType[12]);
                    spinnerStreetType.setSelection(spinnerStreetTypePos);
                    spinnerStreetType.getSelectedItem().toString();

                    break;

            }

            spinnerStreetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0:
                            String ave = "ave";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(ave);
                            break;
                        case 1:
                            String st = "st";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(st);
                            break;
                        case 2:
                            String blvd = "blvd";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.getHome().setStreetType(blvd);
                            break;
                        case 3:
                            String rd = "rd";
                            pt.getHome().setStreetType(rd);
                            break;
                        case 4:
                            String pl = "pl";
                            pt.getHome().setStreetType(pl);
                            break;
                        case 5:
                            String cir = "cir";
                            pt.getHome().setStreetType(cir);
                            break;
                        case 6:
                            String hwy = "hwy";
                            pt.getHome().setStreetType(hwy);
                            break;
                        case 7:
                            String ln = "ln";
                            pt.getHome().setStreetType(ln);
                            break;
                        case 8:
                            String PWY = "PWY";
                            pt.getHome().setStreetType(PWY);
                            break;
                        case 9:
                            String ter = "ter";
                            pt.getHome().setStreetType(ter);
                            break;
                        case 10:
                            String trl = "trl";
                            pt.getHome().setStreetType(trl);
                            break;
                        case 11:
                            String ct = "ct";
                            pt.getHome().setStreetType(ct);
                            break;
                        case 12:
                            String dr = "dr";
                            pt.getHome().setStreetType(dr);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            int spinnerRecruitmentPos;
            switch(pt.getRecruitment())
            {
                /*<option value="ddp">Dr. Dong's patient</option>
                    <option value="fg">Focus group (RO3)</option>
                    <option value="refer">Referred by a participant</option>
                    <option value="edus">Education sessions</option>
                    <option value="dtdc">Door to door census</option>
                    <option value="ccba">CCBA</option>
                    <option value="cpihs">CASL Program - IHS</option>
                    <option value="cppine">CASL Program - Pine Tree</option>
                    <option value="cpads">CASL Program - ADS</option>
                    <option value="ad">Advertisement</option>
                    <option value="stt">St Therese</option>
                    <option value="maha">MAHA</option>
                    <option value="nw">Northwestern</option>
                    <option value="snps">SN project-Senior</option>
                    <option value="snpf">SN project-Family member</option>
                    <option value="xilin">XiLin</option>
                    <option value="blc">Bread for Life Church, Evanston</option>
                    <option value="hpsg">Hyde Park senior group</option>
                    <option value="cmaa">Chinese Mutual Aid Association</option>
                    <option value="seac">South-East Asia Center</option>
                    <option value="benton">Benton House</option>
                    <option value="seniors">From Seniors</option>
                    <option value="other">Other</option>*/

                case "ddp" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[0]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "fg" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[1]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "refer" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[2]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "edus" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[3]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "dtdc" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[4]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;

                case "ccba" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[5]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "cpihs" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[6]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "cppine" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[7]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "cpads" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[8]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "ad" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[9]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;

                case "stt" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[10]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "maha" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[11]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "nw" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[12]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "snps" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[13]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "snpf" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[14]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;


                case "xilin" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[15]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "blc" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[16]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "hpsg" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[17]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;

                case "cmaa" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[18]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "seac" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[19]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "benton" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[20]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "seniors" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[21]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;
                case "other" :
                    spinnerRecruitmentPos = adapterRecruitment.getPosition(recruitment[22]);
                    spinnerRecruitment.setSelection(spinnerRecruitmentPos);
                    spinnerRecruitment.getSelectedItem().toString();
                    break;

            }

            spinnerRecruitment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0:
                            String ddp = "ddp";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.setRecruitment(ddp);
                            break;
                        case 1:
                            String fg = "fg";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.setRecruitment(fg);
                            break;
                        case 2:
                            String refer = "refer";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.setRecruitment(refer);
                            break;
                        case 3:
                            String edus = "edus";
                            pt.setRecruitment(edus);
                            break;
                        case 4:
                            String dtdc = "dtdc";
                            pt.setRecruitment(dtdc);
                            break;
                        case 5:
                            String ccba = "ccba";
                            pt.setRecruitment(ccba);
                            break;
                        case 6:
                            String cpihs = "cpihs";
                            pt.setRecruitment(cpihs);
                            break;
                        case 7:
                            String cppine = "cppine";
                            pt.setRecruitment(cppine);
                            break;
                        case 8:
                            String cpads = "cpads";
                            pt.setRecruitment(cpads);
                            break;
                        case 9:
                            String ad = "ad";
                            pt.setRecruitment(ad);
                            break;
                        case 10:
                            String stt = "stt";
                            pt.setRecruitment(stt);
                            break;
                        case 11:
                            String maha = "maha";
                            pt.setRecruitment(maha);
                            break;
                        case 12:
                            String nw = "nw";
                            pt.setRecruitment(nw);
                            break;
                        case 13:
                            String snps = "snps";
                            pt.setRecruitment(snps);
                            break;
                        case 14:
                            String snpf = "snpf";
                            pt.setRecruitment(snpf);
                            break;
                        case 15:
                            String xilin = "xilin";
                            pt.setRecruitment(xilin);
                            break;
                        case 16:
                            String blc = "blc";
                            pt.setRecruitment(blc);
                            break;
                        case 17 :
                            String hpsg = "hpsg";
                            pt.setRecruitment(hpsg);
                            break;
                        case 18:
                            String cmaa = "cmaa";
                            pt.setRecruitment(cmaa);
                            break;
                        case 19:
                            String seac = "seac";
                            pt.setRecruitment(seac);
                            break;
                        case 20:
                            String benton = "benton";
                            pt.setRecruitment(benton);
                            break;
                        case 21:
                            String seniors = "seniors";
                            pt.setRecruitment(seniors);
                            break;
                        case 22:
                            String other = "other";
                            pt.setRecruitment(other);
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            int spinnerBornCountryPos;
            switch(pt.getBornCountry())
            {

               /* <select name="user.bornCountry" id="bornCountry" style="display: none;">
        <option value=""></option>
        <option value="chn" selected="selected">Mainland China</option>
        <option value="hkm">Hong Kong/Macau</option>
        <option value="tai">Taiwan</option>
        <option value="mal">Malaysia</option>
        <option value="sin">Singapore</option>
        <option value="phi">Philippines</option>
        <option value="tha">Thailand</option>
        <option value="vie">Vietnam</option>
        <option value="usa">USA</option>
        <option value="can">Canada</option>
        <option value="other">Other</option>*/

                case "chn" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[0]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "hkm" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[1]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "tai" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[2]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();

                    break;
                case "mal" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[3]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "sin" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[4]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "phi" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[5]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "tha" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[6]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();

                    break;
                case "vie" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[7]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "usa" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[8]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "can" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[9]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;
                case "other" :
                    spinnerBornCountryPos = adapterBornCountry.getPosition(bornCountry[10]);
                    spinnerBornCountry.setSelection(spinnerBornCountryPos);
                    spinnerBornCountry.getSelectedItem().toString();
                    break;

            }

            spinnerBornCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0:
                            String chn = "chn";
                            //e = spinnerDirection.getSelectedItem().toString();
                            pt.setBornCountry(chn);
                            break;
                        case 1:
                            String hkm = "hkm";
                            //s = spinnerDirection.getSelectedItem().toString();
                            pt.setBornCountry(hkm);
                            break;
                        case 2:
                            String tai = "tai";
                            //w = spinnerDirection.getSelectedItem().toString();
                            pt.setBornCountry(tai);
                            break;
                        case 3:
                            String mal = "mal";
                            pt.setBornCountry(mal);
                            break;
                        case 4:
                            String sin = "sin";
                            pt.setBornCountry(sin);
                            break;
                        case 5:
                            String phi = "phi";
                            pt.setBornCountry(phi);
                            break;
                        case 6:
                            String tha = "tha";
                            pt.setBornCountry(tha);
                            break;
                        case 7:
                            String vie = "vie";
                            pt.setBornCountry(vie);
                            break;
                        case 8:
                            String usa = "usa";
                            pt.setBornCountry(usa);
                            break;
                        case 9:
                            String can = "can";
                            pt.setBornCountry(can);
                            break;
                        case 10:
                            String other = "other";
                            pt.setBornCountry(other);
                            break;

                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            binding.setPt(pt);
            binding.executePendingBindings();
        }

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.ptLayout);
        int count = tableLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = tableLayout.getChildAt(i);
            if (v instanceof TableRow) {
                if (((TableRow) v).getChildCount()==2 && ((TableRow) v).getChildAt(1) instanceof EditText){
                    EditText cv = (EditText)((TableRow) v).getChildAt(1);
                   // Spinner sp = (Spinner)((TableRow)v).getChildAt(1);
                    cv.addTextChangedListener(new ParticipantTextWatcher(cv, pt));

                    //sp.addTextChangedListener(new ParticipantTextWatcher(sp,pt));
                }
            }
        }
        //language
       /* Spinner readPre = (Spinner)view.findViewById(R.id.lang_pre_read);
        Spinner speakPre = (Spinner)view.findViewById(R.id.lang_pre_speak);
        Spinner writePre = (Spinner)view.findViewById(R.id.lang_pre_write);
        readPre.setSelection(getPosition(pt.getLangReadPre()));
        speakPre.setSelection(getPosition(pt.getLangSpeakPre()));
        writePre.setSelection(getPosition(pt.getLangWritePre()));*/


        return view;
    }

    public int getPosition(String lang){
        if (lang.equals("en")){
            return 0;
        }
        else if (lang.equals("pt")){
            return 1;
        }
        else if (lang.equals("gd")){
            return 2;
        }
        else if (lang.equals("ts")){
            return 3;
        }
        else if (lang.equals("cn")){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //spinnerDirection.setSelection(getPosition(pt.getHome().getStreetDirection()));
        participantDao.insertOrReplace(pt);
        if (pt.getHome()!=null) {
            homeDao.insertOrReplace(pt.getHome());
        }
    }
}
