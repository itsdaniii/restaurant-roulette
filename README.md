# Restaurant Roulette

### A personalized restaurant tracker with recommendation functionality

In a city with such an incredible food scene like Vancouver, there are always new restaurants popping up and I find myself frequently faced with two challenges: **(1)** remembering names of the restaurants when it comes time to make a reservation, and **(2)** coming to a group consensus on where to eat. As I know this is a struggle that is shared amongst many others, this application is intended to assist those who enjoy food, are indecisive about food, and want to track new and old restaurant favourites!

*Functionalities*

This application is designed to be a repository, or “tracker”, to hold a list of restaurants which can be easily referenced when setting up that get-together. Specifically, it has the following functions:
- Add restaurants to the list. Required metadata includes the restaurant name, cuisine type, neighbourhood, whether it has a patio or not (important to know during Covid-19), and price range
- Remove restaurants from the list
- View restaurant names in the list
- Select individual restaurant and view its details
- Provide a random restaurant recommendation from the list (Restaurant Roulette!)
- Persistence functionality to save list and re-load list in a separate session
- All functions available through the Main GUI or Console UI

***
*User Stories*:
1. As a user, I want to be able to add a restaurant to a list
   - Metadata = restaurant name, cuisine type, neighbourhood, patio (yes/no), price range
     - Define price ranges as:
       - $ = < $15 / person 
       - $$ = $15 – 30 / person 
       - $$$ = $30-50 / person 
       - $$$$ = $50+ / person

2. As a user, I want to be able to remove a restaurant from the same list
3. As a user, I want to be able to select a restaurant and view all its details (ie. its metadata)
4. As a user, I want to be able to view all the restaurant names in the list
5. As a user, I want to be able to save my restaurant list to file
6. As a user, I want to be able to load my restaurant list from file
7. As a user, I want to play Restaurant Roulette (ie. I want the program to suggest a random restaurant from my restaurant list)


*****
#### *UPCOMING IMPROVEMENTS* 
Aside from improving on the visual aesthetics of the UI and implementing input controls (patio field should be a checkbox/boolean, price range should be a drop-down list of options with the description of range definitions that existed in the console UI), there are some design choices which I would like to refactor including:

- **Single Responsibility Principle**: Extracting parts of the RestaurantAppGUI class which are unrelated to GUI set-up to improve the *cohesion* within RestaurantAppGUI, specifically:
  - Extracting the button functionality classes into new classes within the UI package (add, remove, save, load, restaurant roulette)
- Improve my *coupling* between classes by extracting all the print functions (including console action statements, ConsolePrinter, and GUI display) into a separate class so that modifications to Restaurant and RestaurantList don't trigger required updates to code in multiple classes  
- Could also modify RestaurantApp & RestaurantAppGUI classes so that they don't hold already hold a placeholder Restaurant "Anh and Chi," and instead initialize with an empty RestaurantList (not a design choice, but an implementation choice) 
