package hu.suppoze.pupperbot.help

import hu.suppoze.pupperbot.common.Command
import hu.suppoze.pupperbot.common.CommandParser

class HelpCommand(val rawCommand: CommandParser.RawCommand) : Command<Any> {

    override val onNext: (Any) -> Unit
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val onError: (Throwable) -> Unit
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun perform() {
        rawCommand.event.message.channel.sendMessage(
                "`;giphy <tags separated by spaces>` - random gif\n" +
                "`;say <text>` - make PupperBot say something")
    }
}