package com.iti.bago.deliveryapp.intro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iti.bago.deliveryapp.MainActivity

import com.iti.bago.deliveryapp.R
import kotlinx.android.synthetic.main.fragment_verification.*
import android.os.Message
import android.telephony.SmsManager
import android.app.PendingIntent


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [VerificationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [VerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class VerificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var firstNum : String =""
    var phoneNum : String = ""

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
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resend!!.setOnClickListener {
            val fragment = LoginFragment()
            val frgMng = fragmentManager
            val frgTran = frgMng!!.beginTransaction()
            frgTran.replace(R.id.intro_frame, fragment).addToBackStack(null).commit()
        }

        done.setOnClickListener {
//            val intent = Intent()
//
//            intent.setClass(activity!!, MainActivity::class.java)
//            activity!!.startActivity(intent)
            sendSMs()
        }
    }

    private fun sendSMs(){

        firstNum = first_num.text.toString()
        phone_num.text = phoneNum
        // Set the service center address if needed, otherwise null.
        val scAddress: String? = null
        // Set pending intents to broadcast
        // when message sent and when delivered, or set to null.
        val sentIntent: PendingIntent? = null
        val deliveryIntent: PendingIntent? = null
        // Use SmsManager.
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(
            phoneNum, scAddress, firstNum,
            sentIntent, deliveryIntent
        )


//        val smsIntent = Intent(Intent.ACTION_GET_CONTENT)
//        smsIntent.data = Uri.parse("sms to ")
//        smsIntent.type = "vnd.android-dir/mms-sms"
//        smsIntent.putExtra("address", "01229959474")
//        val smgr = SmsManager.getDefault()
//        smgr.sendTextMessage("01229959474", null, Message, null, null)
//        startActivity(smsIntent)
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
            VerificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
