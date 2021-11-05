package com.android.vengateshm.uicore

import com.android.vengateshm.uicore.utils.DataCache
import org.junit.Assert
import org.junit.Test

class DataCacheTest {
    @Test
    fun testPut() {
        DataCache.putData("key", "value")
        val value = DataCache.getData("key")
        Assert.assertNotNull(value)
        Assert.assertEquals(value, "value")
    }

    @Test
    fun testClear() {
        DataCache.putData("key", "value")
        DataCache.clear()
        Assert.assertNull(DataCache.getData("key"))
    }

    @Test
    fun testRemove() {
        DataCache.putData("key", "value")
        DataCache.putData("key1", "value1")
        DataCache.removeData("key")
        Assert.assertNull(DataCache.getData("key"))
        Assert.assertNotNull(DataCache.getData("key1"))
        Assert.assertEquals(DataCache.getData("key1"), "value1")
    }
}