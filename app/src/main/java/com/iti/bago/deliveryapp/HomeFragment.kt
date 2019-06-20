package com.iti.bago.deliveryapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.iti.bago.deliveryapp.pojo.DeliveryApi
import com.iti.bago.deliveryapp.tracking.BusyFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.state_buttons.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment(), OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks {

    //Map Fragment
    private var mapFragment : SupportMapFragment? = null

    private val TAG = "MapActivity"
    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private var googleApiClient: GoogleApiClient? = null

    //Map Object
    private var mMap: GoogleMap? = null
    //Current Device Location
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLocationPermissionGranted = false
    private var placesClient: PlacesClient? = null
    private var predictionList: List<AutocompletePrediction>? = null

    private var mLastKnownLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private var geocoder: Geocoder? = null
    private var mapView: View? = null
    private var DEFAULT_ZOOM = 15f

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    var Address: String = ""
    var city: String = ""
    var addresses: List<Address>? = null

    var delObj : DeliveryApi? = DeliveryApi()
    var pref: SharedPref? = SharedPref()

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
        mapView = inflater.inflate(R.layout.fragment_home, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        if (mapFragment == null){
            val frgMng = fragmentManager
            val frgTran = frgMng!!.beginTransaction()
            mapFragment = SupportMapFragment.newInstance()
            frgTran.replace(R.id.content_frame, mapFragment!!).addToBackStack(null).commit()
        }

        mapFragment!!.getMapAsync(this)
        getLocationPermission()

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
        Places.initialize(activity!!.applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(activity!!.applicationContext)

        return mapView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "HOME"

//        delObj!!.delivery.name = shared.getDeliveryObj()
        var fragment: Fragment?

        val delObj = pref!!.getDeliveryObj(context!!)
        var nameDel = delObj!!.delivery.name

        available.setBackgroundColor(resources.getColor(R.color.colorAccent))
        busy.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))

        offline.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            busy.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            available.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//            val builder = AlertDialog.Builder(activity!!)
//            builder.setMessage("You are Offline Return Available to receive Orders")
//            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
//            builder.setPositiveButton(android.R.string.yes) { _, _ ->
//                Toast.makeText(activity!!,
//                    android.R.string.yes, Toast.LENGTH_SHORT).show()
//            }
//            builder.setNegativeButton(android.R.string.no) { _, _ ->
//                Toast.makeText(activity!!,
//                    android.R.string.no, Toast.LENGTH_SHORT).show()
//            }
//            builder.show()

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.offline_dialog, null)
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(mDialogView)
            //show dialog
            val  mAlertDialog = mBuilder.show()

        }
        available.setOnClickListener{
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            busy.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }
        busy.setOnClickListener{
            fragment = BusyFragment()
            if (fragment != null){
                val frgMng = fragmentManager
                val frgTran = frgMng!!.beginTransaction()
                frgTran.replace(R.id.content_frame, fragment as BusyFragment).addToBackStack(null).commit()
            }
            it.setBackgroundColor(resources.getColor(R.color.colorAccent))
            offline.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            offline.isClickable = false
            available.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            available.isClickable = false
        }

        addressLayout.visibility = View.GONE

        confirm_btn.setOnClickListener{
            addressLayout.visibility = View.VISIBLE
            confirm_btn!!.visibility = View.GONE
            getAddress()
        }
    }

    private fun getAddress() {
        latitude = mLastKnownLocation!!.latitude
        longitude = mLastKnownLocation!!.longitude
        geocoder = Geocoder(activity!!.applicationContext, Locale.getDefault())
        addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
        Address = addresses!![0].getAddressLine(0)
        city = addresses!![0].locality
        val state = addresses!![0].adminArea
        val country = addresses!![0].countryName
        val postalCode = addresses!![0].postalCode
        Log.i("geocoders ", Address + city + state + country + postalCode)
        address.text = "$Address"+"$city"+"$state"+"$country"
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting Location Permissions")
        val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    activity!!.applicationContext,
                    COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionGranted = true
            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    permission, LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                permission, LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun init() {
        Log.d(TAG, "init: initializing")
    }

    override fun onMapReady(googleMap: GoogleMap?) {
//        mMap!!.clear()
        mMap = googleMap

        googleApiClient = GoogleApiClient.Builder(activity!!.applicationContext)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()
        googleApiClient!!.connect()

        if (mLocationPermissionGranted) {
            getDeviceLocation()
            if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity!!.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap!!.isMyLocationEnabled = true
            mMap!!.isBuildingsEnabled = true
            mMap!!.uiSettings.isMyLocationButtonEnabled = true
            init()
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(
            TAG,
            "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude
        )
        val options: MarkerOptions = MarkerOptions()
            .position(latLng)
            .title(title)
            .snippet("Here you are")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholderfilledpoint))
        mMap!!.addMarker(options)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult: called")
        mLocationPermissionGranted = false

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false
                            Log.d(TAG, "onRequestPermissionsResult: permission failed")
                            return
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted")
                    mLocationPermissionGranted = true
                    //initialize our map
                    initMap()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation() {

        mFusedLocationProviderClient!!.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mLastKnownLocation = task.result
                if (mLastKnownLocation != null) {
                    mMap!!.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                mLastKnownLocation!!.latitude,
                                mLastKnownLocation!!.longitude
                            ), DEFAULT_ZOOM
                        )
                    )
                } else {
                    val locationRequest: LocationRequest = LocationRequest.create()
                    locationRequest.interval = 10000
                    locationRequest.fastestInterval = 5000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            super.onLocationResult(locationResult)
                            if (locationResult == null) {
                                return
                            }
                            mLastKnownLocation = locationResult.lastLocation
                            mMap!!.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        mLastKnownLocation!!.latitude,
                                        mLastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM
                                )
                            )
                            mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
                        }
                    }
                    mFusedLocationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
                }
            } else {
                Toast.makeText(activity, "unable to get last location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        val latLng = LatLng(location!!.latitude, location.longitude)
        moveCamera(latLng,DEFAULT_ZOOM,"Here you are")
    }

    private fun initMap() {}

    override fun onConnected(p0: Bundle?) {
        val locationRequest = LocationRequest.create()
        if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            return
        }
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        //To change body of created functions use File | Settings | File Templates.
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
    //enable to destroy activity ????
    override fun onDestroy() {
        super.onDestroy()
//        if (fragmentManager != null)
//            fragmentManager!!.beginTransaction().remove(mapFragment!!).commit()
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
