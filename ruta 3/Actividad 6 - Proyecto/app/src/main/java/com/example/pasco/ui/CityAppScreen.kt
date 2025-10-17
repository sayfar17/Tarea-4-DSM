package com.example.pasco.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pasco.R
import com.example.pasco.data.Place
import com.example.pasco.model.Category
import com.example.pasco.model.CityAppViewModel
import com.example.pasco.ui.utils.ContentType

/**
 * An enum to represent the different screens of the app.
 */
enum class CityAppScreen {
    Categories,
    Recommendations,
    Details
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityAppAppBar(
    currentScreen: CityAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val viewModel: CityAppViewModel = viewModel()
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CityAppScreen.valueOf(
        backStackEntry?.destination?.route ?: CityAppScreen.Categories.name
    )

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> ContentType.ListOnly
        WindowWidthSizeClass.Expanded -> ContentType.ListAndDetail
        else -> ContentType.ListOnly
    }

    Scaffold(
        topBar = {
            CityAppAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CityAppScreen.Categories.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CityAppScreen.Categories.name) {
                CategoriesScreen(
                    onCategoryClicked = {
                        viewModel.updateCurrentCategory(it)
                        navController.navigate(CityAppScreen.Recommendations.name)
                    }
                )
            }
            composable(route = CityAppScreen.Recommendations.name) {
                RecommendationsScreen(
                    viewModel = viewModel,
                    onRecommendationClicked = {
                        viewModel.updateCurrentRecommendation(it)
                        navController.navigate(CityAppScreen.Details.name)
                    }
                )
            }
            composable(route = CityAppScreen.Details.name) {
                DetailsScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(Category.values()) { category ->
            CategoryCard(category = category, onClick = { onCategoryClicked(category) })
        }
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RecommendationsScreen(
    viewModel: CityAppViewModel,
    onRecommendationClicked: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(uiState.recommendations) { recommendation ->
            RecommendationCard(
                recommendation = recommendation,
                onClick = { onRecommendationClicked(recommendation) }
            )
        }
    }
}

@Composable
fun RecommendationCard(recommendation: Place, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // TODO: Add image
            Text(
                text = stringResource(id = recommendation.name),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
fun DetailsScreen(viewModel: CityAppViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val recommendation = uiState.currentRecommendation
    if (recommendation != null) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = recommendation.name),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = recommendation.description),
                style = MaterialTheme.typography.bodyLarge
            )
            Image(
                painter = painterResource(id = recommendation.image),
                contentDescription = stringResource(id = recommendation.name),
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}