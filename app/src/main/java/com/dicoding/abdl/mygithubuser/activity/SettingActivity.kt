package com.dicoding.abdl.mygithubuser.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.abdl.mygithubuser.MyPreferenceFragment
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.TimePickerFragment
import com.dicoding.abdl.mygithubuser.adapter.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.text.SimpleDateFormat
import java.util.*

class SettingActivity : AppCompatActivity(), View.OnClickListener,
    TimePickerFragment.DialogTimeListener {

    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val TIME_PICKER_TAG = "TimePickerTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment())
            .commit()
        setContentView(R.layout.activity_setting)

        btn_time.setOnClickListener(this)
        btn_on_reminder.setOnClickListener(this)
        btn_off_reminder.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()

        val titleBar = "Setting Reminder"

        supportActionBar?.title = titleBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_TAG)
            }
            R.id.btn_on_reminder -> {
                val alarmTime = tv_time.text.toString()
                val messageReminder =
                    "Ayo.. buka lagi aplikasi Github untuk mengenang perjuanganmu:)"
                alarmReceiver.setAlarm(this, alarmTime, messageReminder)
            }
            R.id.btn_off_reminder -> {
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            TIME_PICKER_TAG -> tv_time.text = dateFormat.format(calendar.time)
            else -> {

            }
        }
    }
}