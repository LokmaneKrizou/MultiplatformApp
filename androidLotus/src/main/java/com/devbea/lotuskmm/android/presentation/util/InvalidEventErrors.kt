package com.devbea.lotuskmm.android.presentation.util

import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.UIComponentType
import java.util.*

fun invalidEventErrorDialog() = GenericMessageInfo.Builder()
    .id(UUID.randomUUID().toString())
    .title("Error")
    .uiComponentType(UIComponentType.Dialog)
    .description("Invalid Event")