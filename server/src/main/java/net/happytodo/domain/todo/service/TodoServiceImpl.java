package net.happytodo.domain.todo.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.domain.todo.repository.TodoRepository;
import net.happytodo.domain.todo.dto.Todo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    @Override
    public List<Todo.Domain> getTodoDomainList(Todo.Condition condition) {
        return todoRepository.findAllDomain(condition);
    }

    @Override
    @Transactional
    public Todo.Domain addTodoDomain(Todo.Domain domain) {
        if (StringUtils.isEmpty(domain.getTitle())) {
            throw new CustomException(CustomExceptionCode.TODO_CONTENT_NOT_NULL);
        }

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

    @Override
    public List<Todo.Status> getTodoStatusList() {
        return todoRepository.findAllStatus();
    }
}
