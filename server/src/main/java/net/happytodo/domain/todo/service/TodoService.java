package net.happytodo.domain.todo.service;

import net.happytodo.domain.todo.dto.Todo;

import java.util.List;

public interface TodoService {
    List<Todo.Domain> getTodoDomainList(Todo.Condition condition);

    Todo.Domain addTodoDomain(Todo.Domain domain);

    Todo.Domain updateTodoDomain(Todo.Domain domain);

    void deleteTodoDomain(int id);

    List<Todo.Status> getTodoStatusList();
}
