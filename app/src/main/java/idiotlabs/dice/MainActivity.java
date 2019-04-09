package idiotlabs.dice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import io.fabric.sdk.android.Fabric;
import java.util.Random;

public class MainActivity extends Activity {
    private ImageView ivDice;
    private AudioManager am;
    private float x = 0;
    private float y = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showDice(new Random().nextInt(6) + 1, msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        // alertdialog
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        boolean isInitialized = sharedPref.getBoolean("INIT", false);

        if (!isInitialized) {
            this.simpleAlert();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("INIT", true);
            editor.apply();
        }

        ivDice = (ImageView)findViewById(R.id.dice);

        //am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8206166796422159~4404315621");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        // live
        AdRequest adRequest = new AdRequest.Builder().build();
        // test
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("3EA55B11DB92B9BE")
//                .build();
        mAdView.loadAd(adRequest);
    }

    public void DiceClick(View arg0) {

        try {
            // get Touch Position in ImageView
            arg0.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    x = event.getX();
                    y = event.getY();
                    return false;
                }
            });

            // Rolling...?
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        try {
                            //handler.sendMessage(handler.obtainMessage());
                            handler.sendEmptyMessage(i);
                            Thread.sleep(15);
                        } catch (Throwable t) {
                        }
                    }
                }
            }).start();

            // Get Ring Volume
//        float volume = am.getStreamVolume(AudioManager.STREAM_RING);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showDice(int rnd, int loop) {
        System.out.println(x + ", " + y);

        if (loop >= 19 && x > 320 && y > 320) {
            rnd = new Random().nextInt(3) + 4;
        }
        else if (loop >= 19 && x < 90 && y < 90) {
            rnd = new Random().nextInt(3) + 1;
        }

        switch(rnd) {
            case 1:
                ivDice.setImageResource(R.drawable.one);
                break;
            case 2:
                ivDice.setImageResource(R.drawable.two);
                break;
            case 3:
                ivDice.setImageResource(R.drawable.three);
                break;
            case 4:
                ivDice.setImageResource(R.drawable.four);
                break;
            case 5:
                ivDice.setImageResource(R.drawable.five);
                break;
            case 6:
                ivDice.setImageResource(R.drawable.six);
                break;
            default:
        }
    }

    public void simpleAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("너무 궁금해서요");
        builder.setMessage("설문조사 하나만 해주실 수 있을까요?");
        builder.setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/PRFPsNNxoGosIXhv2"));
                        startActivity(browserIntent);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Hidden VolumeControl
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                am.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
    */

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
