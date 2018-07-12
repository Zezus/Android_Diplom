package com.example.azia.diplom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView itemRecyclerView;
    private ArrayList<Item> items;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        itemRecyclerView = view.findViewById(R.id.fl_items_rv);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        init();

        itemRecyclerView.setAdapter(new ItemAdapter(getContext(), items, (MainActivity) getActivity()));

        return view;

    }

    private void init() {
        Item item1 = new Item();
        item1.setId(UUID.randomUUID());
        item1.setTitle("Расписание");
        item1.setPhoto(R.drawable.classroom);

        Item item2 = new Item();
        item2.setId(UUID.randomUUID());
        item2.setTitle("iPhone X");
        item2.setPhoto(R.drawable.iphone);


        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
    }


    public interface Callback {
        void changeFragmentClicked(View view, Item item);
    }


}
