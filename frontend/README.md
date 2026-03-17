# Frontend Template Notes

This frontend is the Vue 3 + Bootstrap side of the `auth-starter-template` repository.

## Purpose
Provide a reusable authenticated app shell for future projects, with:
- guest view
- authenticated dashboard view
- profile/session fetch
- protected dashboard API consumption
- lightweight background-job trigger example

## Current responsibilities
- call backend auth/session endpoints
- render guest vs authenticated UI
- render starter dashboard cards/feed
- show protected profile data
- trigger/poll demo background jobs

## Current backend dependency
Default backend base is:
- `http://localhost:8080`

## Future improvements
- add Vue Router
- add auth guards
- extract services/composables further
- introduce layout/page module boundaries
