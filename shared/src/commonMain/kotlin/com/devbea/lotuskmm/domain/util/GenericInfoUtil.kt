package com.devbea.lotuskmm.domain.util

import com.devbea.lotuskmm.domain.model.GenericMessageInfo

class GenericInfoUtil {
    fun doesMessageAlreadyExistInQueue(
        queue: Queue<GenericMessageInfo>,
        messageInfo: GenericMessageInfo
    ): Boolean = queue.items.map { it::id.get() }.contains(messageInfo.id)

}