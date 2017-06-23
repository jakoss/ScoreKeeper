package pl.ownvision.scorekeeper.db.converters

import android.arch.persistence.room.TypeConverter
import org.joda.time.DateTime

/**
 * Created by jakub on 23.06.2017.
 */

class DateTimeConverter {
    companion object {
        @TypeConverter
        fun toDateTime(timestamp: Long): DateTime = DateTime(timestamp)

        @TypeConverter
        fun toTimestamp(dateTime: DateTime): Long = dateTime.millis
    }
}