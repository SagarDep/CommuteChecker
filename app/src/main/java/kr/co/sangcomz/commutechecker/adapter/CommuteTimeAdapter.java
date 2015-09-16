package kr.co.sangcomz.commutechecker.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.sangcomz.commutechecker.R;
import kr.co.sangcomz.commutechecker.bean.CommuteTimeBean;
import kr.co.sangcomz.commutechecker.util.TimeUtils;

/**
 * Created by sangc on 2015-09-15.
 */
public class CommuteTimeAdapter
        extends RecyclerView.Adapter<CommuteTimeAdapter.ViewHolder> {

    ArrayList<CommuteTimeBean> commuteTimeBeans;

    public CommuteTimeAdapter(ArrayList<CommuteTimeBean> commuteTimeBeans){
        this.commuteTimeBeans = commuteTimeBeans;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView commuteDate;
        public final TextView commuteTime;

        public ViewHolder(View view) {
            super(view);
            commuteDate = (TextView) view.findViewById(R.id.txt_date);
            commuteTime = (TextView) view.findViewById(R.id.txt_time);
        }
    }


//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.commute_time_item, parent, false);
//        return new ViewHolder(view);
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commute_time_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position%2 == 1)
            holder.commuteDate.setBackgroundColor(Color.parseColor("#000000"));
        holder.commuteDate.setText(TimeUtils.getDateString("MM/dd", commuteTimeBeans.get(position).getCommuteTime()));
        holder.commuteTime.setText(TimeUtils.getDateString("HH:mm", commuteTimeBeans.get(position).getCommuteTime()));
    }


    @Override
    public int getItemCount() {
        return commuteTimeBeans.size();
    }
}
