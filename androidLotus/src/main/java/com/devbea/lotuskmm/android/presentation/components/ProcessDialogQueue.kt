package com.devbea.lotuskmm.android.presentation.components

import androidx.compose.runtime.Composable
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.util.Queue

@Composable
fun ProcessDialogQueue(dialogQueue: Queue<GenericMessageInfo>?, onRemoveHeadFromQueue: () -> Unit) {
    dialogQueue?.peek()?.let { dialogInfo ->

        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            onRemoveHeadFromQueue = onRemoveHeadFromQueue,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}