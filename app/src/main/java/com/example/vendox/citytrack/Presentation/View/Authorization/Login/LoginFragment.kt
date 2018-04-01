package com.example.vendox.citytrack.Presentation.View.Authorization.Login

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.vendox.citytrack.Presentation.View.Map.MapBoxActivity
import com.example.vendox.citytrack.R
import com.example.vendox.citytrack.Retrofit.Request.LoginRequest
import com.example.vendox.citytrack.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.facebook.login.widget.LoginButton
import com.rengwuxian.materialedittext.MaterialEditText


/**
 * Created by vendox on 26.02.18.
 */
class LoginFragment: Fragment(){

    val client by lazy {
        RetrofitClient.create()
    }

    var disposable: Disposable? = null

    lateinit var username: String
    lateinit var password: String
    lateinit var btnLogin: Button
    lateinit var btnLoginFacebook: LoginButton
    lateinit var editTextPassword: MaterialEditText

    companion object {

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //3
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.login_fragment, container, false)

        return  rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin = view!!.findViewById<Button>(R.id.btn_login)
        btnLoginFacebook = view!!.findViewById<LoginButton>(R.id.facebook_login_btn)
        editTextPassword = view!!.findViewById<MaterialEditText>(R.id.login_password)

        btnLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, MapBoxActivity::class.java)
            startActivity(intent)
//            username = view.findViewById<EditText>(R.id.login_email).text.toString()
//                password = view.findViewById<EditText>(R.id.login_password).text.toString()
//                if (validateUsername(username) != null)  {
//                Toast.makeText(activity, validateUsername(username), Toast.LENGTH_SHORT).show()
//                            }
//                if (validatePassword(password) != null){
//                Toast.makeText(activity, validatePassword(password), Toast.LENGTH_SHORT). show()
//                            }
//                else {password = MD5.convertPassMd5(password)
//                        doLogin(LoginRequest(username, password))}
        })
    }

    override fun onStart() {
        super.onStart()
    }

     fun doLogin(request: LoginRequest){
         disposable = client.getUserData(request)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         { result -> if (result.status== "OK" ) {
                             val intent = Intent(activity, MapBoxActivity::class.java)
                             startActivity(intent)}
                             else Toast.makeText(activity, "Ooops", Toast.LENGTH_SHORT).show()
                         },
                         { error: Throwable -> error.printStackTrace()
                             Toast.makeText(activity, "false", Toast.LENGTH_SHORT).show()
                         })
     }


    private fun validateUsername(username: String): String? {
        if (username.length < 6) {
            return "Username length can not be less than 6 symbols"
        }

        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 8) {
            return "Username length can not be less than 8 symbols"
        }

        return null
    }

}