#  Covid-19 Tracker Application
This is an android application for tracking covid-19 cases worldwide.

- On home screen user have two options Global and Favourites) at bottom tab navigator.
- First option (Global) will be by default selected which will have a list of countries and a summary of cases with them fetched from covid API (https://covid19api.com). Each country will have its own flag image fetched from this API (https://www.countryflags.io/).
- User will have a search option to search for a particular country.
- Option to sort countries based on Confirmed, Deaths and active cases is provided.
- Clicking on any country will navigate user to details activity screen which is right now in progress.
- On the details screen at the top, a star option to favourite this country is there, doing that will save this country on the local database. The list of favourite countries can be accessed from favourite fragment which is option two of bottom tab navigator.

## Demo
https://github.com/jsanyam123/Covid-19-tracker-app/assets/87381556/47d3d947-3749-4a1d-a458-cc6f825f4749

## Requirements:

- Use the https://covid19api.com/ to show a list of search results of countries and relevant covid19 statistics pertaining to the country. The link contains all the required APIs for achieving the same.
- Use Kotlin as a programming language.
- Use the Architecture and Design Pattern of your choice (Please do specify the reason for your choice when you submit the assignment).
- Implement a Search to search for a country with Search suggestions as user type.
- Each list item should contain an image of the country flag, if not all of the countries.
- Clicking a list item launches the respective information view on the covid statistics, use appropriate graphs.
- Save the items visited by the user to a local database and should be shown in Home Screen every time the user visits the App.
- Keeping the code updated with the latest Framework APIs and Components & inclusive of solutions like Data binding, Dependency Injection, Test coverage.
- The UI specification is left to you. Use appropriate UX and UI widgets to give the best experience as you see fit.You can choose any more features you think will be good to have (not mandatory)
- Sorting the countries based on deaths, total and confirmed cases.
- Other countries we can just show the graph.
- India we can show states and cases within again sorting needs to be there.
