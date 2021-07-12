package com.example.testtask.TerminalFragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testtask.Database;
import com.example.testtask.MainActivity;
import com.example.testtask.R;
import com.example.testtask.TerminalCell;
import com.example.testtask.TerminalsScreen;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FromFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FromFragment extends Fragment implements  View.OnClickListener{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout layoutList;
    Button add;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<TerminalCell> fromCells;

    public FromFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FromFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FromFragment newInstance(String param1, String param2) {
        FromFragment fragment = new FromFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_from, container, false);
        layoutList = view.findViewById(R.id.layout_list);
        fromCells = Database.Instance.GetReceiveList();
        AddViews addViews = new AddViews();
        addViews.execute();

        return view;
    }

    private class AddViews extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(Void... voids)
        {
            for (int i = 0; i < fromCells.size(); i++)
            {
                AddView(i);
            }
            return  null;
        }

    }


    private void AddView(int i) {
        View cellView = getLayoutInflater().inflate(R.layout.terminal_cell_layout, null, false);
        TextView city = cellView.findViewById(R.id.City);
        city.setText(fromCells.get(i).getCity());

        System.out.println(fromCells.get(i).getCity());
        layoutList.addView(cellView);
    }

    private  void  DeleteView(View v)
    {

    }


    @Override
    public void onClick(View v) {

    }
}