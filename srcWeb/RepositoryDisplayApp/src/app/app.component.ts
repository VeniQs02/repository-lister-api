import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgForOf, NgIf, NgOptimizedImage } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RepositoryService } from "../services/repository.service";
import { FooterComponent } from "../components/footer/footer.component";

interface Branch {
  branchName: string;
  commitSHA: string;
}

interface Repository {
  repositoryName: string;
  userLogin: string;
  branches: Branch[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgOptimizedImage, FormsModule, NgForOf, NgIf, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  protected username: string = "";
  protected repositories: Repository[] = [];
  protected isLoading: boolean = false;
  protected isDataFetched: boolean = false;
  protected errorMessage: string = "";

  constructor(private repositoryService: RepositoryService) {}

  buttonClick() {
    this.isLoading = true;
    this.errorMessage = "";

    this.repositoryService.getRepositories(this.username).subscribe(
      (data) => {
        this.repositories = data;
        this.finishLoadingData()
      },
      (error) => {
        this.errorMessage = 'Failed to load repositories. Please try again later. Error: ' + error.status;
        this.finishLoadingData()
      }
    );
  }
  finishLoadingData(){
    this.isLoading = false;
    this.isDataFetched = true;
  }
}

