package com.example.imhere.util

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy

object ConnectionUtil {
    private const val SERVICE_ID = "com.example.imhere"
    private var client :ConnectionsClient? = null

    // Callback student
    private val studentConnectionCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            client?.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, resolution: ConnectionResolution) {
            if (resolution.status.isSuccess) {
                val dane = "To ja andrzej"
                client?.sendPayload(endpointId, Payload.fromBytes(dane.toByteArray()))
            }
        }

        override fun onDisconnected(endpointId: String) {
            Log.d("Nearby", "Rozlaczono")
        }

    }

    // Pracownik znalazł odpowiedz dla studenta i odsyła posłańca, ktory dowiaduje sie co chce nauczyciel
    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {

        override fun onEndpointFound(endpointId: String,  info: DiscoveredEndpointInfo) {
            client?.requestConnection("Student_123456" ,endpointId, studentConnectionCallback)
        }

        override fun onEndpointLost(endpointId: String) {
            Log.d("Nearby", "Utracono połaczenie")
        }

    }

    // Posłaniec odbiera paczke i przynosi
    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String,  payload: Payload) {
            val dane = payload.asBytes()?.decodeToString()

            if (dane == "ACK") {
                Log.d("Nearby", "Prowadzący potwierdził moją obecność!")
                client?.disconnectFromEndpoint(endpointId)
            } else {
                Log.d("Nearby", "Odebrano studenta: $dane")
                // Prowadzący odsyła potwierdzenie:
                client?.sendPayload(endpointId, Payload.fromBytes("ACK".toByteArray()))
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            Log.d("Nearby", "Odebrano studenta")
        }

    }

    // Pracownik, ktory czeka az ktos sie bedzie chcial polaczyc || callback dla nauczyciela
    private val teacherConnectionCallback = object : ConnectionLifecycleCallback() {

        // Automatyczna akceptacja, oraz  oddelegowanie poslanca po paczke z danymi
        override fun onConnectionInitiated(endpointId :String, connectionInfo :ConnectionInfo) {
            client?.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, connectionResolution: ConnectionResolution) {
            Log.d("Nearby", "Połączono!")
        }

        override fun onDisconnected(p0: String) {
            Log.d("Nearby", "Rozłączono!")
        }
    }

    fun stopAdvertising() {
        client?.stopAdvertising()
        client?.stopAllEndpoints()
    }

    fun stopDiscovery() {
        client?.stopDiscovery()
    }

    // Prowadzacy robi hotspot
    fun startAdvertising(context: Context) {
        client = Nearby.getConnectionsClient(context.applicationContext)
        val options = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()

        client?.startAdvertising("ImHere[Random code]", SERVICE_ID, teacherConnectionCallback, options)
            ?.addOnSuccessListener {

            }
            ?.addOnFailureListener {
                // Wysłanie informacji o błędzie nadawania - brakujace biblioteki czy cos
            }
    }

    // Student szuka prowadzacego i wysłanie pracownika aby czekal na odpowiedz
    fun startDiscovery(context: Context) {
        client = Nearby.getConnectionsClient(context.applicationContext)
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        client?.startDiscovery(SERVICE_ID, endpointDiscoveryCallback, discoveryOptions)
            ?.addOnSuccessListener {
                Log.d("Nearby", "Znaleziono spotkanie, Wysyalm dane")
            }
            ?.addOnFailureListener {
                Log.d("Nearby", "Nie udalo sie polaczyc")
            }
    }
}