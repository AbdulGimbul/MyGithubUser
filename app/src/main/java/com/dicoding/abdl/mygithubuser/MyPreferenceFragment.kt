package com.dicoding.abdl.mygithubuser

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.abdl.mygithubuser.activity.SettingActivity
import com.dicoding.abdl.mygithubuser.adapter.AlarmReceiver

class MyPreferenceFragment: PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var ALARM: String
    private lateinit var alarmReceiverPreference: AlarmReceiver

    private lateinit var isAalarmOnPreference: SwitchPreference

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)

        ALARM = resources.getString(R.string.key_reminder)
        isAalarmOnPreference = findPreference<SwitchPreference>(ALARM) as SwitchPreference

        val sh = preferenceManager.sharedPreferences
        isAalarmOnPreference.isChecked = sh.getBoolean(ALARM, false)

        alarmReceiverPreference = AlarmReceiver()

        if (isAalarmOnPreference.isChecked == true){
            val alarmDaily = "10:46"
            val messageDaily = "Ayo.. buka lagi aplikasi Github untuk mengenang perjuanganmu:)"
            alarmReceiverPreference.setAlarm(context as SettingActivity, alarmDaily, messageDaily)
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == ALARM){
            isAalarmOnPreference.isChecked = sharedPreferences.getBoolean(ALARM, false)
        }

    }


}