package com.example.profind;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class d_0_0_4_5_previous_appointmentAdapter extends FragmentPagerAdapter {
    int tabcount;

    public d_0_0_4_5_previous_appointmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new d_0_0_4_5_previous_appointment_fragment_1();
            case 1:return new d_0_0_4_5_previous_appointment_fragment_2();

            default:return null;
        }
    }
    @Override
    public int getCount() {
        return tabcount;
    }
}
