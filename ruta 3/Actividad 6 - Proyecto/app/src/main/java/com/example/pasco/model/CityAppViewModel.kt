package com.example.pasco.model

import androidx.lifecycle.ViewModel
import com.example.pasco.data.Datasource
import com.example.pasco.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A ViewModel to hold the state of the app.
 */
class CityAppViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CityAppUiState())
    val uiState: StateFlow<CityAppUiState> = _uiState.asStateFlow()

    fun updateCurrentCategory(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = category,
                recommendations = getRecommendationsForCategory(category)
            )
        }
    }

    fun updateCurrentRecommendation(place: Place) {
        _uiState.update { currentState ->
            currentState.copy(currentRecommendation = place)
        }
    }

    private fun getRecommendationsForCategory(category: Category): List<Place> {
        return when (category) {
            Category.Cafeterias -> Datasource.coffeeShops
            Category.Restaurantes -> Datasource.restaurants
            Category.Parques -> Datasource.parks
            Category.Museos -> Datasource.museums
            Category.Otros -> Datasource.others
        }
    }
}

data class CityAppUiState(
    val currentCategory: Category = Category.Cafeterias,
    val recommendations: List<Place> = Datasource.coffeeShops,
    val currentRecommendation: Place? = null
)

enum class Category {
    Cafeterias,
    Restaurantes,
    Parques,
    Museos,
    Otros
}