package com.example.campusos

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp class CampusOSApplication : android.app.Application()

@HiltViewModel
class CampusOSViewModel @Inject constructor(
    private val repository: CampusOSRepository,
    private val prefRepository: UserPreferencesRepository,
    val auth: FirebaseAuth
) : ViewModel() {
    val user = repository.user.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val preferences = prefRepository.preferences.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppPreferences())
    
    val documents = repository.documents.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val timetable = repository.timetable.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val appointments = repository.appointments.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val cvApplications = repository.cvApplications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val messages = repository.messages.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val teacherShortages = repository.teacherShortages.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val achievements = repository.achievements.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val publicAchievements = repository.publicAchievements.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    
    val academicItems get() = repository.academicItems
    val notices get() = repository.notices
    
    fun addDocument(name: String) = viewModelScope.launch { repository.addDocument(name, "PDF") }
    fun deleteDocument(doc: PersonalDocumentEntity) = viewModelScope.launch { repository.removeDocument(doc) }
    fun updateUser(user: User) = viewModelScope.launch { repository.updateUser(user) }
    
    fun bookAppointment(faculty: User, dayOfWeek: Int, time: String, note: String) = viewModelScope.launch { repository.bookAppointment(faculty, dayOfWeek, time, note) }
    fun updateAppointmentStatus(appointment: Appointment, status: String) = viewModelScope.launch { repository.updateAppointmentStatus(appointment, status) }
    fun uploadCV(fileName: String, content: String) = viewModelScope.launch { repository.uploadCV(fileName, content) }
    
    fun addAcademicItem(item: AcademicItem) = viewModelScope.launch { repository.addAcademicItem(item) }
    fun addTimetableEvent(event: TimetableEvent) = viewModelScope.launch { repository.addTimetableEvent(event) }
    fun generateTimetable(subjects: List<String>, slots: List<String>, rooms: List<String>) = viewModelScope.launch { repository.generateTimetable(subjects, slots, rooms) }
    fun sendMessage(receiverId: String, text: String) = viewModelScope.launch { repository.sendMessage(receiverId, text) }

    fun addAchievement(title: String, desc: String, date: String, org: String) = viewModelScope.launch {
        val user = user.value ?: return@launch
        repository.addAchievement(Achievement(id = "ach-${System.currentTimeMillis()}", userId = user.id, title = title, description = desc, date = date, issuingOrg = org))
    }

    fun addShortage(dept: String, sub: String, count: Int) = viewModelScope.launch {
        repository.addTeacherShortage(TeacherShortage(id = "sh-${System.currentTimeMillis()}", department = dept, subject = sub, requiredCount = count))
    }

    fun updateCVStatus(app: CVApplication, status: String, salary: String = "") = viewModelScope.launch {
        repository.updateCVStatus(app, status, salary)
    }

    fun searchCandidates(skills: List<String>) = repository.searchCandidates(skills)

    fun updateTheme(theme: AppTheme) = viewModelScope.launch { prefRepository.updateTheme(theme) }
    fun updateDarkMode(dark: Boolean) = viewModelScope.launch { prefRepository.updateDarkMode(dark) }
    fun updateFontSize(multiplier: Float) = viewModelScope.launch { prefRepository.updateFontSize(multiplier) }
    fun updateLanguage(lang: String) = viewModelScope.launch { prefRepository.updateLanguage(lang) }
    
    fun mockLogin(role: UserRole = UserRole.STUDENT) {
        repository.mockLogin(role)
    }

    fun signOut() { auth.signOut() }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm by viewModels<CampusOSViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs by vm.preferences.collectAsStateWithLifecycle()
            
            LaunchedEffect(prefs.language) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(prefs.language)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
            
            CampusOSTheme(prefs) {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val user by vm.user.collectAsStateWithLifecycle()
                    if (user == null) {
                        AuthScreen(vm.auth, onMockLogin = { vm.mockLogin(it) }) { /* Success handled by auth state listener */ }
                    } else {
                        MainNavigation(vm, user!!)
                    }
                }
            }
        }
    }
}

