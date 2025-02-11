package net.happytodo.domain.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.core.security.dto.User;
import net.happytodo.core.security.service.SecurityService;
import net.happytodo.domain.todo.dto.Todo;
import net.happytodo.domain.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequestMapping("/api/todo")
@RestController
@RequiredArgsConstructor
@Tag(name = "todo", description="Todo 컨트롤러")
public class TodoController {
    private final TodoService todoService;
    private final SecurityService securityService;

    @GetMapping("/domain")
    @Operation(summary = "Todo 도메인 조건 별 전체 리스트 목록 조회")
    public ResponseEntity<List<Todo.DomainResponse>> getTodoDomainList(@RequestParam(name = "status", required = false) Integer status) {
        User.UserAccount loginUser = securityService.getLoginUser().orElseThrow(() -> new CustomException(CustomExceptionCode.USER_UNAUTHORIZED));
        List<Todo.Domain> domainList = todoService.getTodoDomainList(Todo.Condition.builder()
                .userId(loginUser.getId())
                .status(status)
                .build());

        return ResponseEntity.ok(domainList.stream()
                .map(Todo.Domain::toResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping("/domain")
    @Operation(summary = "Todo 도메인 추가")
    public ResponseEntity<Todo.DomainResponse> addTodoDomain(@RequestBody Todo.DomainRequest request) {
        User.UserAccount loginUser = securityService.getLoginUser().orElseThrow(() -> new CustomException(CustomExceptionCode.USER_UNAUTHORIZED));
        request.setUserId(loginUser.getId());

        Todo.DomainResponse response = todoService.addTodoDomain(request.toDomain()).toResponse();
        return ResponseEntity.status(CREATED).body(response);
    }

    @PutMapping("/domain/{id}")
    @Operation(summary = "Todo 도메인 수정")
    public ResponseEntity<Todo.DomainResponse> updateTodoDomain(@RequestBody Todo.DomainRequest request,
                                                                @PathVariable int id) {
        Todo.Domain domain = request.toDomain();
        domain.setId(id);

        Todo.DomainResponse response = todoService.updateTodoDomain(domain).toResponse();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/domain/{id}")
    @Operation(summary =  "Todo 도메인 삭제")
    public ResponseEntity<Void> deleteTodoDomain(@PathVariable int id) {
        todoService.deleteTodoDomain(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/status")
    @Operation(summary = "Todo 상태 목록 조회")
    public ResponseEntity<List<Todo.Status>> getTodoStatusList() {
        return ResponseEntity.ok(todoService.getTodoStatusList());
    }

    @GetMapping("/{id}/files")
    @Operation(summary = "Todo 파일 조회")
    public ResponseEntity<List<Todo.File>> getTodoFiles(@PathVariable int id) {
        return ResponseEntity.ok(todoService.getTodoFiles(id));
    }

    @PostMapping("/files")
    @Operation(summary = "Todo 파일 저장")
    public ResponseEntity<Void> addTodoFiles(@RequestBody List<Todo.File> todoFiles) {
        todoService.addTodoFiles(todoFiles);
        return ResponseEntity.ok().build();
    }
}
