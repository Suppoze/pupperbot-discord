package hu.suppoze.pupperbot.rss

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import io.reactivex.Observable
import java.io.InputStreamReader
import java.net.URL

class RssServer {

    fun getFeed(feedUrl: URL): Observable<SyndFeed> = Observable.fromPublisher<SyndFeed> {
        try {
            val feed = SyndFeedInput().build(InputStreamReader(feedUrl.openStream()))
            it.onNext(feed)
        } catch(t: Throwable) {
            it.onError(t)
        }
        it.onComplete()
    }
}