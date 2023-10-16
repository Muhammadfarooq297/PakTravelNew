package com.example.paktravelnew.Fragments

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.paktravelnew.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode
import com.maps.route.extensions.drawMarker
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var mAutoCompleteRequestCode: Int = 1
class ExploreFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_explore, container, false)

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyC6kR16M9IDJ4WawIFKOtuvnjgbbQ5fnYY")
        }

        // Initialize Autocomplete Support Fragment
        val autocompleteSupportFragment1 =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?

        // Specify the fields you want to retrieve for selected places
        autocompleteSupportFragment1!!.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )
//        val mFields = listOf(
//            Place.Field.ID,
//            Place.Field.NAME,
//            Place.Field.LAT_LNG,
//            Place.Field.ADDRESS,
//            Place.Field.ADDRESS_COMPONENTS
//        )
//        val intent =
//            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, mFields).build(
//                this.requireContext()
//            )
//        startActivityForResult(intent, mAutoCompleteRequestCode)
        // Set up Autocomplete place selection listener
        autocompleteSupportFragment1.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place, e.g., display it on the map
                val latLng = place.latLng
                if (latLng != null) {
//                    updateMap(latLng)
                    handlePlaceSelection(latLng)
                    // You can update the map or perform other actions here
                }
            }

            override fun onError(status: Status) {
                // Handle any errors in place selection
                Log.e(TAG, "Error selecting place: ${status.statusMessage}")
            }
        })

        // Set up the map (you can leave this code as it is)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        return rootView
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        // Customize the map as needed and add markers or other map-related functionality.
        val source = LatLng(31.490127, 74.316971) //starting point (LatLng)
        val destination = LatLng(31.474316, 74.316112) // ending point (LatLng)


        googleMap?.run {
            //if you want to move the map on specific location
            moveCameraOnMap(latLng = source)

            //if you want to drop a marker of maps, call it
            drawMarker(location = source, context = requireContext(), title = "test marker")

            //if you only want to draw a route on maps
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps
            drawRouteOnMap(
                getString(R.string.google_maps_key),
                source = source,
                destination = destination,
                context = requireContext()
            )

            //if you only want to draw a route on maps and also need the ETAs
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps


        }
    }
    private fun handlePlaceSelection(selectedPlaceLatLng: LatLng) {
        if (googleMap != null) {
            // Call the updateMap function to draw the route and update the map
            updateMap(selectedPlaceLatLng)
        } else {
            // Handle the case where the map is not ready yet
            Toast.makeText(context, "Map is not ready", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to update the map with a line and center on the selected place
    private fun updateMap(selectedPlaceLatLng: LatLng) {
        // Draw a route from the user's current location to the selected place
        googleMap?.clear()
        val source = LatLng(31.490127, 74.316971) // User's current location (change this to the actual location)


        googleMap?.run {
            //if you want to move the map on specific location
            moveCameraOnMap(latLng = source)
            val radiusInMeters = 1000.0  // 1 kilometer

// Number of random markers you want to add
            val numberOfRandomMarkers = 5

            //Generate random locations
            val randomLocations = generateRandomLocations(source, radiusInMeters / 1000.0, numberOfRandomMarkers)
            val randomLocationsb = generateRandomLocations(source, radiusInMeters / 1000.0, numberOfRandomMarkers)
            val vehicleIcon = BitmapDescriptorFactory.fromResource(R.drawable.car)

// The original vehicle icon
            val originalVehicleIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.car)

// Define the scaling factor (e.g., 0.5 for 50% size)
            val scale = 0.1f

// Convert the original icon to a Bitmap
            val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.car)

// Calculate the new width and height based on the scaling factor
            val width = (originalBitmap.width * scale).toInt()
            val height = (originalBitmap.height * scale).toInt()

// Create a smaller version of the icon
            val smallerBitmap: Bitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)

// Convert the smaller Bitmap back to a BitmapDescriptor
            val smallerVehicleIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallerBitmap)

// Add markers for the random locations on the map
            randomLocations.forEach { location ->
                drawMarker(location = location, context = requireContext(), title = "Random Marker")

            }
            randomLocationsb.forEach { location ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .icon(smallerVehicleIcon)
                        .title("Custom Vehicle")
                )
            }

            val radiusInKilometers = radiusInMeters / 1000.0

            generateTouristLocations(source, radiusInKilometers, numberOfRandomMarkers) { touristLocations ->

                logDebug("sssThis is a debug message to check if it's working.")
                // Add markers for the tourist locations on the map
                touristLocations.forEach { location ->
                    drawMarker(location = location, context = requireContext(), title = "tourist Marker")
                    println(location)
                    Log.e(TAG, "ExceptionL: tessssetestest${location}")
                }
            }




            //if you want to drop a marker of maps, call it
            drawMarker(location = source, context = requireContext(), title = "test marker")

            //if you only want to draw a route on maps
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps
            drawRouteOnMap(
                getString(R.string.google_maps_key),
                source = source,
                destination = selectedPlaceLatLng,
                context = requireContext()
            )

            //if you only want to draw a route on maps and also need the ETAs
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps


        }
    }
    private fun generateRandomLocations(center: LatLng, radius: Double, count: Int): List<LatLng> {
        val randomLocations = mutableListOf<LatLng>()

        // Earth radius in kilometers (you can also use 3956 miles for miles)
        val earthRadius = 6371.0

        for (i in 1..count) {
            // Generate a random distance and angle
            val randomDistance = Math.random() * radius
            val randomAngle = Math.random() * 2 * Math.PI

            // Calculate the new latitude and longitude
            val latitude = center.latitude + (180 / Math.PI) * (randomDistance / earthRadius) * Math.cos(randomAngle)
            val longitude = center.longitude + (180 / Math.PI) * (randomDistance / earthRadius) * Math.sin(randomAngle)

            randomLocations.add(LatLng(latitude, longitude))
        }

        return randomLocations
    }
    // A function to generate tourist locations near a center point within a radius
    @SuppressLint("MissingPermission")
    private fun generateTouristLocations(center: LatLng, radius: Double, count: Int, callback: (List<LatLng>) -> Unit) {
        val touristLocations = mutableListOf<LatLng>()
        logDebug("This is a debug message to check if it's working.")
        Log.e(TAG, "Exception: tessssetestest")

        // Create a list of fields
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES)

        // Create a request object
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        // Get a PlacesClient instance
        val placesClient = Places.createClient(requireContext())

        // Make the request
        placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
            // Loop through the results
            for (placeLikelihood in response.placeLikelihoods) {
                // Get the place object
                val place = placeLikelihood.place

                // Get the location and types of the place
                val latLng = place.latLng
                val types = place.types

                // Check if the place is a tourist attraction and within the radius
                if (types != null && types.contains(Place.Type.TOURIST_ATTRACTION) && latLng?.let {
                        distanceBetween(center,
                            it
                        )
                    }!! <= radius) {
                    // Add the location to the list
                    touristLocations.add(latLng)
                }
            }

            // Shuffle the list of tourist locations
            touristLocations.shuffle()

            // Return the list of tourist locations using the callback
            callback(touristLocations.take(count))
        }.addOnFailureListener { exception ->
            // Handle exception
            Log.e(TAG, "Exception: ${exception.message}")
        }
    }

    // A helper function to calculate the distance between two locations in meters
    private fun distanceBetween(from: LatLng, to: LatLng): Double {
        val fromLat = Math.toRadians(from.latitude)
        val fromLng = Math.toRadians(from.longitude)
        val toLat = Math.toRadians(to.latitude)
        val toLng = Math.toRadians(to.longitude)

        // Earth radius in meters
        val earthRadius = 6371000.0

        // Haversine formula
        val dLat = toLat - fromLat
        val dLng = toLng - fromLng
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(fromLat) * Math.cos(toLat) * Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }fun logDebug(message: String) {
        Log.d("YourTag", message)
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