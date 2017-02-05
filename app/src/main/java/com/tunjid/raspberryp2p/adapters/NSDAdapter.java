package com.tunjid.raspberryp2p.adapters;

import android.net.nsd.NsdServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helloworld.utils.baseclasses.BaseRecyclerViewAdapter;
import com.helloworld.utils.baseclasses.BaseViewHolder;
import com.helloworld.utils.text.SpanUtils;
import com.tunjid.raspberryp2p.R;

import java.util.List;

/**
 * Adapter for showing open NSD services
 * <p>
 * Created by tj.dahunsi on 2/4/17.
 */

public class NSDAdapter extends BaseRecyclerViewAdapter<NSDAdapter.NSDViewHolder, NSDAdapter.ServiceClickedListener> {

    private List<NsdServiceInfo> infoList;

    public NSDAdapter(List<NsdServiceInfo> list) {
        this.infoList = list;
    }

    @Override
    public NSDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_nsd_list, parent, false);
        return new NSDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NSDViewHolder holder, int position) {
        holder.bind(infoList.get(position), adapterListener);
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

   public interface ServiceClickedListener extends BaseRecyclerViewAdapter.AdapterListener {
        void onServiceClicked(NsdServiceInfo serviceInfo);
    }

    static class NSDViewHolder extends BaseViewHolder<NSDAdapter.ServiceClickedListener>
            implements View.OnClickListener {

        NsdServiceInfo serviceInfo;
        TextView textView;

        NSDViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        void bind(NsdServiceInfo info, ServiceClickedListener listener) {
            serviceInfo = info;
            adapterListener = listener;

            SpanUtils.SpanBuilder spanBuilder = SpanUtils.spanBuilder(itemView.getContext(), info.getServiceName());
            spanBuilder.appendNewLine().appendCharsequence(info.getHost().getHostAddress());

            textView.setText(spanBuilder.build());
        }

        @Override
        public void onClick(View v) {
            adapterListener.onServiceClicked(serviceInfo);
        }
    }
}