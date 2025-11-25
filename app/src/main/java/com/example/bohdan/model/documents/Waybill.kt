package com.example.bohdan
import java.util.Date

class Waybill(
    id: Int,
    date: Date,
    totalAmount: Double,
    sender: String,
    receiver: String,
    val carrier: String,
    val vehicleNumber: String,
    val cargoList: List<String>
) : Document(id, sender, receiver, totalAmount, date) {
    override fun toString(): String {
        return "Waybill(id=$id, total=$totalAmount, carrier='$carrier', vehicle='$vehicleNumber', cargo=$cargoList)"
    }
}