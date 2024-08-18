import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgForOf, NgOptimizedImage } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RepositoryService } from "../services/repository.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgOptimizedImage, FormsModule, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  protected username: string = "";
  protected repositories: any[] = [];

  constructor(private repositoryService: RepositoryService) {}

  buttonClick() {
    document.getElementById("logo")!.hidden;

    this.repositoryService.getRepositories(this.username).subscribe(
      (data) => {
        this.repositories = data;
      },
      (error) => {
        console.error('Failed to load repositories:', error);
      }
    );
  }
}
