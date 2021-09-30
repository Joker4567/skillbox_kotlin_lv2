package com.example.compose.model

import java.io.Serializable
import java.util.*

data class CharacterModel(
        val id: Long,
        val name: String,
        val species: String,
        val status: String,
        val location: Location,
        val image: String,
        val origin: Origin,
        val episode: List<String>
) : Serializable
