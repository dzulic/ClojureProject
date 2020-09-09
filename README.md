# Clojure app for editing photos

The project was developed as part of the assignment for the course Software Engineering Tools and Methodology on Master's studies - Software Engineering and Computer Sciences at the Faculty of Organization Sciences, University of Belgrade, Serbia. 
The main goal of the application design is to allow users to store and manipulate their images. 

## Prerequisites
You will need Leiningen 2.0 or above and Postgres installed. 

## Installing
Create database "image_fixer" and schema "image_fixer"
Recomended settings:
> :host     "localhost"
  :port     5432
  :user     "postgres"
  :password "postgres"

Then, you need to change database parameters that is based in files from the configuration folder: 
>db-config.edn

On running of the application, the script for creating tables will be executed.


## Running
To start a web server for the application, run:

    lein ring server 


## Libraries
Project was developed using:
- Leiningen: Leiningen is a build automation and dependency management tool for the simple configuration of software projects written in the Clojure programming language
- Ring:  It's a lower-level framework to handle HTTP requests, with a focus on traditional web development. 
- Compojure: Compojure is a small routing library for Ring that allows web applications to be composed of small, independent parts.
- Hiccup: Hiccup is a library for representing HTML in Clojure. It uses vectors to represent elements, and maps to represent an element's attributes.
- Quin: Library for manipulating the images

## Project description

User mode consists of *Home page*, *Upload to gallery* page, and *Gallery* page. 

- Any user can create new user
- User can upload image
- User can access his gallery
- User can manipulate the image

When the user wants to exit the system, there is an option in the menu bar for logout.

## Built with
- IntelliJ Idea


## References
Clojure for the brave and true

https://www.baeldung.com/clojure-ring