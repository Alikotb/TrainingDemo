package com.train.trainingdemo.presentation.screens

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import com.train.trainingdemo.R
import com.train.trainingdemo.presentation.view_model.MapViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
    onLocationSelected: (lat: Double, lon: Double) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val packageName = context.packageName

    Configuration.getInstance().userAgentValue = packageName


    Box (
        modifier = modifier.padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ){
        AndroidView(

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
                            marker.title = "Lat: ${it.latitude}\nLon: ${it.longitude}"

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


}