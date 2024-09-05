# List Reader

This is a test project. It reads a file from a server and displays it on the screen.


## Description

The project is a single activity application implemented using Jetpack Compose. 

The dependency injection was handled with Hilt. The data source for the list is a remote collection fetched with Retrofit. It can be easily replaced with a local mock for debugging purposes.

The project also handles edge cases of an empty list or unexpected data format. 

## Dependencies

- Gradle v8.4
- Jetpack Compose
- Retrofit
- Hilt
- Material 3


## Known Issues

- No test coverage
- No refreshing on error
- No error handling by scenario (i.e. network error, fatal error, page not found, device not supported etc.)
- Sorting description is ambiguous. The description of the test task does not specify the way in which the name property should be sorted. There are two ways to do so: by characters (scaleable), or by numbers in the item name (will break in the scenario where the developer will decide to change the naming). For example, in the former case the sorting result will be: "Item 376", "Item 39", "Item 395". In the later it will be: "Item 39", "Item 376", "Item 395". The former was chosen.

## Authors

- Margo Krylova
