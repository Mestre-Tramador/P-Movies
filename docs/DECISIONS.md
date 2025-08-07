# Decisions

Below is a list of decisions made for this project, separated by topics.

## General

- Even if it was possible for multiple repositories to be created, and Git Submodules
be used to manage them, it was opted to include all files in a single repository;
- A NGINX reverse proxy was created to make the application don't dependent of
multiple ports and easily configurable with self-signed SSL certificates and a
local domain name;
- Possibility to use VS Code Code Workspace was added as it is my usual IDE;
- It is already intended for the application to be scalable on a Kubernetes cluster
and be available as Docker images on GitHub registry.

## Backend

- Spring Boot was chosen due to its easiness to setup, using Java as the main language
due to its strict and static type rules, a security aligned option for backend
in general;
- Gradle was chosen instead of Maven out of pure personal preference;
- The `.war` compilation was chosen due to the opening of the possibility to, in
a production build, merge the frontend in the backend.
- The service can run locally if preferred, and it can be developed under a Dev Container,
a flexible environment decision to improve DevEx.

## Frontend

**\*Still not implemented!**

- React with JavaScript was mandatory, so I didn't opt to use TypeScript except
for type definitions along the project;
- Vite as a builder and development server was chosen out of pure personal preference;
- The service can run locally if preferred, and it can be developed under a Dev Container,
a flexible environment decision to improve DevEx.
