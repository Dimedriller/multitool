package com.dimedriller.multitool.purchases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dimedriller.multitool.R;
import com.dimedriller.multitoolmodel.purchases.Purchase;

class PurchasesAdapter extends BaseAdapter {
    private final Purchase[] mPurchases;

    PurchasesAdapter(Purchase[] purchases) {
        mPurchases = purchases;
    }

    @Override
    public int getCount() {
        return mPurchases.length;
    }

    @Override
    public Object getItem(int position) {
        return mPurchases[position];
    }

    @Override
    public long getItemId(int position) {
        return mPurchases[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Purchase purchase = mPurchases[position];

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.purchase_item, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.Name);
        nameView.setText(purchase.getName());

        TextView countView = (TextView) convertView.findViewById(R.id.Count);
        countView.setText(Integer.toString(purchase.getCount()));
        return convertView;
    }
}
