package app.shift.domain.progress

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.math.roundToInt

/*
 * Чистая логика экрана «Прогресс». Повторяет progVals() из прототипа
 * (prototype/Shift v10 Android.dc.html). Без Compose — тестируется в commonTest.
 */

enum class Quad { CALM_ENERGY, TENSE_ENERGY, CALM_TIRED, TENSE_TIRED }
enum class Dir { BREATH, MED, TRANCE, PSY, BODY }
enum class Goal { FOCUS, SLEEP, CALM, ENERGY, OWN }
enum class SessionKind { PRACTICE, PROGRAM, TIMER }

/** Категории фильтра «Время практик», в порядке показа. */
enum class TimeCat(val label: String) {
  BREATH("Дыхание"), MED("Медитация"), TIMER("Таймер"), BODY("Тело"),
  PSY("Психотехника"), TRANCE("Транс"), PROG("Программы"),
}

data class PracticeRef(val id: String, val title: String, val dir: Dir, val goal: Goal, val recMin: Int)

data class Session(
  val id: String,
  val pid: String?,
  val title: String?,
  val kind: SessionKind,
  val before: Quad?,
  val rating: Int?,          // 0..3
  val note: String?,
  val date: LocalDate,
  val time: String,          // «15:40», уже в локальной зоне
  val durationSec: Int,
  val goal: Goal? = null,    // для программ без pid
)

class Catalog(val practices: Map<String, PracticeRef>, val ownTechIds: Set<String>, val ownTechNames: Map<String, String>)

// ── Форматирование ────────────────────────────────────────────────

fun plural(n: Int, one: String, few: String, many: String): String {
  val m10 = n % 10; val m100 = n % 100
  return if (m10 == 1 && m100 != 11) one else if (m10 in 2..4 && (m100 < 12 || m100 > 14)) few else many
}

/** «» (0) · «45 с» · «5 мин» · «1 ч» · «1 ч 20 мин». */
fun formatDuration(sec: Int): String {
  if (sec <= 0) return ""
  if (sec < 60) return "$sec с"
  val m = (sec / 60.0).roundToInt(); val h = m / 60
  return if (h > 0) "$h ч" + (if (m % 60 > 0) " ${m % 60} мин" else "") else "$m мин"
}

val MONTHS = listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
val MONTHS_GEN = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
val RATING_LABEL = listOf("Хуже", "Так же", "Лучше", "Отлично")
val QUAD_NAME = mapOf(Quad.CALM_ENERGY to "В порядке", Quad.TENSE_ENERGY to "На взводе", Quad.CALM_TIRED to "В тумане", Quad.TENSE_TIRED to "На пределе")

fun dayName(d: LocalDate, today: LocalDate): String = when (d) {
  today -> "Сегодня"
  today.minus(DatePeriod(days = 1)) -> "Вчера"
  today.minus(DatePeriod(days = 2)) -> "Позавчера"
  else -> "${d.dayOfMonth} ${MONTHS_GEN[d.monthNumber - 1]}"
}

fun sessionTitle(s: Session, c: Catalog) = s.title ?: s.pid?.let { c.practices[it]?.title ?: c.ownTechNames[it] } ?: "Практика"

// ── Строка сессии ─────────────────────────────────────────────────

data class SessionRowUi(val id: String, val title: String, val line1: String, val line2: String, val note: String?, val before: Quad?, val rating: Int?)

fun sessionRow(s: Session, c: Catalog, today: LocalDate) = SessionRowUi(
  id = s.id,
  title = sessionTitle(s, c),
  line1 = listOf("${dayName(s.date, today)}, ${s.time}", formatDuration(s.durationSec)).filter { it.isNotEmpty() }.joinToString(" · "),
  line2 = listOfNotNull(s.before?.let { QUAD_NAME[it] }, s.rating?.let { RATING_LABEL[it].lowercase() }).joinToString(" → "),
  note = s.note?.takeIf { it.isNotBlank() },
  before = s.before, rating = s.rating,
)

