package com.rabbi.ecommercedemofirebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.rabbi.ecommercedemofirebase.Interface.ItemClickListener;
import com.rabbi.ecommercedemofirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProdcutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

{
    public TextView txtProductName, txtDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProdcutViewHolder(@NonNull View itemView)
    {
        super(itemView);


        imageView  = itemView.findViewById(R.id.product_iamge);
        txtProductName  = itemView.findViewById(R.id.product_name);
        txtDescription  = itemView.findViewById(R.id.product_description);
        txtProductPrice  = itemView.findViewById(R.id.product_price);

    }

    public void setItenClickListener(ItemClickListener listener){

        this.listener = listener;

    }

    @Override
    public void onClick(View view)
    {

        listener.onClick(view, getAdapterPosition(),false);
    }
}
