package com.irex.appspark.irexr_03;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.media.CamcorderProfile.get;

public class VoiceControl extends Activity implements RecognitionListener {

    //Notification listner
    Button robott;
    ListView list;
    CustomListAdapter adapter;
    ArrayList<Model> modelList;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private AlertDialog enableNotificationListenerAlertDialog;

    Socket myAppSocket = null;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";
    public TextView tv;
    private TextView seoncdtext;
    public ToggleButton toggleButton,togleee;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    public String tejxt;
    private String LOG_TAG = "VoiceRecognitionActivity";
    String text = "a";
    ArrayList<String> matches;
    public EditText txtAddress;
    public TextView returnedText,outputt;
    public TextView notii;

    public String gettingMesg;
    public String mesgg="";
    public  Switch swtch;
    public  boolean autoMode=false;
    private Dialog webViewDialog;
    //a WebView object to display a web page
    private WebView webView;
    public Button btClose;
    public  TextView rtextt;
    public  EditText ipad,porta;



    public int a= 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        //noti listner

    Thread myt =new Thread(new MyServerThread());

        modelList = new ArrayList<Model>();
        adapter = new CustomListAdapter(getApplicationContext(), modelList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

        robott =(Button) findViewById(R.id.robot);
        ipad = (EditText) findViewById(R.id.ipadd);
        porta = (EditText) findViewById(R.id.portadd);


        returnedText = (TextView) findViewById(R.id.textvieww);
        rtextt = (TextView) findViewById(R.id.rtext);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        int color = Color.parseColor("#183c56");
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);


        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

  //      tv.setText(getIntent().getStringExtra("EdiTtEXTvALUE"));


        try {
            //Create a new dialog
            webViewDialog = new Dialog(this);
            //Remove the dialog's title
            webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //Inflate the contents of this dialog with the Views defined at 'webviewdialog.xml'
            webViewDialog.setContentView(R.layout.webviewdialog);
            //With this line, the dialog can be dismissed by pressing the back key
            webViewDialog.setCancelable(true);

            // initialize the browser object
            //   WebView browser = (WebView) findViewById(R.id.wb_webview);

            //Initialize the WebView object with data from the 'webviewdialog.xml' file
            webView = (WebView) webViewDialog.findViewById(R.id.wb_webview);
            //Scroll bars should not be hidden
            webView.setScrollbarFadingEnabled(false);
            //Disable the horizontal scroll bar
            webView.setHorizontalScrollBarEnabled(false);
            //Enable JavaScript
            //webView.getSettings().setJavaScriptEnabled(true);
            //Set the user agent
            webView.getSettings().setUserAgentString("Google");
            //Clear the cache
            webView.clearCache(true);
            //Make the webview load the specified URL
            //webView.loadUrl("http://www.google.com");
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(true);


            togleee = (ToggleButton) findViewById(R.id.toglee);
            togleee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                      a=20;
                    } else {
                      a=30;
                    }
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(VoiceControl.this, "Please try again later "+e, Toast.LENGTH_SHORT).show();

        }


        progressBar.setVisibility(View.INVISIBLE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    // progressBar.setIndeterminate(true);
                    restartListeningService();
                    // speech.startListening(recognizerIntent);



                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    //                  speech.cancel();
//                    speech.destroy();

                }
            }
        });
        robott.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */

                    MyClientTask myClientTask = new MyClientTask(
                            ipad.getText().toString(),
                            Integer.parseInt(porta.getText().toString()));
                    myClientTask.execute("stopm");
                }
                catch ( Exception e)
                {
                    Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

                }
            }

        });
        // If the user did not turn the notification listener service on we prompt him to do so
        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }
    }

    class MyServerThread implements Runnable
    {
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;

        BufferedReader bufferedReader;
        Handler h = new Handler();
        String message;

        public void run()
        {
            try {
                ss= new ServerSocket(7801);
                while(true)
                {
                    s= ss.accept();
                    isr =new InputStreamReader(s.getInputStream());
                    bufferedReader = new BufferedReader(isr);
                    message= bufferedReader.readLine();

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VoiceControl.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
        }
    }
    private AlertDialog buildNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Notification Access");
        alertDialogBuilder.setMessage("Please Enble Notification Access for IREX");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton("Not this time",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected

                    }
                });
        return(alertDialogBuilder.create());
    }
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            mesgg = intent.getStringExtra("text");

            rtextt.setText(mesgg);
