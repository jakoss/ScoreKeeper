package pl.ownvision.scorekeeper.repositories

import io.realm.Realm

/**
 * Created by jakub on 05.06.2017.
 */

open class BaseRepository(protected val realm: Realm){
    fun closeRealm() {
        realm.close()
    }
}