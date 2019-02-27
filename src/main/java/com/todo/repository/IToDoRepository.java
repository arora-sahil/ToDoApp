package com.todo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToDoRepository extends CrudRepository<ToDoEntity, Integer> {

}
