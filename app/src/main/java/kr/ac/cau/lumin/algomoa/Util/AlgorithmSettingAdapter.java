package kr.ac.cau.lumin.algomoa.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.io.InvalidObjectException;
import java.util.List;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;

/**
 * Created by Lumin on 2015-11-27.
 */
public class AlgorithmSettingAdapter extends RecyclerView.Adapter<AlgorithmSettingAdapter.ViewHolder> {
    private Context context;
    private List<SiteList> siteList;
    private int viewItemLayout;

    public AlgorithmSettingAdapter(Context context, List<SiteList> siteList, int viewItemLayout) {
        this.context = context;
        this.siteList = siteList;
        this.viewItemLayout = viewItemLayout;
    }

    @Override
    public int getItemCount() {
        return this.siteList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final SiteList site = this.siteList.get(i);
        viewHolder.textView.setText(site.toString());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    User.getInstance().addNewFavoriteSite(site);
                } else {
                    try {
                        User.getInstance().deleteFavoriteSite(site);
                    } catch (InvalidObjectException e) {
                        Log.e("CheckBoxListener", "Cannot find User language." + e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setting_itemview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;
        private AppCompatCheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView = (AppCompatTextView) itemView.findViewById(R.id.setting_lang_text);
            this.checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.setting_checkbox);
        }
    }
}
