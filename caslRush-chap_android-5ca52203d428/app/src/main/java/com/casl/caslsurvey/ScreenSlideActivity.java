package com.casl.caslsurvey;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.casl.model.Instrument;
import com.casl.model.InstrumentDao;
import com.casl.model.Participant;
import com.casl.model.ParticipantDao;

public class ScreenSlideActivity extends MainActivity implements QuestionListFragment.OnFragmentInteractionListener {
    private Participant pt;
    private int iid;
    private int curr;
    private String istr;
    private Float percentage;
    private long[] instList;
    private CustomViewPager mPager;
    private Button next;
    private Button prev;
    private Button home;
    private PagerAdapter mPagerAdapter;
    private InstrumentDao instrumentDao;
    public static final String PID = "pid";
    public static final String IID = "iid";
    public static final String InterviewId = "id";
    public static final String PER = "percentage";
    public static final String PSTR = "pstr";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: change to fragmentManager
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.contraint_content);
        View childLayout = inflater.inflate(R.layout.activity_screen_slide,null);
        //cl.addView(childLayout);
        zoomView.addView(childLayout);
        //get data
        instrumentDao = ((App)getApplication()).getDaoSession().getInstrumentDao();
        Intent intent = getIntent();
        pt = intent.getParcelableExtra(InterviewActivity.PID);
        iid = intent.getIntExtra(InterviewActivity.IID,-1);
        instList = intent.getLongArrayExtra(InstrumentActivity.INS_LIST);
        istr = intent.getStringExtra("Interview");
        percentage = getIntent().getFloatExtra("percentage", (float) 0.0);
        curr = intent.getIntExtra("position",0);
        //ui
        //setContentView(R.layout.activity_screen_slide);
        mPager = (CustomViewPager) childLayout.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(0);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(),this.getParent());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(curr);
        next = (Button)findViewById(R.id.next_btn);
        prev = (Button)findViewById(R.id.prev_btn);
        home = (Button)findViewById(R.id.home_btn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(instList.length-1 != mPager.getCurrentItem())
                {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }else{
                    //Toast.makeText(ScreenSlideActivity.this, "Thanks!!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(ScreenSlideActivity.this, ThankYouActivity.class);
                    intent1.putExtra(PID,pt);
                    intent1.putExtra(IID,iid);
                    intent1.putExtra(InterviewId,istr);
                    intent1.putExtra(PER,percentage);

                    startActivity(intent1);
                    finish();
                    //finish();
                }



                //next.setEnabled(false);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instList==null){
                    onBackPressed();
                }
                else {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (instList==null){
            prev.setVisibility(View.VISIBLE);
            prev.setText("Update");
            home.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
            /*((LinearLayout.LayoutParams)home.getLayoutParams()).weight = 0;
            home.getLayoutParams().width= LinearLayout.LayoutParams.WRAP_CONTENT;*/
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Activity context;

        public ScreenSlidePagerAdapter(FragmentManager fm, Activity context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (instList==null){
                return ParticipantFragment.create(pt.getId(),iid);
            }
            /*else if(instList == null)
            {
                return ContactFragment.create(pt.getId(),iid);
            }*/

            Instrument instrument = instrumentDao.load(instList[position]);
            getIntent().putExtra("position",mPager.getCurrentItem());
            //progressDialog.show();
            return QuestionListFragment.create((int)instList[position],pt,iid,instrument.toString(),instrument.getContent(),staffId);

        }

        @Override
        public int getCount() {
            if (instList==null){
                return 1;
            }

            return instList.length;
        }
    }


}
