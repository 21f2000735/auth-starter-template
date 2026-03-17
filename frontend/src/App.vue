<script setup>
import { computed, onMounted, ref } from 'vue'

const backendBase = 'http://localhost:8080'
const loading = ref(true)
const profileLoading = ref(false)
const dashboardLoading = ref(false)
const authError = ref('')
const profileError = ref('')
const dashboardError = ref('')
const authState = ref({
  authenticated: false,
  username: null,
  loginUrl: '/oauth2/authorization/google',
  logoutUrl: '/logout',
})
const profile = ref({
  name: null,
  email: null,
  picture: null,
  username: null,
})
const dashboard = ref({
  appName: 'OpenClaw Gmail',
  environment: 'localhost',
  authenticated: false,
  username: 'guest',
  cards: [],
  recentActions: [],
})

const quickChecks = computed(() => [
  { label: 'Frontend', value: 'Vue 3 + Bootstrap', status: 'Ready' },
  { label: 'Backend', value: 'Spring Boot', status: 'Ready' },
  { label: 'Auth flow', value: 'Google OAuth (phase 1)', status: authState.value.authenticated ? 'Connected' : 'Pending config' },
])

const profileDisplayName = computed(() => profile.value.name || authState.value.username || 'Google account')
const sessionSummary = computed(() => authState.value.authenticated
  ? `Signed in as ${profile.value.name || authState.value.username}`
  : 'Not signed in yet. Google OAuth credentials still need to be configured.')

async function loadAuthStatus() {
  loading.value = true
  authError.value = ''

  try {
    const response = await fetch(`${backendBase}/api/auth/status`, {
      credentials: 'include',
    })

    if (!response.ok) throw new Error(`Auth status returned ${response.status}`)

    authState.value = await response.json()

    await Promise.all([
      loadDashboard(),
      authState.value.authenticated ? loadProfile() : Promise.resolve(),
    ])

    if (!authState.value.authenticated) {
      profile.value = { name: null, email: null, picture: null, username: null }
    }
  } catch (error) {
    authError.value = error instanceof Error ? error.message : 'Unable to read auth state'
  } finally {
    loading.value = false
  }
}

async function loadProfile() {
  profileLoading.value = true
  profileError.value = ''

  try {
    const response = await fetch(`${backendBase}/api/me`, {
      credentials: 'include',
    })

    if (!response.ok) throw new Error(`Profile returned ${response.status}`)

    profile.value = await response.json()
  } catch (error) {
    profileError.value = error instanceof Error ? error.message : 'Unable to load profile'
  } finally {
    profileLoading.value = false
  }
}

async function loadDashboard() {
  dashboardLoading.value = true
  dashboardError.value = ''

  try {
    const response = await fetch(`${backendBase}/api/dashboard/summary`, {
      credentials: 'include',
    })

    if (!response.ok) throw new Error(`Dashboard returned ${response.status}`)

    dashboard.value = await response.json()
  } catch (error) {
    dashboardError.value = error instanceof Error ? error.message : 'Unable to load dashboard'
  } finally {
    dashboardLoading.value = false
  }
}

function startGoogleLogin() {
  window.location.href = `${backendBase}${authState.value.loginUrl || '/oauth2/authorization/google'}`
}

async function logout() {
  try {
    await fetch(`${backendBase}${authState.value.logoutUrl || '/logout'}`, {
      method: 'POST',
      credentials: 'include',
    })
    profile.value = { name: null, email: null, picture: null, username: null }
    await loadAuthStatus()
  } catch (error) {
    authError.value = error instanceof Error ? error.message : 'Logout failed'
  }
}

onMounted(loadAuthStatus)
</script>

