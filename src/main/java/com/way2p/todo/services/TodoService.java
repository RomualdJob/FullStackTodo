package com.way2p.todo.services;
import com.way2p.todo.entity.Todo;
import com.way2p.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TodoService {



    @Autowired
    private TodoRepository todoRepository;

    public Page<Todo> getTodosPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }
}
