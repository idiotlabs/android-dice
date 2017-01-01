package idiotlabs.dice;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends Activity {
    ImageView ivDice;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDice = (ImageView)findViewById(R.id.dice);

        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);


    }

    public void DiceClick(View arg0) {
        long start_time = System.currentTimeMillis();
        long wait_time = 100;
        long end_time = start_time + wait_time;

//        while (System.currentTimeMillis() < end_time) {
//            System.out.println(System.currentTimeMillis());
//        }

        float volume = am.getStreamVolume(AudioManager.STREAM_RING);
        System.out.println(volume);

        int rnd = new Random().nextInt(6) + 1;

        if (volume > 3) {
            rnd = new Random().nextInt(3) + 4;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
