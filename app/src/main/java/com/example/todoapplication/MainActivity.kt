package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.theme.ToDoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoApp()
                }
            }
        }
    }
}

@Composable
fun ToDoApp() {

    var newTaskText by remember { mutableStateOf(TextFieldValue("")) }
    var taskList by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input Field
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BasicTextField(
                value = newTaskText,
                onValueChange = { newTaskText = it },
                modifier = Modifier

                    .weight(2f)
                    .height(60.dp)
                    .padding(end = 8.dp)
                    .border(width = 5.dp, shape = RoundedCornerShape(4.dp), color = Color.Gray)
            )
            Button(onClick = {
                if (newTaskText.text.isNotBlank()) {
                    taskList = taskList + newTaskText.text
                    newTaskText = TextFieldValue("") // Reset input
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        Column {
            taskList.forEachIndexed { index, task ->
                TaskItem(task = task, onDelete = {
                    taskList = taskList.filterIndexed { i, _ -> i != index }
                })
            }
        }
    }
}
@Composable
fun TaskItem(task: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = task, modifier = Modifier.weight(1f))
        Button(onClick = { onDelete() }) {
            Text("Delete")
        }
    }
}