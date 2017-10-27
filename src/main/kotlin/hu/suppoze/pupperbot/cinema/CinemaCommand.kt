package hu.suppoze.pupperbot.cinema

import com.github.salomonbrys.kodein.instance
import hu.suppoze.pupperbot.common.AvailableCommands
import hu.suppoze.pupperbot.common.ChatCommand
import hu.suppoze.pupperbot.common.ParameterizedCommand
import hu.suppoze.pupperbot.common.UseCase
import hu.suppoze.pupperbot.di.kodein
import mu.KLogging
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.MessageEmbed
import java.awt.Color
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@ChatCommand(type = AvailableCommands.CINEMA_CITY)
class CinemaCommand : UseCase<String> {

    companion object : KLogging()

    private val cinemaService: CinemaService by kodein.instance()

    private lateinit var parameterizedCommand: ParameterizedCommand

    override val onNext: (String) -> Unit = {
        // TODO do somethin'
    }

    override val onError: (Throwable) -> Unit = {
        logger.error(it) { it.message }
        parameterizedCommand.event.textChannel.sendMessage("Error during cinema command: ${it.message}").queue()
    }

    override fun execute(parameterizedCommand: ParameterizedCommand) {
        this.parameterizedCommand = parameterizedCommand
        try {
            val schedule = cinemaService.fetchSchedule()
            parameterizedCommand.event.textChannel.sendMessage(buildScheduleEmbed(schedule)).queue()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    private fun buildScheduleEmbed(schedule: Schedule): MessageEmbed {
        val builder = EmbedBuilder()
                .setTitle("Cinema City Szeged Műsor")
                .setDescription(createDescriptionText())
                .setColor(Color.ORANGE)
                .setImage("http://www.cinemacity.hu/media/iti_cz/imgs/cc_logo.png")
        for (screening in schedule.screenings) {
            builder.addField(
                    screening.key.title,
                    screening.value
                            .sorted()
                            .groupBy { it.toLocalTime() }
                            .map { createScreeningRow(it) }
                            .reduce { r1, r2 -> "$r1\n$r2" },
                    false)
        }
        return builder.build()
    }

    private fun createDescriptionText(): String {
        val pattern = DateTimeFormatter.ofPattern("YYYY. MMMM d.")
        val from = LocalDate.now().format(pattern)
        val to = LocalDate.now().plusDays(7).format(pattern)
        return "$from - $to"

    }

    private fun createScreeningRow(timeToDateTime: Map.Entry<LocalTime, List<LocalDateTime>>): String {
        val timeString = timeToDateTime.key.format(DateTimeFormatter.ofPattern("H:mm"))
        val dateString = timeToDateTime.value.map {
            it.format(DateTimeFormatter.ofPattern("d."))
        }.reduce { s1, s2 -> "$s1, $s2" }
        return "**$timeString** - $dateString"
    }
}