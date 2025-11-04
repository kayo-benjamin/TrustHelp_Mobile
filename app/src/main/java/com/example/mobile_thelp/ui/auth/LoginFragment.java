package com.example.mobile_thelp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.mobile_thelp.R;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnLogin).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_login_to_dashboard)
        );
        view.findViewById(R.id.btnRegister).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_login_to_register)
        );
    }
}