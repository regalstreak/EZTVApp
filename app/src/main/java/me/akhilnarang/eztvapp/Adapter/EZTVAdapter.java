package me.akhilnarang.eztvapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import me.akhilnarang.eztvapp.Model.Torrent;
import me.akhilnarang.eztvapp.R;
import me.akhilnarang.eztvapp.Utils.Tools;

/**
 * Created by men_in_black007 on 23/5/17.
 */

public class EZTVAdapter extends RecyclerView.Adapter<EZTVAdapter.ViewHolder> implements Filterable {

    private List<Torrent> eztvModelList;
    private List<Torrent> originalEztvModelList;
    private Context context;

    public EZTVAdapter(Context context, List<Torrent> eztvModels) {
        this.eztvModelList = eztvModels;
        this.originalEztvModelList = eztvModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = eztvModelList.get(position).getFilename();
        holder.filename.setText(Tools.formatName(name));
        String imgUrl = eztvModelList.get(position).getSmallScreenshot();
        if (imgUrl.isEmpty()) {
            Picasso.get().load(R.drawable.placeholder).into(holder.image);
        } else {
            Picasso.get().load(imgUrl.replace("//", "https://")).placeholder(R.drawable.placeholder).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return eztvModelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Torrent> filteredResults;
                if (constraint.length() == 0) {
                    filteredResults = originalEztvModelList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                eztvModelList = (List<Torrent>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    protected List<Torrent> getFilteredResults(String constraint) {
        List<Torrent> results = new ArrayList<>();

        for (Torrent item : originalEztvModelList) {
            if (item.getFilename().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }

        return results;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView filename;
        private ImageView image;

        private ViewHolder(View view) {
            super(view);
            filename = (TextView) view.findViewById(R.id.filename);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

}
