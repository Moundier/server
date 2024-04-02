# About the server

A record about theorical segment of recommendation.

### User-Based Recommendation:
- **Compare my preferences to other similar users preferences, and returns recommendations based on those**
- Recommends items to a user based on the preferences and interactions of similar users.
- Identifies users with similar preferences and recommends items liked or interacted with by those similar users.

### Item-Based Recommendation:
- **Based on idirect filter (clicked, viewed, liked)**
- Recommends items to a user based on the similarity of items they have interacted with.
- Identifies items similar to those the user has shown interest in (clicked, viewed, liked).

### Content-Based Recommendations:
- **Based on explicit filter**
- Recommends items to a user based on explicit filters or topics chosen by the user.
- Retrieves items that match the specified filters or topics chosen by the user, focusing on the content attributes.

Note: Hybrid approaches, combining elements of these recommendation types, are common for leveraging their strengths and addressing limitations.

---

# Token Management

This record provides a simple view of how tokens are managed.

### 1. Frontend

### `auth.interceptor.ts`
The `auth.interceptor.ts` switches tokens places if the access token has expired.

### `auth.guard.ts`
The `auth.guard.ts` controls access to tabs based on the value returned from the `isSessionValid` function.

### `auth.token.service.ts`
The `auth.token.service.ts` contains the `isSessionValid` function, which operates as follows:
1. If the access token is valid, it grants access to pages, returning true. If the access token is invalid, it moves to the next `if` statement.
2. If the refresh token is valid, it refreshes the access token and grants access to pages, returning true. If the refresh token is invalid, it moves to the next `if` statement.
3. If both tokens are invalid, it clears the local storage, performs a session logout, and prevents access to pages, returning false.

### 2. Backend
- The `AuthInterceptor.java` is a `server side` interceptor. 
- It validates the token on each request made `client side`. 