package net.happytodo.domain.todo.dao;

import net.happytodo.domain.todo.dto.Todo;

import java.util.List;

public interface TodoDao {
    List<Todo.Domain> findAllDomain();
}
