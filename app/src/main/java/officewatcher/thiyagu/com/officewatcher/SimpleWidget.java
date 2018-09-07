package officewatcher.thiyagu.com.officewatcher;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SimpleWidget extends AppWidgetProvider {
    private static final String MyOnClick1 = "myOnClickTag1";
    private static final String MyOnClick2 = "myOnClickTag2";

    private static final String MyOnClick3 = "myOnClickTag3";
    SharedPreferences.Editor editor;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);
        // Construct an Intent object includes web adresss.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://erenutku.com"));
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);
        views.setOnClickPendingIntent(R.id.tvWidget1, pendingIntent);
        // Instruct the widget manager to update the widget

        views.setOnClickPendingIntent(R.id.tvWidget, getPendingSelfIntent(context, MyOnClick1));
        views.setOnClickPendingIntent(R.id.tvWidget1, getPendingSelfIntent(context, MyOnClick2));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//add this line
        if (MyOnClick1.equals(intent.getAction())) {
            // your onClick action is here
            Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

            editor = context.getSharedPreferences("Office", MODE_PRIVATE).edit();
            editor.putString("intime", format.format(calendar.getTime()));
            editor.apply();
            editor.commit();




        }
        else if (MyOnClick2.equals(intent.getAction())) {
            // your onClick action is here

            SharedPreferences prefs;
            prefs = context.getSharedPreferences("Office", Context.MODE_PRIVATE);
            Toast.makeText(context, "Button2", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button2");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String intime = prefs.getString("intime","");
            String outime = format.format(calendar.getTime());
            Log.v("sadasdasdasd",intime);
            Log.v("sadasdasdasd",outime);
            Date date = new Date(); // your date

            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            month=month+1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            Log.v("sadasdasdasd",""+day+month+year);
            DataPojoClass dataPojoClass = new DataPojoClass(intime,outime,""+day+month+year);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.addTime(dataPojoClass);
        }
    }




    public void updateInDatabase(String intime,String outime,String day)
    {

    }
}
