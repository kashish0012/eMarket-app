package com.example.emarket;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;

    private FrameLayout parentFrameLayout;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        registeredEmail = view.findViewById(R.id.email_forgotPassword);
        resetPasswordBtn = view.findViewById(R.id.reset_password_btn);
        goBack = view.findViewById(R.id.go_back_forgotPassword);

        emailIconContainer = view.findViewById(R.id.forgotPassword_linear_layout);
        emailIcon = view.findViewById(R.id.imageView_email_icon);
        emailIconText = view.findViewById(R.id.email_icon_text);
        progressBar = view.findViewById(R.id.progressBar_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void resetPassword() {

        String email = registeredEmail.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            registeredEmail.setError("Please provide Email ID");
            registeredEmail.requestFocus();
        }
        else if (!(email.matches(emailPattern)))
        {
            registeredEmail.setError("Invaild Email ID Pattern");
            registeredEmail.requestFocus();
        }
        else
        {
            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIconText.setVisibility(View.GONE);

            TransitionManager.beginDelayedTransition(emailIconContainer);
            emailIcon.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            resetPasswordBtn.setEnabled(false);

            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,emailIcon.getWidth()/2,emailIcon.getHeight()/2);
                                scaleAnimation.setDuration(100);
                                scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                scaleAnimation.setRepeatMode(Animation.REVERSE);
                                scaleAnimation.setRepeatCount(1);

                                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        emailIconText.setText("Recovery email sent successfully ! check your inbox");
                                        emailIconText.setTextColor(getResources().getColor(R.color.colorGreen));

                                        TransitionManager.beginDelayedTransition(emailIconContainer);
                                        emailIcon.setVisibility(View.VISIBLE);
                                        emailIconText.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                        emailIcon.setImageResource(R.drawable.green_email);
                                    }
                                });

                                emailIcon.startAnimation(scaleAnimation);
                            }
                            else
                            {
                                String error = task.getException().getMessage();


                                emailIconText.setText(error);
                                emailIconText.setTextColor(getResources().getColor(R.color.colorRed));
                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                emailIconText.setVisibility(View.VISIBLE);
                            }
                            progressBar.setVisibility(View.GONE);
                            resetPasswordBtn.setEnabled(true);
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
