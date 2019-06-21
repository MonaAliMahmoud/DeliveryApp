package com.iti.bago.deliveryapp.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref
import com.iti.bago.deliveryapp.intro.SplashActivity
import com.iti.bago.deliveryapp.menu.current_order.CurrentOrderFragment
import com.iti.bago.deliveryapp.settings.*
import kotlinx.android.synthetic.main.fragment_settings.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    var pref : SharedPref? = SharedPref()

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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "SETTINGS"

        profile.setOnClickListener(this)
//        language.setOnClickListener(this)
        faqs.setOnClickListener(this)
        about.setOnClickListener(this)
        policy.setOnClickListener(this)
        contact.setOnClickListener(this)
        logout.setOnClickListener(this)
    }
    override fun onClick(v: View?) {

        var fragment : Fragment? = null

        when (v!!.id) {
            R.id.profile -> {
                fragment = ProfileFragment()
            }
            R.id.faqs -> {
                fragment = FAQSFragment()
            }
            R.id.about -> {
                fragment = AboutFragment()
            }
            R.id.policy -> {
                fragment = PolicyFragment()
            }
            R.id.contact -> {
                fragment = ContactFragment()
            }
            R.id.logout -> {
//                pref!!.clear(context!!)
                pref!!.setIsLoggedIn(false,context!!)
                startActivity(Intent(context, SplashActivity::class.java))
            }
        }

        if (fragment != null){
            val frgMng = fragmentManager
            val frgTran = frgMng!!.beginTransaction()
            frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
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
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
