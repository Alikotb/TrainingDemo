package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import com.train.trainingdemo.presentation.contract.MLKitContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class MLKitViewModel @Inject constructor(
) : ViewModel() {
    private val _effect = MutableSharedFlow<MLKitContract.Effect>()
    val effect = _effect.asSharedFlow()
    fun onIntent(intent: MLKitContract.Intent) {
        when (intent) {
            MLKitContract.Intent.NavigateToTranslator -> {
                emitEffect(MLKitContract.Effect.NavigateToTranslator)
            }
        }

    }

    private fun emitEffect(effect: MLKitContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}