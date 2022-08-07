package com.ndomx.reign.dummy

import java.util.*

object DummyContent {
    internal val authors = arrayOf(
        "ndomx", "frodo", "random_user123", "voldemort", "zelda",
        "reign_manager", "aka_theking", "thor", "zeus", "iron_man",
        "isaac_newton", "eistein", "frozono", "buzz_lightyear", "ness"
    )

    internal val titles = arrayOf(
        "How to win the lottery twice",
        "You won't believe what happens if you mix these common household ingredients",
        "Single people in your area... Truth or scam?",
        "Do you still fully charge your phone?",
        "Dolphins have a more complete language than english, study finds",
        "10 reasons why you should buy bitcoins (and 10 reasons why you shouldn't)",
        "Meet borophene, the material that will outdate graphene",
        "Watch these astonishing photos captured by Nasa's JWSP",
        "Looking for jobs aboard? Follow these 7 steps",
        "Best bucket laptops for 2022"
    )

    internal val oldestPost = Calendar.getInstance().let { calendar ->
        calendar.set(2021, 12, 28)
        calendar.timeInMillis
    }

    internal val newestPost = Calendar.getInstance().timeInMillis
}