if (a==20) {
    try {

                  /*  getIPandPort();
                    CMD = rtextt.getText().toString();
                    Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                    cmd_increase_servo.execute();
*/
        if (mesgg != "") {

            MyClientTask myClientTask = new MyClientTask(
                    ipad.getText().toString(),
                    Integer.parseInt(porta.getText().toString()));
            myClientTask.execute(rtextt.getText().toString());

        }


    } catch (Exception e) {

        Toast.makeText(VoiceControl.this, "Please try again later " + e, Toast.LENGTH_SHORT).show();
    }

}
else if (a==30){
    rtextt.setText(mesgg);
}

        }

    };


    protected void initSpeech() {
        if (speech == null) {
            speech = SpeechRecognizer.createSpeechRecognizer(this);
            if (!SpeechRecognizer.isRecognitionAvailable(this)) {
                Toast.makeText(this, "Speech Recognition is not available",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            speech.setRecognitionListener(this);
        }
    }
    // starts the service
    protected void startListening() {
        try {
            initSpeech();
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            if (!intent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE))
            {
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                        "en");
            }
            speech.startListening(intent);
        } catch(Exception ex) {

        }
    }
    // stops the service
    protected void stopListening() {
        if (speech != null) {
            speech.stopListening();
            speech.cancel();
            speech.destroy();
        }
        speech = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        //   progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        //  returnedText.setText(errorMessage);
        toggleButton.setChecked(false);
    }

    public void restartListeningService() {
        stopListening();
        startListening();
    }
    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches) {
            text += result + "\n";
        }

        returnedText.setText(text);
     /*   try {
            webViewDialog.show();
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://www.google.com/search?q=" + text);
        }
        catch (Exception e)
        {

            Toast.makeText(VoiceControl.this, "Please try again later "+e, Toast.LENGTH_SHORT).show();

        }
        */
