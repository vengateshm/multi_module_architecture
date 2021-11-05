package com.android.vengateshm.uicore.utils

object DataCache {
    private val dataCacheMap = mutableMapOf<String, Any>()

    fun putData(key: String, data: Any) {
        dataCacheMap[key] = data
    }

    fun getData(key: String): Any? {
        return getData(key, null)
    }

    fun getData(key: String, defaultValue: Any?): Any? {
        return if (dataCacheMap.containsKey(key)) dataCacheMap[key] else defaultValue
    }

    fun removeData(key: String) {
        dataCacheMap.remove(key)
    }

    fun clear() {
        dataCacheMap.clear()
    }
}