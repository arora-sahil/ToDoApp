package com.todo.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.todo.repository.IToDoRepository;
import com.todo.repository.ToDoEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.todo.dto.ToDo;

@RestController
@RequestMapping("/tasks")
public class ToDoServiceController {

	@Autowired
	IToDoRepository toDoRepository;

	@Autowired
	DozerBeanMapper dozerBeanMapper;

	@GetMapping(value="")
	public ResponseEntity<List<ToDo>> showTasks()
	{
		Iterable<ToDoEntity> allTasks = toDoRepository.findAll();
		List<ToDo> allTasksDto = new ArrayList<ToDo>();
		for (ToDoEntity toDoEntity : allTasks) {
			allTasksDto.add(dozerBeanMapper.map(toDoEntity, ToDo.class));
		}
		return ResponseEntity.ok().body(allTasksDto);
	}
	@PostMapping(value="")
	public ResponseEntity<ToDo> createTask(@Valid @RequestBody ToDo toDo)
	{
		ToDoEntity toDoEntity = dozerBeanMapper.map(toDo, ToDoEntity.class);
		ToDoEntity afterSave = toDoRepository.save(toDoEntity);
		ToDo toDoAfterSave = dozerBeanMapper.map(afterSave, ToDo.class);
		return ResponseEntity.ok().body(toDoAfterSave);
	}
	@GetMapping(value="/{taskId}")
	public ResponseEntity<ToDo> getTask(@PathVariable("taskId")int taskId)
	{
		return toDoRepository.findById(taskId)
				.map(todoentity -> dozerBeanMapper.map(todoentity, ToDo.class))
				.map(todo -> ResponseEntity.ok().body(todo))
				.orElse(ResponseEntity.notFound().build());
	}
	@PutMapping(value="/{taskId}")
	public ResponseEntity<ToDo> updateTask(@PathVariable("taskId")int taskId, @RequestBody ToDo toDo)
	{
		return toDoRepository.findById(taskId)
				.map(todoEntity -> {
					todoEntity.setTaskId(toDo.getTaskId());
					todoEntity.setTaskPriority(toDo.getTaskPriority());
					todoEntity.setTaskName(toDo.getTaskName());
					todoEntity.setTaskStatus(toDo.getTaskStatus());
					ToDo updatedTodo = dozerBeanMapper.map(toDoRepository.save(todoEntity),ToDo.class);
					return ResponseEntity.ok().body(updatedTodo);
				}).orElse(ResponseEntity.notFound().build());
	}
	@DeleteMapping(value = "/{taskId}")
	public ResponseEntity<?> deleteTask(@PathVariable("taskId") int taskId)
	{
		return toDoRepository.findById(taskId)
				.map(
						toDo -> {
							toDoRepository.deleteById(taskId);
							return ResponseEntity.ok().build();
						}).orElse(ResponseEntity.notFound().build());
	}
}
