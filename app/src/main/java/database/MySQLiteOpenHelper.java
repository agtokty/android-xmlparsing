package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "yemeks";
	
    public static final String TABLE_NAME = "yemek";
    public static final String COLUMN_DATE = "gun";
    public static final String COLUMN_BIR = "bir";
    public static final String COLUMN_IKI = "iki";
    public static final String COLUMN_UC = "uc";
    public static final String COLUMN_DORT = "dort";
    public static final String COLUMN_CAL = "cal";
    
    public static final String SQL_CREATE_TABLE="create table "	+ TABLE_NAME + "( " + 
    		COLUMN_DATE + " text  , "+
			COLUMN_BIR	+ " text  , "+ 
			COLUMN_IKI  + " text  , "+
			COLUMN_UC   + " text  , "+
			COLUMN_DORT   + " text  , "+
			COLUMN_CAL + " text   )";
    
    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_TABLE);
		onCreate(db);
	}

}
