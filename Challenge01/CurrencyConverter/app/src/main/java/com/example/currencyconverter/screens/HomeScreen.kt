package com.example.currencyconverter.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.viewmodels.CurrencyConverterViewModel
import java.text.DecimalFormat

val backgroundColor = Color(0xff14150f)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onChooseCurrency: (Int) -> Unit,
    viewModel: CurrencyConverterViewModel
) {

    val convertedValue by viewModel.convertedValue.collectAsState()
    val fromCurrency by viewModel.fromCurrency.collectAsState()
    val toCurrency by viewModel.toCurrency.collectAsState()
    val errorMessage by viewModel.error.collectAsState()
    val isError by viewModel.isError.collectAsState()

    var value by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(
                WindowInsets.systemBars.asPaddingValues()
            )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(vertical = 20.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Currency Converter",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            ExchangeSection(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                value =  DecimalFormat("#,###.#####").format(convertedValue),
                onCurrencyClick = { onChooseCurrency(it) },
                onValueChange = {
                    value = it
                },
                viewModel = viewModel
            )

            Button(
                onClick = {
                    if(value.isEmpty()){
                        viewModel.setIsError(true)
                        viewModel.setErrorMessage("Please enter a value")
                    } else {
                        viewModel.convert(fromCurrency, toCurrency, value.toDouble())
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffa5db7a),
                    contentColor = Color.White
                ),

                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Convert",
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = backgroundColor,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }

    ErrorDialog(
        isVisible =  isError,
        onDismiss = { viewModel.setIsError(false) },
        message = errorMessage
    )
}

@Composable
fun SwapButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,

        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        modifier = modifier.wrapContentSize(),
        border = BorderStroke(
            width = 2.dp,
            color = Color.Gray
        ),
        shape = CircleShape,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.exchange_icon),
            contentDescription = "Swap",
            tint = Color.Green,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .rotate(90f),
        )
    }
}

@Composable
fun ExchangeSection(
    modifier: Modifier = Modifier,
    fromCurrency: String = "USD",
    toCurrency: String = "EUR",
    value: String = "",
    onCurrencyClick: (Int) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    viewModel: CurrencyConverterViewModel
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {

        Column {

            ExchangeTextField(
                currency = fromCurrency,
                onCurrencyClick = {
                    onCurrencyClick(1)
                    viewModel.setFromCurrency(fromCurrency)
                    viewModel.setChosenCurrency(fromCurrency)
                },
                onValueChange = onValueChange
            )

            ExchangeTextField(
                currency = toCurrency,
                readOnly = true,
                value = value,
                onCurrencyClick = {
                    onCurrencyClick(2)
                    viewModel.setToCurrency(toCurrency)
                    viewModel.setChosenCurrency(toCurrency)
                }
            )
        }

        SwapButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp),
            onClick = {
                viewModel.setFromCurrency(toCurrency)
                viewModel.setToCurrency(fromCurrency)
            }
        )
    }

}

@Preview
@Composable
fun ExchangeTextField(
    currency: String = "USD",
    readOnly: Boolean = false,
    value: String = "",
    onCurrencyClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var input by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .border(
                width = 2.dp,
                color = if (isFocused) Color.Green else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onCurrencyClick()
                }
            ) {
                Text(
                    currency,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    color = Color.Green
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
            }

            TextField(
                value = if(readOnly) value else input,
                onValueChange = {
                    if (it.matches(Regex("^[0-9]*\\.?[0-9]*$"))) {
                        input = it
                        onValueChange(it)
                    }
                },
                readOnly = readOnly,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedTextColor = Color.White,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Thiết lập kiểu nhập là số
                ),
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
            )
        }
    }
}

@Composable
fun ErrorDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    message: String
) {
    if (isVisible) {
        // Dialog
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Error",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red)
                )
            },
            text = {
                Text(
                    text = message,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black)
                )
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "OK", color = Color.White)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            containerColor = Color.Transparent
        )
    }
}
