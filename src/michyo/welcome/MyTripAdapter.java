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

public class MyTripAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Bitmap mIcon;  
	private List<String> paths; 
	
	public MyTripAdapter(Context context, List<String> pa){  
		mInflater = LayoutInflater.from(context);  
		paths = pa;  
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 4;//图片宽高都为原来的四分之一，即图片为原来的十六分之一 
		mIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.mark, opts);  
	}  
    
	public int getCount(){  
		return paths.size();  
	}  
	public Object getItem(int position){  
		return paths.get(position);  
	}      
	public long getItemId(int position){  
		return position;  
	}      
	public View getView(int position,View convertView,ViewGroup parent){  
		ViewHolder holder;      
		if(convertView == null){  
			convertView = mInflater.inflate(R.layout.triprow, null);  
			holder = new ViewHolder();  
			holder.text = (TextView) convertView.findViewById(R.id.text);  
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);  
        
			convertView.setTag(holder);  
		}  
		else{  
			holder = (ViewHolder) convertView.getTag();  
		}  
		
        holder.icon.setImageBitmap(mIcon);  
		holder.text.setText(paths.get(position).toString().trim());

		return convertView;  
  }  
	
  private class ViewHolder{  
    TextView text;  
    ImageView icon;  
  }  
}
