package takayuki.techinstitute.jp.memoprot003.Memo;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import takayuki.techinstitute.jp.memoprot003.Memo.MemoFragment.OnListFragmentInteractionListener;
import takayuki.techinstitute.jp.memoprot003.R;

public class MemoViewAdapter extends RecyclerView.Adapter<MemoViewAdapter.ViewHolder>{

    private List<MemoItem> mValues;
    private OnListFragmentInteractionListener mListener;
    private float moveX;
    private final float ANIMATION_MIN = 80;

    public MemoViewAdapter(List<MemoItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mContentView.setText(mValues.get(position).getMemo());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mTitleView;
        public TextView mContentView;
        public MemoItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.data);
        }
    }
}
