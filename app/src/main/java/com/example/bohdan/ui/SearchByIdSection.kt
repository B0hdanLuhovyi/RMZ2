package com.example.bohdan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bohdan.Document
import com.example.bohdan.MainViewModel

@Composable
fun SearchByIdSection(viewModel: MainViewModel) {
    // ... (залишається без змін)
    var query by rememberSaveable { mutableStateOf("") }
    var result by remember { mutableStateOf<Document?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Search document by ID", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("ID") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    val id = query.toIntOrNull()
                    result = if (id != null) viewModel.findDocumentById(id) else null
                },
                enabled = query.isNotBlank()
            ) {
                Text("Search")
            }
        }

        result?.let { doc ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text("Found document", style = MaterialTheme.typography.titleMedium)
                    Text("ID: ${doc.id}")
                    Text("Type: ${doc::class.simpleName}")
                    Text("From ${doc.sender} to ${doc.receiver}")
                    Text("Amount: ${doc.totalAmount}")
                    Text("Date: ${doc.date}")
                }
            }
        } ?: run {
            if (query.isNotEmpty() && query.toIntOrNull() != null)
                Text("Document not found", color = MaterialTheme.colorScheme.error)
        }
    }
}