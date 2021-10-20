package com.example.emarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emarket.Model.AddressesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressesActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText cityName;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmark;
    private EditText fullName;
    private EditText mobileNo;
    private Spinner stateSpinner;
    private EditText alternateMobileNo;
    private RadioGroup radioGroup;
    private RadioButton homeRadioBtn, officeRadioBtn;

    private String[] stateList;
    private String selectedState;
    private Dialog loadingDialog;
//    private String addressType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");

        ///////Loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ///////Loading Dialog

        saveBtn = findViewById(R.id.save_btn);
        stateSpinner = findViewById(R.id.state);
        cityName = findViewById(R.id.city);
        locality = findViewById(R.id.locality_or_street);
        flatNo = findViewById(R.id.flat_no);
        pincode = findViewById(R.id.add_address_pincode);
        landmark = findViewById(R.id.landmark_optional);
        fullName = findViewById(R.id.full_name);
        mobileNo = findViewById(R.id.mobile_no);
        alternateMobileNo = findViewById(R.id.alternate_mobile_no);
        radioGroup = findViewById(R.id.group_radio);
        homeRadioBtn = findViewById(R.id.radio_home);
        officeRadioBtn = findViewById(R.id.radio_office);

        stateList = getResources().getStringArray(R.array.india_states);

//        addressType = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();


//        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.india_states, android.R.layout.simple_spinner_item);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stateList[position].equals("States*")) {
                    stateSpinner.requestFocus();
                    Toast.makeText(AddAddressesActivity.this, "Please select your state!", Toast.LENGTH_SHORT).show();
                } else {
                    selectedState = stateList[position];
                    Toast.makeText(AddAddressesActivity.this, selectedState, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddress();
            }
        });
    }

    private void AddAddress() {
        String landmarkText = landmark.getText().toString();
        String city = cityName.getText().toString();
        String localityOrStreet = locality.getText().toString();
        String flatNumber = flatNo.getText().toString();
        final String pinCode = pincode.getText().toString();
        final String name = fullName.getText().toString();
        final String mobile = mobileNo.getText().toString();
        String alternateMobile = alternateMobileNo.getText().toString();

        if (TextUtils.isEmpty(city)) {
            cityName.setError("This field is required");
            cityName.requestFocus();
        } else if (TextUtils.isEmpty(localityOrStreet)) {
            locality.setError("This field is required");
            locality.requestFocus();
        } else if (TextUtils.isEmpty(flatNumber)) {
            flatNo.setError("This field is required");
            flatNo.requestFocus();
        } else if (TextUtils.isEmpty(pinCode)) {
            pincode.setError("This field is required");
            pincode.requestFocus();
        } else if (pinCode.length() != 6) {
            pincode.setError("Please provide valid pincode");
            pincode.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            fullName.setError("This field is required");
            fullName.requestFocus();
        } else if (!(mobile.length() == 10)) {
            mobileNo.setError("Please provide valid mobile number");
            mobileNo.requestFocus();
        } else {
            if (!TextUtils.isEmpty(alternateMobile)) {
                if (alternateMobile.length() != 10) {
                    alternateMobileNo.setError("Please provide valid mobile number");
                    alternateMobileNo.requestFocus();
                } else {
                    loadingDialog.show();
                    final String fullAddress = flatNumber + " " + localityOrStreet + " " + landmarkText + " " + city + " " + selectedState;

                    Map<String, Object> addAddress = new HashMap();
                    addAddress.put("list_size", (long) DBqueries.addressesModelList.size() + 1);
                    addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), name);
                    addAddress.put("address_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), fullAddress);
                    addAddress.put("pincode_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), pinCode);
                    addAddress.put("mobileNo_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), mobile);
                    addAddress.put("alternateMobileNo_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), alternateMobile);
//            addAddress.put("addressType_" +String.valueOf((long)DBqueries.addressesModelList.size() + 1), addressType);
                    addAddress.put("selected_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), true);
                    if (DBqueries.addressesModelList.size() > 0) {
                        addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                    }

                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                            .collection("USER_DATA")
                            .document("MY_ADDRESSES")
                            .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (DBqueries.addressesModelList.size() > 0) {
                                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                                }
                                DBqueries.addressesModelList.add(new AddressesModel(name, fullAddress, pinCode, mobile,true));
                                if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                    Intent deliveryIntent = new Intent(AddAddressesActivity.this, DeliveryActivity.class);
                                    startActivity(deliveryIntent);
                                } else {
                                    MyAddressesActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressesModelList.size()-1);
                                }
                                DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(AddAddressesActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });
                }
            } else {
                loadingDialog.show();
                final String fullAddress = flatNumber + " " + localityOrStreet + " " + landmarkText + " " + city + " " + selectedState;

                Map<String, Object> addAddress = new HashMap();
                addAddress.put("list_size", (long) DBqueries.addressesModelList.size() + 1);
                addAddress.put("fullname_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), name);
                addAddress.put("address_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), fullAddress);
                addAddress.put("pincode_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), pinCode);
                addAddress.put("mobileNo_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), mobile);
//            addAddress.put("addressType_" +String.valueOf((long)DBqueries.addressesModelList.size() + 1), addressType);
                addAddress.put("selected_" + String.valueOf((long) DBqueries.addressesModelList.size() + 1), true);
                if (DBqueries.addressesModelList.size() > 0) {
                    addAddress.put("selected_" + (DBqueries.selectedAddress + 1), false);
                }

                FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                        .collection("USER_DATA")
                        .document("MY_ADDRESSES")
                        .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (DBqueries.addressesModelList.size() > 0) {
                                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelected(false);
                            }
                            DBqueries.addressesModelList.add(new AddressesModel(name, fullAddress, pinCode, mobile, true));
                            DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
                            Intent deliveryIntent = new Intent(AddAddressesActivity.this, DeliveryActivity.class);
                            startActivity(deliveryIntent);
                            finish();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(AddAddressesActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        if (text.equals("States*")) {
//            //do nothing
//        } else {
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
