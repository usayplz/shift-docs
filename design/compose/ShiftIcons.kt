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
 * Иконка медитации (контур в позе лотоса). Заменяет иконку таймера:
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
                "M106,50 a22,22 0 1,0 44,0 a22,22 0 1,0 -44,0",
                "M110,94 C92,98 84,108 80,126 L72,170",
                "M146,94 C164,98 172,108 176,126 L184,170",
                "M100,128 C102,144 104,158 106,174",
                "M156,128 C154,144 152,158 150,174",
                "M72,170 C98,166 114,174 128,184 C142,174 158,166 184,170",
                "M72,170 C46,176 34,192 44,202 C62,220 122,212 162,196",
                "M184,170 C210,176 222,192 212,202 C194,220 134,212 94,196",
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
