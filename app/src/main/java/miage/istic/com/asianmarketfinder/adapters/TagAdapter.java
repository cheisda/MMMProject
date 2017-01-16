package miage.istic.com.asianmarketfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import miage.istic.com.asianmarketfinder.R;
import miage.istic.com.asianmarketfinder.database.tag.Tag;

/**
 * Created by Rom on 1/15/2017.
 */
public class TagAdapter extends BaseAdapter {

    Context context;
    List<Tag> data;
    private static LayoutInflater inflater = null;

    public TagAdapter(Context context, List<Tag> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(List<Tag> data) {
        this.data = data;
    }

    @Override
    public int getViewTypeCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        final ViewHolder holder;

        if (vi == null){
            vi = inflater.inflate(R.layout.fragment_tag_libelle, null);
            holder = new ViewHolder();
            holder.name = data.get(position).getName();
            holder.id = data.get(position).getId();
            vi.setTag(holder);
        }else{
            holder = (ViewHolder) vi.getTag();
        }

        TextView text = (TextView) vi.findViewById(R.id.tag_libelle);
        text.setText(data.get(position).getName());
        text.setTag(data.get(position).getId());

        return vi;
    }

    static class ViewHolder {
        String id;
        String name;
    }
}