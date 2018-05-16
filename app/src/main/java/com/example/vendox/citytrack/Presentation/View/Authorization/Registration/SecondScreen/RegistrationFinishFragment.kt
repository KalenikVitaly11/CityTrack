package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen

import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity

import com.example.vendox.citytrack.R


class RegistrationFinishFragment : Fragment(), RegistrationFinishView {

    lateinit var registerBtn:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val view = inflater.inflate(R.layout.fragment_registration_finish, container, false)

        val registerUseCase = RegisterUseCase(RepositoryProvider.getAuthRepository())
        val presenter = RegistrationFinishPresenter(this, registerUseCase)

        registerBtn = view.findViewById<Button>(R.id.btn_registration)
        registerBtn.setOnClickListener({view ->
            val emailRegistraion = EmailRegistration("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "123123123")
            presenter.registerEmail(emailRegistraion)
        })

        return view
    }

    override fun registrationError() {
        Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
        Log.d("myLogs", "Ошибка")
    }

    override fun registrationSuccess() {
        Toast.makeText(activity, "Успех", Toast.LENGTH_SHORT).show()
        Log.d("myLogs", "Успех")
    }

    override fun goToMap() {
        val intent = Intent(activity, MapBoxActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
