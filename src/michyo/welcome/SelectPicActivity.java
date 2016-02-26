package michyo.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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

public class SelectPicActivity extends Activity{  
      
    public static final int FILE_RESULT_CODE = 1;
    
    private String path;  
    private TextView textView;  
    private ImageView show;
    private Button btCon;
    private Button btCan;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.selectpic);  
        
        Bitmap bit = BitmapFactory.decodeFile("");
        show = (ImageView) findViewById(R.id.imageViewShow);
        show.setImageBitmap(bit);
        
        Button button = (Button)findViewById(R.id.buttonSelect);  
        textView = (TextView)findViewById(R.id.fileText);
        
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");  
        TextPaint paint = textView.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        textView.setTypeface(fontFace);//设置中文字体             
        TextView txCon=(TextView)findViewById(R.id.textViewConfirm);
        paint = txCon.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txCon.setTypeface(fontFace);//设置中文字体      
        TextView txCan=(TextView)findViewById(R.id.textViewCancel);
        paint = txCan.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        txCan.setTypeface(fontFace);//设置中文字体     
        
        ButtonListener blCon = new ButtonListener();      
        btCon = (Button) findViewById(R.id.buttonConfirmPic);
        btCon.setOnClickListener(blCon);
        btCon.setOnTouchListener(blCon);
        
        ButtonListener blCan = new ButtonListener();      
        btCan = (Button) findViewById(R.id.buttonCancelPic);
        btCan.setOnClickListener(blCan);
        btCan.setOnTouchListener(blCan);
        
        Intent intent = new Intent(SelectPicActivity.this, MyFileManager.class);  
        startActivityForResult(intent, FILE_RESULT_CODE); 
        
        button.setOnClickListener(new OnClickListener() {                
            public void onClick(View v) {  
                Intent intent = new Intent(SelectPicActivity.this, MyFileManager.class);  
                startActivityForResult(intent, FILE_RESULT_CODE);  
            }  
        });  
    }  
      
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        /*Bitmap bit = BitmapFactory.decodeFile("");
        show = (ImageView) findViewById(R.id.imageViewShow);
        show.setImageBitmap(bit); */
        
        if(FILE_RESULT_CODE == requestCode){  
            Bundle bundle = null;  
            if(data != null && (bundle = data.getExtras()) != null){  
                textView.setText("选择的文件为：" + bundle.getString("file"));
                path = bundle.getString("file");              

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 8;//图片宽高都为原来的八分之一，即图片为原来的六十四分之一 
                Bitmap bit = BitmapFactory.decodeFile(bundle.getString("file"), opts);                 
                show.setImageBitmap(bit);  
                //bit.recycle();
            }  
        }  
    } 
    
    class ButtonListener implements OnClickListener,OnTouchListener{
    	public void onClick(View v){
    		if(v.getId() == R.id.buttonConfirmPic){  
                Log.d("test", "cansal button ---> click");
            } 
    		else if(v.getId() == R.id.buttonCancelPic){
    			Log.d("test", "cansal button ---> click");
    			path = "";
    		}   
    		
    		Intent data = new Intent(SelectPicActivity.this, EditActivity.class);  
            Bundle bundle = new Bundle();  
            bundle.putString("file", path);  
            data.putExtras(bundle);  
            setResult(2, data);  
            
            finish();   
    	}

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			 if(v.getId() == R.id.buttonConfirmPic){  
	                if(event.getAction() == MotionEvent.ACTION_UP){  
	                    Log.d("test", "cansal button ---> cancel");  
	                    btCon.setBackgroundResource(R.drawable.button_add);  
	                }   
	                if(event.getAction() == MotionEvent.ACTION_DOWN){  
	                    Log.d("test", "cansal button ---> down");  
	                    btCon.setBackgroundResource(R.drawable.button_add_pressed);  
	                }  
	            }
			 else if(v.getId() == R.id.buttonCancelPic){
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