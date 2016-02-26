package michyo.welcome;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

//创建类，实现overlay
//一个OverlayItem对象代表一个地图上标记
public class FirstOverlay extends ItemizedOverlay<OverlayItem> {

	//创建一个list，用于持有该图层当中所有的标记对象
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public FirstOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

	}

	//defaultMarker 指定标记所使用的默认图片
	public FirstOverlay(Drawable defaultMarker, Context context) {
		//必须调用父类的构造函数
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	//创建OverlayItem对象
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	//返回当前Overlay所包含OverlayItem对象
	@Override
	public int size() {
		return mOverlays.size();
	}
	
	//将生成OverlayItem对象添加到list中
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	//当用户点击标记时，执行
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
