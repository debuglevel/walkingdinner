# Walking Dinner

Walking Dinner is a web app to organize what in Germany is known as "Running Dinner" (a registered trademark in Germany)
, "Rudi", "Run and Dine" or as "progressive dinner", "safari supper" in english speaking countries.

## Deployment

The tool is not too complicated to deploy, but also not trivial. It is composed of multiple services, which have to be
deployed - usage of `docker-compose.yml` is recommended therefore.

### Services

* `backend` is the main API backend. It stores data et cetera.
* `angular` is the web fronted which talks to the `backend` service.
* `planner` is a worker microservice to calculate routes and stuff. It's separated from `backend` because it might
  produce heavy loads and maybe should be scaled to multiple machines.