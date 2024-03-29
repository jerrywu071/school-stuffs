package ca.yorku.eecs.mack.democamera;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Switch;


@SuppressWarnings("unused")
public class ImageListViewerActivity extends Activity implements AdapterView.OnItemClickListener
{
	final static String MYDEBUG = "MYDEBUG"; // for Log.i messages

	final static String IMAGE_INDEX_KEY = DemoCameraActivity.IMAGE_INDEX_KEY;
	final static String DIRECTORY_KEY = DemoCameraActivity.DIRECTORY_KEY;
	final static String IMAGE_FILENAMES_KEY = DemoCameraActivity.IMAGE_FILENAMES_KEY;

	private GridView gridView;

	String[] imageFilenames;
	String directory;

	Switch darkswitch;
	LinearLayout p;

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewlayout);
        init();
    }

    private void init()
    {
		// data passed from the calling activity in startActivityForResult (see DemoCameraIntentActivity)
		Bundle b = getIntent().getExtras();
		imageFilenames = b.getStringArray(IMAGE_FILENAMES_KEY);
		directory = b.getString(DIRECTORY_KEY);

		// determine display width (will be used to scale images)
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int displayWidth = dm.widthPixels;

		ImageAdapter adapter = new ImageAdapter(imageFilenames, directory, displayWidth);

		gridView = (GridView) findViewById(R.id.gridView);
    	gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		darkswitch = (Switch) findViewById(R.id.darkswitch);
		p = (LinearLayout) findViewById(R.id.background);

		//check if switch has been clicked
		darkswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					p.setBackgroundColor(0x00000000);
				} else {
					p.setBackgroundColor(0xff123456);
				}
			}
		});
    }

	//method taken from DemoGridView
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
		final Bundle b = new Bundle();
		b.putStringArray("imageFilenames", imageFilenames);
		b.putString("directory", directory.toString());
		b.putInt("position", position);

		// start image viewer activity
		Intent i = new Intent(getApplicationContext(), ImageViewerActivity.class);
		i.putExtras(b);
		startActivity(i);
	}

	@Override
	public void onBackPressed()
	{
		this.setResult(Activity.RESULT_OK);
		super.onBackPressed();
	}
}
