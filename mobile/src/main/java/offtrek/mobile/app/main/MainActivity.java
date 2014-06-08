package offtrek.mobile.app.main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import offtrek.mobile.app.R;
import offtrek.mobile.app.api.Request;


public class MainActivity extends Activity implements ActionBar.TabListener, LocationListener{

    public static final int MINIMUM_ACCEPTABLE_ACCURACY = 200;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v13.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    private LocationManager locationManager;
    private Location lastLocation = null;
    private String locationData;
    private boolean GPS_captureStarted = false;

    private double currentLatitude = 0;
    private double currentLongitude = 0;
    private SegmentType currentDifficulty = SegmentType.EASY;

    private long chronoBase;

    Document gpx_document;
    Element rootElement;
    Element track;
    Element trackSegment;

    private Map<String, Boolean> filesToSync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        filesToSync = new HashMap<>();
        initLocationServices();
    }

    public void initLocationServices(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        chronoBase = SystemClock.elapsedRealtime();
    }


    public void handle_GPS_capture_toggle(View v){
        if(GPS_captureStarted){
            Log.i("GPS_Capture", "Stopping GPS tracking");
            stop_GPS_Capture();
        }else{
            Log.i("GPS_Capture", "Starting GPS tracking");
            try {
                start_GPS_Capture();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void start_GPS_Capture() throws ParserConfigurationException, TransformerConfigurationException {

        gpx_document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        rootElement = gpx_document.createElement("gpx");
        Attr xmlns = gpx_document.createAttribute("xmlns");
        Attr version = gpx_document.createAttribute("version");
        Attr creator = gpx_document.createAttribute("creator");

        xmlns.setValue("http://www.topografix.com/GPX/1/1");
        version.setValue("1.1");
        creator.setValue("http://offtrek.com");

        rootElement.setAttributeNode(xmlns);
        rootElement.setAttributeNode(version);
        rootElement.setAttributeNode(creator);


        track = gpx_document.createElement("trk");
        trackSegment = gpx_document.createElement("trkseg");

        track.appendChild(trackSegment);
        rootElement.appendChild(track);
        gpx_document.appendChild(rootElement);


        Chronometer chrono = (Chronometer) findViewById(R.id.chronometer);
        chrono.setBase(chronoBase);
        chrono.start();
        GPS_captureStarted = true;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    public void stop_GPS_Capture(){
        Chronometer chrono = (Chronometer) findViewById(R.id.chronometer);
        chronoBase = SystemClock.elapsedRealtime();
        chrono.stop();
        locationManager.removeUpdates(this);
        GPS_captureStarted = false;
    }



    public void saveCurrentRoute(View view){

        if(GPS_captureStarted){
            Log.w("Saving Route", "Can't Save, tracking not paused");
            return;
        }

        Element metadata = gpx_document.createElement("metadata");
        Element filename = gpx_document.createElement("name");
        Element author = gpx_document.createElement("author");
        Element trackName = gpx_document.createElement("name");

        String fn = "TestFile";
        String tn = "TestTrack";
        String auth = "Username";

        filename.appendChild(gpx_document.createTextNode(fn));
        author.appendChild(gpx_document.createTextNode(auth));
        trackName.appendChild(gpx_document.createTextNode(tn));

        track.appendChild(trackName);
        metadata.appendChild(filename);
        metadata.appendChild(author);
        rootElement.appendChild(metadata);


        try {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(gpx_document);
            StreamResult result;
            if(isConnected){
                result = new StreamResult(new StringWriter());
                transformer.transform(source, result);
                String gpx = result.getWriter().toString();
                Request.send_GPX(gpx);
            }else{
                FileOutputStream stream = openFileOutput(fn + ".gpx", MODE_PRIVATE);
                result = new StreamResult(stream);
                transformer.transform(source,result);

                Log.i("writing file", "success");
                filesToSync.put(fn + ".gpx", true);
            }

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    @Override
    public void onLocationChanged(Location location) {

        double newLat = location.getLatitude();
        double newLon = location.getLongitude();

        if(newLat != currentLatitude || newLon != currentLongitude){
            currentLatitude = newLat;
            currentLongitude = newLon;
            Log.i("Location Update", "new coordinates");
            store_data(location);

        }else{
            //TODO add restpoints on pause
        }
    }

    private void store_data(Location loc){

        Element trkpt = gpx_document.createElement("trkpt");

        Attr longitude = gpx_document.createAttribute("lon");
        Attr latitude = gpx_document.createAttribute("lat");
        longitude.setValue(Double.toString(currentLongitude));
        latitude.setValue(Double.toString(currentLatitude));

        trkpt.setAttributeNode(longitude);
        trkpt.setAttributeNode(latitude);

        Element elevation = gpx_document.createElement("ele");
        String altitude = Double.toString(loc.getAltitude());
        elevation.appendChild(gpx_document.createTextNode(altitude));
        trkpt.appendChild(elevation);

        Element timestamp = gpx_document.createElement("time");
        String time = Long.toString(loc.getTime());
        timestamp.appendChild(gpx_document.createTextNode(time));
        trkpt.appendChild(timestamp);

        Element type = gpx_document.createElement("type");
        String difficulty = currentDifficulty.getDifficulty();
        type.appendChild(gpx_document.createTextNode(difficulty));
        trkpt.appendChild(type);

        trackSegment.appendChild(trkpt);

        update_screen_info(altitude, difficulty);
    }


    private void update_screen_info(String alt, String diff){


        TextView altitude = (TextView) findViewById(R.id.elevationData);
        altitude.setText(alt);
        TextView difficulty = (TextView) findViewById(R.id.difficultyData);
        difficulty.setText(diff);

    }



    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position){
                case 0: return LeftFragment.newInstance(position );
                case 1: return CenterFragment.newInstance(position);
                case 2: return RightFragment.newInstance(position);
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CenterFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CenterFragment newInstance(int sectionNumber) {
            CenterFragment fragment = new CenterFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public CenterFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_center, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.centerSectionLabel);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RightFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RightFragment newInstance(int sectionNumber) {
            RightFragment fragment = new RightFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public RightFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_right, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.rightSectionLabel);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }




    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LeftFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static LeftFragment newInstance(int sectionNumber) {
            LeftFragment fragment = new LeftFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public LeftFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_left, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.leftSectionLabel);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }




    }


    public class NetworkStateReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(isConnected(context)){
                try {
                    Log.i("Connectivity Status", "Is connected");

                    Set<String> files =  filesToSync.keySet();

                    for(String s : files){
                        if(filesToSync.get(s)){
                            FileInputStream fis = openFileInput(s);
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);

                            StringBuilder total = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                total.append(line);
                            }

                            Request.send_GPX(total.toString());
                        }
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public boolean isConnected(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
            return isConnected;
        }
    }

    private enum SegmentType{

        EASY("easy"),
        REGULAR("regular"),
        TOUGH("tough"),
        EXTREME("extreme");

        private String difficulty;

        SegmentType(String s){
            difficulty = s;
        }

        public String getDifficulty() {
            return difficulty;
        }
        public void setDifficulty(String dif) {
            this.difficulty = dif;
        }

    }
}
