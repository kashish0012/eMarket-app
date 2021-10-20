package com.example.emarket;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText signUpEmail, signUpFirstName, signUpLastName, signUpPassword, signUpConfirmPassword;
    private ImageView closeBtn;
    private Button signUpBtn;

    private ProgressBar progressBar;
    private ConstraintLayout parentConstraintLayout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        alreadyHaveAnAccount = view.findViewById(R.id.signUp_login);
        signUpEmail = view.findViewById(R.id.signUp_email);
        signUpFirstName = view.findViewById(R.id.signUp_first_name);
        signUpLastName = view.findViewById(R.id.signUp_last_name);
        signUpPassword = view.findViewById(R.id.signUp_password);
        signUpConfirmPassword = view.findViewById(R.id.signUp_confirm_password);

        closeBtn = view.findViewById(R.id.signUp_close_button);
        signUpBtn = view.findViewById(R.id.button_signUp);

        progressBar = view.findViewById(R.id.progressBar_signUp);
        parentConstraintLayout= view.findViewById(R.id.signUp_constraint_layout);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (disableCloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }

    private void signUpUser() {
       final String firstName = signUpFirstName.getText().toString();
       final String lastName = signUpLastName.getText().toString();
       final String email = signUpEmail.getText().toString();
       final String password = signUpPassword.getText().toString();
       final String confirmPassword = signUpConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(firstName))
        {
            signUpFirstName.setError("First name Required");
            signUpFirstName.requestFocus();
        }
        else if (TextUtils.isEmpty(email))
        {
            signUpEmail.setError("Email ID Required");
            signUpEmail.requestFocus();

        }else if (!(email.matches(emailPattern)))
        {
            signUpEmail.setError("Invaild Email ID Pattern");
            signUpEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            signUpPassword.setError("Password Required");
            signUpPassword.requestFocus();
        }
        else if ((password.length() < 8))
        {
            signUpPassword.setError("Password too weak");
            signUpPassword.requestFocus();
        }
        else if (!(password.equals(confirmPassword)))
        {
            signUpConfirmPassword.setError("Password does't match");
            signUpConfirmPassword.requestFocus();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            parentConstraintLayout.setVisibility(View.GONE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Map<String, Object> userdata = new HashMap<>();
                                userdata.put("firstName", firstName);
                                userdata.put("lastName", lastName);

                                firestore.collection("USERS").document(mAuth.getUid())
                                        .set(userdata)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    CollectionReference userDataReference = firestore.collection("USERS").document(mAuth.getUid())
                                                            .collection("USER_DATA");

                                                    //// MAPS
                                                    Map<String, Object> wishlistMap = new HashMap<>();
                                                    wishlistMap.put("list_size",(long) 0);

                                                    Map<String, Object> ratingsMap = new HashMap<>();
                                                    ratingsMap.put("list_size",(long) 0);

                                                    Map<String, Object> cartMap = new HashMap<>();
                                                    cartMap.put("list_size",(long) 0);

                                                    Map<String, Object> myAddressesMap = new HashMap<>();
                                                    myAddressesMap.put("list_size",(long) 0);
                                                    //// MAPS


                                                    final List<String> documentNames = new ArrayList<>();
                                                    documentNames.add("MY_WISHLIST");
                                                    documentNames.add("MY_RATINGS");
                                                    documentNames.add("MY_CART");
                                                    documentNames.add("MY_ADDRESSES");

                                                    List<Map<String, Object>> documentFields = new ArrayList<>();
                                                    documentFields.add(wishlistMap);
                                                    documentFields.add(ratingsMap);
                                                    documentFields.add(cartMap);
                                                    documentFields.add(myAddressesMap);

                                                    for (int x=0; x<documentNames.size(); x++) {

                                                        final int finalX = x;
                                                        userDataReference.document(documentNames.get(x))
                                                                .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (finalX == documentNames.size() - 1)
                                                                    mainIntent();
                                                                } else {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    parentConstraintLayout.setVisibility(View.VISIBLE);
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }

                                                }
                                                else
                                                {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void mainIntent() {
                            progressBar.setVisibility(View.GONE);
                            parentConstraintLayout.setVisibility(View.VISIBLE);

                            if (disableCloseBtn) {
                                disableCloseBtn = false;
                            } else {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                            getActivity().finish();
                            Toast.makeText(getActivity(), "Sign Up Successful...", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
