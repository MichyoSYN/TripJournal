package michyo.welcome;

import java.util.List;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class FootprintActivity extends MapActivity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footprint);
        
        final MyLocation myLocation = new MyLocation();
        //��ʼִ�л�ȡlocation����  
        myLocation.initLocation(FootprintActivity.this);
        String result = myLocation.getResult();
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        //GeoPoint�ĵ�λΪ΢��
        int geoLongitude = (int) (longitude * 1000000);
        int geoLatitude = (int) (latitude * 1000000);
        
        MapView mapView = (MapView) findViewById(R.id.mapview); 
        //��ʾ�������ŵĹ�����
        mapView.setBuiltInZoomControls(true);
        
        //mapView.getOverlays()���ڵõ�����ͼ�����
		List<Overlay> mapOverlays = mapView.getOverlays();		
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);		
		FirstOverlay firstOverlay = new FirstOverlay(drawable,this);
		
		//GeoPoint����ͨ����γ��ָ����ͼ�ϵ�һ����
		GeoPoint point = new GeoPoint(geoLatitude, geoLongitude);		
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", result);		
		firstOverlay.addOverlay(overlayitem);		
		mapOverlays.add(firstOverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
