package com.example.bohdan.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bohdan.MainViewModel
import com.example.bohdan.R


@Composable
fun AccountingApp(viewModel: MainViewModel) {
    var permissionsGranted by remember { mutableStateOf(false) }

    if (!permissionsGranted) {
        RequestPermissions(
            onPermissionsGranted = {
                permissionsGranted = true
            }
        )
    } else {
        Text("Всі дозволи отримані")
    }
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SearchByIdSection(viewModel)
                Divider()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.document_icon),
                        contentDescription = "Document Icon",
                        modifier = Modifier.size(48.dp).padding(end = 8.dp)
                    )
                    Text("Document Management", style = MaterialTheme.typography.headlineSmall)
                }
                DocumentForm(viewModel)
                Divider()
            }
            DocumentList(viewModel, modifier = Modifier.weight(1f))
        }
    }
}