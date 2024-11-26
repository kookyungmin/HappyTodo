package net.happytodo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.domain.todo.dao.TodoDao;
import net.happytodo.domain.todo.dto.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoDao todoDao;
    @Override
    public List<Todo.Domain> getTodoDomainList() {
        return todoDao.findAllDomain();
    }
}
