package com.example.campusos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Dao
interface DocumentDao {
    @Query("SELECT * FROM personal_documents WHERE studentId = :studentId ORDER BY createdAt DESC") fun observe(studentId: String): Flow<List<PersonalDocumentEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(item: PersonalDocumentEntity)
    @Delete suspend fun delete(item: PersonalDocumentEntity)
}

@Dao
interface TimetableDao {
    @Query("SELECT * FROM timetable ORDER BY dayOfWeek, time") fun observe(): Flow<List<TimetableEvent>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<TimetableEvent>)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(item: TimetableEvent)
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM messages WHERE senderId = :uid OR receiverId = :uid ORDER BY timestamp ASC") fun observe(uid: String): Flow<List<ChatMessage>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(msg: ChatMessage)
}

@Dao
interface AcademicItemDao {
    @Query("SELECT * FROM cached_academic_items ORDER BY id DESC") fun observe(): Flow<List<CachedAcademicItemEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(item: CachedAcademicItemEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<CachedAcademicItemEntity>)
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments WHERE studentId = :uid OR facultyId = :uid ORDER BY timestamp DESC") fun observe(uid: String): Flow<List<Appointment>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(item: Appointment)
}

@Dao
interface CVApplicationDao {
    @Query("SELECT * FROM cv_applications ORDER BY appliedAt DESC") fun observeAll(): Flow<List<CVApplication>>
    @Query("SELECT * FROM cv_applications WHERE candidateEmail = :email") fun observeByEmail(email: String): Flow<List<CVApplication>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(item: CVApplication)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<CVApplication>)
}

@Dao
interface TeacherShortageDao {
    @Query("SELECT * FROM teacher_shortages ORDER BY createdAt DESC") fun observeAll(): Flow<List<TeacherShortage>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(item: TeacherShortage)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<TeacherShortage>)
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements WHERE userId = :userId ORDER BY date DESC") fun observeByUser(userId: String): Flow<List<Achievement>>
    @Query("SELECT * FROM achievements WHERE isPublic = 1 ORDER BY date DESC") fun observePublic(): Flow<List<Achievement>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(item: Achievement)
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<Achievement>)
}

class Converters {
    @TypeConverter fun fromStringList(value: List<String>): String = value.joinToString(",")
    @TypeConverter fun toStringList(value: String): List<String> = if (value.isBlank()) emptyList() else value.split(",")
}

