package com.devbea.lotuskmm.android.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbea.lotuskmm.domain.model.NegativeAction
import com.devbea.lotuskmm.domain.model.PositiveAction

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)?,
    title: String,
    description: String? = null,
    positiveAction: PositiveAction?,
    negativeAction: NegativeAction?,
    onRemoveHeadFromQueue: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
            onRemoveHeadFromQueue()
        },
        text = { description?.let { Text(text = it, style = MaterialTheme.typography.body1) } },
        title = { Text(text = title, style = MaterialTheme.typography.body1) },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                negativeAction?.let { negativeAction ->
                    Button(onClick = {
                        negativeAction.onNegativeAction.invoke()
                        onRemoveHeadFromQueue()
                    }, modifier = Modifier.padding(end = 8.dp)) {
                        Text(
                            text = negativeAction.negativeBtnTxt,
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }
                positiveAction?.let { positiveAction ->
                    Button(onClick = {
                        positiveAction.onPositiveAction.invoke()
                        onRemoveHeadFromQueue()
                    }, modifier = Modifier.padding(end = 8.dp)) {
                        Text(
                            text = positiveAction.positiveBtnTxt,
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }

            }
        }
    )
}