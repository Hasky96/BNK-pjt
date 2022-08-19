package net.daum.android.map.openapi.sampleapp.demos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.daum.android.map.openapi.sampleapp.MapApiConst;
import net.daum.android.map.openapi.sampleapp.R;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class PolygonDemoActivity extends FragmentActivity implements MapView.MapViewEventListener {

    private static final int MENU_ADD_POLYLINE1 = Menu.FIRST;
    private static final int MENU_ADD_POLYLINE2 = Menu.FIRST + 1;
    private static final int MENU_REMOVE_POLYLINES = Menu.FIRST + 2;
    private static final int MENU_ADD_CIRCLES = Menu.FIRST + 3;
    private static final int MENU_REMOVE_CIRCLE = Menu.FIRST + 4;

    private MapView mMapView;
    private MapPoint[] mPolyline2Points;
    private boolean isMapViewInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_nested_mapview);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setMapViewEventListener(this);

        mPolyline2Points = new MapPoint[]{
                //
                MapPoint.mapPointWithWCONGCoord(475334.0, 1101210.0),
                MapPoint.mapPointWithWCONGCoord(474300.0,1104123.0),
                MapPoint.mapPointWithWCONGCoord(474300.0,1104123.0),
                MapPoint.mapPointWithWCONGCoord(473873.0,1105377.0),
                MapPoint.mapPointWithWCONGCoord(473302.0,1107097.0),
                MapPoint.mapPointWithWCONGCoord(473126.0,1109606.0),
                MapPoint.mapPointWithWCONGCoord(473063.0,1110548.0),
                MapPoint.mapPointWithWCONGCoord(473435.0,1111020.0),
                MapPoint.mapPointWithWCONGCoord(474068.0,1111714.0),
                MapPoint.mapPointWithWCONGCoord(475475.0,1112765.0),
                MapPoint.mapPointWithWCONGCoord(476938.0,1113532.0),
                MapPoint.mapPointWithWCONGCoord(478725.0,1114391.0),
                MapPoint.mapPointWithWCONGCoord(479453.0,1114785.0),
                MapPoint.mapPointWithWCONGCoord(480145.0,1115145.0),
                MapPoint.mapPointWithWCONGCoord(481280.0,1115237.0),
                MapPoint.mapPointWithWCONGCoord(481777.0,1115164.0),
                MapPoint.mapPointWithWCONGCoord(482322.0,1115923.0),
                MapPoint.mapPointWithWCONGCoord(482832.0,1116322.0),
                MapPoint.mapPointWithWCONGCoord(483384.0,1116754.0),
                MapPoint.mapPointWithWCONGCoord(484401.0,1117547.0),
                MapPoint.mapPointWithWCONGCoord(484893.0,1117930.0),
                MapPoint.mapPointWithWCONGCoord(485016.0,1118034.0)
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ADD_POLYLINE1, Menu.NONE, "Add Polyline1");
        menu.add(0, MENU_ADD_POLYLINE2, Menu.NONE, "Add Polyline2");
        menu.add(0, MENU_REMOVE_POLYLINES, Menu.NONE, "Remove All Polylines");
        menu.add(0, MENU_ADD_CIRCLES, Menu.NONE, "Add Circles");
        menu.add(0, MENU_REMOVE_CIRCLE, Menu.NONE, "Remove All Circles");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isMapViewInitialized) {
            Toast.makeText(this, "MapView is not initialized", Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

        final int itemId = item.getItemId();

        switch (itemId) {
            case MENU_ADD_POLYLINE1: {
                addPolyline1();
                return true;
            }
            case MENU_ADD_POLYLINE2: {
                addPolyline2();
                return true;
            }
            case MENU_REMOVE_POLYLINES: {
                mMapView.removeAllPolylines();
                return true;
            }
            case MENU_ADD_CIRCLES : {
                addCircles();
                return true;
            }
            case MENU_REMOVE_CIRCLE : {
                mMapView.removeAllCircles();

                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void addCircles() {
        MapCircle circle1 = new MapCircle(
        		MapPoint.mapPointWithGeoCoord(37.537094, 127.005470), // center
        		500, // radius
        		Color.argb(128, 255, 0, 0), // strokeColor 
        		Color.argb(128, 0, 255, 0) // fillColor
        );
        circle1.setTag(1234);
        mMapView.addCircle(circle1);
        MapCircle circle2 = new MapCircle(
        		MapPoint.mapPointWithGeoCoord(37.551094, 127.019470), // center
        		1000, // radius
        		Color.argb(128, 255, 0, 0), // strokeColor 
        		Color.argb(128, 255, 255, 0) // fillColor
        );
        circle2.setTag(5678);
        mMapView.addCircle(circle2);
        
     // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        MapPointBounds[] mapPointBoundsArray = { circle1.getBound(), circle2.getBound() };
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 50; // px
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    private void addPolyline2() {
        MapPOIItem existingPOIItemStart = mMapView.findPOIItemByTag(10001);
        if (existingPOIItemStart != null) {
            mMapView.removePOIItem(existingPOIItemStart);
        }

        MapPOIItem existingPOIItemEnd = mMapView.findPOIItemByTag(10002);
        if (existingPOIItemEnd != null) {
            mMapView.removePOIItem(existingPOIItemEnd);
        }

        MapPolyline existingPolyline = mMapView.findPolylineByTag(2000);
        if (existingPolyline != null) {
            mMapView.removePolyline(existingPolyline);
        }

        MapPOIItem poiItemStart = new MapPOIItem();
        poiItemStart.setItemName("Start");
        poiItemStart.setTag(10001);
        poiItemStart.setMapPoint(MapPoint.mapPointWithWCONGCoord(475334.0,1101210.0));
        poiItemStart.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemStart.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemStart.setShowCalloutBalloonOnTouch(false);
        poiItemStart.setCustomImageResourceId(R.drawable.custom_poi_marker_start);
        poiItemStart.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemStart);

        MapPOIItem poiItemEnd = new MapPOIItem();
        poiItemEnd.setItemName("End");
        poiItemEnd.setTag(10001);
        poiItemEnd.setMapPoint(MapPoint.mapPointWithWCONGCoord(485016.0,1118034.0));
        poiItemEnd.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemEnd.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemEnd.setShowCalloutBalloonOnTouch(false);
        poiItemEnd.setCustomImageResourceId(R.drawable.custom_poi_marker_end);
        poiItemEnd.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemEnd);

        MapPolyline polyline2 = new MapPolyline(21);
        polyline2.setTag(2000);
        polyline2.setLineColor(Color.argb(128, 0, 0, 255));
        polyline2.addPoints(mPolyline2Points);
        mMapView.addPolyline(polyline2);

        MapPointBounds mapPointBounds = new MapPointBounds(mPolyline2Points);
        int padding = 200; // px
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    private void addPolyline1() {
        MapPolyline existingPolyline = mMapView.findPolylineByTag(1000);
        if (existingPolyline != null) {
            mMapView.removePolyline(existingPolyline);
        }
        double[][] arr = {{128.9912235,35.1607871},{128.9911146,35.1610788},{128.9910486,35.1614303},{128.9910014,35.1626327},{128.9909999,35.1626751},{128.9910008,35.1631431},{128.9910013,35.1634126},{128.9909976,35.1636886},{128.9909953,35.1639257},{128.9909866,35.1640466},{128.9909776,35.1641586},{128.9909176,35.1643586},{128.990859,35.1645153},{128.9908244,35.1646076},{128.9907615,35.1647076},{128.9906993,35.1647913},{128.9906616,35.1648333},{128.9905991,35.1648962},{128.9903781,35.165118},{128.9897374,35.165748},{128.9893653,35.1661142},{128.9892748,35.1662081},{128.9892069,35.1662784},{128.9891616,35.1663249},{128.9890743,35.1664162},{128.9889991,35.1665082},{128.9889905,35.1665164},{128.9889411,35.1665756},{128.9889088,35.1666139},{128.9888747,35.1666666},{128.9888583,35.1667353},{128.9888353,35.1668573},{128.9887374,35.1678079},{128.9887056,35.1681726},{128.9886909,35.1683314},{128.9886506,35.1687683},{128.9886317,35.1688776},{128.9886129,35.1689897},{128.9885573,35.1691923},{128.9885247,35.1692747},{128.9885059,35.1695653},{128.9884957,35.1697322},{128.9884897,35.1698216},{128.9884886,35.169882},{128.988472,35.1707171},{128.9884169,35.1710072},{128.9884108,35.171037},{128.9882911,35.1713991},{128.988246,35.1715151},{128.988215,35.1716164},{128.9882098,35.1716354},{128.9881888,35.1716898},{128.9881396,35.1718184},{128.9881144,35.1718837},{128.9880232,35.1721228},{128.9880139,35.1721509},{128.98801,35.1721816},{128.9880105,35.1722059},{128.9881088,35.1727186},{128.9882111,35.1733945},{128.9882248,35.1734827},{128.9882475,35.1736438},{128.9883035,35.1740623},{128.9883786,35.1746303},{128.9884115,35.1748716},{128.9884356,35.1750489},{128.9884499,35.1751659},{128.9884537,35.1751938},{128.9884739,35.1753414},{128.988487,35.1755162},{128.9884872,35.1756496},{128.9884995,35.1763825},{128.9884929,35.1765043},{128.9884935,35.1765944},{128.9884929,35.1766215},{128.9884989,35.1770722},{128.9885221,35.1778627},{128.9885217,35.1784451},{128.9885257,35.1786055},{128.9885265,35.1786488},{128.9885344,35.1787786},{128.988535,35.1788741},{128.9885244,35.1801581},{128.9885248,35.1804827},{128.9885143,35.1811095},{128.9885123,35.1811816},{128.9885218,35.1815827},{128.988524,35.1816431},{128.9885246,35.1816747},{128.9885218,35.1815827},{128.9885123,35.1811816},{128.9885143,35.1811095},{128.9885248,35.1804827},{128.9885244,35.1801581},{128.988535,35.1788741},{128.9885344,35.1787786},{128.9885296,35.1786957},{128.9885265,35.1786488},{128.9885217,35.1784451},{128.9885217,35.1780205}};
        MapPolyline polyline1 = new MapPolyline(10000);
        polyline1.setTag(1000);
        polyline1.setLineColor(Color.RED);


        for (double[] temp: arr) {
            polyline1.addPoint(MapPoint.mapPointWithGeoCoord(temp[1], temp[0]));
        }
//        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.537229, 127.005515));
//        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.545024,127.03923));
//        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.527896,127.036245));
//        polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.541889,127.095388));
        mMapView.addPolyline(polyline1);

        MapPointBounds mapPointBounds = new MapPointBounds(polyline1.getMapPoints());
        int padding = 100; // px
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        isMapViewInitialized = true;
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
