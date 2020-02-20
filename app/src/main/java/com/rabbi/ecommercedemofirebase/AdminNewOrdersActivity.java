package com.rabbi.ecommercedemofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.rabbi.ecommercedemofirebase.Prevalent.Prevalent;


public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderlist;
    private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderlist = findViewById(R.id.order_list);
        orderlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

       // final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder
                        <AdminOrders>()
                        .setQuery(ordersRef,AdminOrders.class)
                        .build();



        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter
                = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
            {
                holder.username.setText("Name: "+model.getName());
                holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                holder.userTotalPrice.setText("Total Amount= $"+model.getTotlaAmount());
                holder.userDateTime.setText("Orders at: "+model.getDate()+ " "+ model.getTime());
                holder.userSpippingAddress.setText("Shipping Address: "+model.getAddress() +", "+model.getCity());

                holder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID = getRef(position).getKey();
                        Intent intent = new Intent(getApplicationContext(),AdminUserProductsActivity.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have you Shipped this order Products ?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0){

                                    String uidd = getRef(position).getKey();
                                    RemovedOrders(uidd);
                                }else {
                                    finish();
                                }
                            }
                        });

                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                AdminOrdersViewHolder holder = new AdminOrdersViewHolder(view);
                return holder;

            }
        };

        orderlist.setAdapter(adapter);
        adapter.startListening();

    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username, userPhoneNumber, userTotalPrice,userDateTime,userSpippingAddress;
        private Button ShowOrderBtn;
        public ItemClickListener listener;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userSpippingAddress = itemView.findViewById(R.id.order_address_city);
            ShowOrderBtn = itemView.findViewById(R.id.show_all_products_btn);

        }
    }
    // remove orders to admin
    private void RemovedOrders(String uID)
    {

        ordersRef.child(uID).removeValue();

    }
}
