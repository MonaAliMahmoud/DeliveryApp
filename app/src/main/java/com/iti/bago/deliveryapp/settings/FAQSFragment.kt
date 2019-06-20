package com.iti.bago.deliveryapp.settings

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView

import com.iti.bago.deliveryapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FAQSFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FAQSFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FAQSFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    internal var expandableListView1: ExpandableListView? = null
    internal var expandableListView2: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null

    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val Lorem_ipsum1 = ArrayList<String>()
            Lorem_ipsum1.add("First, you need to add a product to your cart, add a product to your cart by selecting the cart icon, find your cart at the menu and complete your purchase.\n" +
                    "How to change.")

            val Lorem_ipsum2 = ArrayList<String>()
            Lorem_ipsum2.add("First, you need to add a product to your cart, add a product to your cart by selecting the cart icon, find your cart at the menu and complete your purchase.\n" +
                    "How to change.")

            val Lorem_ipsum3 = ArrayList<String>()
            Lorem_ipsum3.add("First, you need to add a product to your cart, add a product to your cart by selecting the cart icon, find your cart at the menu and complete your purchase.\\n\" +\n" +
                    "                    \"How to change.")

            listData["How to make orders?"] = Lorem_ipsum1
            listData["How to change supermarkets?"] = Lorem_ipsum2
            listData["How to set my address?"] = Lorem_ipsum3

            return listData
        }

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
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "FAQS"

        expandableListView1 = activity!!.findViewById(R.id.expandableListView1)
        if (expandableListView1 != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ExpandableListAdapter(activity!!.applicationContext, titleList as ArrayList<String>, listData)
            expandableListView1!!.setAdapter(adapter)
        }

        expandableListView2 = activity!!.findViewById(R.id.expandableListView2)
        if (expandableListView2 != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = ExpandableListAdapter(activity!!.applicationContext, titleList as ArrayList<String>, listData)
            expandableListView2!!.setAdapter(adapter)
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
            FAQSFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
