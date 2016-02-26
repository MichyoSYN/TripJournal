package michyo.welcome;

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

public class LoginActivity extends Activity { 
	private Button buttonLogin;
	private Button buttonRegist;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //设置textView字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txUN=(TextView)findViewById(R.id.textViewUN);
        TextPaint paint = txUN.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txUN.setTypeface(fontFace);//设置中文字体       
        TextView txPWD=(TextView)findViewById(R.id.textViewPWD);
        paint = txPWD.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txPWD.setTypeface(fontFace);//设置中文字体       
        TextView txLogin=(TextView)findViewById(R.id.textViewLogin);
        paint = txLogin.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txLogin.setTypeface(fontFace);//设置中文字体       
        TextView txRegist=(TextView)findViewById(R.id.textViewRegist);
        paint = txRegist.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txRegist.setTypeface(fontFace);//设置中文字体       
        
        ButtonListener blLogin = new ButtonListener();      
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(blLogin);
        buttonLogin.setOnTouchListener(blLogin);   
        
        ButtonListener blRegist = new ButtonListener();      
        buttonRegist = (Button) findViewById(R.id.buttonRegist);
        buttonRegist.setOnClickListener(blRegist);
        buttonRegist.setOnTouchListener(blRegist); 
    }
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	private EditText etUN = (EditText)findViewById(R.id.editTextUN);
    	private EditText etPWD = (EditText)findViewById(R.id.editTextPWD);
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonLogin){  
                Log.d("test", "cansal button ---> click");                
                
                String name = etUN.getText().toString().trim();
                String password = etPWD.getText().toString().trim();
                
                if(!name.equals("") && !password.equals("")){            	
                	User user = new User();
                	user.setUN(name);
                	user.setPWD(password);
                	
                	SQLite dbOp = new SQLite(LoginActivity.this);
                	int i = dbOp.selectUser(user);
                	if(i != -1){
                		User data = dbOp.selectUserForInfo(i);              		
                		
                		Toast.makeText(LoginActivity.this, "哟！童鞋，乃又来啦~欢迎欢迎~", Toast.LENGTH_SHORT).show(); 
                		Intent intent = new Intent();
                		intent.setClass(LoginActivity.this, DirectionActivity.class);
                		intent.putExtra("user", data);
                		LoginActivity.this.startActivity(intent);
                		LoginActivity.this.finish();
                	}
                	else{
                		Toast.makeText(LoginActivity.this, "啊偶，木有乃的记录呢，检查下用户名和密码吧~", Toast.LENGTH_SHORT).show(); 
                	}
                }
                else if(name.equals("") && password.equals("")){
                	Toast.makeText(LoginActivity.this, "请输入用户名和密码！", Toast.LENGTH_SHORT).show(); 
                }
                else if(name.equals("")){
                	Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show(); 
                }
                else{
                	Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show(); 
                }
            } 
    		else if(v.getId() == R.id.buttonRegist){
    			Log.d("test", "cansal button ---> click"); 
    			Intent intent = new Intent();
            	intent.setClass(LoginActivity.this, RegistActivity.class);
            	LoginActivity.this.startActivity(intent);
            	LoginActivity.this.finish();
                return;
    		}
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			 if(v.getId() == R.id.buttonLogin){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonLogin.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonLogin.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	            }
			 else if(v.getId() == R.id.buttonRegist){
				 if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonRegist.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonRegist.setBackgroundResource(R.drawable.button_add_pressed);  
	             }
			 }
			return false;
		}
    }    
}