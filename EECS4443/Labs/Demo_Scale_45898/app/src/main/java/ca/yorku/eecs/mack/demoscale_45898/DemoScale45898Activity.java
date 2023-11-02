package ca.yorku.eecs.mack.demoscale_45898;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Demo_Scale - with modifications by...
 *
 * Login ID - jerrywu0
 * Student ID - 217545898
 * Last name - Wu
 * First name(s) - Jerry
 */

public class DemoScale45898Activity extends Activity
{
    PaintPanel imagePanel; // the panel in which to paint the image
    StatusPanel statusPanel; // a status panel the display the image coordinates, size, and scale

    Switch darkModeSwitch;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide title bar
        setContentView(R.layout.main);

        // get references to UI components
        // cast removed (not needed anymore, avoids warning message)
        imagePanel = findViewById(R.id.paintpanel);
        statusPanel = findViewById(R.id.statuspanel);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        //reference the paintpanel
        PaintPanel p = findViewById(R.id.paintpanel);

        // give the image panel a reference to the status panel
        imagePanel.setStatusPanel(statusPanel);

        //add dark mode listener
        //the listener was causing dark mode to crash on landscape mode because i forgot to check orientation. that has been fixed
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //here we set the background colour of the paintpanel
                if (isChecked) {
                    p.setBackgroundColor(0x00000000);
                } else {
                    p.setBackgroundColor(0xffffafb0);
                }
            }
        });
    }

    // Called when the "Reset" button is pressed.
    public void clickReset(View view)
    {
        imagePanel.xPosition = 10;
        imagePanel.yPosition = 10;
        imagePanel.scaleFactor = 1f;
        imagePanel.invalidate();
    }
}