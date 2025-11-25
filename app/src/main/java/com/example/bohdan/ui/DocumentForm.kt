package com.example.bohdan.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext // <<< ВАЖЛИВИЙ ІМПОРТ 1
import androidx.compose.ui.unit.dp
import com.example.bohdan.MainViewModel
import com.example.bohdan.ui.theme.BohdanTheme
import java.util.*

// Додаємо анотації для експериментальних API
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentForm(viewModel: MainViewModel) {
    var sender by rememberSaveable { mutableStateOf("") }
    var receiver by rememberSaveable { mutableStateOf("") }
    var total by rememberSaveable { mutableStateOf("") }
    var docType by rememberSaveable { mutableStateOf("Invoice") }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current // <<< КОРИСТУЄМОСЬ LocalContext

    val calendar = remember { Calendar.getInstance() }
    var dueDate by rememberSaveable { mutableStateOf(calendar.time) }


    // Підказка (Tooltip)
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = { Text("Enter details to create a new accounting document") },
        state = rememberTooltipState()
    ) {
        Text("Documents creation ", style = MaterialTheme.typography.titleMedium)
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = sender,
                onValueChange = { sender = it },
                label = { Text("Send") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = receiver,
                onValueChange = { receiver = it },
                label = { Text("Recive") },
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = total,
            onValueChange = { total = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        // Додаємо вибір дати (Date Picker) для Invoice
        if (docType == "Invoice") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Використовуємо android.text.format.DateFormat та context
                Text("Due Date: ${android.text.format.DateFormat.getDateFormat(context).format(dueDate)}")
                Button(onClick = { showDatePicker = true }) {
                    Text("Select Date")
                }
            }
        }


        // Діалог для вибору дати
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                Column(Modifier.padding(24.dp)) {
                    // Це повна реалізація DatePicker для Material 3
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = calendar.timeInMillis
                    )
                    DatePicker(state = datePickerState)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Selected Date: ${android.text.format.DateFormat.getDateFormat(context).format(Date(datePickerState.selectedDateMillis ?: System.currentTimeMillis()))}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    // Оновлюємо dueDate при закритті діалогу
                    DisposableEffect(datePickerState.selectedDateMillis) {
                        if (datePickerState.selectedDateMillis != null) {
                            dueDate = Date(datePickerState.selectedDateMillis!!)
                        }
                        onDispose {}
                    }
                }
            }
        }


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Invoice", "Receipt", "Waybill").forEach { type ->
                FilterChip(
                    selected = docType == type,
                    onClick = {
                        docType = type
                        if (type != "Invoice") showDatePicker = false
                    },
                    label = { Text(type) }
                )
            }
        }

        Button(
            onClick = {
                if (sender.isNotBlank() && receiver.isNotBlank() && total.toDoubleOrNull() != null) {
                    showConfirmationDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create $docType")
        }

        Text(
            text = "Total Amount: ${viewModel.totalAmount}",
            style = MaterialTheme.typography.bodyLarge
        )
    }

    // Діалогове вікно (Confirmation Dialog)
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            title = { Text("Confirm Creation") },
            text = { Text("Are you sure you want to create a $docType for amount $total?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val sum = total.toDoubleOrNull() ?: 0.0
                        when (docType) {
                            "Invoice" -> viewModel.createInvoice(sender, receiver, sum, dueDate)
                            "Receipt" -> viewModel.createReceipt(sender, receiver, sum, "Bank transfer")
                            "Waybill" -> viewModel.createWaybill(sender, receiver, listOf("Cargo 1", "Cargo 2"), sum)
                        }
                        sender = ""
                        receiver = ""
                        total = ""
                        showConfirmationDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}