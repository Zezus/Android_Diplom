package com.example.azia.diplom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azia.diplom.helpers.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private Button button_back;
    private TextView nameTV;
    private TextView coastTV;
    private TextView titleTV;
    private ImageView imageV;

    private ViewGroup container;
    private LayoutInflater inflater;

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);


        button_back = view.findViewById(R.id.fi_back_btn);
        nameTV = view.findViewById(R.id.fi_name_tv);
        coastTV = view.findViewById(R.id.fi_coast_tv);
        titleTV = view.findViewById(R.id.fi_title_tv);
        imageV = view.findViewById(R.id.i_image_iv);

        button_back.setOnClickListener(view1 -> {
            getFragmentManager().popBackStack();
        });

        Item item = getArguments().getParcelable("item");
        titleTV.setText(item.getTitle());
        imageV.setImageResource(item.getPhoto());

//        this.container = container;
//        this.inflater = inflater;


        return view;
//        return initializeInterface();
    }

//    public View initializeInterface(){
//        View view;
//
//        if (container != null){
//            container.removeAllViewsInLayout();
//        }
//
//        int orientation = getActivity().getResources().getConfiguration().orientation;
//
//        if (orientation == Configuration.ORIENTATION_PORTRAIT){
//            view = inflater.inflate(R.layout.fragment_item, container, false);
//        }
//        else {
//            view = inflater.inflate(R.layout.fragment_item, container, false);
//        }
//
//        button_back = view.findViewById(R.id.fi_back_btn);
//        nameTV = view.findViewById(R.id.fi_name_tv);
//        coastTV = view.findViewById(R.id.fi_coast_tv);
//        titleTV = view.findViewById(R.id.fi_title_tv);
//
//        Item item = getArguments().getParcelable("item");
//        nameTV.setText(item.getName());
//        coastTV.setText(item.getCoast());
//        titleTV.setText(item.getTitle());
//
//        button_back.setOnClickListener(view1 -> {
//            getFragmentManager().popBackStack();
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        String s_name  = nameTV.getText().toString();
//        String s_coast = coastTV.getText().toString();
//        String s_title = titleTV.getText().toString();
//
//        View view = initializeInterface();
//
//        nameTV.setText(s_name);
//        coastTV.setText(s_coast);
//        titleTV.setText(s_title);
//
//        container.addView(view);
//
//        super.onConfigurationChanged(newConfig);
//    }
}
