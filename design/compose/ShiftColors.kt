// Shift — цвета темы для Compose Multiplatform (Material 3 + расширения).
// Источник: прототип «Shift v10 Android.dc.html». Сгенерировано из токенов прототипа.
package app.shift.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset

// ── M3 ColorScheme ───────────────────────────────────────────────

val LightScheme = lightColorScheme(
  primary = Color(0xFF5A5FB0),
  onPrimary = Color(0xFFFFFFFF),
  primaryContainer = Color(0xFF5A5FB0),
  onPrimaryContainer = Color(0xFFFFFFFF),
  secondary = Color(0xFF1E1E22),
  onSecondary = Color(0xFFFFFFFF),
  secondaryContainer = Color(0xFFEBEAFB),
  onSecondaryContainer = Color(0xFF3E3F68),
  tertiary = Color(0xFF5F49B8),
  tertiaryContainer = Color(0xFFEFEAFE),
  onTertiaryContainer = Color(0xFF5F49B8),
  background = Color(0xFFF5F4FE),
  onBackground = Color(0xFF3E3F68),
  surface = Color(0xFFF5F4FE),
  onSurface = Color(0xFF3E3F68),
  surfaceVariant = Color(0xFFEFEEFC),
  onSurfaceVariant = Color(0xFF64658E),
  surfaceContainerLowest = Color(0xFFFFFFFF),
  surfaceContainerLow = Color(0xFFECEBFD),
  surfaceContainer = Color(0xFFEFEEFC),
  surfaceContainerHigh = Color(0xFFEBEAFB),
  surfaceContainerHighest = Color(0xFFE3E2F8),
  surfaceBright = Color(0xFFFFFFFF),
  surfaceDim = Color(0xFFEFEEFC),
  inverseSurface = Color(0xFF3E3F68),
  inverseOnSurface = Color(0xFFF5F4FE),
  inversePrimary = Color(0xFFBDBFF0),
  outline = Color(0xFFDEDDF5),
  outlineVariant = Color(0xFFF0EFFB),
  error = Color(0xFFD0566B),
  onError = Color(0xFFFFFFFF),
  errorContainer = Color(0xFFFBE3E7),
  onErrorContainer = Color(0xFFD0566B),
  scrim = Color(0x662F221D),
)

val DarkScheme = darkColorScheme(
  primary = Color(0xFFBDBFF0),
  onPrimary = Color(0xFF141416),
  primaryContainer = Color(0xFF8D90D8),
  onPrimaryContainer = Color(0xFF141416),
  secondary = Color(0xFFF1F0F3),
  onSecondary = Color(0xFF141416),
  secondaryContainer = Color(0xFF28282C),
  onSecondaryContainer = Color(0xFFF1F0F3),
  tertiary = Color(0xFFD6CBEB),
  tertiaryContainer = Color(0xFF26232C),
  onTertiaryContainer = Color(0xFFD6CBEB),
  background = Color(0xFF141416),
  onBackground = Color(0xFFF1F0F3),
  surface = Color(0xFF141416),
  onSurface = Color(0xFFF1F0F3),
  surfaceVariant = Color(0xFF28282C),
  onSurfaceVariant = Color(0xFFA3A2AA),
  surfaceContainerLowest = Color(0xFF1E1E21),
  surfaceContainerLow = Color(0xFF242427),
  surfaceContainer = Color(0xFF28282C),
  surfaceContainerHigh = Color(0xFF28282C),
  surfaceContainerHighest = Color(0xFF323236),
  surfaceBright = Color(0xFF1E1E21),
  surfaceDim = Color(0xFF28282C),
  inverseSurface = Color(0xFFF1F0F3),
  inverseOnSurface = Color(0xFF141416),
  inversePrimary = Color(0xFF5A5FB0),
  outline = Color(0xFF323236),
  outlineVariant = Color(0xFF28282C),
  error = Color(0xFFEE8E96),
  onError = Color(0xFF141416),
  errorContainer = Color(0xFF3A2226),
  onErrorContainer = Color(0xFFEE8E96),
  scrim = Color(0x662F221D),
)

// ── Расширения (то, чего нет в M3) ───────────────────────────────

@Immutable data class GoalColors(
  val color: Color,        // точка, иконка, орб
  val deep: Color,         // текст на soft
  val soft: Color,         // фон карточки цели
  val ring: Color,         // ореол, обводка выбранного
  val playerTop: Color,    // фон плеера, верх
  val playerBottom: Color, // фон плеера, низ
) { val playerBrush get() = Brush.verticalGradient(listOf(playerTop, playerBottom)) }

@Immutable data class Goals(val focus: GoalColors, val sleep: GoalColors, val calm: GoalColors, val energy: GoalColors, val own: GoalColors)

