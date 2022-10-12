package com.example.crudretrofitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.crudretrofitapp.R;
import com.example.crudretrofitapp.activities.DetailActivity;
import com.example.crudretrofitapp.model.Product;

import java.util.List;

public class ProductsAdapter extends BaseAdapter {

    List<Product> products;
    Context context;
    TextView nameText;
    Button viewButton;


    public ProductsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list, parent, false);
        }

        nameText = convertView.findViewById(R.id.nameText);
        nameText.setText(products.get(position).getName());
        viewButton = convertView.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetail(products.get(position).getId());
            }
        });
        return convertView;
    }

    private void callDetail(int id){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
