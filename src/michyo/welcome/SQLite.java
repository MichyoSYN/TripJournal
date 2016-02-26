package michyo.welcome;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {
	public SQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	private static final String DB_NAME = "Test";
	private static final int DB_VERSION = 1;
	
	public SQLite(Context context) 
	{ 
		super(context, DB_NAME,null, DB_VERSION);
    } 

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS User (id integer primary key " +
				", name nvarchar(9), password varchar(16), registDate varchar(10), " +
				"locate nvarchar(10), feeling text, icon text)" ); 
		db.execSQL("CREATE TABLE IF NOT EXISTS Journal (jid integer primary key " +
				", date varchar(10), title nvarchar(16), location nvarchar(10), " +
				"content text, picture text, record text, video text, id integer references User(id))");
		db.execSQL("CREATE TABLE IF NOT EXISTS Friendship (fid integer primary key " +
				", one integer, another integer)");		
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub 
		db.execSQL("DROP TABLE IF EXISTS User");
		db.execSQL("DROP TABLE IF EXISTS Journal");
		db.execSQL("DROP TABLE IF EXISTS Friendship");
		onCreate(db);
	}

    public void insertUser(User user){
    	String name = user.getUN();
    	String password = user.getPWD();
    	String registDate = user.getRegistDate();
    	String locate = user.getLocate();
    	String feeling = user.getFeeling();
    	String icon = user.getIcon();
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("insert into User(name, password, registDate, locate, feeling, icon)" +
    			" values('" + name + "', '" + password + "', '" + registDate + "', '" 
    			+ locate + "', '" + feeling + "', '" + icon + "')");    	
    	db.close();
    	return;
    }
    
    public void insertJournal(Journal jnl){
    	String date = jnl.getDate();
    	String title = jnl.getTitle();
    	String location = jnl.getLocation();
    	String content = jnl.getContent();
    	String picture = jnl.getPicture();
    	String record = jnl.getRecord();
    	String video = jnl.getVideo();
    	int id = jnl.getId();
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("insert into Journal(date, title, location, content, picture, record, video, id) values('" 
    	+ date + "', '" + title + "', '" + location + "', '" + content + "', '" + picture + "', '"  
    			+ record + "', '" + video + "', '" + Integer.toString(id) +"')");
    	
    	db.close();
    	return;
    }
    
    public void createFriendship(User user1, User user2){
    	int one = user1.getId();
    	int another = user2.getId();
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("insert into Friendship(one, another) values(" + one + ", " + another + ")");    
    	db.execSQL("insert into Friendship(one, another) values(" + another + ", " + one + ")");  
    	db.close();
    	return;
    }
    
    public void createFriendship(int one, int another){    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("insert into Friendship(one, another) values(" + one + ", " + another + ")");    	
    	db.close();
    	return;
    }
    
    public int selectUser(User user){
    	String name = user.getUN();
    	String password = user.getPWD();
    	int id = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from User where name = ? and password = ?",
    			new String[]{name, password});
    	while(cr.moveToNext()){
    		id = cr.getInt(0);
    	}
    	
    	db.close();
    	return id;
    }
    
    public User selectUserForInfo(int findId){
    	User result = new User();
    	String name;
    	String password;
    	String registDate;
    	String feeling;
    	String locate;
    	String icon;
    	int id = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from User where id = ? ",
    			new String[]{Integer.toString(findId)});
    	while(cr.moveToNext()){
    		id = cr.getInt(0);
    		name = cr.getString(1);
    		password = cr.getString(2);
    		registDate = cr.getString(3);
    		locate = cr.getString(4);
    		feeling = cr.getString(5); 
    		icon = cr.getString(6);
    		result.setId(id);
    		result.setUN(name);
    		result.setPWD(password);
    		result.setRegistDate(registDate);
    		result.setLocate(locate);
    		result.setFeeling(feeling);   
    		result.setIcon(icon);
    	}    	
    	db.close();
    	return result;
    }
    
    public User selectUserByName(String findName){
    	User result = new User();
    	String name;
    	String password;
    	String registDate;
    	String feeling;
    	String locate;
    	String icon;
    	int id = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from User where name = ? ",
    			new String[]{findName.trim()});
    	while(cr.moveToNext()){
    		id = cr.getInt(0);
    		name = cr.getString(1);
    		password = cr.getString(2);
    		registDate = cr.getString(3);
    		locate = cr.getString(4);
    		feeling = cr.getString(5); 
    		icon = cr.getString(6);
    		result.setId(id);
    		result.setUN(name);
    		result.setPWD(password);
    		result.setRegistDate(registDate);
    		result.setLocate(locate);
    		result.setFeeling(feeling);   
    		result.setIcon(icon);
    	}    	
    	db.close();
    	return result;
    }
    
    public Vector<Integer> findFriendId(User user){
    	Vector<Integer> result = new Vector<Integer>();
    	int id = user.getId();
    	int friend = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Friendship where one = ?",
    			new String[]{Integer.toString(id)});
    	while(cr.moveToNext()){
    		friend = cr.getInt(2);
    		result.add(friend);
    	}
    	
    	db.close();
    	return result;
    }
    
    public Vector<Integer> findFriendId(int id){
    	Vector<Integer> result = new Vector<Integer>();
    	int friend = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Friendship where one = ?",
    			new String[]{Integer.toString(id)});
    	while(cr.moveToNext()){
    		friend = cr.getInt(2);
    		result.add(friend);
    	}
    	
    	db.close();
    	return result;
    }
    
    public Vector<User> findFriendInfo(User user){
    	Vector<Integer> resultId = findFriendId(user);
    	Vector<User> result = new Vector<User>();
    	
    	for(int i = 0; i < resultId.size(); i++) {
    		User temp = selectUserForInfo(resultId.get(i));
    		result.add(temp);
    	}
    	return result;
    }
	
    public Journal recentJournal(User user){
    	Journal result = new Journal();
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id = user.getId();
    	int jid = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where id = ? ",
    			new String[]{Integer.toString(id)});
    	while(cr.moveToLast()){
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		result.setJid(jid);
    		result.setDate(date);
    		result.setTitle(title);
    		result.setLocation(location);
    		result.setContent(content);
    		result.setPicture(picture);
    		result.setRecord(record);
    		result.setVideo(video);
    		result.setId(id);
    	}
    	
    	db.close();
    	return result;
    }
    
	public Journal selectJournal(int findId){
    	Journal result = new Journal();
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id;
    	int jid = -1;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where jid = ? ",
    			new String[]{Integer.toString(findId)});
    	while(cr.moveToNext()){
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		result.setJid(jid);
    		result.setDate(date);
    		result.setTitle(title);
    		result.setLocation(location);
    		result.setContent(content);
    		result.setPicture(picture);
    		result.setRecord(record);
    		result.setVideo(video);
    		result.setId(id);
    	}
    	
    	db.close();
    	return result;
    }
	
	public Vector<Journal> selectJournalByAuthor(User user){
		Vector<Journal> result = new Vector<Journal>();
    	int jid;
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id = user.getId();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where id = ? ",
    			new String[]{Integer.toString(id)});
        while(cr.moveToNext()) {
        	Journal temp = new Journal();
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		temp.setJid(jid);
    		temp.setDate(date);
    		temp.setTitle(title);
    		temp.setLocation(location);
    		temp.setContent(content);
    		temp.setPicture(picture);
    		temp.setRecord(record);
    		temp.setVideo(video);
    		temp.setId(id);
    		//插入Journal类
    		result.add(temp);
    	}
    	
    	db.close();
		return result;
	}
	
	public Vector<Journal> selectJournalByDate(User user, String findDate){
		Vector<Journal> result = new Vector<Journal>();
		Journal temp = new Journal();
    	int jid;
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id = user.getId();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where id = ? and date = ?",
    			new String[]{Integer.toString(id), findDate});
    	while(cr.moveToNext()){
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		temp.setJid(jid);
    		temp.setDate(date);
    		temp.setTitle(title);
    		temp.setLocation(location);
    		temp.setContent(content);
    		temp.setPicture(picture);
    		temp.setRecord(record);
    		temp.setVideo(video);
    		temp.setId(id);
    		//插入Journal类
    		result.add(temp);
    	}
    	
    	db.close();
		return result;
	}
	
	public Vector<Journal> selectJournalByPlace(User user, String findPlace){
		Vector<Journal> result = new Vector<Journal>();
		Journal temp = new Journal();
    	int jid;
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id = user.getId();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where id = ? and location = ?",
    			new String[]{Integer.toString(id), findPlace});
    	while(cr.moveToNext()){
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		temp.setJid(jid);
    		temp.setDate(date);
    		temp.setTitle(title);
    		temp.setLocation(location);
    		temp.setContent(content);
    		temp.setPicture(picture);
    		temp.setRecord(record);
    		temp.setVideo(video);
    		temp.setId(id);
    		//插入Journal类
    		result.add(temp);
    	}
    	
    	db.close();
		return result;
	}

	public Vector<Journal> selectJournalByDateAndPlace(User user, String findPlace, String findDate){
		Vector<Journal> result = new Vector<Journal>();
		Journal temp = new Journal();
    	int jid;
    	String date;
    	String title;
    	String location;
    	String content;
		String picture;
		String record;
		String video;
    	int id = user.getId();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cr = db.rawQuery("select * from Journal where id = ? and date = ? and location = ?",
    			new String[]{Integer.toString(id), findDate, findPlace});
    	while(cr.moveToNext()){
    		jid = cr.getInt(0);
    		date = cr.getString(1);
    		title = cr.getString(2);
    		location = cr.getString(3);
    		content = cr.getString(4);
    		picture = cr.getString(5);
    		record = cr.getString(6);
    		video = cr.getString(7);
    		id = cr.getInt(8);
    		temp.setJid(jid);
    		temp.setDate(date);
    		temp.setTitle(title);
    		temp.setLocation(location);
    		temp.setContent(content);
    		temp.setPicture(picture);
    		temp.setRecord(record);
    		temp.setVideo(video);
    		temp.setId(id);
    		//插入Journal类
    		result.add(temp);
    	}
    	
    	db.close();
		return result;
	}	
    
    public void deleteUser(User user){
    	SQLiteDatabase db = this.getWritableDatabase();
    	int id = user.getId();
    	String where = "id = ?";
    	String[] whereValue = {Integer.toString(id)};
    	db.delete("User", where, whereValue);
    	db.delete("Journal", where, whereValue);
    	db.close();
    	return;
    }
    
    public void deleteUser(int id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String where = "id = ?";
    	String[] whereValue = {Integer.toString(id)};
    	db.delete("User", where, whereValue);
    	db.delete("Journal", where, whereValue);
    	db.close();
    	return;
    }   
    
    public void cancelFriendship(User user1, User user2){
    	SQLiteDatabase db = this.getWritableDatabase();
    	int one = user1.getId();
    	int another = user2.getId();
    	db.execSQL("delete from Friendship where one = " + one + " and another =" + another);
    	db.execSQL("delete from Friendship where one = " + another + " and another =" + one);
    	db.close();
    	return;
    }
    
    public void cancelFriendship(int one, int another){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from Friendship where one = " + one + " and another =" + another);
    	db.execSQL("delete from Friendship where one = " + another + " and another =" + one);
    	db.close();
    	return;
    }
    
    public void deleteJournal(int jid){
    	SQLiteDatabase db = this.getWritableDatabase();
    	String where = "jid = ?";
    	String[] whereValue = {Integer.toString(jid)};
    	db.delete("Journal", where, whereValue);
    	
    	db.close();
    	return;
    }
    
    public void updateUser(User user, String item, String value){
    	SQLiteDatabase db = this.getWritableDatabase();
    	int id = user.getId();
    	db.execSQL("update User set " + item + " = '" + value + "' where id = " + id);
    	db.close();
    	return;
    }
    
    public void updateUser(int id, String item, String value){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("update User set " + item + " = '" + value + "' where id = " + id);
    	db.close();
    	return;
    }
    
    public void updateJournal(Journal jnl, String item, String value){
    	SQLiteDatabase db = this.getWritableDatabase();
    	int jid = jnl.getJid();
    	db.execSQL("update Journal set " + item + " = '" + value + "' where jid = " + jid);
    	db.close();
    	return;
    }
    public void updateJournal(int jid, String item, String value){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("update Journal set " + item + " = '" + value + "' where jid = " + jid);
    	db.close();
    	return;
    }
}