@Composable
fun MainNavigation(vm: CampusOSViewModel, user: User) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(Modifier.fillMaxWidth().padding(24.dp)) {
                    Column {
                        Icon(Icons.Default.School, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(12.dp))
                        Text("FacultyHub", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(user.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(label = { Text("Profile") }, selected = false, onClick = { navController.navigate("profile"); scope.launch { drawerState.close() } }, icon = { Icon(Icons.Default.Person, null) })
                if (user.role == UserRole.ADMIN) {
                    NavigationDrawerItem(label = { Text("Admin Dashboard") }, selected = false, onClick = { navController.navigate("admin"); scope.launch { drawerState.close() } }, icon = { Icon(Icons.Default.AdminPanelSettings, null) })
                }
                NavigationDrawerItem(label = { Text("Settings") }, selected = false, onClick = { navController.navigate("settings"); scope.launch { drawerState.close() } }, icon = { Icon(Icons.Default.Settings, null) })
                NavigationDrawerItem(label = { Text("Appearance") }, selected = false, onClick = { navController.navigate("appearance"); scope.launch { drawerState.close() } }, icon = { Icon(Icons.Default.Palette, null) })
                NavigationDrawerItem(label = { Text("Recruitment") }, selected = false, onClick = { navController.navigate("recruitment"); scope.launch { drawerState.close() } }, icon = { Icon(Icons.Default.Work, null) })
                NavigationDrawerItem(label = { Text("Data & Storage") }, selected = false, onClick = { /* TODO */ }, icon = { Icon(Icons.Default.Storage, null) })
                NavigationDrawerItem(label = { Text("About Us") }, selected = false, onClick = { /* TODO */ }, icon = { Icon(Icons.Default.Info, null) })
                Spacer(Modifier.weight(1f))
                HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                NavigationDrawerItem(label = { Text("Sign Out") }, selected = false, onClick = { vm.signOut() }, icon = { Icon(Icons.AutoMirrored.Filled.Logout, null) }, modifier = Modifier.padding(bottom = 12.dp))
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                    val items = listOf(
                        "home" to Icons.Default.Home, 
                        "hub" to Icons.AutoMirrored.Filled.MenuBook, 
                        "timetable" to Icons.Default.CalendarMonth, 
                        "appointments" to Icons.AutoMirrored.Filled.Assignment
                    )
                    items.forEach { (route, icon) ->
                        NavigationBarItem(
                            selected = currentRoute == route,
                            onClick = { navController.navigate(route) { popUpTo("home"); launchSingleTop = true } },
                            icon = { Icon(icon, null) },
                            label = { Text(route.replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
                composable("home") { HomeScreen(user, vm.notices, onOpenDrawer = { scope.launch { drawerState.open() } }) { navController.navigate("hub") } }
                composable("hub") {
                    val docs by vm.documents.collectAsStateWithLifecycle()
                    val items by vm.academicItems.collectAsStateWithLifecycle(emptyList())
                    HubScreen(items, docs, user, open = { /* Detail */ }, add = { vm.addDocument(it) }, delete = { vm.deleteDocument(it) }, onAddItem = { vm.addAcademicItem(it) })
                }
                composable("timetable") {
                    val events by vm.timetable.collectAsStateWithLifecycle()
                    TimetableScreen(events, user, vm)
                }
                composable("appointments") {
                    val apts by vm.appointments.collectAsStateWithLifecycle()
                    val msgs by vm.messages.collectAsStateWithLifecycle()
                    AppointmentScreen(apts, msgs, user, vm)
                }
                composable("recruitment") {
                    val apps by vm.cvApplications.collectAsStateWithLifecycle()
                    RecruitmentScreen(apps, user, vm)
                }
                composable("admin") { AdminDashboard(vm) }
                composable("profile") { ProfileScreen(user, vm.documents.collectAsStateWithLifecycle().value, vm, onUpdate = { vm.updateUser(it) }, delete = { vm.deleteDocument(it) }, onSignOut = { vm.signOut() }) }
                composable("settings") { SettingsScreen(vm) }
                composable("appearance") { AppearanceScreen(vm) }
            }
        }
    }
}
