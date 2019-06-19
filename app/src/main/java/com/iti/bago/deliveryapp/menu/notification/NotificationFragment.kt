package com.iti.bago.deliveryapp.menu.notification

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.iti.bago.deliveryapp.R
import com.iti.bago.deliveryapp.network.RetrofitApi
import com.iti.bago.deliveryapp.network.ServiceBuilder
import com.iti.bago.deliveryapp.pojo.Orders
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

    private var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager? = null
    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<NotificationAdapter.ViewHolder>? = null

    var orderList: ArrayList<Orders>? = null

    var emptylayoutFlag : Boolean = true

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

            val service = ServiceBuilder.RetrofitManager.getInstance()?.create(RetrofitApi::class.java)
            val call: Call<ArrayList<Orders>>? = service?.getOrder()
            call?.enqueue(object: Callback<ArrayList<Orders>> {

            override fun onResponse(call: Call<ArrayList<Orders>>, response: Response<ArrayList<Orders>>) {
                if (response.isSuccessful) {
                    orderList = response.body()
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity!!.applicationContext)
                    notification_recycle!!.layoutManager = layoutManager
                    notification_recycle!!.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                    adapter = NotificationAdapter(orderList!!)
                    notification_recycle!!.adapter = adapter

                    Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        emptynotifyFrame.visibility = View.VISIBLE
//                        recycleFrame.visibility = View.GONE
//                    }
                } else {
//                    emptynotifyFrame.visibility = View.VISIBLE
//                    recycleFrame.visibility = View.GONE
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Orders>>, t: Throwable) {
//                emptynotifyFrame.visibility = View.VISIBLE
//                recycleFrame.visibility = View.GONE
                Toast.makeText(context, "Failed to connect server", Toast.LENGTH_SHORT).show()
            }
        })

//        goToHome.setOnClickListener{
//            val fragment: Fragment?
//                fragment = HomeFragment()
//                val frgMng: FragmentManager? = fragmentManager
//                val frgTran = frgMng!!.beginTransaction()
//                frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
//        }
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
