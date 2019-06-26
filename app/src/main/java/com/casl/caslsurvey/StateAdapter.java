package com.casl.caslsurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.casl.model.InstrumentInstance;
import com.casl.model.InterviewStatus;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.util.List;

import static com.casl.caslsurvey.R.drawable.ic_file_upload_black_24dp;

/**
 * Created by Staff73 on 6/27/2017.
 */

public abstract class StateAdapter<T> extends ArrayAdapter<T>  {
    private int mListItemLayoutResId;

    public StateAdapter(Context context, List<T> ts) {
        this(context, R.layout.instrument_item, ts);
    }

    public StateAdapter(
            Context context,
            int listItemLayoutResourceId,
            List<T> ts) {
        super(context, listItemLayoutResourceId, ts);
        mListItemLayoutResId = listItemLayoutResourceId;
    }

    @Override
    public android.view.View getView(
            int position,
            View convertView,
            ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(
                    mListItemLayoutResId,
                    parent,
                    false);
        }
        // The ListItemLayout must use the standard text item IDs.
        CheckedTextView ctv = (CheckedTextView)listItemView.findViewById(R.id.checkedTextView);
        T t = getItem(position);
        ctv.setText(t.toString());
        InstrumentInstance instrumentInstance = getStatus(t);
        if (instrumentInstance.isFinished()==1&&!instrumentInstance.isUploaded()){
            ctv.setText(t.toString()+" (NOT UPLOADED)");
        }
        return listItemView;
    }
    public abstract InstrumentInstance getStatus(T t);
}
