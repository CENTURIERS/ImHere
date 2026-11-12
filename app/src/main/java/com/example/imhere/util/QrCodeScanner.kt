package com.example.imhere.util

import android.content.Context
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode


object QrCodeScanner {
    fun startScan(context: Context, onSuccess: (String) -> Unit, onError: (Exception) -> Unit = {}, onCanceled: () -> Unit = {}) {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom()
            .build()

        val scanner = GmsBarcodeScanning.getClient(context, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                barcode.rawValue?.let {text ->
                    onSuccess(text)
                }
            }
            .addOnCanceledListener {
                onCanceled()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }


}