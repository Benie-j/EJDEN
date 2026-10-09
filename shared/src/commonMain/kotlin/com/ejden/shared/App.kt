package com.ejden.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Primary = Color(0xFF176B87)
private val PrimaryDark = Color(0xFF12566C)
private val PrimarySoft = Color(0xFFEAF4F7)
private val Background = Color(0xFFF5F7F8)
private val SurfaceColor = Color(0xFFFFFFFF)
private val MainText = Color(0xFF172126)
private val SecondaryText = Color(0xFF68777D)
private val LightText = Color(0xFF8D9A9F)
private val BorderColor = Color(0xFFE2E8EA)
private val Success = Color(0xFF168A5B)
private val Warning = Color(0xFFD98A16)
private val Error = Color(0xFFD64545)

@Composable
fun EjdenApp() {
    var showQuickActions by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf<String?>(null) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar(
                    onNotifications = {
                        notice = "Les notifications seront reliées aux alertes de votre activité."
                    },
                    onProfile = {
                        notice = "Les paramètres seront intégrés dans leur écran dédié."
                    }
                )

                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    val wide = maxWidth >= 720.dp

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                horizontal = if (wide) 28.dp else 16.dp,
                                vertical = if (wide) 22.dp else 16.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(if (wide) 16.dp else 12.dp)
                    ) {
                        DashboardHeader(
                            wide = wide,
                            onPeriod = {
                                notice = "Le choix de période sera raccordé aux données de vente."
                            },
                            onNewSale = {
                                notice = "L'écran Nouvelle vente sera raccordé à la caisse et au stock."
                            }
                        )

                        KpiSection(wide = wide)

                        if (wide) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                SalesPanel(modifier = Modifier.weight(1.7f))
                                CashPanel(modifier = Modifier.weight(0.8f))
                            }
                        } else {
                            SalesPanel()
                            CashPanel()
                        }

                        RecentSalesPanel()

                        StockPanel()

                        if (wide) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                MiniPanel(
                                    eyebrow = "CLIENTS",
                                    title = "Clients actifs",
                                    value = "0",
                                    description = "client enregistré",
                                    modifier = Modifier.weight(1f),
                                    onClick = { notice = "La liste des clients sera intégrée dans son écran dédié." }
                                )
                                MiniPanel(
                                    eyebrow = "CRÉANCES",
                                    title = "À encaisser",
                                    value = "—",
                                    description = "FCFA",
                                    modifier = Modifier.weight(1f),
                                    onClick = { notice = "Les crédits clients seront raccordés aux ventes à crédit." }
                                )
                                MiniPanel(
                                    eyebrow = "ATTENTION",
                                    title = "Alertes",
                                    value = "0",
                                    description = "aucune alerte",
                                    modifier = Modifier.weight(1f),
                                    onClick = { notice = "Les alertes seront calculées à partir des données réelles." }
                                )
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                MiniPanel(
                                    eyebrow = "CLIENTS",
                                    title = "Clients actifs",
                                    value = "0",
                                    description = "client enregistré",
                                    onClick = { notice = "La liste des clients sera intégrée dans son écran dédié." }
                                )
                                MiniPanel(
                                    eyebrow = "CRÉANCES",
                                    title = "À encaisser",
                                    value = "—",
                                    description = "FCFA",
                                    onClick = { notice = "Les crédits clients seront raccordés aux ventes à crédit." }
                                )
                                MiniPanel(
                                    eyebrow = "ATTENTION",
                                    title = "Alertes",
                                    value = "0",
                                    description = "aucune alerte",
                                    onClick = { notice = "Les alertes seront calculées à partir des données réelles." }
                                )
                            }
                        }

                        ActivityPanel()

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                BottomNavigation(
                    onSelect = { item ->
                        if (item == "＋") {
                            showQuickActions = true
                        } else if (item != "Accueil") {
                            notice = "La section $item sera raccordée à son écran natif."
                        }
                    }
                )
            }
        }

        if (showQuickActions) {
            AlertDialog(
                onDismissRequest = { showQuickActions = false },
                containerColor = SurfaceColor,
                title = {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            "ACTION RAPIDE",
                            color = Primary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.3.sp
                        )
                        Text(
                            "Que souhaitez-vous faire ?",
                            color = MainText,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        QuickAction("Nouvelle vente", "Enregistrer une nouvelle vente") {
                            showQuickActions = false
                            notice = "L'enregistrement des ventes sera intégré dans l'écran Ventes."
                        }
                        QuickAction("Ajouter un produit", "Enregistrer un nouveau produit") {
                            showQuickActions = false
                            notice = "La création de produit sera intégrée dans l'écran Produits."
                        }
                        QuickAction("Ajouter un client", "Créer une nouvelle fiche client") {
                            showQuickActions = false
                            notice = "La création de client sera intégrée dans l'écran Clients."
                        }
                        QuickAction("Nouvelle dépense", "Enregistrer une dépense") {
                            showQuickActions = false
                            notice = "La saisie des dépenses sera intégrée dans l'écran Dépenses."
                        }
                        QuickAction("Entrée de caisse", "Enregistrer une entrée d'argent") {
                            showQuickActions = false
                            notice = "Les mouvements de caisse seront intégrés dans l'écran Caisse."
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showQuickActions = false }) {
                        Text("Fermer", color = Primary)
                    }
                }
            )
        }

        notice?.let { message ->
            AlertDialog(
                onDismissRequest = { notice = null },
                containerColor = SurfaceColor,
                title = {
                    Text(
                        "EJDEN",
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = { Text(message, color = SecondaryText) },
                confirmButton = {
                    TextButton(onClick = { notice = null }) {
                        Text("Compris", color = Primary)
                    }
                }
            )
        }
    }
}

