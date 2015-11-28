package kr.ac.cau.lumin.algomoa.Util.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Activity.ProblemViewActivity;
import kr.ac.cau.lumin.algomoa.Network.ProblemCrawlTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

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
        final StringBuilder problemCodeBuilder = new StringBuilder(Integer.toString(problem.getProblemNumber()));
        if (problem instanceof CodeforcesProblem) {
            problemCodeBuilder.append(((CodeforcesProblem) problem).getProblemIndex());
        }

        viewHolder.textView.setText(problemCodeBuilder.toString() + " - " + problem.getProblemName());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProblemCrawlTask problemCrawlTask = new ProblemCrawlTask(problem, context, new PostTaskListener() {
                    @Override
                    public void executeOnPostTask(Object helper) {
                        Hashtable<String, String> problemInfo = (Hashtable<String, String>) helper;
                        Intent intent = new Intent(context, ProblemViewActivity.class);
                        intent.putExtra("Description", problemInfo.get("problem_description"));
                        intent.putExtra("Input", problemInfo.get("problem_input"));
                        intent.putExtra("Output", problemInfo.get("problem_output"));
                        intent.putExtra("SampleInput", problemInfo.get("sample_input_1"));
                        intent.putExtra("SampleOutput", problemInfo.get("sample_output_1"));
                        intent.putExtra("ProblemName", problem.getProblemName());
                        intent.putExtra("ProblemCode", problemCodeBuilder.toString());
                        intent.putExtra("SiteName", problem.getSiteList());
                        context.startActivity(intent);
                    }
                });

                problemCrawlTask.execute();
            }
        });
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
