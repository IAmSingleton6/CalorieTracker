# Architecture Guide

## Overview

This project follows a **Feature-First Clean Architecture** approach using **Kotlin Multiplatform (KMP)** and **Koin** for dependency injection.

The architecture is designed around the following goals:

* Strong separation of concerns
* Feature isolation
* Platform independence
* Testability
* Predictable dependency flow
* Scalability as the application grows

---

# Core Principles

## 1. Feature First

Every feature owns everything required for its implementation.

A feature contains:

* UI
* Domain
* Network/Data
* Dependency Injection

A feature should be understandable in isolation.

Example:

```text
features/
├── auth/
├── profile/
├── settings/
└── home/
```

---

## 2. Strict Layer Boundaries

Every feature contains three layers:

```text
UI → Domain → Network
```

Dependencies only flow downward.

### Allowed

```text
UI depends on Domain
Domain depends on Network contracts
Network implements Domain contracts
```

### Forbidden

```text
UI → Network
Network → UI
Network → ViewModel
Repository → ViewModel
```

---

## 3. Dependency Inversion

The Domain layer owns interfaces.

Implementations belong to lower layers.

Example:

```kotlin
interface UserRepository {
    suspend fun getUser(id: String): User
}
```

Implementation:

```kotlin
class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository
```

---

## 4. Shared Core Infrastructure

Only truly shared concerns belong in shared modules.

Examples:

```text
core/
├── network/
├── database/
├── logging/
├── analytics/
├── designsystem/
└── common/
```

Avoid creating a "god module".

---

# Module Structure

```text
shared/
│
├── core/
│   ├── common/
│   ├── network/
│   ├── database/
│   ├── logging/
│   └── designsystem/
│
├── features/
│   ├── auth/
│   ├── home/
│   ├── profile/
│   └── settings/
│
└── app/
```

---

# Feature Structure

Every feature follows the same layout.

```text
feature-auth/
│
├── ui/
│   ├── screen/
│   ├── components/
│   ├── navigation/
│   ├── state/
│   └── AuthViewModel.kt
│
├── domain/
│   ├── model/
│   ├── repository/
│   ├── usecase/
│   └── error/
│
├── network/
│   ├── dto/
│   ├── api/
│   ├── mapper/
│   └── repository/
│
└── di/
    └── AuthModule.kt
```

---

# Layer Definitions

---

## UI Layer

Responsible for:

* Compose screens
* Navigation
* UI state
* User interactions
* ViewModels

Contains:

```text
ui/
├── screen/
├── components/
├── state/
├── navigation/
└── viewmodel/
```

### Rules

ViewModels may depend only on:

* UseCases
* Domain Models

Never on:

* APIs
* DTOs
* Repository Implementations

Example:

```kotlin
class LoginViewModel(
    private val loginUseCase: LoginUseCase
)
```

---

## Domain Layer

The heart of the application.

Contains:

```text
domain/
├── model/
├── repository/
├── usecase/
└── error/
```

### Domain Models

Pure business objects.

```kotlin
data class User(
    val id: String,
    val name: String
)
```

No serialization annotations.

No framework dependencies.

---

### Repositories

Repository contracts live here.

```kotlin
interface UserRepository {
    suspend fun getUser(id: String): User
}
```

---

### Use Cases

Use Cases contain business logic.

Every user action should map to a Use Case.

Examples:

```text
GetProfileUseCase
LoginUseCase
UpdateSettingsUseCase
LogoutUseCase
```

Example:

```kotlin
class GetProfileUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke() =
        repository.getProfile()
}
```

### Rules

Use Cases may depend on:

* Repositories
* Other Use Cases

Never on:

* APIs
* DTOs
* Platform code

---

## Network Layer

Responsible for data acquisition.

Contains:

```text
network/
├── api/
├── dto/
├── mapper/
└── repository/
```

---

### API

Ktor service definitions.

```kotlin
interface UserApi {
    suspend fun getUser(id: String): UserDto
}
```

