# making.md

## Purpose
This file is the portable build manual, feature map, and decision log for `openclaw-gmail`.

It must remain useful even if a different developer opens the repository later. A new developer should be able to understand the project, pick a feature area quickly, continue work safely, and know what external setup is still required.

---

# 1) Project Overview

## Current Goal
Build a startup-ready authentication starter template whose current example app is **OpenClaw Gmail**, with:
- Vue 3 frontend
- Bootstrap UI
- Spring Boot backend
- Google OAuth directly in the app for phase 1
- backend-heavy auth flow
- Docker-first local infrastructure
- Postgres + Keycloak Docker setup kept in repo for later phases
- `.sh` as the primary startup script
- Keycloak integration deferred to a later phase
- this repo should be treated as a reusable login template for future frontend + backend projects, so both sides can take help from it later

## Current Repo Location
`/Users/indiadelhi/repo/apps/openclaw-gmail`

## GitHub Repo
`https://github.com/21f2000735/auth-starter-template`

## Push Policy
- GitHub repo now exists under the generic template name `auth-starter-template`
- local example app folder is currently still named `openclaw-gmail`

---

# 2) Locked Decisions

## Stack
- Frontend: Vue 3 + Vite + Bootstrap
- Backend: Spring Boot
- Auth phase 1: Google OAuth directly in the app
- Auth style: backend-heavy auth flow
- Keycloak: later phase, not phase 1
- Local infra: Docker Compose with Postgres + Keycloak prepared

## Ports
- Frontend: `5173`
- Backend: `8080`
- Keycloak: `8081`
- Postgres: `5432`

## Local Testing Credentials
For local/testing use only right now:
- Postgres user: `root`
- Postgres password: `root`
- Keycloak admin user: `root`
- Keycloak admin password: `root`
- Default app test-user password convention: `root`

## Startup Mode
- Infrastructure starts with Docker Compose
- App services start with `start-dev.sh`
- `.sh` is primary because the current machine is macOS

---

# 3) Feature Map

This section is meant for fast pickup. Each feature area should always show status, implementation location, and next steps.

## Feature A — Repo/Foundation
**Status:** DONE

### Includes
- local repo created
- `README.md`
- `making.md`
- `.gitignore`
- `start-dev.sh`
- `stop.sh`

### Files
- `README.md`
- `making.md`
- `architecture.md`
- `start-dev.sh`
- `stop.sh`
- `.gitignore`

### Next
- keep updating docs when architecture or setup changes
- keep `architecture.md` aligned with the implemented HLD/LLD and feature set

---

## Feature B — Frontend Shell
**Status:** IN PROGRESS

### Includes
- Vue 3 scaffold
- Bootstrap added
- login/dashboard/profile shell created
- Google login CTA button wired to backend authorization endpoint
- frontend auth status fetch
- frontend profile fetch from `/api/me`
- sample dashboard UI wired to protected sample API
- lightweight background-job UI example for authenticated users
- guest/authenticated app shell split for startup-template flow

### Files
- `frontend/src/App.vue`
- `frontend/src/main.js`
- `frontend/src/style.css`

### Current Behavior
- user sees a clean landing page
- Google button points to backend auth entrypoint
- frontend reads backend auth state from `/api/auth/status`
- frontend fetches profile/session info from `/api/me` when authenticated
- frontend dashboard consumes `/api/dashboard/summary`
- frontend can trigger and poll a protected demo job via `/api/jobs/gmail-sync`

### Next
- add real route structure if the UI grows beyond the current guest/auth split
- refine logged-in dashboard behavior after auth is connected
- optionally introduce a router-based dashboard/profile separation later

---

## Feature C — Backend Bootstrap
**Status:** IN PROGRESS

### Includes
- Spring Boot project scaffolded
- health endpoint
- protected profile endpoint
- auth status endpoint
- sample dashboard endpoint
- dashboard service layer
- protected background-job endpoints
- in-memory job service for demo async flow
- global exception handler
- initial security config
- Google OAuth placeholders in config

### Files
- `backend/pom.xml`
- `backend/src/main/java/com/coderhigher/auth/CoderHigherAuthApplication.java`
- `backend/src/main/java/com/coderhigher/auth/api/HealthController.java`
- `backend/src/main/java/com/coderhigher/auth/api/ProfileController.java`
- `backend/src/main/java/com/coderhigher/auth/config/SecurityConfig.java`
- `backend/src/main/resources/application.yml`

### Current Behavior
- `/api/health` is public
- `/api/auth/status` exposes frontend-readable session state
- `/api/me` returns authenticated profile details
- `/api/dashboard/summary` provides a sample protected startup dashboard API
- `/api/jobs/gmail-sync` and `/api/jobs/{id}` provide a protected async-job example
- successful Google login now redirects back to the configured frontend base URL
- unauthenticated `/api/**` requests return JSON 401 responses for SPA use
- other endpoints are treated as authenticated
- Spring Security is prepared for Google OAuth login flow

### Next
- refine CORS/session behavior for localhost
- evolve into clearer controller/service layering examples over time
- standardize JSON auth/error behavior for SPA consumers

---

## Feature D — Google OAuth Integration
**Status:** PENDING EXTERNAL SETUP

