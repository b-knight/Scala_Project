
val url = "https://api.census.gov/data/2016/pep/population?" +
  "get=POP,GEONAME&for=" +
  "county:001&in=" +
  "state:01&" +
  "key=1d6aae80059ec70da67ab88b9cb7ef9c1cf4ddc6"

def read(url:String):String = io.Source.fromURL(url).mkString

val response = read(url)


