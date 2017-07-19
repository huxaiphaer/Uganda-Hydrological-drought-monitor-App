package datastructs.example.huzy_kamz.ugandagraphical;/** * Created by Huzy_Kamz on 4/4/2017. */import android.content.Context;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteOpenHelper;import java.util.ArrayList;import java.util.List;/** * Created by DevExpress on 4/14/2016. */public class DataProvider extends SQLiteOpenHelper {    // If you change the database schema, you must increment the database version.      public static final int DATABASE_VERSION = 1;    public static final String DATABASE_NAME = "police_db.db";    String SQL_CREATE_SETTINGS = "CREATE TABLE usersettings(_ID INT PRIMARY KEY, district TEXT, district_id INT)";    String SQL_CREATE_SPINNER_ITEMS = "CREATE TABLE districts(_ID INT PRIMARY KEY , DistrictName TEXT UNIQUE , ID INT UNIQUE)";    String SQL_DELETE_SETTINGS = "DROP TABLE IF EXISTS usersettings";    String SQL_DELETE_DISTRICTS = "DROP TABLE IF EXISTS districts";    String SQL_DELETE_DISTRICT_SETTINGS ="DROP TABLE IF EXISTS currentdistrict";    String SQL_DISTRICT_SETTINGS = "CREATE TABLE currentdistrict(_ID PRIMARY KEY , district TEXT)";    String SQL_SOS_SETTINGS =    "CREATE TABLE sos(_ID PRIMARY KEY , myphone TEXT ,emergencyone TEXT," +            "emergencytwo TEXT , emergencythree TEXT )";    String SQL_NEWS_SETTINGS =    "CREATE TABLE News(_ID PRIMARY KEY , headlines TEXT ,details TEXT," +            "photo TEXT , date TEXT )";    String SQL_DISTRICT_INITIAL_VALUE = "INSERT INTO  currentdistrict (district) VALUES ('BUGIRI')";    String SQL_OFFENCES = "CREATE TABLE Offence(_ID PRIMARY KEY , Offence TEXT UNIQUE ,id TEXT UNIQUE)";    public DataProvider(Context context) {        super(context, DATABASE_NAME, null, DATABASE_VERSION);    }    public void onCreate(SQLiteDatabase db) {        db.execSQL(SQL_CREATE_SETTINGS);        //db.execSQL(SQL_DEFAULT_SETTINGS);        db.execSQL(SQL_CREATE_SPINNER_ITEMS);        db.execSQL(SQL_DISTRICT_SETTINGS);        db.execSQL(SQL_DISTRICT_INITIAL_VALUE);        db.execSQL(SQL_SOS_SETTINGS);        db.execSQL(SQL_NEWS_SETTINGS);        db.execSQL(SQL_OFFENCES);    }    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {        db.execSQL(SQL_DELETE_SETTINGS);        db.execSQL(SQL_DELETE_DISTRICTS);        db.execSQL(SQL_DELETE_DISTRICT_SETTINGS);       // db.execSQL(OnInsertDistrict());        onCreate(db);    }    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {        onUpgrade(db, oldVersion, newVersion);    }    public String UpdateDistrict(String district) {        try {            SQLiteDatabase db = this.getWritableDatabase();            db.execSQL("UPDATE currentdistrict SET district='" + district + "';");            return "Updates Saved";        } catch (Exception ex) {            return "Error! " + ex.getMessage();        }    }    public String UpdateNews() {        try {            SQLiteDatabase db = this.getWritableDatabase();            db.execSQL("truncate  News;");            return "Updates Saved";        } catch (Exception ex) {            return "Error! " + ex.getMessage();        }    }    public void InsertOffence(String Offence , String id){        try {            SQLiteDatabase db = this.getWritableDatabase();            String query = "INSERT INTO Offence (Offence,id) " +                    "VALUES('"+Offence+"', '"+id+"');";            db.execSQL(query);            //  return "insertion achieved";        } catch (Exception ex) {            // return "Error! " + ex.getMessage();        }    }    public void InsertNews(String headlines , String details, String photo, String date){        try {            SQLiteDatabase db = this.getWritableDatabase();            String query = "INSERT INTO News (headlines,details,photo,date) " +                    "VALUES('"+headlines+"', '"+details+"','"+photo+"','"+date+"');";            db.execSQL(query);            //  return "insertion achieved";        } catch (Exception ex) {            // return "Error! " + ex.getMessage();        }    }    public  void InsertSOS(String myphone , String emergone, String emergtwo, String emergthree){        try {            SQLiteDatabase db = this.getWritableDatabase();            String query = "INSERT INTO sos (myphone,emergencyone,emergencytwo,emergencythree) " +                    "VALUES('"+myphone+"', '"+emergone+"','"+emergtwo+"','"+emergthree+"');";            db.execSQL(query);          //  return "insertion achieved";        } catch (Exception ex) {           // return "Error! " + ex.getMessage();        }    }    public String OnInsertDistrict(){        try {            SQLiteDatabase db = this.getWritableDatabase();            String query = "INSERT INTO currentdistrict (district) VALUES('"+"Arua"+"');";            db.execSQL(query);            return "insertion achieved";        } catch (Exception ex) {            return "Error! " + ex.getMessage();        }    }    public String GetEmergThree(){        String selectQuery = "SELECT emergencythree FROM sos";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("emergencythree")).toUpperCase();        }        else            return "0";    }    public String GetEmergTwo(){        String selectQuery = "SELECT emergencytwo FROM sos";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("emergencytwo")).toUpperCase();        }        else            return "0";    }    public String GetEmergOne(){        String selectQuery = "SELECT emergencyone FROM sos";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("emergencyone")).toUpperCase();        }        else            return "0";    }    public String GetMyPhone(){        String selectQuery = "SELECT myphone FROM sos";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("myphone")).toUpperCase();        }        else            return "0";    }    public String GetDistrictName(){        String selectQuery = "SELECT district FROM currentdistrict";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("district")).toUpperCase();        }        else            return "Kampala";    }    public int GetDistrictId(){        String selectQuery = "SELECT ID  FROM districts";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            String id =c.getString(c.getColumnIndex("ID")).toUpperCase();            int id_ = Integer.parseInt(id);            return   id_;        }        else            return 1;    }    public String InsertDistricts(String district, int id){        try {            SQLiteDatabase db = this.getWritableDatabase();            String query = "INSERT INTO districts (ID,DistrictName) VALUES('"+id+"', '"+district+"');";            db.execSQL(query);            return "insertion achieved";        } catch (Exception ex) {            return "Error! " + ex.getMessage();        }    }    public List GetOffenceList(){        List<String> list = new ArrayList<String>();        SQLiteDatabase db = this.getReadableDatabase();        String query = "SELECT Offence, id from Offence";        Cursor c = db.rawQuery(query, null);        if (c.moveToFirst()) {            do {                list.add(c.getString(c.getColumnIndex("Offence")));                //you get all index in cursor. you can also use pojo class to retrieve all data            } while (c.moveToNext());        }        c.close();        return  list ;    }    public List GetOffenceListId(){        List<String> list = new ArrayList<String>();        SQLiteDatabase db = this.getReadableDatabase();        String query = "SELECT id from Offence";        Cursor c = db.rawQuery(query, null);        if (c.moveToFirst()) {            do {                list.add(c.getString(c.getColumnIndex("id")));                //you get all index in cursor. you can also use pojo class to retrieve all data            } while (c.moveToNext());        }        c.close();        return  list ;    }    public List GetListOfDistricts(){        List<String> list = new ArrayList<String>();        SQLiteDatabase db = this.getReadableDatabase();        String query = "SELECT DistrictName from districts order by DistrictName  asc";        Cursor c = db.rawQuery(query, null);        if (c.moveToFirst()) {            do {                list.add(c.getString(c.getColumnIndex("DistrictName")));                //you get all index in cursor. you can also use pojo class to retrieve all data            } while (c.moveToNext());        }        c.close();        return  list ;    }    public String GetCurrentDistrict()    {        String selectQuery = "SELECT district FROM usersettings";        SQLiteDatabase db = this.getReadableDatabase();        Cursor c = db.rawQuery(selectQuery, null);        if (c != null && c.moveToFirst()) {            return  c.getString(c.getColumnIndex("district")).toUpperCase();        }        else            return "Kampala";    }}