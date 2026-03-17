#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
BACKEND_ENV="$ROOT_DIR/backend/.env"

if [ -f "$BACKEND_ENV" ]; then
  set -a
  # shellcheck disable=SC1090
  source "$BACKEND_ENV"
  set +a
fi

echo "Starting coder-higher-auth-starter local app services..."
echo "Tip: start infrastructure separately with: cd infra && docker compose up -d"

(
  cd "$ROOT_DIR/frontend"
  npm run dev
) &
FRONTEND_PID=$!

(
  cd "$ROOT_DIR/backend"
  mvn spring-boot:run
) &
BACKEND_PID=$!

trap 'kill $FRONTEND_PID $BACKEND_PID 2>/dev/null || true' EXIT
wait
