package app.shift.domain.timer

import app.shift.domain.progress.Session
import app.shift.domain.progress.formatDuration
import app.shift.domain.progress.plural
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus

/*
 * Итог таймера медитации. Повторяет tdTitle / tdMeta / tdStats из прототипа.
 * `all` уже содержит текущую сессию (она пишется при входе на экран).
 */

const val TIMER_MIN_SEC = 30          // ≤ 30 с после подготовки — итог не показываем, сессию не пишем
const val TIMER_SESSION_TITLE = "Таймер медитации"

data class StatUi(val label: String, val value: String, val sub: String) {
  val a11y get() = "$label, $value, $sub"
}

data class TimerDoneUi(
  val sessionId: String,
  val title: String,   // «20 минут тишины»
  val meta: String,    // «Чаша · 2 интервала · 07:15–07:35»
  val today: StatUi,
  val week: StatUi,
)

fun shouldShowTimerDone(elapsedSec: Double, warmSec: Int) = elapsedSec - warmSec > TIMER_MIN_SEC

/** Секунды тишины без подготовки. */
fun playedSec(elapsedSec: Double, warmSec: Int) = maxOf(0, kotlin.math.round(elapsedSec - warmSec).toInt())

fun timerDoneTitle(played: Int): String {
  val n = (played + 30) / 60  // Math.round
  return if (n > 0) "$n ${plural(n, "минута", "минуты", "минут")} тишины" else "Меньше минуты тишины"
}

/** startBell — подпись звука начала («Чаша», «Гонг»…) или null при «Нет». */
fun timerDoneMeta(startBell: String?, intervals: Int, played: Int, endedAt: LocalTime): String {
  val endSec = endedAt.toSecondOfDay()
  val startSec = ((endSec - played) % 86_400 + 86_400) % 86_400
  val parts = buildList {
    if (!startBell.isNullOrBlank()) add(startBell)
    if (intervals > 0) add("$intervals ${plural(intervals, "интервал", "интервала", "интервалов")}")
    add("${hm(startSec)}–${hm(endSec)}")
  }
  return parts.joinToString(" · ")
}

fun timerDoneUi(
  all: List<Session>,
  sessionId: String,
  played: Int,
  startBell: String?,
  intervals: Int,
  endedAt: LocalTime,
  today: LocalDate,
): TimerDoneUi {
  val weekFrom = today.minus(DatePeriod(days = 6))
  val td = all.filter { it.date == today }
  val wk = all.filter { it.date >= weekFrom && it.date <= today }
  val days = (wk.map { it.date } + today).toSet().size
  return TimerDoneUi(
    sessionId = sessionId,
    title = timerDoneTitle(played),
    meta = timerDoneMeta(startBell, intervals, played, endedAt),
    today = StatUi("Сегодня", dur(td.sumOf { it.durationSec }), "${td.size} ${plural(td.size, "сессия", "сессии", "сессий")}"),
    week = StatUi("За неделю", dur(wk.sumOf { it.durationSec }), "$days ${plural(days, "день", "дня", "дней")} из 7"),
  )
}

private fun dur(sec: Int) = formatDuration(sec).ifEmpty { "0 мин" }
private fun hm(sec: Int) = "${(sec / 3600).toString().padStart(2, '0')}:${(sec / 60 % 60).toString().padStart(2, '0')}"
