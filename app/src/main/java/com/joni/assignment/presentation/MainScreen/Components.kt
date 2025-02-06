package com.joni.assignment.presentation.MainScreen

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joni.assignment.presentation.navigation.Screen
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SwipingTopAppBar(texts: List<String>) {
    var currentIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            // Animated text switching
            AnimatedContent(
                targetState = texts[currentIndex],
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                },
                label = "Swipe"
            ) { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        },

        modifier = Modifier.fillMaxWidth(),

    )
    LaunchedEffect(currentIndex) {
        delay(3000)
        currentIndex = (currentIndex + 1) % texts.size
    }
}

@Composable
fun FloatButton(modifier: Modifier = Modifier, navController: NavController) {
    FloatingActionButton(onClick = { navController.navigate("add_product") }, shape = CircleShape) {
    Icon(Icons.Default.Add, contentDescription = "Add")
    }
}