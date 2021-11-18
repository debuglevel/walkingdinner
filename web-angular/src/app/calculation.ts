export interface Calculation {
  id: string;
  finished: boolean;
  surveyfile: string;
  populationSize: number;
  fitnessThreshold: number;
  steadyFitness: number;
  planId: string;
  begin: string;
  end: string;
  dinnerId: string;
}
