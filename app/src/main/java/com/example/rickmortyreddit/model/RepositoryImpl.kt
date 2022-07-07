package com.example.rickmortyreddit.model

import android.content.Context
import androidx.core.content.edit
import com.example.rickmortyreddit.model.util.mapToModel
import com.example.rickmortyreddit.model.util.modelToEntity
import com.example.rickmortyreddit.model.local.RickMortyDao
import com.example.rickmortyreddit.model.remote.CharacterApi
import com.example.rickmortyreddit.model.util.NetworkInspection
import retrofit2.Response
import kotlin.properties.Delegates

class RepositoryImpl(private val context: Context, private val dao: RickMortyDao):
    Repository {

    companion object{
        const val SP_CACHE = "RickMortySP"
        const val SP_CACHE_Time = "RickMortySP_Time"
        const val TIMESTAMP = 1000 * 60 * 5 //5minutes
    }

    private var boolean by Delegates.notNull<Boolean>()

    init {
        isDeviceConnected()
    }

    /**
     * Check cache first
     * If available, return cache data.
     * If not, read remote data
     * If remote data available, update Cache
     * Update your timestamps
     * Update the UI
     */
    override suspend fun getCharacters(page: Int): AppState {
        return if(boolean) {
            if (isCacheAvailable()) {//read from cache first
                AppState.SUCCESS(queryLocalDB())
            } else {// no cache data, fetch and update DB
                val response =
                    CharacterApi.getCharacterApi().getCharacters(page)
                updateLocalDb(response)
                AppState.SUCCESS(queryLocalDB())
            }
        }else{
            if (isCacheAvailable())
                AppState.SUCCESS(queryLocalDB())
            else
                AppState.SUCCESS(null)
        }
    }

    private suspend fun queryLocalDB(): CharacterResponse? {
        return dao.readCache().mapToModel()
    }

    private suspend fun updateLocalDb(response: Response<CharacterResponse>) {
        if(response.isSuccessful && response.body() != null){
            response.body()?.results?.forEach {
                dao.insertFromRemote(
                    it.modelToEntity()
                )
            }
        }
    }

    /**
     * Defines a timestamp to check if current Cache is valid.
     * To save data consumption.
     * Offline Device Experience.
     */
    private fun isCacheAvailable(): Boolean{
        context.getSharedPreferences(SP_CACHE, Context.MODE_PRIVATE)
            .run {
                val temp = getString(SP_CACHE_Time, null)
                return if(temp == null){// first timer
                    edit { putString(SP_CACHE_Time,
                        System.currentTimeMillis().toString()) }
                    false
                }
                else{
                    System.currentTimeMillis() > temp.toLong() + TIMESTAMP
                }
            }
    }

    /**
     * Default Network defined by the System.
     * For this propose, query the current state...
     * @see <a href="https://developer.android.com/training/basics/network-ops/reading-network-state#listening-events>Listening to network events</a>
     */
    private fun isDeviceConnected(){
        NetworkInspection(context = context, ::boolean)
    }

    sealed class AppState {
        data class SUCCESS(val data: CharacterResponse? = null) : AppState()
        data class ERROR(val errorMessage: Data) : AppState()
        data class LOADING(val boolean: Boolean = true) : AppState()
    }
}