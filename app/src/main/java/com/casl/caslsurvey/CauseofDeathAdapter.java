package com.casl.caslsurvey;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class CauseofDeathAdapter extends RecyclerView.Adapter<CauseofDeathAdapter.ViewHolder> {

    private Activity context;
    //private int size;
    List<String> list;

    public CauseofDeathAdapter(Activity context, List<String> list) {
        this.context = context;
       // this.size = size;
        this.list = list;

    }

    @NonNull
    @Override
    public CauseofDeathAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact, viewGroup, false);
        CauseofDeathAdapter.ViewHolder viewHolder = new CauseofDeathAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CauseofDeathAdapter.ViewHolder viewHolder, final int position) {


       // list.get(position);
        viewHolder.textViewCauseofDeath.setText(list.get(position));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(position)
                {
                    case 0:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 1:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 2:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 3:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 4:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 5:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 6:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    case 7:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.brown));
                        break;
                    default:
                        viewHolder.textViewCauseofDeath.setBackgroundColor(context.getResources().getColor(R.color.offwhite));
                        break;



                }

                /*Intent intent = new Intent(context, KamgarListActivity.class);
                intent.putExtra("KamgarSubCategory", (Serializable) subcategoryListResponse);
                context.startActivity(intent);*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
       // return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCauseofDeath;
        private CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardView);
            textViewCauseofDeath = (TextView)itemView.findViewById(R.id.textViewCauseofDeath);

        }
    }
}
