package com.iti.bago.deliveryapp.menu.current_order

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
import com.iti.bago.deliveryapp.menu.pojo.ItemOrder
import com.iti.bago.deliveryapp.menu.pojo.Orders
import kotlinx.android.synthetic.main.fragment_current_order.*
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
 * [CurrentOrderFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CurrentOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CurrentOrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ItemOrderAdapter.ViewHolder>? = null

    var itemList: ArrayList<ItemOrder>? = null

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
        return inflater.inflate(R.layout.fragment_current_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Current Order"

        val orderService= ServiceBuilder.buildService(RetrofitApi::class.java)
        val requestCall = orderService.getOrder()

        requestCall.enqueue(object: Callback<java.util.ArrayList<Orders>> {

            override fun onResponse(call: Call<ArrayList<Orders>>, response: Response<ArrayList<Orders>>) {
                if (response.isSuccessful) {
//                    itemList= response.body().item_order // Use it or ignore it
                    layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    item_recycle!!.layoutManager = layoutManager
                    item_recycle!!.itemAnimator = DefaultItemAnimator()
                    adapter = ItemOrderAdapter(itemList!!, context!!)
                    item_recycle!!.adapter = adapter

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CurrentOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurrentOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
