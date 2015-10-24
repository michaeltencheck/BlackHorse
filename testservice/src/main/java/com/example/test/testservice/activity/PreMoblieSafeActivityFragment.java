package com.example.test.testservice.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.testservice.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreMoblieSafeActivityFragment extends Fragment {

    public PreMoblieSafeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_moblie_safe, container, false);
    }
}
