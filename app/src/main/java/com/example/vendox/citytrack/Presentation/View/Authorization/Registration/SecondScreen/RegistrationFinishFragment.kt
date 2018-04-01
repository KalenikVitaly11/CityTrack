package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen

import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vendox.citytrack.Data.AuthApi
import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Data.Repository.AuthRepositoryImpl
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase

import com.example.vendox.citytrack.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RegistrationFinishFragment : Fragment(), RegistrationFinishView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val view = inflater.inflate(R.layout.fragment_registration_finish, container, false)

        val registerUseCase = RegisterUseCase(RepositoryProvider.getAuthRepository())
        val presenter = RegistrationFinishPresenter(this, registerUseCase)
        val emailRegistraion = EmailRegistraion("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "123123123")
        presenter.register(emailRegistraion)

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
}
