package com.iranmobiledev.moodino.service

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.iranmobiledev.moodino.NOTIFICATION_ID
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.ui.MainActivity

class AlarmReciver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        notification(context!!)

    }

    fun notification(context : Context){
        val intent = Intent(context , MainActivity::class.java)
        val content = PendingIntent.getActivity(context , 1 , intent , PendingIntent.FLAG_MUTABLE)

        val notifiManager = context.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager

        val notifi = NotificationCompat.Builder(context, NOTIFICATION_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("امروز چطوری ؟")
            .setContentIntent(content)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500,500,500,500))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
            .build()

        val notifiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context , notifiSound)
        r.play()

        notifiManager.notify(101 , notifi)
    }

}