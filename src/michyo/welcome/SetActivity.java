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
    	
    	
        //����textView����
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txT=(TextView)findViewById(R.id.textViewPWDE);
        TextPaint paint = txT.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txT.setTypeface(fontFace);//������������       
        TextView txL=(TextView)findViewById(R.id.textViewPWDEA);
        paint = txL.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txL.setTypeface(fontFace);//������������       
        TextView txU=(TextView)findViewById(R.id.textViewF);
        paint = txU.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txU.setTypeface(fontFace);//������������
        TextView txS=(TextView)findViewById(R.id.textViewCancelS);
        paint = txS.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txS.setTypeface(fontFace);//������������       
        TextView txC=(TextView)findViewById(R.id.textViewSet);
        paint = txC.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txC.setTypeface(fontFace);//������������
        
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
    					Toast.makeText(SetActivity.this, "�ɹ��޸�����!", Toast.LENGTH_SHORT).show(); 
    					SQLite dbOp = new SQLite(SetActivity.this);
    	                dbOp.updateUser(user, "password", pwd);
    				}
    				else{
    					Toast.makeText(SetActivity.this, "�����������벻һ��!", Toast.LENGTH_SHORT).show();
    				}
    			}
    			else if(pwd.equals("") && pwda.equals("")){
    				Toast.makeText(SetActivity.this, "����������!", Toast.LENGTH_SHORT).show();
    			}
    			else{
    				Toast.makeText(SetActivity.this, "�������ظ�������ȷ����ȷ!", Toast.LENGTH_SHORT).show();
    			}
    			
    			user.setFeeling(feeling);
    			SQLite dbOp = new SQLite(SetActivity.this);
                dbOp.updateUser(user, "feeling", feeling);
    			
                Log.d("test", "cansal button ---> click");
                Toast.makeText(SetActivity.this, "����~�ɹ��޸�ǩ����Ӵ~", Toast.LENGTH_SHORT).show();
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
