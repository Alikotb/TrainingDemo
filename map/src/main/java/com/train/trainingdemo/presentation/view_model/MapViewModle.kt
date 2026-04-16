package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import com.train.trainingdemo.presentation.contract.MapContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
) : ViewModel() {
    private val _effect = MutableSharedFlow<MapContract.Effect>()
    val effect = _effect.asSharedFlow()
    fun onIntent(intent: MapContract.Intent) {

//        when (intent) {
//            HomeContract.Intent.NavigateToNext -> {
////                if (getFirstTime())
//                    emitEffect(HomeContract.Effect.NavigateToHome)
////                else
////                    emitEffect(SplashContract.Effect.NavigateToOnBoarding)
//
//            }
//
//            HomeContract.Intent.SaveFirstTime -> {
////                saveFirstTime()
////                emitEffect(SplashContract.Effect.NavigateToHome)
//            }
//        }
    }
    private fun emitEffect(effect: MapContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}