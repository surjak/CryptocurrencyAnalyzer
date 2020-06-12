import scalaj.http.{Http, HttpOptions}
object SendService {
  def main(args: Array[String]) = {

    val delay = 10000 //10s
    val  post_address = "http://localhost:9000/startSending"
    println("Start sending emails...")
    while (true){
      Thread.sleep(delay)
      println("Send...")
      var result = Http(post_address).postData("").
        header("Content-Type", "application/json").
        header("Charset", "UTF-8")
        .option(HttpOptions.connTimeout(10000000))
        .asString
      println(result)
    }
  }
}
