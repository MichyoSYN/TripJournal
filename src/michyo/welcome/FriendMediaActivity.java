package michyo.welcome;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
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

public class FriendMediaActivity extends Activity{      
    private String pic;
    private String video;
    private String record;
    private TextView picPath;
    private TextView videoPath;
    private TextView recordPath;
    private ImageView iv;
    private ImageView ivR;
    private ImageView ivV;
    private Button btCon;
    private Button btCan;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.media);  
        
        pic = this.getIntent().getStringExtra("pic");
        video = this.getIntent().getStringExtra("video");
        record = this.getIntent().getStringExtra("record");        
        
        Button button = (Button)findViewById(R.id.buttonMedia);
        button.setVisibility(View.INVISIBLE);
        
        picPath = (TextView)findViewById(R.id.picPath);
        videoPath = (TextView)findViewById(R.id.videoPath);
        recordPath = (TextView)findViewById(R.id.recordPath);
        iv = (ImageView)findViewById(R.id.pic);
        ivV = (ImageView)findViewById(R.id.video);
        ivR = (ImageView)findViewById(R.id.record);
        
        Typeface fontFaceE = Typeface.createFromAsset(getAssets(), "fonts/english_font.ttf"); 
        TextPaint paint = picPath.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        picPath.setTypeface(fontFaceE);//设置英文字体         
        
        paint = videoPath.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        videoPath.setTypeface(fontFaceE);//设置英文字体      
        
        paint = recordPath.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        recordPath.setTypeface(fontFaceE);//设置英文字体     

        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF"); 
        TextView txCon=(TextView)findViewById(R.id.textViewConfirm);
        paint = txCon.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txCon.setTypeface(fontFace);//设置中文字体      
        TextView txCan=(TextView)findViewById(R.id.textViewCancel);
        paint = txCan.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txCan.setTypeface(fontFace);//设置中文字体     
        
        ButtonListener blCon = new ButtonListener();      
        btCon = (Button) findViewById(R.id.buttonConfirmMedia);
        btCon.setOnClickListener(blCon);
        btCon.setOnTouchListener(blCon);
        
        ButtonListener blCan = new ButtonListener();      
        btCan = (Button) findViewById(R.id.buttonCancelMedia);
        btCan.setOnClickListener(blCan);
        btCan.setOnTouchListener(blCan);
        
        picPath.setText("图片文件为：" + pic);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 8;//图片宽高都为原来的八分之一，即图片为原来的六十四分之一 
        Bitmap bit = BitmapFactory.decodeFile(pic, opts);                 
        iv.setImageBitmap(bit);
        iv.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
            	if(!pic.equals("")){
            		File file = new File(pic); 
            		Intent intent = new Intent();  
            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            		intent.setAction(android.content.Intent.ACTION_VIEW);  
            		intent.setDataAndType(Uri.fromFile(file), "image/*");  
            		startActivity(intent);   
            	}
            	else{
            		Toast.makeText(FriendMediaActivity.this, "啊偶，还木有图片呢", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
        
        videoPath.setText("视频文件为：" + video);
		ivV.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) { 
            	if(!video.equals("")){
            		File file = new File(video); 
            		Intent intent = new Intent();  
            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            		intent.setAction(android.content.Intent.ACTION_VIEW);  
            		intent.setDataAndType(Uri.fromFile(file), "video/*");  
            		startActivity(intent); 
            	}
            	else{
            		Toast.makeText(FriendMediaActivity.this, "啊偶，还木有视频呢", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
		
		recordPath.setText("声音文件为：" + record);
		ivR.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) { 
            	if(!record.equals("")){
            		File file = new File(record); 
            		Intent intent = new Intent();  
            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            		intent.setAction(android.content.Intent.ACTION_VIEW);  
            		intent.setDataAndType(Uri.fromFile(file), "audio/*");  
            		startActivity(intent);
            	}
            	else{
            		Toast.makeText(FriendMediaActivity.this, "啊偶，还木有录音呢", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
        
        button.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
                finish();  
            }  
        });  
    }  
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonConfirmMedia){  
                Log.d("test", "cansal button ---> click");
            } 
    		else if(v.getId() == R.id.buttonCancelMedia){
    			Log.d("test", "cansal button ---> click");	
    		}   
    		finish();
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			 if(v.getId() == R.id.buttonConfirmMedia){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    btCon.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    btCon.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	            }
			 else if(v.getId() == R.id.buttonCancelMedia){
				 if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    btCan.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    btCan.setBackgroundResource(R.drawable.button_add_pressed);  
	             }
			 }
			return false;
		}
    }    
}  