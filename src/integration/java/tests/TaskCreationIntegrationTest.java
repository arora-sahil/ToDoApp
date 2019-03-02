package tests;

import com.todo.ToDoAppProjectApplication;
import com.todo.dto.ToDo;
import config.IntegrationTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes ={ToDoAppProjectApplication.class,IntegrationTestConfig.class})
@ActiveProfiles({"default", "integration"})
public class TaskCreationIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void createTask()
    {
        String body ="{\"taskName\":\"withdraw cash from atm\",\"taskId\":\"102\",\"taskPriority\":\"high\",\"taskStatus\":\"Scheduled\"}";
        HttpEntity httpEntity = new HttpEntity<String>(body,getHeaders());
        ResponseEntity<ToDo> response = restTemplate.exchange(
                createURLWithPort("/tasks"), HttpMethod.POST, httpEntity, ToDo.class);
        ToDo toDo =response.getBody();
        Assert.assertEquals("withdraw cash from atm", toDo.getTaskName());
        Assert.assertEquals("high",toDo.getTaskPriority());
        Assert.assertEquals(102,toDo.getTaskId());
        Assert.assertEquals("Scheduled",toDo.getTaskStatus());
    }

    public HttpHeaders getHeaders() {

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON}));
        return headers;
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
