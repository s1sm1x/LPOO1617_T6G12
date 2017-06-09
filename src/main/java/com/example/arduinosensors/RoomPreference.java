package com.example.arduinosensors;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ASUS on 23/05/2017.
 */

public class RoomPreference extends Preference{

    public RoomPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoomPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPreference(Context context) {
        super(context);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        setSummary(getPersistedString("1"));
    }

    @Override
    protected void onClick() {
        super.onClick();

        Log.d("CENAS", getPersistedString("1") + " " + getKey());
        persistString("1,2,3");
    }
}
