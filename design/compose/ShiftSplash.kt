package app.shift.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/*
 * Знак Shift — вариант 3b «Сдвиг в цветах целей».
 * Системный splash (Android 12+ / iOS Launch Screen) рисует тот же кадр, что и первый кадр
 * этого оверлея: 1 единица знака = 1.75 dp, внешнее кольцо Ø 140 dp, по центру экрана.
 * Android: installSplashScreen() + setOnExitAnimationListener { it.remove() } — сразу убрать
 * системный splash, дальше анимирует ShiftSplashOverlay поверх «Сегодня» / онбординга.
 */
private val RingR = floatArrayOf(40f, 30f, 20.5f, 11.5f)
private val RingOff = arrayOf(-3f to 3f, 2.5f to -2.5f, 7.5f to -7.5f, 12f to -12f)
private val LightRings = listOf(Color(0xFFFBE3A6), Color(0xFFBCD8FF), Color(0xFFC9DBA8), Color(0xFF8699FF))
private val DarkRings = listOf(Color(0xFF4A4128), Color(0xFF2C4058), Color(0xFF34432A), Color(0xFFAEB2F2))
private val MergeEasing = CubicBezierEasing(0.45f, 0f, 0.2f, 1f)
private val ShiftEasing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)

/**
 * @param onReveal старт каскада контента под оверлеем (fade + 12 dp, задержки 150/230/310/390 мс)
 * @param onFinished убрать оверлей из композиции
 */
@Composable
fun ShiftSplashOverlay(
    dark: Boolean,
    reduceMotion: Boolean,
    onReveal: () -> Unit,
    onFinished: () -> Unit,
) {
    val bg = if (dark) Color(0xFF141416) else Color(0xFFF5F4FE)
    val colors = if (dark) DarkRings else LightRings
    val merge = remember { Animatable(0f) }   // 0 — кольца смещены, 1 — концентрический орб
    val exit = remember { Animatable(0f) }    // 0 — splash, 1 — растворился

    LaunchedEffect(Unit) {
        if (reduceMotion) { onReveal(); onFinished(); return@LaunchedEffect }
        merge.animateTo(1f, tween(900, easing = MergeEasing))
        onReveal()
        exit.animateTo(1f, tween(700, easing = ShiftEasing))
        onFinished()
    }

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 1f - exit.value }
            .background(bg),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            Modifier
                .size(160.dp)
                .graphicsLayer { val s = 1f + 0.6f * exit.value; scaleX = s; scaleY = s },
        ) {
            val u = 1.75.dp.toPx()
            val k = 1f - merge.value
            RingR.forEachIndexed { i, r ->
                val (dx, dy) = RingOff[i]
                drawCircle(colors[i], radius = r * u, center = center + Offset(dx * k * u, dy * k * u))
            }
        }
    }
}
