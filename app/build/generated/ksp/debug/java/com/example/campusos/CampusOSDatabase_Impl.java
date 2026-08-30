package com.example.campusos;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CampusOSDatabase_Impl extends CampusOSDatabase {
  private volatile DocumentDao _documentDao;

  private volatile TimetableDao _timetableDao;

  private volatile ChatDao _chatDao;

  private volatile AppointmentDao _appointmentDao;

  private volatile CVApplicationDao _cVApplicationDao;

  private volatile AcademicItemDao _academicItemDao;

  private volatile TeacherShortageDao _teacherShortageDao;

  private volatile AchievementDao _achievementDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `submissions` (`id` TEXT NOT NULL, `itemId` TEXT NOT NULL, `studentId` TEXT NOT NULL, `text` TEXT NOT NULL, `fileName` TEXT, `status` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `personal_documents` (`id` TEXT NOT NULL, `studentId` TEXT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_academic_items` (`id` TEXT NOT NULL, `studentId` TEXT NOT NULL, `title` TEXT NOT NULL, `subtitle` TEXT NOT NULL, `due` TEXT NOT NULL, `type` TEXT NOT NULL, `icon` TEXT NOT NULL, `color` INTEGER NOT NULL, `progress` INTEGER NOT NULL, `status` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `timetable` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `time` TEXT NOT NULL, `location` TEXT NOT NULL, `type` TEXT NOT NULL, `color` INTEGER NOT NULL, `dayOfWeek` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `messages` (`id` TEXT NOT NULL, `senderId` TEXT NOT NULL, `receiverId` TEXT NOT NULL, `text` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `appointments` (`id` TEXT NOT NULL, `studentId` TEXT NOT NULL, `studentName` TEXT NOT NULL, `facultyId` TEXT NOT NULL, `facultyName` TEXT NOT NULL, `dayOfWeek` INTEGER NOT NULL, `time` TEXT NOT NULL, `note` TEXT NOT NULL, `status` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cv_applications` (`id` TEXT NOT NULL, `candidateName` TEXT NOT NULL, `candidateEmail` TEXT NOT NULL, `fileName` TEXT NOT NULL, `experience` INTEGER NOT NULL, `skills` TEXT NOT NULL, `education` TEXT NOT NULL, `score` INTEGER NOT NULL, `status` TEXT NOT NULL, `salaryOffer` TEXT NOT NULL, `shortlistReason` TEXT NOT NULL, `appliedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `teacher_shortages` (`id` TEXT NOT NULL, `department` TEXT NOT NULL, `subject` TEXT NOT NULL, `requiredCount` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `date` TEXT NOT NULL, `issuingOrg` TEXT NOT NULL, `certificateUrl` TEXT, `isPublic` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cd3a31d959c9937a152fc89e89f037c5')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `submissions`");
        db.execSQL("DROP TABLE IF EXISTS `personal_documents`");
        db.execSQL("DROP TABLE IF EXISTS `cached_academic_items`");
        db.execSQL("DROP TABLE IF EXISTS `timetable`");
        db.execSQL("DROP TABLE IF EXISTS `messages`");
        db.execSQL("DROP TABLE IF EXISTS `appointments`");
        db.execSQL("DROP TABLE IF EXISTS `cv_applications`");
        db.execSQL("DROP TABLE IF EXISTS `teacher_shortages`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSubmissions = new HashMap<String, TableInfo.Column>(7);
        _columnsSubmissions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("itemId", new TableInfo.Column("itemId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("studentId", new TableInfo.Column("studentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("fileName", new TableInfo.Column("fileName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubmissions.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubmissions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubmissions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubmissions = new TableInfo("submissions", _columnsSubmissions, _foreignKeysSubmissions, _indicesSubmissions);
        final TableInfo _existingSubmissions = TableInfo.read(db, "submissions");
        if (!_infoSubmissions.equals(_existingSubmissions)) {
          return new RoomOpenHelper.ValidationResult(false, "submissions(com.example.campusos.SubmissionEntity).\n"
                  + " Expected:\n" + _infoSubmissions + "\n"
                  + " Found:\n" + _existingSubmissions);
        }
        final HashMap<String, TableInfo.Column> _columnsPersonalDocuments = new HashMap<String, TableInfo.Column>(5);
        _columnsPersonalDocuments.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalDocuments.put("studentId", new TableInfo.Column("studentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalDocuments.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalDocuments.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersonalDocuments.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPersonalDocuments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPersonalDocuments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPersonalDocuments = new TableInfo("personal_documents", _columnsPersonalDocuments, _foreignKeysPersonalDocuments, _indicesPersonalDocuments);
        final TableInfo _existingPersonalDocuments = TableInfo.read(db, "personal_documents");
        if (!_infoPersonalDocuments.equals(_existingPersonalDocuments)) {
          return new RoomOpenHelper.ValidationResult(false, "personal_documents(com.example.campusos.PersonalDocumentEntity).\n"
                  + " Expected:\n" + _infoPersonalDocuments + "\n"
                  + " Found:\n" + _existingPersonalDocuments);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedAcademicItems = new HashMap<String, TableInfo.Column>(10);
        _columnsCachedAcademicItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("studentId", new TableInfo.Column("studentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("subtitle", new TableInfo.Column("subtitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("due", new TableInfo.Column("due", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedAcademicItems.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedAcademicItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedAcademicItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedAcademicItems = new TableInfo("cached_academic_items", _columnsCachedAcademicItems, _foreignKeysCachedAcademicItems, _indicesCachedAcademicItems);
        final TableInfo _existingCachedAcademicItems = TableInfo.read(db, "cached_academic_items");
        if (!_infoCachedAcademicItems.equals(_existingCachedAcademicItems)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_academic_items(com.example.campusos.CachedAcademicItemEntity).\n"
                  + " Expected:\n" + _infoCachedAcademicItems + "\n"
                  + " Found:\n" + _existingCachedAcademicItems);
        }
        final HashMap<String, TableInfo.Column> _columnsTimetable = new HashMap<String, TableInfo.Column>(7);
        _columnsTimetable.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("time", new TableInfo.Column("time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetable.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimetable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTimetable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimetable = new TableInfo("timetable", _columnsTimetable, _foreignKeysTimetable, _indicesTimetable);
        final TableInfo _existingTimetable = TableInfo.read(db, "timetable");
        if (!_infoTimetable.equals(_existingTimetable)) {
          return new RoomOpenHelper.ValidationResult(false, "timetable(com.example.campusos.TimetableEvent).\n"
                  + " Expected:\n" + _infoTimetable + "\n"
                  + " Found:\n" + _existingTimetable);
        }
        final HashMap<String, TableInfo.Column> _columnsMessages = new HashMap<String, TableInfo.Column>(5);
        _columnsMessages.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("senderId", new TableInfo.Column("senderId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("receiverId", new TableInfo.Column("receiverId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMessages = new TableInfo("messages", _columnsMessages, _foreignKeysMessages, _indicesMessages);
        final TableInfo _existingMessages = TableInfo.read(db, "messages");
        if (!_infoMessages.equals(_existingMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "messages(com.example.campusos.ChatMessage).\n"
                  + " Expected:\n" + _infoMessages + "\n"
                  + " Found:\n" + _existingMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsAppointments = new HashMap<String, TableInfo.Column>(10);
        _columnsAppointments.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("studentId", new TableInfo.Column("studentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("studentName", new TableInfo.Column("studentName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("facultyId", new TableInfo.Column("facultyId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("facultyName", new TableInfo.Column("facultyName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("time", new TableInfo.Column("time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppointments.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppointments = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppointments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppointments = new TableInfo("appointments", _columnsAppointments, _foreignKeysAppointments, _indicesAppointments);
        final TableInfo _existingAppointments = TableInfo.read(db, "appointments");
        if (!_infoAppointments.equals(_existingAppointments)) {
          return new RoomOpenHelper.ValidationResult(false, "appointments(com.example.campusos.Appointment).\n"
                  + " Expected:\n" + _infoAppointments + "\n"
                  + " Found:\n" + _existingAppointments);
        }
        final HashMap<String, TableInfo.Column> _columnsCvApplications = new HashMap<String, TableInfo.Column>(12);
        _columnsCvApplications.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("candidateName", new TableInfo.Column("candidateName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("candidateEmail", new TableInfo.Column("candidateEmail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("fileName", new TableInfo.Column("fileName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("experience", new TableInfo.Column("experience", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("skills", new TableInfo.Column("skills", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("education", new TableInfo.Column("education", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("salaryOffer", new TableInfo.Column("salaryOffer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("shortlistReason", new TableInfo.Column("shortlistReason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCvApplications.put("appliedAt", new TableInfo.Column("appliedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCvApplications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCvApplications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCvApplications = new TableInfo("cv_applications", _columnsCvApplications, _foreignKeysCvApplications, _indicesCvApplications);
        final TableInfo _existingCvApplications = TableInfo.read(db, "cv_applications");
        if (!_infoCvApplications.equals(_existingCvApplications)) {
          return new RoomOpenHelper.ValidationResult(false, "cv_applications(com.example.campusos.CVApplication).\n"
                  + " Expected:\n" + _infoCvApplications + "\n"
                  + " Found:\n" + _existingCvApplications);
        }
        final HashMap<String, TableInfo.Column> _columnsTeacherShortages = new HashMap<String, TableInfo.Column>(6);
        _columnsTeacherShortages.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherShortages.put("department", new TableInfo.Column("department", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherShortages.put("subject", new TableInfo.Column("subject", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherShortages.put("requiredCount", new TableInfo.Column("requiredCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherShortages.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeacherShortages.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeacherShortages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeacherShortages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeacherShortages = new TableInfo("teacher_shortages", _columnsTeacherShortages, _foreignKeysTeacherShortages, _indicesTeacherShortages);
        final TableInfo _existingTeacherShortages = TableInfo.read(db, "teacher_shortages");
        if (!_infoTeacherShortages.equals(_existingTeacherShortages)) {
          return new RoomOpenHelper.ValidationResult(false, "teacher_shortages(com.example.campusos.TeacherShortage).\n"
                  + " Expected:\n" + _infoTeacherShortages + "\n"
                  + " Found:\n" + _existingTeacherShortages);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(9);
        _columnsAchievements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("issuingOrg", new TableInfo.Column("issuingOrg", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("certificateUrl", new TableInfo.Column("certificateUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isPublic", new TableInfo.Column("isPublic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.example.campusos.Achievement).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cd3a31d959c9937a152fc89e89f037c5", "77c3c5145cc8828e425dceeee1315566");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "submissions","personal_documents","cached_academic_items","timetable","messages","appointments","cv_applications","teacher_shortages","achievements");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `submissions`");
      _db.execSQL("DELETE FROM `personal_documents`");
      _db.execSQL("DELETE FROM `cached_academic_items`");
      _db.execSQL("DELETE FROM `timetable`");
      _db.execSQL("DELETE FROM `messages`");
      _db.execSQL("DELETE FROM `appointments`");
      _db.execSQL("DELETE FROM `cv_applications`");
      _db.execSQL("DELETE FROM `teacher_shortages`");
      _db.execSQL("DELETE FROM `achievements`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DocumentDao.class, DocumentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TimetableDao.class, TimetableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChatDao.class, ChatDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppointmentDao.class, AppointmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CVApplicationDao.class, CVApplicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AcademicItemDao.class, AcademicItemDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TeacherShortageDao.class, TeacherShortageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public DocumentDao documentDao() {
    if (_documentDao != null) {
      return _documentDao;
    } else {
      synchronized(this) {
        if(_documentDao == null) {
          _documentDao = new DocumentDao_Impl(this);
        }
        return _documentDao;
      }
    }
  }

  @Override
  public TimetableDao timetableDao() {
    if (_timetableDao != null) {
      return _timetableDao;
    } else {
      synchronized(this) {
        if(_timetableDao == null) {
          _timetableDao = new TimetableDao_Impl(this);
        }
        return _timetableDao;
      }
    }
  }

  @Override
  public ChatDao chatDao() {
    if (_chatDao != null) {
      return _chatDao;
    } else {
      synchronized(this) {
        if(_chatDao == null) {
          _chatDao = new ChatDao_Impl(this);
        }
        return _chatDao;
      }
    }
  }

  @Override
  public AppointmentDao appointmentDao() {
    if (_appointmentDao != null) {
      return _appointmentDao;
    } else {
      synchronized(this) {
        if(_appointmentDao == null) {
          _appointmentDao = new AppointmentDao_Impl(this);
        }
        return _appointmentDao;
      }
    }
  }

  @Override
  public CVApplicationDao cvApplicationDao() {
    if (_cVApplicationDao != null) {
      return _cVApplicationDao;
    } else {
      synchronized(this) {
        if(_cVApplicationDao == null) {
          _cVApplicationDao = new CVApplicationDao_Impl(this);
        }
        return _cVApplicationDao;
      }
    }
  }

  @Override
  public AcademicItemDao academicItemDao() {
    if (_academicItemDao != null) {
      return _academicItemDao;
    } else {
      synchronized(this) {
        if(_academicItemDao == null) {
          _academicItemDao = new AcademicItemDao_Impl(this);
        }
        return _academicItemDao;
      }
    }
  }

  @Override
  public TeacherShortageDao teacherShortageDao() {
    if (_teacherShortageDao != null) {
      return _teacherShortageDao;
    } else {
      synchronized(this) {
        if(_teacherShortageDao == null) {
          _teacherShortageDao = new TeacherShortageDao_Impl(this);
        }
        return _teacherShortageDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }
}
