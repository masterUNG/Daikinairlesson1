package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{

    private Context context;
    public static final String nameDatabaseSTRING = "daikin.db";
    private static final int versionDatabaseINT = 1;
    private  static  final  String detailDatabaseString = "Create Table airTABLE (" +
            "id Integer Primary Key, " +
            "Name Text, " +
            "IPAddress Text, " +
            "MacAddress Text);";

    private static final String detailControllerSTRING = "Create Table controllerTABLE (" +
            "id Integer Primary Key, " +
            "IdAir Text, " +
            "AirData Text, " +
            "EnableT Text);";

    private static final String detailsetbackSTRING = "Create Table setbackTABLE (" +
            "id Integer Primary Key, " +
            "IdSAir Text, " +
            "SetbackData Text, " +
            "EnableS Text);";

    public MyOpenHelper(Context context) {
        super(context,nameDatabaseSTRING,null,versionDatabaseINT);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(detailDatabaseString);
        db.execSQL(detailControllerSTRING);
        db.execSQL(detailsetbackSTRING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
