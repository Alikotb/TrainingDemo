package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.train.trainingdemo.presentation.contract.BiometricContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiometricViewModel @Inject constructor(
) : ViewModel() {
    private val _effect = MutableSharedFlow<BiometricContract.Effect>()
    val effect = _effect.asSharedFlow()
    fun onIntent(intent: BiometricContract.Intent) {
        when(intent){
            BiometricContract.Intent.NavigatePatternLock -> {
                emitEffect(BiometricContract.Effect.NavigatePatternLock)
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
    private fun emitEffect(effect: BiometricContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}