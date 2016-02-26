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

public class MyJournalManager extends ListActivity {  
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
    private Journal chosedJournal = new Journal();
    
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
                Intent intent = new Intent(MyJournalManager.this, LookActivity.class);  
                intent.putExtra("chosedJournal", chosedJournal);
                MyJournalManager.this.startActivity(intent);
                MyJournalManager.this.finish();  
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancle);  
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                finish();  
            }  
        });  
        items = pictures; 
        paths = shows; 
        setListAdapter(new MyJournalAdapter(this, items, paths));  
    }  
    
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) { 
    	mTitle.setText(titles.get(position) + "  " + dates.get(position));
    	chosedJournal.setContent(contents.get(position));
    	chosedJournal.setTitle(titles.get(position));
    	chosedJournal.setDate(dates.get(position));
    	chosedJournal.setLocation(locations.get(position));
    	chosedJournal.setPicture(pictures.get(position));
    	chosedJournal.setJid(jids.get(position));
    	chosedJournal.setVideo(videos.get(position));
    	chosedJournal.setRecord(records.get(position));
    }
}  