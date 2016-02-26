package michyo.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MenuActivity extends Activity {
	private Button buttonNewTrip;
	private Button buttonMyTrip;
	private User user;
	private String iconPath;
	private int choice = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);        

        user = (User)this.getIntent().getSerializableExtra("user");
 
    	String userName = user.getUN().trim();
    	String registDate = user.getRegistDate().trim();
    	String locate;
    	String feeling; 
    	String icon = user.getIcon();
        
    	if(user.getLocate().trim().equals("")){
    		locate = "哪儿呢？";
    	}
    	else{
    		locate = user.getLocate().toString().trim();
    	}
    	if(user.getFeeling().trim().equals("")){
    		feeling = "您的一句话个性签名？";
    	}
    	else{
    		feeling = "——" + user.getFeeling().toString().trim();
    	}
    	
        ButtonListener blNewTrip = new ButtonListener();     
        buttonNewTrip = (Button) findViewById(R.id.buttonNewTrip);
        buttonNewTrip.setOnClickListener(blNewTrip);
        buttonNewTrip.setOnTouchListener(blNewTrip);  
       
        ButtonListener blRegist = new ButtonListener();     
        buttonMyTrip = (Button) findViewById(R.id.buttonMyTrip);
        buttonMyTrip.setOnClickListener(blRegist);
        buttonMyTrip.setOnTouchListener(blRegist);
        
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );  
        TextView textIM = (TextView)findViewById(R.id.textViewIM);   
        TextPaint paint = textIM.getPaint();
        paint.setFakeBoldText(true);
        textIM.setTypeface(fontFace); 
        
        TextView textUN = (TextView)findViewById(R.id.textViewUNM);   
        paint = textUN.getPaint();
        paint.setFakeBoldText(true);
        textUN.setTypeface(fontFace);
        textUN.setText(userName);
        
        TextView textRD = (TextView)findViewById(R.id.textViewRegistDate);   
        paint = textRD.getPaint();
        paint.setFakeBoldText(true);
        textRD.setTypeface(fontFace);
        textRD.setText(registDate + "加入");
        
        TextView textL = (TextView)findViewById(R.id.textViewLocate);   
        paint = textL.getPaint();
        paint.setFakeBoldText(true);
        textL.setTypeface(fontFace);
        textL.setText("在" + locate);
        
        TextView textF = (TextView)findViewById(R.id.textViewFeeling);   
        paint = textF.getPaint();
        paint.setFakeBoldText(true);
        textF.setTypeface(fontFace);
        textF.setText(feeling);
        
        ImageView head = (ImageView) findViewById(R.id.imageViewHead);
        if(icon != "") {
        	BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 25;//图片宽高都为原来的二十五分之一，即图片为原来的六百二十五分之一 
        	Bitmap bit = BitmapFactory.decodeFile(icon, opts);                    
            head.setImageBitmap(bit);
        }
        else{
        	head.setImageResource(R.drawable.ic_launcher);
        }
    }
    
    @Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if(1 == requestCode){  
            Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){ 
            	String path = bundle.getString("file");
            	iconPath = path;
            	
            	ImageView head = (ImageView) findViewById(R.id.imageViewHead);
                if(iconPath != "") {
                	BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 25;//图片宽高都为原来的二十五分之一，即图片为原来的六百二十五分之一 
                	Bitmap bit = BitmapFactory.decodeFile(iconPath, opts);                    
                    head.setImageBitmap(bit);
                }
                else{
                	head.setImageResource(R.drawable.ic_launcher);
                }
                
                //数据库操作
                SQLite dbOp = new SQLite(MenuActivity.this);
                dbOp.updateUser(user, "icon", iconPath);
            }  
        }  
        else if(2 == requestCode){
        	Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){ 
            	User userNew = (User) bundle.getSerializable("user");
            	String feeling = userNew.getFeeling();
            	
            	if(feeling == ""){
            		feeling = "您的一句话个性签名？";
            	}           	
            	
            	Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );
                TextView textF = (TextView)findViewById(R.id.textViewFeeling);   
                TextPaint paint = textF.getPaint();
                paint.setFakeBoldText(true);
                textF.setTypeface(fontFace);
                textF.setText(feeling);
                
                user.setFeeling(feeling);
                user.setPWD(userNew.getPWD());
                //数据库操作
                SQLite dbOp = new SQLite(MenuActivity.this);
                dbOp.updateUser(user, "feeling", feeling);
            } 
        }
    }
    
    class ButtonListener implements OnClickListener,OnTouchListener{
        public void onClick(View v){
             if(v.getId() == R.id.buttonNewTrip){ 
            	Log.d("test", "cansal button ---> click"); 
            	//提供多个模板给用户选择--度假随记--商务笔记--明信片--信笺
            	AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                alert.setIcon(getResources().getDrawable(R.drawable.journal));
                alert.setTitle("My Journal Style");
              
                //添加一个单项选择列表以做选择
                final String[] modes = {"空白", "度假随记", "商务笔记", "明信片", "信笺"};
                alert.setSingleChoiceItems(modes, 0, new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	choice = whichButton;  
                    }  
                });  
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        Toast.makeText(MenuActivity.this, "喵~乃选择了" + modes[choice] + "模板哟~", Toast.LENGTH_SHORT).show(); 
                        Intent intent = new Intent();
                        intent.putExtra("choice", choice);
                        intent.putExtra("user", user);
                        intent.setClass(MenuActivity.this, EditActivity.class); 
                   		MenuActivity.this.startActivityForResult(intent, 1);
                    }  
                });  
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
              
                    }  
                });  
                 //显示对话框等待输入数据
                alert.create().show();         	
             } 
             else if(v.getId() == R.id.buttonMyTrip){
            	Log.d("test", "cansal button ---> click");
            	Intent intent = new Intent();
            	intent.putExtra("user", user);
               	intent.setClass(MenuActivity.this, FindActivity.class); 
       			MenuActivity.this.startActivity(intent);
             }
        }

        public boolean onTouch(View v, MotionEvent event) {
              // TODO Auto-generated method stub
              if(v.getId() == R.id.buttonNewTrip){ 
                    if(event.getAction() == MotionEvent.ACTION_UP){ 
                        Log.d("test", "cansal button ---> cancel"); 
                        buttonNewTrip.setBackgroundColor(Color.TRANSPARENT); 
                    }  
                    if(event.getAction() == MotionEvent.ACTION_DOWN){ 
                        Log.d("test", "cansal button ---> down"); 
                        buttonNewTrip.setBackgroundResource(R.drawable.button_add_pressed);
                    } 
                }
              else if(v.getId() == R.id.buttonMyTrip){
                   if(event.getAction() == MotionEvent.ACTION_UP){ 
                        Log.d("test", "cansal button ---> cancel"); 
                        buttonMyTrip.setBackgroundColor(Color.TRANSPARENT); 
                    }  
                    if(event.getAction() == MotionEvent.ACTION_DOWN){ 
                        Log.d("test", "cansal button ---> down"); 
                        buttonMyTrip.setBackgroundResource(R.drawable.button_add_pressed);
                 }
              }
              return false;
         }
   }
}