/** Состояния (квадранты энергия × напряжение). */
@Immutable data class QuadColors(val dot: Color, val bg: Color, val ink: Color)
@Immutable data class Quads(val calmEnergy: QuadColors, val tenseEnergy: QuadColors, val calmTired: QuadColors, val tenseTired: QuadColors)

/** Фазы дыхания: подложка блока фазы + точка. */
@Immutable data class PhaseColors(
  val inhale: Color, val topUp: Color, val hold: Color, val exhale: Color, val holdEmpty: Color,
  val dotInhale: Color, val dotTopUp: Color, val dotHold: Color, val dotExhale: Color, val dotHoldEmpty: Color,
)

/** Экраны инструментов: таймер и метроном. */
@Immutable data class ToolColors(val bg: Color, val ink: Color, val muted: Color, val accent: Color, val onAccent: Color, val track: Color, val line: Color, val endInk: Color, val glow: Color)

@Immutable data class ShiftColors(
  val bg: Color,
  val card: Color,
  val ink: Color,
  val muted: Color,
  val muted2: Color,
  val label: Color,
  val body: Color,
  val body2: Color,
  val subtle: Color,
  val second: Color,
  val accentText: Color,
  val line: Color,
  val divider: Color,
  val dash: Color,
  val summary: Color,
  val warnBg: Color,
  val warnInk: Color,
  val danger: Color,
  val glass: Color,
  val track: Color,
  val dot: Color,
  val navOff: Color,
  val selBg: Color,
  val selFg: Color,
  val disabled: Color,
  val before: Color,
  val shadow: Color,
  val pill: Color,
  val pillOff: Color,
  val hl: Color,
  val hl2: Color,
  val metroSub: Color,
  val accentBrush: List<Color>,     // главная кнопка, 135°
  val timerCardBrush: List<Color>,  // карточка «Посидеть в тишине», 135°
  val goals: Goals,
  val quads: Quads,
  val phases: PhaseColors,
  val tools: ToolColors,
  val isDark: Boolean,
) {
  val accent get() = Brush.linearGradient(accentBrush, start = Offset.Zero, end = Offset.Infinite)
  val timerCard get() = Brush.linearGradient(timerCardBrush, start = Offset.Zero, end = Offset.Infinite)
}

