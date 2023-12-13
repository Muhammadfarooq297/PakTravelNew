package com.example.paktravelnew.Fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.paktravelnew.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExploreFragment : Fragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


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
        val rootView: View = inflater.inflate(R.layout.fragment_explore, container, false)

        // Inside your onCreate method or onCreateView
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyCIAQSWoAC-7bSHWyUEcUdUGwUfiEWqF3U")
        }

        // Initialize Autocomplete Support Fragment
        val autocompleteSupportFragment1 =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?

        // Specify the fields you want to retrieve for selected places
        autocompleteSupportFragment1?.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )

        // Set up Autocomplete place selection listener
        autocompleteSupportFragment1?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val latLng = place.latLng
                if (latLng != null) {
                    handlePlaceSelection(latLng)
                }
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                Log.e(TAG, "Error selecting place: ${status.statusMessage}")
            }
        })

        // Set up the map (you can leave this code as it is)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        requestLocationPermission()
    }

    private fun handlePlaceSelection(selectedPlaceLatLng: LatLng) {
        if (googleMap != null) {
            updateMap(selectedPlaceLatLng)
        } else {
            Toast.makeText(context, "Map is not ready", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMap(selectedPlaceLatLng: LatLng) {
        getUserLocation { source ->
            if (source != null) {
                googleMap?.run {
                    moveCameraOnMap(latLng = source)
                    drawMarker(location = source, context = requireContext(), title = "Current Location")
                    drawRouteOnMap(
                        getString(R.string.google_maps_key),
                        source = source,
                        destination = selectedPlaceLatLng,
                        context = requireContext()
                    )
                }
            } else {
                Toast.makeText(context, "Unable to retrieve current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserLocation(callback: (LatLng?) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lng = location.longitude
                    callback(LatLng(lat, lng))
                } else {
                    callback(null)
                }
            }
        } else {
            // Permission not granted
            callback(null)
        }
    }




    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
