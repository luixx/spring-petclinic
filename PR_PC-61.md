# PC-61: Search by City

## Jira
- Issue: `PC-61`
- Scope: owner city search + autocomplete + pagination-safe URL filters

## Why
- Users need to filter owners by city quickly, combine this with existing name search, and keep searches bookmarkable.
- Existing owner search only supported last-name starts-with logic.

## What changed
1. Backend search and autocomplete:
   - Added combined query:
     - `findByLastNameStartingWithAndCityContainingIgnoreCase(...)`
   - Added city autocomplete query:
     - `findDistinctCitiesForAutocomplete(...)`
   - Added endpoint:
     - `GET /api/cities?query=...`
2. Controller behavior:
   - `/owners` now supports `lastName` + `city` with AND logic.
   - City filter is case-insensitive substring.
   - Search state is persisted in model (`searchLastName`, `searchCity`).
   - If only city filter is used and a single record is found, the app stays on list view (no owner-detail redirect).
3. UI behavior:
   - City search field added to find/list screens.
   - Live filtering while typing (debounced submit).
   - Autocomplete suggestions via `/api/cities`.
   - Clear city button added.
   - Pagination links keep `lastName` and `city` URL params.
4. Performance:
   - Added `city` index in H2, MySQL, and Postgres schemas.
5. i18n:
   - Added `clearCitySearch` key in:
     - `messages.properties`
     - `messages_de.properties`
   - Button now uses message key in both owner templates.

## Files
- `src/main/java/org/springframework/samples/petclinic/owner/OwnerController.java`
- `src/main/java/org/springframework/samples/petclinic/owner/OwnerRepository.java`
- `src/main/resources/templates/owners/findOwners.html`
- `src/main/resources/templates/owners/ownersList.html`
- `src/main/resources/db/h2/schema.sql`
- `src/main/resources/db/mysql/schema.sql`
- `src/main/resources/db/postgres/schema.sql`
- `src/main/resources/messages/messages.properties`
- `src/main/resources/messages/messages_de.properties`
- `src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTests.java`

## Acceptance criteria mapping
- Search field for city in owner list: done.
- Autocomplete for available cities: done (`/api/cities`).
- Live filtering while entering city: done (debounced submit).
- Combined with name search (AND): done.
- Case-insensitive search: done.
- Substring search (`Berlin` -> `Berlin-Mitte`): done.
- Clear city button: done.
- Pagination preserved during search: done (URL params retained).
- Performance for larger owner sets: addressed via DB index on `city`.

## Workflow followed
1. Assessed issue description sufficiency for implementation and tests.
2. Created feature branch first: `feature/pc-61-city-search`.
3. Added tests first (TDD).
4. Implemented backend and UI to satisfy behavior.
5. Added documentation/PR notes.

## Testing
- Added/updated controller tests in `OwnerControllerTests`.
- Environment limitation:
  - Java tests could not be executed in this runtime due to missing JDK/JAVA_HOME.
  - Tests are ready to run in CI or any local environment with JDK configured.

## Risk / impact
- Main behavior change is owner search flow and list rendering.
- Redirect semantics intentionally changed for city-only single-result searches to keep list workflow.
- DB schema index additions are additive and low risk.