val LightShift = ShiftColors(
    bg = Color(0xFFF5F4FE),
    card = Color(0xFFFFFFFF),
    ink = Color(0xFF3E3F68),
    muted = Color(0xFF64658E),
    muted2 = Color(0xFF5C5D86),
    label = Color(0xFF6A6B94),
    body = Color(0xFF4F5079),
    body2 = Color(0xFF45466F),
    subtle = Color(0xFFEFEEFC),
    second = Color(0xFFEBEAFB),
    accentText = Color(0xFF5A5FB0),
    line = Color(0xFFDEDDF5),
    divider = Color(0xFFF0EFFB),
    dash = Color(0xFFCFCFF1),
    summary = Color(0xFFECEBFD),
    warnBg = Color(0xFFFFF1E2),
    warnInk = Color(0xFF6B4A12),
    danger = Color(0xFFD0566B),
    glass = Color(0xBFFFFFFF),
    track = Color(0x1F3E3F68),
    dot = Color(0xFFE3E2F8),
    navOff = Color(0xFFC3C4E4),
    selBg = Color(0xFF1E1E22),
    selFg = Color(0xFFFFFFFF),
    disabled = Color(0xFFCBCEF3),
    before = Color(0xFF6A6B94),
    shadow = Color(0x2E6E73D2),
    pill = Color(0xD9FFFFFF),
    pillOff = Color(0x59FFFFFF),
    hl = Color(0xA6FFFFFF),
    hl2 = Color(0x80FFFFFF),
    metroSub = Color(0xFF5B5FA0),
    accentBrush = listOf(Color(0xFF5A5FB0), Color(0xFF6268B8)),
    timerCardBrush = listOf(Color(0xFF8699FF), Color(0xFF86BAFF)),
    goals = Goals(
    focus = GoalColors(color = Color(0xFFA9C07A), deep = Color(0xFF3F5220), soft = Color(0xFFE9EFDB), ring = Color(0xFFD6E2BE), playerTop = Color(0xFFF2F5EA), playerBottom = Color(0xFFDCE7C6)),
    sleep = GoalColors(color = Color(0xFF8699FF), deep = Color(0xFF3A45A8), soft = Color(0xFFE7E9FF), ring = Color(0xFFD3D8FF), playerTop = Color(0xFFF6F6FF), playerBottom = Color(0xFFD3D8FF)),
    calm = GoalColors(color = Color(0xFF86BAFF), deep = Color(0xFF24508F), soft = Color(0xFFE1EEFE), ring = Color(0xFFC9DFFF), playerTop = Color(0xFFF5F9FF), playerBottom = Color(0xFFCCE0FF)),
    energy = GoalColors(color = Color(0xFFF6C95A), deep = Color(0xFF6E4A00), soft = Color(0xFFFCEFC8), ring = Color(0xFFF7DE97), playerTop = Color(0xFFFDF6E1), playerBottom = Color(0xFFF8E2A0)),
    own = GoalColors(color = Color(0xFFB9A6F5), deep = Color(0xFF5F49B8), soft = Color(0xFFEFEAFE), ring = Color(0xFFE0D7FC), playerTop = Color(0xFFFBFAFF), playerBottom = Color(0xFFE3DBFD)),
    ),
    quads = Quads(
    calmEnergy = QuadColors(dot = Color(0xFF9BB36A), bg = Color(0xFFE8F3E2), ink = Color(0xFF3F5A2A)),
    tenseEnergy = QuadColors(dot = Color(0xFFF3913F), bg = Color(0xFFFDEBDD), ink = Color(0xFF8A3E10)),
    calmTired = QuadColors(dot = Color(0xFF8EC0E6), bg = Color(0xFFE1EEFE), ink = Color(0xFF284F86)),
    tenseTired = QuadColors(dot = Color(0xFFB98AF2), bg = Color(0xFFEEE6FE), ink = Color(0xFF5531A0)),
    ),
    phases = PhaseColors(
      inhale = Color(0xFFDFEDF7), topUp = Color(0xFFDFEDF7), hold = Color(0xFFFCEFC8), exhale = Color(0xFFE9EFDB), holdEmpty = Color(0xFFEDE1FC),
      dotInhale = Color(0xFF6FA8D6), dotTopUp = Color(0xFF9CC8EA), dotHold = Color(0xFFE2B23A), dotExhale = Color(0xFF8DA85A), dotHoldEmpty = Color(0xFFA57BE0),
    ),
    tools = ToolColors(bg = Color(0xFFF5F4FE), ink = Color(0xFF3E3F68), muted = Color(0xFF64658E), accent = Color(0xFF5A5FB0), onAccent = Color(0xFFFFFFFF), track = Color(0xFFE3E2F8), line = Color(0xFFCFCFF1), endInk = Color(0xFF3A45A8), glow = Color(0x295A5FB0)),
    isDark = false,
)