fun summary(all: List<Session>): String {
  val n = all.size; val t = all.sumOf { it.durationSec }
  return "$n ${plural(n, "сессия", "сессии", "сессий")}" + (if (n > 0 && t > 0) " · ${formatDuration(t)}" else "") + " за всё время"
}

// ── Календарь ─────────────────────────────────────────────────────

sealed interface DayCell {
  data object Blank : DayCell
  data class Day(val date: LocalDate, val bestRating: Int?, val hasSessions: Boolean, val isToday: Boolean, val isFuture: Boolean) : DayCell
}

data class MonthUi(val title: String, val sub: String, val cells: List<DayCell>, val canNext: Boolean, val first: LocalDate, val last: LocalDate)

fun buildMonth(all: List<Session>, today: LocalDate, monthOffset: Int): MonthUi {
  val first = LocalDate(today.year, today.monthNumber, 1).minus(DatePeriod(months = monthOffset))
  val last = first.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
  val lead = first.dayOfWeek.ordinal // DayOfWeek: MONDAY = 0
  val byDay = all.groupBy { it.date }
  val cells = MutableList<DayCell>(lead) { DayCell.Blank }
  var days = 0; var rated = 0; var better = 0
  var d = first
  while (d <= last) {
    val xs = byDay[d].orEmpty(); val rs = xs.mapNotNull { it.rating }
    if (xs.isNotEmpty()) days++
    rated += rs.size; better += rs.count { it >= 2 }
    cells += DayCell.Day(d, rs.maxOrNull(), xs.isNotEmpty(), d == today, d > today)
    d = d.plus(DatePeriod(days = 1))
  }
  val title = MONTHS[first.monthNumber - 1] + if (first.year != today.year) " ${first.year}" else ""
  val sub = if (days == 0) "нет практик" else "$days ${plural(days, "день", "дня", "дней")}" + if (rated > 0) " · легче в $better из $rated" else ""
  return MonthUi(title, sub, cells, canNext = monthOffset > 0, first = first, last = last)
}

// ── «Что помогает, когда ты…» ─────────────────────────────────────

data class HelpTopUi(val pid: String, val title: String, val meta: String)

/** Счётчики на кнопках: оценённые сессии с отмеченным «до». */
fun helpCounts(all: List<Session>): Map<Quad, Int> =
  Quad.entries.associateWith { q -> all.count { it.rating != null && it.before == q } }

/** Топ-3 встроенных практик для состояния: доля «Лучше»+«Отлично», затем сумма оценок. */
fun helpTop(all: List<Session>, q: Quad, c: Catalog): List<HelpTopUi> =
  all.filter { it.rating != null && it.before == q && it.pid != null && c.practices.containsKey(it.pid) }
    .groupBy { it.pid!! }
    .map { (pid, xs) -> Triple(pid, xs, xs.count { it.rating!! >= 2 }) }
    .sortedWith(compareByDescending<Triple<String, List<Session>, Int>> { it.third.toDouble() / it.second.size }.thenByDescending { t -> t.second.sumOf { it.rating!! } })
    .take(3)
    .map { (pid, xs, b) -> HelpTopUi(pid, c.practices.getValue(pid).title, "${xs.size} ${plural(xs.size, "раз", "раза", "раз")} · ${(b * 100.0 / xs.size).roundToInt()}% легче") }

// ── «Время практик» ───────────────────────────────────────────────

enum class TimePeriod { MONTH, ALL }

data class TimeRowUi(val key: String, val title: String, val goal: Goal?, val isTimer: Boolean, val count: String, val time: String, val fraction: Float)

data class TimeStatsUi(
  val cats: List<TimeCat>,         // только типы с сессиями за период
  val selected: TimeCat?,          // null = «Все»
  val total: String, val sub: String,
  val rows: List<TimeRowUi>, val totalRows: Int,
  val emptyText: String?,
)

