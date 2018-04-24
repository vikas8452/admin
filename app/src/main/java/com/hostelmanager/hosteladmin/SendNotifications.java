package com.hostelmanager.hosteladmin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SendNotifications extends AppCompatActivity {

    TextView tv1,tv2;
    Button sendn;
    String topic,desc;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifications);

        tv1 = findViewById(R.id.topic);
        tv2 = findViewById(R.id.description);
        sendn = findViewById(R.id.sendnotification);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        sendn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = tv1.getText().toString();
                desc = tv2.getText().toString();
                if(topic.equals(""))
                    Toast.makeText(SendNotifications.this,"Give topic",Toast.LENGTH_LONG).show();
                else if(desc.equals(""))
                    Toast.makeText(SendNotifications.this,"Provide description",Toast.LENGTH_LONG).show();
                try {
                    sendNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SendNotifications.this,e+"",Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent inte = new Intent(SendNotifications.this,MainActivity.class);
        inte.setAction("com.hostelmanager.SendBroadcast");
        inte.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(inte);
    }

    private void sendNotification(){

        Intent in =new Intent(SendNotifications.this,MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(SendNotifications.this,0 ,in,PendingIntent.FLAG_ONE_SHOT);

        String chid = getString(R.string.default_notification_channel_id);
        Uri d = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noti = new NotificationCompat.Builder(SendNotifications.this,chid)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setContentTitle(topic)
                .setContentText(desc)
                .setAutoCancel(true)
                .setSound(d)
                .setContentIntent(pendingIntent);

        NotificationManager n = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chid,"Channel human Readable title",NotificationManager.IMPORTANCE_DEFAULT);
            n.createNotificationChannel(channel);
        }
        n.notify(0,noti.build());
    }

    /*private void sendNotification() throws IOException {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
                try {
                    String jsonResponse;

                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", "OTNiODMyMDQtZGMyNS00YzdjLWI1YjktY2E1OTU1YzFiMGY0");
                    con.setRequestMethod("POST");

                    String email = "7388796555";

                    String strJsonBody = "{"
                            +   "\"app_id\": \"d40bc8a5-d075-4a70-ae63-b7e06112270a\","
                            +   "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"*\", \"value\": \"" + email + "\"}],"
                            +   "\"data\": {\"foo\": \"bar\"},"
                            +   "\"contents\": {\"en\": \"English Message\"}"
                            + "}";


                    System.out.println("strJsonBody:\n" + strJsonBody);

                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                    con.setFixedLengthStreamingMode(sendBytes.length);

                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(sendBytes);

                    int httpResponse = con.getResponseCode();
                    System.out.println("httpResponse: " + httpResponse);

                    if (  httpResponse >= HttpURLConnection.HTTP_OK
                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    else {
                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    System.out.println("jsonResponse:\n" + jsonResponse);

                } catch(Throwable t) {
                    t.printStackTrace();
                }


            }
        });
    }*/
}

        // The topic name can be optionally prefixed with "/topics/".
       /* String topic = "highScores";

// See documentation on defining a message payload.
        NotificationCompat.MessagingStyle.Message message = NotificationCompat.MessagingStyle.Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setTopic(topic)
                .build();

// Send a message to the devices subscribed to the provided topic.
        String response = FirebaseMessaging.getInstance().send(message).get();
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);*/

