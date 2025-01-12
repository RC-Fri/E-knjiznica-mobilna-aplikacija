package com.example.e_knjiznica_mobilna_aplikacija.data.model
import DatabaseClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _materials = MutableStateFlow<List<Material>>(emptyList())
    val materials: StateFlow<List<Material>> = _materials

    fun loadMaterialsForUser(userId: Int) {
        viewModelScope.launch {
            val userMaterials = DatabaseClient.getBorrowedMaterials(userId)
            _materials.value = userMaterials
        }
    }

    fun extendMaterialDate(material: Material, userId: Int) {
        viewModelScope.launch {
            DatabaseClient.extendMaterialDate(material.id, userId)
        }
    }

    fun viewMaterialDetails(material: Material) {
        // Handle viewing material details (e.g., navigate to a details screen)
    }
}

