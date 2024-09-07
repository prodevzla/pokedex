<img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_1.png" alt="Sample Image" width="300" /> <img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_2.png" alt="Sample Image" width="300" /> <img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_3.png" alt="Sample Image" width="300" />
<img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_4.png" alt="Sample Image" width="300" /> <img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_5.png" alt="Sample Image" width="300" /> <img src="https://github.com/prodevzla/pokedex/blob/main/screenshots/pokedex_6.png" alt="Sample Image" width="300" />

This is a small Android project that have been developed for training purposes. The main goal is to build a Pokémon Pokédex using some of the latest technologies available for Android.

## Architecture
The architecture is MVVM + Clean. I have created 3 modules:

1) App module: The main module of the app. It contains all the UI logic (composables + viewmodels). 
2) Domain module: Contains all the business logic (use cases + domain models).
3) Data module: Responsible for data management. It contains a repository that pulls data from a free GraphQL instance and stores it in a local database using Room. It also contains mappers/transformers responsible for transforming entities and GraphQL models into domain models.

## Comments
1) Using shared element transitions between composables (Pokémon list and Pokémon details composables).
2) Using context receivers.
3) Using the latest beta version of the Navigation Compose library, which allows navigation between composables using objects and data classes instead of string paths.
4) Created a custom NavType to send the selected Pokémon instance to the details screen.
5) Using flows in the data and domain layers.
6) Using StateFlow in my viewmodels.
