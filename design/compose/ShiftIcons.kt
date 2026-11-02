package app.shift.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * Иконка медитации (цветок лотоса: три лепестка и линия основания). Заменяет иконку таймера:
 * «Сегодня → Посидеть в тишине», «Каталог → Таймер медитации», карточка «Таймер».
 * Общая для Android и iOS (Compose Multiplatform).
 *
 * Icon(ShiftIcons.Meditation, contentDescription = null, tint = colors.ink, modifier = Modifier.size(28.dp))
 * Размеры в прототипе: 28 dp на «Сегодня», 26 dp в каталоге, 24 dp на карточке «Таймер».
 */
object ShiftIcons {
    val Meditation: ImageVector by lazy {
        ImageVector.Builder(
            name = "Meditation",
            defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 256f, viewportHeight = 256f,
        ).apply {
            listOf(
                "M128,56 C100,84 100,140 128,176 C156,140 156,84 128,56 Z",
                "M112,172 C84,166 58,144 52,108 C78,108 100,120 112,136",
                "M144,172 C172,166 198,144 204,108 C178,108 156,120 144,136",
                "M56,204 L200,204",
            ).forEach { d ->
                addPath(
                    pathData = addPathNodes(d),
                    fill = null,
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 13f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                )
            }
        }.build()
    }
}
