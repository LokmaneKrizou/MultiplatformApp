package com.devbea.lotuskmm.domain.model

sealed class UIComponentType {
    object Dialog : UIComponentType()
    object None : UIComponentType()
}