package michyo.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionActivity extends Activity {
	private Button buttonJournal;
	private Button buttonFriend;
	private Button buttonTrip;
	private Button buttonFootprint;
	private User user;
	private String iconPath;
	private String inputFeeling;
	private String inputName;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction);
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
    	String userName = "I'm " + user.getUN().trim();
    	String registDate = user.getRegistDate().trim() + "����";
    	String feeling; 
    	String icon = user.getIcon();
    	
    	if(user.getFeeling().trim().equals("")){
    		feeling = "����һ�仰����ǩ����";
    	}
    	else{
    		feeling = "����" + user.getFeeling().toString().trim();
    	}
    	
    	Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );  
        final TextView textUND = (TextView)findViewById(R.id.textViewUND);   
        TextPaint paint = textUND.getPaint();
        paint.setFakeBoldText(true);
        textUND.setTypeface(fontFace);
        textUND.setText(userName);
        textUND.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder alert = new AlertDialog.Builder(DirectionActivity.this);
                alert.setIcon(getResources().getDrawable(R.drawable.star));
                alert.setTitle("My Nick Name");
                alert.setMessage("����������ǳư�~");  
              
                // ���һ��EditText��������
                final EditText input = new EditText(DirectionActivity.this);
                alert.setView(input);  
              
                alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	inputName = input.getText().toString().trim();
                	if(inputName.equals("")){
                		Toast.makeText(DirectionActivity.this, "��ż���ǳƲ���Ϊ��Ŷ��~�޸�ʧ�ܿ�> <", Toast.LENGTH_SHORT).show();
                	}
                	else{
                		textUND.setText("I'm " + inputName);
                		user.setUN(inputName);
                		SQLite dbOp = new SQLite(DirectionActivity.this);
    	                dbOp.updateUser(user, "name", inputName);
    	                Toast.makeText(DirectionActivity.this, "����~��ϲ�˳ɹ��޸��ǳ���~", Toast.LENGTH_SHORT).show();
                	}                	
                  }
                });        
                alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int whichButton) {
                    // ��ȡ�����������
                  }
                });
                 //��ʾ�Ի���ȴ���������
                alert.show();
            }
        });
        
        
        TextView textRDD = (TextView)findViewById(R.id.textViewRDD);   
        paint = textRDD.getPaint();
        paint.setFakeBoldText(true);
        textRDD.setTypeface(fontFace);
        textRDD.setText(registDate);
        
        final TextView textFD = (TextView)findViewById(R.id.textViewFD);   
        paint = textFD.getPaint();
        paint.setFakeBoldText(true);
        textFD.setTypeface(fontFace);
        textFD.setText(feeling);
        textFD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DirectionActivity.this);
                alert.setIcon(getResources().getDrawable(R.drawable.heart));
                alert.setTitle("My Feeling");
                alert.setMessage("��������������~");  
              
                // ���һ��EditText��������
                final EditText input = new EditText(DirectionActivity.this);
                alert.setView(input);  
              
                alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	inputFeeling = input.getText().toString().trim();
                	user.setFeeling(inputFeeling);
            		SQLite dbOp = new SQLite(DirectionActivity.this);
	                dbOp.updateUser(user, "feeling", inputFeeling);
                	if(inputFeeling.equals("")){
                		inputFeeling = "����һ�仰����ǩ����";
                	}
                	else{
                		inputFeeling = "����" + inputFeeling;
                	}
                	textFD.setText(inputFeeling);
                  }
                });        
                alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int whichButton) {
                    // ��ȡ�����������
                  }
                });
                 //��ʾ�Ի���ȴ���������
                alert.show();
            }
        });
        
        ImageView head = (ImageView) findViewById(R.id.imageViewHeadD);
        if(icon != "") {
        	BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
        	Bitmap bit = BitmapFactory.decodeFile(icon, opts);                    
            head.setImageBitmap(bit);
        }
        else{
        	head.setImageResource(R.drawable.ic_launcher);
        }
        head.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent();
               	intent.setClass(DirectionActivity.this, MyFileManager.class); 
       			DirectionActivity.this.startActivityForResult(intent, 1);
            }
        });
    	
    	ButtonListener blJournal = new ButtonListener();     
    	buttonJournal = (Button) findViewById(R.id.buttonJournal);
    	buttonJournal.setOnClickListener(blJournal);
    	buttonJournal.setOnTouchListener(blJournal);  
    	
    	ButtonListener blFriend = new ButtonListener();     
    	buttonFriend = (Button) findViewById(R.id.buttonFriend);
    	buttonFriend.setOnClickListener(blFriend);
    	buttonFriend.setOnTouchListener(blFriend);  
    	
    	ButtonListener blTrip = new ButtonListener();     
    	buttonTrip = (Button) findViewById(R.id.buttonTrip);
    	buttonTrip.setOnClickListener(blTrip);
    	buttonTrip.setOnTouchListener(blTrip); 
    	
    	ButtonListener blFootprint = new ButtonListener();     
    	buttonFootprint = (Button) findViewById(R.id.buttonFootprint);
    	buttonFootprint.setOnClickListener(blFootprint);
    	buttonFootprint.setOnTouchListener(blFootprint); 
    }
    
    @Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if(1 == requestCode){  
            Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){ 
            	String path = bundle.getString("file");
            	iconPath = path;
            	
            	ImageView head = (ImageView) findViewById(R.id.imageViewHeadD);
                if(iconPath != "") {
                	BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
                	Bitmap bit = BitmapFactory.decodeFile(iconPath, opts);                    
                    head.setImageBitmap(bit);
                }
                else{
                	head.setImageResource(R.drawable.ic_launcher);
                }
                
                //���ݿ����
                SQLite dbOp = new SQLite(DirectionActivity.this);
                dbOp.updateUser(user, "icon", iconPath);
            }  
        }  
        else if(2 == requestCode){
        	Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){ 
            	User userNew = (User) bundle.getSerializable("user");
            	String feeling = userNew.getFeeling();
            	
            	if(feeling == ""){
            		feeling = "����һ�仰����ǩ����";
            	}
            	else{
            		feeling = "����" + feeling;
            	}
            	
            	Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );
                TextView textF = (TextView)findViewById(R.id.textViewFD);   
                TextPaint paint = textF.getPaint();
                paint.setFakeBoldText(true);
                textF.setTypeface(fontFace);
                textF.setText(feeling);
                
                user.setFeeling(feeling);
                user.setPWD(userNew.getPWD());
                //���ݿ����
                SQLite dbOp = new SQLite(DirectionActivity.this);
                dbOp.updateUser(user, "feeling", feeling);
            } 
        }
    }
    
    class ButtonListener implements OnClickListener,OnTouchListener{
        public void onClick(View v){
             if(v.getId() == R.id.buttonJournal){
            	 Log.d("test", "cansal button ---> click");
            	 Toast.makeText(DirectionActivity.this, "ͯЬ����ӭʹ����־����Ӵ~����~", Toast.LENGTH_SHORT).show();
            	 Intent intent = new Intent();
         		 intent.setClass(DirectionActivity.this, MenuActivity.class);
         		 intent.putExtra("user", user);
         		 DirectionActivity.this.startActivity(intent);
             } 
             else if(v.getId() == R.id.buttonFriend){
            	 Log.d("test", "cansal button ---> click");
            	 Toast.makeText(DirectionActivity.this, "ͯЬ����ӭʹ�ú��ѹ���Ӵ~����~", Toast.LENGTH_SHORT).show();
            	 Intent intent = new Intent();
         		 intent.setClass(DirectionActivity.this, SelectFriendActivity.class);
         		 intent.putExtra("user", user);
         		 DirectionActivity.this.startActivity(intent);
             }
             else if(v.getId() == R.id.buttonTrip){
            	 Log.d("test", "cansal button ---> click");
            	 Toast.makeText(DirectionActivity.this, "ͯЬ����ӭʹ���ó̹���Ӵ~����~", Toast.LENGTH_SHORT).show();
            	 Intent intent = new Intent();
         		 intent.setClass(DirectionActivity.this, MyTripManager.class);
         		 intent.putExtra("user", user);
         		 DirectionActivity.this.startActivity(intent);
             }
             else if(v.getId() == R.id.buttonFootprint){
            	 Log.d("test", "cansal button ---> click");
            	 Toast.makeText(DirectionActivity.this, "ͯЬ����ӭʹ���㼣����Ӵ~����~", Toast.LENGTH_SHORT).show();
            	 Intent intent = new Intent();
         		 intent.setClass(DirectionActivity.this, FootprintActivity.class);
         		 DirectionActivity.this.startActivity(intent);
             }
        }

        public boolean onTouch(View v, MotionEvent event) {
              // TODO Auto-generated method stub
              return false;
         }
   }
}
