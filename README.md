# 🛫 Holiday Journey Planner

A backend Java application that calculates the **cheapest round-trip holiday journey** based on vehicle type (car or taxi) and available flight routes. Designed for use by a holiday agency to suggest cost-effective journeys to customers, including indirect multi-leg flights if cheaper.

---

## 🧠 Problem Overview

Customers want to travel from their home to a destination and return. The system must:

- Determine the cheapest vehicle option (car or taxi) to reach the airport
- Find the cheapest outbound and inbound flight routes, including indirect flights
- Calculate the total cost for the round-trip journey
- Handle missing routes gracefully with "No outbound flight" or "No inbound flight"

---

## 🛠 Technologies Used

- **Java 17**
- **Maven** (build and dependency management)
- **JUnit 5** (unit and acceptance testing)
- **Console-based CLI interface**

---


---

## 🚀 How to Run the Application

1. Clone the repository
2. Open the project in IntelliJ or your preferred Java IDE
3. Ensure Java 17 is installed and configured
4. Build the project using Maven:

```bash
mvn clean install
```
### ▶️ Run the App Interactively
```
mvn exec:java -Dexec.mainClass=org.example.Main
```
### 💡 Alternative: Run from IntelliJ (No command line)

If you're using IntelliJ IDEA, you can run the program directly:

1. Open the project in IntelliJ
2. In the `src/main/java/org/example/Main.java` file, right-click anywhere inside the class
3. Click **Run 'Main.main()'**
4. The app will start in the IntelliJ terminal, and you can follow the same CLI prompts


### 🧪 How to Run the Tests
```
mvn test
```
### 💡 Alternative: Run tests from IntelliJ

You can also run all unit and acceptance tests directly from IntelliJ:

1. Open the project in IntelliJ
2. Right-click the `test` directory (under `src/test/java`)
3. Click **Run 'All Tests'** or select individual test classes (e.g., `JourneyPlannerTest`)
4. Test results will appear in IntelliJ’s test runner pane

### ✅ Sample Journey Coverage

The `JourneyPlannerAcceptanceTest` class includes complete automated coverage for:

- Journey rows **1–10** from the assessment's **sample input/output**
- Verifies vehicle type, flight route, cost breakdown, and total cost
- Ensures that "No outbound flight" or "No inbound flight" conditions are respected where applicable

This shows the program works exactly as required against the example data.


### 🏙 Available Cities
This version includes four sample cities (represented by codes):

| Code | City   |
| ---- | ------ |
| L    | London |
| P    | Paris  |
| M    | Madrid |
| R    | Rome   |

### 🧭 Sample CLI Output
```
📍 Enter departure city code (L/P/M/R): L
🚗 How far (in miles) are you from London airport? 20
👥 Enter number of passengers: 2
🛫 Enter destination city code (L/P/M/R): P

✅ Journey Suggestion:
From: London
To: Paris
--------------------------------------------------
Vehicle: Car
Vehicle Return Cost: £11.00
Outbound Flight Route: London → Rome [400 mi] => Rome → Paris [400 mi]
Outbound Flight Cost: £160.00
Inbound Flight Route: Paris → London [800 mi]
Inbound Flight Cost: £160.00
Total Journey Cost: £331.00
```

### ✅ Features Implemented
- Clean, modular Java design using OOP principles
- Cheapest route logic with graph-based pathfinding (Dijkstra-like)
- Cost calculation for cars (with parking) vs taxis (by passenger count)
- Interactive CLI to simulate user input
- Smart handling of "no route available" scenarios
- Unit and acceptance testing
- Readable journey output with city names and flight miles


### Test Sample Results

Please Note:
This solution selects the cheapest vehicle option based purely on cost — even if it involves using multiple cars for larger groups.
Although the original sample output for Row 6 uses a taxi for 5 passengers, this implementation opts for 2 cars if that results in a lower total cost

```
#   | Vehicle | Vehicle Cost        | Outbound Route                 | Outbound Cost  | Inbound Route                  | Inbound Cost   | Total Cost 
----+---------+---------------------+--------------------------------+----------------+--------------------------------+----------------+-------------
1   | Car     | £11.00              | BF400--FD200                   | £120.00       | DE300--EB500                   | £160.00       | £291.00   
2   | Car     | £15.00              | BF400--FD200                   | £60.00        | DE300--EB500                   | £80.00        | £155.00   
3   | Car     | £11.00              | AB800--BF400--FD200            | £280.00       | No inbound flight              | £0.00         | £0.00     
4   | Car     | £15.00              | No outbound flight             | £0.00         | AB800--BC900                   | £340.00       | £0.00     
5   | Car     | £7.00               | BC900                          | £180.00       | CE200--EB500                   | £140.00       | £327.00   
6   | Car     | £14.00              | BC900                          | £450.00       | CE200--EB500                   | £350.00       | £814.00   
7   | Car     | £13.00              | DE300--EB500                   | £80.00        | BF400--FD200                   | £60.00        | £153.00   
8   | Car     | £19.00              | No outbound flight             | £0.00         | AB800--BF400--FD200            | £560.00       | £0.00     
9   | Taxi    | £4.00               | BF400--FD200                   | £120.00       | DE300--EB500                   | £160.00       | £284.00   
10  | Car     | £45.00              | BF400--FD200                   | £540.00       | DE300--EB500                   | £720.00       | £1305.00  

```

### 📌 Notes
- Flight data is designed to sometimes favor indirect routes (via randomized distances)
- Only 4 cities are included to simplify testing and ensure route coverage
- Program can easily be extended to add more cities, a frontend, or CSV import/export