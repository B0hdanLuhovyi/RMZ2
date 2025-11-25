package com.example.bohdan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bohdan.Invoice
import com.example.bohdan.MainViewModel
import com.example.bohdan.Receipt
import com.example.bohdan.Waybill


@Composable
fun DocumentList(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val docs = viewModel.documents
    val context = LocalContext.current // <<< КОРИСТУЄМОСЬ LocalContext

    Column(modifier = modifier.padding(16.dp)) {
        Text("Documents (${docs.size})", style = MaterialTheme.typography.titleMedium)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(docs) { doc ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("ID: ${doc.id}")
                        Text("Type: ${doc::class.simpleName}")
                        Text("From ${doc.sender} to ${doc.receiver}")
                        Text("Amount: ${doc.totalAmount}")
                        // Відображаємо додаткові деталі
                        when (doc) {
                            is Invoice -> Text("Due: ${android.text.format.DateFormat.getDateFormat(context).format(doc.paymentDue)}")
                            is Receipt -> Text("Method: ${doc.paymentMethod}")
                            is Waybill -> Text("Cargo: ${doc.cargoList.joinToString(", ")}")
                        }
                    }
                }
            }
        }
    }
}