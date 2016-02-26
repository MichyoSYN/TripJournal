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

public class LookActivity extends Activity{
	private EditText etTitle;
	private EditText etLocation;
	private EditText etContent;
	private TextView tv;
	private TextView media;
	private Journal show;
	private Button buttonUpdate;
	private Button buttonAlter;
	private boolean flag = false;
	private String pic = "";
	private String record = "";
	private String video = "" ;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look);
        
        show = (Journal)this.getIntent().getSerializableExtra("chosedJournal");
        
        tv = (TextView) findViewById(R.id.textViewUpdate);        		
        etTitle = (EditText) findViewById(R.id.editTextTitleL);
        etLocation = (EditText) findViewById(R.id.editTextLocationL);
        etContent = (EditText) findViewById(R.id.editTextContentL);
        media = (TextView) findViewById(R.id.textViewMediaL);
        
        etTitle.setText(show.getTitle());
        etLocation.setText(show.getLocation());
        etContent.setText(show.getContent());
        
        pic = show.getPicture();
        record = show.getRecord();
        video = show.getVideo();
        String mediaDiscribe = "图片为" + pic + "\n录音为" + record + "\n视频为" + video;
        media.setText(mediaDiscribe);
        
        //设置textView字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        TextView txT=(TextView)findViewById(R.id.textViewTitleL);
        TextPaint paint = txT.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txT.setTypeface(fontFace);//设置中文字体       
        TextView txL=(TextView)findViewById(R.id.textViewLocationL);
        paint = txL.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txL.setTypeface(fontFace);//设置中文字体       
        TextView txU=(TextView)findViewById(R.id.textViewUpdate);
        paint = txU.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txU.setTypeface(fontFace);//设置中文字体
        TextView txA=(TextView)findViewById(R.id.textViewAlter);
        paint = txA.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txA.setTypeface(fontFace);//设置中文字体
        
        etTitle.setFocusable(false);
    	etLocation.setFocusable(false);
    	etContent.setFocusable(false);
    	tv.setText("编辑");
    	
        ButtonListener blUpdate = new ButtonListener();      
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(blUpdate);
        buttonUpdate.setOnTouchListener(blUpdate); 
        
        ButtonListener blAlter = new ButtonListener();
        buttonAlter = (Button) findViewById(R.id.buttonAlter);
        buttonAlter.setOnClickListener(blAlter);
        buttonAlter.setOnTouchListener(blAlter); 
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
                media.setText(mediaDescribe);
            }  
        }  
    } 
	
    class ButtonListener implements OnClickListener,OnTouchListener{    	
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonUpdate){  
                Log.d("test", "cansal button ---> click");
                if(flag){            	
                	show.setTitle(etTitle.getText().toString().trim());
                	show.setLocation(etLocation.getText().toString().trim());
                	show.setContent(etContent.getText().toString().trim());
                	show.setPicture(pic.trim());
                	show.setRecord(record.trim());
                	show.setVideo(video.trim());
                	
                	SQLite dbOp = new SQLite(LookActivity.this);
                    dbOp.updateJournal(show, "title", etTitle.getText().toString().trim());
                    dbOp.updateJournal(show, "location", etLocation.getText().toString().trim());
                    dbOp.updateJournal(show, "content", etContent.getText().toString().trim());
                    dbOp.updateJournal(show, "picture", pic);
                    dbOp.updateJournal(show, "record", record);
                    dbOp.updateJournal(show, "video", video);
                    Toast.makeText(LookActivity.this, "成功在本地更新本日志!", Toast.LENGTH_SHORT).show(); 
                    LookActivity.this.finish();
                }
                else{
                	etTitle.setFocusable(true);
                	etLocation.setFocusable(true);
                	etContent.setFocusable(true);
                	etTitle.setFocusableInTouchMode(true);
                	etLocation.setFocusableInTouchMode(true);
                	etContent.setFocusableInTouchMode(true);
                	tv.setText("保存");
                	flag = true;
                }
                Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
                TextPaint paint = tv.getPaint();
                paint.setFakeBoldText(true); //设置粗体
                tv.setTypeface(fontFace);//设置中文字体       
            }
    		else if(v.getId() == R.id.buttonAlter){  
                Log.d("test", "cansal button ---> click");
            	
                Intent intent = new Intent();
                intent.putExtra("pic", pic);
                intent.putExtra("record", record);
                intent.putExtra("video", video);
               	intent.setClass(LookActivity.this, MediaActivity.class); 
               	LookActivity.this.startActivityForResult(intent, 1);               
            }
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.buttonUpdate){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    buttonUpdate.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    buttonUpdate.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	        }			
			if(v.getId() == R.id.buttonAlter){  
                if(event.getAction() == MotionEvent.ACTION_UP){  
                    Log.d("test", "cansal button ---> cancel");  
                    buttonAlter.setBackgroundResource(R.drawable.button_add);  
                }   
                if(event.getAction() == MotionEvent.ACTION_DOWN){  
                    Log.d("test", "cansal button ---> down");  
                    buttonAlter.setBackgroundResource(R.drawable.button_add_pressed);  
                }  
        }
			return false;
		}
    }  
}
