package rubilov.eugeniy.prettyseekbar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by eugeniy.rubilov (e.rubilov@gmail.com) on 18.03.2015.
 */

public class MainActivity extends ActionBarActivity {
    private BubbleSeekBar mSeekBar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout)getLayoutInflater().inflate(R.layout.layout_seek_progress, null);
        tv = (TextView)ll.findViewById(R.id.progress_text);

        //get BubbleSeekBar
        mSeekBar = (BubbleSeekBar)findViewById(R.id.my_seek);

        //set bubble custom view and root activity view
        mSeekBar.setPopUpView(ll, getWindow().getDecorView());

        //set tip gravity
        mSeekBar.setBubbleGravity(Gravity.CENTER);

        //add custom listener
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    tv.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekBar.show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekBar.hide();
            }
        });

        //PROFIT
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
