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

public class ReadActivity extends Activity{
	private EditText etTitle;
	private EditText etLocation;
	private EditText etContent;
	private TextView media;
	private Journal show;
	private Button buttonUpdate;
	private Button buttonAlter;
	private String pic = "";
	private String record = "";
	private String video = "" ;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look);
        
        show = (Journal)this.getIntent().getSerializableExtra("chosedFriendJournal");
        
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
        TextView txT = (TextView)findViewById(R.id.textViewTitleL);
        TextPaint paint = txT.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txT.setTypeface(fontFace);//设置中文字体       
        TextView txL = (TextView)findViewById(R.id.textViewLocationL);
        paint = txL.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txL.setTypeface(fontFace);//设置中文字体       
        TextView txU = (TextView)findViewById(R.id.textViewUpdate);
        paint = txU.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txU.setTypeface(fontFace);//设置中文字体
        txU.setText("返回");
        
        TextView txA=(TextView)findViewById(R.id.textViewAlter);
        paint = txA.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txA.setTypeface(fontFace);//设置中文字体
        /*txA.setVisibility(View.GONE);*/
        
        etTitle.setFocusable(false);
    	etLocation.setFocusable(false);
    	etContent.setFocusable(false);
        
        ButtonListener blUpdate = new ButtonListener();      
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(blUpdate);
        buttonUpdate.setOnTouchListener(blUpdate); 
        
        ButtonListener blAlter = new ButtonListener(); 
        buttonAlter = (Button) findViewById(R.id.buttonAlter);
        buttonAlter.setOnClickListener(blAlter);
        buttonAlter.setOnTouchListener(blAlter); 
        /*buttonAlter.setVisibility(View.GONE);*/
	}
	
    class ButtonListener implements OnClickListener,OnTouchListener{    	
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonUpdate){  
                Log.d("test", "cansal button ---> click");
                //Toast.makeText(ReadActivity.this, "返回", Toast.LENGTH_SHORT).show(); 
                ReadActivity.this.finish();
            }
    		else if(v.getId() == R.id.buttonAlter){  
                Log.d("test", "cansal button ---> click");
                //Toast.makeText(ReadActivity.this, "返回", Toast.LENGTH_SHORT).show(); 
                Intent intent = new Intent();
                intent.putExtra("pic", pic);
                intent.putExtra("record", record);
                intent.putExtra("video", video);
               	intent.setClass(ReadActivity.this, FriendMediaActivity.class); 
               	ReadActivity.this.startActivity(intent);  
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
			else if(v.getId() == R.id.buttonAlter){  
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
