package in.parttwo.uploaddemo;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class GridAdapter extends BaseAdapter {

	List<GridItem> gridData;
	Context context;
	
	public GridAdapter(	List<GridItem> gridData, Context context) {
		
		this.gridData = gridData;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return gridData.size();
	}

	@Override
	public GridItem getItem(int pos) {
		// TODO Auto-generated method stub
		return gridData.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {

		if(convertView==null)
		{
			convertView = View.inflate(context,R.layout.grid_item, null);
			new ViewHolder(convertView);
		}

		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.icon.setImageBitmap(gridData.get(pos).getIcon());
		holder.title.setText(gridData.get(pos).getTitle());
		
		return convertView;
	}

	class ViewHolder
	{
		ImageView icon;
		TextView title;
		
		public ViewHolder(View view)
		{
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			view.setTag(this);
		}
	}

}
