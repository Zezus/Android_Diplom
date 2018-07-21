package com.example.azia.diplom;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AZIA on 20.07.2018.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private final Context context;
    private final List<ScheduleList> scheduleLists;
    private ScheduleActivity mainActivity;
    private Dialog dialog;

    public ScheduleListAdapter(Context context, List<ScheduleList> scheduleLists, ScheduleActivity mainActivity) {
        this.context = context;
        this.scheduleLists = scheduleLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.schedule, null);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_monday1);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final ScheduleList scheduleList = scheduleLists.get(position);
        holder.object.setText(scheduleList.getObject());
        holder.room.setText(scheduleList.getRoom());
        holder.time_start.setText(scheduleList.getTime_start());
        holder.time_end.setText(scheduleList.getTime_end());
        holder.days.setText(scheduleList.getDay());

    }

    @Override
    public int getItemCount() {
        return scheduleLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView object;
        TextView room;
        TextView time_start;
        TextView time_end;
        TextView days;
        RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            object = itemView.findViewById(R.id.sc_object);
            room = itemView.findViewById(R.id.sc_room);
            time_start = itemView.findViewById(R.id.sc_time_start);
            time_end = itemView.findViewById(R.id.sc_time_end);
            days = itemView.findViewById(R.id.sc_days);
            room = itemView.findViewById(R.id.sc_room);
            relativeLayout = itemView.findViewById(R.id.sc_rl);
        }
    }
}
