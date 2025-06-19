# 📝 Notes App

A simple and lightweight Android application that allows users to write, save, and delete notes. Built using Kotlin and Gradle Kotlin DSL, this app serves as a minimal yet functional note-taking tool.

## ✨ Features

- 🖊 Create and write notes easily  
- 💾 Save notes persistently  
- ❌ Delete notes you no longer need  
- 🧠 Simple and intuitive UI  
- ⚡ Fast and responsive performance

## 📁 Project Structure
 ├── app/                 
 Main Android app module ├── gradle/            
 Gradle wrapper files ├── .idea/              
 IDE settings ├── build.gradle.kts    
 Root Gradle build file ├── settings.gradle.kts 
 Module settings ├── gradlew / .bat      
 Gradle wrapper scripts ├── gradle.properties    
 Gradle configuration └── .gitignore          
 Git ignore rules

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later  
- JDK 17+  
- Android SDK

### How to Run the App

1. *Clone the Repository*  
   ```bash
   git clone https://github.com/your-username/notes-app.git
   cd notes-app

2. *Open in Android Studio :*
      File → Open → Select the project root folder

3. *Build the App :*
     ./gradlew build

4. *Run the App :*
      Choose an emulator or connected device -> Click  Run in Android Studio

## Tech Stack
     -Kotlin
     -Android Jetpack
     -Room (if used for persistence)
     -ViewModel & LiveData (if applicable)
     -Gradle Kotlin DSL
