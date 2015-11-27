package kr.ac.cau.lumin.algomoa.Util;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Activity.ReferenceViewActivity;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;

/**
 * Created by CAUCSE on 2015-11-27.
 */
public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ViewHolder> {
    private Context context;
    private List<LanguageRefer> refers;
    private int viewItemLayout;
    private PostTaskListener postTaskListener;

    public ReferenceAdapter(Context context, List<LanguageRefer> refers, PostTaskListener postTaskListener, int viewItemLayout) {
        this.context = context;
        this.refers = refers;
        this.viewItemLayout = viewItemLayout;
        this.postTaskListener = postTaskListener;
    }

    public void clearData() {
        int size = this.refers.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.refers.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public int getItemCount() {
        return this.refers.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final LanguageRefer refer = this.refers.get(i);
        viewHolder.textView.setText(refer.getLanguage().toString() + " - " + refer.getReferenceName());
        viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.codeforce_ic));

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReferenceViewActivity.class);
                intent.putExtra("Language", refer.getLanguage().toString());
                intent.putExtra("Reference", refer.getReferenceName());
                intent.putExtra("URL", refer.getRequestURL());
                context.startActivity(intent);
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReferenceViewActivity.class);
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
