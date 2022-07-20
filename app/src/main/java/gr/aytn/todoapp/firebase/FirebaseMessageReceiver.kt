package gr.aytn.todoapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gr.aytn.todoapp.R
import gr.aytn.todoapp.ui.MainActivity


class FirebaseMessageReceiver: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        /*if(remoteMessage.getData().size()>0){
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }*/

        if(message.notification != null){
            showNotification(
                message.notification!!.title!!,
                message.notification!!.body!!
            )
        }
    }
//    fun getCustomDesign(title: String, message: String): RemoteViews{
//        val remoteViews: RemoteViews = RemoteViews(applicationContext.packageName, R.layout.notification)
//        remoteViews.setTextViewText(R.id.title, title)
//        remoteViews.setTextViewText(R.id.message, message)
//        remoteViews.setImageViewResource(R.id.icon, R.drawable.tasks)
//        return remoteViews
//    }
    fun showNotification(title: String,message: String){
        val intent = Intent(this, MainActivity::class.java)
        val channel_id = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channel_id)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.tasks)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

//        if (Build.VERSION.SDK_INT
//            >= Build.VERSION_CODES.JELLY_BEAN) {
//            builder = builder.setContent(
//                getCustomDesign(title, message))
//        }else {
//            builder = builder.setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.tasks)
//        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id, "to_do_app",
                NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }
    override fun onNewToken(token: String) {
        Log.d("firebase", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server
    }
}