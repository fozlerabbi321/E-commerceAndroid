package com.rabbi.ecommercedemofirebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rabbi.ecommercedemofirebase.Interface.ItemClickListener;
import com.rabbi.ecommercedemofirebase.Model.AdminOrders;
import com.rabbi.ecommercedemofirebase.Model.Cart;
import com.rabbi.ecommercedemofirebase.Prevalent.Prevalent;
import com.rabbi.ecommercedemofirebase.ViewHolder.CartViewHolder;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    private DatabaseReference cartListRef;
    private String userID = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);




        userID = getIntent().getStringExtra("uid");
        cartListRef = FirebaseDatabase.getInstance().getReference().
                child("Cart List")
                .child("Admin View").child(userID).child("Products");

        productsList = findViewById(R.id.products_list);
        productsList.setLayoutManager(new LinearLayoutManager(this));

    }
        @Override
        protected void onStart() {
            super.onStart();

            // check oder mehot


            // viewholder

            FirebaseRecyclerOptions<Cart> options =
                    new FirebaseRecyclerOptions.Builder<Cart>()
                            .setQuery(cartListRef,Cart.class)
                            .build();

            FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                    = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull final Cart model)
                {

                    holder.txtProductName.setText(model.getPname());
                    holder.txtprodeuctPrice.setText("Price "+model.getPrice()+"$");
                    holder.txtPorductQuantity.setText("Quantity = "+model.getQuantity());


                }

                @NonNull
                @Override
                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                {

                    View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                    CartViewHolder holder = new CartViewHolder(view);
                    return holder;

                }
            };

            productsList.setAdapter(adapter);
            adapter.startListening();


        }

    }
