package com.example.rescueme1.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RescueMe.db";
    private static final int DATABASE_VERSION = 11;


    public static final String TABLE_APPUSER = "appuser";
    public static final String COLUMN_APPUSER_ID = "id";
    public static final String COLUMN_APPUSER_EMAIL = "email";
    public static final String COLUMN_APPUSER_TYPE = "user_type";



    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "uid";
    public static final String COLUMN_APPUSER_ID_UF = "appuser_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_CONTACT = "user_contact";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_PROFILE_IMAGE = "profile_image";


    public static final String TABLE_ADMIN = "admin";
    public static final String COLUMN_ADMIN_NAME = "admin_name";
    public static final String COLUMN_ADMIN_ID = "aid";
    public static final String COLUMN_ADMIN_EMAIL = "admin_email";
    public static final String COLUMN_APPUSER_ID_AF = "appuser_id";
    public static final String COLUMN_ADMIN_CONTACT = "admin_contact";
    public static final String COLUMN_ADMIN_PASSWORD = "admin_password";


    public static final String TABLE_SHELTER = "shelter";
    public static final String COLUMN_SHELTER_ID = "sid";
    public static final String COLUMN_APPUSER_ID_SF = "appuser_id";
    public static final String COLUMN_SHELTER_NAME = "shelter_name";
    public static final String COLUMN_OWNER_NAME = "owner_name";
    public static final String COLUMN_SHELTER_EMAIL = "shelter_email";
    public static final String COLUMN_SHELTER_CONTACT = "shelter_contact";
    public static final String COLUMN_SHELTER_LAT = "lat";
    public static final String COLUMN_SHELTER_LON = "lon";
    public static final String COLUMN_REGISTRATION_NUMBER = "registration_number";
    public static final String COLUMN_ESTABLISHED_DATE = "established_date";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SHELTER_PASSWORD = "shelter_password";
    public static final String COLUMN_PROFILE_SHELTER_IMAGE = "profile_shelter_image";



    public static final String TABLE_PET = "pet";
    public static final String COLUMN_PET_ID = "pid";
    public static final String COLUMN_SHELTER_ID_SF = "shelter_id";
    public static final String COLUMN_PROFILE_PET_IMAGE = "profile_pet_image";
    public static final String COLUMN_PET_CATEGORY = "category";
    public static final String COLUMN_PET_NAME = "pet_name";
    public static final String COLUMN_PET_AGE = "pet_age";
    public static final String COLUMN_PET_GENDER = "pet_gender";
    public static final String COLUMN_PET_DESCRIPTION = "pet_description";


    public static final String TABLE_ADOPTION = "adoption";
    public static final String COLUMN_ADOPTION_ID = "adoption_id";
    public static final String COLUMN_PET_ID_PF = "pet_id";
    public static final String COLUMN_SHELTER_ID_PF = "shelter_id";
    public static final String COLUMN_PROFILE_ADOPTION_IMAGE = "profile_adoption_image";
    public static final String COLUMN_ADOPTION_PET_CATEGORY = "adoption_category";
    public static final String COLUMN_ADOPTION_PET_NAME = "adoption_pet_name";
    public static final String COLUMN_ADOPTION_PET_AGE = "adoption_pet_age";
    public static final String COLUMN_ADOPTION_PET_GENDER = "adoption_pet_gender";
    public static final String COLUMN_ADOPTION_PET_DESCRIPTION = "adoption_pet_description";
    public static final String COLUMN_ADOPTION_PHOTO = "adoption_photo";
    public static final String COLUMN_ADOPTION_NAME = "adoption_name";
    public static final String COLUMN_ADOPTION_AGE = "adoption_age";
    public static final String COLUMN_ADOPTION_GENDER = "adoption_gender";
    public static final String COLUMN_ADOPTION_CONTACT = "adoption_contact";
    public static final String COLUMN_ADOPTION_NIC = "adoption_nic";
    public static final String COLUMN_ADOPTION_ADDRESS = "adoption_address";
    public static final String COLUMN_ADOPTION_DATE = "adoption_date";



    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String COLUMN_APPOINTMENT_ID = "appointment_id";
    public static final String COLUMN_APPOINTMENT_PET_ID = "pet_fk_id";
    public static final String COLUMN_APPOINTMENT_USER_ID = "user_fk_id";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String COLUMN_APPOINTMENT_TIME = "appointment_time";
    public static final String COLUMN_APPOINTMENT_STATUS = "appointment_status";


    public static final String TABLE_UHELP = "uhelp";
    public static final String COLUMN_UHELP_ID = "uhelp_id";
    public static final String COLUMN_UHELP_NAME = "uname_id";
    public static final String COLUMN_UHELP_CONTACT = "ucontact_id";
    public static final String COLUMN_UHELP_MESSAGE = "umessage_id";
    public static final String COLUMN_UHELP_DATE = "udate_id";
    public static final String COLUMN_UHELP_TIME = "utime_id";


    public static final String TABLE_SHELP = "shelp";
    public static final String COLUMN_SHELP_ID = "shelp_id";
    public static final String COLUMN_SHELP_NAME = "sname_id";
    public static final String COLUMN_SHELP_CONTACT = "scontact_id";
    public static final String COLUMN_SHELP_MESSAGE = "smessage_id";
    public static final String COLUMN_SHELP_DATE = "sdate_id";
    public static final String COLUMN_SHELP_TIME = "stime_id";

    private static final String TABLE_REPORT = "report";
    private static final String TABLE_DONATION = "donation";
    private static final String TABLE_MYPET = "MyPet";
    private static final String TABLE_RESERVATION = "reservation";

    public static final String TABLE_PROMOTIONS = "promotions";
    public static final String COLUMN_PROMO_ID = "promo_id";
    public static final String COLUMN_PROMO_TITLE = "title";
    public static final String COLUMN_PROMO_IMAGE = "image";

    private Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createAppUserTable = "CREATE TABLE " + TABLE_APPUSER + " (" +
                COLUMN_APPUSER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPUSER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COLUMN_APPUSER_TYPE + " TEXT NOT NULL CHECK(" + COLUMN_APPUSER_TYPE + " IN ('a', 's', 'u'))" +
                ");";
        db.execSQL(createAppUserTable);

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPUSER_ID_UF + " INTEGER NOT NULL, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                COLUMN_USER_CONTACT + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_PROFILE_IMAGE + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_APPUSER_ID_UF + ") REFERENCES " + TABLE_APPUSER + "(" + COLUMN_APPUSER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createUsersTable);


        String createAdminTable = "CREATE TABLE " + TABLE_ADMIN + " (" +
                COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPUSER_ID_AF + " INTEGER NOT NULL, " +
                COLUMN_ADMIN_NAME + " TEXT, " +
                COLUMN_ADMIN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_ADMIN_CONTACT + " TEXT, " +
                COLUMN_ADMIN_PASSWORD + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_APPUSER_ID_AF + ") REFERENCES " + TABLE_APPUSER + "(" + COLUMN_APPUSER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createAdminTable);


        String createShelterTable = "CREATE TABLE " + TABLE_SHELTER + " (" +
                COLUMN_SHELTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPUSER_ID_SF + " INTEGER NOT NULL, " +
                COLUMN_SHELTER_NAME + " TEXT NOT NULL, " +
                COLUMN_OWNER_NAME + " TEXT, " +
                COLUMN_SHELTER_EMAIL + " TEXT UNIQUE, " +
                COLUMN_SHELTER_CONTACT + " TEXT, " +
                COLUMN_SHELTER_LAT + " TEXT, " +
                COLUMN_SHELTER_LON + " TEXT, " +
                COLUMN_REGISTRATION_NUMBER + " TEXT, " +
                COLUMN_ESTABLISHED_DATE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SHELTER_PASSWORD + " TEXT, " +
                COLUMN_PROFILE_SHELTER_IMAGE + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_APPUSER_ID_SF + ") REFERENCES " + TABLE_APPUSER + "(" + COLUMN_APPUSER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createShelterTable);

        String createPetTable = "CREATE TABLE " + TABLE_PET + " (" +
                COLUMN_PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SHELTER_ID_SF + " INTEGER NOT NULL, " +
                COLUMN_PROFILE_PET_IMAGE + " BLOB, " +
                COLUMN_PET_CATEGORY + " TEXT, " +
                COLUMN_PET_NAME + " TEXT, " +
                COLUMN_PET_AGE + " TEXT, " +
                COLUMN_PET_GENDER + " TEXT, " +
                COLUMN_PET_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_SHELTER_ID_SF + ") REFERENCES " + TABLE_SHELTER + "(" + COLUMN_SHELTER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createPetTable);

        String createAdoptionTable = "CREATE TABLE " + TABLE_ADOPTION + " (" +
                COLUMN_ADOPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PET_ID_PF + " INTEGER NOT NULL, " +
                COLUMN_SHELTER_ID_PF + " INTEGER NOT NULL, " +
                COLUMN_PROFILE_ADOPTION_IMAGE + " BLOB, " +
                COLUMN_ADOPTION_PET_CATEGORY + " TEXT, " +
                COLUMN_ADOPTION_PET_NAME + " TEXT, " +
                COLUMN_ADOPTION_PET_AGE + " TEXT, " +
                COLUMN_ADOPTION_PET_GENDER + " TEXT, " +
                COLUMN_ADOPTION_PET_DESCRIPTION + " TEXT, " +
                COLUMN_ADOPTION_PHOTO + " BLOB, " +
                COLUMN_ADOPTION_NAME + " TEXT, " +
                COLUMN_ADOPTION_AGE + " TEXT, " +
                COLUMN_ADOPTION_GENDER + " TEXT, " +
                COLUMN_ADOPTION_CONTACT + " TEXT, " +
                COLUMN_ADOPTION_NIC + " TEXT, " +
                COLUMN_ADOPTION_ADDRESS + " TEXT, " +
                COLUMN_ADOPTION_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PET_ID_PF + ") REFERENCES " + TABLE_PET + "(" + COLUMN_PET_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_SHELTER_ID_PF + ") REFERENCES " + TABLE_PET + "(" + COLUMN_SHELTER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createAdoptionTable);

        String createAppointmentTable = "CREATE TABLE " + TABLE_APPOINTMENT + " (" +
                COLUMN_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPOINTMENT_PET_ID + " INTEGER NOT NULL, " +
                COLUMN_APPOINTMENT_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_APPOINTMENT_DATE + " TEXT, " +
                COLUMN_APPOINTMENT_TIME + " TEXT, " +
                COLUMN_APPOINTMENT_STATUS + " TEXT DEFAULT 'pending' CHECK(" + COLUMN_APPOINTMENT_STATUS + " IN ('pending', 'approved', 'rejected', 'completed')), " +
                "FOREIGN KEY(" + COLUMN_APPOINTMENT_PET_ID + ") REFERENCES " + TABLE_PET + "(" + COLUMN_PET_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_APPOINTMENT_USER_ID + ") REFERENCES " + TABLE_SHELTER + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE" +
                ");";
        db.execSQL(createAppointmentTable);


        String createUHelpTable = "CREATE TABLE " + TABLE_UHELP + " (" +
                COLUMN_UHELP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UHELP_NAME + " TEXT, " +
                COLUMN_UHELP_CONTACT + " TEXT, " +
                COLUMN_UHELP_MESSAGE + " TEXT, " +
                COLUMN_UHELP_DATE + " TEXT, " +
                COLUMN_UHELP_TIME + " TEXT);";

        db.execSQL(createUHelpTable);

        String createSHelpTable = "CREATE TABLE " + TABLE_SHELP + " (" +
                COLUMN_SHELP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SHELP_NAME + " TEXT, " +
                COLUMN_SHELP_CONTACT + " TEXT, " +
                COLUMN_SHELP_MESSAGE + " TEXT, " +
                COLUMN_SHELP_DATE + " TEXT, " +
                COLUMN_SHELP_TIME + " TEXT);";

        db.execSQL(createSHelpTable);

        String createPromotionsTable = "CREATE TABLE " + TABLE_PROMOTIONS + " (" +
                COLUMN_PROMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PROMO_TITLE + " TEXT NOT NULL, " +
                COLUMN_PROMO_IMAGE + " BLOB);";


        db.execSQL(createPromotionsTable);


        String createpetdetailreportTableQuery = "CREATE TABLE " + TABLE_REPORT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pet_category TEXT, " +
                "shelter_id INTEGER, " +
                "appuser_id INTEGER, " +
                "description TEXT, " +
                "latitude REAL, " +
                "longitude REAL," +
                "petimage BLOB," +
                "report_status TEXT," +
                "FOREIGN KEY(shelter_id) REFERENCES shelter(sid) ON DELETE CASCADE, " +
                "FOREIGN KEY(appuser_id) REFERENCES appuser(appuser_id) ON DELETE CASCADE" +
                ");";
        db.execSQL(createpetdetailreportTableQuery);

        String createDonationTable = "CREATE TABLE " + TABLE_DONATION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "shelter_id INTEGER, " +
                "user_id INTEGER, " +
                "first_name TEXT, " +
                "last_name TEXT, " +
                "amount REAL, " +
                "remark TEXT," +
                "FOREIGN KEY(shelter_id) REFERENCES shelter(sid), " +
                "FOREIGN KEY(user_id) REFERENCES users(uid)" +
                ");";
        db.execSQL(createDonationTable);

        String createMyPetTable = "CREATE TABLE " + TABLE_MYPET + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "pet_name TEXT, " +
                "category TEXT, " +
                "dob TEXT, " +
                "vaccinated TEXT, " +
                "vaccines TEXT, " +
                "vaccine_date TEXT, " +
                "exercise_reminder INTEGER, " +
                "meal_reminder INTEGER, " +
                "pet_image BLOB, " +
                "days_remaining INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES users(uid))";
        db.execSQL(createMyPetTable);

        String createReservationTable = "CREATE TABLE " + TABLE_RESERVATION + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pet_id INTEGER NOT NULL, " +
                "user_id INTEGER NOT NULL, " +
                "shelter_id INTEGER NOT NULL, " +
                "address TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "amount REAL, " +
                "FOREIGN KEY(pet_id) REFERENCES MyPet(id), " +
                "FOREIGN KEY(user_id) REFERENCES users(uid), " +
                "FOREIGN KEY(shelter_id) REFERENCES Shelter(sid))";
        db.execSQL(createReservationTable);



        insertDefaultAppUsers(db);
        insertDefaultAdmins(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADOPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPUSER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UHELP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYPET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONS);
        onCreate(db);
    }


    private void insertDefaultAppUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();


        values.put(COLUMN_APPUSER_EMAIL, "admin@gmail.com");
        values.put(COLUMN_APPUSER_TYPE, "a");
        db.insert(TABLE_APPUSER, null, values);

    }


    private void insertDefaultAdmins(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_APPUSER_ID_AF, 1);
        values.put(COLUMN_ADMIN_NAME, "Admin");
        values.put(COLUMN_ADMIN_EMAIL, "admin@gmail.com");
        values.put(COLUMN_ADMIN_CONTACT, "0714234321");
        values.put(COLUMN_ADMIN_PASSWORD, "123");
        db.insert(TABLE_ADMIN, null, values);
    }

    public boolean insertUser(String name, String email, String contact, String dob, String gender, String password, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues appUserValues = new ContentValues();
        appUserValues.put("email", email);
        appUserValues.put("user_type", "u");
        long appUserId = db.insert("appuser", null, appUserValues);

        if (appUserId == -1) {
            return false;
        }


        ContentValues userValues = new ContentValues();
        userValues.put("appuser_id", appUserId);
        userValues.put("user_name", name);
        userValues.put("user_email", email);
        userValues.put("user_contact", contact);
        userValues.put("dob", dob);
        userValues.put("gender", gender);
        userValues.put("user_password", password);
        userValues.put("profile_image", image);

        long userResult = db.insert("users", null, userValues);
        return userResult != -1;
    }

    public UserModel getyuUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " =?", new String[]{id});

        if (cursor != null && cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CONTACT));
            byte[] profileImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE));

            cursor.close();
            return new UserModel(name, email, dob, contact, profileImage);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public boolean insertShelter(String name, String owner, String email, String contact,
                                 String lat, String lon, String regNumber, String estDate,
                                 String description, String password, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_SHELTER,
                new String[]{COLUMN_REGISTRATION_NUMBER},
                COLUMN_REGISTRATION_NUMBER + "=?",
                new String[]{regNumber},
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        if (cursor != null) {
            cursor.close();
        }

        ContentValues appUserValues = new ContentValues();
        appUserValues.put(COLUMN_APPUSER_EMAIL, email);
        appUserValues.put(COLUMN_APPUSER_TYPE, "s");

        long appUserId = db.insert(TABLE_APPUSER, null, appUserValues);
        if (appUserId == -1) return false;

        ContentValues shelterValues = new ContentValues();
        shelterValues.put(COLUMN_APPUSER_ID_SF, appUserId);
        shelterValues.put(COLUMN_SHELTER_NAME, name);
        shelterValues.put(COLUMN_OWNER_NAME, owner);
        shelterValues.put(COLUMN_SHELTER_EMAIL, email);
        shelterValues.put(COLUMN_SHELTER_CONTACT, contact);
        shelterValues.put(COLUMN_SHELTER_LAT, lat);
        shelterValues.put(COLUMN_SHELTER_LON, lon);
        shelterValues.put(COLUMN_REGISTRATION_NUMBER, regNumber);
        shelterValues.put(COLUMN_ESTABLISHED_DATE, estDate);
        shelterValues.put(COLUMN_DESCRIPTION, description);
        shelterValues.put(COLUMN_SHELTER_PASSWORD, password);
        shelterValues.put(COLUMN_PROFILE_SHELTER_IMAGE, image);

        long result = db.insert(TABLE_SHELTER, null, shelterValues);
        return result != -1;
    }

    public Cursor getAllShelters() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHELTER, null);
    }

    // Get shelter ID using appointment ID
    public int getShelterIdByAppointmentId(int appointmentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT pet.shelter_id FROM pet " +
                "JOIN appointment ON pet.pid = appointment.pet_fk_id " +
                "WHERE appointment.appointment_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(appointmentId)});
        int shelterId = -1;
        if (cursor.moveToFirst()) {
            shelterId = cursor.getInt(0);
        }
        cursor.close();
        return shelterId;
    }

    public String[] getShelterLatLonById(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT lat, lon FROM shelter WHERE sid = ?", new String[]{String.valueOf(shelterId)});
        if (cursor.moveToFirst()) {
            String lat = cursor.getString(0);
            String lon = cursor.getString(1);
            cursor.close();
            return new String[]{lat, lon};
        }
        cursor.close();
        return null;
    }


    public Cursor getShelterById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SHELTER + " WHERE " + COLUMN_SHELTER_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<String> getAllShelterNames() {
        List<String> shelterNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_SHELTER_NAME + " FROM " + TABLE_SHELTER, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_NAME));
                shelterNames.add(name);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return shelterNames;
    }




    public boolean updateShelter(int id, String name, String owner, String email, String contact,
                                 String lat, String lon, String regNo, String estDate,
                                 String desc, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHELTER_NAME, name);
        values.put(COLUMN_OWNER_NAME, owner);
        values.put(COLUMN_SHELTER_EMAIL, email);
        values.put(COLUMN_SHELTER_CONTACT, contact);
        values.put(COLUMN_SHELTER_LAT, lat);
        values.put(COLUMN_SHELTER_LON, lon);
        values.put(COLUMN_REGISTRATION_NUMBER, regNo);
        values.put(COLUMN_ESTABLISHED_DATE, estDate);
        values.put(COLUMN_DESCRIPTION, desc);
        values.put(COLUMN_SHELTER_PASSWORD, password);

        return db.update(TABLE_SHELTER, values, COLUMN_SHELTER_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }


    public boolean deleteShelter(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SHELTER, COLUMN_SHELTER_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public List<ShelterItem> getAllShelterItems() {
        List<ShelterItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sid, shelter_name FROM shelter", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("sid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                list.add(new ShelterItem(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }


    public boolean insertPet(int shelterId, byte[] profileImage, String category, String petName,
                             String petAge, String petGender, String petDescription) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("shelter_id", shelterId);
        values.put("profile_pet_image", profileImage);
        values.put("category", category);
        values.put("pet_name", petName);
        values.put("pet_age", petAge);
        values.put("pet_gender", petGender);
        values.put("pet_description", petDescription);

        long result = db.insert("pet", null, values);
        return result != -1;
    }

    public List<PetModel> getAllPetsByShelterId(int shelterId) {
        List<PetModel> petList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pet WHERE shelter_id = ?", new String[]{String.valueOf(shelterId)});

        if (cursor.moveToFirst()) {
            do {
                int petId = cursor.getInt(cursor.getColumnIndexOrThrow("pid"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("profile_pet_image"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
                String age = cursor.getString(cursor.getColumnIndexOrThrow("pet_age"));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow("pet_gender"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("pet_description"));

                PetModel pet = new PetModel(petId, shelterId, image, category, name, age, gender, description);
                petList.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return petList;
    }



    public PetModel getPetById(int petId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PET, null, COLUMN_PET_ID + "=?", new String[]{String.valueOf(petId)}, null, null, null);

        PetModel pet = null;
        if (cursor != null && cursor.moveToFirst()) {
            pet = new PetModel();
            pet.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)));
            pet.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME)));
            pet.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_CATEGORY)));
            pet.setPetAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE)));
            pet.setPetGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER)));
            pet.setPetDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_DESCRIPTION)));
            pet.setProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PET_IMAGE)));
            cursor.close();
        }
        return pet;
    }


    public boolean updatePet(PetModel pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, pet.getPetName());
        values.put(COLUMN_PET_CATEGORY, pet.getCategory());
        values.put(COLUMN_PET_AGE, pet.getPetAge());
        values.put(COLUMN_PET_GENDER, pet.getPetGender());
        values.put(COLUMN_PET_DESCRIPTION, pet.getPetDescription());
        values.put(COLUMN_PROFILE_PET_IMAGE, pet.getProfileImage());

        int rows = db.update(TABLE_PET, values, COLUMN_PET_ID + "=?", new String[]{String.valueOf(pet.getPetId())});
        return rows > 0;
    }



    public boolean deletePet(int petId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_PET, COLUMN_PET_ID + "=?", new String[]{String.valueOf(petId)});
        return rows > 0;
    }




    public List<SPetModel> getAllPets() {
        List<SPetModel> petList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p." + COLUMN_PET_ID + ", p." + COLUMN_PET_NAME + ", p." + COLUMN_PET_CATEGORY + ", " +
                "p." + COLUMN_PET_AGE + ", p." + COLUMN_PET_GENDER + ", s.shelter_name, s.shelter_contact, " +
                "p." + COLUMN_PROFILE_PET_IMAGE + ", p." + COLUMN_PET_DESCRIPTION +
                " FROM " + TABLE_PET + " p INNER JOIN " + TABLE_SHELTER + " s ON p." + COLUMN_SHELTER_ID_SF + " = s." + COLUMN_SHELTER_ID;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int petId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID));
                String petName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME));
                String petCategory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_CATEGORY));
                String petAge = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE));
                String petGender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER));
                String shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                String shelterContact = cursor.getString(cursor.getColumnIndexOrThrow("shelter_contact"));
                byte[] petImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PET_IMAGE));
                String petDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_DESCRIPTION));

                petList.add(new SPetModel(petId, petName, petCategory, petAge, petGender,
                        shelterName, shelterContact, petImage, petDescription));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return petList;
    }


    public List<PetModel> getUPetsByShelterId(int shelterId) {
        List<PetModel> petList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PET + " WHERE " + COLUMN_SHELTER_ID_SF + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});

        if (cursor.moveToFirst()) {
            do {
                PetModel pet = new PetModel();
                pet.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)));
                pet.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME)));
                pet.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_CATEGORY)));
                pet.setPetAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE)));
                pet.setPetGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER)));
                pet.setPetDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_DESCRIPTION)));

                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PET_IMAGE));
                pet.setProfileImage(image);

                petList.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return petList;
    }



    public boolean insertAdoptionAndDeletePet(
            PetModel pet,
            byte[] adopterPhoto,
            String adopterName,
            String adopterAge,
            String adopterGender,
            String adopterContact,
            String adopterNic,
            String adopterAddress,
            String adoptionDate,
            int shelterId
    )
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADOPTION_NAME, adopterName);
        values.put(COLUMN_ADOPTION_AGE, adopterAge);
        values.put(COLUMN_ADOPTION_GENDER, adopterGender);
        values.put(COLUMN_ADOPTION_CONTACT, adopterContact);
        values.put(COLUMN_ADOPTION_NIC, adopterNic);
        values.put(COLUMN_ADOPTION_ADDRESS, adopterAddress);
        values.put(COLUMN_ADOPTION_PHOTO, adopterPhoto);
        values.put(COLUMN_ADOPTION_DATE, adoptionDate);


        values.put(COLUMN_PET_ID_PF, pet.getPetId());
        values.put(COLUMN_ADOPTION_PET_NAME, pet.getPetName());
        values.put(COLUMN_ADOPTION_PET_CATEGORY, pet.getCategory());
        values.put(COLUMN_ADOPTION_PET_AGE, pet.getPetAge());
        values.put(COLUMN_ADOPTION_PET_GENDER, pet.getPetGender());
        values.put(COLUMN_ADOPTION_PET_DESCRIPTION, pet.getPetDescription());
        values.put(COLUMN_PROFILE_ADOPTION_IMAGE, pet.getProfileImage());


        values.put(COLUMN_SHELTER_ID_PF, shelterId);

        long result = db.insert(TABLE_ADOPTION, null, values);

        if (result != -1) {
            db.delete(TABLE_PET, COLUMN_PET_ID + " = ?", new String[]{String.valueOf(pet.getPetId())});
            db.delete(TABLE_APPOINTMENT, "pet_fk_id = ?", new String[]{String.valueOf(pet.getPetId())});
            return true;
        } else {
            return false;
        }
    }


    public List<AdoptionModel> getAdoptionsByShelter(int shelterId) {
        List<AdoptionModel> adoptionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ADOPTION + " WHERE " + COLUMN_SHELTER_ID_PF + " = ?",
                new String[]{String.valueOf(shelterId)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                AdoptionModel model = new AdoptionModel();

                model.setAdoptionId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ID)));
                model.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID_PF)));
                model.setShelterId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_ID_PF)));
                model.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_NAME)));
                model.setPetProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_ADOPTION_IMAGE)));
                model.setAdopterPhoto(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PHOTO)));
                model.setAdopterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NAME)));
                model.setAdopterAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_AGE)));
                model.setAdopterGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_GENDER)));
                model.setAdopterContact(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_CONTACT)));
                model.setAdopterNIC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NIC)));
                model.setAdopterAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ADDRESS)));
                model.setAdoptionDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_DATE)));

                adoptionList.add(model);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return adoptionList;
    }


    public AdoptionModel getAdoptionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADOPTION + " WHERE " + COLUMN_ADOPTION_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            AdoptionModel model = new AdoptionModel();
            model.setAdoptionId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ID)));
            model.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID_PF)));
            model.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_NAME)));

            model.setPetProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_ADOPTION_IMAGE)));  // Added this line
            model.setAdopterPhoto(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PHOTO)));

            model.setAdopterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NAME)));
            model.setAdopterAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_AGE)));
            model.setAdopterGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_GENDER)));
            model.setAdopterContact(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_CONTACT)));
            model.setAdopterNIC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NIC)));
            model.setAdopterAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ADDRESS)));
            model.setAdoptionDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_DATE)));

            cursor.close();
            return model;
        }

        cursor.close();
        return null;
    }



    public boolean updateAdoption(AdoptionModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADOPTION_NAME, model.getAdopterName());
        values.put(COLUMN_ADOPTION_AGE, model.getAdopterAge());
        values.put(COLUMN_ADOPTION_GENDER, model.getAdopterGender());
        values.put(COLUMN_ADOPTION_CONTACT, model.getAdopterContact());
        values.put(COLUMN_ADOPTION_NIC, model.getAdopterNIC());
        values.put(COLUMN_ADOPTION_ADDRESS, model.getAdopterAddress());
        values.put(COLUMN_ADOPTION_DATE, model.getAdoptionDate());

        if (model.getAdopterPhoto() != null) {
            values.put(COLUMN_ADOPTION_PHOTO, model.getAdopterPhoto());
        }

        int rows = db.update(TABLE_ADOPTION, values, COLUMN_ADOPTION_ID + " = ?", new String[]{String.valueOf(model.getAdoptionId())});
        return rows > 0;
    }





    public List<AdoptionModel> getAllAdoptions() {
        List<AdoptionModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADOPTION, null);

        if (cursor.moveToFirst()) {
            do {
                AdoptionModel model = new AdoptionModel();
                model.setAdoptionId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ID)));
                model.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID_PF)));
                model.setShelterId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_ID_PF)));
                model.setPetProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_ADOPTION_IMAGE)));
                model.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_NAME)));
                model.setAdopterPhoto(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PHOTO)));
                model.setAdopterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NAME)));
                model.setAdopterAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_AGE)));
                model.setAdopterGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_GENDER)));
                model.setAdopterContact(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_CONTACT)));
                model.setAdopterNIC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NIC)));
                model.setAdopterAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ADDRESS)));
                model.setAdoptionDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_DATE)));
                list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }


    public int getAdoptionCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ADOPTION, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }


    public AdoptionModel getAAdoptionById(int adoptionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        AdoptionModel model = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADOPTION + " WHERE " + COLUMN_ADOPTION_ID + "=?", new String[]{String.valueOf(adoptionId)});

        if (cursor.moveToFirst()) {
            model = new AdoptionModel();

            model.setAdoptionId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ID)));
            model.setPetId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID_PF)));
            model.setShelterId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_ID_PF)));
            model.setPetProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_ADOPTION_IMAGE)));

            model.setPetCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_CATEGORY)));
            model.setPetName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_NAME)));
            model.setPetAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_AGE)));
            model.setPetGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_GENDER)));
            model.setPetDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PET_DESCRIPTION)));

            model.setAdopterPhoto(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_PHOTO)));
            model.setAdopterName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NAME)));
            model.setAdopterAge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_AGE)));
            model.setAdopterGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_GENDER)));
            model.setAdopterContact(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_CONTACT)));
            model.setAdopterNIC(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_NIC)));
            model.setAdopterAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_ADDRESS)));
            model.setAdoptionDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADOPTION_DATE)));
        }

        cursor.close();
        db.close();

        return model;
    }




    public int getCount(String tableName) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        switch (tableName) {
            case "shelter":
            case "pet":
            case "adoption":
            case "users":
                Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
                cursor.close();
                break;
        }

        return count;
    }



    public boolean insertAppointment(int userId, int petId, String date, String time, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_APPOINTMENT_USER_ID, userId);
        cv.put(COLUMN_APPOINTMENT_PET_ID, petId);
        cv.put(COLUMN_APPOINTMENT_DATE, date);
        cv.put(COLUMN_APPOINTMENT_TIME, time);
        cv.put(COLUMN_APPOINTMENT_STATUS, status);

        long result = db.insert(TABLE_APPOINTMENT, null, cv);
        return result != -1;
    }


    public ArrayList<AppointmentModel> getAppointmentsByUserId(int userId) {
        ArrayList<AppointmentModel> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "appointment." + COLUMN_APPOINTMENT_ID + ", " +
                "appointment." + COLUMN_APPOINTMENT_DATE + ", " +
                "appointment." + COLUMN_APPOINTMENT_TIME + ", " +
                "appointment." + COLUMN_APPOINTMENT_STATUS + ", " +
                "appointment." + COLUMN_APPOINTMENT_PET_ID + ", " +
                "pet." + COLUMN_PET_NAME + ", " +
                "pet." + COLUMN_PROFILE_PET_IMAGE + ", " +
                "pet." + COLUMN_SHELTER_ID_SF + ", " +
                "shelter." + COLUMN_SHELTER_NAME + ", " +
                "pet." + COLUMN_PET_CATEGORY + ", " +
                "pet." + COLUMN_PET_AGE + ", " +
                "pet." + COLUMN_PET_GENDER + ", " +
                "pet." + COLUMN_PET_DESCRIPTION + ", " +
                "shelter." + COLUMN_SHELTER_CONTACT +
                " FROM " + TABLE_APPOINTMENT + " appointment" +
                " INNER JOIN " + TABLE_PET + " pet ON appointment." + COLUMN_APPOINTMENT_PET_ID + " = pet." + COLUMN_PET_ID +
                " INNER JOIN " + TABLE_SHELTER + " shelter ON pet." + COLUMN_SHELTER_ID_SF + " = shelter." + COLUMN_SHELTER_ID +
                " WHERE appointment." + COLUMN_APPOINTMENT_USER_ID + " = ?" +
                " AND DATE(appointment." + COLUMN_APPOINTMENT_DATE + ") >= DATE('now')" +
                " ORDER BY appointment." + COLUMN_APPOINTMENT_DATE + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int appointmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID));
                String appointmentDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE));
                String appointmentTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_TIME));
                String appointmentStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_STATUS));
                int petId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_PET_ID));
                String petName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME));
                byte[] petImageBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PET_IMAGE));
                int shelterId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_ID_SF));
                String shelterName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_NAME));
                String petCategory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_CATEGORY));
                String petAge = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE));
                String petGender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER));
                String petDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_DESCRIPTION));
                String shelterContact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_CONTACT));

                AppointmentModel appointment = new AppointmentModel(
                        appointmentId, petId, shelterId, petName, shelterName,
                        appointmentDate, appointmentTime, petImageBlob,
                        petCategory, petAge, petGender, petDescription,
                        shelterContact, appointmentStatus
                );

                appointments.add(appointment);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return appointments;
    }


    public boolean deleteAppointment(int appointmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("appointment", "appointment_id = ?", new String[]{String.valueOf(appointmentId)});
        db.close();
        return deletedRows > 0;
    }

    public boolean updateAppointment(int id, String newDate, String newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("appointment_date", newDate);
        values.put("appointment_time", newTime);

        int result = db.update("appointment", values, "appointment_id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public List<AppointmentModel1> getAppointmentsByShelterId(int shelterId) {
        List<AppointmentModel1> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "a." + COLUMN_APPOINTMENT_ID + ", " +
                "a." + COLUMN_APPOINTMENT_DATE + ", " +
                "a." + COLUMN_APPOINTMENT_TIME + ", " +
                "a." + COLUMN_APPOINTMENT_STATUS + ", " +
                "p." + COLUMN_PET_ID + ", " +
                "p." + COLUMN_PET_NAME + ", " +
                "p." + COLUMN_PROFILE_PET_IMAGE + ", " +
                "p." + COLUMN_PET_CATEGORY + ", " +
                "p." + COLUMN_PET_AGE + ", " +
                "p." + COLUMN_PET_GENDER + ", " +
                "p." + COLUMN_PET_DESCRIPTION + ", " +
                "u." + COLUMN_USER_ID + ", " +
                "u." + COLUMN_USER_NAME + ", " +
                "u." + COLUMN_USER_CONTACT + " " +
                "FROM " + TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + TABLE_PET + " p ON a." + COLUMN_APPOINTMENT_PET_ID + " = p." + COLUMN_PET_ID + " " +
                "INNER JOIN " + TABLE_USERS + " u ON a." + COLUMN_APPOINTMENT_USER_ID + " = u." + COLUMN_USER_ID + " " +
                "WHERE p." + COLUMN_SHELTER_ID_SF + " = ? " +
                "ORDER BY a." + COLUMN_APPOINTMENT_DATE + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});

        if (cursor.moveToFirst()) {
            do {
                AppointmentModel1 appointment = new AppointmentModel1(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_TIME)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PET_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_AGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CONTACT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_STATUS))
                );
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return appointments;
    }

    public boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_STATUS, newStatus);

        int rowsAffected = db.update(
                TABLE_APPOINTMENT,
                values,
                COLUMN_APPOINTMENT_ID + " = ?",
                new String[]{String.valueOf(appointmentId)}
        );

        db.close();
        return rowsAffected > 0;
    }

    public int getAppointmentCountForShelter(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = dateFormat.format(new Date());


        String query = "SELECT COUNT(*) FROM " + TABLE_APPOINTMENT + " a " +
                "INNER JOIN " + TABLE_PET + " p ON a." + COLUMN_APPOINTMENT_PET_ID + " = p." + COLUMN_PET_ID + " " +
                "WHERE p." + COLUMN_SHELTER_ID_SF + " = ? " +
                "AND date(a." + COLUMN_APPOINTMENT_DATE + ") = date(?) " +
                "AND a." + COLUMN_APPOINTMENT_STATUS + " IN ('pending', 'approved')";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId), todayDate});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        Log.d("DBHelper", "Appointment count for shelter " + shelterId + ": " + count);
        return count;
    }

    public int getPetCountForShelter(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_PET +
                " WHERE " + COLUMN_SHELTER_ID_SF + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getAdoptionCountForShelter(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_ADOPTION +
                " WHERE " + COLUMN_SHELTER_ID_PF + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getReportCountForShelter(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_REPORT +
                " WHERE " + "shelter_id" + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public Cursor getSShelterById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_SHELTER,
                null,
                COLUMN_SHELTER_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );
    }

    public boolean updateShelter(int id, String name, String owner, String email, String contact,
                                 String lat, String lon, String regNo, String estDate,
                                 String desc, String password, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SHELTER_NAME, name);
        values.put(COLUMN_OWNER_NAME, owner);
        values.put(COLUMN_SHELTER_EMAIL, email);
        values.put(COLUMN_SHELTER_CONTACT, contact);
        values.put(COLUMN_SHELTER_LAT, lat);
        values.put(COLUMN_SHELTER_LON, lon);
        values.put(COLUMN_REGISTRATION_NUMBER, regNo);
        values.put(COLUMN_ESTABLISHED_DATE, estDate);
        values.put(COLUMN_DESCRIPTION, desc);

        if (password != null && !password.isEmpty()) {
            values.put(COLUMN_SHELTER_PASSWORD, password);
        }

        if (image != null && image.length > 0) {
            values.put(COLUMN_PROFILE_SHELTER_IMAGE, image);
        }

        int rows = db.update(
                TABLE_SHELTER,
                values,
                COLUMN_SHELTER_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return rows > 0;
    }

    public UserModel1 getAUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL,
                        COLUMN_USER_CONTACT, COLUMN_DOB, COLUMN_PROFILE_IMAGE},
                COLUMN_USER_ID + "=?",
                new String[]{id},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            UserModel1 user = new UserModel1(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CONTACT)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE))
            );
            cursor.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }


    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_APPUSER_ID_UF},
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int appUserId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPUSER_ID_UF));
            cursor.close();

            db.delete(TABLE_USERS, COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});

            db.delete(TABLE_APPUSER, COLUMN_APPUSER_ID + "=?", new String[]{String.valueOf(appUserId)});

            return true;
        }

        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public boolean updateUser(int userId, String name, String email, String contact,
                              String dob, String gender, String password, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_CONTACT, contact);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_GENDER, gender);

        if (password != null && !password.isEmpty()) {
            values.put(COLUMN_USER_PASSWORD, password);
        }

        if (image != null) {
            values.put(COLUMN_PROFILE_IMAGE, image);
        }

        int rowsAffected = db.update(TABLE_USERS, values,
                COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)});

        return rowsAffected > 0;
    }

    public UserModel getUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " =?", new String[]{id});

        if (cursor != null && cursor.moveToFirst()) {
            UserModel user = new UserModel();
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
            user.setContact(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CONTACT)));
            user.setDob(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
            user.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
            user.setProfileImage(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE)));

            cursor.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }


    public boolean insertUHelp(String name, String contact, String message) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put(COLUMN_UHELP_NAME, name);
        values.put(COLUMN_UHELP_CONTACT, contact);
        values.put(COLUMN_UHELP_MESSAGE, message);
        values.put(COLUMN_UHELP_DATE, currentDate);
        values.put(COLUMN_UHELP_TIME, currentTime);

        long result = db.insert(TABLE_UHELP, null, values);
        return result != -1;
    }

    public Cursor getAllUHelpRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_UHELP,
                new String[]{COLUMN_UHELP_ID, COLUMN_UHELP_NAME, COLUMN_UHELP_CONTACT,
                        COLUMN_UHELP_MESSAGE, COLUMN_UHELP_DATE, COLUMN_UHELP_TIME},
                null, null, null, null,
                COLUMN_UHELP_DATE + " DESC, " + COLUMN_UHELP_TIME + " DESC");
    }

    public boolean deleteUHelpMessage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_UHELP, COLUMN_UHELP_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean insertSHelp(String shelterName, String contact, String message) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put(COLUMN_SHELP_NAME, shelterName);
        values.put(COLUMN_SHELP_CONTACT, contact);
        values.put(COLUMN_SHELP_MESSAGE, message);
        values.put(COLUMN_SHELP_DATE, currentDate);
        values.put(COLUMN_SHELP_TIME, currentTime);

        long result = db.insert(TABLE_SHELP, null, values);
        return result != -1;
    }

    public Cursor getAllSHelpRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SHELP,
                new String[]{COLUMN_SHELP_ID, COLUMN_SHELP_NAME, COLUMN_SHELP_CONTACT,
                        COLUMN_SHELP_MESSAGE, COLUMN_SHELP_DATE, COLUMN_SHELP_TIME},
                null, null, null, null,
                COLUMN_SHELP_DATE + " DESC, " + COLUMN_SHELP_TIME + " DESC");
    }

    public boolean deleteSHelpMessage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SHELP, COLUMN_SHELP_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Boolean insertReport (String petCategory, int shelterId, int appUserId, String description, double latitude, double longitude, byte[] image, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pet_category", petCategory);
        contentValues.put("shelter_id", shelterId);
        contentValues.put("appuser_id", appUserId);
        contentValues.put("description", description);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("petimage", image);
        contentValues.put("report_status", status);

        long result = db.insert(TABLE_REPORT, null, contentValues);
        db.close();

        if (result == -1) {
            Log.e("DBHelper", "Failed to insert reports");
            return false;
        }

        else {
            Log.d("DBHelper", "Reports inserted successfully");
            return true;
        }
    }

