# PC-61: Search by City

## Summary
- Adds city search in owner find/list views.
- Adds city autocomplete endpoint (`GET /api/cities?query=...`).
- Keeps pagination links bookmarkable with `lastName` + `city` URL params.
- Adds DB city indexes for H2, MySQL, and Postgres.
- Adds controller tests for city filter, AND logic, autocomplete, and pagination state.

## Workflow (as requested)
1. Assessed Jira description and acceptance criteria for implementation/test readiness.
2. Created feature branch first: `feature/pc-61-city-search`.
3. Added tests first (TDD) for search behavior.
4. Implemented backend + UI changes to satisfy tests and criteria.
5. Prepared rollout notes and validation notes.

## Main changes
- `src/main/java/org/springframework/samples/petclinic/owner/OwnerController.java`
  - Combined last-name + city filtering.
  - Added `GET /api/cities` autocomplete endpoint.
  - Preserves search state in model (`searchLastName`, `searchCity`).
- `src/main/java/org/springframework/samples/petclinic/owner/OwnerRepository.java`
  - Added:
    - `findByLastNameStartingWithAndCityContainingIgnoreCase(...)`
    - `findDistinctCitiesForAutocomplete(...)`
- `src/main/resources/templates/owners/findOwners.html`
  - Added city field, datalist autocomplete, live-search JS, clear button.
- `src/main/resources/templates/owners/ownersList.html`
  - Added search controls and preserved pagination query params.
- DB schema indexes:
  - `src/main/resources/db/h2/schema.sql`
  - `src/main/resources/db/mysql/schema.sql`
  - `src/main/resources/db/postgres/schema.sql`
- Tests:
  - `src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTests.java`
- i18n follow-up:
  - `src/main/resources/messages/messages.properties`
  - `src/main/resources/messages/messages_de.properties`
  - clear button now uses `#{clearCitySearch}` in both templates.

## Acceptance criteria mapping
- City field in owner search/list: implemented.
- Autocomplete cities: implemented via `/api/cities`.
- Live filtering while typing: implemented (debounced submit).
- Combined with name filter (AND): implemented.
- Case-insensitive + substring city search: implemented.
- Clear city filter button: implemented.
- Pagination maintained with filters: implemented via URL params.
- Performance optimization: city DB indexes added.

## Validation
- Automated Java test execution is currently blocked in this environment (no working JDK/JAVA_HOME).
- The added controller tests are included and ready to run locally/CI with JDK configured.
