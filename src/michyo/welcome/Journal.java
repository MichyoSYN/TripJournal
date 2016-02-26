package michyo.welcome;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Journal implements Serializable {
	private int jid;
	private String date;
	private String title;
	private String location;
	private String content;
	private String picture;
	private String record;
	private String video;
	private int id;
	
	public Journal(){
		this.jid = 0;
		this.date = "";
		this.title = "";
		this.location = "";
		this.content = "";
		this.picture = "";
		this.setRecord("");
		this.setVideo("");
		this.id = 0;
	}
	
	public Journal(String date, String title, String location, String content, String picture){
		this.setDate(date);
		this.setTitle(title);
		this.setLocation(location);
		this.setContent(content);
		this.setPicture(picture);
	}
	
	public Journal(String title, String location, String content, String picture){
		java.util.Date now =new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.setDate(dateFormat.format(now));
		this.setTitle(title);
		this.setLocation(location);
		this.setContent(content);
		this.setPicture(picture);
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public void setDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.date = dateFormat.format(date);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
}
