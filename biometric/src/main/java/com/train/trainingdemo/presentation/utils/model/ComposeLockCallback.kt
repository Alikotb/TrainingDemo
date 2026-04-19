package com.train.trainingdemo.presentation.utils.model

interface ComposeLockCallback {
    fun onStart(dot: Dot)
    fun onDotConnected(dot: Dot)
    fun onResult(result:List<Dot>)
}