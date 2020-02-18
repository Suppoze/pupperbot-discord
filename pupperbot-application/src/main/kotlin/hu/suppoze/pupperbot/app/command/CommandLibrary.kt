package hu.suppoze.pupperbot.app.command

import hu.suppoze.pupperbot.app.PupperConfiguration

class CommandLibrary(config: PupperConfiguration) {

    val commands = listOf(
        HelpCommand(this),
        SayCommand(),
        SearchGifCommand(config.gifClient),
        RandomGifCommand(config.gifClient),
        PronCommand(),
        CinemaCommand(config.reactionCallbackCache, config.cinemaScheduleProvider, config.cinemaScheduleEmbedBuilder),
        EmoteReportCommand()
    )

    private val commandsByKeyword = commands.associateBy { command -> command.keyword }

    val keywords = commands.map { command -> command.keyword }

    fun getCommandBy(keyword: String): Command {
        return commandsByKeyword.getOrDefault(keyword, NoopCommand())
    }
}