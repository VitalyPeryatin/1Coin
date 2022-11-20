package com.finance_tracker.finance_tracker.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.toPx
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.pie.BezierLabelConnector
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.pie.StraightLineConnector
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.util.ResponsiveText
import io.github.koalaplot.core.util.toString

private data class OtherOptionsState(
    val showLabels: Boolean = true,
    val holeSize: Float = 0.8f,
    val labelSpacing: Float = 1.1f
)

private data class ConnectorStyleState(
    val strokeStyle: Stroke,
    val straightLine: Boolean
)

@Suppress("MagicNumber")
@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier
) {
    val fibonacci = listOf(1f, 1f, 2f, 3f, 5f, 8f, 13f, 21f)
    val fibonacciSum = fibonacci.sum()
    val colors = listOf(
        Color.Black, Color.Green, Color.Red,
        Color.Blue, Color.Gray, Color.Magenta,
        Color.Yellow, Color.Cyan
    )
    val strokeWidth = 1.dp.toPx()
    val otherOptionsState by remember { mutableStateOf(OtherOptionsState()) }
    val connectorStyle by remember {
        mutableStateOf(
            ConnectorStyleState(
                strokeStyle = Stroke(
                    width = strokeWidth
                ),
                straightLine = false
            )
        )
    }

    ChartLayout(
        modifier = modifier,
        title = {
            Column {
                Text(
                    "Fibonacci Sequence",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    ) {
        PieChart(
            fibonacci,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            slice = { i: Int ->
                DefaultSlice(
                    color = colors[i],
                    hoverExpandFactor = 1.05f,
                    hoverElement = { Text(fibonacci[i].toString()) }
                )
            },
            label = { i ->
                if (otherOptionsState.showLabels) {
                    Text((fibonacci[i] / fibonacciSum).toPercent(1))
                }
            },
            labelConnector = { i ->
                if (otherOptionsState.showLabels) {
                    if (connectorStyle.straightLine) {
                        StraightLineConnector(
                            connectorColor = colors[i],
                            connectorStroke = connectorStyle.strokeStyle
                        )
                    } else {
                        BezierLabelConnector(
                            connectorColor = colors[i],
                            connectorStroke = connectorStyle.strokeStyle
                        )
                    }
                }
            },
            holeSize = otherOptionsState.holeSize,
            holeContent = { HoleTotalLabel(sum = fibonacciSum.toInt()) },
            labelSpacing = if (otherOptionsState.showLabels) otherOptionsState.labelSpacing else 1.0f
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun HoleTotalLabel(
    modifier: Modifier = Modifier,
    sum: Int = 0
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResponsiveText(
            text = "Total",
            modifier = Modifier.weight(0.20f).fillMaxWidth(),
            style = LocalTextStyle.current.copy(
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
        ResponsiveText(
            text = sum.toString(),
            modifier = Modifier.weight(0.80f).fillMaxWidth(),
            style = LocalTextStyle.current.copy(
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}

private fun Float.toPercent(precision: Int): String {
    @Suppress("MagicNumber")
    return "${(this * 100.0f).toString(precision)}%"
}