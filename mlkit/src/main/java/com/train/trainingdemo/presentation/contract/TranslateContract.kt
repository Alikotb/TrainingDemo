package com.train.trainingdemo.presentation.contract

interface TranslateContract {
    data class State(
        val sourceText: String = "",
        val translatedText: String = "",
        val isDownloading: Boolean = false,
        val error: String? = null,
        val sourceLanguage: Language = languages[0], // English
        val targetLanguage: Language = languages[1], // Arabic
        val supportedLanguages: List<Language> = languages
    )

    data class Language(val name: String, val code: String)

    sealed interface Intent {
        data class UpdateSourceText(val text: String) : Intent
        object Translate : Intent
        data class SelectSourceLanguage(val language: Language) : Intent
        data class SelectTargetLanguage(val language: Language) : Intent
        object SwapLanguages : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }

    companion object {
        val languages = listOf(
            Language("English", "en"),
            Language("Arabic", "ar"),
            Language("Spanish", "es"),
            Language("French", "fr"),
            Language("German", "de"),
            Language("Chinese", "zh"),
            Language("Hindi", "hi"),
            Language("Portuguese", "pt"),
            Language("Russian", "ru"),
            Language("Japanese", "ja")
        )
    }
}
