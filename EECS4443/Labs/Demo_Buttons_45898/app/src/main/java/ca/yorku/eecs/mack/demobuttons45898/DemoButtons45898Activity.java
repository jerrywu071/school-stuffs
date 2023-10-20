package ca.yorku.eecs.mack.demobuttons45898;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Demo_Android - with modifications by...
 *
 * Login ID - jerrywu0
 * Student ID - 217545898
 * Last name - Wu
 * First name(s) - Jerry
 */
@SuppressWarnings("unused")
public class DemoButtons45898Activity extends Activity {
    private final static String MYDEBUG = "MYDEBUG"; // for Log.i messages


    LinearLayout layout;
    Button b;
    CheckBox cb;
    RadioButton rb1, rb2, rb3;
    ToggleButton tb;
    ImageButton backspaceButton;
    TextView buttonClickStatus, checkBoxClickStatus, radioButtonClickStatus, toggleButtonClickStatus,
            backspaceButtonClickStatus;

    Switch darkModeSwitch;

    //remove exit button
    //Button exitButton;

    String buttonClickString, backspaceString;
    boolean checkStatus;
    boolean rb1Status, rb2Status, rb3Status;
    boolean tbStatus;

    // called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
        layout = (LinearLayout) findViewById(R.id.linearLayout);
        b = (Button) findViewById(R.id.button);
        cb = (CheckBox) findViewById(R.id.checkbox);
        rb1 = (RadioButton) findViewById(R.id.radiobutton1);
        rb2 = (RadioButton) findViewById(R.id.radiobutton2);
        rb3 = (RadioButton) findViewById(R.id.radiobutton3);
        rb1.toggle();
        tb = (ToggleButton) findViewById(R.id.togglebutton);
        backspaceButton = (ImageButton) findViewById(R.id.backspacebutton);

        darkModeSwitch = (Switch) findViewById(R.id.darkModeSwitch);

        //REMOVED EXIT BUTTON
        //exitButton = (Button) findViewById(R.id.exitbutton);

        buttonClickStatus = (TextView) findViewById(R.id.buttonclickstatus);
        checkBoxClickStatus = (TextView) findViewById(R.id.checkboxclickstatus);
        radioButtonClickStatus = (TextView) findViewById(R.id.radiobuttonclickstatus);
        toggleButtonClickStatus = (TextView) findViewById(R.id.togglebuttonclickstatus);
        backspaceButtonClickStatus = (TextView) findViewById(R.id.backspacebuttonclickstatus);

        buttonClickString = "";
        backspaceString = "";

        buttonClickStatus.setText(buttonClickString);
        checkBoxClickStatus.setText(R.string.unchecked);
        radioButtonClickStatus.setText(R.string.red);
        radioButtonClickStatus.setTextColor(Color.RED);
        toggleButtonClickStatus.setText(R.string.off);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.setBackgroundColor(0x00000000);
                } else {
                    layout.setBackgroundColor(0xff113322);
                }
            }
        });

    }

    // handle button clicks
    public void buttonClick(View v) {
        // plain button
        if (v == b) {
            buttonClickString += ".";
            buttonClickStatus.setText(buttonClickString);
        }

        // checkbox
        else if (v == cb) {
            if (cb.isChecked()) {
                cb.setChecked(true);
                checkBoxClickStatus.setText(R.string.checked);
            } else {
                cb.setChecked(false);
                checkBoxClickStatus.setText(R.string.unchecked);
            }
        }

        // radio button #1 (RED)
        else if (v == rb1) {
            rb1.setChecked(true);
            radioButtonClickStatus.setText(R.string.red);
            radioButtonClickStatus.setTextColor(Color.RED);
        }

        // radio button #2 (GREEN)
        else if (v == rb2) {
            rb2.setChecked(true);
            radioButtonClickStatus.setText(R.string.green);
            radioButtonClickStatus.setTextColor(Color.GREEN);
        }

        // radio button #3 (BLUE)
        else if (v == rb3) {
            rb3.setChecked(true);
            radioButtonClickStatus.setText(R.string.blue);
            radioButtonClickStatus.setTextColor(Color.BLUE);
        }

        // toggle button
        else if (v == tb) {
            tb.setActivated(tb.isChecked());
            if (tb.isChecked())
                toggleButtonClickStatus.setText(R.string.on);
            else
                toggleButtonClickStatus.setText(R.string.off);
        }

        // backspace button
        else if (v == backspaceButton) {
            backspaceString += "BK ";
            backspaceButtonClickStatus.setText(backspaceString);
        }

        // exit button
        /*

        REMOVE EXIT BUTTON
        else if (v == exitButton) {
            this.finish();
        }*/
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //ADD SAVE INSTANCE STATE METHOD
        savedInstanceState.putString("buttonClickString", buttonClickString);
        savedInstanceState.putBoolean("checkStatus", cb.isChecked());
        savedInstanceState.putBoolean("rb1Status", rb1.isChecked());
        savedInstanceState.putBoolean("rb2Status", rb2.isChecked());
        savedInstanceState.putBoolean("rb3Status", rb3.isChecked());
        savedInstanceState.putBoolean("tbStatus", tb.isChecked());
        savedInstanceState.putString("backspaceString", backspaceString);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //ADD RESTORE INSTANCE STATE METHOD
        super.onRestoreInstanceState(savedInstanceState);

        buttonClickString = savedInstanceState.getString("buttonClickString", "");
        checkStatus = savedInstanceState.getBoolean("checkStatus", false);
        rb1Status = savedInstanceState.getBoolean("rb1Status", false);
        rb2Status = savedInstanceState.getBoolean("rb2Status", false);
        rb3Status = savedInstanceState.getBoolean("rb3Status", false);
        tbStatus = savedInstanceState.getBoolean("tbStatus", false);
        backspaceString = savedInstanceState.getString("backspaceString", "");

        // Update your UI elements with the restored data
        buttonClickStatus.setText(buttonClickString);
        if (checkStatus) {
            cb.setChecked(true);
            checkBoxClickStatus.setText(R.string.checked);
        } else {
            cb.setChecked(false);
            checkBoxClickStatus.setText(R.string.unchecked);
        }

        rb1.setChecked(rb1Status);
        rb2.setChecked(rb2Status);
        rb3.setChecked(rb3Status);
        if (rb1Status) {
            radioButtonClickStatus.setText(R.string.red);
            radioButtonClickStatus.setTextColor(Color.RED);
        } else if (rb2Status) {
            radioButtonClickStatus.setText(R.string.green);
            radioButtonClickStatus.setTextColor(Color.GREEN);
        } else if (rb3Status) {
            radioButtonClickStatus.setText(R.string.blue);
            radioButtonClickStatus.setTextColor(Color.BLUE);
        }

        tb.setChecked(tbStatus);
        if (tbStatus)
            toggleButtonClickStatus.setText(R.string.on);
        else
            toggleButtonClickStatus.setText(R.string.off);

        backspaceButtonClickStatus.setText(backspaceString);
    }
}