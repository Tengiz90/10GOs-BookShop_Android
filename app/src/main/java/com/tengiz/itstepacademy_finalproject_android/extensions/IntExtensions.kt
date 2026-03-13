package com.tengiz.itstepacademy_finalproject_android.extensions

fun Int.toLanguageString(): String {
    return when (this) {
        0 -> "English"
        1 -> "Georgian"
        2 -> "Russian"
        3 -> "German"
        4 -> "French"
        5 -> "Spanish"
        6 -> "Polish"
        else -> "Unknown"
    }

}

fun Int.toClientString(): String {
    return when (this) {
        0 -> "Android"
        1 -> "iOS"
        2 -> "Web"
        else -> "Unknown"
    }
}

fun Int.toRoleString(): String {
    return when (this) {
        0 -> "Admin"
        1 -> "Customer"
        else -> "Unknown"
    }
}

fun Int.toCategoryString(): String {
    return when (this) {
        1 -> "Fiction"
        2 -> "Non-Fiction"
        3 -> "Science Fiction"
        4 -> "Fantasy"
        5 -> "Horror"
        6 -> "Dystopian"
        7 -> "Mystery"
        8 -> "Thriller"
        9 -> "Romance"
        10 -> "Biography"
        11 -> "Autobiography"
        12 -> "History"
        13 -> "Philosophy"
        14 -> "Science"
        15 -> "Technology"
        16 -> "Psychology"
        17 -> "Self-Help"
        18 -> "Business"
        19 -> "Economics"
        20 -> "Politics"
        21 -> "Religion"
        22 -> "Spirituality"
        23 -> "Education"
        24 -> "Children"
        25 -> "Young Adult"
        26 -> "Adventure"
        27 -> "Crime"
        28 -> "True Crime"
        29 -> "Classic"
        30 -> "Drama"
        31 -> "Comedy"
        32 -> "Poetry"
        33 -> "Short Stories"
        34 -> "Graphic Novel"
        35 -> "Manga"
        36 -> "Mythology"
        37 -> "Fairy Tales"
        38 -> "Anthology"
        39 -> "Western"
        40 -> "Detective"
        41 -> "Historical Fiction"
        42 -> "Political Fiction"
        43 -> "Science Popular"
        44 -> "Health"
        45 -> "Parenting"
        46 -> "Environmental"
        47 -> "Travel"
        48 -> "Cooking"
        else -> "Unknown"
    }
}