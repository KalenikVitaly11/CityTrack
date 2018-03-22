package com.example.vendox.citytrack.EnterFragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.vendox.citytrack.MD5
import com.example.vendox.citytrack.MapBoxActivity
import com.example.vendox.citytrack.R
import com.example.vendox.citytrack.Retrofit.Request.RegisterRequest
import com.example.vendox.citytrack.Retrofit.RetrofitClient
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by vendox on 28.02.18.
 */
class RegistrationFragment : Fragment() {

    private val client by lazy {
        RetrofitClient.create()
    }

    var disposable: Disposable? = null

    private lateinit var name: String
    private lateinit var surname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var btnContinueReg: Button

    companion object {

        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater?.inflate(R.layout.registration_fragment, container, false)

        btnContinueReg = rootView!!.findViewById<Button>(R.id.btn_continue_registration)

        btnContinueReg.setOnClickListener(View.OnClickListener {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_from_right, R.animator.disappear, R.animator.appear, R.animator.slide_out_to_right)
                    .replace(R.id.fragment_container, RegistrationFinishFragment())
                    .addToBackStack(null)
                    .commit()
        })

        //btnReg = rootView!!.findViewById<Button>(R.id.btn_reg)
//
        //btnReg.setOnClickListener(View.OnClickListener {
//
        //    name = rootView!!.findViewById<MaterialEditText>(R.id.registration_name).text.toString()
        //    surname = rootView!!.findViewById<MaterialEditText>(R.id.registration_surname).text.toString()
        //    email = rootView!!.findViewById<MaterialEditText>(R.id.registration_email).text.toString()
        //    password = rootView!!.findViewById<MaterialEditText>(R.id.registration_password).text.toString()
        //    password = MD5.convertPassMd5(password)
        //    doRegister(RegisterRequest(name, surname, email, password))
        //})

        return rootView
    }



    private fun doRegister(request: RegisterRequest) {
        disposable = client.registration(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result.code() == 200) {
                                //val intent = Intent(activity, LoginFragment::class.java)
                                //startActivity(intent)
                                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                            } else Toast.makeText(activity, "Ooops", Toast.LENGTH_SHORT).show()
                        },
                        { error: Throwable ->
                            error.printStackTrace()
                            Log.d("myLogs", error.message)
                            Toast.makeText(activity, "false", Toast.LENGTH_SHORT).show()
                        })
    }
}
