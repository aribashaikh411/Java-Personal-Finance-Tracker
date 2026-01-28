# Budget Management Application 
A Java-based desktop application developed for the Object-Oriented Programming (CSC213) course.

## Overview
This application provides a functional foundation for personal finance management, allowing users to track their financial health through a clean, intuitive interface.

## Key Features
ser Authentication: Secure login and sign-up management for individual user data.
Income Tracking: Record and categorize income (Weekly, Monthly, or Yearly) with automated total calculations.
Expense Management: Record and categorize expenses (e.g., Transport, Food, Utilities).
Financial Dashboard: A central interface providing a consolidated summary of total income, expenses, and current balance.

## Technical Stack
Language: Java
GUI Library: Java Swing
Data Storage: Java I/O using `.txt` files for data persistence
Layout: Custom Null Layout for precise UI component placement

##  Project Structure
- `src/etsp/Main.java`: Entry point of the application.
- `src/etsp/Dashboard.java`: Main user interface and summary logic.
- `src/etsp/ExpenseManager.java` & `IncomeManager.java`: Core logic for financial records.
- `users.txt`: Storage for user credentials.
