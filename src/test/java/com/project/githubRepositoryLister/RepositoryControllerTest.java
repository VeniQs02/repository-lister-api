package com.project.githubRepositoryLister;

import com.project.githubRepositoryLister.Repository.Repository;
import com.project.githubRepositoryLister.Repository.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryService repositoryService;

    @Test
    void testGetRepositories_UserNotFound() throws Exception {
        String userLogin = "nonexistentUser";
        when(repositoryService.getRepositories(userLogin)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(get("/repository/{userLogin}", userLogin))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void testGetRepositories_Success() throws Exception {
        String userLogin = "validUser";

        List<Repository> repositoryList = new ArrayList<>();
        Repository repository = new Repository();
        repository.setRepositoryName("repo1");
        repository.setUserLogin(userLogin);
        repositoryList.add(repository);

        when(repositoryService.getRepositories(userLogin)).thenReturn(repositoryList);

        mockMvc.perform(get("/repository/{userLogin}", userLogin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").value("repo1"))
                .andExpect(jsonPath("$[0].userLogin").value(userLogin));
    }
}
