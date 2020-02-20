package com.rabbi.ecommercedemofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rabbi.ecommercedemofirebase.Model.Products;
import com.rabbi.ecommercedemofirebase.ViewHolder.ProdcutViewHolder;
import com.rey.material.widget.EditText;
import com.squareup.picasso.Picasso;

public class SearchProductsActivity extends AppCompatActivity {

    private Button SearchBnt;
    private EditText inputText;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        // get ref
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        inputText = findViewById(R.id.search_product_name);
        SearchBnt = findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.search_list);


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SearchBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });

        //       .setQuery(reference.orderByChild("pname").startAt(SearchInput).endAt(SearchInput),Products.class).build();


    }
    @Override
    protected void onStart() {
        super.onStart();

        Query firebaseSearchQuery = ProductRef.orderByChild("pname")
                .startAt(SearchInput)
                .endAt(SearchInput + "\uf8ff");
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(firebaseSearchQuery , Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProdcutViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProdcutViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProdcutViewHolder holder , int i, @NonNull final Products model)
                    {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price "+model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                Intent intent = new Intent(getApplicationContext(), ProductDetetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProdcutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
                        ProdcutViewHolder holder = new ProdcutViewHolder(view);
                        return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