@Database(entities = [SubmissionEntity::class, PersonalDocumentEntity::class, CachedAcademicItemEntity::class, TimetableEvent::class, ChatMessage::class, Appointment::class, CVApplication::class, TeacherShortage::class, Achievement::class], version = 4)
@TypeConverters(Converters::class)
abstract class CampusOSDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun timetableDao(): TimetableDao
    abstract fun chatDao(): ChatDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun cvApplicationDao(): CVApplicationDao
    abstract fun academicItemDao(): AcademicItemDao
    abstract fun teacherShortageDao(): TeacherShortageDao
    abstract fun achievementDao(): AchievementDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton fun provideDb(@ApplicationContext context: Context): CampusOSDatabase = Room.databaseBuilder(context, CampusOSDatabase::class.java, "campusos.db").fallbackToDestructiveMigration().build()
    @Provides fun provideDocumentDao(db: CampusOSDatabase) = db.documentDao()
    @Provides fun provideTimetableDao(db: CampusOSDatabase) = db.timetableDao()
    @Provides fun provideChatDao(db: CampusOSDatabase) = db.chatDao()
    @Provides fun provideAppointmentDao(db: CampusOSDatabase) = db.appointmentDao()
    @Provides fun provideCVApplicationDao(db: CampusOSDatabase) = db.cvApplicationDao()
    @Provides fun provideAcademicItemDao(db: CampusOSDatabase) = db.academicItemDao()
    @Provides fun provideTeacherShortageDao(db: CampusOSDatabase) = db.teacherShortageDao()
    @Provides fun provideAchievementDao(db: CampusOSDatabase) = db.achievementDao()
    @Provides @Singleton fun provideAuth(): FirebaseAuth = Firebase.auth
    @Provides @Singleton fun provideFirestore(): FirebaseFirestore = Firebase.firestore
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val themeKey = stringPreferencesKey("theme")
    private val darkModeKey = booleanPreferencesKey("dark_mode")
    private val fontSizeKey = floatPreferencesKey("font_size")
    private val languageKey = stringPreferencesKey("language")

    val preferences: Flow<AppPreferences> = context.dataStore.data.map { p ->
        AppPreferences(
            theme = AppTheme.valueOf(p[themeKey] ?: AppTheme.BLUE.name),
            isDarkMode = p[darkModeKey] ?: false,
            fontSizeMultiplier = p[fontSizeKey] ?: 1.0f,
            language = p[languageKey] ?: "en"
        )
    }

    suspend fun updateTheme(theme: AppTheme) { context.dataStore.edit { it[themeKey] = theme.name } }
    suspend fun updateDarkMode(dark: Boolean) { context.dataStore.edit { it[darkModeKey] = dark } }
    suspend fun updateFontSize(size: Float) { context.dataStore.edit { it[fontSizeKey] = size } }
    suspend fun updateLanguage(lang: String) { context.dataStore.edit { it[languageKey] = lang } }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class CampusOSRepository @Inject constructor(
    private val documentDao: DocumentDao,
    private val timetableDao: TimetableDao,
    private val chatDao: ChatDao,
    private val appointmentDao: AppointmentDao,
    private val cvApplicationDao: CVApplicationDao,
    private val academicItemDao: AcademicItemDao,
    private val teacherShortageDao: TeacherShortageDao,
    private val achievementDao: AchievementDao,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val repoScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _user = MutableStateFlow<User?>(null)
    val user: Flow<User?> = _user

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                firestore.collection("users").document(firebaseUser.uid).addSnapshotListener { snapshot, _ ->
                    _user.value = snapshot?.toObject(User::class.java)?.copy(id = firebaseUser.uid)
                }
                
                // Real-time Sync: Appointments (Student OR Faculty)
                firestore.collection("appointments")
                    .where(Filter.or(
                        Filter.equalTo("studentId", firebaseUser.uid),
                        Filter.equalTo("facultyId", firebaseUser.uid)
                    ))
                    .addSnapshotListener { snapshot, _ ->
                        snapshot?.toObjects(Appointment::class.java)?.let { apts ->
                            repoScope.launch { apts.forEach { appointmentDao.upsert(it) } }
                        }
                    }
                
                // Real-time Sync: Academic Content
                firestore.collection("academicContent").addSnapshotListener { snapshot, _ ->
                    snapshot?.toObjects(CachedAcademicItemEntity::class.java)?.let { items ->
                        repoScope.launch { academicItemDao.insertAll(items) }
                    }
                }

                // Real-time Sync: Timetable
                firestore.collection("timetable").addSnapshotListener { snapshot, _ ->
                    snapshot?.toObjects(TimetableEvent::class.java)?.let { events ->
                        repoScope.launch { timetableDao.insertAll(events) }
                    }
                }

                // Real-time Sync: Personal Documents
                firestore.collection("personalDocuments")
                    .whereEqualTo("studentId", firebaseUser.uid)
                    .addSnapshotListener { snapshot, _ ->
                        snapshot?.toObjects(PersonalDocumentEntity::class.java)?.let { docs ->
                            repoScope.launch { docs.forEach { documentDao.insert(it) } }
                        }
                    }

                // Real-time Sync: Teacher Shortages
                firestore.collection("teacher_shortages").addSnapshotListener { snapshot, _ ->
                    snapshot?.toObjects(TeacherShortage::class.java)?.let { shortages ->
                        repoScope.launch { teacherShortageDao.insertAll(shortages) }
                    }
                }

                // Real-time Sync: CV Applications
                firestore.collection("cv_applications").addSnapshotListener { snapshot, _ ->
                    snapshot?.toObjects(CVApplication::class.java)?.let { apps ->
                        repoScope.launch { cvApplicationDao.insertAll(apps) }
                    }
                }

                // Real-time Sync: Achievements
                firestore.collection("achievements").addSnapshotListener { snapshot, _ ->
                    snapshot?.toObjects(Achievement::class.java)?.let { items ->
                        repoScope.launch { achievementDao.insertAll(items) }
                    }
                }

                // Real-time Sync: Chat (Sender OR Receiver)
                firestore.collection("messages")
                    .where(Filter.or(
                        Filter.equalTo("senderId", firebaseUser.uid),
                        Filter.equalTo("receiverId", firebaseUser.uid)
                    ))
                    .addSnapshotListener { snapshot, _ ->
                        snapshot?.toObjects(ChatMessage::class.java)?.let { msgs ->
                            repoScope.launch { msgs.forEach { chatDao.insert(it) } }
                        }
                    }
            } else {
                _user.value = null
            }
        }
    }
    
    val documents: Flow<List<PersonalDocumentEntity>> = _user.flatMapLatest { user ->
        documentDao.observe(user?.id ?: "")
    }

    val timetable = timetableDao.observe()
    val appointments: Flow<List<Appointment>> = _user.flatMapLatest { user ->
        appointmentDao.observe(user?.id ?: "")
    }
    val cvApplications = cvApplicationDao.observeAll()
    val teacherShortages = teacherShortageDao.observeAll()
    val achievements = _user.flatMapLatest { user ->
        achievementDao.observeByUser(user?.id ?: "")
    }
    val publicAchievements = achievementDao.observePublic()
    
    val messages = _user.flatMapLatest { user ->
        chatDao.observe(user?.id ?: "")
    }

    val academicItems = academicItemDao.observe().map { list ->
        list.map { AcademicItem(it.id, it.title, it.subtitle, it.due, ContentType.valueOf(it.type), it.icon, it.color, it.progress, it.status) }
    }
    val notices = listOf<Notice>()

    suspend fun addAcademicItem(item: AcademicItem) {
        val entity = CachedAcademicItemEntity(item.id, "", item.title, item.subtitle, item.due, item.type.name, item.icon, item.color, item.progress, item.status)
        academicItemDao.insert(entity)
        firestore.collection("academicContent").document(item.id).set(entity).await()
    }
    
    fun sendMessage(receiverId: String, text: String) {
        val senderId = auth.currentUser?.uid ?: return
        val msg = ChatMessage(id = "msg-${System.currentTimeMillis()}", senderId = senderId, receiverId = receiverId, text = text, timestamp = System.currentTimeMillis())
        repoScope.launch {
            chatDao.insert(msg)
            try {
                firestore.collection("messages").document(msg.id).set(msg).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    suspend fun addTimetableEvent(event: TimetableEvent) {
        timetableDao.insert(event)
        firestore.collection("timetable").document(event.id).set(event).await()
    }

    suspend fun generateTimetable(subjects: List<String>, slots: List<String>, rooms: List<String>) {
        val newEvents = mutableListOf<TimetableEvent>()
        var slotIndex = 0
        var day = 1
        
        subjects.forEach { subject ->
            if (slotIndex >= slots.size) {
                slotIndex = 0
                day++
            }
            if (day > 5) day = 1
            
            val room = rooms.getOrNull(slotIndex % rooms.size) ?: "Main Hall"
            val time = slots[slotIndex]
            
            val event = TimetableEvent(
                id = "tt-${System.currentTimeMillis()}-${subject.hashCode()}",
                title = subject,
                time = time,
                location = room,
                type = "Lecture",
                color = 0xFF3B82F6,
                dayOfWeek = day
            )
            newEvents.add(event)
            slotIndex++
        }
        
        timetableDao.insertAll(newEvents)
        newEvents.forEach { firestore.collection("timetable").document(it.id).set(it).await() }
    }

    suspend fun addDocument(name: String, type: String) {
        val uid = auth.currentUser?.uid ?: return
        val doc = PersonalDocumentEntity(name + System.currentTimeMillis(), uid, name, type, System.currentTimeMillis())
        documentDao.insert(doc)
        firestore.collection("personalDocuments").document(doc.id).set(doc).await()
    }

    suspend fun removeDocument(doc: PersonalDocumentEntity) {
        documentDao.delete(doc)
        firestore.collection("personalDocuments").document(doc.id).delete().await()
    }

    suspend fun updateUser(user: User) {
        firestore.collection("users").document(user.id).set(user).await()
    }

    suspend fun bookAppointment(faculty: User, dayOfWeek: Int, time: String, note: String) {
        val student = _user.value ?: return
        val appointment = Appointment(
            id = "apt-${System.currentTimeMillis()}",
            studentId = student.id,
            studentName = student.name,
            facultyId = faculty.id,
            facultyName = faculty.name,
            dayOfWeek = dayOfWeek,
            time = time,
            note = note
        )
        appointmentDao.upsert(appointment)
        firestore.collection("appointments").document(appointment.id).set(appointment).await()
    }

    suspend fun updateAppointmentStatus(appointment: Appointment, status: String) {
        val updated = appointment.copy(status = status)
        appointmentDao.upsert(updated)
        firestore.collection("appointments").document(updated.id).set(updated).await()
    }

    suspend fun uploadCV(fileName: String, content: String) {
        val candidate = _user.value ?: return
        // Mock "Gwen AI" Ranking Logic
        val skills = listOf("Kotlin", "Java", "Python", "React", "Compose", "Firestore", "Hilt", "Room").filter { content.contains(it, ignoreCase = true) }
        val expMatch = Regex("(\\d+)\\s*years").find(content)
        val exp = expMatch?.groupValues?.get(1)?.toIntOrNull() ?: 0
        
        // Shortlist Reason based on AI analysis
        val reason = if (skills.contains("Kotlin") && exp >= 2) "Strong Kotlin background with $exp years experience." else "Matches some skills: ${skills.joinToString()}."
        
        val app = CVApplication(
            id = "cv-${System.currentTimeMillis()}",
            candidateName = candidate.name,
            candidateEmail = candidate.email,
            fileName = fileName,
            experience = exp,
            skills = skills,
            score = (skills.size * 10) + (exp * 5),
            status = "Applied",
            shortlistReason = reason
        )
        cvApplicationDao.upsert(app)
        firestore.collection("cv_applications").document(app.id).set(app).await()
    }

    suspend fun addAchievement(achievement: Achievement) {
        achievementDao.upsert(achievement)
        firestore.collection("achievements").document(achievement.id).set(achievement).await()
    }

    suspend fun addTeacherShortage(shortage: TeacherShortage) {
        teacherShortageDao.upsert(shortage)
        firestore.collection("teacher_shortages").document(shortage.id).set(shortage).await()
    }

    suspend fun updateCVStatus(app: CVApplication, status: String, salary: String = "") {
        val updated = app.copy(status = status, salaryOffer = salary)
        cvApplicationDao.upsert(updated)
        firestore.collection("cv_applications").document(updated.id).set(updated).await()
        
        if (status == "Invited") {
            // Mock Email Sending via Cloud Function
            println("Sending invitation email to ${app.candidateEmail} with salary $salary")
        }
    }

    // Mock LinkedIn-style Search
    fun searchCandidates(skills: List<String>): List<User> {
        return listOf(
            User(id = "cand1", name = "John Doe", email = "john@example.com", role = UserRole.FACULTY, skills = listOf("Kotlin", "Compose"), experience = 5),
            User(id = "cand2", name = "Jane Smith", email = "jane@example.com", role = UserRole.FACULTY, skills = listOf("Java", "Spring"), experience = 3),
            User(id = "cand3", name = "Mike Ross", email = "mike@example.com", role = UserRole.FACULTY, skills = listOf("Python", "AI"), experience = 4)
        ).filter { it.skills.any { s -> skills.contains(s) } }
    }

    fun mockLogin(role: UserRole = UserRole.STUDENT) {
        _user.value = User(
            id = "mock_id",
            name = "Test ${role.name.lowercase().replaceFirstChar { it.uppercase() }}",
            email = "test@example.com",
            role = role
        )
    }
}