//    public List<ReportModel> getAllReports() {
//        List<ReportModel> reportList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM report", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String category = cursor.getString(cursor.getColumnIndexOrThrow("pet_category"));
//                String branch = cursor.getString(cursor.getColumnIndexOrThrow("branch"));
//                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
//                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
//                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
//                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("petimage")); // make sure column name matches table
//                reportList.add(new ReportModel(category, branch, description, lat, lng, image));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return reportList;
//    }

//    public List<ReportModel> getReportsByShelter(String shelterName) {
//        List<ReportModel> reportList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM report WHERE branch = ?", new String[]{shelterName});
//        if (cursor.moveToFirst()) {
//            do {
//                String petCategory = cursor.getString(cursor.getColumnIndexOrThrow("pet_category"));
//                String branch = cursor.getString(cursor.getColumnIndexOrThrow("branch"));
//                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
//                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
//                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
//                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("petimage"));
//
//                ReportModel model = new ReportModel(petCategory, branch, description, latitude, longitude, image);
//                reportList.add(model);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return reportList;
//    }

    public List<ReportModel> getReportsByShelterId(int shelterId) {
        List<ReportModel> reportList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.id, r.pet_category, r.description, r.latitude, r.longitude, " +
                "r.petimage, r.report_status, u.user_name, u.user_contact, r.appuser_id, " +
                "s.shelter_name " +
                "FROM report r " +
                "JOIN users u ON r.appuser_id = u.uid " +
                "JOIN shelter s ON r.shelter_id = s.sid " +
                "WHERE r.shelter_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});

        if (cursor.moveToFirst()) {
            do {
                ReportModel model = new ReportModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("pet_category")),
                        cursor.getString(cursor.getColumnIndexOrThrow("shelter_name")), // Now available
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                        cursor.getBlob(cursor.getColumnIndexOrThrow("petimage")),
                        cursor.getString(cursor.getColumnIndexOrThrow("report_status")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("appuser_id")) // Now available
                );
                reportList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reportList;
    }

    public int getShelterIdByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sid FROM shelter WHERE shelter_name = ?", new String[]{name});
        int sid = -1;
        if (cursor.moveToFirst()) {
            sid = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sid;
    }

    public List<ReportModel> getReportsByAppUserId(int appUserId) {
        List<ReportModel> reportList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.id, r.pet_category, s.shelter_name, r.description, r.latitude, r.longitude, r.petimage, r.report_status, r.appuser_id " +
                "FROM report r " +
                "JOIN shelter s ON r.shelter_id = s.sid " +
                "WHERE s.appuser_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(appUserId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String petCategory = cursor.getString(cursor.getColumnIndexOrThrow("pet_category"));
                String shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double lon = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("petimage"));
                String reporttStatus = cursor.getString(cursor.getColumnIndexOrThrow("report_status"));
                int reporterAppUserId = cursor.getInt(cursor.getColumnIndexOrThrow("appuser_id"));

                ReportModel model = new ReportModel(id, petCategory, shelterName, description, lat, lon, image, reporttStatus, reporterAppUserId);
                reportList.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reportList;
    }

    public ReportModel getReportById(int reportId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ReportModel model = null;

        String query = "SELECT r.id, r.pet_category, r.description, r.latitude, r.longitude, r.petimage, r.report_status, s.shelter_name, r.appuser_id " +
                "FROM report r JOIN shelter s ON r.shelter_id = s.sid WHERE r.id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(reportId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String petCategory = cursor.getString(cursor.getColumnIndexOrThrow("pet_category"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double lon = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("petimage"));
            String status = cursor.getString(cursor.getColumnIndexOrThrow("report_status"));
            String shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
            int reporterAppUserId = cursor.getInt(cursor.getColumnIndexOrThrow("appuser_id"));

            model = new ReportModel(id, petCategory, shelterName, description, lat, lon, image, status, reporterAppUserId);
        }

        cursor.close();
        db.close();
        return model;
    }

    public String[] getUserDetailsByUserUid(int userUid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT user_name, user_contact FROM users WHERE Uid = ?",
                new String[]{String.valueOf(userUid)}
        );

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow("user_contact"));
            cursor.close();
            return new String[]{name, contact};
        }

        cursor.close();
        return null;
    }

    public boolean updateReportStatus(int reportId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("report_status", newStatus);

        int rowsAffected = db.update("report", values, "id = ?", new String[]{String.valueOf(reportId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean insertDonation(int shelterId, int userId, String fname, String lname, double amount, String remark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("shelter_id", shelterId);
        values.put("user_id", userId);
        values.put("first_name", fname);
        values.put("last_name", lname);
        values.put("amount", amount);
        values.put("remark", remark);

        long result = db.insert("donation", null, values);
        db.close();
        return result != -1;
    }

    public List<DonationModel> getDonationsByShelterId(int shelterId) {
        List<DonationModel> donations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT d.id, d.user_id, d.shelter_id, d.first_name, d.last_name, d.amount, d.remark, u.profile_image " +
                "FROM donation d " +
                "JOIN users u ON d.user_id = u.uid " +
                "WHERE d.shelter_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int donationId = cursor.getInt(0);
                int userId = cursor.getInt(1);
                int shelter_Id = cursor.getInt(2);
                String fname = cursor.getString(3);
                String lname = cursor.getString(4);
                double amount = cursor.getDouble(5);
                String remark = cursor.getString(6);
                byte[] userImage = cursor.getBlob(7);

                donations.add(new DonationModel(donationId, userId, shelter_Id, fname, lname, amount, remark, userImage));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return donations;
    }

    public boolean insertMyPet(int userId, String name, String category, String dob, String vaccinated,
                               String vaccines, String vaccineDate, int exerciseReminder, int mealReminder, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("pet_name", name);
        values.put("category", category);
        values.put("dob", dob);
        values.put("vaccinated", vaccinated);
        values.put("vaccines", vaccines);
        values.put("vaccine_date", vaccineDate);
        values.put("exercise_reminder", exerciseReminder);
        values.put("meal_reminder", mealReminder);
        values.put("pet_image", image);

        long result = db.insert("MyPet", null, values);
        return result != -1;
    }

    public static class Pet {
        public int id;
        public int userId;
        public String name;
        public String category;
        public String dob;
        public String vaccinated;
        public String vaccines;
        public String vaccineDate;
        public int exerciseReminder;
        public int mealReminder;
        public byte[] image;
    }

    public Pet getMyPetById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MyPet WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            Pet pet = new Pet();
            pet.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            pet.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
            pet.name = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
            pet.category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            pet.dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
            pet.vaccinated = cursor.getString(cursor.getColumnIndexOrThrow("vaccinated"));
            pet.vaccines = cursor.getString(cursor.getColumnIndexOrThrow("vaccines"));
            pet.vaccineDate = cursor.getString(cursor.getColumnIndexOrThrow("vaccine_date"));
            pet.exerciseReminder = cursor.getInt(cursor.getColumnIndexOrThrow("exercise_reminder"));
            pet.mealReminder = cursor.getInt(cursor.getColumnIndexOrThrow("meal_reminder"));
            pet.image = cursor.getBlob(cursor.getColumnIndexOrThrow("pet_image"));
            cursor.close();
            return pet;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public Pet getLatestPetByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MyPet WHERE user_id = ? ORDER BY id DESC LIMIT 1", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            Pet pet = new Pet();
            pet.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            pet.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
            pet.name = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
            pet.category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            pet.dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
            pet.vaccinated = cursor.getString(cursor.getColumnIndexOrThrow("vaccinated"));
            pet.vaccines = cursor.getString(cursor.getColumnIndexOrThrow("vaccines"));
            pet.vaccineDate = cursor.getString(cursor.getColumnIndexOrThrow("vaccine_date"));
            pet.exerciseReminder = cursor.getInt(cursor.getColumnIndexOrThrow("exercise_reminder"));
            pet.mealReminder = cursor.getInt(cursor.getColumnIndexOrThrow("meal_reminder"));
            pet.image = cursor.getBlob(cursor.getColumnIndexOrThrow("pet_image"));
            cursor.close();
            return pet;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public boolean updateMyPet(int petId, int userId, String name, String category, String dob, String vaccinated,
                               String vaccines, String vaccineDate, int exerciseReminder, int mealReminder, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("pet_name", name);
        values.put("category", category);
        values.put("dob", dob);
        values.put("vaccinated", vaccinated);
        values.put("vaccines", vaccines);
        values.put("vaccine_date", vaccineDate);
        values.put("exercise_reminder", exerciseReminder);
        values.put("meal_reminder", mealReminder);
        values.put("pet_image", image);

        int rowsAffected = db.update("MyPet", values, "id = ?", new String[]{String.valueOf(petId)});
        return rowsAffected > 0;
    }

    public static class User {
        public int uid;
        public String name;
        public String email;
        public String contact;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE uid = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.uid = cursor.getInt(cursor.getColumnIndexOrThrow("uid"));
            user.name = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
            user.email = cursor.getString(cursor.getColumnIndexOrThrow("user_email"));
            user.contact = cursor.getString(cursor.getColumnIndexOrThrow("user_contact"));
            cursor.close();
            return user;
        }

        if (cursor != null) cursor.close();
        return null;
    }


    public boolean insertReservation(int petId, int userId, int shelterId, String address, String startDate, String endDate, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pet_id", petId);
        values.put("user_id", userId);
        values.put("shelter_id", shelterId);
        values.put("address", address);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        values.put("amount", amount);

        long result = db.insert("Reservation", null, values);
        return result != -1;
    }

    public static class Reservation {
        public int id;
        public int petId;
        public int userId;
        public int shelterId;
        public String petName;
        public String category;
        public String userName;
        public String address;
        public String startDate;
        public String endDate;
        public String shelterName;
        public String shelterContact;
        public String shelterLat;
        public String shelterLon;
        public byte[] petImage;
        public double amount;
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.*, " +
                "p.pet_name, p.pet_image, " +
                "u.user_name, " +
                "s.shelter_name " +
                "FROM Reservation r " +
                "JOIN MyPet p ON r.pet_id = p.id " +
                "JOIN users u ON r.user_id = u.uid " +
                "JOIN shelter s ON r.shelter_id = s.sid " +
                "WHERE r.user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Reservation res = new Reservation();
                res.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                res.petId = cursor.getInt(cursor.getColumnIndexOrThrow("pet_id"));
                res.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                res.shelterId = cursor.getInt(cursor.getColumnIndexOrThrow("shelter_id"));
                res.startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                res.endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
                res.amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                res.petName = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
                res.userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                res.shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                res.petImage = cursor.getBlob(cursor.getColumnIndexOrThrow("pet_image"));

                list.add(res);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Reservation getReservationById(int reservationId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.*, s." + COLUMN_SHELTER_LAT + ", s." + COLUMN_SHELTER_LON + " " +
                "FROM Reservation r " +
                "JOIN " + TABLE_SHELTER + " s ON r.shelter_id = s." + COLUMN_SHELTER_ID + " " +
                "WHERE r.id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(reservationId)});

        if (cursor != null && cursor.moveToFirst()) {
            Reservation res = new Reservation();
            res.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            res.petId = cursor.getInt(cursor.getColumnIndexOrThrow("pet_id"));
            res.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
            res.shelterId = cursor.getInt(cursor.getColumnIndexOrThrow("shelter_id"));
            res.address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            res.startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
            res.endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
            res.amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));

            res.shelterLat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_LAT));
            res.shelterLon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHELTER_LON));

            cursor.close();
            return res;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public static class Shelter {
        public int sid;
        public String name;
        public String contact;
    }

    public Shelter getSheltersbyId(int shelterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Shelter WHERE sid = ?", new String[]{String.valueOf(shelterId)});

        if (cursor != null && cursor.moveToFirst()) {
            Shelter shelter = new Shelter();
            shelter.sid = cursor.getInt(cursor.getColumnIndexOrThrow("sid"));
            shelter.name = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
            shelter.contact = cursor.getString(cursor.getColumnIndexOrThrow("shelter_contact"));
            cursor.close();
            return shelter;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    public boolean deleteReservationById(int reservationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Reservation", "id = ?", new String[]{String.valueOf(reservationId)});
        return result > 0;
    }

    public List<Reservation> getReservationsByShelterId(int shelterId) {
        List<Reservation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.*, " +
                "p.pet_name, p.pet_image, " +
                "u.user_name, u.user_contact, " +
                "s.shelter_name, s.shelter_contact " +
                "FROM Reservation r " +
                "JOIN MyPet p ON r.pet_id = p.id " +
                "JOIN users u ON r.user_id = u.uid " +
                "JOIN shelter s ON r.shelter_id = s.sid " +
                "WHERE r.shelter_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(shelterId)});

        if (cursor.moveToFirst()) {
            do {
                Reservation res = new Reservation();
                res.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                res.petId = cursor.getInt(cursor.getColumnIndexOrThrow("pet_id"));
                res.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                res.shelterId = cursor.getInt(cursor.getColumnIndexOrThrow("shelter_id"));
                res.address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                res.startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                res.endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
                res.amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                res.petName = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
                res.userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                res.shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                res.shelterContact = cursor.getString(cursor.getColumnIndexOrThrow("shelter_contact"));
                res.petImage = cursor.getBlob(cursor.getColumnIndexOrThrow("pet_image"));

                list.add(res);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public List<Reservation> getReservationsByShelterAppUserId(int appUserId) {
        List<Reservation> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.*, " +
                "p.pet_name, p.pet_image, " +
                "u.user_name, " +
                "s.shelter_name, s.shelter_contact " +
                "FROM Reservation r " +
                "JOIN MyPet p ON r.pet_id = p.id " +
                "JOIN users u ON r.user_id = u.uid " +
                "JOIN shelter s ON r.shelter_id = s.sid " +
                "WHERE s.appuser_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(appUserId)});
        if (cursor.moveToFirst()) {
            do {
                Reservation res = new Reservation();
                res.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                res.petId = cursor.getInt(cursor.getColumnIndexOrThrow("pet_id"));
                res.userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                res.shelterId = cursor.getInt(cursor.getColumnIndexOrThrow("shelter_id"));
                res.address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                res.startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                res.endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
                res.amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                res.petName = cursor.getString(cursor.getColumnIndexOrThrow("pet_name"));
                res.userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                res.shelterName = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                res.shelterContact = cursor.getString(cursor.getColumnIndexOrThrow("shelter_contact"));
                res.petImage = cursor.getBlob(cursor.getColumnIndexOrThrow("pet_image"));

                list.add(res);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean insertPromotion(String title, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PROMO_TITLE, title);
        values.put(COLUMN_PROMO_IMAGE, image);

        long result = db.insert(TABLE_PROMOTIONS, null, values);
        return result != -1;
    }

    public List<PromotionsModel> getAllPromotions() {
        List<PromotionsModel> promotionsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROMOTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                PromotionsModel promotion = new PromotionsModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROMO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROMO_TITLE)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROMO_IMAGE))
                );
                promotionsList.add(promotion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return promotionsList;
    }

    public boolean deletePromotion(int promoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PROMOTIONS, COLUMN_PROMO_ID + " = ?",
                new String[]{String.valueOf(promoId)});
        db.close();
        return result > 0;
    }
}
