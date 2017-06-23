package pl.ownvision.scorekeeper.models

import pl.ownvision.scorekeeper.core.getFormattedLocal

/**
 * Created by jakub on 23.06.2017.
 */

interface CreatedAt {
    var createdAt: java.util.Date
    fun getCreatedLocal() = createdAt.getFormattedLocal()
}