package com.project.githubRepositoryLister.Repository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RepositoryController {
    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/repository/{userLogin}")
    public List<Repository> getRepositories(@PathVariable String userLogin,
                                            @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader) {
        return repositoryService.getRepositories(userLogin);
    }
}
