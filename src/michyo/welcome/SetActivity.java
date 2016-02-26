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

public class SetActivity extends Activity{
	private Button buttonSet;
	private Button buttonCancel;
	private User user;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);   
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
    	EditText etFeeling = (EditText) findViewById(R.id.editTextF);
    	etFeeling.setText(user.getFeeling());
    	
    	
        //设置textView字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txT=(TextView)findViewById(R.id.textViewPWDE);
        TextPaint paint = txT.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txT.setTypeface(fontFace);//设置中文字体       
        TextView txL=(TextView)findViewById(R.id.textViewPWDEA);
        paint = txL.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txL.setTypeface(fontFace);//设置中文字体       
        TextView txU=(TextView)findViewById(R.id.textViewF);
        paint = txU.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txU.setTypeface(fontFace);//设置中文字体
        TextView txS=(TextView)findViewById(R.id.textViewCancelS);
        paint = txS.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txS.setTypeface(fontFace);//设置中文字体       
        TextView txC=(TextView)findViewById(R.id.textViewSet);
        paint = txC.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txC.setTypeface(fontFace);//设置中文字体
        
        ButtonListener blSet = new ButtonListener();      
        buttonSet = (Button) findViewById(R.id.buttonSet);
        buttonSet.setOnClickListener(blSet);
        buttonSet.setOnTouchListener(blSet); 
        
        ButtonListener blCan = new ButtonListener();      
        buttonCancel = (Button) findViewById(R.id.buttonCancelS);
        buttonCancel.setOnClickListener(blCan);
        buttonCancel.setOnTouchListener(blCan); 
	}
	
	class ButtonListener implements OnClickListener,OnTouchListener{
    	private EditText etPWD = (EditText) findViewById(R.id.editTextPWDE);
    	private EditText etPWDA = (EditText) findViewById(R.id.editTextPWDEA);
    	private EditText etFeeling = (EditText) findViewById(R.id.editTextF);
    	
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonSet){
    			String pwd = etPWD.getText().toString().trim();
    			String pwda = etPWDA.getText().toString().trim();
    			String feeling = etFeeling.getText().toString().trim();
    			
    			if(!pwd.equals("") && !pwda.equals("")){
    				if(pwd.equals(pwda)){
    					user.setPWD(pwd);
    					Toast.makeText(SetActivity.this, "成功修改密码!", Toast.LENGTH_SHORT).show(); 
    					SQLite dbOp = new SQLite(SetActivity.this);
    	                dbOp.updateUser(user, "password", pwd);
    				}
    				else{
    					Toast.makeText(SetActivity.this, "两次密码输入不一致!", Toast.LENGTH_SHORT).show();
    				}
    			}
    			else if(pwd.equals("") && pwda.equals("")){
    				Toast.makeText(SetActivity.this, "请输入密码!", Toast.LENGTH_SHORT).show();
    			}
    			else{
    				Toast.makeText(SetActivity.this, "密码需重复输入以确保正确!", Toast.LENGTH_SHORT).show();
    			}
    			
    			user.setFeeling(feeling);
    			SQLite dbOp = new SQLite(SetActivity.this);
                dbOp.updateUser(user, "feeling", feeling);
    			
                Log.d("test", "cansal button ---> click");
                Toast.makeText(SetActivity.this, "撒花~成功修改签名了哟~", Toast.LENGTH_SHORT).show();
                Intent data = new Intent(SetActivity.this, MenuActivity.class);  
                Bundle bundle = new Bundle();  
                bundle.putSerializable("user", user);  
                data.putExtras(bundle);  
                setResult(2, data);  
                finish(); 
            }
    		else if(v.getId() == R.id.buttonCancelS){  
                Log.d("test", "cansal button ---> click");
            }
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.buttonSet){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonSet.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonSet.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	        }			
			if(v.getId() == R.id.buttonCancelS){  
                if(event.getAction() == MotionEvent.ACTION_UP){  
                    Log.d("test", "cansal button ---> cancel");  
                    buttonCancel.setBackgroundResource(R.drawable.button_add);  
                }   
                if(event.getAction() == MotionEvent.ACTION_DOWN){  
                    Log.d("test", "cansal button ---> down");  
                    buttonCancel.setBackgroundResource(R.drawable.button_add_pressed);  
                }  
        }
			return false;
		}
    }    
}