### Goal
Use Google OAuth directly in the app for phase 1, with Spring Boot handling the auth flow.

### What is ready
- backend config placeholders exist
- frontend button already points to backend auth endpoint

### What is missing
- Google Cloud project
- OAuth consent screen
- OAuth client ID
- OAuth client secret
- redirect URI registration: `http://localhost:8080/login/oauth2/code/google`

### External Help Required
Yes — user will need to set up Google OAuth in Google Cloud Console later.

### Next
- tell user the exact Google setup steps
- place credentials in local config/env
- test login flow end to end

---

## Feature E — Docker Infrastructure
**Status:** PREPARED / LATER-PHASE SUPPORT

### Includes
- Docker Compose for Postgres + Keycloak
- Keycloak realm bootstrap file retained in repo

### Files
- `infra/docker-compose.yml`
- `infra/keycloak/coder-higher-realm.json`

### Current Note
Infra is present because Keycloak is still part of the longer-term architecture, but Keycloak is not part of phase 1 login.

### Next
- optionally verify Docker infra boots cleanly
- keep ready for future Keycloak migration

---

## Feature F — Keycloak Later Phase
**Status:** DEFERRED

### Goal
Move auth handling behind Keycloak in a later phase.

### Why deferred
Google OAuth direct in the app is the faster, cleaner phase-1 path.

### Next
- only revisit after phase-1 Google auth works fully

---

# 4) Current Status Snapshot

## Latest Implemented Slice
- Vue 3 frontend scaffolded with Bootstrap
- Spring Boot backend scaffolded
- frontend upgraded from starter scaffold to login/dashboard/profile shell
- Google OAuth button wired to backend authorization endpoint
- frontend reads `/api/auth/status`
- frontend fetches `/api/me` profile data when authenticated
- sample dashboard API added at `/api/dashboard/summary`
- frontend dashboard wired to the sample API
- protected async job example added at `/api/jobs/gmail-sync`
- frontend can trigger/poll the demo job flow
- global API exception handler added
- exact Google OAuth redirect/config path documented
- successful Google login verified at backend session level
- login success handler added to return users to the frontend after auth
- `stop.sh` added for local service shutdown

## Validation Already Done
- frontend build passes
- backend compile passes

---

# 5) Instructions and Feedback Log

## Confirmed Instructions
1. Create the repo locally only.
2. Do not push to GitHub yet.
3. Place the repo under `/Users/indiadelhi/repo/apps`.
4. Use Vue.js as the frontend.
5. Use Spring Boot as the backend.
6. Phase 1 auth should be Google OAuth directly in the app.
7. Use a backend-heavy auth flow.
8. Keycloak should be handled later, not in phase 1 user login flow.
9. Use Docker to start local infrastructure.
10. Docker setup should include DB + Keycloak linked together.
11. Default test passwords may be written in `README.md` for now because this is only a testing application.
12. Use `root` as the default local test password.
13. Use `.sh` as the primary startup script.
14. `making.md` must work for another developer too.
15. `making.md` should be structured so feature areas are easy to pick up.
16. Use Vue 3 explicitly.
17. Use Bootstrap.

## Feedback Notes
- The project should feel reusable and documented, not like a throwaway prototype.
- `making.md` should help future developers pick a feature area quickly.
- Keep progress practical and visible.

---

# 6) Decision Log

## 2026-03-17
- Initial backend idea discussed as Python.
- Backend direction changed to Spring Boot.
- `.sh` selected as primary startup script because the current machine is macOS.
- Project should remain local-only for now and should not be pushed to GitHub yet.
- `making.md` must be portable and useful for any future developer.
- Vue 3 + Bootstrap locked for frontend.
- Phase 1 auth changed from Keycloak-first to direct Google OAuth in the app.
- Backend-heavy auth flow locked for phase 1.
- Keycloak moved to a later phase.
- Default local testing password standardized as `root`.
- `making.md` upgraded to a feature-oriented structure for easier pickup.
- Project display name updated to OpenClaw Gmail.
- Google OAuth flow verified far enough to confirm authenticated backend session.
- Added a login success redirect back to the frontend.
- Added `stop.sh` for local shutdown workflow.

---

# 7) Open Questions / External Dependencies

## Still needs user help later
1. exact redirect URI registration in Google Cloud
2. confirm Google OAuth client is configured correctly for localhost

## Still needs implementation
1. stronger frontend route/module split if the UI grows further
2. optional env-loading/documentation refinement
3. standard JSON 401/403 behavior for API consumers
4. later Keycloak migration path

---

# 8) Immediate Next Steps

1. Improve error-state presentation and standard response patterns further
2. Replace remaining hardcoded frontend/backend assumptions with config-driven values
3. Keep phase-2 Keycloak path documented and ready
4. Add optional module/toggle guidance for future features

---

# 9) Rule for Future Updates

Whenever this project changes, update `making.md` in a reusable way:
- what changed
- why it changed
- which feature it belongs to
- whether it is done / in progress / pending / blocked
- what a future developer should do next
- enough current-state detail that the project can be designed, understood, and continued just by reading this file
- when broader personal/project context matters, rely on MEMORY/context separately for that understanding rather than bloating this file with unrelated history
