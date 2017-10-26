package pl.ownvision.scorekeeper.exceptions

class ValidationException(
        val error: String
) : Exception()