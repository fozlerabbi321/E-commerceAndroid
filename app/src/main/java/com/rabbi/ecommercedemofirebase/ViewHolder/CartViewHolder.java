package com.rabbi.ecommercedemofirebase.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.rabbi.ecommercedemofirebase.Interface.ItemClickListener;
import com.rabbi.ecommercedemofirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtprodeuctPrice, txtPorductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtprodeuctPrice = itemView.findViewById(R.id.cart_product_price);
        txtPorductQuantity = itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View view)
    {

        itemClickListener.onClick(view, getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
