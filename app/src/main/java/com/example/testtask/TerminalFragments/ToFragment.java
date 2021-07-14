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

import java.util.LinkedList;
import java.util.List;

public class ToFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    List<TerminalCell> toCells = new LinkedList<>();
    LinearLayout layoutList;

    Button add;

    public ToFragment() {
    }

    public static ToFragment newInstance(String param1, String param2) {
        ToFragment fragment = new ToFragment();
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

        View view = inflater.inflate(R.layout.fragment_to, container, false);
        layoutList = view.findViewById(R.id.layout_list);
        toCells = Database.Instance.GetReceiveList();
        for (int i = 0; i < toCells.size(); i++) {
            AddView(i);
        }
        AddViews addViews = new AddViews();
        //addViews.execute();

        return view;
    }

    private void AddView(int i) {
        View cellView = getLayoutInflater().inflate(R.layout.terminal_cell_layout, null, false);
        TextView city = cellView.findViewById(R.id.City);
        TextView worktable = cellView.findViewById(R.id.worktable);
        city.setText(toCells.get(i).getCity());
        worktable.setText(toCells.get(i).getWorktable());
        layoutList.addView(cellView);
    }

    private void DeleteView(View v) {

    }

    private class AddViews extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids)
        {
            for (int i = 0; i < toCells.size(); i++)
            {
                AddView(i);
            }
            return  null;
        }
    }
}





