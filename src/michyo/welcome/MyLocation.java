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
    //ͨ��network��ȡlocation  
    private String networkProvider;  
    //ͨ��gps��ȡlocation  
    private String GpsProvider; 
    private String result = "";
    private double longitude;
    private double latitude;
    
    MyLocation() {
    	networkProvider = LocationManager.NETWORK_PROVIDER;
    	GpsProvider = LocationManager.GPS_PROVIDER;
    }
    
    //��ȡlocation����  
    public void initLocation(Context mContext){  
        //���ϵͳ�������  LocationManager ����  ����������ôд ���ÿ���  
        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        
        //���ȼ�� ͨ��network �ܷ���location����  
        //��������location���� �����tv  
        if (startLocation(networkProvider, mContext)) {  
            updateLocation(location, mContext);  
        }else   
            //ͨ��gps �ܷ���location����  
            //��������location���� �����tv  
            if(startLocation(GpsProvider, mContext)){  
            updateLocation(location, mContext);  
        }else{  
            //����������ַ��������ܻ��location���� ����ʾ������Ϣ  
            Toast.makeText(mContext, "û�д�GPS�豸", 5000).show(); 
        }  
    }  
    /** 
     * ͨ������ ��ȡLocation���� 
     * ���Location����Ϊ�� �򷵻� true ���Ҹ�ֵ��ȫ�ֱ��� location 
     *   ���Ϊ�� ����false ����ֵ��ȫ�ֱ���location 
     *  
     * @param provider 
     * @param mContext 
     * @return 
     */  
	private boolean startLocation(String provider,final Context mContext){  
        Location location = lm.getLastKnownLocation(provider);  
          
        // λ�ü�����  
        locationListener = new LocationListener() {  
            // ��λ�øı�ʱ����  
            public void onLocationChanged(Location location) {  
                System.out.println(location.toString());  
                updateLocation(location,mContext); 
            }  
  
            // ProviderʧЧʱ����  
            public void onProviderDisabled(String arg0) {  
                System.out.println(arg0);  
            }  
  
            // Provider����ʱ����  
            public void onProviderEnabled(String arg0) {  
                System.out.println(arg0);  
            }  
  
            // Provider״̬�ı�ʱ����  
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {  
                System.out.println("onStatusChanged");  
            }  
        };  
  
        // 500�������һ�Σ�����λ�ñ仯  
        lm.requestLocationUpdates(provider, 500, 0, locationListener);  
          
        /*if(location == null){
        	for(int i = 0; i < 5; i++) {
        		lm.requestLocationUpdates(provider, 500, 0, locationListener); 
        	}
        }*/
//      ���Location����Ϊ�� �򷵻� true ���Ҹ�ֵ��ȫ�ֱ��� location  
//      ���Ϊ�� ����false ����ֵ��ȫ�ֱ���location  
        if (location!= null) {  
            this.location=location;  
            return true;  
        }  
        return false;  
          
    }  
    // ����λ����Ϣ չʾ��tv��  
    private void updateLocation(Location location,Context mContext) {  
        if (location != null) {  
            result = "��λ������Ϣ���£�" + location.toString() + "\n\t���о��ȣ�"  
                    + location.getLongitude() + "\n\t����γ�ȣ�"  
                    + location.getLatitude();  
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //����Ѿ���ȡ��location��Ϣ ��������ע��location�ļ���  
            //gps����һ��ʱ�����Զ��ر�  
            lm.removeUpdates(locationListener);  
        } 
        else {  
            Toast.makeText(mContext, "��ż���޷���λ��> <", Toast.LENGTH_SHORT).show();
            result = "Ŀǰû�еõ��κ���Ϣ";
        }  
    }  
    
  
    protected void onDestroy() {  
        //�����activity����ʱ  ������ע��location�ļ���  
        //gps����һ��ʱ�����Զ��ر�  
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