---

### DTOs

Remote representations.

```kotlin
@Serializable
data class UserDto(
    val id: String,
    val name: String
)
```

DTOs never leave the network layer.

---

### Mappers

Convert DTOs into domain models.

```kotlin
fun UserDto.toDomain() =
    User(
        id = id,
        name = name
    )
```

---

### Repository Implementations

```kotlin
class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository
```

Responsibilities:

* API calls
* Mapping
* Error translation
* Caching coordination

Not business logic.

---

# Service Layer

Services are infrastructure.

Services are not business logic.

Examples:

```text
AuthService
TokenService
AnalyticsService
LoggerService
DatabaseService
```

Services belong in:

```text
core/
```

or

```text
feature/network/service/
```

if feature-specific.

### Rules

Services may be used by:

* Repository Implementations
* Infrastructure modules

Services should never be injected directly into ViewModels.

---

# Dependency Flow

```text
UI
│
▼
UseCase
│
▼
Repository Interface
│
▼
Repository Implementation
│
▼
API / Database / Service
```

---

# Koin Structure

Every module owns its own DI definition.

Example:

```kotlin
val authModule = module {

    single<AuthApi> {
        AuthApiImpl(get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    factory {
        LoginUseCase(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}
```

---

# Dependency Registration

Application startup:

```kotlin
startKoin {
    modules(
        coreModule,
        authModule,
        profileModule,
        homeModule
    )
}
```

Features should expose:

```kotlin
val featureModule
```

Only.

The app should not know feature internals.

---

# Cross-Feature Communication

Avoid direct feature dependencies.

Preferred:

```text
Feature A
     ↓
Domain Contract
     ↑
Feature B
```

Or:

```text
Shared Event Bus
Navigation Contract
```

Never:

```text
AuthViewModel -> ProfileRepository
```

---

# Testing Strategy

## UI Tests

Mock Use Cases.

```text
ViewModel
 ↓
Fake UseCase
```

---

## Domain Tests

Mock Repositories.

```text
UseCase
 ↓
Fake Repository
```

---

## Network Tests

Mock APIs.

```text
Repository
 ↓
Fake API
```

---

# Naming Conventions

## Use Cases

```text
Verb + Noun + UseCase
```

Examples:

```text
LoginUseCase
GetProfileUseCase
UpdateSettingsUseCase
```

---

## Repository Contracts

```text
UserRepository
AuthRepository
SettingsRepository
```

---

## Repository Implementations

```text
UserRepositoryImpl
AuthRepositoryImpl
SettingsRepositoryImpl
```

---

## DTOs

```text
UserDto
LoginRequestDto
LoginResponseDto
```

---

## ViewModels

```text
LoginViewModel
ProfileViewModel
```

---

# Testing Standards

Testing is a first-class concern within the architecture.

Every layer should be independently testable through dependency inversion and dependency injection.

## Testing Frameworks

The project standardises on:

```text
JUnit
MockK
Kotlin Coroutines Test
Turbine (for Flow testing)
```

### Responsibilities

| Framework       | Purpose                                   |
| --------------- | ----------------------------------------- |
| JUnit           | Test runner                               |
| MockK           | Mocking dependencies                      |
| Coroutines Test | Testing suspend functions and dispatchers |
| Turbine         | Testing Flows and StateFlows              |

---

# MockK Guidelines

MockK is the preferred mocking framework throughout the project.

### Allowed

```kotlin
val repository = mockk<UserRepository>()
```

```kotlin
coEvery {
    repository.getUser("123")
} returns user
```

```kotlin
coVerify {
    repository.getUser("123")
}
```

### Forbidden

Avoid mocking:

* DTOs
* Domain Models
* State classes
* Data classes

Example:

```kotlin
// Bad
val user = mockk<User>()

// Good
val user = User(
    id = "123",
    name = "John"
)
```

Domain models should be instantiated directly.

---

