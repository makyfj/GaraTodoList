package com.example.finalproject;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;


public class UserPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        super.addPreferencesFromResource(R.xml.preferences);

    }
}