val DarkShift = ShiftColors(
    bg = Color(0xFF141416),
    card = Color(0xFF1E1E21),
    ink = Color(0xFFF1F0F3),
    muted = Color(0xFFA3A2AA),
    muted2 = Color(0xFFB3B2B9),
    label = Color(0xFF85848C),
    body = Color(0xFFD6D5DB),
    body2 = Color(0xFFE0DFE4),
    subtle = Color(0xFF28282C),
    second = Color(0xFF28282C),
    accentText = Color(0xFFBDBFF0),
    line = Color(0xFF323236),
    divider = Color(0xFF28282C),
    dash = Color(0xFF3E3E43),
    summary = Color(0xFF242427),
    warnBg = Color(0xFF36301F),
    warnInk = Color(0xFFEED8A6),
    danger = Color(0xFFEE8E96),
    glass = Color(0x4D000000),
    track = Color(0x24FFFFFF),
    dot = Color(0xFF323236),
    navOff = Color(0xFF5F5E66),
    selBg = Color(0xFFF1F0F3),
    selFg = Color(0xFF141416),
    disabled = Color(0xFF3E3E43),
    before = Color(0xFF9B9AA2),
    shadow = Color(0x80000000),
    pill = Color(0x1FFFFFFF),
    pillOff = Color(0x0DFFFFFF),
    hl = Color(0x0FFFFFFF),
    hl2 = Color(0x14FFFFFF),
    metroSub = Color(0xFFD7D3E3),
    accentBrush = listOf(Color(0xFF8D90D8), Color(0xFF9AA3DA)),
    timerCardBrush = listOf(Color(0xFF2C2A33), Color(0xFF25242B)),
    goals = Goals(
    focus = GoalColors(color = Color(0xFFA9C07A), deep = Color(0xFFC6DBAE), soft = Color(0xFF1F241D), ring = Color(0xFF29301F), playerTop = Color(0xFF141416), playerBottom = Color(0xFF1F241D)),
    sleep = GoalColors(color = Color(0xFF8699FF), deep = Color(0xFFCFCDEB), soft = Color(0xFF202027), ring = Color(0xFF2A2A34), playerTop = Color(0xFF141416), playerBottom = Color(0xFF202027)),
    calm = GoalColors(color = Color(0xFF86BAFF), deep = Color(0xFFB3CDEB), soft = Color(0xFF1C2128), ring = Color(0xFF252C36), playerTop = Color(0xFF141416), playerBottom = Color(0xFF1C2128)),
    energy = GoalColors(color = Color(0xFFF6C95A), deep = Color(0xFFEBD49A), soft = Color(0xFF29251A), ring = Color(0xFF363020), playerTop = Color(0xFF141416), playerBottom = Color(0xFF29251A)),
    own = GoalColors(color = Color(0xFFB9A6F5), deep = Color(0xFFD6CBEB), soft = Color(0xFF24222A), ring = Color(0xFF2F2C36), playerTop = Color(0xFF141416), playerBottom = Color(0xFF24222A)),
    ),
    quads = Quads(
    calmEnergy = QuadColors(dot = Color(0xFF9BB36A), bg = Color(0xFF212820), ink = Color(0xFFC6DBAE)),
    tenseEnergy = QuadColors(dot = Color(0xFFF3913F), bg = Color(0xFF30241E), ink = Color(0xFFF2BF97)),
    calmTired = QuadColors(dot = Color(0xFF8EC0E6), bg = Color(0xFF1D2530), ink = Color(0xFFB3CDEB)),
    tenseTired = QuadColors(dot = Color(0xFFB98AF2), bg = Color(0xFF29222F), ink = Color(0xFFD5C1EF)),
    ),
    phases = PhaseColors(
      inhale = Color(0xFF1D2530), topUp = Color(0xFF1D2530), hold = Color(0xFF29251A), exhale = Color(0xFF212820), holdEmpty = Color(0xFF29222F),
      dotInhale = Color(0xFF6FA8D6), dotTopUp = Color(0xFF9CC8EA), dotHold = Color(0xFFE2B23A), dotExhale = Color(0xFF8DA85A), dotHoldEmpty = Color(0xFFA57BE0),
    ),
    tools = ToolColors(bg = Color(0xFF141416), ink = Color(0xFFF1F0F3), muted = Color(0xFFA3A2AA), accent = Color(0xFFC9C6E8), onAccent = Color(0xFF141416), track = Color(0xFF28282C), line = Color(0xFF323236), endInk = Color(0xFFCFCDEB), glow = Color(0x29C9C6E8)),
    isDark = true,
)

// ── Общие для обеих тем ──────────────────────────────────────────

/** Лица оценки «Как теперь?»: круг и мягкий фон. Штрихи — Color(0xFF3E3F68). */
object FaceColors {
  val worse = Color(0xFFF6B47E) to Color(0xFFFDEBDD)  // Хуже
  val same = Color(0xFFDCDCEE) to Color(0xFFEFEEFC)   // Так же
  val better = Color(0xFFA6CFEE) to Color(0xFFE1EEFE) // Лучше
  val great = Color(0xFFB6CA8E) to Color(0xFFE8F3E2)  // Отлично
  val stroke = Color(0xFF3E3F68)
}

/** Декоративные «капли» в hero-карточках и онбординге (4 цвета на цель). */
object BlobColors {
  val focus = listOf(Color(0xFFD9E8B8), Color(0xFF7FB08A), Color(0xFFBFE3C8), Color(0xFF5E8F55))
  val sleep = listOf(Color(0xFFC8B2FF), Color(0xFF86BAFF), Color(0xFFB9F0EC), Color(0xFF5D6FD8))
  val calm = listOf(Color(0xFFB8D8FF), Color(0xFF8699FF), Color(0xFFBFEFEA), Color(0xFF4F7FD0))
  val energy = listOf(Color(0xFFFFE3A0), Color(0xFFF2A86B), Color(0xFFFFD3B8), Color(0xFFC9772E))
  val own = listOf(Color(0xFFD9C8FF), Color(0xFF9C8BF0), Color(0xFFF2D0F2), Color(0xFF6E5BC9))
}

// ── Подключение ──────────────────────────────────────────────────

val LocalShiftColors = staticCompositionLocalOf { LightShift }

enum class ThemePref { SYSTEM, LIGHT, DARK }

@Composable
fun ShiftTheme(dark: Boolean, content: @Composable () -> Unit) {
  val scheme = if (dark) DarkScheme else LightScheme
  val ext = if (dark) DarkShift else LightShift
  CompositionLocalProvider(LocalShiftColors provides ext) {
    MaterialTheme(colorScheme = scheme, content = content) // + typography, shapes
  }
}

object Shift { val colors: ShiftColors @Composable get() = LocalShiftColors.current }
