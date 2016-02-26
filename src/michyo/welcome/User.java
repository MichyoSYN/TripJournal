package michyo.welcome;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class User implements Serializable{
	private int userId;
	private String userName;
	private String userPassword;
	private String registDate;
	private String locate;
	private String feeling;
	private String icon;
	
	public User(){
		this.userId = 0;
		this.userName = "";
		this.userPassword = "";
		this.registDate = "";
		this.feeling = "";
		this.locate = "";
		this.icon = "";
	}	
	public User(int id){
		this.userId = id;
	}
	public User(String name, String pwd){
		this.userId = 0;
		this.userName = name;
		this.userPassword = pwd;
		java.util.Date now =new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.registDate = dateFormat.format(now);
		this.registDate = "";
		this.locate = "";
		this.feeling = "";		
	}
	
	public void setId(int id){
		this.userId = id;
		return;
	}	
	public void setUN(String un){
		this.userName = un;
		return;
	}	
	public void setPWD(String pwd){
		this.userPassword = pwd;
		return;
	}
	
	public String getUN(){
		return userName;
	}	
	public String getPWD(){
		return userPassword;
	}
	public int getId(){
		return userId;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public void setRD(Date rd){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.registDate = dateFormat.format(rd);
		return;
	}
	public void setRDToday(){
		java.util.Date now =new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.registDate = dateFormat.format(now);
		return;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}

