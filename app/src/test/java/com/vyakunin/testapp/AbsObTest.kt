package com.vyakunin.testapp

import com.vyakunin.testapp.data.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import org.junit.After
import org.junit.Before
import java.io.File

abstract class AbsObTest {
    private val testDir = File("objectbox-test/test-db")

    var store: BoxStore? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // delete database files before each test to start with a clean database
        BoxStore.deleteAllFiles(testDir)
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(testDir)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
                .build()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        if (store != null) {
            store!!.close()
            store = null
        }
        BoxStore.deleteAllFiles(testDir)
    }
}