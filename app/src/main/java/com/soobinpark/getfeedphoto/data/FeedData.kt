package com.soobinpark.getfeedphoto.data

class FeedItem(val id_str: String, val imageUrl: String?, val title: String)

// JSON 객체기준
data class TimelineFeedData(val text: String,
                            val entities: Entities,
                            val created_at: String,
                            val id_str: String,
                            val user: User
)

data class Entities(val media:ArrayList<Media>)

data class User(val id_str: String,
                val name: String,
                val screen_name: String,
                val description: String,
                val url: String,
                val profile_image_url_https: String
)

data class Media(val media_url: String,
                 val sizes: Size)

data class Size(val medium: MediaSizeMedium)

data class MediaSizeMedium(val w: Int,
                  val h: Int,
                  val resize: String)