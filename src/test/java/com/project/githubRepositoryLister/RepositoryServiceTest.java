package com.project.githubRepositoryLister;

import com.project.githubRepositoryLister.Repository.RepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {

    @InjectMocks
    private RepositoryService repositoryService;

    @Test
    void testGetRepositories_UserNotFound() throws Exception {
        String userLogin = "nonexistentUser";
        String url = "https://api.github.com/users/" + userLogin + "/repos";

        RepositoryService serviceMock = spy(repositoryService);
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))
                .when(serviceMock)
                .getBufferedReader(url);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> serviceMock.getRepositories(userLogin));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    void testGetRepositories_ApiError() throws Exception {
        String userLogin = "validUser";
        String url = "https://api.github.com/users/" + userLogin + "/repos";

        RepositoryService serviceMock = spy(repositoryService);
        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error connecting to GitHub API"))
                .when(serviceMock)
                .getBufferedReader(url);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> serviceMock.getRepositories(userLogin));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Error connecting to GitHub API", exception.getReason());
    }
}
