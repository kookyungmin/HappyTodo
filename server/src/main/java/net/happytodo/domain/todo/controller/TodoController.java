package net.happytodo.domain.todo.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import net.happytodo.domain.todo.dto.Todo;
import net.happytodo.domain.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/todo")
@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/domain")
    public ResponseEntity<List<Todo.Domain>> getTodoDomainList() {
        List<Todo.Domain> domainList = todoService.getTodoDomainList();
        return ResponseEntity.ok(domainList);
    }
}
