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

    public MyOpenHelper(Context context) {
        super(context,nameDatabaseSTRING,null,versionDatabaseINT);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(detailDatabaseString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
