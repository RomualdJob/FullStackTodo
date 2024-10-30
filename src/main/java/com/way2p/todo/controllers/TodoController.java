package com.way2p.todo.controllers;
import com.way2p.todo.entity.Todo;
import com.way2p.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {



    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public Page<Todo> getTodos(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return todoService.getTodosPaginated(page, size);
    }
}
