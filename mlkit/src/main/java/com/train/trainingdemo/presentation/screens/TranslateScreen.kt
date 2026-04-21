package com.train.trainingdemo.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.train.trainingdemo.presentation.contract.TranslateContract
import com.train.trainingdemo.presentation.view_model.TranslateViewModel
import com.train.trainingdemo.ui.component.AppBtn
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslateScreen(
    modifier: Modifier = Modifier,
    viewModel: TranslateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is TranslateContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ML Kit Translation",
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LanguageDropdown(
                label = "Source",
                selectedLanguage = state.sourceLanguage,
                languages = state.supportedLanguages,
                onLanguageSelected = { viewModel.onIntent(TranslateContract.Intent.SelectSourceLanguage(it)) },
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { viewModel.onIntent(TranslateContract.Intent.SwapLanguages) }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Swap")
            }

            LanguageDropdown(
                label = "Target",
                selectedLanguage = state.targetLanguage,
                languages = state.supportedLanguages,
                onLanguageSelected = { viewModel.onIntent(TranslateContract.Intent.SelectTargetLanguage(it)) },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = state.sourceText,
            onValueChange = { viewModel.onIntent(TranslateContract.Intent.UpdateSourceText(it)) },
            label = { Text("Enter text to translate") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        AppBtn(
            text = if (state.isDownloading) "Downloading Model..." else "Translate",
            onClick = { viewModel.onIntent(TranslateContract.Intent.Translate) },
            enabled = !state.isDownloading && state.sourceText.isNotBlank()
        )

        if (state.isDownloading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = state.translatedText.ifEmpty { "Translation will appear here" },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdown(
    label: String,
    selectedLanguage: TranslateContract.Language,
    languages: List<TranslateContract.Language>,
    onLanguageSelected: (TranslateContract.Language) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedLanguage.name,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
            textStyle = MaterialTheme.typography.bodySmall
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(text = language.name) },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}
