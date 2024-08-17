package com.project.githubRepositoryLister.Repository;

import lombok.Data;

@Data
public class Branch {
    private String branchName;
    private String commitSHA;
}
