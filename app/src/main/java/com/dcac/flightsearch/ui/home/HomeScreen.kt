package com.dcac.flightsearch.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dcac.flightsearch.R
import com.dcac.flightsearch.data.airport.Airport
import com.dcac.flightsearch.model.FlightUiState
import com.dcac.flightsearch.ui.FlightSearchTopAppBar
import com.dcac.flightsearch.ui.FlightViewModel
import com.dcac.flightsearch.ui.navigation.NavigationDestination
import androidx.compose.foundation.lazy.items

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)
) {

    val flightUiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FlightSearchTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                scrollBehavior = scrollBehavior
            )
        }
    ){ innerPadding ->
        HomeBody(
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
            flightUiState = flightUiState,
            searchQuery = (flightUiState as FlightUiState.Success).searchQuery,
            onSearchQueryChanged = { query ->
                viewModel.displayAirportsByQuery(query)
                viewModel.saveSearchQuery(query)
            },
            displayRoutesFromSelectedAirport = { code ->
                viewModel.displayRoutesFromSelectedAirport(code)
            },
            onToggleFavorite = { dep, dest ->
                viewModel.onToggleFavorite(dep, dest)
            }
        )
    }
}

@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    flightUiState: FlightUiState,
    displayRoutesFromSelectedAirport: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onToggleFavorite: (Airport, Airport) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(contentPadding)
            .padding(vertical = dimensionResource(R.dimen.padding_vertical_medium))
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) },
            placeholder = {Text(stringResource(R.string.search_airport))},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = { Icon(Icons.Default.Mic, contentDescription = "Voice") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_horizontal_medium))
        )

        if (flightUiState is FlightUiState.Success && flightUiState.showSuggestions) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_horizontal_medium))
            ) {
                items(
                    items = flightUiState.searchSuggestions,
                    key = { "${it.iataCode}-${it.name}" }
                ) { airport ->
                    AirportSuggestion(
                        airport = airport,
                        onClick = displayRoutesFromSelectedAirport
                    )
                }
            }
        } else if (flightUiState is FlightUiState.Error) {
            Text(text = flightUiState.message, color = Color.Red)
        }

        when (flightUiState) {
            is FlightUiState.Success -> {
                if (flightUiState.selectedDeparture != null) {
                    Text(
                        text = "Flights from ${flightUiState.selectedDeparture.iataCode}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_vertical_medium), top = dimensionResource(R.dimen.padding_vertical_medium))
                            .align(Alignment.Start)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_horizontal_medium))
                    ) {
                        items(
                            flightUiState.destinations,
                            key = { "${it.departure.iataCode}-${it.destination.iataCode}" }
                        ) { flight ->
                            FlightRouteCard(
                                flight = flight,
                                onToggleFavorite = onToggleFavorite
                            )
                        }
                    }
                }
            }
            is FlightUiState.Error -> {
                Text(text = flightUiState.message, color = Color.Red)
            }
        }
    }
}