fun categoryOf(s: Session, c: Catalog): TimeCat = when {
  s.kind == SessionKind.TIMER -> TimeCat.TIMER
  s.pid != null && c.practices.containsKey(s.pid) -> when (c.practices.getValue(s.pid).dir) {
    Dir.BREATH -> TimeCat.BREATH; Dir.MED -> TimeCat.MED; Dir.TRANCE -> TimeCat.TRANCE; Dir.PSY -> TimeCat.PSY; Dir.BODY -> TimeCat.BODY
  }
  s.pid != null && s.pid in c.ownTechIds -> TimeCat.BREATH
  else -> TimeCat.PROG
}

fun timeStats(all: List<Session>, month: MonthUi, period: TimePeriod, cat: TimeCat?, expanded: Boolean, c: Catalog): TimeStatsUi {
  val pool = if (period == TimePeriod.MONTH) all.filter { it.date >= month.first && it.date <= month.last } else all
  val cats = TimeCat.entries.filter { k -> pool.any { categoryOf(it, c) == k } }
  val sel = cat?.takeIf { it in cats }
  val fl = if (sel == null) pool else pool.filter { categoryOf(it, c) == sel }
  val tSec = fl.sumOf { it.durationSec }; val days = fl.map { it.date }.toSet().size
  val groups = fl.groupBy { it.pid ?: sessionTitle(it, c) }.map { (k, xs) ->
    val f = xs.first()
    Triple(k, xs, xs.sumOf { it.durationSec }).let { (key, list, sec) ->
      TimeRowUi(key, sessionTitle(f, c), f.pid?.let { c.practices[it]?.goal } ?: f.goal, f.kind == SessionKind.TIMER,
        "${list.size} ${plural(list.size, "раз", "раза", "раз")}", if (sec > 0) formatDuration(sec) else "—", sec.toFloat())
    }
  }.sortedWith(compareByDescending<TimeRowUi> { it.fraction }.thenByDescending { it.count })
  val mx = (groups.maxOfOrNull { it.fraction } ?: 1f).coerceAtLeast(1f)
  val rows = groups.map { it.copy(fraction = (it.fraction / mx).coerceAtLeast(0.03f)) }
  return TimeStatsUi(
    cats = cats, selected = sel,
    total = if (tSec > 0) formatDuration(tSec) else "0 мин",
    sub = if (fl.isEmpty()) "" else "${fl.size} ${plural(fl.size, "сессия", "сессии", "сессий")} · $days ${plural(days, "день", "дня", "дней")}",
    rows = if (expanded) rows else rows.take(5), totalRows = rows.size,
    emptyText = if (fl.isNotEmpty()) null else if (period == TimePeriod.MONTH) "В этом месяце практик пока не было" else "Практик пока не было",
  )
}

/** Карточка на экране: время за месяц календаря, иначе — за всё время. */
fun timeCard(all: List<Session>, month: MonthUi): Pair<String, String> {
  val mp = all.filter { it.date >= month.first && it.date <= month.last }
  val monthName = MONTHS[month.first.monthNumber - 1]
  return if (mp.isNotEmpty()) (formatDuration(mp.sumOf { it.durationSec }).ifEmpty { "0 мин" }) to "$monthName · ${mp.size} ${plural(mp.size, "сессия", "сессии", "сессий")} · по практикам и типам"
  else (formatDuration(all.sumOf { it.durationSec }).ifEmpty { "0 мин" }) to "За всё время · в ${monthName.lowercase()} практик не было"
}

// ── Длительность в шторках ────────────────────────────────────────

fun durationDec(sec: Int): Int = if (sec % 60 != 0) (sec / 60) * 60 else maxOf(0, sec - 60)
fun durationInc(sec: Int): Int = minOf(36_000, (sec / 60 + 1) * 60)
fun durationLabel(sec: Int): String = if (sec > 0) formatDuration(sec) else "Не указано"
fun defaultDuration(pid: String?, c: Catalog): Int = pid?.let { c.practices[it]?.recMin?.times(60) } ?: 300
