package app.shift.domain.timer

import app.shift.domain.progress.Session
import app.shift.domain.progress.SessionKind
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TimerDoneLogicTest {
  private val today = LocalDate(2026, 10, 29)
  private fun s(id: String, daysAgo: Int, sec: Int, kind: SessionKind = SessionKind.PRACTICE) = Session(
    id = id, pid = null, title = null, kind = kind, before = null, rating = null, note = null,
    date = LocalDate.fromEpochDays(today.toEpochDays() - daysAgo), time = "07:00", durationSec = sec,
  )

  @Test fun threshold() {
    assertFalse(shouldShowTimerDone(40.0, 10))
    assertTrue(shouldShowTimerDone(40.1, 10))
  }

  @Test fun title() {
    assertEquals("Меньше минуты тишины", timerDoneTitle(29))
    assertEquals("1 минута тишины", timerDoneTitle(31))
    assertEquals("2 минуты тишины", timerDoneTitle(120))
    assertEquals("5 минут тишины", timerDoneTitle(300))
    assertEquals("21 минута тишины", timerDoneTitle(21 * 60))
    assertEquals("11 минут тишины", timerDoneTitle(11 * 60))
  }

  @Test fun meta() {
    assertEquals("Чаша · 2 интервала · 07:15–07:35", timerDoneMeta("Чаша", 2, 1200, LocalTime(7, 35)))
    assertEquals("07:15–07:35", timerDoneMeta(null, 0, 1200, LocalTime(7, 35)))
    assertEquals("1 интервал · 23:50–00:10", timerDoneMeta(null, 1, 1200, LocalTime(0, 10)))
  }

  @Test fun statsIncludeCurrent() {
    val cur = s("cur", 0, 1200, SessionKind.TIMER)
    val all = listOf(cur, s("a", 0, 900), s("b", 2, 600), s("c", 6, 300), s("old", 7, 3000))
    val ui = timerDoneUi(all, "cur", 1200, "Чаша", 0, LocalTime(7, 35), today)
    assertEquals("35 мин", ui.today.value)
    assertEquals("2 сессии", ui.today.sub)
    assertEquals("50 мин", ui.week.value)
    assertEquals("3 дня из 7", ui.week.sub)
  }

  @Test fun onlyCurrent() {
    val ui = timerDoneUi(listOf(s("cur", 0, 45, SessionKind.TIMER)), "cur", 45, null, 0, LocalTime(9, 0), today)
    assertEquals("45 с", ui.today.value)
    assertEquals("1 сессия", ui.today.sub)
    assertEquals("1 день из 7", ui.week.sub)
  }
}
