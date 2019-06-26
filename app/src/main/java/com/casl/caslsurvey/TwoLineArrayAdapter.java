package com.casl.caslsurvey;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.casl.model.InterviewStatus;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Staff73 on 6/27/2017.
 */

public abstract class TwoLineArrayAdapter<T> extends ArrayAdapter<T>  {
    private int mListItemLayoutResId;

    public TwoLineArrayAdapter(Context context, List<T> ts) {
        this(context, android.R.layout.two_line_list_item, ts);
    }

    public TwoLineArrayAdapter(
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
        TextView lineOneView = (TextView)listItemView.findViewById(
                android.R.id.text1);
        TextView lineTwoView = (TextView)listItemView.findViewById(
                android.R.id.text2);
        TextView lineThreeView = (TextView)listItemView.findViewById(
                R.id.text3);
        TextView lineFourView = (TextView)listItemView.findViewById(
                R.id.text4);

       /* lineThreeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        T t = getItem(position);
        lineOneView.setText(Html.fromHtml(lineOneText(t)));
        lineTwoView.setText(lineTwoText(t));
        //lineThreeView.setText(Html.fromHtml(lineThreeText(t)));
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        lineFourView.setText(df.format(getPercentage(t)*100)+"%");
        return listItemView;
    }

    public abstract float getPercentage(T t);

    public abstract String lineOneText(T t);

    public abstract String lineTwoText(T t);

    public abstract String lineThreeText(T t);
}
