package com.iti.bago.deliveryapp.intro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iti.bago.deliveryapp.MainActivity
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref
import com.iti.bago.deliveryapp.firebase.FireBase_Obj
import com.iti.bago.deliveryapp.firebase.Firebase_Response
//import com.iti.bago.deliveryapp.SharedPref
import com.iti.bago.deliveryapp.network.RetrofitApi
import com.iti.bago.deliveryapp.network.ServiceBuilder
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.iti.bago.deliveryapp.pojo.Login
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private  var shared : SharedPref? = SharedPref()
    val countrycode = "020"
    var phonenumber : String = ""
    var deliverylogin: Login = Login(countrycode, phonenumber)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        signin.isClickable = false
//        signup!!.setOnClickListener {
//            val fragment = SignupFragment()
//            val frgMng = fragmentManager
//            val frgTran = frgMng!!.beginTransaction()
//            frgTran.replace(R.id.intro_frame, fragment).addToBackStack(null).commit()
//        }

        confirm_signin.setOnClickListener {

            validate_txt.text = ""
            phonenumber = phone_number.text.toString()
            if (phonenumber != "") {
                phonenumber = phone_number.text.toString()
                deliverylogin.country_code = countrycode
                deliverylogin.phone_number = phonenumber

                Log.i("phone", phone_number.toString())
                val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
                val call: Call<DeliveryApi>? = service?.postToken(deliverylogin)
                call?.enqueue(object : Callback<DeliveryApi> {

                    override fun onResponse(call: Call<DeliveryApi>, response: Response<DeliveryApi>) {
                        if (response.isSuccessful) {
                            val obj = response.body()!!
                            val context1 = context!!
                            shared?.saveDeliveryObj(obj, context1)
                            val tokenResponse: String = response.body()!!.token
                            //                        Toast.makeText(context, tokenResponse, Toast.LENGTH_SHORT).show()

                            var firetoken = shared?.getFirebaseToken(context!!)
                            val delObj = shared!!.getDeliveryObj(context!!)
                            var deliveryId = delObj!!.delivery.id

                            shared!!.setIsLoggedIn(true, context1)

                            val service2 = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
                            val call2: Call<Firebase_Response>? = service2?.postFirebaseToken(FireBase_Obj(deliveryId, firetoken!!))
                            call2?.enqueue(object : Callback<Firebase_Response> {

                                override fun onResponse(call: Call<Firebase_Response>, response: Response<Firebase_Response>) {
                                    if (response.isSuccessful) {
                                        val obj2 = response.body()!!
//                                        var delId = obj2.delivery_id
//                                        var deltoken = obj2.token
//                                        var delupdate = obj2.updated_at
//                                        var delD = obj2.id
                                    } else {
                                        Toast.makeText(context, "Failed send token", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                override fun onFailure(call: Call<Firebase_Response>, t: Throwable) {
                                    Toast.makeText(context, "Failed to connect server ", Toast.LENGTH_SHORT).show()
                                }
                            })

                            val intent = Intent()
                            intent.setClass(activity!!, MainActivity::class.java)
                            activity!!.startActivity(intent)
                            activity!!.finish()
                        } else {
                            validate_txt.text = "Please Enter Valid Number"
//                            phone_number
//                            Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DeliveryApi>, t: Throwable) {
                        Toast.makeText(context, "Failed to connect server ", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else{
                validate_txt.text = "Please Enter Your Number!"
//                phone_number.text
            }
        }
    }

        // TODO: Rename method, update argument and hook method into UI event
        fun onButtonPressed(uri: Uri) {
            listener?.onFragmentInteraction(uri)
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
            if (context is OnFragmentInteractionListener) {
                listener = context
            } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
            }
        }


        override fun onDetach() {
            super.onDetach()
            listener = null
        }

        interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            fun onFragmentInteraction(uri: Uri)
        }

        companion object {

            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                LoginFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }

