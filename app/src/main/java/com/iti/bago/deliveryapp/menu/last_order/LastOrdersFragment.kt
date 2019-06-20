package com.iti.bago.deliveryapp.menu.last_order

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref
import com.iti.bago.deliveryapp.network.RetrofitApi
import com.iti.bago.deliveryapp.network.ServiceBuilder
import com.iti.bago.deliveryapp.pojo.LastOrders
import kotlinx.android.synthetic.main.fragment_last_orders.*
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
 * [LastOrdersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LastOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LastOrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<LastOrderAdapter.ViewHolder>? = null

    var lorderList: ArrayList<LastOrders>? = null
    var pref: SharedPref? = SharedPref()

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
        return inflater.inflate(R.layout.fragment_last_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Last Orders"

        val delObj = pref!!.getDeliveryObj(context!!)
        var delivid = delObj!!.delivery.id

        val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
        val call: Call<ArrayList<LastOrders>>? = service?.getHistory(delivid)
        call?.enqueue(object : Callback<ArrayList<LastOrders>> {

            override fun onResponse(call: Call<ArrayList<LastOrders>>, response: Response<ArrayList<LastOrders>>) {
                if (response.isSuccessful) {
                    lorderList = response.body()
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity!!.applicationContext)
                    last_orders_recycle!!.layoutManager = layoutManager
                    last_orders_recycle!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                    adapter = LastOrderAdapter(lorderList!!, context!!)
                    last_orders_recycle!!.adapter = adapter

                    Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<LastOrders>>, t: Throwable) {
                Toast.makeText(context, "Failed to connect server", Toast.LENGTH_SHORT).show()
            }
        })
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
            LastOrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
