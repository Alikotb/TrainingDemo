package com.train.trainingdemo.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.train.trainingdemo.presentation.contract.TranslateContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TranslateContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TranslateContract.Effect>()
    val effect = _effect.asSharedFlow()

    private var translator: Translator? = null

    init {
        setupTranslator()
    }

    private fun setupTranslator() {
        translator?.close()
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(_state.value.sourceLanguage.code)
            .setTargetLanguage(_state.value.targetLanguage.code)
            .build()
        translator = Translation.getClient(options)
    }

    fun onIntent(intent: TranslateContract.Intent) {
        when (intent) {
            is TranslateContract.Intent.UpdateSourceText -> {
                _state.update { it.copy(sourceText = intent.text) }
            }
            TranslateContract.Intent.Translate -> {
                translateText()
            }
            is TranslateContract.Intent.SelectSourceLanguage -> {
                _state.update { it.copy(sourceLanguage = intent.language) }
                setupTranslator()
            }
            is TranslateContract.Intent.SelectTargetLanguage -> {
                _state.update { it.copy(targetLanguage = intent.language) }
                setupTranslator()
            }
            TranslateContract.Intent.SwapLanguages -> {
                _state.update { 
                    it.copy(
                        sourceLanguage = it.targetLanguage,
                        targetLanguage = it.sourceLanguage,
                        sourceText = it.translatedText,
                        translatedText = it.sourceText
                    )
                }
                setupTranslator()
            }
        }
    }

    private fun translateText() {
        val textToTranslate = _state.value.sourceText
        if (textToTranslate.isBlank()) return

        _state.update { it.copy(isDownloading = true, error = null) }

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        translator?.downloadModelIfNeeded(conditions)
            ?.addOnSuccessListener {
                _state.update { it.copy(isDownloading = false) }
                translator?.translate(textToTranslate)
                    ?.addOnSuccessListener { translatedText ->
                        _state.update { it.copy(translatedText = translatedText) }
                    }
                    ?.addOnFailureListener { exception ->
                        _state.update { it.copy(error = exception.message) }
                        emitEffect(TranslateContract.Effect.ShowError(exception.message ?: "Translation failed"))
                    }
            }
            ?.addOnFailureListener { exception ->
                _state.update { it.copy(isDownloading = false, error = exception.message) }
                emitEffect(TranslateContract.Effect.ShowError(exception.message ?: "Model download failed"))
            }
    }

    private fun emitEffect(effect: TranslateContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    override fun onCleared() {
        super.onCleared()
        translator?.close()
    }
}
