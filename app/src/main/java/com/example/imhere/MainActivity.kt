package com.example.imhere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imhere.ui.theme.ImHereTheme
import com.example.imhere.util.QrCodeGenerator
import com.example.imhere.util.QrCodeScanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImHereTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QrCodePreviewScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImHereTheme {
        QrCodePreviewScreen()
    }
}

@Composable
fun QrCodePreviewScreen(text: String = "IMHERE_TEST_POZNAN") {
    val bitmap = remember(text) {
        QrCodeGenerator.generate(text, size = 512)
    }

    var scanResult by remember {
        mutableStateOf("Brak stanu")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Kod QR",
            modifier = Modifier.size(250.dp)
        )
        Button(
            onClick = { QrCodeScanner.startScan(context, {scanResult = it}) },
        ) {
            Text("Zeskanuj kod QR")
        }
        Text("Zeskanowano: $scanResult")
    }
}