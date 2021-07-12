package com.example.testtask.TerminalFragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.testtask.*;
import com.example.testtask.R;
import com.example.testtask.TerminalCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<TerminalCell> toCells = new LinkedList<>();
    LinearLayout layoutList;

    Button add;

    public ToFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        AddViews addViews = new AddViews();
        addViews.execute();

        return view;
    }


    private void AddView(int i) {
        View cellView = getLayoutInflater().inflate(R.layout.terminal_cell_layout, null, false);
        TextView city = cellView.findViewById(R.id.City);
        city.setText(toCells.get(i).getCity());

        System.out.println(toCells.get(i).getCity());
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





