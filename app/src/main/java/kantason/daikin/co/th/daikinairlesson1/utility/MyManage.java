package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyManage {

    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }   // Constructor

    public long addController(String IdAirString, String airDataString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("IdAir", IdAirString);
        contentValues.put("AirData", airDataString);
        return sqLiteDatabase.insert("controllerTABLE", null, contentValues);

    }


    public  long addValue(String nameString, String ipAddressString, String macAddressString) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", nameString);
        contentValues.put("IPAddress", ipAddressString);
        contentValues.put("MacAddress", macAddressString);
        return sqLiteDatabase.insert("airTABLE",null,contentValues);
    }
}
