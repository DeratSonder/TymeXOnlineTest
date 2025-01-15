package com.example.currencyconverter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.utilities.Constants.currencies
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.runtime.collectAsState
import com.example.currencyconverter.viewmodels.CurrencyConverterViewModel

@Composable
fun ChooseCurrencyScreen(
    modifier: Modifier = Modifier,
    onChoose: () -> Unit,
    status: Int,
    viewModel: CurrencyConverterViewModel
) {

    var filteredCurrencies by remember { mutableStateOf(currencies) }
    var searchQuery by remember { mutableStateOf("") }
    val currentCurrency by viewModel.chosenCurrency.collectAsState()
    var focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(WindowInsets.statusBars.asPaddingValues()),
        topBar = { TopBar(onBack = onChoose) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .clickable { focusManager.clearFocus() }
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            SearchTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it

                    filteredCurrencies = currencies.filter { (key, value) ->
                        key.contains(it, ignoreCase = true) ||
                                value.contains(it, ignoreCase = true)
                    } as LinkedHashMap<String, String>

                },
                placeholder = "Search for a currency",
                onSearch = {
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (filteredCurrencies.isEmpty()) {
                Text(
                    text = "\"${searchQuery}\" doesn't match any currencies or " +
                            "countries that we support",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.White
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = "Current Currencies",
                        modifier = Modifier.fillMaxWidth()

                    )
                    Divider(color = Color.White)

                    CurrencyItem(
                        key = currentCurrency,
                        value = currencies[currentCurrency] ?: "No countries",
                        isChosen = true,
                        onClick = {
                            if(status == 1){
                                viewModel.setFromCurrency(currentCurrency)
                                onChoose()
                            } else {
                                viewModel.setToCurrency(currentCurrency)
                                onChoose()
                            }
                        }
                    )

                    Text(
                        text = "All Currencies",
                        modifier = Modifier.fillMaxWidth()

                    )

                    Divider(color = Color.White)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredCurrencies.size) { currencyItem ->
                            CurrencyItem(
                                key = filteredCurrencies.keys.elementAt(currencyItem),
                                value = filteredCurrencies.values.elementAt(currencyItem),
                                isChosen = false,
                                onClick = {
                                    if(status == 1){
                                        viewModel.setFromCurrency(filteredCurrencies.keys.elementAt(currencyItem))
                                        onChoose()
                                    } else {
                                        viewModel.setToCurrency(filteredCurrencies.keys.elementAt(currencyItem))
                                        onChoose()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(
    key: String,
    value: String,
    isChosen: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = if(isChosen) Color.Green else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = key,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                )
            }

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check Icon",
                tint = if (isChosen) Color.Green else Color.Transparent,
                modifier = Modifier.padding(10.dp)
            )

        }
    }
}

@Composable
fun SearchTextField(
    value: String,
    placeholder: String = "Search for a currency",
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isFocus by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.White),
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(color = Color.White)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = if (isFocus) Color.Green else Color.Gray
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = if (isFocus) Color.Green else Color.Gray,
                shape = RoundedCornerShape(30.dp)
            )
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(30.dp)
            )
            .onFocusChanged {
                isFocus = it.isFocused
            },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.padding(16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            ),
            shape = CircleShape,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Icon",
                tint = Color.Green
            )
        }
    }
}