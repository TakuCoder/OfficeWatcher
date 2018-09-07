package officewatcher.thiyagu.com.officewatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "office.db";
    private static final String TABLE_NAME = "officetable";

    private static final String ID = "id";
    private static final String COLUMN_IN = "column_in";
    private static final String COLUMN_OUT = "column_out";
    private static final String COLUMN_DATE = "column_date";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + DatabaseHelper.TABLE_NAME + " (" +
                    DatabaseHelper.ID + " INTEGER PRIMARY KEY," +
                    DatabaseHelper.COLUMN_IN + " TEXT," +
                    DatabaseHelper.COLUMN_OUT + " TEXT," +
                    DatabaseHelper.COLUMN_DATE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



    public void addTime(DataPojoClass dataPojoClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IN, dataPojoClass.getInTime());
        values.put(COLUMN_OUT, dataPojoClass.getOutTime());
        values.put(COLUMN_DATE, dataPojoClass.getDate());
        db.insert(TABLE_NAME, null, values);
        db.close();
        PrintAllParamHeader();

    }

    public ArrayList<String> PrintAllParamHeader() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(COLUMN_IN))+"@@"+res.getString(res.getColumnIndex(COLUMN_OUT))+"@@"+res.getString(res.getColumnIndex(COLUMN_DATE)));
            res.moveToNext();
        }
        db.close();
        Log.v("thisisallcontent", array_list.toString());
        return array_list;
    }

}
