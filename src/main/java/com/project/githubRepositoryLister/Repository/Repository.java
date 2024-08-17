package com.project.githubRepositoryLister.Repository;

import lombok.Data;

@Data
public class Repository {
    private String repositoryName;
    private String userLogin;
    private Branch[] branches;
}
