package com.example.azia.diplom.mainMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azia.diplom.R;
import com.example.azia.diplom.helpers.Item;

import java.util.ArrayList;
import java.util.UUID;

import me.anwarshahriar.calligrapher.Calligrapher;


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

        Calligrapher calligrapher = new Calligrapher(getActivity());
        calligrapher.setFont(getActivity(), "rus.ttf", true);

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
        item2.setTitle("Таймер для выполнения \n\tдомашнего задания");
        item2.setPhoto(R.drawable.timer);

        Item item3 = new Item();
        item3.setId(UUID.randomUUID());
        item3.setTitle("Предметы и \n\tпреподаватели");
        item3.setPhoto(R.drawable.objects);

        Item item4 = new Item();
        item4.setId(UUID.randomUUID());
        item4.setTitle("Домашнее задание");
        item4.setPhoto(R.drawable.homework);

        Item item5 = new Item();
        item5.setId(UUID.randomUUID());
        item5.setTitle("Записи");
        item5.setPhoto(R.drawable.notes);

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
    }


    public interface Callback {
        void changeFragmentClicked(View view, Item item);
    }


}
