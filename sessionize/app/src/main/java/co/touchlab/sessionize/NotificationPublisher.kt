package co.touchlab.sessionize

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.Notification
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.touchlab.sessionize.api.notificationFeedbackId
import co.touchlab.sessionize.api.notificationReminderId
import co.touchlab.sessionize.platform.AndroidAppContext
import co.touchlab.sessionize.platform.NotificationsModel

class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val notificationActionId = intent.getIntExtra(NOTIFICATION_ACTION_ID, 0)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(notificationActionId == 0){
            Log.i(TAG, "---OnReceive called, creating   ${if(notificationId == notificationReminderId) "reminder" else "feedback"} notification")
            with(NotificationManagerCompat.from(AndroidAppContext.app)) {
                this.notify(notificationId, notification)
            }

            if(notificationId == notificationReminderId) {
                NotificationsModel.recreateReminderNotifications()
            }
            if(notificationId == notificationFeedbackId){
                NotificationsModel.recreateFeedbackNotifications()
            }
        }
        else {
            Log.i(TAG, "---OnReceive called, dismissing ${if(notificationActionId == notificationReminderId) "reminder" else "feedback"} notification")
            val pendingIntent = NotificationsApiImpl.createPendingIntent(notificationActionId)
            val alarmManager = AndroidAppContext.app.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            notificationManager.cancel(notificationActionId)
        }



    }

    companion object {
        val TAG:String = NotificationPublisher::class.java.simpleName

        var NOTIFICATION_ID = "notification_id"
        var NOTIFICATION_ACTION_ID = "notification_action_id"
        var NOTIFICATION = "notification"

        const val NOTIFICATION_ACTION_CREATE = "notificationCreate"
        const val NOTIFICATION_ACTION_DISMISS = "notificationDismiss"
    }
}