# Layer Testing Rules

## UI Layer

ViewModels should be tested in isolation.

Dependencies must be mocked using MockK.

Example:

```kotlin
class LoginViewModelTest {

    private val loginUseCase = mockk<LoginUseCase>()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase)
    }
}
```

### Verify

* State updates
* User actions
* Navigation events
* Error handling

### Do Not Verify

* Repository behaviour
* API behaviour
* Mapper behaviour

Those belong to lower layers.

---

## Use Case Tests

Use Cases are the primary location for business logic testing.

All repository dependencies should be mocked.

Example:

```kotlin
@Test
fun `returns profile from repository`() = runTest {

    coEvery {
        repository.getProfile()
    } returns profile

    val result = useCase()

    assertEquals(profile, result)
}
```

### Verify

* Business rules
* Validation
* Data transformations
* Error handling

### Required

Every UseCase should have unit tests.

---

## Repository Tests

Repository implementations should be tested separately.

Dependencies should be mocked.

Example:

```kotlin
private val api = mockk<UserApi>()
```

Verify:

```kotlin
coVerify {
    api.getUser("123")
}
```

### Verify

* API interaction
* DTO mapping
* Error mapping
* Cache behaviour

### Do Not Verify

Business rules.

Business rules belong in UseCases.

---

## Flow Testing

All Flows and StateFlows should be tested using Turbine.

Example:

```kotlin
viewModel.state.test {

    assertEquals(
        LoginState.Loading,
        awaitItem()
    )

    assertEquals(
        LoginState.Success,
        awaitItem()
    )
}
```

Avoid manual collection where possible.

---

# Test Structure

Tests should mirror production structure.

Example:

```text
feature-auth/
│
├── ui/
│   └── LoginViewModel.kt
│
├── domain/
│   └── LoginUseCase.kt
│
└── network/
    └── AuthRepositoryImpl.kt

test/
│
├── ui/
│   └── LoginViewModelTest.kt
│
├── domain/
│   └── LoginUseCaseTest.kt
│
└── network/
    └── AuthRepositoryImplTest.kt
```

---

# Mocking Rules

## Mock Interfaces

Always mock abstractions.

Preferred:

```kotlin
mockk<UserRepository>()
```

Avoid:

```kotlin
mockk<UserRepositoryImpl>()
```

The architecture depends on interfaces, therefore tests should too.

---

## One Layer Down

Tests may only mock immediate dependencies.

Example:

```text
ViewModel
 └── Mock UseCase
```

```text
UseCase
 └── Mock Repository
```

```text
Repository
 └── Mock Api
```

Never skip layers.

Bad:

```text
ViewModel
 └── Mock Api
```

---

# Coverage Expectations

Minimum expectations:

| Layer                      | Coverage |
| -------------------------- | -------- |
| UseCases                   | 100%     |
| ViewModels                 | High     |
| Repository Implementations | High     |
| Mappers                    | High     |
| DTOs                       | None     |

Focus on behaviour rather than line coverage.

---

# Architectural Testing Rules

1. Every UseCase must have unit tests.
2. Every ViewModel must have unit tests.
3. Repository implementations must be tested independently.
4. MockK is the standard mocking framework.
5. Tests must mock interfaces, not implementations.
6. Tests may only mock one layer below the system under test.
7. Domain models should be real objects, not mocks.
8. Flows must be tested using Turbine.
9. Business logic belongs in UseCase tests.
10. ViewModels should be tested without network or database dependencies.


# Architectural Rules

1. UI never talks to APIs.
2. UI only uses UseCases.
3. Domain owns repository contracts.
4. Network implements repository contracts.
5. DTOs never leave the network layer.
6. Domain models never contain framework annotations.
7. Business logic belongs in UseCases.
8. Services are infrastructure, not business logic.
9. Features are self-contained.
10. Dependencies always point inward.

Following these rules ensures the project remains scalable, testable, and maintainable as additional features and platforms are added.
