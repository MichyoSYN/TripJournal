package michyo.welcome;

import java.util.Vector;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private RadioGroup rg;  
	private RadioButton rbExact;
	private RadioButton rbLike;
	private EditText etSearch;
	private TextView txOK;
	private Button btOK;
	private int flag = 1;
	private User user;
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.search);
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
        rg = (RadioGroup) findViewById(R.id.radiogroupS);  
        rbExact = (RadioButton) findViewById(R.id.radioButtonExact);
        rbLike = (RadioButton) findViewById(R.id.radioButtonLike);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                // TODO Auto-generated method stub  
                if(checkedId == rbExact.getId()) {  
                    flag = 1;  
                }
                else if(checkedId == rbLike.getId()) {  
                    flag = 2;  
                } 
                //Toast.makeText(FindActivity.this, Integer.toString(flagR), Toast.LENGTH_SHORT).show(); 
            }  
        }); 
        
        etSearch = (EditText) findViewById(R.id.editTextSearchFriend);
        
        //����textView����
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        txOK=(TextView)findViewById(R.id.textViewConfirmFriend);
        TextPaint paint = txOK.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txOK.setTypeface(fontFace);//������������      
        
        if(flag == 1){
        	txOK.setText("���");
        }
        else if(flag == 2){
        	txOK.setText("����");
        }
        
        ButtonListener blOK = new ButtonListener();     
        btOK = (Button) findViewById(R.id.buttonConfirmFriend);
        btOK.setOnClickListener(blOK);
        btOK.setOnTouchListener(blOK); 
	}
	
	class ButtonListener implements OnClickListener,OnTouchListener{		
        public void onClick(View v){
             if(v.getId() == R.id.buttonConfirmFriend){
            	Log.d("test", "cansal button ---> click");
            	
            	String search = etSearch.getText().toString().trim();
            	SQLite dbOp = new SQLite(SearchActivity.this);
        		User searchedUser = dbOp.selectUserByName(search);
        		
        		//�ж��Ƿ��Ѿ���Ϊ����
        		Vector<User> friendList = dbOp.findFriendInfo(user);
        		int flag = 0;
        		if(friendList.size() != 0){
        			for(int i = 0; i < friendList.size(); i++){
        				if(friendList.get(i).getId() == searchedUser.getId()){
        					flag = 1;
        					break;
        				}
        			}
        		}
        		
        		if(searchedUser.getId() !=0 ){
        			if(flag == 0){
        				dbOp.createFriendship(user, searchedUser);
        				Toast.makeText(SearchActivity.this, "����~�ѳɹ����" + search + "Ϊ�˵ĺ�����~", Toast.LENGTH_SHORT).show();
        		
        				Intent intent = new Intent(SearchActivity.this, SelectFriendActivity.class);   
        				intent.putExtra("chosedUser", searchedUser);
        				setResult(2, intent);   
        				SearchActivity.this.finish(); 
        			}
        			else{
        				Toast.makeText(SearchActivity.this, "�����Ѿ��Ǻ����ˣ������ټ�һ����~", Toast.LENGTH_SHORT).show();
        				Intent intent = new Intent(SearchActivity.this, SelectFriendActivity.class);   
        				intent.putExtra("chosedUser", searchedUser);
        				setResult(2, intent);   
        				SearchActivity.this.finish(); 
        			}
        		}
        		else{
        			Toast.makeText(SearchActivity.this, "��ż���Ҳ���Ҫ������ͯЬ�������������˰�~", Toast.LENGTH_SHORT).show();
        		}
             }
        }

        public boolean onTouch(View v, MotionEvent event) {
              // TODO Auto-generated method stub
              if(v.getId() == R.id.buttonConfirmFriend){ 
                    if(event.getAction() == MotionEvent.ACTION_UP){ 
                        Log.d("test", "cansal button ---> cancel"); 
                        btOK.setBackgroundColor(R.drawable.button_add); 
                    }  
                    if(event.getAction() == MotionEvent.ACTION_DOWN){ 
                        Log.d("test", "cansal button ---> down"); 
                        btOK.setBackgroundResource(R.drawable.button_add_pressed);
                    } 
              }
              return false;
         }
   }
}
