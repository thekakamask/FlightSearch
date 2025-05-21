package com.dcac.flightsearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dcac.flightsearch.R
import com.dcac.flightsearch.ui.navigation.FlightSearchNavHost

@Composable
fun flightSearchApp(navController: NavHostController = rememberNavController()) {
    FlightSearchNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.flight_search_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.padding_horizontal_medium))
                    .height(dimensionResource(R.dimen.top_bar_logo)),
                contentScale = ContentScale.Fit
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewFlightSearchTopAppBar() {
    MaterialTheme {
        FlightSearchTopAppBar(
            title = "Flight Search Preview",
            modifier = Modifier,
            scrollBehavior = null
        )
    }
}