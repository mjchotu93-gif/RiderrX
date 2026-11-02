package com.riderx.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// RiderX Theme Colors
val DeepBlack = Color(0xFF0B0F0C)
val DarkSurface = Color(0xFF141A16)
val DarkSurfaceVariant = Color(0xFF1D2620)
val NeonGreen = Color(0xFFB7F34A)
val TextWhite = Color(0xFFF5F5F5)
val TextGray = Color(0xFF9AA89E)
val ErrorRed = Color(0xFFFF5252)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RiderXMainApp()
        }
    }
}

@Composable
fun RiderXMainApp() {
    val context = LocalContext.current
    var isCycleMode by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DeepBlack,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    Toast.makeText(context, "Ride Planner: Opening GPS Route...", Toast.LENGTH_SHORT).show() 
                },
                containerColor = NeonGreen,
                contentColor = DeepBlack
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Ride")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "RiderX",
                    color = NeonGreen,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Ride Together. Explore More. Go Beyond.",
                    color = TextGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Mode Selector: Cycle vs Bike
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkSurface, RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isCycleMode) NeonGreen else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { isCycleMode = true }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "🚲 Cycle Mode",
                            color = if (isCycleMode) DeepBlack else TextGray,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (!isCycleMode) NeonGreen else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { isCycleMode = false }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "🏍️ Bike Mode",
                            color = if (!isCycleMode) DeepBlack else TextGray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Telemetry Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Distance", color = TextGray, fontSize = 12.sp)
                            Text("1,248 KM", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text("Completed", color = TextGray, fontSize = 12.sp)
                            Text("28 Rides", color = NeonGreen, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Active / Upcoming Rides List Header
            item {
                Text(
                    text = if (isCycleMode) "Nearby Cycling Trails" else "Upcoming Motorcycle Rides",
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(4) { index ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable {
                            Toast.makeText(context, "Opening Live Tracking...", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isCycleMode) "Morning Hill Climb #$index" else "Highway Express Ride #$index",
                            color = NeonGreen,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Destination: Foothill Viewpoint", color = TextWhite, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${30 + index * 15} KM Distance", color = TextGray, fontSize = 12.sp)
                            Text("Starts: 06:00 AM", color = TextGray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
