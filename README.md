
# The RepositoryLister App

The project is a web application designed to list repositories of a specific user from GitHub.


![Logo](https://github.com/user-attachments/assets/61f5b407-bf12-4362-9286-60314186be74)


## Tech Stack

**Client:** Angular 17, NPM

**Server:** SpringBoot 5, Java 21, Maven, GithubAPI

## Tests

The RepositoryApplicationTests file is a test class for a Spring Boot application. It contains unit tests to verify the app. Service and controller  layers are being tested, as well as the app context.

![image](https://github.com/user-attachments/assets/8e069afb-cfc4-41ac-9366-d284f2104e45)

All the tests are being passed

![image](https://github.com/user-attachments/assets/4d9837d4-3fdd-4bdf-82d2-ad3038115079)

## Usage/Examples

This project is developed like a full stack app, although its endpoint can be used on its own. More on that in the API Reference section.
It performs well on both larger and smaller screens. Here is the comparison of a smaller desktop monitor, to a Iphone 14 Pro Max you can select in Google Chrome DevTools.

1366x768:

![image](https://github.com/user-attachments/assets/aaf40a99-9991-4d4c-9df0-7c8593518ab4)

430x932:

![image](https://github.com/user-attachments/assets/20a9e13f-8a14-419e-a5f0-013749268671)

Error message:

![image](https://github.com/user-attachments/assets/e15c4c24-be4e-4763-8081-6c5927bd3d47)

If the user has no public repositories it doesn't display anything.

The app delivers a responsive design with user-friendly animations and overall good readability and usability



## API Reference

#### Get user

```http
  GET /api/repository/githubUsername
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `githubUsername` | `string` | **Required**. User you're looking for |

It returns all user's public repositories along with their branches that are not forks. It looks like this:

```json
[
  {
          "repositoryName": "pep.net_Bachelors-Thesis-Web-App-Project",
          "userLogin": "VeniQs02",
          "branches": [
              {
                  "branchName": "master",
                  "commitSHA": "19aa98e803dd7e422fced25d47c2c91cc628cf7c"
              }
          ]
      }
... (and more)
]
```

If the GitHub API reaches its limits, or the user was not found, or something else went wrong it will return an object in this format:


```json
{
    “status”: "responseCode"
    “message”: "errorMessage"
}
```

If the user has no public repositories it returns []. 

## Acknowledgements

 - [Used icons and fonts](https://fonts.google.com/)
