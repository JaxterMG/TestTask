package com.example.testtask;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.example.testtask.TerminalFragments.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class TerminalsScreen extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    Database database;
    int tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab = getIntent().getExtras().getInt("tabNum");
        setContentView(R.layout.activity_terminalscreen);
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("From"));
        tabLayout.addTab(tabLayout.newTab().setText("To"));
        pager2.setCurrentItem(tab);
        tabLayout.selectTab(tabLayout.getTabAt(tab));
        database = Database.Instance;
        database.TerminalsRequest();
        System.out.println(database.getDatabaseName());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private class GetList extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... voids)
        {

            return  null;
        }
    }
}
