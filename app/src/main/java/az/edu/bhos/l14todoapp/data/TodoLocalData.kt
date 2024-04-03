package az.edu.bhos.l14todoapp.data

import az.edu.bhos.l14todoapp.data.dto.TodoLocalDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface TodoLocalData {
    fun observeItems(): Flow<List<TodoLocalDto>>
    suspend fun save(data: List<TodoLocalDto>)
}

class TodoLocalDataImpl(
    private val todoDao: TodoDao
) : TodoLocalData {

    override fun observeItems(): Flow<List<TodoLocalDto>> {
        val items = todoDao.getAll()

        return items.map {
            list -> list.sortedBy {
                when(it.weekday) {
                    "Monday" -> 1
                    "Tuesday" -> 2
                    "Wednesday" -> 3
                    "Thursday" -> 4
                    "Friday" -> 5
                    "Saturday" -> 6
                    "Sunday" -> 7
                    else -> 0
                }
            }
        }

    }

    override suspend fun save(data: List<TodoLocalDto>) {
        todoDao.insertAll(*data.toTypedArray())
    }

}