package kr.ac.cau.lumin.algomoa.Util.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;

/**
 * Created by Lumin on 2015-11-28.
 */
public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ViewHolder> {
    private Context context;
    private List<Problem> problemList;
    private int viewItemLayout;

    public SimpleItemAdapter(Context context, List<Problem> problemList, int viewItemLayout) {
        this.context = context;
        this.problemList = problemList;
        this.viewItemLayout = viewItemLayout;
    }

    @Override
    public int getItemCount() {
        return this.problemList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Problem problem = this.problemList.get(i);
        String problemCode = Integer.toString(problem.getProblemNumber());
        if (problem instanceof CodeforcesProblem) {
            problemCode += ((CodeforcesProblem) problem).getProblemIndex();
        }

        viewHolder.textView.setText(problemCode + " - " + problem.getProblemName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_itemview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (AppCompatTextView) itemView.findViewById(R.id.simple_text);
        }
    }

}
