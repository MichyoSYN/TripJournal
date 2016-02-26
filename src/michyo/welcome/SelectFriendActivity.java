package michyo.welcome;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFriendActivity extends Activity{
	private Button buttonFriendJournal;
	private Button buttonFriendMessage;
	private Button buttonSelectFriend;
	private Button buttonSearchFriend;
	private ImageView ivHead;
	private User user;
	private User chosedUser;
    private ArrayList<Integer> ids = new ArrayList<Integer>();
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> passwords = new ArrayList<String>();
    private ArrayList<String> registDates = new ArrayList<String>();
    private ArrayList<String> locates = new ArrayList<String>();        
    private ArrayList<String> feelings = new ArrayList<String>();
    private ArrayList<String> icons = new ArrayList<String>();
    private ArrayList<String> infos = new ArrayList<String>();
    

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.selectfriend);
        ivHead = (ImageView) findViewById(R.id.imageViewHeadF);
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
        ButtonListener blSF = new ButtonListener();     
        buttonSelectFriend = (Button) findViewById(R.id.buttonSelectFriend);
        buttonSelectFriend.setOnClickListener(blSF);
        buttonSelectFriend.setOnTouchListener(blSF); 
        
        ButtonListener blSearch = new ButtonListener();     
        buttonSearchFriend = (Button) findViewById(R.id.buttonSearchFriend);
        buttonSearchFriend.setOnClickListener(blSearch);
        buttonSearchFriend.setOnTouchListener(blSearch);
        
        ButtonListener blFJ = new ButtonListener();     
        buttonFriendJournal = (Button) findViewById(R.id.buttonFriendJournal);
        buttonFriendJournal.setOnClickListener(blFJ);
        buttonFriendJournal.setOnTouchListener(blFJ);  
       
        ButtonListener blFM = new ButtonListener();     
        buttonFriendMessage = (Button) findViewById(R.id.buttonFriendMessage);
        buttonFriendMessage.setOnClickListener(blFM);
        buttonFriendMessage.setOnTouchListener(blFM);
        
        setIntent(user);
        
        if(ids.size() == 0){
        	Toast.makeText(SelectFriendActivity.this, "��ż��ͯЬ�������ڻ�ľ�к���=-=���Ͻ�ȥ��һ���~", Toast.LENGTH_SHORT).show();
         	Intent intent = new Intent(SelectFriendActivity.this, SearchActivity.class); 
         	intent.putExtra("user", user);
         	startActivityForResult(intent, 2);
        }        
        else{
        	Intent intent = new Intent(SelectFriendActivity.this, MyFriendManager.class); 
        	intent.putIntegerArrayListExtra("ids", ids);
        	intent.putStringArrayListExtra("names", names);
        	intent.putStringArrayListExtra("passwords", passwords);
        	intent.putStringArrayListExtra("registDates", registDates);
        	intent.putStringArrayListExtra("locates", locates);
        	intent.putStringArrayListExtra("feelings", feelings);
        	intent.putStringArrayListExtra("icons", icons); 
        	intent.putStringArrayListExtra("infos", infos); 
        	startActivityForResult(intent, 1);
        }
	}
	
	//�����ҵ������ѵ���Ϣ----����ȫ�ֱ���ֵ
	private void setIntent(User user){
		SQLite dbOp = new SQLite(SelectFriendActivity.this);
    	Vector<User> myFriend = dbOp.findFriendInfo(user);
    	
    	ids.clear();
    	names.clear();
    	passwords.clear();
    	registDates.clear();
    	locates.clear();
    	feelings.clear();
    	icons.clear();
    	infos.clear();
    	
    	for(int i = 0; i < myFriend.size(); i++){
    		User temp = myFriend.get(i);
    		ids.add(temp.getId());
    		names.add(temp.getUN());
    		passwords.add(temp.getPWD());
    		registDates.add(temp.getRegistDate());
    		locates.add(temp.getLocate());
    		feelings.add(temp.getFeeling());
    		icons.add(temp.getIcon());
    		infos.add(temp.getUN() + "��" + temp.getFeeling() + "\n������" + temp.getRegistDate() + "����");
    		//Toast.makeText(SelectFriendActivity.this, temp.getUN(), Toast.LENGTH_SHORT).show();
    	}
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {          
        if(1 == requestCode){ 
        	if(resultCode == 2){
        		SelectFriendActivity.this.finish();
        	}
            if(data != null){         
            	chosedUser = (User) data.getSerializableExtra("chosedUser");
            	
            	String userName = chosedUser.getUN().trim();
            	String registDate = chosedUser.getRegistDate().trim() + "����";
            	String feeling = chosedUser.getFeeling().trim(); 
            	String icon = chosedUser.getIcon();
            	
            	if(feeling.equals("")){
            		feeling = "...TAû����ǩ��";
            	}
            	else{
            		feeling = "����" + feeling;
            	}
                
                Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );  
                TextView textUND = (TextView)findViewById(R.id.textViewUNF);   
                TextPaint paint = textUND.getPaint();
                paint.setFakeBoldText(true);
                textUND.setTypeface(fontFace);
                textUND.setText(userName);
                
                TextView textRDD = (TextView)findViewById(R.id.textViewRDF);   
                paint = textRDD.getPaint();
                paint.setFakeBoldText(true);
                textRDD.setTypeface(fontFace);
                textRDD.setText(registDate);
                
                TextView textFD = (TextView)findViewById(R.id.textViewFF);   
                paint = textFD.getPaint();
                paint.setFakeBoldText(true);
                textFD.setTypeface(fontFace);
                textFD.setText(feeling);
                
                ImageView head = (ImageView) findViewById(R.id.imageViewHeadF);
                if(icon != "") {
                	BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
                	Bitmap bit = BitmapFactory.decodeFile(icon, opts);                    
                    head.setImageBitmap(bit);
                }
                else{
                	head.setImageResource(R.drawable.ic_launcher);
                }

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 8;//ͼƬ��߶�Ϊԭ���İ˷�֮һ����ͼƬΪԭ������ʮ�ķ�֮һ 
                Bitmap bit = BitmapFactory.decodeFile(chosedUser.getIcon(), opts);                 
                ivHead.setImageBitmap(bit);  
            }  
        }  
        else if(2 == requestCode){   
            if(data != null){        
            	chosedUser = (User) data.getSerializableExtra("chosedUser");
            	
            	String userName = chosedUser.getUN().trim();
            	String registDate = chosedUser.getRegistDate().trim() + "����";
            	String feeling = chosedUser.getFeeling().trim(); 
            	String icon = chosedUser.getIcon();
            	
            	if(feeling.equals("")){
            		feeling = "...TAû����ǩ��";
            	}
            	else{
            		feeling = "����" + feeling;
            	}
                
                Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );  
                TextView textUND = (TextView)findViewById(R.id.textViewUNF);   
                TextPaint paint = textUND.getPaint();
                paint.setFakeBoldText(true);
                textUND.setTypeface(fontFace);
                textUND.setText(userName);
                
                TextView textRDD = (TextView)findViewById(R.id.textViewRDF);   
                paint = textRDD.getPaint();
                paint.setFakeBoldText(true);
                textRDD.setTypeface(fontFace);
                textRDD.setText(registDate);
                
                TextView textFD = (TextView)findViewById(R.id.textViewFF);   
                paint = textFD.getPaint();
                paint.setFakeBoldText(true);
                textFD.setTypeface(fontFace);
                textFD.setText(feeling);
                
                ImageView head = (ImageView) findViewById(R.id.imageViewHeadF);
                if(icon != "") {
                	BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
                	Bitmap bit = BitmapFactory.decodeFile(icon, opts);                    
                    head.setImageBitmap(bit);
                }
                else{
                	head.setImageResource(R.drawable.ic_launcher);
                }

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 8;//ͼƬ��߶�Ϊԭ���İ˷�֮һ����ͼƬΪԭ������ʮ�ķ�֮һ 
                Bitmap bit = BitmapFactory.decodeFile(chosedUser.getIcon(), opts);                 
                ivHead.setImageBitmap(bit);  
            }  
        }  
    } 
	
	class ButtonListener implements OnClickListener,OnTouchListener{
        public void onClick(View v){
             if(v.getId() == R.id.buttonFriendJournal){ 
            	Log.d("test", "cansal button ---> click");
            	
            	//�鿴������־
            	ArrayList<String> pictures = new ArrayList<String>();
                ArrayList<String> titles = new ArrayList<String>();
                ArrayList<String> dates = new ArrayList<String>();
                ArrayList<String> locations = new ArrayList<String>();
                ArrayList<String> contents = new ArrayList<String>();
                ArrayList<Integer> jids = new ArrayList<Integer>();
                ArrayList<String> shows = new ArrayList<String>();
                ArrayList<String> videos = new ArrayList<String>();
                ArrayList<String> records = new ArrayList<String>();
                
                SQLite dbOp = new SQLite(SelectFriendActivity.this);
            	Vector<Journal> findJournal = new Vector<Journal>();
            	findJournal = dbOp.selectJournalByAuthor(chosedUser);
            	
            	if(!findJournal.isEmpty()){
            		for(int i = 0; i < findJournal.size(); i++){
            			Journal temp = findJournal.get(i);
            			jids.add(temp.getJid());
            			pictures.add(temp.getPicture());
            			titles.add(temp.getTitle());
            			dates.add(temp.getDate());
            			locations.add(temp.getLocation());
            			contents.add(temp.getContent());
            			shows.add(temp.getTitle() + " " + temp.getDate() + "\n@" + temp.getLocation()
            					+ "\n" + temp.getContent());
            			videos.add(temp.getVideo());
            			records.add(temp.getRecord());
            		}            		
            		Intent intent = new Intent();
                	intent.setClass(SelectFriendActivity.this, MyFriendJournalManager.class);
                	intent.putStringArrayListExtra("pictures", pictures);
                	intent.putStringArrayListExtra("titles", titles);
                	intent.putStringArrayListExtra("dates", dates);
                	intent.putStringArrayListExtra("locations", locations);
                	intent.putStringArrayListExtra("contents", contents);
                	intent.putIntegerArrayListExtra("jids", jids);
                	intent.putStringArrayListExtra("shows", shows);
                	intent.putStringArrayListExtra("videos", videos);
                	intent.putStringArrayListExtra("records", records);
                	SelectFriendActivity.this.startActivity(intent); 
            	}
            	else{
            		Toast.makeText(SelectFriendActivity.this, "��ż����λͯЬ��û���κ�������־=-=", Toast.LENGTH_SHORT).show(); 
            	}      
             } 
             else if(v.getId() == R.id.buttonFriendMessage){
            	Log.d("test", "cansal button ---> click");
             }
             else if(v.getId() == R.id.buttonSelectFriend){
            	 Log.d("test", "cansal button ---> click");
                 
            	 //ѡ�����
            	 setIntent(user);
                 if(ids.size() == 0){
                 	Toast.makeText(SelectFriendActivity.this, "��ż��ͯЬ�������ڻ�ľ�к���=-=���Ͻ�ȥ��һ���~", Toast.LENGTH_SHORT).show();
                 	Intent intent = new Intent(SelectFriendActivity.this, SearchActivity.class); 
                 	intent.putExtra("user", user);
                 	startActivityForResult(intent, 2);
                 }
                 else{                 
                	 Intent intent = new Intent(SelectFriendActivity.this, MyFriendManager.class); 
                	 intent.putIntegerArrayListExtra("ids", ids);
                	 intent.putStringArrayListExtra("names", names);
                	 intent.putStringArrayListExtra("passwords", passwords);
                	 intent.putStringArrayListExtra("registDates", registDates);
                	 intent.putStringArrayListExtra("locates", locates);
                	 intent.putStringArrayListExtra("feelings", feelings);
                	 intent.putStringArrayListExtra("icons", icons); 
                	 intent.putStringArrayListExtra("infos", infos); 
                	 startActivityForResult(intent, 1);
                 }
             }
             else if(v.getId() == R.id.buttonSearchFriend){
            	 Log.d("test", "cansal button ---> click");
            	 Intent intent = new Intent(SelectFriendActivity.this, SearchActivity.class); 
            	 intent.putExtra("user", user);
            	 startActivityForResult(intent, 2);
             }
        }

        public boolean onTouch(View v, MotionEvent event) {
              // TODO Auto-generated method stub
              if(v.getId() == R.id.buttonFriendJournal){ 
                    if(event.getAction() == MotionEvent.ACTION_UP){ 
                        Log.d("test", "cansal button ---> cancel"); 
                        buttonFriendJournal.setBackgroundColor(Color.TRANSPARENT); 
                    }  
                    if(event.getAction() == MotionEvent.ACTION_DOWN){ 
                        Log.d("test", "cansal button ---> down"); 
                        buttonFriendJournal.setBackgroundResource(R.drawable.button_add_pressed);
                    } 
                }
              else if(v.getId() == R.id.buttonFriendMessage){
                   if(event.getAction() == MotionEvent.ACTION_UP){ 
                        Log.d("test", "cansal button ---> cancel"); 
                        buttonFriendMessage.setBackgroundColor(Color.TRANSPARENT); 
                    }  
                    if(event.getAction() == MotionEvent.ACTION_DOWN){ 
                        Log.d("test", "cansal button ---> down"); 
                        buttonFriendMessage.setBackgroundResource(R.drawable.button_add_pressed);
                 }
              }
              return false;
         }
   }
}
