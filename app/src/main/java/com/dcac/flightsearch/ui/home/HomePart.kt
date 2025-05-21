package com.dcac.flightsearch.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcac.flightsearch.R
import com.dcac.flightsearch.data.airport.Airport
import com.dcac.flightsearch.model.FlightUiModel

@Composable
fun AirportSuggestion(
    airport: Airport,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(airport.iataCode) }
            .padding(dimensionResource(R.dimen.padding_horizontal_x_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = airport.iataCode,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_horizontal_medium)))
        Text(
            text = airport.name,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FlightRouteCard(
    flight: FlightUiModel,
    onToggleFavorite: (Airport, Airport) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_horizontal_small)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_horizontal_medium))
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "DEPART",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.scrim
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = flight.departure.iataCode,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_horizontal_small)))
                    Text(
                        text = flight.departure.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.scrim
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = flight.destination.iataCode,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_horizontal_small)))
                    Text(
                        text = flight.destination.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            IconButton(
                onClick = { onToggleFavorite(flight.departure, flight.destination) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite",
                    tint = if (flight.isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlightRouteCard() {
    val mockDeparture = Airport(
        iataCode = "CDG",
        name = "Charles de Gaulle Airport",
        passengers = 10000
    )

    val mockDestination = Airport(
        iataCode = "JFK",
        name = "John F. Kennedy International Airport",
        passengers = 15000
    )

    val mockFlight = FlightUiModel(
        departure = mockDeparture,
        destination = mockDestination,
        isFavorite = true
    )

    MaterialTheme {
        FlightRouteCard(
            flight = mockFlight,
            onToggleFavorite = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAirportSuggestion() {
    val mockAirport = Airport(
        iataCode = "CDG",
        name = "Charles de Gaulle Airport",
        passengers = 10000
    )

    MaterialTheme {
        AirportSuggestion(airport = mockAirport, onClick = {})
    }
}