package com.example.azia.diplom;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by СадвакасовР on 04.04.2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context context;
    private final List<Item> itemList;
    private MainActivity mainActivity;
    private Dialog dialog;


    public ItemAdapter(Context context, List<Item> itemList, MainActivity mainActivity) {
        this.context = context;
        this.itemList = itemList;
        this.mainActivity = mainActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_item);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Item item = itemList.get(position);
        holder.imageButton.setImageResource(item.getPhoto());
        holder.titleTV.setText(item.getTitle());

        holder.imageButton.setOnClickListener(view1 -> {
            mainActivity.changeFragmentClicked(null, item);
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        ImageButton imageButton;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.txt_movie_details);
            imageButton = itemView.findViewById(R.id.img_background);
            relativeLayout = itemView.findViewById(R.id.card_rl);

        }

    }
}

