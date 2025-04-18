CREATE DATABASE IF NOT EXISTS bank;
USE bank;

CREATE TABLE IF NOT EXISTS accounts (
    accountNumber VARCHAR(20) PRIMARY KEY,
    accountHolderName VARCHAR(100) NOT NULL,
    pin VARCHAR(4) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    accountType VARCHAR(20) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    transactionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    accountNumber VARCHAR(20) NOT NULL,
    transactionType VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)
);

SELECT * FROM accounts;


-- Check accounts with balance > 5000
SELECT accountNumber, accountHolderName, balance 
FROM accounts 
WHERE balance > 5000;

-- Count transactions by type
SELECT transactionType, COUNT(*) as count
FROM transactions
GROUP BY transactionType;



SELECT * FROM transactions 
WHERE accountNumber = '1234567891'
ORDER BY transactionDate DESC
LIMIT 5;