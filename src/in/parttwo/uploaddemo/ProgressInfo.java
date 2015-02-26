package in.parttwo.uploaddemo;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.entity.mime.MultipartEntity;

@SuppressWarnings("deprecation")
public class ProgressInfo extends MultipartEntity {

	UploadedListener listener;

	ProgressInfo(UploadedListener listener) {
		super();
		this.listener = listener;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		// TODO Auto-generated method stub
		super.writeTo(new WrapperOutputStream(outstream, this.listener));
	}
	
	//FILTERING THE OUTPUT STREAM TO GET THE PROGRESS AND UPDATING THE PROGRESS VIA UPLOADEDlISTENER'S 
	//AMOUNT TRANSFERRED METHOD
	//http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/io/FilterOutputStream.java
	public class WrapperOutputStream extends FilterOutputStream {
		
		UploadedListener listener;
		int amount;
		
		public WrapperOutputStream(OutputStream out1, UploadedListener listener) {
			super(out1);
			this.listener = listener;
		}
	
		//out is the underlying outputstream
		@Override
		public void write(byte[] buffer) throws IOException {
			// TODO Auto-generated method stub
			out.write(buffer);
			amount+=buffer.length;
			this.listener.AmountTransferred(amount);
		}
		
		@Override
		public void write(byte[] buffer, int offset, int length)
				throws IOException {
			out.write(buffer, offset, length);
			amount+=length;
			this.listener.AmountTransferred(amount);
		}
		
		@Override
		public void write(int oneByte) throws IOException {
			out.write(oneByte);
			amount+=oneByte;
			this.listener.AmountTransferred(amount);
		}
	
	}

}
