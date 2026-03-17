#!/usr/bin/env bash
set -euo pipefail

echo "Stopping OpenClaw Gmail local services..."
pkill -f 'vite' 2>/dev/null || true
pkill -f 'spring-boot:run' 2>/dev/null || true
pkill -f 'coder-higher-auth-backend' 2>/dev/null || true

echo "Requested stop for frontend/backend processes."
