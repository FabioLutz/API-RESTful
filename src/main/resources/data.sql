CREATE TABLE users_tb(
    id INT PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(128)
);

INSERT INTO users_tb (
    id,
    username,
    password
) VALUES (
    1,
    'User1',
    'password123'
);