<template>
  <div class="app-shell bg-light min-vh-100">
    <nav class="navbar navbar-expand-lg bg-white border-bottom sticky-top">
      <div class="container d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center gap-2">
          <a class="navbar-brand fw-semibold mb-0" href="#">OpenClaw Gmail</a>
          <span class="badge text-bg-dark">auth starter</span>
        </div>

        <div class="d-flex align-items-center gap-2">
          <span v-if="loading" class="text-secondary small">Checking session...</span>
          <template v-else-if="authState.authenticated">
            <span class="text-secondary small">{{ profileDisplayName }}</span>
            <button class="btn btn-sm btn-outline-primary" @click="loadAuthStatus">Refresh</button>
            <button class="btn btn-sm btn-outline-danger" @click="logout">Logout</button>
          </template>
          <button v-else class="btn btn-sm btn-dark" @click="startGoogleLogin">Login</button>
        </div>
      </div>
    </nav>

    <main class="container py-5">
      <section v-if="!authState.authenticated" class="row g-4 align-items-stretch">
        <div class="col-lg-7">
          <section class="card border-0 shadow-sm h-100">
            <div class="card-body p-4 p-md-5">
              <p class="text-uppercase text-secondary small fw-semibold mb-2">Guest view</p>
              <h1 class="display-6 fw-bold mb-3">OpenClaw Gmail — startup-ready auth starter</h1>
              <p class="lead text-secondary mb-4">
                Auth is solved first so future products can focus on content, features, and business logic instead of rebuilding login every time.
              </p>

              <div class="d-flex flex-wrap gap-3 mb-4">
                <button class="btn btn-dark btn-lg px-4" @click="startGoogleLogin">
                  Continue with Google
                </button>
                <a class="btn btn-outline-secondary btn-lg px-4" :href="`${backendBase}/api/health`" target="_blank">
                  Backend health
                </a>
                <button class="btn btn-outline-primary btn-lg px-4" @click="loadAuthStatus">
                  Refresh auth state
                </button>
              </div>

              <div class="alert alert-light border mb-0">
                <strong>Current state:</strong>
                {{ sessionSummary }}
              </div>
            </div>
          </section>
        </div>

        <div class="col-lg-5">
          <section class="card border-0 shadow-sm h-100">
            <div class="card-body p-4">
              <h2 class="h5 mb-3">Quick readiness checks</h2>
              <div class="vstack gap-3">
                <div v-for="item in quickChecks" :key="item.label" class="rounded-3 border p-3 bg-white-subtle">
                  <div class="d-flex justify-content-between align-items-start gap-3">
                    <div>
                      <div class="fw-semibold">{{ item.label }}</div>
                      <div class="text-secondary small">{{ item.value }}</div>
                    </div>
                    <span class="badge" :class="item.status === 'Ready' || item.status === 'Connected' ? 'text-bg-success' : 'text-bg-warning'">
                      {{ item.status }}
                    </span>
                  </div>
                </div>
              </div>

              <div v-if="authError" class="alert alert-warning mt-3 mb-0">{{ authError }}</div>
            </div>
          </section>
        </div>
      </section>

      <section v-else class="row g-4">
        <div class="col-lg-8">
          <section class="card border-0 shadow-sm">
            <div class="card-body p-4">
              <p class="text-uppercase text-secondary small fw-semibold mb-1">Authenticated dashboard</p>
              <div class="d-flex justify-content-between align-items-start gap-3 mb-4">
                <div>
                  <h1 class="h2 mb-2">Welcome, {{ profileDisplayName }}</h1>
                  <p class="text-secondary mb-0">Your session is active and this dashboard is consuming a protected sample API.</p>
                </div>
                <span class="badge text-bg-success">Authenticated</span>
              </div>

              <div v-if="dashboardLoading" class="text-secondary small mb-3">Loading dashboard...</div>
              <div v-else class="row g-3">
                <div v-for="card in dashboard.cards" :key="card.title" class="col-md-4">
                  <div class="rounded-3 border p-3 bg-body-tertiary h-100">
                    <div class="text-secondary small">{{ card.title }}</div>
                    <div class="fw-semibold mt-1">{{ card.value }}</div>
                    <div class="small text-secondary">{{ card.hint }}</div>
                  </div>
                </div>
              </div>

              <div v-if="dashboardError" class="alert alert-warning mt-3 mb-0">{{ dashboardError }}</div>
            </div>
          </section>

          <section class="card border-0 shadow-sm mt-4">
            <div class="card-body p-4">
              <p class="text-uppercase text-secondary small fw-semibold mb-1">Recent starter actions</p>
              <h2 class="h4 mb-3">Example dashboard feed</h2>
              <div class="list-group list-group-flush">
                <div v-for="item in dashboard.recentActions" :key="item.action" class="list-group-item px-0">
                  <div class="d-flex justify-content-between align-items-start gap-3">
                    <div>
                      <div class="fw-semibold">{{ item.action }}</div>
                      <div class="text-secondary small">{{ item.note }}</div>
                    </div>
                    <span class="badge text-bg-light border">{{ item.status }}</span>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </div>

        <div class="col-lg-4">
          <section class="card border-0 shadow-sm h-100">
            <div class="card-body p-4">
              <p class="text-uppercase text-secondary small fw-semibold mb-1">Profile details</p>
              <h2 class="h4 mb-3">Protected profile panel</h2>

              <div v-if="profileLoading" class="text-secondary small mb-3">Loading profile...</div>
              <div v-else class="d-flex align-items-center gap-3 mb-3">
                <img
                  v-if="profile.picture"
                  :src="profile.picture"
                  alt="profile"
                  class="rounded-circle border"
                  width="56"
                  height="56"
                />
                <div>
                  <div class="fw-semibold">{{ profile.name || 'Name after login' }}</div>
                  <div class="text-secondary small">{{ profile.email || 'Email after login' }}</div>
                </div>
              </div>

              <ul class="list-group list-group-flush small">
                <li class="list-group-item px-0">Backend username: {{ profile.username || 'pending' }}</li>
                <li class="list-group-item px-0">Email: {{ profile.email || 'pending' }}</li>
                <li class="list-group-item px-0">Session state: authenticated</li>
                <li class="list-group-item px-0">Protected API path: <code>/api/me</code></li>
              </ul>

              <div class="d-grid gap-2 mt-3">
                <button class="btn btn-outline-primary" @click="loadAuthStatus">Refresh session</button>
                <button class="btn btn-outline-secondary" @click="loadDashboard">Refresh dashboard</button>
              </div>

              <div v-if="profileError" class="alert alert-warning mt-3 mb-0">{{ profileError }}</div>
            </div>
          </section>
        </div>
      </section>
    </main>
  </div>
</template>
