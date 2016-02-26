package michyo.welcome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FindActivity extends Activity {
	private Button find;
	private RadioGroup rg;  
	private RadioButton rbAll;
	private RadioButton rbSome;
	private CheckBox cbDate;
	private CheckBox cbPlace;
	private EditText etPlace;
	private DatePicker dpDate;
	private int flagR = 1;
	private int flagC = 0;
	private String chosedDate;
	private String chosedPlace;
	private User user;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        
        user = (User)this.getIntent().getSerializableExtra("user");
        
        final ArrayList<String> pictures = new ArrayList<String>();
        final ArrayList<String> titles = new ArrayList<String>();
        final ArrayList<String> dates = new ArrayList<String>();
        final ArrayList<String> locations = new ArrayList<String>();
        final ArrayList<String> contents = new ArrayList<String>();
        final ArrayList<Integer> jids = new ArrayList<Integer>();
        final ArrayList<String> shows = new ArrayList<String>();
        final ArrayList<String> videos = new ArrayList<String>();
        final ArrayList<String> records = new ArrayList<String>();
        
        rg = (RadioGroup) findViewById(R.id.radiogroup);  
        rbAll = (RadioButton) findViewById(R.id.radioButtonAll);
        rbSome = (RadioButton) findViewById(R.id.radioButtonSome);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                // TODO Auto-generated method stub  
                if(checkedId == rbAll.getId()) {  
                    flagR = 1;  
                }
                else if(checkedId == rbSome.getId()) {  
                    flagR = 2;  
                } 
                //Toast.makeText(FindActivity.this, Integer.toString(flagR), Toast.LENGTH_SHORT).show(); 
            }  
        });  
        
        cbDate = (CheckBox) findViewById(R.id.checkBoxTime);
        cbPlace = (CheckBox) findViewById(R.id.checkBoxPlace);
        etPlace = (EditText) findViewById(R.id.editTextPlace);
        
        Calendar c = Calendar.getInstance(); 
        dpDate = (DatePicker) findViewById(R.id.datePickerDate);
        dpDate.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c  
                .get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {  
  
            public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {  
                chosedDate = arg0.getYear() + "-" + (arg0.getMonth() + 1)  
                        + "-" + arg0.getDayOfMonth();    
            }    
        });  
        
        find = (Button) findViewById(R.id.buttonFind);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(flagR == 2){
            		if(cbDate.isChecked() && cbPlace.isChecked()){
            			flagC = 1;
            		}
            		else if(cbPlace.isChecked()){
            			flagC = 2;
            		}
            		else if(cbDate.isChecked()){
            			flagC = 3;
            		}
            		else{
            			flagC = 0;
            		}
            	}
            	
            	chosedDate = dpDate.getYear() + "-" + (dpDate.getMonth() + 1)  
                        + "-" + dpDate.getDayOfMonth();    
            	chosedPlace = etPlace.getText().toString().trim();
            	
            	SQLite dbOp = new SQLite(FindActivity.this);
            	Vector<Journal> findJournal = new Vector<Journal>();
            	
            	//Toast.makeText(FindActivity.this, Integer.toString(flagC), Toast.LENGTH_SHORT).show(); 
            	if(flagR == 1){
            		findJournal = dbOp.selectJournalByAuthor(user);
            	}
            	else if(flagR == 2){
            		if(flagC == 1){
            			findJournal = dbOp.selectJournalByDateAndPlace(user, chosedPlace, chosedDate);
            		}
            		else if(flagC == 2){
            			findJournal = dbOp.selectJournalByPlace(user, chosedPlace);
            		}
            		else if(flagC == 3){
            			findJournal = dbOp.selectJournalByDate(user, chosedDate);
            		}
            		else if(flagC == 0){
            			findJournal = dbOp.selectJournalByAuthor(user);
            		}
            	}
            	
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
            			videos.add(temp.getVideo());
            			records.add(temp.getRecord());
            		}
            		
            		Intent intent = new Intent();
                	intent.setClass(FindActivity.this, MyJournalManager.class);
                	intent.putStringArrayListExtra("pictures", pictures);
                	intent.putStringArrayListExtra("titles", titles);
                	intent.putStringArrayListExtra("dates", dates);
                	intent.putStringArrayListExtra("locations", locations);
                	intent.putStringArrayListExtra("contents", contents);
                	intent.putIntegerArrayListExtra("jids", jids);
                	intent.putStringArrayListExtra("shows", shows);
                	intent.putStringArrayListExtra("videos", videos);
                	intent.putStringArrayListExtra("records", records);
                	FindActivity.this.startActivity(intent); 
                	FindActivity.this.finish();
            	}
            	else{
            		Toast.makeText(FindActivity.this, "没有您所要查找的信息!", Toast.LENGTH_SHORT).show(); 
            	}            	       	
                return;
            }
        });
    } 
}