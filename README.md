# auth-starter-template

Current example app inside this template: **OpenClaw Gmail**

A startup-ready authentication starter template using:
- Vue 3 frontend
- Bootstrap UI
- Spring Boot backend
- Google OAuth directly in the app for phase 1
- Docker Compose for optional local infrastructure
- Keycloak reserved for a later phase

## What this template already gives you
- login
- logout
- backend session check
- protected profile API
- protected dashboard API
- guest/authenticated app shell
- lightweight protected background-job example
- startup scripts
- structured project memory via `making.md`

## Status
Core starter foundation is working locally.

## Repo structure
- `frontend/` → Vue 3 app shell, auth-state consumption, dashboard/profile UI
- `backend/` → Spring Boot auth/session/profile/dashboard/job APIs
- `infra/` → optional Postgres + Keycloak preparation for later phases
- `making.md` → detailed project contract, status, design direction, and future pickup guide

## Quickstart
### 1. Prepare backend env
Copy:
- `backend/.env.example` → `backend/.env`

Then set:
- `GOOGLE_CLIENT_ID=...`
- `GOOGLE_CLIENT_SECRET=...`
- `FRONTEND_BASE_URL=http://localhost:5173`

### 2. Start the app
From repo root:
```bash
./start-dev.sh
```

### 3. Open locally
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- Health: `http://localhost:8080/api/health`

### 4. Stop locally
```bash
./stop.sh
```

## Google OAuth setup
Create a Google OAuth client and add this exact redirect URI:
- `http://localhost:8080/login/oauth2/code/google`

## Local Infrastructure Plan
Docker Compose currently prepares:
- Postgres
- Keycloak

These remain in the repo because Keycloak is planned for a later phase.
Phase 1 login uses Google OAuth directly with the Spring Boot backend.

## Test-only Default Credentials
This repository is currently for local/testing use only.
Default passwords may be kept here temporarily until the project is hardened.

Planned local defaults:
- Postgres DB: `coderhigher`
- Postgres user: `root`
- Postgres password: `root`
- Keycloak admin user: `root`
- Keycloak admin password: `root`
- Default application test user password: `root`

## Verified Working State
Verified locally:
- frontend starts successfully
- backend starts successfully
- Google OAuth redirect begins successfully
- authenticated backend session was confirmed via `/api/auth/status`
- sample protected dashboard API works
- protected demo background-job flow exists

## Notes
See `making.md` for the full design and ongoing project memory.
