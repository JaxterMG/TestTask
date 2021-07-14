package com.example.testtask.TerminalFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testtask.Database;
import com.example.testtask.R;
import com.example.testtask.TerminalCell;

import java.util.List;

public class FromFragment extends Fragment implements  View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout layoutList;
    Button add;
    private String mParam1;
    private String mParam2;
    List<TerminalCell> fromCells;

    public FromFragment() {
    }

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
        fromCells = Database.Instance.GetGiveOutList();
        for (int i = 0; i < fromCells.size(); i++) {
            AddView(i);
        }

        return view;
    }

    private class AddViews extends AsyncTask<Void, Void, String> {

        protected void onPreExecute()
        {
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
        layoutList.addView(cellView);
    }

    private  void  DeleteView(View v)
    {

    }

    @Override
    public void onClick(View v) {

    }
}