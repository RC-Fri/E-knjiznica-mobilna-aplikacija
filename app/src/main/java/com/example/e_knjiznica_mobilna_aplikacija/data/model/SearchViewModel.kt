package com.example.e_knjiznica_mobilna_aplikacija.data.model
import DatabaseClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Material>>(emptyList())
    val searchResults: StateFlow<List<Material>> = _searchResults

    fun loadMaterials() {
        viewModelScope.launch {
            val userMaterials = DatabaseClient.getMaterials()
            _searchResults.value = userMaterials
        }
    }

    fun searchMaterials(
        materialName: String,
        authorName: String,
        releaseDate: String,
        materialType: String
    ) {
        viewModelScope.launch {
            val results = DatabaseClient.searchMaterials(materialName, authorName, releaseDate, materialType)
            _searchResults.value = results
        }
    }

    fun borrowMaterial(materialId: Int, userId: Int) {
        viewModelScope.launch {
            DatabaseClient.borrowMaterial(materialId, userId)
        }
    }
}
