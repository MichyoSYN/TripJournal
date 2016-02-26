package michyo.welcome;

import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
import android.app.ListActivity;  
import android.content.Intent;  
import android.net.Uri;  
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.Window;  
import android.widget.Button;  
import android.widget.ListView;  
import android.widget.TextView;  
import android.widget.Toast;
public class MyMediaManager extends ListActivity {  
    private List<String> items = null;  
    private List<String> paths = null;  
    private String rootPath = "/sdcard";  
    private String curPath = "/";  
    private TextView mPath;  
    private String mediaType = "";
    private String filePath = "";
    
    @Override  
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.fileselect);  
        mPath = (TextView) findViewById(R.id.mPath);  
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);  
        buttonConfirm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {              	
            	Intent intent = new Intent(MyMediaManager.this, MediaActivity.class);  
            	intent.putExtra("file", filePath); 
            	intent.putExtra("mediaType", mediaType); 
                setResult(2, intent);  
                finish();
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancle);  
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) { 
            	Intent intent = new Intent(MyMediaManager.this, MediaActivity.class);  
            	intent.putExtra("file", filePath); 
            	intent.putExtra("mediaType", mediaType); 
                setResult(2, intent); 
                finish();  
            }  
        });  
        getFileDir(rootPath);  
    }  
    private void getFileDir(String filePathP) {  
        mPath.setText(filePathP);  
        items = new ArrayList<String>();  
        paths = new ArrayList<String>();  
        File f = new File(filePathP);  
        File[] files = f.listFiles();  
        if (!filePathP.equals(rootPath)) {  
            items.add("b1");  
            paths.add(rootPath);  
            items.add("b2");  
            paths.add(f.getParent());  
        }  
        for (int i = 0; i < files.length; i++) {  
            File file = files[i];  
            items.add(file.getName());  
            paths.add(file.getPath());  
        }  
        setListAdapter(new MyAdapter(this, items, paths));  
    }  
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) {  
        File file = new File(paths.get(position));  
        if (file.isDirectory()) {  
            curPath = paths.get(position);  
            getFileDir(paths.get(position));  
        } 
        else {  
            //可以打开文件  
        	String type = getMIMEType(file);
        	if(type.equals("image/*")){
        		Toast.makeText(MyMediaManager.this, "乃选择一个图片文件~", Toast.LENGTH_SHORT).show();
        		openFile(file);  
        		
        		mediaType = "pic";
        		filePath = curPath + "/" + file.getName();
        		mPath.setText(filePath);
        		
            	Intent intent = new Intent(MyMediaManager.this, MediaActivity.class);  
            	intent.putExtra("file", filePath); 
            	intent.putExtra("mediaType", mediaType); 
                setResult(2, intent);  
                finish();
        	}
        	else if(type.equals("audio/*")){
        		Toast.makeText(MyMediaManager.this, "乃选择一个声音文件~", Toast.LENGTH_SHORT).show();
        		openFile(file);    
        		
        		mediaType = "record";
        		filePath = curPath + "/" + file.getName();  
        		mPath.setText(filePath);

        		Intent intent = new Intent(MyMediaManager.this, MediaActivity.class);  
            	intent.putExtra("file", filePath); 
            	intent.putExtra("mediaType", mediaType); 
                setResult(2, intent);  
                finish();
        	}
        	else if(type.equals("video/*")){
        		Toast.makeText(MyMediaManager.this, "乃选择一个视频文件~", Toast.LENGTH_SHORT).show();
        		openFile(file);
        		
        		mediaType = "video";
        		filePath = curPath + "/" + file.getName();
        		mPath.setText(filePath);

        		Intent intent = new Intent(MyMediaManager.this, MediaActivity.class);  
            	intent.putExtra("file", filePath); 
            	intent.putExtra("mediaType", mediaType); 
                setResult(2, intent);  
                finish();
        	}
        	else{
        		Toast.makeText(MyMediaManager.this, "啊偶，乃选择的不是多媒体文件呢，重新选一个吧~", Toast.LENGTH_SHORT).show(); 
        	}         	
        }  
    }      
    private void openFile(File f) {  
        Intent intent = new Intent();  
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        intent.setAction(android.content.Intent.ACTION_VIEW);  
        String type = getMIMEType(f);  
        intent.setDataAndType(Uri.fromFile(f), type);  
        startActivity(intent);  
    }  
    private String getMIMEType(File f) {  
        String type = "";  
        String fName = f.getName();  
        String end = fName  
                .substring(fName.lastIndexOf(".") + 1, fName.length())  
                .toLowerCase();  
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")  
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {  
            type = "audio";  
        } else if (end.equals("3gp") || end.equals("mp4")) {  
            type = "video";  
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")  
                || end.equals("jpeg") || end.equals("bmp")) {  
            type = "image";  
        } else {  
            type = "*";  
        }  
        type += "/*";  
        return type;  
    }  
}  