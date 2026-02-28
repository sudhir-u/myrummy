# Test Coverage Report – RummyTriangle

## Summary

| Area | Test Files | Tests | Coverage | Status |
|------|------------|-------|----------|--------|
| **Domain** | 7 | ~85 | Strong | CardGroup, Hand, Deck, DiscardSet, StandardPlayingCard well covered |
| **Service** | 1 | 4 | Partial | GameService only; WebService, UsersService untested |
| **Web/Controllers** | 0 | 0 | None | HomeResource, Security untested |

**Overall:** Domain logic has good coverage (~85 tests). Web layer and services have little or no coverage.

---

## Detailed Coverage

### Well Covered

| Class | Test File | Tests | What's Tested |
|------|-----------|------|---------------|
| **CardGroup** | CardGroupTest | 40 | hasMinimumSetCount, hasSameSuit, hasAllStandardCards, isPureSequence, isValidSequence, isPureSet, isValidSet, getPoints, getJokerCount, getQualifiedJokersInGroup, insertCardAtIndex. Edge cases: A-2-3 sequences, K-Q-J-Ace, jokers. |
| **Hand** | Handtest | 13 | addCard, getCardCount, sortIntoGroupsOnRequest, moveCardBetweenGroupsOnRequest, moveCardInGroupOnRequest, addCardGroupOfCards, profile (PURE_RUN, PURE_SET, RUN, SET). |
| **GameService** | GameServiceTest | 4 | Deck counts (Standard, RummySingle, RummyDouble), pullCard, full flow: add players, deal 13 each, pull joker. |
| **StandardPlayingCard** | StandardPlayingCardTest | 7 | Sorting (same suit, different suits), isJokerInContext (standard/joker as joker), isContainedIn. |
| **Deck** | DeckTest, StandardDeckTest, RummySingleDeckTest | 4 | drawRandomCards, pickRandomIndex, card counts (52, 53, 54, 108). |
| **DiscardSet** | DiscardSetTest | 3 | addCard, getOpenCard, pullOpenCard. |

### Partially Covered

| Class | Gap |
|-------|-----|
| **Hand** | `discardCard()`, `createNewGroupOnRequest()`, `isRummy()` not explicitly tested. `getPoints()` returns hardcoded 80 – test `testHandnitialGetPointsPostProfiling` asserts 80; will need update when real logic is implemented. |
| **CardGroup** | `hasAllStandardCards()` with empty group and with joker – fix added, but no dedicated tests. |

### Not Covered

| Class / Component | Priority | Notes |
|-------------------|----------|-------|
| **WebService** | High | addUser, updateUser, getUserInfo. `getUserInfo(id)` uses `.get()` – throws if not found. |
| **HomeResource** | High | REST endpoints for /, /admin, /user, /liveusers, /users. |
| **UsersService** | Medium | loadUserByUsername, UsernameNotFoundException. |
| **UsersNDetails** | Medium | Authority handling for null/empty roles (logic was added). |
| **SecurityConfiguration** | Low | Auth/authz config – usually tested via integration tests. |
| **Player** | Low | Used in GameService; no standalone tests. |
| **JokerPlayingCard** | Low | Only via CardGroup/Hand; isContainedIn, isJokerInContext could be unit tested. |
| **RummyDoubleDeck** | Low | Count tested in GameServiceTest only. |

---

## Tests to Add

### Priority 1 – Bug Guard & Core Logic ✓ Added

1. **CardGroup.hasAllStandardCards** ✓ – `CardGroupTest`: testHasAllStandardCardsEmptyGroup, testHasAllStandardCardsGroupWithJoker, testHasAllStandardCardsAllStandardCards

2. **WebService** ✓ – `WebServiceTest`: getLoggedInUsers, getUserInfo (valid + invalid), addUser, updateUser

### Priority 2 – Hand & Game Logic ✓ Added

3. **Hand.discardCard** ✓ – `HandAdditionalTest`: testDiscardCardRemovesFromGroup, testDiscardCardRemovesEmptyGroup

4. **Hand.createNewGroupOnRequest** ✓ – `HandAdditionalTest`: testCreateNewGroupOnRequest

5. **UsersNDetails** ✓ – `UsersNDetailsTest`: null roles, empty roles, ADMIN+USER roles

### Priority 3 – Remaining

6. **Hand.isRummy**
   - When all groups valid (points = 0) → true
   - When any invalid group (points > 0) → false  
   *(Depends on implementing `getPoints()` correctly.)*

5. **Hand.createNewGroupOnRequest**
   - Adds new empty group; group count increases

### Priority 4 – Web Layer

7. **HomeResource (MockMvc)**
   - GET / → 200, contains "Welcome"
   - GET /admin (unauthenticated) → redirect to login
   - GET /admin (as ADMIN) → 200
   - GET /user (as USER) → 200
   - GET /liveusers → 200, JSON array

### Priority 5 – Minor

8. **JokerPlayingCard**
   - isContainedIn with JOKER in list → true
   - isJokerInContext(any) → true
   - getDescription contains "Joker"

9. **Player**
   - setUser/getUser, setHand/getHand

---

## Running Tests

```bash
cd RummyTriangle
mvn test
```

For a report:
```bash
mvn test jacoco:report
```
*(Requires JaCoCo plugin in pom.xml.)*

---

## Notes

- **Handtest** uses `Handtest` (lowercase `t`); consider renaming to `HandTest` for consistency.
- **CardGroupTest** line 326: `testIsNotvalidSequenceDifferentSuitsNoJokers` – typo in name (`Notvalid` → `NotValid`).
- **DiscardSet**: `getOpenCard()` and `pullOpenCard()` use `element()` (head) while `addCard` adds to tail – verify this matches intended discard-pile semantics (LIFO vs FIFO).
