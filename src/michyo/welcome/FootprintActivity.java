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
        //开始执行获取location对象  
        myLocation.initLocation(FootprintActivity.this);
        String result = myLocation.getResult();
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        //GeoPoint的单位为微度
        int geoLongitude = (int) (longitude * 1000000);
        int geoLatitude = (int) (latitude * 1000000);
        
        MapView mapView = (MapView) findViewById(R.id.mapview); 
        //显示用于缩放的工具条
        mapView.setBuiltInZoomControls(true);
        
        //mapView.getOverlays()用于得到所有图层对象
		List<Overlay> mapOverlays = mapView.getOverlays();		
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);		
		FirstOverlay firstOverlay = new FirstOverlay(drawable,this);
		
		//GeoPoint用于通过经纬度指定地图上的一个点
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
