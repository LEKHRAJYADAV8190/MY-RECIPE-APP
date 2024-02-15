package com.example.myrecipeapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.Exception

class MainViewModel :ViewModel(){

    private val _categoriestate=mutableStateOf(RecipeState())
    val categoriesstate:State<RecipeState> = _categoriestate

    init {
        Fetchcatorgies()
    }


    private fun Fetchcatorgies(){
        //like we know suspend function that run in background tho run that bacground we you viewmodelscope
        viewModelScope.launch {
            try {
                val response= reciperservice.getCategories()
                _categoriestate.value= _categoriestate.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            }
            catch (e:Exception){
                _categoriestate.value= _categoriestate.value.copy(
                    loading = false,
                    error ="Error fetching categories ${e.message}"
                )

            }
        }
    }


    data class RecipeState(
        val loading: Boolean=true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

}