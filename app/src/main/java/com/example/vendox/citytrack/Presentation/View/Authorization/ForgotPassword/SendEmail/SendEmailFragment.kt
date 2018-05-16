package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendEmail

import android.os.Bundle
import android.app.Fragment
import android.hardware.camera2.TotalCaptureResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendCode.SendCodeFragment

import com.example.vendox.citytrack.R
import com.rengwuxian.materialedittext.MaterialEditText

class SendEmailFragment : Fragment(), SendEmailView {

    lateinit var email: MaterialEditText
    lateinit var sendButton: Button
    lateinit var presenter: SendEmailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)


        val registerUseCase = ForgotPasswordUseCase(RepositoryProvider.getAuthRepository())
        presenter = SendEmailPresenter(this, registerUseCase)


        email = view.findViewById(R.id.forgot_password_email)
        sendButton = view.findViewById(R.id.btn_send_email)
        sendButton.setOnClickListener { view ->
            presenter.sendEmail(email.text.toString())
        }
        return view
    }

    override fun goToSendCode() {
        val sendCodeFragment = SendCodeFragment()
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
