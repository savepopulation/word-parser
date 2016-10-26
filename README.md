# word-parser
Finds words in given url's html content. (Example implementation of MVP - Clean Architecture, Non-Blocking Ui)

Android Architecture:

MVP: Clean  Architecture
Fragments used as Views. Views implements View interfaces defined in Contract classes of related package. View Logic is seperated from fragments and managed by Presenters. Presenters implements Presenter interfaces defined in Contract classes of releated package.
Business logic also seperated from presenters with use-cases (interactors). Use-Cases defined in domain packages. Presenters and Domain(use-cases) are fully seperated from Android SDK. Main benefit of this approach is testablity. Business logic is seperated from ui and it’s easy to run ui and unit tests. Also project is easy to understand and maintain.

Command Pattern
Command Pattern is used to execute use-cases from off the main thread. Running business logic in off the main thread is a good solution for Android Apps for Non-Blocking UI applications. Worker threads managed with Thread Pool and execution of uses cases handled with UseCaseHandler class.