//returnedText.setText(mesgg);

        if (matches.contains("hello")) {

            try {
              /*  getIPandPort();
                CMD = "hello";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
                */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("hello");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("hello how are you") ||matches.contains("how are you")) {
           try {
        /*    getIPandPort();
            CMD = "howyou";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
               MyClientTask myClientTask = new MyClientTask(
                       ipad.getText().toString(),
                       Integer.parseInt(porta.getText().toString()));
               myClientTask.execute("iamfine");
        }
            catch ( Exception e)
        {
            Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

        }
        }
        else if (matches.contains("what is childish behaviour")||matches.contains("what is childish behavior")) {
            try {
               /* getIPandPort();
                CMD = "childish";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
                */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("childish");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("what is mature behaviour")||matches.contains("what is mature behavior")) {
            try {
             /*   getIPandPort();
                CMD = "mature";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
                */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("mature");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("who are you") ||matches.contains("what is your name")) {
            try {
            /*    getIPandPort();
                CMD = "who";
                Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                cmd_increase_servo.execute();
                */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("who");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("what are your features")||matches.contains("what are your functions")) {
           try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
               MyClientTask myClientTask = new MyClientTask(
                       ipad.getText().toString(),
                       Integer.parseInt(porta.getText().toString()));
               myClientTask.execute("howyou");
           }
           catch ( Exception e)
           {
               Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

           }
        }
        else if (matches.contains("you are a bad robot")||matches.contains("you are bad robot")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("bad");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("you are a good robot")||matches.contains("you are good robot")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("good");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("reset position")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("stable");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("find your")||matches.contains("follow me")||matches.contains("follow")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("obavoiding");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("stop working")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */

                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("stopm");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("is this environment is good for us")||matches.contains("analyze environment")||matches.contains("calculate environment")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("env");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("turn left")||matches.contains("move left")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("left");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("turn right")||matches.contains("move right")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("right");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("move back")||matches.contains("back")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("back");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("play music")||matches.contains("start music")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("play");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("water vapour")||matches.contains("water vapor")||matches.contains("water vapour in air")||matches.contains("what is humidity")||matches.contains("humidity")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("hum");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("what is temperature")||matches.contains("temperature")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("temp");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("effect of water vapor")||matches.contains("effect of water vapour")||matches.contains("effect of humidity")||matches.contains("effect humidity")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("eff");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("what is safe level of water vapour")||matches.contains("what is safe level of water vapor")||matches.contains("what is safe level of humidity")||matches.contains("safe level of humidity")||matches.contains("safe level of water vapor")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("safe");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        }
        else if (matches.contains("diseases due to high humidity")||matches.contains("high level of humidity")||matches.contains("high level humidity")||matches.contains("what is high level of water vapor")||matches.contains("what is minimum high level of water vapor")) {
            try {
            /*
			getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("highh");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        }
        else if (matches.contains("diseases due to low humidity")||matches.contains("low level of humidity")||matches.contains("low level of water vapour")||matches.contains("low level of water vapours")||matches.contains("what is minimum level of water vapour")) {
            try {
			
            /*getIPandPort();
			CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("loww");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("run")||matches.contains("Run") ||matches.contains("move forward")) {
            try {
            /*getIPandPort();
            CMD = "features";
            Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
            cmd_increase_servo.execute();
            */
                MyClientTask myClientTask = new MyClientTask(
                        ipad.getText().toString(),
                        Integer.parseInt(porta.getText().toString()));
                myClientTask.execute("run");
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

            }
        }
        else if (matches.contains("is there any new message for me")) {

            try {
                if (rtextt.getText() == "") {
                   /* getIPandPort();
                    CMD = "mesgsno";
                    Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                    cmd_increase_servo.execute();
                    */
                    MyClientTask myClientTask = new MyClientTask(
                            ipad.getText().toString(),
                            Integer.parseInt(porta.getText().toString()));
                    myClientTask.execute("mesgsno");
                } else {
                   /* getIPandPort();
                    CMD = "mesgsyes";
                    Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                    cmd_increase_servo.execute();
                    */
                    MyClientTask myClientTask = new MyClientTask(
                            ipad.getText().toString(),
                            Integer.parseInt(porta.getText().toString()));
                    myClientTask.execute("mesgsyes");
                }
            }
            catch ( Exception e)
        {
            Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();

        }
        }
        else if (matches.contains("call to sir Arif")) {

            try {
//Make a call
            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "03419013753"));
                    startActivity(callIntent);

                    // startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("555")));
                }
                catch (Exception e)
                {
                    Toast.makeText(VoiceControl.this, "error", Toast.LENGTH_SHORT).show();
                }}
//Make call end
            }
            catch ( Exception e)
            {
                Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        }

        else if (matches.contains("what is new message")) {

            if (rtextt.getText() =="")
            {
                try {

                    /*getIPandPort();
                    CMD = "nothing";
                    Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                    cmd_increase_servo.execute();
*/
                    MyClientTask myClientTask = new MyClientTask(
                            ipad.getText().toString(),
                            Integer.parseInt(porta.getText().toString()));
                    myClientTask.execute("nothing");
                }
                catch ( Exception e)
                {
                    Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
            else {

                // returnedText.setText(mesgg);

                try {

                  /*  getIPandPort();
                    CMD = rtextt.getText().toString();
                    Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
                    cmd_increase_servo.execute();
*/
                    MyClientTask myClientTask = new MyClientTask(
                            ipad.getText().toString(),
                            Integer.parseInt(porta.getText().toString()));
                    myClientTask.execute(rtextt.getText().toString());
                }
                catch ( Exception e)
                {
                    Toast.makeText(VoiceControl.this, "Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }

        else
        {
            autoMode = false;
            Toast.makeText(VoiceControl.this, "Command Again", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
/*
    public void getIPandPort()
    {
        String iPandPort = tv.getText().toString();
        Log.d("MYTEST","IP String: "+ iPandPort);
        String temp[]= iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST","IP:" +wifiModuleIp);
        Log.d("MY TEST","PORT:"+wifiModulePort);
    }
    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;
        @Override
        protected Void doInBackground(Void... params){
            try{
                InetAddress inetAddress = InetAddress.getByName(VoiceControl.wifiModuleIp);
                socket = new java.net.Socket(inetAddress,VoiceControl.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            }catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
            return null;
        }
    }
    */

    public class MyClientTask extends AsyncTask<String, Void, Void> {

        String dstAddress;
        int dstPort;
        String response;

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Socket socket = new Socket(dstAddress, dstPort);

                OutputStream outputStream = socket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(params[0]);

                socket.close();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }
}