package com.ndomx.reign

import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

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

fun formatDate(date: Date): String {
    val now = Date()

    val millis = now.time - date.time
    val seconds = millis / 1000
    val minutes = seconds / 60
    when {
        minutes < 1 -> return "A moment ago"
        minutes < 60 -> return "${minutes}m ago"
    }

    val hours = minutes / 60
    if (hours < 24) {
        return "${hours}h ago"
    }

    val days = hours / 24
    if (days < 7) {
        return "$days days ago"
    }

    val formatter = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
    return formatter.format(date)
}