//import { InMemoryDbService } from "angular-in-memory-web-api";
import { Organisation } from "./organisation";
import { Dinner } from "./dinner";
import { Plan } from "./plan";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class InMemoryDataService /*implements InMemoryDbService*/ {
  createDb() {
    const organisations = [
      { id: "a9d21e9f-fc3f-4207-a553-49c57e1c9850", name: "Umbrella Corp." },
      { id: "93895da4-54ac-4bb7-9c7e-d56cadd37b6e", name: "Tyrell Corp." },
      { id: "a336ec43-2f48-41a8-a336-8405fb9b17bf", name: "Stark Industries" },
      { id: "cf3ac4e4-a8a2-4c94-9a17-07bf7f7b2633", name: "Gringotts" },
      { id: "7e56f904-0e2b-4fe3-938a-e068828e0e87", name: "MomCorp" },
      { id: "0f8a3173-dd2f-4565-97fc-64727e6dccfa", name: "Duff Beer" },
      { id: "00061eb7-a033-4728-81aa-943787d1fc62", name: "Octan" },
    ];
    const dinners = [
      {
        id: "8e127b87-3ddc-4797-bebc-c95372bbfc44",
        name: "1st Zombie dinner at Umbrella Corp.",
      },
      {
        id: "e9b75ad9-ff6f-4711-80eb-bedb7ae7818e",
        name: "2nd Zombie dinner at Umbrella Corp.",
      },
      {
        id: "f345e3dd-c5f7-43ab-a137-417a277488ac",
        name: "Tyrell's Replicant Dinner",
      },
      {
        id: "8398dfe1-b818-4bc9-83ed-3744b9d87b29",
        name: "Eating old fish (goblins invited only)",
      },
      {
        id: "ac5aeb62-605e-4526-8867-683877a4c79e",
        name: "Tony destroys your meal",
      },
      {
        id: "dff57bbf-9a15-44ab-a248-89351631e92a",
        name: "J.A.R.V.I.S. cooks better than you",
      },
      { id: "318896a6-efad-4c89-81bc-d172a0b0c532", name: "Beer-gathering" },
    ];
    const teams = [
      { id: "2da97dce-8354-4041-b2d4-cb599ba60d87", name: "Luna and Neville" },
      {
        id: "ed694d32-79be-4b67-9b69-1f29f0c72aad",
        name: "Kreacher and Dobby",
      },
      { id: "64d77396-e74b-4991-8c9d-38a627491762", name: "Harry and Ron" },
      {
        id: "df70e21f-4701-4fb0-a8e6-3b32d21b5aff",
        name: "Hermoine and Ginny",
      },
      { id: "43ef372b-1a86-486b-ae11-f66f539e6a5f", name: "Dean and Seamus" },
      { id: "e80e8f5a-e141-4c93-ba3d-9b6e7be6818c", name: "Patil twins" },
      {
        id: "714ef130-e9e0-4423-a996-234bafdc1f79",
        name: "Tom Riddle and some other parts of his soul",
      },
      { id: "9213062a-7d6b-4e39-af83-fcba813499aa", name: "James and Lilly" },
      {
        id: "211353a4-dfbf-4491-bfe8-a8a0ce8c8391",
        name: "Aberforth Dumbledore and his goat",
      },
    ];
    const plans = [
      { id: "90e950ca-1233-450c-af3d-4e85e4437970", name: "Plan 1" },
      { id: "ddf44ced-ac49-40d8-a9b1-a93b44e256ad", name: "Plan 2" },
      { id: "fbd6268c-7cf8-4250-b669-172304363a3c", name: "Plan 3" },
      { id: "b7bc550c-c1ea-41df-a715-92864bc1c841", name: "Plan 4" },
    ];
    const calculations = [
      {
        id: "f9549236-db01-41b9-96e4-ba4ff081d93f",
        finished: true,
        planId: "90e950ca-1233-450c-af3d-4e85e4437970",
        surveyfile: "dGVzdA==",
      },
      {
        id: "9a3348a3-e8ed-495c-9e94-0664cbd9df95",
        finished: true,
        planId: "ddf44ced-ac49-40d8-a9b1-a93b44e256ad",
        surveyfile: "dGVzdA==",
      },
      {
        id: "6bc193d3-22d5-4768-ae21-779193a5d18d",
        finished: true,
        planId: "fbd6268c-7cf8-4250-b669-172304363a3c",
        surveyfile: "dGVzdA==",
      },
      {
        id: "663cbc6d-b04d-4b4d-9aca-76708a226527",
        finished: true,
        planId: "b7bc550c-c1ea-41df-a715-92864bc1c841",
        surveyfile: "dGVzdA==",
      },
      {
        id: "ac5d67a2-8429-4e1a-a7fe-2d15f96f4237",
        finished: false,
        surveyfile: "dGVzdA==",
      },
    ];
    return { organisations, dinners, teams, plans, calculations };
  }

  // // Overrides the genId method to ensure that a organisation always has an id.
  // // If the organisations array is empty,
  // // the method below returns the initial number (11).
  // // if the organisations array is not empty, the method below returns the highest
  // // organisation id + 1.
  // genId(organisations: Organisation[]): number {
  //   return organisations.length > 0
  //     ? Math.max(...organisations.map(organisation => organisation.id)) + 1
  //     : 11;
  // }
  genId(plans: Plan[]): string {
    return Guid.newGuid();
  }
}

// see: https://stackoverflow.com/a/26502275/4764279
class Guid {
  static newGuid() {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(
      /[xy]/g,
      function (c) {
        var r = (Math.random() * 16) | 0,
          v = c == "x" ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      }
    );
  }
}
