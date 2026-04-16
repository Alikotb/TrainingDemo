package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import com.train.trainingdemo.presentation.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {
    private val _effect = MutableSharedFlow<SplashContract.Effect>()
    val effect = _effect.asSharedFlow()
    fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.NavigateToNext -> {
//                if (getFirstTime())
                    emitEffect(SplashContract.Effect.NavigateToHome)
//                else
//                    emitEffect(SplashContract.Effect.NavigateToOnBoarding)

            }

            SplashContract.Intent.SaveFirstTime -> {
//                saveFirstTime()
//                emitEffect(SplashContract.Effect.NavigateToHome)
            }
        }
    }
    private fun emitEffect(effect: SplashContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}