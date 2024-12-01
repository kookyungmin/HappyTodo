package net.happytodo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.domain.todo.repository.TodoRepository;
import net.happytodo.domain.todo.dto.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    @Override
    public List<Todo.Domain> getTodoDomainList() {
        return todoRepository.findAllDomain();
    }

    @Override
    @Transactional
    public Todo.Domain addTodoDomain(Todo.Domain domain) {
        todoRepository.persistDomain(domain);
        return todoRepository.findDomainById(domain.getId());
    }

    @Override
    @Transactional
    public Todo.Domain updateTodoDomain(Todo.Domain domain) {
        todoRepository.updateDomain(domain);
        return todoRepository.findDomainById(domain.getId());
    }

    @Override
    public void deleteTodoDomain(int id) {
        todoRepository.deleteDomain(id);
    }
}
