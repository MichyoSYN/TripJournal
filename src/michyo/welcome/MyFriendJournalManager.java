package michyo.welcome;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyFriendJournalManager extends ListActivity {
	private List<String> items = null;  
    private List<String> paths = null;
    private ArrayList<String> pictures;
    private ArrayList<String> titles;
	private ArrayList<String> dates;
	private ArrayList<String> locations;
	private ArrayList<String> contents;
	private ArrayList<Integer> jids;
    private ArrayList<String> shows;
    private ArrayList<String> videos;
    private ArrayList<String> records;
	private TextView mTitle;  
    private Journal chosedFriendJournal = new Journal();
    
	@Override  
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.jnlselect);  
        mTitle = (TextView) findViewById(R.id.mTitle);    

        pictures = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("pictures"));
        contents = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("contents"));
        titles = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("titles"));
        locations = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("locations"));
        dates = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("dates"));
        shows = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("shows")); 
        jids = (ArrayList<Integer>) (this.getIntent().getIntegerArrayListExtra("jids"));
        videos = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("videos"));
        records = (ArrayList<String>) (this.getIntent().getStringArrayListExtra("records"));
        
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);         
        buttonConfirm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                Intent intent = new Intent(MyFriendJournalManager.this, ReadActivity.class);  
                intent.putExtra("chosedFriendJournal", chosedFriendJournal);
                MyFriendJournalManager.this.startActivity(intent);
                //MyFriendJournalManager.this.finish();  
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancle); 
        buttonCancle.setText("·µ»Ø");
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
            	MyFriendJournalManager.this.finish();   
            }  
        });  
        items = pictures; 
        paths = shows; 
        setListAdapter(new MyJournalAdapter(this, items, paths));  
    }  
    
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) { 
    	mTitle.setText(titles.get(position) + "  " + dates.get(position));
    	chosedFriendJournal.setContent(contents.get(position));
    	chosedFriendJournal.setTitle(titles.get(position));
    	chosedFriendJournal.setDate(dates.get(position));
    	chosedFriendJournal.setLocation(locations.get(position));
    	chosedFriendJournal.setPicture(pictures.get(position));
    	chosedFriendJournal.setJid(jids.get(position));
    	chosedFriendJournal.setVideo(videos.get(position));
    	chosedFriendJournal.setRecord(records.get(position));
    }
}
