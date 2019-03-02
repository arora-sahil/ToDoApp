package com.todo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import com.todo.dto.ToDo;
import com.todo.repository.IToDoRepository;
import com.todo.repository.ToDoEntity;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoServiceControllerTest {

	@MockBean
	IToDoRepository iToDoRepository;

	@Autowired
	ToDoServiceController toDoServiceController;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		//Mock for find task
		ToDoEntity toDoEntity = new ToDoEntity();
		toDoEntity.setTaskId(1);
		toDoEntity.setTaskName("Withdraw Cash");
		toDoEntity.setTaskPriority("High");
		toDoEntity.setTaskStatus("Not Completed");
		ToDo toDo = new ToDo();
		when(iToDoRepository.findById(1)).thenReturn(Optional.of(toDoEntity));

		//Mock for create task
		when(iToDoRepository.save(any(ToDoEntity.class))).thenAnswer(new Answer<ToDoEntity>() {
			@Override
			public ToDoEntity answer(InvocationOnMock invocation) throws Throwable {
				return toDoEntity;
			}
		});

		//Mock for show all tasks
		ToDoEntity toDoEntity1 = new ToDoEntity();
		toDoEntity1.setTaskId(1);
		toDoEntity1.setTaskName("Open Bank Account");
		toDoEntity1.setTaskPriority("Low");
		toDoEntity1.setTaskStatus("Not Completed");
		List<ToDoEntity> list = new ArrayList<ToDoEntity>();
		list.add(toDoEntity);
		list.add(toDoEntity1);
		when(iToDoRepository.findAll()).thenReturn(list);

	}
	@Test
	public void retrieveAllTasks() throws Exception{

		MvcResult mvcResult = this.mockMvc.perform(get("/tasks")).andExpect(status().isOk()).andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		JSONArray jsonArray = new JSONArray(actual);
		Assert.assertTrue(jsonArray.length()==2);
	}
	@Test
	public void retrieveTaskByTaskId() throws Exception{
		MvcResult mvcResult = this.mockMvc.perform(get("/tasks/1")).andExpect(status().isOk()).andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(Integer.toUnsignedLong(JsonPath.read(actual,"$.taskId")),1);
		Assert.assertEquals(JsonPath.read(actual,"$.taskName"),"Withdraw Cash");
		Assert.assertEquals(JsonPath.read(actual,"$.taskPriority"),"High");
		Assert.assertEquals(JsonPath.read(actual,"$.taskStatus"),"Not Completed");
	}
	@Test
	public void deleteTask() throws Exception{
		MvcResult mvcResult = this.mockMvc.perform(delete("/tasks/1")).andExpect(status().isOk()).andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(actual.isEmpty());
	}

	@Test
	public void createTask() throws Exception {
		ToDo toDo = new ToDo();
		toDo.setTaskId(1);
		toDo.setTaskName("Withdraw Cash");
		toDo.setTaskPriority("High");
		toDo.setTaskStatus("Not Completed");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(toDo);

		MvcResult mvcResult = this.mockMvc.perform(post("/tasks").accept(APPLICATION_JSON).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk()).andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(Integer.toUnsignedLong(JsonPath.read(actual,"$.taskId")),1);
		Assert.assertEquals(JsonPath.read(actual,"$.taskName"),"Withdraw Cash");
		Assert.assertEquals(JsonPath.read(actual,"$.taskPriority"),"High");
		Assert.assertEquals(JsonPath.read(actual,"$.taskStatus"),"Not Completed");
	}

	@Test
	public void updateTask() throws Exception {
		ToDo toDo = new ToDo();
		toDo.setTaskId(1);
		toDo.setTaskName("Opem Bank Account");
		toDo.setTaskPriority("High");
		toDo.setTaskStatus("Completed");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(toDo);

		MvcResult mvcResult = this.mockMvc.perform(put("/tasks/1").accept(APPLICATION_JSON).contentType(APPLICATION_JSON_UTF8).content(requestJson)).andExpect(status().isOk()).andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(Integer.toUnsignedLong(JsonPath.read(actual,"$.taskId")),1);
		Assert.assertEquals(JsonPath.read(actual,"$.taskName"),"Opem Bank Account");
		Assert.assertEquals(JsonPath.read(actual,"$.taskPriority"),"High");
		Assert.assertEquals(JsonPath.read(actual,"$.taskStatus"),"Completed");
	}

}