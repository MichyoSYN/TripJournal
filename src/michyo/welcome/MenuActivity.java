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
    		locate = "�Ķ��أ�";
    	}
    	else{
    		locate = user.getLocate().toString().trim();
    	}
    	if(user.getFeeling().trim().equals("")){
    		feeling = "����һ�仰����ǩ����";
    	}
    	else{
    		feeling = "����" + user.getFeeling().toString().trim();
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
        textRD.setText(registDate + "����");
        
        TextView textL = (TextView)findViewById(R.id.textViewLocate);   
        paint = textL.getPaint();
        paint.setFakeBoldText(true);
        textL.setTypeface(fontFace);
        textL.setText("��" + locate);
        
        TextView textF = (TextView)findViewById(R.id.textViewFeeling);   
        paint = textF.getPaint();
        paint.setFakeBoldText(true);
        textF.setTypeface(fontFace);
        textF.setText(feeling);
        
        ImageView head = (ImageView) findViewById(R.id.imageViewHead);
        if(icon != "") {
        	BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
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
                    opts.inSampleSize = 25;//ͼƬ��߶�Ϊԭ���Ķ�ʮ���֮һ����ͼƬΪԭ�������ٶ�ʮ���֮һ 
                	Bitmap bit = BitmapFactory.decodeFile(iconPath, opts);                    
                    head.setImageBitmap(bit);
                }
                else{
                	head.setImageResource(R.drawable.ic_launcher);
                }
                
                //���ݿ����
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
            		feeling = "����һ�仰����ǩ����";
            	}           	
            	
            	Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF" );
                TextView textF = (TextView)findViewById(R.id.textViewFeeling);   
                TextPaint paint = textF.getPaint();
                paint.setFakeBoldText(true);
                textF.setTypeface(fontFace);
                textF.setText(feeling);
                
                user.setFeeling(feeling);
                user.setPWD(userNew.getPWD());
                //���ݿ����
                SQLite dbOp = new SQLite(MenuActivity.this);
                dbOp.updateUser(user, "feeling", feeling);
            } 
        }
    }
    
    class ButtonListener implements OnClickListener,OnTouchListener{
        public void onClick(View v){
             if(v.getId() == R.id.buttonNewTrip){ 
            	Log.d("test", "cansal button ---> click"); 
            	//�ṩ���ģ����û�ѡ��--�ȼ����--����ʼ�--����Ƭ--�ż�
            	AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
                alert.setIcon(getResources().getDrawable(R.drawable.journal));
                alert.setTitle("My Journal Style");
              
                //���һ������ѡ���б�����ѡ��
                final String[] modes = {"�հ�", "�ȼ����", "����ʼ�", "����Ƭ", "�ż�"};
                alert.setSingleChoiceItems(modes, 0, new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	choice = whichButton;  
                    }  
                });  
                alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        Toast.makeText(MenuActivity.this, "��~��ѡ����" + modes[choice] + "ģ��Ӵ~", Toast.LENGTH_SHORT).show(); 
                        Intent intent = new Intent();
                        intent.putExtra("choice", choice);
                        intent.putExtra("user", user);
                        intent.setClass(MenuActivity.this, EditActivity.class); 
                   		MenuActivity.this.startActivityForResult(intent, 1);
                    }  
                });  
                alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
              
                    }  
                });  
                 //��ʾ�Ի���ȴ���������
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