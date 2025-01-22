package ru.topbun.rapid.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImagePainter.State.Empty.painter
import okhttp3.internal.http2.Header
import ru.topbun.pawmate.presentation.theme.Colors
import ru.topbun.pawmate.presentation.theme.Typography.APP_TEXT
import ru.topbun.rapid.R
import ru.topbun.rapid.entity.News
import ru.topbun.rapid.presentation.screens.appeals.AppealsScreen
import ru.topbun.rapid.presentation.screens.settings.SettingsScreen
import ru.topbun.rapid.presentation.screens.submit.SubmitScreen
import ru.topbun.rapid.presentation.screens.submit.SubmitViewModel
import ru.topbun.rapid.presentation.theme.Fonts
import ru.topbun.rapid.presentation.theme.components.bottomBorder
import ru.topbun.rapid.utils.formatToNewsDate
import ru.topbun.rapid.utils.toDate
import java.util.Date

object MainScreen: Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val navigator = LocalNavigator.currentOrThrow
            Header()
            Box(modifier = Modifier.weight(1f)){
                NewsList()
                FloatingActionButton(
                    modifier = Modifier.padding(12.dp)
                        .size(56.dp)
                        .align(Alignment.BottomEnd),
                    containerColor = Colors.CYAN,
                    onClick = { navigator.push(SubmitScreen()) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Colors.WHITE
                    )
                }
            }
        }
    }

}

@Composable
private fun NewsList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Spacer(Modifier.height(10.dp)) }
        items(items = newsList.reversed()) { NewsItem(it) }
        item { Spacer(Modifier.height(10.dp)) }
    }
}

@Composable
private fun NewsItem(news: News) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp)
            .sizeIn(maxHeight = 120.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(Colors.WHITE, RoundedCornerShape(8.dp))
            .padding(start = 12.dp)
    ){
        Column(
            modifier = Modifier.weight(1f).padding(top = 20.dp, bottom = 12.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.title,
                style = APP_TEXT,
                fontSize = 16.sp,
                fontFamily = Fonts.SF.SEMI_BOLD,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.date.formatToNewsDate(),
                style = APP_TEXT,
                fontSize = 11.sp,
                fontFamily = Fonts.SF.MEDIUM,
                color = Colors.GRAY_DARK,
            )
        }
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.fillMaxHeight().aspectRatio(1.3f).clip(RoundedCornerShape(8.dp)),
            painter = painterResource(news.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bottomBorder(1.dp, Colors.GRAY_LIGHT)
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val navigator = LocalNavigator.currentOrThrow
        Text(
            modifier = Modifier.weight(1f),
            text = "Главная",
            style = APP_TEXT,
            fontSize = 20.sp,
            fontFamily = Fonts.SF.SEMI_BOLD,
        )
        IconButton(
            onClick = { navigator.push(AppealsScreen) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_folder),
                contentDescription = null,
                tint = Colors.BLACK
            )
        }
        IconButton(
            onClick = { navigator.push(SettingsScreen) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = null,
                tint = Colors.BLACK
            )
        }
    }
}

private val newsList = listOf(
    News(
        title = "На ВДНХ в Москве продается машинное место по цене трёхкомнатной квартиры.\nЗа 15 млн покупатель получит пустое место на парковке размером 36 кв. м.",
        imageRes = R.drawable.news1,
        date = "03.02.2023".toDate()
    ),
    News(
        title = "❗\uFE0FНеизвестного в маске с предметом, похожим на оружие разыскивает полиция ЦАО Москвы.\n" +
                "\n" +
                "Парня заметили у школы 2123 на Столовом переулке — ученики с сотрудниками учебного заведения сообщили об этом в полицию.",
        imageRes = R.drawable.news2,
        date = "24.05.2023".toDate()
    ),
    News(
        title = "\uD83D\uDE87Сокольническую линию продлят на север до 2032 года.\n" +
                "\n" +
                "После станции «Бульвар Рокоссовского» появится как минимум еще две станции: «МГСУ» и «Холмогорская».",
        imageRes = R.drawable.news3,
        date = "12.08.2023".toDate()
    ),
    News(
        title = "\uD83E\uDEB3Паутинных клещей в ёлке обнаружили москвичи. \n" +
                "\n" +
                "Мужчина приобрёл ель за 16,5 тысяч рублей, привёз её домой и нарядил. Позже он с женой заметили, что вся гостиная в паутине. Причиной этого стали паутинные клещи, которые были в ёлке. Мужчина потребовал компенсации, но ему предложили 5 тысяч рублей и скидку на будущую покупку. Он уже подал в суд на продавца.",
        imageRes = R.drawable.news4,
        date = "01.09.2023".toDate()
    ),
    News(
        title = "❄\uFE0FВ Москве практически исчез снежный покров, — синоптики.",
        imageRes = R.drawable.news5,
        date = "11.11.2023".toDate()
    ),
    News(
        title = "В Москве продолжается фестиваль «Снег и лед в Москве» от проекта «Зима в Москве»",
        imageRes = R.drawable.news6,
        date = "29.02.2024".toDate()
    ),
    News(
        title = "❄\uFE0FВ Москве почти не осталось снега, но в вольере Катюши его хоть отбавляй. \n" +
                "\n" +
                "Так что, если вы соскучились по зиме, навестите малышку в зоопарке.",
        imageRes = R.drawable.news7,
        date = "18.03.2024".toDate()
    ),
    News(
        title = "\uD83C\uDF01Этим утром небо над столицей окрасилось в розовый",
        imageRes = R.drawable.news8,
        date = "02.06.2024".toDate()
    ),News(
        title = "\uD83D\uDEA8Крупное ДТП произошло в Царицыно — 8 человек пострадали. \n" +
                "\n" +
                "Свидетели сообщают о 4 погибших и 4 пострадавших, которые находятся в тяжёлом состоянии. На месте много машин скорой помощи.",
        imageRes = R.drawable.news9,
        date = "30.07.2024".toDate()
    ),News(
        title = "\uD83E\uDD2FВ Москве интернет-коуч и эксперт по снятию стресса покончила с собой из-за сильного стресса.\n" +
                "\n" +
                "Она проводила тренинги по управлению тревожностью и повышению психологической устойчивости, однако сама проходила лечение в психдиспансере. Её дочь обнаружила тело 16 января. В прощальной записке женщина написала: «Летела, хотела, но не смогла. Сгорела. P.S. Простите всех, кого я знаю».",
        imageRes = R.drawable.news10,
        date = "22.10.2024".toDate()
    ),
    News(
        title = "⚜\uFE0FДом в виде огромного слитка построят в Москве.\n" +
                "\n" +
                "Квартиры в этом ЖК, названном в честь Казимира Малевича, уже раскупили.",
        imageRes = R.drawable.news11,
        date = "31.12.2024".toDate()
    ),


)
