package pl.ownvision.scorekeeper.exceptions

/**
 * Created by jakub on 01.06.2017.
 */

class ValidationException(
        val error: String
) : Exception()