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

    public long addSetback(String IdSAirString, String EnableSString, String SetbackDataString){
        ContentValues contentValues = new ContentValues();
        contentValues.put("IdSAir",IdSAirString);
        contentValues.put("SetbackData",SetbackDataString);
        contentValues.put("EnableS",EnableSString);
        return sqLiteDatabase.insert("setbackTABLE", null, contentValues);

    }

    public long addController(String IdAirString, String airDataString
            , String EnableTString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("IdAir", IdAirString);
        contentValues.put("AirData", airDataString);
        contentValues.put("EnableT", EnableTString);

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
