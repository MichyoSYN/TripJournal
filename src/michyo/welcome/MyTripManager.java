package michyo.welcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyTripManager extends ListActivity {
	private User user;
    private List<String> paths = null;
    private Vector<String> trip = new Vector<String>();
	private TextView mTitle;  
    private String location = "";
    
	@Override  
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.tripselect);  
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
        SQLite dbOp = new SQLite(MyTripManager.this);
        Vector<Journal> journal = dbOp.selectJournalByAuthor(user);
        for(int i = 0; i < journal.size(); i++){
        	trip.add(journal.get(i).getLocation());
        }
        
        mTitle = (TextView) findViewById(R.id.mTitle); 
        mTitle.setText("选择一个旅程吧~");
        
        final ArrayList<String> pictures = new ArrayList<String>();
        final ArrayList<String> titles = new ArrayList<String>();
        final ArrayList<String> dates = new ArrayList<String>();
        final ArrayList<String> locations = new ArrayList<String>();
        final ArrayList<String> contents = new ArrayList<String>();
        final ArrayList<Integer> jids = new ArrayList<Integer>();
        final ArrayList<String> shows = new ArrayList<String>();
        
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);         
        buttonConfirm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	if(!location.equals("")){
            		SQLite dbOp = new SQLite(MyTripManager.this);
            		Vector<Journal> findJournal = dbOp.selectJournalByPlace(user, location);
            		if(!findJournal.isEmpty()){
                		for(int i = 0; i < findJournal.size(); i++){
                			Journal temp = findJournal.get(i);
                			jids.add(temp.getJid());
                			pictures.add(temp.getPicture());
                			titles.add(temp.getTitle());
                			dates.add(temp.getDate());
                			locations.add(temp.getLocation());
                			contents.add(temp.getContent());
                			shows.add(temp.getTitle() + " " + temp.getDate() + "\n@" + temp.getLocation()
                					+ "\n" + temp.getContent());
                		}
                		
                		Intent intent = new Intent();
                    	intent.setClass(MyTripManager.this, MyJournalManager.class);
                    	intent.putStringArrayListExtra("pictures", pictures);
                    	intent.putStringArrayListExtra("titles", titles);
                    	intent.putStringArrayListExtra("dates", dates);
                    	intent.putStringArrayListExtra("locations", locations);
                    	intent.putStringArrayListExtra("contents", contents);
                    	intent.putIntegerArrayListExtra("jids", jids);
                    	intent.putStringArrayListExtra("shows", shows);
                    	MyTripManager.this.startActivity(intent); 
                    	MyTripManager.this.finish();
                	}
                	else{
                		Toast.makeText(MyTripManager.this, "没有您所要查找的信息!", Toast.LENGTH_SHORT).show(); 
                	}  
            	}
            	else{
            		Toast.makeText(MyTripManager.this, "童鞋，先要选择一个旅程哦~", Toast.LENGTH_SHORT).show();
            	}
                /*Intent intent = new Intent(MyTripManager.this, LookActivity.class);  
                intent.putExtra("chosedJournal", chosedJournal);
                MyTripManager.this.startActivity(intent);
                MyTripManager.this.finish(); */
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancle);  
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                finish();  
            }  
        });  

        paths = trip; 
        setListAdapter(new MyTripAdapter(this, paths));  
    }  
    
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) { 
    	mTitle.setText(trip.get(position));
    	location = trip.get(position);
    }
}
