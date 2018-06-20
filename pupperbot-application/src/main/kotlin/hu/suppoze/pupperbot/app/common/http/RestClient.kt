package hu.suppoze.pupperbot.app.common.http

interface RestClient {

    fun get(url: String, params: List<Pair<String, Any>>? = null, vararg pathParams: String): String

}