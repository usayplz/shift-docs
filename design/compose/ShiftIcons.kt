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
                "M128,35 C93,70 93,141 128,186 C163,141 163,70 128,35 Z",
                "M108,181 C73,173 40,146 33,100 C65,100 93,115 108,136",
                "M148,181 C183,173 216,146 223,100 C191,100 163,115 148,136",
                "M38,221 L218,221",
            ).forEach { d ->
                addPath(
                    pathData = addPathNodes(d),
                    fill = null,
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 16f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                )
            }
        }.build()
    }
}
