package in.parttwo.uploaddemo;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Upload extends Activity {

	int fileSize;
	String path;
	String NAME = "";
	TextView progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		progress = (TextView)findViewById(R.id.progress);
		NAME = getIntent().getExtras().getString("name");
		new UploadAsync(this).execute();
	}

	public class UploadAsync extends AsyncTask<Void, Integer, String> {

		Activity activity;
		public UploadAsync(Activity activity) {

			this.activity = activity;
			
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			
			String str = SendToServer();
			System.out.println(str);
			
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progress.setText(Integer.toString(values[0])+"% " + "uploaded");
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

		}
		
		 private String SendToServer() {
	            String responseString = null;
	 
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(Constants.URL);
	            
	            System.out.println("HEY");
	 
	            try {
	                ProgressInfo entity = new ProgressInfo(
	                      new UploadedListener() {
							
							@Override
							public void AmountTransferred(int amount) {

								System.out.println(((float)amount/fileSize)*100);
								float temp_amount = ((float)amount/fileSize)*100;
								publishProgress((int)temp_amount);
							}
						});
	                
	                File sourceFile = new File("/mnt/sdcard/AndroidUploadData",NAME);
	 
	                // Adding file data to http body
	                entity.addPart("image", new FileBody(sourceFile));

	 
	                fileSize = (int) entity.getContentLength();
	                httppost.setEntity(entity);
	 
	                // Making server call
	                HttpResponse response = httpclient.execute(httppost);
	                HttpEntity r_entity = response.getEntity();
	 
	                int statusCode = response.getStatusLine().getStatusCode();
	                if (statusCode == 200) {
	                    // Server response
	                    responseString = EntityUtils.toString(r_entity);
	                    System.out.println(responseString);
	                } else {
	                    responseString = "Error occurred! Http Status Code: "
	                            + statusCode;
	                    System.out.println(responseString);
	                }
	 
	            } catch (ClientProtocolException e) {
	                responseString = e.toString();
	            } catch (IOException e) {
	                responseString = e.toString();
	            }
	 
	            return responseString;
	 
	        }
		
	}

	
	
}
