package com.iti.bago.deliveryapp.intro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iti.bago.deliveryapp.MainActivity
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref
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
    private  var shared : SharedPref? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        signin.isClickable = false
//        signup!!.setOnClickListener {
//            val fragment = SignupFragment()
//            val frgMng = fragmentManager
//            val frgTran = frgMng!!.beginTransaction()
//            frgTran.replace(R.id.intro_frame, fragment).addToBackStack(null).commit()
//        }

        val deliverylogin: Login = Login("020", "01000000000")

//        deliverylogin!!.country_code = countryCode.text.toString()
//        deliverylogin.phone_number = phone_number.text.toString()

        confirm_signin.setOnClickListener {

            val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
            val call: Call<DeliveryApi>? = service?.postToken(deliverylogin)
            call?.enqueue(object : Callback<DeliveryApi> {

                override fun onResponse(call: Call<DeliveryApi>, response: Response<DeliveryApi>) {
                    if (response.isSuccessful) {
                        shared?.saveDeliveryObj(response.body()!! , context!!)
                        val tokenResponse: String = response.body()!!.token
//                        Toast.makeText(context, tokenResponse, Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.setClass(activity!!, MainActivity::class.java)
                        activity!!.startActivity(intent)
                    } else {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeliveryApi>, t: Throwable) {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            })
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

