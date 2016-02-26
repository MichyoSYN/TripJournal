package michyo.welcome;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

//�����࣬ʵ��overlay
//һ��OverlayItem�������һ����ͼ�ϱ��
public class FirstOverlay extends ItemizedOverlay<OverlayItem> {

	//����һ��list�����ڳ��и�ͼ�㵱�����еı�Ƕ���
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public FirstOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

	}

	//defaultMarker ָ�������ʹ�õ�Ĭ��ͼƬ
	public FirstOverlay(Drawable defaultMarker, Context context) {
		//������ø���Ĺ��캯��
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	//����OverlayItem����
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	//���ص�ǰOverlay������OverlayItem����
	@Override
	public int size() {
		return mOverlays.size();
	}
	
	//������OverlayItem������ӵ�list��
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	//���û�������ʱ��ִ��
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
