# Library Management System

A NetBeans Java project for managing a library — books, members, and borrowing records.

## Structure

- `src/schema/` — domain model classes (`book`, `Novel`, `Comic`, `Textbook`, `Others`, `Member`, `Borrowing`)
- `src/runtime/Main.java` — application entry point
- `src/lab/pkg01/LAB01.java` — lab entry class
- `build.xml`, `manifest.mf`, `nbproject/` — NetBeans / Ant build configuration

## Build & run

Open the project in NetBeans, or build with Ant:

```sh
ant clean jar
ant run
```
