package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Task
import viewmodel.BoardViewModel

@Composable
fun BoardScreen(boardViewModel: BoardViewModel) {
    // Replace this with the actual state that holds the list of tasks
    val boardState = boardViewModel.boardState.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Project Team Spirit") }) },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                // Add your columns for To Do, Doing, Done, etc.
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Dynamically create a composable for each list in your board
                    boardState.lists.forEach { list ->
                        TaskList(list)
                    }
                }
            }
        }
    )
}

@Composable
fun TaskList(taskList: TaskList) {
    // Replace this with your actual composable that represents a list of tasks
    Card(
        elevation = 4.dp,
        modifier = Modifier.width(280.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(taskList.title, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(4.dp))
            // Dynamically add tasks here
            taskList.tasks.forEach { task ->
                TaskItem(task)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    // Replace this with your actual composable that represents a single task
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(task.title)
        // Add more task details here
    }
}

// You would need to replace TaskList and Task with the actual data classes
// that represent the state of your board and tasks
