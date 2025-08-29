INSERT INTO wallets(user_id, currency_id, total, available)
SELECT u.id,
       c.id,
       0,
       0
FROM currencies c,
     users u
WHERE u.email IN ('user@yopmail.com');
