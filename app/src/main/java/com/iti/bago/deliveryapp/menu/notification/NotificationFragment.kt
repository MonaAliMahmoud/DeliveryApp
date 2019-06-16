package com.iti.bago.deliveryapp.menu.notification

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.menu.RetrofitApi
import com.iti.bago.deliveryapp.menu.ServiceBuilder
import com.iti.bago.deliveryapp.menu.pojo.Orders
import kotlinx.android.synthetic.main.fragment_notification.*
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
 * [NotificationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>? = null

    var orderList: ArrayList<Orders>? = null

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
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Notification"

        val orderService= ServiceBuilder.buildService(RetrofitApi::class.java)
        val requestCall = orderService.getOrder()

        requestCall.enqueue(object: Callback<ArrayList<Orders>> {

            override fun onResponse(call: Call<ArrayList<Orders>>, response: Response<ArrayList<Orders>>) {
                if (response.isSuccessful) {
                    orderList= response.body()
                    layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    notification_recycle!!.layoutManager = layoutManager
                    notification_recycle!!.itemAnimator = DefaultItemAnimator()
                    adapter = NotificationAdapter(orderList!!)
                    notification_recycle!!.adapter = adapter

                    (adapter as NotificationAdapter).onItemClick = {

                    }

                    Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Orders>>, t: Throwable) {
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
            NotificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
