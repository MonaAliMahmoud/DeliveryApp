package com.iti.bago.deliveryapp.tracking

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.iti.bago.deliveryapp.HomeFragment
import com.iti.bago.deliveryapp.R
import kotlinx.android.synthetic.main.fragment_busy.*
import kotlinx.android.synthetic.main.fragment_current_order.market_address
import kotlinx.android.synthetic.main.fragment_current_order.market_name
import kotlinx.android.synthetic.main.state_buttons.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BusyFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BusyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BusyFragment : Fragment(), OnMapReadyCallback, TaskLoadedCallback {

    lateinit var task : AsyncTask<Void, Void, String>
    private var busyMapFragment: SupportMapFragment? = null

    private var mMap: GoogleMap? = null
    private var currentPolyline: Polyline? = null

    //Location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private val myPermissionCode: Int = 1000
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null

    private var DEFAULT_ZOOM = 14f

    var storeName: String? = ""
    var storeAddress: String? = ""
    var customerAddress: String? = ""
    var customerPhone: String? = ""
    var customerName: String? = ""

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        val busyMapView : View = inflater.inflate(R.layout.fragment_busy, container, false)
        busyMapFragment = childFragmentManager.findFragmentById(R.id.busyMap) as SupportMapFragment
        if (busyMapFragment == null){
            val frgMng = fragmentManager
            val frgTran = frgMng!!.beginTransaction()
            busyMapFragment = SupportMapFragment.newInstance()
            frgTran.replace(R.id.content_frame, busyMapFragment!!).commit()
        }
        busyMapFragment!!.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallBack()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            }
        } else {
            buildLocationRequest()
            buildLocationCallBack()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }

        return busyMapView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Navigate To Store"

        var fragment: Fragment?

        val arguments = arguments

        if(arguments != null) {
            storeName = arguments.getString("supermarket_name")
            storeAddress = arguments.getString("supermarket_address")
            customerPhone = arguments.getString("customer_phone_number")
            customerAddress = arguments.getString("customer_address")
            customerName = arguments.getString("customer_name")

//            val paymentType = arguments.getString("payment")

            marketname.text = storeName
            marketaddress.text = storeAddress
            customer_phone.text = customerPhone
        }
        else {
            marketname.text = ""
            marketaddress.text = ""
            customer_phone.text = ""
        }

        busy.setBackgroundColor(resources.getColor(R.color.colorAccent))
        available.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))

        offline.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            busy.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            available.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("You are Offline Return Available to receive Orders")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
            builder.setPositiveButton(android.R.string.yes) { _, _ ->
                Toast.makeText(activity!!,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton(android.R.string.no) { _, _ ->
                Toast.makeText(activity!!,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
        available.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            busy.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))

            fragment = HomeFragment()
            if (fragment != null){
                val frgMng = fragmentManager
                val frgTran = frgMng!!.beginTransaction()
                frgTran.replace(R.id.content_frame, fragment as HomeFragment).commit()
            }
        }
        busy.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            offline.isClickable = false
            available.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            available.isClickable = false
        }

        confirm_arrive.setOnClickListener {
            val fragment: Fragment?
            val arguments = Bundle()
            arguments.putString("customer_name", customerName)
            arguments.putString("customer_address", customerAddress)
            arguments.putString("customer_phone_number", customerPhone)
            fragment = NavigateToCustomerFragment()
            fragment.setArguments(arguments)
            val frgMng = fragmentManager
            val frgTran = frgMng!!.beginTransaction()
            frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        //Init Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                mMap!!.isMyLocationEnabled = true
            }
        } else
            mMap!!.isMyLocationEnabled = true
    }

    private fun getUrl(origin: LatLng, dest: LatLng, directionMode: String): String {
        // Origin of route
        val strOrigin = "origin=" + origin.latitude + "," + origin.longitude
        // Destination of route
        val strDest = "destination=" + dest.latitude + "," + dest.longitude
        // Mode
        val mode = "mode=$directionMode"
        // Building the parameters to the web service
        val parameters = "$strOrigin&$strDest&$mode"
        // Output format
        val output = "json"
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key)
    }

    override fun onTaskDone(vararg values: Any) {
        if (currentPolyline != null)
            currentPolyline!!.remove()
        currentPolyline = mMap!!.addPolyline(values[0] as PolylineOptions)
//        currentPolyline = mMap!!.addPolyline(PolylineOptions().width(25f)
//            .color(Color.BLUE)
//            .geodesic(true))
    }

    private fun checkLocationPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    myPermissionCode
                )
            else
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    myPermissionCode
                )
            return false
        } else
            return true
    }

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback(), TaskLoadedCallback {
            override fun onTaskDone(vararg values: Any) {
                if (currentPolyline != null)
                    currentPolyline!!.remove()
                currentPolyline = mMap!!.addPolyline(values[0] as PolylineOptions)


            }

            override fun onLocationResult(p0: LocationResult?) {
                mMap!!.clear()
                mLastLocation = p0!!.locations[p0.locations.size - 1]

                if (mMarker != null) {
                    mMarker!!.remove()
                }

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latlng2= LatLng(31.181721, 29.885290)

                val place1 = MarkerOptions().position(LatLng(31.181721, 29.885290)).title("location 1")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholderfilledpoint))

                mMarker = mMap!!.addMarker(place1)
                val place2 = MarkerOptions().position(LatLng(31.201237, 29.900850)).title("location 2")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.group43))

                mMarker = mMap!!.addMarker(place2)
                //move Camera
                if (place1.position != null && place2.position != null) {
                    FetchURL(this)
                        .execute(getUrl(place1.position, place2.position, "driving"), "driving")
                }

                mMap!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latlng2, DEFAULT_ZOOM
                    )
                )
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            myPermissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED)
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallBack()
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                            mMap!!.isMyLocationEnabled = true
                        }
                } else {
                    Toast.makeText(activity!!, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
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

    override fun onDestroy() {
        super.onDestroy()
        if(fragmentManager != null)
            fragmentManager!!.beginTransaction().remove(busyMapFragment!!).commit()
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BusyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
