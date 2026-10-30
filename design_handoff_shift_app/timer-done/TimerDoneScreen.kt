package app.shift.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.ui.semantics.Role
import app.shift.domain.timer.StatUi
import app.shift.domain.timer.TimerDoneUi
import app.shift.ui.icons.ShiftIcons
import app.shift.ui.theme.Shift

// TODO: заменить на FontFamily Nunito из темы проекта.
private val Nunito: FontFamily = FontFamily.Default

/** Системный «назад» = onDone — повесить в навигации (BackHandler / PredictiveBackHandler). */
@Composable
fun TimerDoneScreen(ui: TimerDoneUi, onDone: () -> Unit, onHowNow: () -> Unit, modifier: Modifier = Modifier) {
  val c = Shift.colors
  Column(
    modifier.fillMaxSize().background(c.bg)
      .windowInsetsPadding(WindowInsets.systemBars)
      .padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 30.dp),
    verticalArrangement = Arrangement.spacedBy(22.dp),
  ) {
    Column(
      Modifier.weight(1f).fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      MeditationBadge(128.dp)
      Text(
        ui.title, color = c.ink, textAlign = TextAlign.Center,
        style = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, lineHeight = 33.6.sp, lineBreak = LineBreak.Heading),
        modifier = Modifier.semantics { heading() },
      )
      Text(ui.meta, color = c.muted, fontSize = 13.sp, textAlign = TextAlign.Center)
    }
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      StatCard(ui.today, Modifier.weight(1f).fillMaxHeight())
      StatCard(ui.week, Modifier.weight(1f).fillMaxHeight())
    }
    Text(
      "Сессия сохранена. Отметь, как ты сейчас, — увидишь сдвиг.",
      color = c.muted, fontSize = 13.sp, lineHeight = 19.5.sp, textAlign = TextAlign.Center,
      style = TextStyle(lineBreak = LineBreak.Paragraph), modifier = Modifier.fillMaxWidth(),
    )
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      Pill("Готово", Modifier.weight(1f), onDone, bg = Modifier.background(c.second), fg = c.ink, size = 15)
      Pill("Как теперь?", Modifier.weight(2f), onHowNow, bg = Modifier.background(c.accent), fg = Color.White, size = 16)
    }
  }
}

@Composable
fun MeditationBadge(size: Dp) {
  val c = Shift.colors
  Box(Modifier.size(size).clip(CircleShape).background(c.subtle), contentAlignment = Alignment.Center) {
    Icon(ShiftIcons.Meditation, contentDescription = null, tint = c.accentText, modifier = Modifier.size(size * 66 / 128))
  }
}

@Composable
fun StatCard(stat: StatUi, modifier: Modifier = Modifier) {
  val c = Shift.colors
  Column(
    modifier.clip(RoundedCornerShape(20.dp)).background(c.card)
      .semantics(mergeDescendants = true) { contentDescription = stat.a11y }
      .padding(horizontal = 16.dp, vertical = 14.dp),
    verticalArrangement = Arrangement.spacedBy(2.dp),
  ) {
    Text(stat.label.uppercase(), color = c.label, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
    Text(stat.value, color = c.ink, style = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, lineHeight = 26.4.sp, fontFeatureSettings = "tnum"))
    Text(stat.sub, color = c.muted, fontSize = 12.sp)
  }
}

@Composable
private fun Pill(text: String, modifier: Modifier, onClick: () -> Unit, bg: Modifier, fg: Color, size: Int) {
  Box(
    modifier.heightIn(min = 56.dp).clip(RoundedCornerShape(30.dp)).then(bg)
      .clickable(role = Role.Button, onClick = onClick).padding(horizontal = 12.dp),
    contentAlignment = Alignment.Center,
  ) {
    Text(text, color = fg, fontSize = size.sp, fontWeight = FontWeight.Bold, maxLines = 1, softWrap = false)
  }
}

/*
 * @Preview — в androidMain (или через compose-multiplatform preview):
 *
 * private val previewUi = TimerDoneUi(
 *   sessionId = "p", title = "20 минут тишины", meta = "Чаша · 2 интервала · 07:15–07:35",
 *   today = StatUi("Сегодня", "35 мин", "2 сессии"), week = StatUi("За неделю", "1 ч 50 мин", "4 дня из 7"),
 * )
 * @Preview(name = "Светлая") @Composable fun TimerDoneLight() = ShiftTheme(dark = false) { TimerDoneScreen(previewUi, {}, {}) }
 * @Preview(name = "Тёмная") @Composable fun TimerDoneDark() = ShiftTheme(dark = true) { TimerDoneScreen(previewUi, {}, {}) }
 * @Preview(name = "Шрифт 200 %", fontScale = 2f) @Composable fun TimerDoneLarge() = ShiftTheme(dark = false) { TimerDoneScreen(previewUi, {}, {}) }
 */
