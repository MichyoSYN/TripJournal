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
        
        //����textView����
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txUN=(TextView)findViewById(R.id.textViewUN);
        TextPaint paint = txUN.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txUN.setTypeface(fontFace);//������������       
        TextView txPWD=(TextView)findViewById(R.id.textViewPWD);
        paint = txPWD.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txPWD.setTypeface(fontFace);//������������       
        TextView txLogin=(TextView)findViewById(R.id.textViewLogin);
        paint = txLogin.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txLogin.setTypeface(fontFace);//������������       
        TextView txRegist=(TextView)findViewById(R.id.textViewRegist);
        paint = txRegist.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txRegist.setTypeface(fontFace);//������������       
        
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
                		
                		Toast.makeText(LoginActivity.this, "Ӵ��ͯЬ����������~��ӭ��ӭ~", Toast.LENGTH_SHORT).show(); 
                		Intent intent = new Intent();
                		intent.setClass(LoginActivity.this, DirectionActivity.class);
                		intent.putExtra("user", data);
                		LoginActivity.this.startActivity(intent);
                		LoginActivity.this.finish();
                	}
                	else{
                		Toast.makeText(LoginActivity.this, "��ż��ľ���˵ļ�¼�أ�������û����������~", Toast.LENGTH_SHORT).show(); 
                	}
                }
                else if(name.equals("") && password.equals("")){
                	Toast.makeText(LoginActivity.this, "�������û��������룡", Toast.LENGTH_SHORT).show(); 
                }
                else if(name.equals("")){
                	Toast.makeText(LoginActivity.this, "�������û�����", Toast.LENGTH_SHORT).show(); 
                }
                else{
                	Toast.makeText(LoginActivity.this, "���������룡", Toast.LENGTH_SHORT).show(); 
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