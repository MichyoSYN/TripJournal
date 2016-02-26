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

public class EditActivity extends Activity {	
	private Button buttonUpload;
	private Button buttonAdd;
	private User user;
	private int choice;
	private String pic = "";
	private String record = "";
	private String video = "" ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        
        user = (User)this.getIntent().getSerializableExtra("user");
        choice = (Integer)this.getIntent().getIntExtra("choice", 0);
        
        EditText title = (EditText)findViewById(R.id.editTextTitle);
        EditText location = (EditText)findViewById(R.id.editTextLocation);
        EditText content = (EditText)findViewById(R.id.editTextContent);
        //----添加模板设置
        //模板:空白0--度假随记1--商务笔记2--明信片3--信笺4
        if(choice == 1){
        	title.setText("-度假随记");
        	location.setText("-景点");
        	content.setText("今天去了\n虽然人很多，但是还是玩得很开心。");
        }
        else if(choice == 2){
        	title.setText("-商务笔记");
        	location.setText("-宾馆会议厅");
        	content.setText("今天与       在         就            问题进行讨论。\n本次会议总共达成一下共识：");
        }
        else if(choice == 3){
        	title.setText("-明信片");
        	location.setText("-邮局");
        	content.setText("亲爱的     ：\n  我在       问候你哟~这里\n希望下次能和你一起来。");
        }
        else if(choice == 4){
        	title.setText("-信笺");
        	location.setText("-邮局");
        	content.setText("亲爱的     ：\n  最近好吗？我在       问候你哟~这里\n希望下次能和你一起来。\n此致\n  敬礼");
        }
        
        //设置textView字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txT=(TextView)findViewById(R.id.textViewTitle);
        TextPaint paint = txT.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txT.setTypeface(fontFace);//设置中文字体       
        TextView txL=(TextView)findViewById(R.id.textViewLocation);
        paint = txL.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txL.setTypeface(fontFace);//设置中文字体       
        TextView txU=(TextView)findViewById(R.id.textViewUpload);
        paint = txU.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txU.setTypeface(fontFace);//设置中文字体
        TextView txA=(TextView)findViewById(R.id.textViewAdd);
        paint = txA.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txA.setTypeface(fontFace);//设置中文字体    
        
        ButtonListener blUpload = new ButtonListener();      
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(blUpload);
        buttonUpload.setOnTouchListener(blUpload); 
        
        ButtonListener blAdd = new ButtonListener();  
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(blAdd);
        buttonAdd.setOnTouchListener(blAdd); 
    }
    
    @Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if(1 == requestCode){  
            Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){ 
            	pic = bundle.getString("pic");
            	record = bundle.getString("record");
            	video = bundle.getString("video");            	                 
                
                String mediaDescribe = "图片为" + pic + "\n录音为" + record + "\n视频为" + video;
                TextView media = (TextView) findViewById(R.id.textViewMedia);
                media.setText(mediaDescribe);
            }  
        }  
    } 
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	private EditText etTitle = (EditText) findViewById(R.id.editTextTitle);
    	private EditText etLocation = (EditText) findViewById(R.id.editTextLocation);
    	private EditText etContent = (EditText) findViewById(R.id.editTextContent);
    	
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonUpload){  
                Log.d("test", "cansal button ---> click");
                java.util.Date now =new java.util.Date();
                String title = etTitle.getText().toString().trim();
                String location = etLocation.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                int id = user.getId();
                
                Journal jnl = new Journal();
                jnl.setDate(now);
                jnl.setTitle(title);
                jnl.setLocation(location);
                jnl.setContent(content);
                jnl.setPicture(pic);
                jnl.setRecord(record);
                jnl.setVideo(video);
                jnl.setId(id);
                
                SQLite dbOp = new SQLite(EditActivity.this);
                dbOp.insertJournal(jnl);
                Toast.makeText(EditActivity.this, "成功在本地保存本日志!", Toast.LENGTH_SHORT).show(); 
                
                Intent data = new Intent(EditActivity.this, MenuActivity.class);  
                Bundle bundle = new Bundle();  
                bundle.putSerializable("journal", jnl);  
                data.putExtras(bundle);  
                setResult(2, data);  
                finish(); 
            }
    		else if(v.getId() == R.id.buttonAdd){  
                Log.d("test", "cansal button ---> click");
                Intent intent = new Intent();
                intent.putExtra("pic", pic);
                intent.putExtra("record", record);
                intent.putExtra("video", video);
               	intent.setClass(EditActivity.this, MediaActivity.class); 
       			EditActivity.this.startActivityForResult(intent, 1);
            }
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.buttonUpload){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonUpload.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonUpload.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	        }			
			if(v.getId() == R.id.buttonAdd){  
                if(event.getAction() == MotionEvent.ACTION_UP){  
                    Log.d("test", "cansal button ---> cancel");  
                    buttonAdd.setBackgroundResource(R.drawable.button_add);  
                }   
                if(event.getAction() == MotionEvent.ACTION_DOWN){  
                    Log.d("test", "cansal button ---> down");  
                    buttonAdd.setBackgroundResource(R.drawable.button_add_pressed);  
                }  
        }
			return false;
		}
    }    
}