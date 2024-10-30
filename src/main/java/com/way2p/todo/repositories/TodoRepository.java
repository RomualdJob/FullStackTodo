package com.way2p.todo.repositories;

import com.way2p.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface  TodoRepository extends JpaRepository<Todo, Integer> {
    Page<Todo> findAll(Pageable pageable);

}
