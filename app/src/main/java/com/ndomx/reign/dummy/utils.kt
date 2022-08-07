package com.ndomx.reign.dummy

import com.ndomx.reign.db.Post
import java.util.*
import kotlin.random.Random

fun<T> Random.choose(array: Array<T>): T {
    val index = nextInt(array.size)
    return array[index]
}

fun generateRandomPosts(size: Int): List<Post> = List(size) { i ->
    Post(
        id = i,
        author = Random.choose(DummyContent.authors),
        createDate = Date(Random.nextLong(DummyContent.oldestPost, DummyContent.newestPost)),
        title = Random.choose(DummyContent.titles),
        url = "https://www.google.cl"
    )
}