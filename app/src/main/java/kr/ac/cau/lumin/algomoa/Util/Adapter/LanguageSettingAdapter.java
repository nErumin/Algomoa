package kr.ac.cau.lumin.algomoa.Util.Adapter;

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
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.User;

/**
 * Created by Lumin on 2015-11-27.
 */
public class LanguageSettingAdapter extends RecyclerView.Adapter<LanguageSettingAdapter.ViewHolder> {
    private Context context;
    private List<LanguageList> languageLists;
    private int viewItemLayout;

    public LanguageSettingAdapter(Context context, List<LanguageList> languageLists, int viewItemLayout) {
        this.context = context;
        this.languageLists = languageLists;
        this.viewItemLayout = viewItemLayout;
    }

    @Override
    public int getItemCount() {
        return this.languageLists.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final LanguageList language = this.languageLists.get(i);
        viewHolder.textView.setText(language.toString());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    User.getInstance().addNewFavoriteLanguage(language);
                } else {
                    try {
                        User.getInstance().deleteFavoriteLanguage(language);
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
