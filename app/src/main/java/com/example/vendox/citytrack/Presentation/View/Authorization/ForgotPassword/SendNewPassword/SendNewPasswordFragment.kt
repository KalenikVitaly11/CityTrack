package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendNewPassword

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.Login.LoginFragment

import com.example.vendox.citytrack.R
import com.rengwuxian.materialedittext.MaterialEditText


class SendNewPasswordFragment : Fragment(), SendNewPasswordView {
    lateinit var sendButton: Button
    lateinit var newPassword:MaterialEditText
    lateinit var email:MaterialEditText
    lateinit var presenter:SendNewPasswordPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_send_new_password, container, false)
        sendButton = view.findViewById(R.id.btn_send_new_password)
        newPassword = view.findViewById(R.id.forgot_new_password)
        email = view.findViewById(R.id.forgot_password_email)

        val useCase = ForgotPasswordUseCase(RepositoryProvider.getAuthRepository())
        presenter = SendNewPasswordPresenter(this, useCase)

        val bundle = this.arguments
        if (bundle != null) {
            email.setText(bundle.getString("email"))
        }

        sendButton.setOnClickListener { view ->
            presenter.sendNewPassword(email.text.toString(), newPassword.text.toString())
        }

        return view
    }

    override fun returnToLogin(){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.disappear, R.animator.appear, R.animator.slide_out_to_right)
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun showSuccess() {
        Toast.makeText(activity, "Пароль изменен", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
    }
}
