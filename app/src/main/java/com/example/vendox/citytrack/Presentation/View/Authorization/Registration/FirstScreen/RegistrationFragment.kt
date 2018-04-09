package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.FirstScreen

import android.app.Fragment
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.vendox.citytrack.Data.RepositoryProvider
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen.RegistrationFinishFragment
import com.example.vendox.citytrack.Presentation.View.Main.MainActivity
import com.example.vendox.citytrack.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

class RegistrationFragment : Fragment(), RegistrationView {

    private lateinit var btnContinueReg: Button
    private lateinit var btnRegistrationVk:Button
    private lateinit var btnRegistrationFakeBtn:LoginButton
    private lateinit var btnRegistrationFb:Button
    private lateinit var presenter: RegistrationPresenter
    private lateinit var tvTitle: TextView
    private lateinit var tvAgreement: TextView
    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.registration_fragment, container, false)
        val registerUseCase = RegisterUseCase(RepositoryProvider.getAuthRepository())
        presenter = RegistrationPresenter(this, registerUseCase)

        btnContinueReg = rootView!!.findViewById(R.id.btn_continue_registration)
        btnContinueReg.setOnClickListener{ view ->
            presenter.continueRegistration()
        }

        btnRegistrationVk = rootView!!.findViewById(R.id.btn_registration_vk)
        btnRegistrationVk.setOnClickListener { view ->
            VKSdk.login(this, *arrayOf())
        }
        btnRegistrationFakeBtn = rootView!!.findViewById(R.id.facebook_registration_fake_btn)
        btnRegistrationFakeBtn.setReadPermissions("email", "public_profile")
        btnRegistrationFakeBtn.setFragment(this)

        btnRegistrationFakeBtn.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
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

        btnRegistrationFb = rootView.findViewById(R.id.facebook_registration_btn)
        btnRegistrationFb.setOnClickListener { view ->
            btnRegistrationFakeBtn.performClick()
        }

        tvTitle = rootView.findViewById(R.id.registration_title)
        tvAgreement = rootView.findViewById(R.id.tv_registration_agreement)

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            //r will be populated with the coordinates of your view that area still visible.
            rootView.getWindowVisibleDisplayFrame(r)

            val heightDiff = rootView.rootView.height - (r.bottom - r.top)
            if (heightDiff > 500) { // if more than 100 pixels, its probably a keyboard...
                Log.d("myLogs", "Фокус")
                tvAgreement.setText("Авторизуясь, вы принимаете условия пользовательского соглашения. Подробнее можно ознакомиться здесь.")
                btnRegistrationVk.visibility = View.GONE
                btnRegistrationFb.visibility = View.GONE

            } else {
                Log.d("myLogs", "Расфокус")
                tvAgreement.setText("Или используя один из сервисов")
                btnRegistrationVk.visibility = View.VISIBLE
                btnRegistrationFb.visibility = View.VISIBLE
            }
        }


        return rootView
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
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun continueRegistration() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.disappear, R.animator.appear, R.animator.slide_out_to_right)
                .replace(R.id.fragment_container, RegistrationFinishFragment())
                .addToBackStack(null)
                .commit()
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
