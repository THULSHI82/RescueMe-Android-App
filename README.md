# RescueMe

RescueMe is an Android application that connects pet owners, animal shelters, adopters, and administrators in one local pet-care management system. The project was developed as an academic computing project using Java, XML, and SQLite.

## Main features

- Separate user, shelter, and administrator workflows
- User registration, login, and profile management
- Pet profiles and shelter management
- Pet adoption listings and requests
- Veterinary appointment booking
- Shelter reservations and donations
- Lost or vulnerable animal reporting
- Promotions and user-to-shelter enquiries
- Map-based discovery of shelters and veterinary services

## Technologies

- Java
- Android Studio and Android SDK
- XML layouts and Material Components
- SQLite with `SQLiteOpenHelper`
- Google Maps SDK for Android
- RecyclerView, Activities, Fragments, and View Binding

## Project structure

```text
app/src/main/java/com/example/rescueme1/
├── Admin/      # Administrator screens and management workflows
├── DB/         # SQLite helper and data models
├── Opening/    # Welcome, registration, and login screens
├── Shelter/    # Shelter dashboard and operational workflows
└── User/       # Pet owner and adopter workflows
```

## Run locally

1. Clone the repository and open it in Android Studio.
2. Allow Gradle to download and synchronize the dependencies.
3. Create a Google Maps API key in Google Cloud Console and enable the Maps SDK for Android.
4. Replace `YOUR_GOOGLE_MAPS_API_KEY` in `app/src/main/res/values/strings.xml` with your development key.
5. Run the app on an Android emulator or a device running Android 7.0 or later.

## Important note

This is an academic prototype. It stores application data locally with SQLite. A production release should use secure password hashing, protected secret management, a remote backend, and additional automated testing.

## Author

Thulshani Nawoda Dissanayaka
