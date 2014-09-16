package intranet.fw.com.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustubh on 16/9/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

  // All Static variables
  // Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "Intranet";

  // table name
  private static final String TABLE_USER_DETAIL = "userDetails";

  // Discussion_Data Table Columns names
  private static final String KEY_ID = "uid";
  private static final String KEY_OAUTHID = "oauthid";
  private static final String KEY_name = "name";
  private static final String KEY_gender = "gender";
  private static final String KEY_email = "email";
  private static final String KEY_picture = "picture";

  public DatabaseHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String CREATE_USER_DETAIL = "CREATE TABLE " + TABLE_USER_DETAIL + "("
        + KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_OAUTHID + " TEXT,"+KEY_name+ " TEXT,"+KEY_gender+ " TEXT,"+KEY_email+ " TEXT ,"+KEY_picture+ " TEXT)";
    db.execSQL(CREATE_USER_DETAIL);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i2) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAIL);

    // Create tables again
    onCreate(db);
  }

  public void addUserDetail(User user){
    SQLiteDatabase db = this.getReadableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_OAUTHID,user.getOauth());
    values.put(KEY_name,user.getName());
    values.put(KEY_gender,user.getGender());
    values.put(KEY_email,user.getEmail());
    values.put(KEY_picture,user.getPicture());
    // Inserting Row
    db.insert(TABLE_USER_DETAIL, null, values);

    Log.i("Database","inserted User data");
  }

  public List<User> getUserDetail() {
    List<User> userList = new ArrayList<User>();
    String selectQuery = "SELECT  * FROM " + TABLE_USER_DETAIL;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);
    User user = new User();
    // looping through all rows and adding to list
    if (c != null) {
      try {
        while (c.moveToNext()) {
          user.setName(c.getString(c.getColumnIndex(KEY_name)));
          user.setGender(c.getString(c.getColumnIndex(KEY_gender)));
          user.setEmail(c.getString(c.getColumnIndex(KEY_email)));
          user.setPicture(c.getString(c.getColumnIndex(KEY_picture)));
        }
        userList.add(user);
      } catch (Exception e) {
        System.out.println("Error in getUserDetail in DB :" + e);
      } finally {
        c.close();
      }
    }
    return userList;
  }
}
