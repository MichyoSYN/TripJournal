package michyo.welcome;

import michyo.welcome.SQLite;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity {
	private Button buttonOK;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        
        //设置textView字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txUN=(TextView)findViewById(R.id.textViewUNR);
        TextPaint paint = txUN.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txUN.setTypeface(fontFace);//设置中文字体       
        TextView txPWD=(TextView)findViewById(R.id.textViewPWDR);
        paint = txPWD.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txPWD.setTypeface(fontFace);//设置中文字体       
        TextView txPWDA=(TextView)findViewById(R.id.textViewPWDAgainR);
        paint = txPWDA.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txPWDA.setTypeface(fontFace);//设置中文字体       
        TextView txOK=(TextView)findViewById(R.id.textViewOK);
        paint = txOK.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txOK.setTypeface(fontFace);//设置中文字体
        
        ButtonListener blLogin = new ButtonListener();      
        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(blLogin);
        buttonOK.setOnTouchListener(blLogin); 
    }
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	private EditText etUN = (EditText) findViewById(R.id.editTextUNR);
    	private EditText etPWD = (EditText) findViewById(R.id.editTextPWDR);
    	private EditText etPWDA = (EditText) findViewById(R.id.editTextPWDAgainR);
    	private User user = new User();
    	
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonOK){  
                Log.d("test", "cansal button ---> click");
                String un = etUN.getText().toString().trim();
                String pwd = etPWD.getText().toString().trim();
                String pwda = etPWDA.getText().toString().trim();
                if(!un.equals("") && !pwd.equals("") && !pwda.equals("")){
                	if(pwd.equals(pwda)){
                		user.setId(1);
                		user.setUN(un);
                		user.setPWD(pwd);
                		user.setRDToday();
                		
                		//数据库操作
                		SQLite dbOp = new SQLite(RegistActivity.this);
                		//dbOp.onUpgrade(dbOp.getWritableDatabase(), 1, 1);//----------------test---------更新数据库
                		dbOp.insertUser(user);
                		Toast.makeText(RegistActivity.this, "撒花~恭喜童鞋乃注册成功!喵~", Toast.LENGTH_SHORT).show(); 
                		
                		int i = dbOp.selectUser(user);
                		user.setId(i);
                		
                		Intent intent = new Intent();
                		intent.setClass(RegistActivity.this, DirectionActivity.class); 
                		intent.putExtra("user", user);
                		RegistActivity.this.startActivity(intent);
                		RegistActivity.this.finish();
                	}
                	else if(pwd.equals("")){
                		Toast.makeText(RegistActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show(); 
                	}
                	else{
                		Toast.makeText(RegistActivity.this, "请确认两次密码输入相同!", Toast.LENGTH_SHORT).show();                	
                	}
                }
                else if(un.equals("")){
                	Toast.makeText(RegistActivity.this, "请输入用户名!", Toast.LENGTH_SHORT).show(); 
                }
                else{
                	Toast.makeText(RegistActivity.this, "密码需重复输入以确保正确!", Toast.LENGTH_SHORT).show();
                }
            } 
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.buttonOK){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonOK.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonOK.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	        }
			return false;
		}
    }    
}
