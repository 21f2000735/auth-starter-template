# architecture.md

# auth-starter-template Architecture

Current example app: **OpenClaw Gmail**

This file captures the architecture view of the project so a developer can quickly understand:
- high-level design (HLD)
- low-level design (LLD)
- features implemented
- technologies used
- what is already done
- what can be extended next

---

# 1) High-Level Design (HLD)

## Goal
Build a startup-ready authentication starter template that can be reused for future frontend + backend projects.

The current example implementation demonstrates:
- Google login
- backend-owned session auth
- protected APIs
- authenticated dashboard shell
- profile panel
- lightweight async background-job pattern

## Main Building Blocks

### Frontend
- Vue 3 app
- Bootstrap UI
- renders guest and authenticated experiences
- consumes backend auth/session/profile/dashboard/job APIs

### Backend
- Spring Boot app
- Spring Security + OAuth2 client
- handles Google OAuth login flow
- maintains session
- exposes protected starter APIs

### Infrastructure
- local shell scripts for start/stop
- Docker Compose kept for optional/future infrastructure
- Postgres + Keycloak present as future-ready path, not phase-1 runtime requirement

## Runtime Flow
1. User opens frontend (`localhost:5173`)
2. Frontend shows guest view
3. User clicks Google login
4. Backend starts OAuth flow with Google
5. On successful login, backend creates authenticated session and redirects back to frontend
6. Frontend calls backend endpoints:
   - `/api/auth/status`
   - `/api/me`
   - `/api/dashboard/summary`
7. User can trigger a protected demo background job:
   - `POST /api/jobs/gmail-sync`
   - then poll `GET /api/jobs/{jobId}`

---

# 2) Low-Level Design (LLD)

## Frontend Structure

### Main file
- `frontend/src/App.vue`

### Current responsibilities
- load auth/session status
- load profile data after authentication
- load dashboard summary
- trigger demo background job
- poll background-job status
- render guest view
- render authenticated dashboard/profile view

### Current support files
- `frontend/src/main.js`
- `frontend/src/style.css`

### Frontend current design state
The frontend is currently a strong core starter, but still centralized in `App.vue`.

Future recommended split:
- router
- layouts
- pages
- services/composables
- auth guards

## Backend Structure

### Core application
- `backend/src/main/java/com/coderhigher/auth/CoderHigherAuthApplication.java`

### Controllers
- `HealthController`
- `AuthController`
- `ProfileController`
- `DashboardController`
- `JobsController`

### Services
- `DashboardService`
- `JobService`

### Config
- `SecurityConfig`
- `LoginSuccessHandler`
- `FrontendProperties`

### DTOs / response models
- `DashboardSummaryResponse`
- `JobStatusResponse`

### Error handling
- `ApiExceptionHandler`

## Security Design
- Spring Security protects all endpoints except health
- Google OAuth login via Spring OAuth2 client
- backend session is used for authenticated access
- API auth failures for `/api/**` return JSON 401 responses
- frontend origin is configurable via `FRONTEND_BASE_URL`

## Background Job Design
- uses Spring `@Async`
- in-memory job map for safe local demo behavior
- protected endpoint starts demo Gmail sync job
- frontend polls job status

This is intentionally lightweight so it can serve as a starter pattern before adding queues or messaging systems.

---

# 3) Features Implemented

## Auth Features
- Google login
- logout
- backend session check
- profile endpoint
- auth status endpoint
- login success redirect back to frontend

## Frontend Features
- guest view
- authenticated dashboard view
- profile panel
- session refresh
- backend health link
- protected background-job trigger
- job progress polling
- loading/error states

## Backend Features
- public health endpoint
- protected profile API
- protected dashboard API
- protected async demo job API
- service layer for dashboard logic
- global exception handling foundation
- JSON 401 handling for SPA/API consumers

## Workflow Features
- `start-dev.sh`
- `stop.sh`
- `.env.example`
- structured documentation via `README.md`, `making.md`, `architecture.md`

---

# 4) Technology Used

## Frontend
- Vue 3
- Vite
- Bootstrap
- Fetch API

## Backend
- Java 21
- Spring Boot
- Spring Security
- Spring OAuth2 Client
- Spring `@Async`
- Maven

## Auth
- Google OAuth
- session/cookie-based auth
- backend-heavy auth model

## Infrastructure / Local Tooling
- Docker Compose
- Postgres (prepared)
- Keycloak (prepared for later phase)
- shell scripting (`.sh`)

---

# 5) What Is Done

## Core v1 completed
- local repo created and pushed
- generic starter naming established (`auth-starter-template`)
- current example app established (`OpenClaw Gmail`)
- Google auth flow working to authenticated backend session
- frontend and backend both boot successfully
- guest/authenticated shell implemented
- protected profile and dashboard APIs implemented
- sample protected async job pattern implemented
- start/stop scripts added
- quickstart docs added
- structured `making.md` maintained

---

# 6) What Can Be Done More

## Frontend improvements
- add Vue Router
- add auth guards
- extract services/composables from `App.vue`
- add layout split (`GuestLayout`, `AppLayout`)
- add Dashboard / Profile / Settings routes
- improve reusable design tokens/theme system

## Backend improvements
- standardize all API responses further
- improve 403 / validation error handling
- re-enable stronger CSRF/session policy when moving toward production
- add service-layer separation for more modules
- add local user/profile persistence model
- add audit/logging hooks

## Template improvements
- document optional feature toggles more explicitly
- separate current example app from reusable template boundaries more cleanly
- add module examples for future adopters

## Future optional integrations
- Keycloak mode
- GitHub auth provider
- email/password auth provider
- GraphQL example
- gRPC example
- WebSocket example
- MongoDB example path
- MySQL alternative path
- messaging / SQS / queue-backed background jobs

---

# 7) Current Recommendation

Use the current repo as:
- a working auth starter
- a reference architecture for FE + BE
- a base project for future startup/product work

Then evolve it in phases instead of trying to add every optional feature immediately.

Recommended next phase:
1. frontend route/layout split
2. backend response/error standardization
3. better feature-toggle documentation
4. then optional advanced modules
