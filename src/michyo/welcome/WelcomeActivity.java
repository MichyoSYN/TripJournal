package michyo.welcome;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.text.TextPaint;

public class WelcomeActivity extends Activity {
	/**进度条的进度*	*/
	static int idx=0;
	/**进度条*	*/
	protected ProgressBar myProgressBar;
	/**提示文本**/
	protected TextView tips;
	private Handler process=new HandlerWelcomeProc(this);
	Timer tr;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        //设置tips字体
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/chinese_font.TTF");          
        tips=(TextView)findViewById(R.id.tips);
        TextPaint paint = tips.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        tips.setTypeface(fontFace);//设置中文字体       

        myProgressBar=(ProgressBar)findViewById(R.id.loading);
        //持续滚动
        tr = new Timer();
        tr.schedule(new TimerTask(){
            @Override
            public void run() {
                WelcomeActivity.idx++;
                process.sendEmptyMessage(WelcomeActivity.idx);
            }
        }, 0,50);        	
    }
}

class HandlerWelcomeProc extends Handler {

    private Activity res;
    
    public HandlerWelcomeProc(Activity aThis) {
         this.res = aThis;
    }
    @Override
    public void handleMessage(Message msg) {
        int prc = msg.what;
        if(prc > 0){
            ((WelcomeActivity)res).tips.setText(res.getString(R.string.welcometips1));
        }
        if(prc > 25){
            ((WelcomeActivity)res).tips.setText(res.getString(R.string.welcometips2));
        }
        if(prc > 50){
            ((WelcomeActivity)res).tips.setText(res.getString(R.string.welcometips3));
        }
        if (prc > 75) {
        	((WelcomeActivity)res).tips.setText(res.getString(R.string.wait));
        }
        if (prc > 100){         
        	if(((WelcomeActivity)res).tr!=null){
                ((WelcomeActivity)res).tr.cancel();
            }  
        	Intent intent = new Intent();
        	intent.setClass(res, LoginActivity.class);
        	res.startActivity(intent);
        	res.finish();
            return;
        }
        ((WelcomeActivity)res).myProgressBar.setProgress(prc);
   }    
}