package com.example.profind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class d_0_0_4_9_client_appointments extends AppCompatActivity implements View.OnClickListener {

    Button back_btn;
    TabLayout tabLayout;
    TabItem tabitem1, tabitem2;
    ViewPager viewpager;
    d_0_0_4_9_client_appointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Profind);
        setContentView(R.layout.activity_d_0_0_4_9_client_appointments);
        getSupportActionBar().hide();

        back_btn = findViewById(R.id.client_appointments_page_back_btn_id);
        tabLayout = (TabLayout) findViewById(R.id.client_appointments_page_tab_layout_id);
        tabitem1 = (TabItem) findViewById(R.id.client_appointments_page_tab_item_1_id);
        tabitem2 = (TabItem) findViewById(R.id.client_appointments_page_tab_item_2_id);
        viewpager=(ViewPager) findViewById(R.id.client_appointments_page_viewpager_id);

        adapter=new d_0_0_4_9_client_appointmentAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}