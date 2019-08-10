package com.soobinpark.getfeedphoto.data

data class TimelineFeedData(val feed: CurrentFeed)

class CurrentFeed {
    var text: String? = null
    var mediaUrl: String? = null
}

// JSON 객체기준
data class FeedList(
    val feeds:ArrayList<FeedData>
)

data class FeedData(val text:String,
                    val media:ArrayList<Media>)

data class Media(val mediaUrl: String,
                 val sizes: Size)

data class Size(val medium: Medium)

data class Medium(val w: Int,
                  val h: Int,
                  val resize: String)