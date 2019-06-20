package com.iti.bago.deliveryapp.menu.last_order

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.pojo.Products
import kotlinx.android.synthetic.main.fragment_last_order_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LastOrderDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LastOrderDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LastOrderDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<LastOrderDetailsAdapter.ViewHolder>? = null

    var products: ArrayList<Products>? = null

    var supermarketAddress: String? = ""
    var supermarketName: String? = ""
    var customerAddress: String? = ""
    var requestTime: String? = ""
    var payment: String? = ""

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
        return inflater.inflate(R.layout.fragment_last_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "LAST TRIP"

        val arguments = arguments

        if(arguments != null) {
            supermarketName = arguments.getString("store_name")
            supermarketAddress = arguments.getString("store_address")
            customerAddress = arguments.getString("customer_address")
            requestTime = arguments.getString("created_at")
            payment = arguments.getString("payment_type")

            val product = arguments.getSerializable("products") as ArrayList<Products>

            storename.text = supermarketName
            storeaddress.text = supermarketAddress
            useraddress.text = customerAddress
            paymentType.text = ""
            tdate.text = ""
            products = product

            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity!!.applicationContext)
            product_recycle!!.layoutManager = layoutManager
            product_recycle!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            adapter = LastOrderDetailsAdapter(product)
            product_recycle!!.adapter = adapter
        }
        else{
            storename.text = ""
            storeaddress.text = ""
            useraddress.text = ""
            paymentType.text = ""
            tdate.text = ""
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
            LastOrderDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
