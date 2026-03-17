# OpenClaw Gmail

A localhost-only authentication starter project using:
- Vue 3 frontend
- Bootstrap UI
- Spring Boot backend
- Google OAuth directly in the app for phase 1
- Docker Compose for local infrastructure
- Keycloak reserved for a later phase

## Status
Bootstrap in progress.

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

## Startup Approach
- Infrastructure: Docker Compose
- App startup: `start-dev.sh`

## Google OAuth Setup (Phase 1)
Before login can work, create Google OAuth credentials.

### Google Cloud steps
1. Go to Google Cloud Console.
2. Create or choose a project.
3. Configure the OAuth consent screen.
4. Create an OAuth 2.0 Client ID.
5. Choose **Web application**.
6. Add this authorized redirect URI:
   - `http://localhost:8080/login/oauth2/code/google`

### Local backend credentials
1. Copy:
   - `backend/.env.example` → `backend/.env`
2. Put your values in `backend/.env`:
   - `GOOGLE_CLIENT_ID=...`
   - `GOOGLE_CLIENT_SECRET=...`
3. Run:
   - `./start-dev.sh`

### Local login flow
- Frontend runs on `http://localhost:5173`
- Backend runs on `http://localhost:8080`
- Frontend button redirects to Spring Boot Google login
- Spring Boot handles the OAuth callback and session

## Verified Working State
Verified locally:
- frontend starts successfully
- backend starts successfully
- Google OAuth redirect begins successfully
- authenticated backend session was confirmed via `/api/auth/status`

## Notes
See `making.md` for the full decision log, instructions, and ongoing project memory.
