package kr.ac.cau.lumin.algomoa.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
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
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Contest;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;

/**
 * Created by Lumin on 2015-11-27.
 */
public class ContestSettingAdapter extends RecyclerView.Adapter<ContestSettingAdapter.ViewHolder> {
    private Context context;
    private List<Contest> contests;
    private int viewItemLayout;

    public ContestSettingAdapter(Context context, List<Contest> contests, int viewItemLayout) {
        this.context = context;
        this.contests = contests;
        this.viewItemLayout = viewItemLayout;
    }

    @Override
    public int getItemCount() {
        return this.contests.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Contest contest = this.contests.get(i);
        viewHolder.nameView.setText(contest.getName());
        viewHolder.timeView.setText(contest.getStartTime().toString());
        viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.codeforce_ic));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contest_itemview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView nameView;
        private AppCompatTextView timeView;
        private AppCompatImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.nameView = (AppCompatTextView) itemView.findViewById(R.id.contest_name);
            this.timeView = (AppCompatTextView) itemView.findViewById(R.id.contest_time);
            this.imageView = (AppCompatImageView) itemView.findViewById(R.id.contest_image);
        }
    }
}
