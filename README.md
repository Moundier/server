
# java-api 
README fot the java-api.

### Production and Development environment
- `sudo docker ps` (ensure no duplicate containers are running)
- `sudo docker system prune --all --volumes` (removes previous allocated cache)
- `sudo docker compose -f docker-compose-db.yml up` (starts only the database and pgadmin)
- `sudo docker compose -f docker-compose-api.yml up` (starts only the API server)

### Fixes
- Read only error by changing file permissions: `sudo chown -R $USER ./`

### Todo
- [x] Implement RESTful API endpoints.
- [x] Implement user authentication and authorization
- [x] Implement security authentication and authorization
- [x] Implement jwt bearer
- [x] Implement refresh token
- [x] Update docker-compose to V3 [depends_on](https://docs.docker.com/compose/compose-file/05-services/#depends_on)
- [ ] Implement server to server AMQP (Advanced Message Queuing Protocol)

### Todo
- [ ] Implement RBAC 
- [ ] Implement Logout.
- [ ] Implement Validation Annotations
- [ ] Implement helpers (done, info, warn, success, failure,)

### User-Based Collaborative Filtering
Implementation Steps: 
1. Collect (user_data, item_data, interaction_data)
2. Pre-process data
3. Similarity Calculation: measure the similarity between users, based on their preferences (get similar users)
4. Recommendation: get similar users and recommend items they liked, but the target hasnt interacted with (get_similar_users_preferences)

### Item-Based Collaborative Filtering
Implementation Steps: 
1. Collect data (user_data, movie_data, interaction_data)
2. Pre-process data
3. Similarity Calculation: Use a similarity metric to measure the similarity between items based on user interactions.
4. Recommendation: for a target item, identify similar items based on user interactions and recommend them to users who interacted with the target item.

### Content-Based Filtering
Implementation Steps:
1. Collect data (user_data, item_data, interaction_data)
2. Pre-process data
3. Feature extraction: extract relevant features from the movie data (e.g., genre, keywords)
4. User-Profile Creation: create user profiles based on their preferences, incorporating explicit (ratings) and implicit (watch history) feedback.
5. Recommendation: recommend items that have features similar to the user's preferences. 
