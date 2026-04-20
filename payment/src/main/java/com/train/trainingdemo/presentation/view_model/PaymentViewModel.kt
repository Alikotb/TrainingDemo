//package com.train.trainingdemo.presentation.view_model
//
//import androidx.lifecycle.ViewModel
//import com.train.trainingdemo.presentation.contract.PaymentContract
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import javax.inject.Inject
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//@HiltViewModel
//class PaymentViewModel @Inject constructor(
//) : ViewModel() {
//    private val _effect = MutableSharedFlow<PaymentContract.Effect>()
//    val effect = _effect.asSharedFlow()
//    fun onIntent(intent: PaymentContract.Intent) {
//
//    }
//
//    private fun emitEffect(effect: PaymentContract.Effect) {
//        viewModelScope.launch {
//            _effect.emit(effect)
//        }
//    }
//
//}