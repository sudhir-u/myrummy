# User setup and troubleshooting

## 1. Users already created manually

The app **does not overwrite** existing users. If you inserted `admin`, `alice`, or `bob` manually, they stay. The DataSeeder only **adds** a user if `findByUserName(...)` returns empty (see `DataSeeder.java`).

So you can keep your manually created users. To fix login (see below), use the password-reset endpoint or delete and let the seeder recreate.

---

## 2. "Bad credentials" for alice / bob (or admin)

Login uses **BCrypt**-hashed passwords. If you inserted users manually with plain text or wrong hashes, login will fail with "Bad credentials".

**Option A – Reset password (recommended)**  
As **admin**, call the password-reset endpoint so the app stores a correct BCrypt hash:

```bash
# After logging in as admin (e.g. in browser), from terminal with session cookie, or use a REST client:

curl -X PUT 'http://localhost:8085/users/alice/password' \
  -H 'Content-Type: application/json' \
  -d '{"password":"alice123"}' \
  -u admin:admin123

curl -X PUT 'http://localhost:8085/users/bob/password' \
  -H 'Content-Type: application/json' \
  -d '{"password":"bob123"}' \
  -u admin:admin123
```

- **URL:** `PUT /users/{userName}/password`
- **Body:** `{ "password": "newpassword" }` (min 6 characters)
- **Auth:** Only **ADMIN** can call this. Log in as admin in the browser first, then either:
  - **From browser console (F12):**  
    `fetch('/users/alice/password', { method: 'PUT', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ password: 'alice123' }) }).then(r => r.json()).then(console.log)`
  - Or use curl with session cookie, or a REST client (Postman) after logging in.

**Option B – Delete and let the app recreate**  
If you prefer to start clean:

```sql
DELETE FROM app_user WHERE user_name IN ('alice', 'bob');
```

Restart the app. DataSeeder will see that `alice` and `bob` are missing and create them with passwords `alice123` and `bob123`.

---

## 3. Second browser not refreshing (still shows "Start Game")

The second browser must:

1. Be in the **same room** (join with the same room ID).
2. Be **subscribed to that room’s WebSocket topic** so it receives draw/discard/start updates.

**Changes made in the app:**

- **Subscribe when WebSocket connects:** If you’re already in a room when the connection is established (or reconnects), the client now subscribes to `/topic/room/{roomId}` so it gets all game updates.
- **Single room handler:** One `onRoomMessage` handler updates players, shows the play area when state is PLAY/SCORE/FINISHED, and calls `refreshPlayState()` so the second browser’s hand, turn, and buttons stay in sync.

**What you should do:**

1. **Second browser:** Log in (e.g. as bob), open the game page, **join the same room** (paste the room ID from the first browser).
2. **First browser:** Start the game. The second browser should soon show “Game in progress” and the play area; if it was connected a bit late, it may take one refresh or the next broadcast.
3. If the second browser was opened **before** the WebSocket connected, it now subscribes to the room as soon as the connection is ready, so it will receive “game started” and later draw/discard broadcasts.

If it still doesn’t update, do a **hard refresh** (Ctrl+F5 / Cmd+Shift+R) on the second browser so it loads the latest `game.html` with the new subscription logic.

---

## Quick reference

| Issue | Action |
|-------|--------|
| Users already in DB | No change needed; seeder only adds missing users. |
| Bad credentials (alice/bob) | As admin: `PUT /users/alice/password` and `PUT /users/bob/password` with `{"password":"alice123"}` / `{"password":"bob123"}`. |
| Second browser stuck on “Start Game” | Ensure same room, same app version; hard refresh second browser; check WebSocket “Connected” on both. |
