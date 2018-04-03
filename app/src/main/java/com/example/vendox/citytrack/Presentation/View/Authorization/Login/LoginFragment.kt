package com.example.vendox.citytrack.Presentation.View.Authorization.Login

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen.RegistrationFinishPresenter
import com.example.vendox.citytrack.Presentation.View.Map.MapBoxActivity
import com.example.vendox.citytrack.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import io.reactivex.disposables.Disposable

import com.facebook.login.widget.LoginButton
import com.rengwuxian.materialedittext.MaterialEditText
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError


/**
 * Created by vendox on 26.02.18.
 */
class LoginFragment: Fragment(), LoginView{
    lateinit var username: String
    lateinit var password: String
    lateinit var btnLogin: Button
    lateinit var btnLoginVk: Button
    lateinit var btnLoginFb: Button
    lateinit var btnLoginFbFake: LoginButton
    lateinit var editTextPassword: MaterialEditText
    private val callbackManager = CallbackManager.Factory.create()

    lateinit var presenter:LoginPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.login_fragment, container, false)

        val registerUseCase = RegisterUseCase(RepositoryProvider.getAuthRepository())
        presenter = LoginPresenter(this, registerUseCase)


        btnLogin = view!!.findViewById(R.id.btn_login)
        btnLogin.setOnClickListener { view ->
            val emailRegistraion = EmailRegistration("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "123123123")
            presenter.registerEmail(emailRegistraion)
        }
        btnLoginVk = view!!.findViewById(R.id.btn_login_vk)
        btnLoginVk.setOnClickListener { view ->
            VKSdk.login(this, *arrayOf())
        }
        btnLoginFb = view!!.findViewById(R.id.btn_login_fb)
        btnLoginFb.setOnClickListener { view ->
            btnLoginFbFake.performClick()
        }
        btnLoginFbFake = view!!.findViewById<LoginButton>(R.id.btn_login_fb_fake)
        btnLoginFbFake.setReadPermissions("email", "public_profile")
        btnLoginFbFake.setFragment(this)
        btnLoginFbFake.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d("myLogs", result.toString())
                val registrationObject = SocNetRegistrationRequest("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "id123123123", "null")
                presenter.registerFb(registrationObject)
            }

            override fun onError(error: FacebookException?) {
                registrationError()
            }

            override fun onCancel() {

            }
        });

        editTextPassword = view!!.findViewById(R.id.login_password)

        return  view
    }


    override fun registrationSuccess() {
        Toast.makeText(activity, "Успех", Toast.LENGTH_SHORT).show()
        Log.d("myLogs", "Успех")
    }

    override fun registrationError() {
        Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
        Log.d("myLogs", "Ошибка")
    }

    override fun goToMap() {
        val intent = Intent(activity, MapBoxActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken?) {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        val registrationObject = SocNetRegistrationRequest("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "id123123123", "null")
                        presenter.registerVk(registrationObject)
                    }

                    override fun onError(error: VKError?) {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }

                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}