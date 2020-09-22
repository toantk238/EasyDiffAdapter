package com.example.easydiffadapter

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Utils {

    fun readAssetFile(context: Context, fileName: String): String? {
        var input: BufferedReader? = null
        try {
            input = BufferedReader(InputStreamReader(context.assets.open(fileName)))
            var line: String?
            val buffer = StringBuilder()
            line = input.readLine()
            while (line != null) {
                buffer.append(line)
                line = input.readLine()
            }

            input.close()
            return buffer.toString()
        } catch (e: IOException) {
            if (input != null)
                try {
                    input.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            e.printStackTrace()
        }

        return null
    }
}