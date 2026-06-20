# Calorie Tracker

A cross-platform calorie tracking app built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, targeting Android, iOS, Desktop (JVM), and Web (Wasm/JS).

Track your daily calorie intake, view history with charts, and manage your daily goal—all from a shared codebase.

## Tech Stack

| Technology | Purpose |
|---|---|
| **Kotlin 2.3.21** | Language |
| **Compose Multiplatform 1.11.0** | Shared UI (Material3) |
| **Kotlin Multiplatform** | Cross-platform targeting |
| **Koin 4.2.0** | Dependency injection |
| **SQLDelight 2.3.2** | Local database |
| **kotlinx-datetime 0.8.0** | Date/time handling |
| **detekt 1.23.8** | Static analysis |
| **Turbine / Mockative** | Flow testing / mocking |

## Architecture

Feature-first Clean Architecture (`UI → Domain → Data` with dependency inversion). Each feature is self-contained in `shared/src/commonMain`:

- **`tracking/`** — Daily calorie logging, progress bar, quick-add
- **`history/`** — Bar chart history, stats
- **`settings/`** — Daily goal management

Shared core modules live under `shared/src/commonMain/core/`: `common`, `database`, `designsystem`.

A full architecture guide is in [architecture.md](architecture.md).

## Modules

| Module | Platform |
|---|---|
| `:shared` | Common code (Android, iOS, JVM, Wasm/JS) |
| `:androidApp` | Android entry point |
| `:desktopApp` | Desktop (JVM) entry point |
| `iosApp/` | iOS Xcode project |

## Setup

1. **Clone the repo**
   ```bash
   git clone <repo-url>
   cd CalorieTracker
   ```

2. **Open in Android Studio** (or IntelliJ IDEA with KMP plugin)

3. **Run the app**

   | Platform | Command |
   |---|---|
   | Android | `./gradlew :androidApp:assembleDebug` |
   | Desktop | `./gradlew :desktopApp:run` |
   | Desktop (hot reload) | `./gradlew :desktopApp:hotRun --auto` |
   | Web (Wasm) | `./gradlew :shared:wasmJsBrowserDevelopmentRun` |
   | iOS | Open `iosApp/` in Xcode |

4. **Run tests**
   ```bash
   ./gradlew allTests
   ```
   Or per-platform: `:shared:jvmTest`, `:shared:iosSimulatorArm64Test`, `:shared:androidHostTest`, etc.

## Code Quality

- **detekt** runs on `preBuild` via Git hooks (installed automatically)
- Manual run: `./gradlew detekt`
