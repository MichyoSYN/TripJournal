package michyo.welcome;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


public class MyLocation {
    Location location;  
    LocationManager lm;  
    LocationListener locationListener;  
    //通过network获取location  
    private String networkProvider;  
    //通过gps获取location  
    private String GpsProvider; 
    private String result = "";
    private double longitude;
    private double latitude;
    
    MyLocation() {
    	networkProvider = LocationManager.NETWORK_PROVIDER;
    	GpsProvider = LocationManager.GPS_PROVIDER;
    }
    
    //获取location对象  
    public void initLocation(Context mContext){  
        //获得系统及服务的  LocationManager 对象  这个代码就这么写 不用考虑  
        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        
        //首先检测 通过network 能否获得location对象  
        //如果获得了location对象 则更新tv  
        if (startLocation(networkProvider, mContext)) {  
            updateLocation(location, mContext);  
        }else   
            //通过gps 能否获得location对象  
            //如果获得了location对象 则更新tv  
            if(startLocation(GpsProvider, mContext)){  
            updateLocation(location, mContext);  
        }else{  
            //如果上面两种方法都不能获得location对象 则显示下列信息  
            Toast.makeText(mContext, "没有打开GPS设备", 5000).show(); 
        }  
    }  
    /** 
     * 通过参数 获取Location对象 
     * 如果Location对象为空 则返回 true 并且赋值给全局变量 location 
     *   如果为空 返回false 不赋值给全局变量location 
     *  
     * @param provider 
     * @param mContext 
     * @return 
     */  
	private boolean startLocation(String provider,final Context mContext){  
        Location location = lm.getLastKnownLocation(provider);  
          
        // 位置监听器  
        locationListener = new LocationListener() {  
            // 当位置改变时触发  
            public void onLocationChanged(Location location) {  
                System.out.println(location.toString());  
                updateLocation(location,mContext); 
            }  
  
            // Provider失效时触发  
            public void onProviderDisabled(String arg0) {  
                System.out.println(arg0);  
            }  
  
            // Provider可用时触发  
            public void onProviderEnabled(String arg0) {  
                System.out.println(arg0);  
            }  
  
            // Provider状态改变时触发  
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {  
                System.out.println("onStatusChanged");  
            }  
        };  
  
        // 500毫秒更新一次，忽略位置变化  
        lm.requestLocationUpdates(provider, 500, 0, locationListener);  
          
        /*if(location == null){
        	for(int i = 0; i < 5; i++) {
        		lm.requestLocationUpdates(provider, 500, 0, locationListener); 
        	}
        }*/
//      如果Location对象为空 则返回 true 并且赋值给全局变量 location  
//      如果为空 返回false 不赋值给全局变量location  
        if (location!= null) {  
            this.location=location;  
            return true;  
        }  
        return false;  
          
    }  
    // 更新位置信息 展示到tv中  
    private void updateLocation(Location location,Context mContext) {  
        if (location != null) {  
            result = "定位对象信息如下：" + location.toString() + "\n\t其中经度："  
                    + location.getLongitude() + "\n\t其中纬度："  
                    + location.getLatitude();  
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //如果已经获取到location信息 则在这里注销location的监听  
            //gps会在一定时间内自动关闭  
            lm.removeUpdates(locationListener);  
        } 
        else {  
            Toast.makeText(mContext, "啊偶，无法定位呢> <", Toast.LENGTH_SHORT).show();
            result = "目前没有得到任何信息";
        }  
    }  
    
  
    protected void onDestroy() {  
        //当这个activity销毁时  在这里注销location的监听  
        //gps会在一定时间内自动关闭  
        lm.removeUpdates(locationListener);  
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}  
}
