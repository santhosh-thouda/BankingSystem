# Banking System Application

A Java-based banking system with account management features.

## Features
- Create savings and current accounts
- Deposit/withdraw funds
- Transfer between accounts
- Interest calculation for savings accounts
- Database persistence

## Requirements
- Java 11+
- MySQL 8.0+
- Maven 3.6+

## Setup
1. Create MySQL database:

CREATE DATABASE bank;


2. Configure database in `config.properties`

3. Build and run:

mvn clean package
java -jar target/banking-system-1.0.0.jar


## Database Schema
The application will automatically create these tables:
- `accounts` - Stores account information
- `transactions` - Records all financial transactions
- `transfers` - Records transfers between accounts

## Security Note
Change default credentials before deploying to production!