@Composable
private fun TopBar(
    onNotifications: () -> Unit,
    onProfile: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceColor)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EjdenLogo(
                modifier = Modifier.size(38.dp),
                color = Primary
            )

            Spacer(modifier = Modifier.width(9.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "EJDEN",
                    color = MainText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "Bonjour",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }

            TextButton(onClick = onNotifications) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.size(22.dp)
                ) {
                    val bell = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.12f)
                        cubicTo(
                            size.width * 0.28f, size.height * 0.12f,
                            size.width * 0.24f, size.height * 0.34f,
                            size.width * 0.24f, size.height * 0.49f
                        )
                        lineTo(size.width * 0.17f, size.height * 0.76f)
                        lineTo(size.width * 0.83f, size.height * 0.76f)
                        lineTo(size.width * 0.76f, size.height * 0.49f)
                        cubicTo(
                            size.width * 0.76f, size.height * 0.34f,
                            size.width * 0.72f, size.height * 0.12f,
                            size.width * 0.5f, size.height * 0.12f
                        )
                    }
                    drawPath(
                        path = bell,
                        color = SecondaryText,
                        style = Stroke(
                            width = 1.7.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawLine(
                        color = SecondaryText,
                        start = Offset(size.width * 0.40f, size.height * 0.85f),
                        end = Offset(size.width * 0.60f, size.height * 0.85f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            TextButton(onClick = onProfile) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(PrimarySoft, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "E",
                            color = Primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Mon espace",
                        color = MainText,
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Background,
                    shape = RoundedCornerShape(13.dp)
                )
                .border(
                    width = 1.dp,
                    color = BorderColor,
                    shape = RoundedCornerShape(13.dp)
                )
                .padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EjdenIcon(
                name = "search",
                color = SecondaryText,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))

            androidx.compose.foundation.text.BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = MainText,
                    fontSize = 13.sp
                ),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(Primary),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Rechercher un produit...",
                                color = LightText,
                                fontSize = 13.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun EjdenLogo(
    modifier: Modifier = Modifier,
    color: Color = Primary
) {
    Canvas(modifier = modifier) {
        val scale = size.width / 120f

        drawCircle(
            color = color.copy(alpha = 0.10f),
            radius = 48f * scale,
            center = Offset(size.width / 2, size.height / 2),
            style = Stroke(width = 1.2f * scale)
        )

        fun line(points: List<Offset>, strokeWidth: Float = 7f) {
            val path = Path().apply {
                moveTo(points.first().x * scale, points.first().y * scale)
                points.drop(1).forEach { point ->
                    lineTo(point.x * scale, point.y * scale)
                }
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = strokeWidth * scale,
                    cap = StrokeCap.Round
                )
            )
        }

        line(listOf(Offset(34f, 38f), Offset(50f, 28f), Offset(67f, 28f), Offset(84f, 38f)))
        line(listOf(Offset(34f, 38f), Offset(34f, 63f), Offset(40f, 75f), Offset(50f, 79f)))
        line(listOf(Offset(50f, 79f), Offset(67f, 92f), Offset(80f, 95f), Offset(84f, 90f)))
        line(listOf(Offset(84f, 38f), Offset(84f, 65f), Offset(78f, 78f), Offset(68f, 82f), Offset(50f, 82f)))

        drawLine(
            color = color,
            start = Offset(49f * scale, 58f * scale),
            end = Offset(70f * scale, 58f * scale),
            strokeWidth = 5f * scale,
            cap = StrokeCap.Round
        )
        drawCircle(
            color = color,
            radius = 4f * scale,
            center = Offset(60f * scale, 58f * scale)
        )
    }
}

@Composable
private fun DashboardHeader(
    wide: Boolean,
    onPeriod: () -> Unit,
    onNewSale: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                "TABLEAU DE BORD",
                color = Primary,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.3.sp
            )
            Text(
                "Vue générale",
                color = MainText,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Suivez votre activité et prenez les bonnes décisions.",
                color = SecondaryText,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }

        if (wide) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PeriodButton(onPeriod)
                NewSaleButton(onNewSale)
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PeriodButton(onPeriod, Modifier.weight(1f))
                NewSaleButton(onNewSale, Modifier.weight(1.3f))
            }
        }
    }
}

