package michyo.welcome;
 
import java.util.ArrayList;
import java.util.List;  
import android.app.ListActivity;  
import android.content.Intent;   
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.Window;  
import android.widget.Button;  
import android.widget.ListView;  
import android.widget.TextView;  

public class MyFriendManager extends ListActivity {  
    private List<String> items = null;  
    private List<String> paths = null;
    private ArrayList<Integer> ids;
    private ArrayList<String> names;
    private ArrayList<String> passwords;
    private ArrayList<String> registDates;
    private ArrayList<String> locates;        
    private ArrayList<String> feelings;
    private ArrayList<String> icons;
    private ArrayList<String> infos;
	private TextView mTitle;  
    private User chosedUser = new User();
    
	@Override  
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.friendselect);  
        mTitle = (TextView) findViewById(R.id.mTitle);  

        ids = (ArrayList<Integer>) (this.getIntent().getIntegerArrayListExtra("ids"));
        names = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("names"));
        passwords = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("passwords"));
        registDates = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("registDates"));
        locates = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("locates"));
        feelings = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("feelings"));
        icons = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("icons")); 
        infos = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("infos")); 
        
        
        Button buttonConfirm = (Button) findViewById(R.id.buttonCheckFriend);         
        buttonConfirm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                Intent intent = new Intent(MyFriendManager.this, SelectFriendActivity.class);   
                intent.putExtra("chosedUser", chosedUser);
                setResult(1, intent);   
                MyFriendManager.this.finish();  
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancelFriend);  
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) { 
            	Intent intent = new Intent(MyFriendManager.this, SelectFriendActivity.class); 
            	intent.putExtra("chosedUser", chosedUser);
                setResult(2, intent);   
            	MyFriendManager.this.finish();
            }  
        });  
        items = icons; 
        paths = infos; 
        setListAdapter(new MyFriendAdapter(this, items, paths));  
    }  
    
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) { 
    	mTitle.setText(names.get(position) + "  ¡ª¡ªÓÚ" + registDates.get(position) + "¼ÓÈë");
    	chosedUser.setId(ids.get(position));
    	chosedUser.setUN(names.get(position));
    	chosedUser.setPWD(passwords.get(position));
    	chosedUser.setRegistDate(registDates.get(position));
    	chosedUser.setLocate(locates.get(position));
    	chosedUser.setFeeling(feelings.get(position));
    	chosedUser.setIcon(icons.get(position));
    }
}  