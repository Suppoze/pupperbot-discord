package hu.suppoze.pupperbot.listeners

import com.github.salomonbrys.kodein.instance
import hu.suppoze.pupperbot.PupperBotApplication
import hu.suppoze.pupperbot.common.Command
import hu.suppoze.pupperbot.common.CommandError
import hu.suppoze.pupperbot.common.CommandFactory
import hu.suppoze.pupperbot.common.PupperBot
import hu.suppoze.pupperbot.di.kodein
import hu.suppoze.pupperbot.giphy.GiphyCommand
import hu.suppoze.pupperbot.help.HelpCommand
import hu.suppoze.pupperbot.rss.RssCommand
import hu.suppoze.pupperbot.rss.RssService
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.obj.Status

class AnnotationListener {

    private val pupperBot: PupperBot by kodein.instance()

    @EventSubscriber
    fun onReadyEvent(event: ReadyEvent) {
        pupperBot.client.changeStatus(Status.game(";help for commands"))
        PupperBotApplication.listenForCommand()
    }

    @EventSubscriber
    fun onMessageReceiedEvent(event: MessageReceivedEvent) {
        if (event.message.content.startsWith(';')) {
            CommandFactory(event).build().perform()
        }
    }
}