package com.example.emarket;


import android.content.Intent;
import android.media.Image;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.emarket.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText signInEmail, signInPassword;
    private Button signInBtn;
    private TextView forgotPassword;
    private ImageView signInCloseBtn;

    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private ProgressBar progressBarSignIn;
    private ConstraintLayout signInConstraintLayout;

    private FirebaseAuth mAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        dontHaveAnAccount = view.findViewById(R.id.login_registerHere);
        signInEmail = view.findViewById(R.id.signIn_email);
        signInPassword = view.findViewById(R.id.signIn_password);
        signInBtn = view.findViewById(R.id.button_login);
        forgotPassword = view.findViewById(R.id.textView_forgot_password);
        signInCloseBtn = view.findViewById(R.id.signIn_close_button);

        textInputLayoutEmail = view.findViewById(R.id.login_email_textInput);
        textInputLayoutPassword = view.findViewById(R.id.login_password_textInput);
        signInConstraintLayout = view.findViewById(R.id.signIn_constraint_layout);

        progressBarSignIn = view.findViewById(R.id.signIn_progressBar);
        mAuth = FirebaseAuth.getInstance();

        if (disableCloseBtn) {
            signInCloseBtn.setVisibility(View.GONE);
        } else {
            signInCloseBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInUser();
            }
        });

        signInCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void signInUser() {
        String email = signInEmail.getText().toString();
        String password = signInPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            textInputLayoutEmail.setError("Enter Email ID First");

        }else if (!(email.matches(emailPattern)))
        {
            textInputLayoutEmail.setError("Enter valid Email ID");
        }
        else if (TextUtils.isEmpty(password))
        {
            textInputLayoutPassword.setError("Enter your Password.");
        }
        else if ((password.length() < 8))
        {
            textInputLayoutPassword.setError("Enter correct Password.");
        }
        else
        {
            progressBarSignIn.setVisibility(View.VISIBLE);
            signInConstraintLayout.setVisibility(View.GONE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                progressBarSignIn.setVisibility(View.GONE);
                                if (disableCloseBtn) {
                                    disableCloseBtn = false;
                                } else {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                                getActivity().finish();
                                Toast.makeText(getActivity(), "Welcome. Sign In successful.", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                progressBarSignIn.setVisibility(View.GONE);
                                signInConstraintLayout.setVisibility(View.VISIBLE);

                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
