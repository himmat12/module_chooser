# Module Chooser Application

## Overview
A JavaFX-based module selection system implementing Object-Oriented Design principles. This application allows computing students to create profiles and select their final year modules based on their course of study.

## Features
- Create student profile
- Select optional and reserved modules along with mandatory modules
- Generate student profile overview
- Save student profile overview into file
- Save student profile data
- Retrieve previously saved student profile data
- Load course data from text file

## Course Module Structure
- Computer Science and Software Engineering courses supported
- 120 total credits required
- 90 compulsory credits for both courses
- Block/Semester-based module organization
- Reserve module selection system

## Module Selection Rules
- Compulsory modules are automatically assigned based on course
- Optional modules must be selected according to course requirements
- One reserve module must be selected from available options
- Total credits must equal 120
- Modules are organized by blocks and semesters


## Note
> The courses data in the file should follow the standard input data schema as the line of string separated by comma as shown below:
'Course name, Module code, Module name, Module credits, Is mandatory, Run plan'. Check the 'courses.txt' file to see the courses and modules data which was loaded into the application dropdown field.

## Data Management
- Profile data can be saved and loaded
- Course data is loaded from structured text files
- Overview generation for selected modules
- Validation for module selection rules

## Technical Stack
- Java JDK 17 (LTS)
- JavaFX
- Eclipse IDE
- MVC Architecture Pattern

## System Requirements
- Java JDK 17 or higher
- IDE supporting JavaFX development (e.g., Eclipse, IntelliJ)
- Properly configured Java environment variables

## Setup Instructions
1. Download and install JDK 17 from [BellSoft](https://bell-sw.com/pages/downloads/#jdk-17-lts)
2. Install Eclipse IDE from [Eclipse Downloads](https://www.eclipse.org/downloads/)
3. Configure Java environment variables
4. Import project into Eclipse
5. Run the application

## Screenshots
### Main Interface & Module Selection
![Main Interface](https://github.com/user-attachments/assets/fcbfc421-8f57-4c0a-9d84-f50bf4dd9ad2)
![Module Selection](https://github.com/user-attachments/assets/a8de4ec9-aa4d-45ca-af69-3efb76aaea53)
![Compulsory Modules](https://github.com/user-attachments/assets/8eee1fc5-7468-466c-936b-388f65ebc351)
![Optional Modules](https://github.com/user-attachments/assets/d1ea4a59-179d-4670-9899-736e3f571d83)

### Profile Management & Overview
![Profile Creation](https://github.com/user-attachments/assets/67be328f-b0cb-41f4-ade8-5afaf8480016)
![Profile Overview](https://github.com/user-attachments/assets/0b3422bf-dd84-41fd-9416-412d7e79047b)
![Save Profile](https://github.com/user-attachments/assets/23cb0547-976d-49b0-af0e-f709b4c40dcf)
![Load Profile](https://github.com/user-attachments/assets/23c009fb-c40a-4836-af77-2c69709e3f13)

