# SkillSwap

A Spring Boot and MySQL skill exchange app with a vanilla HTML, CSS, and JavaScript dashboard.

## Requirements

- Java 17 or later
- Maven 3.6+
- MySQL 8.x (or a compatible MySQL server)

## Database setup

Create the database once in MySQL:

```sql
CREATE DATABASE skillswap CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Configure the database connection in the environment before starting Spring Boot. The defaults are `root` with an empty password for a local development database. Override them rather than editing the app or placing credentials in the frontend.

PowerShell example:

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/skillswap?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = 'your-local-mysql-password'
mvn spring-boot:run
```

The configured Hibernate `update` mode creates the application tables and adds missing columns when the app starts. The existing `members.credit_balance` column is mapped explicitly. Back up valuable databases before applying schema changes.

Open [http://localhost:8080/](http://localhost:8080/). Add or select a member, offer a skill, and request it as a different member. A request moves through `PENDING`, `CONFIRMED`, and `COMPLETED` (or `REJECTED`). Completing a confirmed request atomically deducts one credit per requested hour from the requester, credits the provider, writes both ledger entries, and marks the request complete. Credits are checked when the request is made and again when it is completed.

## REST endpoints

| Method | Path | Purpose |
|---|---|---|
| POST, GET | `/api/members` | Create a member / list members |
| GET, PUT, DELETE | `/api/members/{id}` | Read, update, or delete a member |
| GET | `/api/skills` | List skill offers |
| GET, DELETE | `/api/skills/{id}` | Read or delete a skill offer |
| POST | `/api/skills/member/{memberId}` | Create an offer for a member |
| POST, GET | `/api/session-requests` | Create / list session requests |
| GET | `/api/session-requests/{id}` | Read a session request |
| PUT | `/api/session-requests/{id}/confirm` | Confirm a pending request |
| PUT | `/api/session-requests/{id}/reject` | Reject a pending request |
| PUT | `/api/session-requests/{id}/complete` | Complete a confirmed request and transfer credits |
| GET | `/api/credits` | List ledger entries |
| GET | `/api/credits/{id}` | Read a ledger entry |
| GET | `/api/credits/member/{memberId}` | List one member's credit history |

The create-request JSON has `requesterId`, `providerId`, `skillOfferId`, and `requestedHours`. The credit ledger response contains signed `amount` values: negative for spending and positive for receiving.

## Try the workflow

1. Start the app and open the site. In Members, create Sanjay with 10 credits and Arun with 15 credits.
2. Select Arun and offer a Java Programming skill with a two-hour session.
3. Select Sanjay, request the Java skill for two hours, then select Arun and confirm the request.
4. Complete the confirmed session. Sanjay should now have 8 credits, Arun 17, and each member's ledger should show respectively `-2` and `+2`.

All dashboard data is loaded from the REST API; the current member selection alone is stored in local storage.
