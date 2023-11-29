package model


data class Board(
    val id: String,
    val name: String,
    val lists: List<TaskList>
)

data class TaskList(
    val id: String,
    val title: String,
    val tasks: List<Task>
)

data class Task(
    val id: String,
    val title: String,
    val description: String?,
    val labels: List<String>,
    val dueDate: String?,
    val assignedUsers: List<String> // Consider using a User class instead of just String
)
