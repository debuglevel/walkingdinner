import { Component, OnInit } from '@angular/core';

import { Calculation } from '../calculation';
import { CalculationService } from '../calculation.service';
import { Base64Service } from '../base64.service';

@Component({
  selector: 'app-calculations',
  templateUrl: './calculations.component.html',
  styleUrls: ['./calculations.component.css'],
})
export class CalculationsComponent implements OnInit {
  calculations: Calculation[] | undefined;

  fileToUploadFile: File | null = null;
  fileToUploadBase64: String | null = null;
  handleFileInput(event: Event | null) {
    if (event) {
      const eventTarget = event.target;
      if (eventTarget) {
        const htmlInputElement = eventTarget as HTMLInputElement;
        const files = htmlInputElement.files;
        if (files) {
          this.handleFileInput2(files);
        }
      }
    }
  }
  handleFileInput2(files: FileList) {
    this.fileToUploadFile = files.item(0);
    if (this.fileToUploadFile != null) {
      this.base64Service
        .getBase64(this.fileToUploadFile)
        .then((res) => {
          //console.log("Converted file to Base64:", res);
          this.fileToUploadBase64 = res;
        })
        .catch((error) => {
          console.log('ERROR:', error.message);
        });
    }
  }

  constructor(
    private calculationService: CalculationService,
    private base64Service: Base64Service
  ) {}

  ngOnInit() {
    this.getCalculations();
  }

  getCalculations(): void {
    this.calculationService
      .getCalculations()
      .subscribe((calculations) => (this.calculations = calculations));
  }

  addSurveyfile(
    populationSizeString: string,
    fitnessThresholdString: string,
    steadyFitnessString: string
  ): void {
    const populationSize = Number(populationSizeString);
    const fitnessThreshold = Number(fitnessThresholdString);
    const steadyFitness = Number(steadyFitnessString);

    const surveyfile = this.fileToUploadBase64;

    this.calculationService
      .addCalculation({
        finished: false,
        surveyfile,
        populationSize: populationSize,
        fitnessThreshold: fitnessThreshold,
        steadyFitness: steadyFitness,
      } as Calculation)
      .subscribe((calculation) => {
        if (this.calculations) {
          this.calculations.push(calculation);
        }
      });
  }

  delete(calculation: Calculation): void {
    if (this.calculations) {
      this.calculations = this.calculations.filter((h) => h !== calculation);
      this.calculationService.deleteCalculation(calculation).subscribe();
    }
  }
}
