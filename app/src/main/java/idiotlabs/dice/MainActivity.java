package idiotlabs.dice;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends Activity {
    ImageView ivDice;
    AudioManager am;
    float x = 0;
    float y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDice = (ImageView)findViewById(R.id.dice);

        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8206166796422159~4404315621");

        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("3EA55B11DB92B9BE")
                .build();
        mAdView.loadAd(adRequest);

    }

    public void DiceClick(View arg0) {

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
//        long start_time = System.currentTimeMillis();
//        long wait_time = 100;
//        long end_time = start_time + wait_time;

//        while (System.currentTimeMillis() < end_time) {
//            System.out.println(System.currentTimeMillis());
//        }

        // Get Ring Volume
//        float volume = am.getStreamVolume(AudioManager.STREAM_RING);
//        System.out.println(volume);

        int rnd = new Random().nextInt(6) + 1;

        if (x > 350 && y > 350) {
            rnd = new Random().nextInt(3) + 4;
        }

        // show Dice Image
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
