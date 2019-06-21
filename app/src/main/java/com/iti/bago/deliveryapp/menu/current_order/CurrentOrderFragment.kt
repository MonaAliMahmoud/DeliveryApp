package com.iti.bago.deliveryapp.menu.current_order

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.SharedPref
import com.iti.bago.deliveryapp.menu.last_order.LastOrderAdapter
import com.iti.bago.deliveryapp.network.RetrofitApi
import com.iti.bago.deliveryapp.network.ServiceBuilder
import com.iti.bago.deliveryapp.pojo.ItemOrder
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.iti.bago.deliveryapp.tracking.BusyFragment
import kotlinx.android.synthetic.main.fragment_current_order.*
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

    private var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<ItemOrderAdapter.ViewHolder>? = null

    var itemList: ArrayList<ItemOrder>? = null
    var delivobj: DeliveryApi = DeliveryApi()

    var pref: SharedPref = SharedPref()
    var supermarketAddress: String? = ""
    var supermarketName: String? = ""
    var customerAddress: String? = ""
    var customerPhone: String? = ""
    var customerName: String? = ""

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
        activity!!.title = "CURRENT ORDER"



        val arguments = arguments
        var flag = 0

        if(flag == 0 && arguments == null){
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.not_register, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(mDialogView)
            //show dialog
            val  mAlertDialog = mBuilder.show()
            flag = 1
        }

        if(arguments != null) {

            supermarketName = arguments.getString("supermarket_name")
            supermarketAddress = arguments.getString("supermarket_address")
            customerAddress = arguments.getString("customer_address")
            customerPhone = arguments.getString("customer_phone_number")
            customerName = arguments.getString("customer_name")

//            val paymentType = arguments.getString("payment")
            val items = arguments.getSerializable("itemOrder") as ArrayList<ItemOrder>

            Log.i("Items", items.toString())

            market_name.text = supermarketName
            market_address.text = supermarketAddress
            customer_address.text = customerAddress
            payment.text = ""
            time_date.text = ""
            itemList = items

            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity!!.applicationContext)
            item_recycle!!.layoutManager = layoutManager
            item_recycle!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            adapter = ItemOrderAdapter(items, activity!!.applicationContext)
            item_recycle!!.adapter = adapter
        }
        else{
            market_name.text = ""
            market_address.text = ""
            customer_address.text = ""
            payment.text = ""
            time_date.text = ""
            itemList
        }

        startTrip!!.setOnClickListener{
            val fragment: Fragment?
            val arguments = Bundle()
            arguments.putString("supermarket_name", supermarketName)
            arguments.putString("supermarket_address", supermarketAddress)
            arguments.putString("customer_phone_number", customerPhone)
            arguments.putString("customer_name", customerName)
            arguments.putString("customer_address", customerAddress)

            val delObj = pref.getDeliveryObj(context!!)
            var delivid = delObj!!.delivery.id
            var delivStatus = delObj.delivery.status
            delivStatus = "Busy"
            val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
            val call: Call<String>? = service?.changeStatus(delivStatus, delivid)
            call?.enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {

                        Toast.makeText(context, "Update Successfully: Busy", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context, "Failed to connect server", Toast.LENGTH_SHORT).show()
                }
            })

            fragment = BusyFragment()
            fragment.setArguments(arguments)
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
