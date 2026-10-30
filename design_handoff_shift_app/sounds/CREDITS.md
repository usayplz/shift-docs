# Звуки Shift

Формат: WAV, 44,1 кГц, 16 бит, моно. Каждый файл — один удар, начало без тишины, затухание с плавным концом.

## Записанные звуки

Все — CC0 или Public domain с Wikimedia Commons: можно использовать в коммерческом приложении без указания авторства. Указание авторов ниже — для порядка.

| Файл | Название в приложении | Где звучит | Источник | Лицензия | Автор |
|---|---|---|---|---|---|
| `bowl.wav` | Чаша | таймер: начало / конец / интервал; конец практики (переход на «Как теперь?») | [SingingBowl1.ogg](https://commons.wikimedia.org/wiki/File:SingingBowl1.ogg) | Public domain | BambooBeast |
| `gong.wav` | Гонг | таймер: начало / конец / интервал | [Gong55.ogg](https://commons.wikimedia.org/wiki/File:Gong55.ogg) | CC0 | stephan |
| `chime.wav` | Колокольчик | таймер: начало / конец / интервал | [Bell-ring.flac](https://commons.wikimedia.org/wiki/File:Bell-ring.flac) | CC0 | qubodup |
| `metro.wav` | Метроном | метроном; таймер | [50BPMclicktrack.ogg](https://commons.wikimedia.org/wiki/File:50BPMclicktrack.ogg) | Public domain | Yintan (talk) (Uploads) |
| `wood.wav` | Дерево | метроном; таймер | [Claves.ogg](https://commons.wikimedia.org/wiki/File:Claves.ogg) | Public domain | No machine-readable author provided. Wikipedia-ce  |
| `drum.wav` | Барабан | метроном | [Bęben obręczowy uderzenia pojedyncze.flac](https://commons.wikimedia.org/wiki/File:B%C4%99ben_obr%C4%99czowy_uderzenia_pojedyncze.flac) | CC0 | Swietliste |

## Тоны интерфейса (синтез, собственные)

| Файл | Где звучит |
|---|---|
| `tick.wav` | отсчёт 3-2-1 перед практикой |
| `go.wav` | старт практики после отсчёта (воспроизводить с pitch ×1,5) |
| `phase.wav` | смена фазы дыхания при включённом «Звуке тона»: вдох — pitch ×1,12, выдох — ×0,84, задержка — ×1 |

## Как воспроизводить

- Акцент метронома — тот же файл с pitch ×1,26 и полной громкостью; обычная доля — pitch ×1, громкость ×0,6; деление доли — pitch ×0,9, громкость ×0,3.
- «Вибрация» в списке звуков таймера — не файл, а вибромотор.
- iOS: `AVAudioEngine` + `AVAudioPlayerNode` (`varispeed` / `rate` для pitch). Android: `SoundPool` (параметр `rate` 0,5–2,0) или Oboe для метронома.
- Для размера приложения можно перекодировать в AAC (.m4a) или CAF — качество не пострадает.

`breath-615047.mp3` — звук дыхания для плеера (Pixabay, лицензия Pixabay Content License).
