package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

enum class LemonadeState {
    SELECT, SQUEEZE, DRINK, RESTART
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)
) {
    var state by remember { mutableStateOf(LemonadeState.SELECT) }
    var squeezesRequired by remember { mutableStateOf((2..4).random()) }
    var squeezes by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Yellow
                ),
            )
        }

    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (state) {
                    LemonadeState.SELECT -> {
                        LemonadeImage(
                            imageRes = R.drawable.lemon_tree,
                            contentDescription = R.string.image_lemon_tree.toString(),
                            onClick = { state = LemonadeState.SQUEEZE },
                            text = stringResource(R.string.tap_tree)
                        )
                    }

                    LemonadeState.SQUEEZE -> {
                        LemonadeImage(
                            imageRes = R.drawable.lemon_squeeze,
                            contentDescription = R.string.image_lemon.toString(),
                            onClick = {
                                squeezes++
                                if (squeezes >= squeezesRequired) {
                                    state = LemonadeState.DRINK
                                }
                            },
                            text = stringResource(R.string.tap_lemon)
                        )
                    }

                    LemonadeState.DRINK -> {
                        LemonadeImage(
                            imageRes = R.drawable.lemon_drink,
                            contentDescription = R.string.image_lemonade.toString(),
                            onClick = { state = LemonadeState.RESTART },
                            text = stringResource(R.string.tap_lemonade)
                        )
                    }

                    LemonadeState.RESTART -> {
                        LemonadeImage(
                            imageRes = R.drawable.lemon_restart,
                            contentDescription = R.string.image_glass.toString(),
                            onClick = {
                                state = LemonadeState.SELECT
                                squeezes = 0
                                squeezesRequired = (2..4).random()
                            },
                            text = stringResource(R.string.tap_glass)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LemonadeImage(
    imageRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
            )
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = text,
            modifier = modifier,
            fontSize = 16.sp
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}