package com.project.githubRepositoryLister.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Repository> getRepositories(String userLogin) {
        String userUrl = GITHUB_API_URL + userLogin + "/repos";
        try {
            //connecting to API
            BufferedReader in = getBufferedReader(userUrl);
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //parsing repositories
            JsonNode reposArray = objectMapper.readTree(response.toString());
            List<Repository> repositories = new ArrayList<>();

            for (JsonNode repoNode : reposArray) {
                if (repoNode.get("fork").asBoolean()) {
                    continue; //skip forked repositories
                }

                Repository repository = new Repository();
                repository.setRepositoryName(repoNode.get("name").asText());
                repository.setUserLogin(userLogin);

                //fetching branches
                String branchesUrl = repoNode.get("branches_url").asText().replace("{/branch}", "");
                List<Branch> branchList = new ArrayList<>();

                URL branchesURL = new URI(branchesUrl).toURL();
                HttpURLConnection branchesConnection = (HttpURLConnection) branchesURL.openConnection();
                branchesConnection.setRequestMethod("GET");
                branchesConnection.setRequestProperty("Accept", "application/json");

                //reading and parsing branches
                BufferedReader branchesIn = new BufferedReader(new InputStreamReader(branchesConnection.getInputStream()));
                StringBuilder branchesResponse = new StringBuilder();

                while ((inputLine = branchesIn.readLine()) != null) {
                    branchesResponse.append(inputLine);
                }
                branchesIn.close();

                JsonNode branchesArray = objectMapper.readTree(branchesResponse.toString());

                for (JsonNode branchNode : branchesArray) {
                    Branch branch = new Branch();
                    branch.setBranchName(branchNode.get("name").asText());
                    branch.setCommitSHA(branchNode.get("commit").get("sha").asText());
                    branchList.add(branch);
                }

                repository.setBranches(branchList.toArray(new Branch[0]));
                repositories.add(repository);
            }

            return repositories;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error connecting to GitHub API");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedReader getBufferedReader(String userUrl) throws URISyntaxException, IOException {
        URL url = new URI(userUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();

        //checking if the user exists, and if the connection was established
        if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error connecting to GitHub API");
        }

        //reading and parsing the response
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
}
