package com.example.td;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ItemModel> implements View.OnClickListener {

    private ArrayList<ItemModel> dataSet;
    private Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSubtitle;
        Button btnDelete;
    }

    public CustomAdapter(ArrayList<ItemModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        ItemModel item = getItem(position);
        if (item != null) {
            // Handle click event if needed
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtTitle = convertView.findViewById(R.id.title);
            viewHolder.txtSubtitle = convertView.findViewById(R.id.subtitle);
            viewHolder.btnDelete = convertView.findViewById(R.id.btnDelete);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        ItemModel item = getItem(position);

        if (item != null) {
            viewHolder.txtTitle.setText(item.getTitle());
            viewHolder.txtSubtitle.setText(item.getSubtitle());

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Appeler la méthode de suppression de l'élément dans MainActivity
                    ((MainActivity) mContext).deleteItems(item.getId());

                    ((MainActivity) mContext).deleteItem(position);
                }
            });
        }

        return convertView;
    }
}

