package com.train.trainingdemo.app


import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.train.trainingdemo.R
import com.train.trainingdemo.navigation.AppNavHost
import com.train.trainingdemo.ui.theme.TrainingDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Configuration.getInstance().userAgentValue = packageName

        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            TrainingDemoTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        innerPadding = innerPadding,
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
fun OsmdroidMapView(
    onLocationSelected: (lat: Double, lon: Double) -> Unit
) {
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val mapView = MapView(context)

            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setBuiltInZoomControls(true)
            mapView.setMultiTouchControls(true)

            val mapController = mapView.controller
            mapController.setZoom(16.0)
            mapController.setCenter(GeoPoint(30.0444, 31.2357))

            val marker = Marker(mapView)
            val drawable = ContextCompat.getDrawable(context, R.drawable.marker)
            val bitmap = (drawable as BitmapDrawable).bitmap
            val scaledBitmap = bitmap.scale(40, 40, false)

            marker.icon = scaledBitmap.toDrawable(context.resources)


            val mapEventsReceiver = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        marker.position = it

                        mapView.overlays.remove(marker)
                        mapView.overlays.add(marker)

                        mapView.invalidate()

                        onLocationSelected(it.latitude, it.longitude)
                    }
                    return true
                }

                override fun longPressHelper(p: GeoPoint?): Boolean = false
            }

            val mapEventsOverlay = MapEventsOverlay(mapEventsReceiver)
            mapView.overlays.add(mapEventsOverlay)

            mapView
        }
    )
}



