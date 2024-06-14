# Faal Hafez App

This project is a Java application that uses a graphical user interface (GUI) to fetch and display a Hafez fortune (Faal Hafez) from an API.

## Prerequisites

To run this project, you need the following:

- Java Development Kit (JDK) 8 or higher
- A Java development environment such as IntelliJ IDEA or Eclipse
- `org.json` library

## Installation

1. Clone the repository to your local machine:
    ```sh
    git clone <repository-url>
    ```

2. Import the project into your preferred Java development environment.

3. Download the `org.json` library and add it to your project's build path. You can download it from [here](https://mvnrepository.com/artifact/org.json/json).

## Usage

1. Run the `FaalHafezApp` class in your Java IDE.
2. The initial window will appear
3. Click the "دریافت فال" button to fetch a fortune from the API.
4. The application will display the fetched fortune in a new window

## Code Overview

- The main class is `FaalHafezApp`, which sets up the GUI and handles button click events.
- When the "دریافت فال" button is clicked, an HTTP GET request is sent to `https://faal.spclashers.workers.dev/api` to fetch the fortune data.
- The response is parsed and displayed in a new window.
