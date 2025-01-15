package com.example.currencyconverter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(it)
                .padding(horizontal = 20.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "You're not connected to the internet",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Please check your internet connection and try again",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = {
                   onRefresh()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffa5db7a),
                    contentColor = Color.White
                ),

                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Refresh",
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = backgroundColor,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}