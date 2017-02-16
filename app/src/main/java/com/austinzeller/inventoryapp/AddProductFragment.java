package com.austinzeller.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProductFragment extends AppCompatActivity {

    Button submitB;
    EditText editName, editPrice, editQuantity, editPicture;
    ProductDbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        myDb = new ProductDbHelper(this);

        editName = (EditText) findViewById(R.id.edit_name_edit_text);
        editPrice = (EditText) findViewById(R.id.edit_price_edit_text);
        editQuantity = (EditText) findViewById(R.id.edit_quantity_edit_text);
        editPicture = (EditText) findViewById(R.id.edit_picture_edit_text);

        submitB = (Button) findViewById(R.id.submit_product_button);

        submitAdd();
    }

    public void submitAdd() {
        submitB.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String checkName = editName.getText().toString();
                        String checkPicture = editPicture.getText().toString();
                        String checkQuantityField = editQuantity.getText().toString();
                        String checkPriceField = editPrice.getText().toString();

                        String checkNameSpace = checkName.replace(" ", "");
                        String checkPictureSpace = checkPicture.replace(" ", "");
                        String checkQuantityFieldSpace = checkQuantityField.replace(" ", "");
                        String checkPriceFieldSpace = checkPriceField.replace(" ", "");

                        if (checkNameSpace.matches("") || checkPictureSpace.matches("") ||
                                checkQuantityFieldSpace.matches("") ||
                                checkPriceFieldSpace.matches("")) {
                            Toast.makeText(AddProductFragment.this, "Error: Double check the " +
                                    "data you have entered.", Toast.LENGTH_SHORT).show();
                        } else {
                            Integer checkQuantity = Integer.parseInt(editPrice.getText().toString());
                            Integer checkPrice = Integer.parseInt(editQuantity.getText().toString());

                            if (checkQuantity < 0 || checkPrice < 0) {
                                Toast.makeText(AddProductFragment.this, "Error: Please double check " +
                                        "the data you have entered.", Toast.LENGTH_SHORT).show();
                            } else {
                                boolean isInserted = myDb.insertData(editName.getText().toString(),
                                        Integer.parseInt(editPrice.getText().toString()),
                                        Integer.parseInt(editQuantity.getText().toString()),
                                        editPicture.getText().toString(), 0);
                                if (isInserted) {
                                    Toast.makeText(AddProductFragment.this, "Data entered",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddProductFragment.this, "Data not entered",
                                            Toast.LENGTH_SHORT).show();
                                }
                                Intent returnToMainIntent = new Intent(AddProductFragment.this,
                                        MainActivity.class);
                                startActivity(returnToMainIntent);
                            }
                        }
                    }
                }
        );
    }
}
