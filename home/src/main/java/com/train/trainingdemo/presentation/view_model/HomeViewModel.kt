package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import com.train.trainingdemo.presentation.contract.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect = _effect.asSharedFlow()
    fun onIntent(intent: HomeContract.Intent) {
        when(intent){
            HomeContract.Intent.NavigateToMap -> {
                emitEffect(HomeContract.Effect.NavigateToMap)
            }
        }
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
    private fun emitEffect(effect: HomeContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}