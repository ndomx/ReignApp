package com.ndomx.reign

import org.json.JSONArray
import org.json.JSONObject

inline fun JSONArray.forEach(action: (JSONObject) -> Unit) {
    (0 until length()).forEach { index ->
        action(getJSONObject(index))
    }
}

inline fun<T> JSONArray.map(transform: (JSONObject) -> T): List<T> {
    return List(length()) { index ->
        transform(getJSONObject(index))
    }
}