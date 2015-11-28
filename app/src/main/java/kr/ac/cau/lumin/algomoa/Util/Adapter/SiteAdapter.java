package kr.ac.cau.lumin.algomoa.Util.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.ac.cau.lumin.algomoa.Activity.ProblemSelectActivity;
import kr.ac.cau.lumin.algomoa.Activity.ReferenceViewActivity;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-27.
 */
public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {
    private Context context;
    private List<SiteList> sites;
    private int viewItemLayout;
    private PostTaskListener postTaskListener;

    public SiteAdapter(Context context, List<SiteList> sites, PostTaskListener postTaskListener, int viewItemLayout) {
        this.context = context;
        this.sites = sites;
        this.viewItemLayout = viewItemLayout;
        this.postTaskListener = postTaskListener;
    }

    public void clearData() {
        int size = this.sites.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.sites.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public int getItemCount() {
        return this.sites.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final SiteList site = this.sites.get(i);
        viewHolder.textView.setText(site.toString());
        viewHolder.imageView.setImageDrawable(site.fetchDrawable(context));

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProblemSelectActivity.class);
                intent.putExtra("SiteName", site.toString());
                context.startActivity(intent);
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProblemSelectActivity.class);
                intent.putExtra("SiteName", site.toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.refer_itemview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;
        private AppCompatImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (AppCompatTextView) itemView.findViewById(R.id.refer_text);
            this.imageView = (AppCompatImageView) itemView.findViewById(R.id.refer_image);
        }
    }
}