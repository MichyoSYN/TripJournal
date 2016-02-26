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

public class MediaActivity extends Activity{      
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
    private String type;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.media);  
        
        pic = this.getIntent().getStringExtra("pic");
        video = this.getIntent().getStringExtra("video");
        record = this.getIntent().getStringExtra("record");        
        
        Button button = (Button)findViewById(R.id.buttonMedia);  
        picPath = (TextView)findViewById(R.id.picPath);
        videoPath = (TextView)findViewById(R.id.videoPath);
        recordPath = (TextView)findViewById(R.id.recordPath);
        iv = (ImageView)findViewById(R.id.pic);
        ivV = (ImageView)findViewById(R.id.video);
        ivR = (ImageView)findViewById(R.id.record);
        
        Typeface fontFaceE = Typeface.createFromAsset(getAssets(), "fonts/english_font.ttf"); 
        TextPaint paint = picPath.getPaint();
        paint.setFakeBoldText(true); //���ô���
        picPath.setTypeface(fontFaceE);//����Ӣ������         
        picPath.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
                Intent intent = new Intent(MediaActivity.this, MyMediaManager.class);  
                startActivityForResult(intent, 2);  
            }  
        });
        
        paint = videoPath.getPaint();
        paint.setFakeBoldText(true); //���ô���
        videoPath.setTypeface(fontFaceE);//����Ӣ������      
        videoPath.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
            	Intent intent = new Intent(MediaActivity.this, MyMediaManager.class);  
                startActivityForResult(intent, 3);   
            }  
        });
        
        paint = recordPath.getPaint();
        paint.setFakeBoldText(true); //���ô���
        recordPath.setTypeface(fontFaceE);//����Ӣ������     
        recordPath.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
            	Intent intent = new Intent(MediaActivity.this, MyMediaManager.class);  
                startActivityForResult(intent, 4);   
            }  
        });

        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF"); 
        TextView txCon=(TextView)findViewById(R.id.textViewConfirm);
        paint = txCon.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txCon.setTypeface(fontFace);//������������      
        TextView txCan=(TextView)findViewById(R.id.textViewCancel);
        paint = txCan.getPaint();
        paint.setFakeBoldText(true); //���ô���
        txCan.setTypeface(fontFace);//������������     
        
        ButtonListener blCon = new ButtonListener();      
        btCon = (Button) findViewById(R.id.buttonConfirmMedia);
        btCon.setOnClickListener(blCon);
        btCon.setOnTouchListener(blCon);
        
        ButtonListener blCan = new ButtonListener();      
        btCan = (Button) findViewById(R.id.buttonCancelMedia);
        btCan.setOnClickListener(blCan);
        btCan.setOnTouchListener(blCan);
        
        picPath.setText("ͼƬ�ļ�Ϊ��" + pic);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 8;//ͼƬ��߶�Ϊԭ���İ˷�֮һ����ͼƬΪԭ������ʮ�ķ�֮һ 
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
            		Toast.makeText(MediaActivity.this, "��ż����ľ��ͼƬ��", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
        
        videoPath.setText("��Ƶ�ļ�Ϊ��" + video);
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
            		Toast.makeText(MediaActivity.this, "��ż����ľ����Ƶ��", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
		
		recordPath.setText("�����ļ�Ϊ��" + record);
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
            		Toast.makeText(MediaActivity.this, "��ż����ľ��¼����", Toast.LENGTH_SHORT).show();
            	}
            }  
        }); 
        
        Intent intent = new Intent(MediaActivity.this, MyMediaManager.class);  
        startActivityForResult(intent, 1); 
        
        button.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
                Intent intent = new Intent(MediaActivity.this, MyMediaManager.class);  
                startActivityForResult(intent, 1);  
            }  
        });  
    }  
      
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {         
        if(1 == requestCode){  
            if(data != null){  
            	type = data.getStringExtra("mediaType");
            	String path = data.getStringExtra("file").trim(); 

            	if(type.equals("pic")){
            		pic = path;
            		picPath.setText("ͼƬ�ļ�Ϊ��" + path);
            		BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 8;//ͼƬ��߶�Ϊԭ���İ˷�֮һ����ͼƬΪԭ������ʮ�ķ�֮һ 
                    Bitmap bit = BitmapFactory.decodeFile(path, opts);                 
                    iv.setImageBitmap(bit);
            	}
            	else if(type.equals("record")){
            		record = path;
            		recordPath.setText("�����ļ�Ϊ��" + path);
            	}
            	else if(type.equals("video")){
            		video = path;
            		videoPath.setText("��Ƶ�ļ�Ϊ��" + path);
            	}
            }  
        } 
        if(2 == requestCode){  
            if(data != null){  
            	type = data.getStringExtra("mediaType");
            	String path = data.getStringExtra("file").trim(); 

            	if(type.equals("pic")){
            		pic = path;
            		picPath.setText("ͼƬ�ļ�Ϊ��" + path);
            		BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 8;//ͼƬ��߶�Ϊԭ���İ˷�֮һ����ͼƬΪԭ������ʮ�ķ�֮һ 
                    Bitmap bit = BitmapFactory.decodeFile(path, opts);                 
                    iv.setImageBitmap(bit);
            	}
            	else if(type.equals("record") || type.equals("video")){
            		Toast.makeText(MediaActivity.this, "��ż��Ҫ��ͼƬ����Ŷ������ʧ����> <", Toast.LENGTH_SHORT).show();
            	}
            }  
        } 
        if(3 == requestCode){  
            if(data != null){  
            	type = data.getStringExtra("mediaType");
            	String path = data.getStringExtra("file").trim(); 

            	if(type.equals("video")){
            		video = path;
            		videoPath.setText("��Ƶ�ļ�Ϊ��" + path);
            	}
            	else if(type.equals("record") || type.equals("pic")){
            		Toast.makeText(MediaActivity.this, "��ż��Ҫ����Ƶ����Ŷ������ʧ����> <", Toast.LENGTH_SHORT).show();
            	}
            }  
        } 
        if(4 == requestCode){  
            if(data != null){  
            	type = data.getStringExtra("mediaType");
            	String path = data.getStringExtra("file").trim(); 

            	if(type.equals("record")){
            		record = path;
            		recordPath.setText("�����ļ�Ϊ��" + record);
            	}
            	else if(type.equals("video") || type.equals("pic")){
            		Toast.makeText(MediaActivity.this, "��ż��Ҫ��¼������Ŷ������ʧ����> <", Toast.LENGTH_SHORT).show();
            	}
            }  
        } 
    } 
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonConfirmMedia){  
                Log.d("test", "cansal button ---> click");
            } 
    		else if(v.getId() == R.id.buttonCancelMedia){
    			Log.d("test", "cansal button ---> click");
    			if(type.equals("pic")){
    				pic = "";
    			}
    			else if(type.equals("record")){
    				record = "";
    			}
    			else if(type.equals("video")){
    				video = "";
    			} 			
    		}   
    		
    		Intent data = new Intent(MediaActivity.this, EditActivity.class);  
            Bundle bundle = new Bundle();  
            bundle.putString("pic", pic);
            bundle.putString("record", record);
            bundle.putString("video", video);
            data.putExtras(bundle);  
            setResult(2, data);              
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