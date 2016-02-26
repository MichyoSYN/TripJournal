package michyo.welcome;
  
import java.util.List;  
import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.TextView;  
public class MyJournalAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private Bitmap mPic; 
	private List<String> items;  
	private List<String> paths; 
	
	public MyJournalAdapter(Context context,List<String> it,List<String> pa){  
		mInflater = LayoutInflater.from(context);  
		items = it;  
		paths = pa;  
	}  
    
	public int getCount(){  
		return items.size();  
	}  
	public Object getItem(int position){  
		return items.get(position);  
	}      
	public long getItemId(int position){  
		return position;  
	}      
	public View getView(int position,View convertView,ViewGroup parent){  
		ViewHolder holder;      
		if(convertView == null){  
			convertView = mInflater.inflate(R.layout.jnlrow, null);  
			holder = new ViewHolder();  
			holder.text = (TextView) convertView.findViewById(R.id.text);  
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);  
        
			convertView.setTag(holder);  
		}  
		else{  
			holder = (ViewHolder) convertView.getTag();  
		}  
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 50;//图片宽高都为原来的五十分之一，即图片为原来的二千五百分之一 
		mPic = BitmapFactory.decodeFile(items.get(position).toString().trim(), opts);    
        holder.icon.setImageBitmap(mPic);  
        //mPic.recycle();
		holder.text.setText(paths.get(position).toString().trim());

		return convertView;  
  }  
	
  private class ViewHolder{  
    TextView text;  
    ImageView icon;  
  }  
}  