@Composable
private fun PeriodButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = SecondaryText),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Text("Aujourd'hui  ▾", fontSize = 11.sp, maxLines = 1)
    }
}

@Composable
private fun NewSaleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(9.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Text("+ Nouvelle vente", fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
private fun KpiSection(wide: Boolean) {
    val cards = listOf(
        KpiData("Chiffre d'affaires", "—", "FCFA", "Aujourd'hui"),
        KpiData("Ventes", "0", "transaction", "Aujourd'hui"),
        KpiData("Marge estimée", "—", "FCFA", "Aujourd'hui"),
        KpiData("Trésorerie", "—", "FCFA", "Actuelle")
    )

    if (wide) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            cards.forEach { item ->
                KpiCard(item, Modifier.weight(1f))
            }
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard(cards[0], Modifier.weight(1f))
                KpiCard(cards[1], Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard(cards[2], Modifier.weight(1f))
                KpiCard(cards[3], Modifier.weight(1f))
            }
        }
    }
}

private data class KpiData(
    val title: String,
    val value: String,
    val unit: String,
    val period: String
)

@Composable
private fun KpiCard(
    data: KpiData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 126.dp)
            .background(SurfaceColor, RoundedCornerShape(13.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(13.dp))
            .padding(horizontal = 13.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            data.title,
            color = MainText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            data.value,
            color = MainText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                data.unit,
                color = SecondaryText,
                fontSize = 12.sp,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Text(
                data.period,
                color = SecondaryText,
                fontSize = 11.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun Panel(
    eyebrow: String,
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    onAction: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceColor, RoundedCornerShape(17.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(17.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    eyebrow,
                    color = Primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    title,
                    color = MainText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (action != null && onAction != null) {
                TextButton(onClick = onAction, contentPadding = PaddingValues(0.dp)) {
                    Text(action, color = Primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        content()
    }
}

@Composable
private fun SalesPanel(modifier: Modifier = Modifier) {
    var chartPeriod by remember { mutableStateOf("7 derniers jours") }

    Panel(
        eyebrow = "ACTIVITÉ COMMERCIALE",
        title = "Évolution des ventes",
        modifier = modifier
    ) {
        Text("Chiffre d'affaires", color = SecondaryText, fontSize = 12.sp)
        Text("0 FCFA", color = MainText, fontSize = 24.sp, fontWeight = FontWeight.Normal)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Background, RoundedCornerShape(10.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BorderColor)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    "Pas encore de données",
                    color = MainText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Votre graphique apparaîtra après vos premières ventes.",
                    color = SecondaryText,
                    fontSize = 10.sp,
                    lineHeight = 15.sp
                )
            }
            Text(
                "Touchez une barre pour voir le détail.",
                color = SecondaryText,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("7 derniers jours", "30 derniers jours", "12 derniers mois").forEach { period ->
                val selected = chartPeriod == period
                Text(
                    text = when (period) {
                        "7 derniers jours" -> "7 jours"
                        "30 derniers jours" -> "30 jours"
                        else -> "12 mois"
                    },
                    color = if (selected) Primary else SecondaryText,
                    fontSize = 10.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (selected) PrimarySoft else Background,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { chartPeriod = period }
                        .padding(vertical = 9.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun CashPanel(modifier: Modifier = Modifier) {
    Panel(
        eyebrow = "TRÉSORERIE",
        title = "Caisse",
        modifier = modifier,
        action = "Voir",
        onAction = { }
    ) {
        Text("Solde actuel", color = SecondaryText, fontSize = 10.sp)
        Text(
            "—",
            color = MainText,
            fontSize = 31.sp,
            fontWeight = FontWeight.Bold
        )
        Text("FCFA", color = SecondaryText, fontSize = 10.sp)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderColor)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Entrées", color = SecondaryText, fontSize = 10.sp)
                Text("—", color = MainText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Sorties", color = SecondaryText, fontSize = 10.sp)
                Text("—", color = MainText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun RecentSalesPanel() {
    Panel(
        eyebrow = "COMMERCE",
        title = "Dernières ventes",
        action = "Toutes les ventes",
        onAction = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background, RoundedCornerShape(10.dp))
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "Aucune vente",
                color = MainText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Vos ventes récentes apparaîtront ici.",
                color = SecondaryText,
                fontSize = 10.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Produit", "Client", "Quantité", "Montant", "Statut").forEach { label ->
                Text(
                    label,
                    color = SecondaryText,
                    fontSize = 9.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun StockPanel() {
    Panel(
        eyebrow = "INVENTAIRE",
        title = "Stock",
        action = "Gérer",
        onAction = { }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StockMetric("Produits", "0", Primary, Modifier.weight(1f))
            StockMetric("Stock faible", "0", Warning, Modifier.weight(1f))
            StockMetric("Ruptures", "0", Error, Modifier.weight(1f))
        }
        Text(
            "Aucun produit à surveiller.",
            color = SecondaryText,
            fontSize = 11.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Background, RoundedCornerShape(9.dp))
                .padding(14.dp)
        )
    }
}

@Composable
private fun StockMetric(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier

            .background(Background, RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(label, color = SecondaryText, fontSize = 10.sp, maxLines = 2)
        Text(value, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MiniPanel(
    eyebrow: String,
    title: String,
    value: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceColor, RoundedCornerShape(13.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(13.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            eyebrow,
            color = Primary,
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.1.sp
        )
        Text(title, color = MainText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(value, color = MainText, fontSize = 24.sp, fontWeight = FontWeight.Normal)
        Text(description, color = SecondaryText, fontSize = 12.sp)
    }
}

@Composable
private fun ActivityPanel() {
    Panel(eyebrow = "JOURNAL", title = "Activité récente") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background, RoundedCornerShape(10.dp))
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "Aucune activité enregistrée",
                color = MainText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Les ventes, mouvements de caisse, nouveaux clients et mouvements de stock apparaîtront ici.",
                color = SecondaryText,
                fontSize = 10.sp,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun QuickAction(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(PrimarySoft, RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("+", color = Primary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = MainText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(description, color = SecondaryText, fontSize = 10.sp)
        }
    }
}

@Composable
private fun BottomNavigation(onSelect: (String) -> Unit) {
    val items = listOf("Accueil", "Ventes", "＋", "Produits", "Plus")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(SurfaceColor, RoundedCornerShape(22.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(22.dp))
            .padding(horizontal = 5.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isAdd = item == "＋"
            val isHome = item == "Accueil"

            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 52.dp)
                    .clickable { onSelect(item) }
                    .padding(vertical = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isAdd) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Primary, RoundedCornerShape(17.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        EjdenIcon(
                            name = "add",
                            color = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.height(25.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        EjdenIcon(
                            name = when (item) {
                                "Accueil" -> "home"
                                "Ventes" -> "sales"
                                "Produits" -> "products"
                                else -> "more"
                            },
                            color = if (isHome) Primary else SecondaryText,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item,
                        color = if (isHome) Primary else SecondaryText,
                        fontSize = 10.sp,
                        fontWeight = if (isHome) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Medium
                        },
                        maxLines = 1
                    )

                    if (isHome) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(4.dp)
                                .background(Primary, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EjdenIcon(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val scale = size.minDimension / 24f
        val stroke = Stroke(
            width = 1.8.dp.toPx(),
            cap = StrokeCap.Round,
            join = androidx.compose.ui.graphics.StrokeJoin.Round
        )

        fun drawStroke(block: Path.() -> Unit) {
            drawPath(
                path = Path().apply(block),
                color = color,
                style = stroke
            )
        }

        when (name) {
            "home" -> {
                drawStroke {
                    moveTo(3f * scale, 10f * scale)
                    lineTo(12f * scale, 3.5f * scale)
                    lineTo(21f * scale, 10f * scale)
                    moveTo(5.5f * scale, 9f * scale)
                    lineTo(5.5f * scale, 20.5f * scale)
                    lineTo(10f * scale, 20.5f * scale)
                    lineTo(10f * scale, 15f * scale)
                    lineTo(14f * scale, 15f * scale)
                    lineTo(14f * scale, 20.5f * scale)
                    lineTo(18.5f * scale, 20.5f * scale)
                    lineTo(18.5f * scale, 9f * scale)
                }
            }

            "sales" -> {
                drawStroke {
                    moveTo(6f * scale, 3.5f * scale)
                    lineTo(18f * scale, 3.5f * scale)
                    lineTo(18f * scale, 20.5f * scale)
                    lineTo(15f * scale, 18.5f * scale)
                    lineTo(12f * scale, 20.5f * scale)
                    lineTo(9f * scale, 18.5f * scale)
                    lineTo(6f * scale, 20.5f * scale)
                    close()
                    moveTo(9f * scale, 8f * scale)
                    lineTo(15f * scale, 8f * scale)
                    moveTo(9f * scale, 12f * scale)
                    lineTo(15f * scale, 12f * scale)
                    moveTo(9f * scale, 16f * scale)
                    lineTo(12f * scale, 16f * scale)
                }
            }

            "products" -> {
                drawStroke {
                    moveTo(12f * scale, 3f * scale)
                    lineTo(21f * scale, 7.5f * scale)
                    lineTo(12f * scale, 12f * scale)
                    lineTo(3f * scale, 7.5f * scale)
                    close()
                    moveTo(3f * scale, 7.5f * scale)
                    lineTo(3f * scale, 16.5f * scale)
                    lineTo(12f * scale, 21f * scale)
                    lineTo(21f * scale, 16.5f * scale)
                    lineTo(21f * scale, 7.5f * scale)
                    moveTo(12f * scale, 12f * scale)
                    lineTo(12f * scale, 21f * scale)
                }
            }

            "search" -> {
                drawStroke {
                    moveTo(14.8f * scale, 14.8f * scale)
                    cubicTo(
                        13.5f * scale, 16.1f * scale,
                        11.8f * scale, 16.8f * scale,
                        10f * scale, 16.8f * scale
                    )
                    cubicTo(
                        6.2f * scale, 16.8f * scale,
                        3.2f * scale, 13.8f * scale,
                        3.2f * scale, 10f * scale
                    )
                    cubicTo(
                        3.2f * scale, 6.2f * scale,
                        6.2f * scale, 3.2f * scale,
                        10f * scale, 3.2f * scale
                    )
                    cubicTo(
                        13.8f * scale, 3.2f * scale,
                        16.8f * scale, 6.2f * scale,
                        16.8f * scale, 10f * scale
                    )
                    lineTo(21f * scale, 21f * scale)
                }
            }

            "add" -> {
                drawStroke {
                    moveTo(12f * scale, 5f * scale)
                    lineTo(12f * scale, 19f * scale)
                    moveTo(5f * scale, 12f * scale)
                    lineTo(19f * scale, 12f * scale)
                }
            }

            "more" -> {
                listOf(5f, 12f, 19f).forEach { x ->
                    drawCircle(
                        color = color,
                        radius = 1.6f * scale,
                        center = Offset(x * scale, 12f * scale)
                    )
                }
            }
        }
    }
}
