vidhi 22csu237 - projectservice,castingservice
cheshta 22csu491 - budgetservice
muskan 22csu245- schedulingservice
thService (Port: 9005)
Functionality: Handles user registration and login.

Endpoints:

POST /auth/signup

POST /auth/login

Example Call (Direct):

POST http://localhost:9005/auth/login

Example Call (via Gateway):

POST http://localhost:8080/api/auth/login

ProjectService (Port: 4002)
Functionality: Manages project details, including creating, reading, and finding projects.

Endpoints:

POST /projects/project

GET /projects/allprojects

GET /projects/project/{project_id}

GET /projects/project/projectTitle/{project_id}

GET /projects/project/projectid/{project_title}

GET /projects/project?project_title={project_title}

Example Call (Direct):

POST http://localhost:4002/projects/project

Example Call (via Gateway):

POST http://localhost:8080/api/projects/project

Required Header: X-API-GATEWAY-SECRET: <your-secret-value>

CastingService (Port: 4003)
Functionality: Manages casting, actors, and roles for projects.

Endpoints:

POST /casting

POST /casting/addWithProject

GET /casting/all

GET /casting/{castingId}

GET /casting/actor/{actorName}

GET /casting/count

Example Call (Direct):

GET http://localhost:4003/casting/all

Example Call (via Gateway):

GET http://localhost:8080/api/casting/all

Required Header: X-API-GATEWAY-SECRET: <your-secret-value>

BudgetService (Port: 4004)
Functionality: Manages budgets for projects.

Endpoints:

POST /budget/save

PUT /budget/update

GET /budget/{id}

GET /budget/all

DELETE /budget/{id}

Example Call (Direct):

POST http://localhost:4004/budget/save

Example Call (via Gateway):

POST http://localhost:8080/api/budget/save

Required Header: X-API-GATEWAY-SECRET: <your-secret-value>
