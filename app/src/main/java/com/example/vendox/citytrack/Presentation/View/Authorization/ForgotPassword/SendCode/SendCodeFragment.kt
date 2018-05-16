package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendCode

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendNewPassword.SendNewPasswordFragment

import com.example.vendox.citytrack.R
import com.rengwuxian.materialedittext.MaterialEditText
import java.text.ParseException
import java.text.SimpleDateFormat


class SendCodeFragment : Fragment(), SendCodeView {
    lateinit var sendCodeButton:Button
    lateinit var code:MaterialEditText
    lateinit var email:MaterialEditText
    lateinit var presenter: SendCodePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_send_code, container, false)

        code = view.findViewById(R.id.forgot_password_code)
        email = view.findViewById(R.id.forgot_password_email)

        val bundle = this.arguments
        if (bundle != null) {
            email.setText(bundle.getString("email"))
        }
        val registerUseCase = ForgotPasswordUseCase(RepositoryProvider.getAuthRepository())
        presenter = SendCodePresenter(this, registerUseCase)

        sendCodeButton = view.findViewById(R.id.btn_send_code)
        sendCodeButton.setOnClickListener { view ->
            presenter.sendCode(email.text.toString(), code.text.toString())
        }

        return view
    }

    override fun goToChangePassword() {
        val sendCodeFragment = SendNewPasswordFragment()
        val bundle = Bundle()
        bundle.putString("email", email.text.toString())
        sendCodeFragment.setArguments(bundle)

        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.disappear, R.animator.appear, R.animator.slide_out_to_right)
                .replace(R.id.fragment_container, sendCodeFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun showError() {
        Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
    }
}
