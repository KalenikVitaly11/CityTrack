package com.example.vendox.citytrack.Presentation.View.Authorization.Login

import android.app.Fragment
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity
import com.example.vendox.citytrack.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult

import com.facebook.login.widget.LoginButton
import com.rengwuxian.materialedittext.MaterialEditText
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import android.widget.TextView
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.UseCases.LoginUseCase


/**
 * Created by vendox on 26.02.18.
 */
class LoginFragment : Fragment(), LoginView {
    lateinit var username: String
    lateinit var password: String
    lateinit var btnLogin: Button
    lateinit var btnLoginVk: Button
    lateinit var btnLoginFb: Button
    lateinit var btnLoginFbFake: LoginButton
    lateinit var editTextPassword: MaterialEditText
    lateinit var editTextEmail: MaterialEditText
    lateinit var tvAgreement: TextView
    lateinit var tvForgotPassword: TextView
    lateinit var cardviewLogin:CardView

    private val callbackManager = CallbackManager.Factory.create()

    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.login_fragment, container, false)

        val registerUseCase = RegisterUseCase(RepositoryProvider.getAuthRepository())
        val loginUseCase= LoginUseCase(RepositoryProvider.getAuthRepository())
        presenter = LoginPresenter(this, registerUseCase, loginUseCase)


        editTextPassword = view!!.findViewById(R.id.login_password)
        editTextEmail = view.findViewById(R.id.login_email)

        btnLogin = view.findViewById(R.id.btn_login)
        btnLogin.setOnClickListener { clickedView ->
//            val emailRegistraion = EmailRegistration("Vitaly", "Kalenik", "kvitaly21@yandex.ru", "123123123")
            val emailLogin = EmailLogin(editTextEmail.text.toString(), editTextPassword.text.toString())
            presenter.loginEmail(emailLogin)
        }

        btnLoginVk = view.findViewById(R.id.btn_login_vk)
        btnLoginVk.setOnClickListener { clickedView ->
            VKSdk.login(this, *arrayOf())
        }
        btnLoginFb = view.findViewById(R.id.btn_login_fb)
        btnLoginFb.setOnClickListener { clickedView ->
            btnLoginFbFake.performClick()
        }

        btnLoginFbFake = view.findViewById<LoginButton>(R.id.btn_login_fb_fake)
        btnLoginFbFake.setReadPermissions("email", "public_profile")
        btnLoginFbFake.setFragment(this)
        btnLoginFbFake.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
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


        tvAgreement = view.findViewById(R.id.tv_login_agreement)
        tvForgotPassword = view.findViewById(R.id.tv_login_forgot_password)

        cardviewLogin = view.findViewById(R.id.login_cardview)


        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            //r will be populated with the coordinates of your view that area still visible.
            view.getWindowVisibleDisplayFrame(r)

            val heightDiff = view.rootView.height - (r.bottom - r.top)
            if (heightDiff > 500) { // if more than 100 pixels, its probably a keyboard...
//                Log.d("myLogs", "Фокус");
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.bottomMargin = 40
                tvAgreement.layoutParams = params

                tvAgreement.setText("Авторизуясь, вы принимаете условия пользовательского соглашения. Подробнее можно ознакомиться здесь.")
                btnLoginFb.visibility = View.GONE
                btnLoginVk.visibility = View.GONE
                tvForgotPassword.visibility = View.GONE
                cardviewLogin.layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
//                Log.d("myLogs", "Расфокус");
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.bottomMargin = 0
                tvAgreement.layoutParams = params
                tvAgreement.gravity = Gravity.CENTER_HORIZONTAL
                tvAgreement.textAlignment = View.TEXT_ALIGNMENT_CENTER

                tvAgreement.setText("Или используя один из сервисов")
                btnLoginFb.visibility = View.VISIBLE
                btnLoginVk.visibility = View.VISIBLE
                tvForgotPassword.visibility = View.VISIBLE
                cardviewLogin.layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }

        return view
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