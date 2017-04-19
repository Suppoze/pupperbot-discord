package hu.suppoze.pupperbot.common

import hu.suppoze.pupperbot.error.CommandError
import hu.suppoze.pupperbot.giphy.GiphyCommand
import hu.suppoze.pupperbot.help.HelpCommand
import hu.suppoze.pupperbot.rss.RssCommand
import hu.suppoze.pupperbot.say.SayCommand

class CommandFactory {

    fun  build(rawCommand: CommandParser.RawCommand) : Command<*> {
        return determineAndCreateCommand(rawCommand)
    }

    private fun determineAndCreateCommand(rawCommand: CommandParser.RawCommand): Command<*> {
        when (rawCommand.command) {
            CommandParser.CommandStrings.RSS -> return RssCommand(rawCommand)
            CommandParser.CommandStrings.GIPHY -> return GiphyCommand(rawCommand)
            CommandParser.CommandStrings.HELP -> return HelpCommand(rawCommand)
            CommandParser.CommandStrings.SAY -> return SayCommand(rawCommand)
            else -> return CommandError(rawCommand, "Error parsing your command! Type ;help to see available commands.")
        }
    }


}