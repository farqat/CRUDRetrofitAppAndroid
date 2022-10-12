package com.example.crudretrofitapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudretrofitapp.MainActivity;
import com.example.crudretrofitapp.R;
import com.example.crudretrofitapp.dto.ProductDto;
import com.example.crudretrofitapp.interfaces.CRUDInterface;
import com.example.crudretrofitapp.model.Product;
import com.example.crudretrofitapp.utils.Constans;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity {

    Product product;
    EditText nameText;
    EditText priceText;
    Button editButton;

    CRUDInterface crudInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent detailIntent = getIntent();
        product =(Product) detailIntent.getSerializableExtra("product");
        //Log.i("prod ", product.toString());
        nameText = findViewById(R.id.nameText);
        priceText = findViewById(R.id.priceText);
        nameText.setText(product.getName());
        priceText.setText(String.valueOf(product.getPrice()));
        editButton = findViewById(R.id.editButton);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        priceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editButton.setEnabled(buttonEnabled());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDto dto = new ProductDto(nameText.getText().toString(), Integer.valueOf(priceText.getText().toString()));
                edit(dto);
            }
        });
    }

    private void edit(ProductDto dto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Product> call = crudInterface.edit(product.getId(), dto);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("Response err: ", response.message());
                    return;
                }

                Product product = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), product.getName() + " edited", Toast.LENGTH_LONG);
                toast.show();
                callMain();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throwable err: ", t.getMessage());
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length() > 0 && priceText.getText().toString().trim().length() >0;
    }
}