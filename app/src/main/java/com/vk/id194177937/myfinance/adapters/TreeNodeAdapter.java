package com.vk.id194177937.myfinance.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk.id194177937.myfinance.R;
import com.vk.id194177937.myfinance.core.interfaces.TreeNode;
import com.vk.id194177937.myfinance.fragments.HandbookListFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TreeNode} and makes a call to the
 * specified {@link HandbookListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TreeNodeAdapter extends RecyclerView.Adapter<TreeNodeAdapter.ViewHolder> {

    private List<? extends TreeNode> list;
    private final HandbookListFragment.OnListFragmentInteractionListener clickListener;

    public TreeNodeAdapter(List<? extends TreeNode> items, HandbookListFragment.OnListFragmentInteractionListener listener) {
        list = items;
        clickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.handbook_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final TreeNode node = list.get(position);

        holder.tvHandbookName.setText(node.getName());

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    clickListener.onListFragmentInteraction(node);
                }

                if (node.hasChildren()){
                    updateData(node.getChildren());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<? extends TreeNode> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvHandbookName;
        public final ViewGroup layoutMain;


        public ViewHolder(View view) {
            super(view);
            tvHandbookName = (TextView) view.findViewById(R.id.handbook_name);
            layoutMain = (LinearLayout) view.findViewById(R.id.handbook_main_layout);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
