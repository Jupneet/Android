package in.parttwo.uploaddemo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {

	GridView grid;
	Uri URI;
	String NAME_OF_FILE = "";

	public enum typeOfContent {
		image, video
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		grid = (GridView) findViewById(R.id.gridView);
		List<GridItem> gridData = GetGridData();
		GridAdapter adapter = new GridAdapter(gridData, getApplicationContext());
		grid.setAdapter(adapter);

		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String[] titles = { "image", "video"};

				switch (pos) {
				case 0:
					TakeAnImage();
					break;
				case 1:
					RecordVideo();
					break;

				default:
					break;
				}

			}

			private void RecordVideo() {

				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

				// Where the clicked image is going to be saved
				URI = getFileUri(typeOfContent.video);

				if (URI != null) {
					intent.putExtra(MediaStore.EXTRA_OUTPUT, URI);
					setResult(RESULT_OK, intent);
					// 1 is the request code for a video
					startActivityForResult(intent, 2);
				} else
					ShowAlert("Something went wrong.Please try again");
			}

			private void TakeAnImage() {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				// Where the clicked image is going to be saved
				URI = getFileUri(typeOfContent.image);

				if (URI != null) {
					intent.putExtra(MediaStore.EXTRA_OUTPUT, URI);
					setResult(RESULT_OK, intent);
					// 1 is the request code for an image
					startActivityForResult(intent, 1);
				} else
					ShowAlert("Something went wrong.Please try again");
			}

			private Uri getFileUri(typeOfContent type) {

				File rootDir = new File("/mnt/sdcard/AndroidUploadData");

				switch (type) {
				case image:

					if (!rootDir.exists()) {
						if (!rootDir.mkdirs())
							ShowAlert("Sorry directory cannot be created");
						else {
							String fileName = "IMG_" + "UploadTest"
									+ Calendar.getInstance().getTimeInMillis()
									+ ".jpg";
							NAME_OF_FILE = fileName;
							File imageFile = new File(rootDir.getPath()
									+ File.separator + fileName);

							return Uri.fromFile(imageFile);
						}
					} else {
						String fileName = "IMG_" + "UploadTest"
								+ Calendar.getInstance().getTimeInMillis()
								+ ".jpg";
						NAME_OF_FILE = fileName;
						File imageFile = new File(rootDir.getPath()
								+ File.separator + fileName);
						System.out.println();
						return Uri.fromFile(imageFile);

					}
					break;
				case video:

					if (!rootDir.exists()) {
						if (!rootDir.mkdirs())
							ShowAlert("Sorry directory cannot be created");
						else {
							String fileName = "VID_" + "UploadTest"
									+ Calendar.getInstance().getTimeInMillis()
									+ ".mp4";
							NAME_OF_FILE = fileName;
							File imageFile = new File(rootDir.getPath()
									+ File.separator + fileName);

							return Uri.fromFile(imageFile);
						}
					} else {
						String fileName = "VID_" + "UploadTest"
								+ Calendar.getInstance().getTimeInMillis()
								+ ".mp4";
						NAME_OF_FILE = fileName;
						File imageFile = new File(rootDir.getPath()
								+ File.separator + fileName);
						System.out.println();
						return Uri.fromFile(imageFile);

					}

					break;
				default:
					break;
				}

				return null;
			}

			private void ShowAlert(String message) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setTitle("Error");
				alert.setMessage(message);
				alert.show();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent dataWithIntent) {

		// IMAGE
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Upload(typeOfContent.image, URI);
			}
		}

		if (requestCode == 2) {
			if (resultCode == RESULT_OK) {
				Upload(typeOfContent.video, URI);
			}

		}

	}

	private void Upload(typeOfContent type, Uri uri) {

		Intent in = new Intent(MainActivity.this, Upload.class);
		System.out.println("PATH " + uri.toString());
		in.putExtra("name", NAME_OF_FILE);
		startActivity(in);

	}

	private List<GridItem> GetGridData() {

		List<GridItem> gridData = new ArrayList<GridItem>();
		String[] titles = { "image", "video" };
		Bitmap[] icons = {
				BitmapFactory.decodeResource(getResources(), R.drawable.image),
				BitmapFactory.decodeResource(getResources(), R.drawable.video), };

		for (int i = 0; i < 2; i++) {
			GridItem item = new GridItem();
			item.setIcon(icons[i]);
			item.setTitle(titles[i]);

			gridData.add(item);
		}
		return gridData;
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
}
