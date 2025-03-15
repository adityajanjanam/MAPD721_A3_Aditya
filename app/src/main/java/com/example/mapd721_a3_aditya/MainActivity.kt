package com.example.mapd721_a3_aditya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}

// App Navigation
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "main_menu") {
        composable("main_menu") { MainMenuScreen(navController) }
        composable("screen1") { AnimatedContentScreen(navController) }
        composable("screen2") { ValueBasedAnimationScreen(navController) }
        composable("screen3") { InfiniteTransitionScreen(navController) }
        composable("screen4") { GestureBasedAnimationScreen(navController) }
    }
}

// Common BackButton for all screens
@Composable
fun BackButton(navController: NavHostController) {
    IconButton(
        onClick = { navController.navigateUp() },
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}

// Main Menu Screen with animated buttons
@Composable
fun MainMenuScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition(label = "menu-transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "menu-pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Animation Demos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        val animationScreens = listOf(
            "Animated Content Demo" to "screen1",
            "Value-Based Animation Demo" to "screen2",
            "Infinite Transition Demo" to "screen3",
            "Gesture Animation Demo" to "screen4"
        )

        animationScreens.forEachIndexed { index, (label, route) ->
            val staggeredDelay = index * 100
            var visible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(staggeredDelay.toLong())
                visible = true
            }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { it * 2 },
                    animationSpec = tween(500)
                )
            ) {
                Button(
                    onClick = { navController.navigate(route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .scale(if (index == 0) scale else 1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (index) {
                            0 -> Color(0xFF6200EE)
                            1 -> Color(0xFF03DAC5)
                            2 -> Color(0xFFFF6D00)
                            else -> Color(0xFF018786)
                        }
                    ),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text(
                        label,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier
                .graphicsLayer {
                    rotationX = 10f
                    shadowElevation = 8f
                }
                .border(2.dp, Color.Blue.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Aditya Janjanam",
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    fontSize = 18.sp
                )
                Text(
                    "Student ID: 301357523",
                    color = Color.Blue,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// Screen 1 - Enhanced Animated Content with transitions
@Composable
fun AnimatedContentScreen(navController: NavHostController) {
    var currentState by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            BackButton(navController)
            Text(
                "Animated Content Demo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier.padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = currentState,
                        transitionSpec = {
                            // Different transition effects based on state change direction
                            if (targetState > initialState) {
                                (slideInHorizontally(initialOffsetX = { it }) + fadeIn()).togetherWith(
                                    slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                                )
                            } else {
                                (slideInHorizontally(initialOffsetX = { -it }) + fadeIn()).togetherWith(
                                    slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                                )
                            }.using(SizeTransform(clip = false))
                        }
                    ) { state ->
                        when (state) {
                            0 -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Email,
                                        contentDescription = "Email",
                                        modifier = Modifier.size(60.dp),
                                        tint = Color.Blue
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        "Welcome to Animation Demo!",
                                        fontSize = 22.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            1 -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Star",
                                        modifier = Modifier.size(60.dp),
                                        tint = Color(0xFFFFC107)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        "AnimatedContent transitions between different composables!",
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            2 -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.AccountCircle,
                                        contentDescription = "Account",
                                        modifier = Modifier.size(60.dp),
                                        tint = Color.Green
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        "Different transition effects can be applied based on state changes!",
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { currentState = (currentState - 1).mod(3) }) {
                Text("Previous")
            }
            Button(onClick = { currentState = (currentState + 1) % 3 }) {
                Text("Next")
            }
        }
    }
}

// Screen 2 - Enhanced Value-Based Animation
@Composable
fun ValueBasedAnimationScreen(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            BackButton(navController)
            Text(
                "Value-Based Animation Demo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Complex animated visibility with multiple effects
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { with(density) { 50.dp.roundToPx() } }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            // Content that will be animated
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Animated content within the card
                    val boxColor by animateColorAsState(
                        targetValue = if (isVisible) Color.Blue else Color.Gray,
                        animationSpec = tween(1000),
                        label = "box-color"
                    )

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(boxColor)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod tempor.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Animated progress indicator
                    var progress by remember { mutableFloatStateOf(0f) }

                    LaunchedEffect(isVisible) {
                        if (isVisible) {
                            // Animate progress from 0 to 1 when visible
                            animate(
                                initialValue = 0f,
                                targetValue = 1f,
                                animationSpec = tween(2000)
                            ) { value, _ ->
                                progress = value
                            }
                        } else {
                            progress = 0f
                        }
                    }

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = Color.Blue
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { isVisible = !isVisible },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isVisible) Color.Red else Color.Green
            )
        ) {
            Text(if (isVisible) "Hide Content" else "Show Content")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "This demo combines multiple animations:\n" +
                    "• Slide and fade transitions\n" +
                    "• Color animations\n" +
                    "• Progress animation",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

// Screen 3 - Enhanced Infinite Transition
@Composable
fun InfiniteTransitionScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    // Multiple animated values
    val color by infiniteTransition.animateColor(
        initialValue = Color(0xFF6200EE),
        targetValue = Color(0xFF03DAC5),
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorAnimation"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ),
        label = "rotationAnimation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scaleAnimation"
    )

    val orbit by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        ),
        label = "orbitAnimation"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            BackButton(navController)
            Text(
                "Infinite Transition Demo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background circle
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .border(
                        width = 2.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
            )

            // Central animated element
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
                    .rotate(rotation)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(color, Color.White),
                            center = Offset(40f, 40f),
                            radius = 100f
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
            )

            // Orbiting elements
            for (i in 0 until 3) {
                val angleOffset = 120f * i
                val orbitRadius = 120f
                val x = cos(Math.toRadians((orbit + angleOffset).toDouble())).toFloat() * orbitRadius
                val y = sin(Math.toRadians((orbit + angleOffset).toDouble())).toFloat() * orbitRadius

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                        .size(30.dp)
                        .background(color, CircleShape)
                )
            }

            // Descriptive text
            Text(
                text = "Multiple infinite animations:\n• Color transitions\n• Rotation\n• Scaling\n• Orbital motion",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

// Screen 4 - Enhanced Gesture-Based Animation
@Composable
fun GestureBasedAnimationScreen(navController: NavHostController) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }
    var scale by remember { mutableFloatStateOf(1f) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            BackButton(navController)
            Text(
                "Gesture Animation Demo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Text(
            text = "Drag, pinch or rotate the box below:",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Spring animation for bouncing back to center when released
            val springSpec = spring<Float>(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )

            // Animated values for smooth transitions
            val animatedOffsetX by animateFloatAsState(
                targetValue = offsetX,
                animationSpec = springSpec,
                label = "offsetXAnimation"
            )
            val animatedOffsetY by animateFloatAsState(
                targetValue = offsetY,
                animationSpec = springSpec,
                label = "offsetYAnimation"
            )
            val animatedRotation by animateFloatAsState(
                targetValue = rotationAngle,
                animationSpec = springSpec,
                label = "rotationAnimation"
            )
            val animatedScale by animateFloatAsState(
                targetValue = scale,
                animationSpec = springSpec,
                label = "scaleAnimation"
            )

            // Shadow box to show motion path
            Box(
                Modifier
                    .size(100.dp)
                    .offset { IntOffset(animatedOffsetX.roundToInt(), animatedOffsetY.roundToInt()) }
                    .graphicsLayer(
                        alpha = 0.3f,
                        rotationZ = animatedRotation,
                        scaleX = animatedScale,
                        scaleY = animatedScale
                    )
                    .background(Color.Gray, RoundedCornerShape(8.dp))
            )

            // Main interactive box
            Box(
                Modifier
                    .size(100.dp)
                    .offset { IntOffset(animatedOffsetX.roundToInt(), animatedOffsetY.roundToInt()) }
                    .graphicsLayer(
                        rotationZ = animatedRotation,
                        scaleX = animatedScale,
                        scaleY = animatedScale,
                        shadowElevation = 8f
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5)),
                            start = Offset(0f, 0f),
                            end = Offset(100f, 100f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { },
                            onDragEnd = {
                                // Reset position with spring animation when released
                                offsetX = 0f
                                offsetY = 0f
                            },
                            onDragCancel = {
                                // Reset position with spring animation when cancelled
                                offsetX = 0f
                                offsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                // Update position
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y

                                // Add some rotation based on horizontal movement
                                rotationAngle += dragAmount.x * 0.5f

                                // Add some scale effect based on vertical movement
                                val newScale = max(0.5f, min(1.5f, scale + dragAmount.y * 0.01f))
                                scale = newScale
                            }
                        )
                    }
            ) {
                Text(
                    "DRAG ME",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Instructions
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Drag: Move the box",
                    fontSize = 14.sp
                )
                Text(
                    "Horizontal drag: Changes rotation",
                    fontSize = 14.sp
                )
                Text(
                    "Vertical drag: Changes scale",
                    fontSize = 14.sp
                )
                Text(
                    "Release: Springs back to center",
                    fontSize = 14.sp
                )
            }
        }
    }
}

// Preview for Main Menu
@Preview(showBackground = true)
@Composable
fun PreviewMainMenu() {
    val navController = rememberNavController()
    MainMenuScreen(navController)
}