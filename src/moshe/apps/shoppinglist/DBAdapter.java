package moshe.apps.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBAdapter {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ISDONE = "isdone";
	public static final String KEY_TITLE = "title";
	public static final String KEY_PUBLISHER = "publisher";
	public static final String KEY_QUANTITY="quantity";
	
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "shopping_lists";
	private static final String DATABASE_TABLE = "list_items";
	private static final int DATABASE_VERSION = 6;
	private static final String DATABASE_CREATE =
	"create table list_items (_id integer primary key autoincrement, "
	+ "isdone boolean  not null DEFAULT false,"
	+" title text not null,"
	+" quantity integer not null DEFAULT 1"
	+ ");";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public DBAdapter(Context ctx)
	{
	   this.context = ctx;
	   DBHelper = new DatabaseHelper(context);
	}	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
		   super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			
		   try {
			   db.execSQL(DATABASE_CREATE);
			} 
		    catch (Exception e){
		    	e.printStackTrace();
			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)		
		{/*
			Log.w(TAG, "Upgrading database from version " + oldVersion
			+ " to "
			+ newVersion + ", which will destroy all old data");*/
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	
	
	public DBAdapter open() throws SQLException
	{
	     db = DBHelper.getWritableDatabase();
	    
	   return this;
	}
	//---closes the database---
	public void close()
	{
	     DBHelper.close();
	}
	//---insert a item into the database---
	public long insertTitle(String isdone, String title, String publisher)
	{
		Long res=(long) 0;
		try{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ISDONE, false);
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_QUANTITY, 1);
		res= db.insert(DATABASE_TABLE, null, initialValues);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return res;
	}
	//---deletes a particular title---
	public boolean deleteItem(long rowId)
	{
		try {
			return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	//---retrieves all the titles---
	public Cursor getAllTitles()
	{
		Cursor c=null;
		try {
			c =db.query(DATABASE_TABLE, new String[] {
			KEY_ROWID,
			KEY_ISDONE,
			KEY_TITLE,
			KEY_QUANTITY},
			null,
			null,
			null,
			null,
			null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return c;
	}
	//---retrieves a particular title---
	public Cursor getTitle(long rowId) throws SQLException
	{
		Cursor mCursor =null;
		try {
			mCursor =
			db.query(true, DATABASE_TABLE, new String[] {
			KEY_ROWID,
			KEY_ISDONE,
			KEY_TITLE,
			KEY_QUANTITY
			},
			KEY_ROWID + "=" + rowId,
			null,
			null,
			null,
			null,
			null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if (mCursor != null) {
			try{
		       mCursor.moveToFirst();
			}catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return mCursor;
	}
	//---updates a title---
	public boolean updateTitle(long rowId, boolean isdone,
	String title, int quantity)
	{
		try {
			ContentValues args = new ContentValues();
			args.put(KEY_ISDONE, isdone);
			args.put(KEY_TITLE, title);
			args.put(KEY_QUANTITY, quantity);
			return db.update(DATABASE_TABLE, args,
			KEY_ROWID + "=" + rowId, null) > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	}	
	public boolean updateIsDone( long rowId, boolean isdone
	)
	{
		try {
			ContentValues args = new ContentValues();
			args.put(KEY_ISDONE, isdone);

			return db.update(DATABASE_TABLE, args,
			KEY_ROWID + "=" + rowId, null) > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	}	
}
