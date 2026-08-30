package com.example.campusos

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(user: User, notices: List<Notice>, onOpenDrawer: () -> Unit, openHub: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { 
            Row(Modifier.fillMaxWidth().padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onOpenDrawer) { Icon(Icons.Default.Menu, "Menu") }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Good morning, ${user.name.substringBefore(' ')}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("${user.program} · ${user.semester}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        item { 
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard(stringResource(R.string.attendance), user.attendance, "+4.2%", Modifier.weight(1f))
                MetricCard(stringResource(R.string.cgpa), user.cgpa, "+0.3", Modifier.weight(1f))
            } 
        }
        item { 
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(22.dp)) {
                    Text("Your academic hub", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Everything you need to stay ahead this semester.", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = .82f), modifier = Modifier.padding(top = 6.dp))
                    Button(openHub, Modifier.padding(top = 18.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary, contentColor = MaterialTheme.colorScheme.primary)) {
                        Text("Explore hub")
                    }
                }
            }
        }
        item { Text(stringResource(R.string.recent_updates), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
        items(notices) { NoticeRow(it) }
    }
}

@Composable
fun TimetableScreen(events: List<TimetableEvent>, user: User, vm: CampusOSViewModel) {
    var showAdd by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(stringResource(R.string.timetable), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Weekly Schedule", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (user.role == UserRole.ADMIN || user.role == UserRole.FACULTY) {
                IconButton({ showAdd = true }) { Icon(Icons.Default.Add, null) }
            }
        }
        Spacer(Modifier.height(20.dp))
        if (events.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No classes scheduled", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(events) { event ->
                    Card(shape = RoundedCornerShape(16.dp)) {
                        ListItem(
                            headlineContent = { Text(event.title, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("${event.time} · ${event.location}") },
                            leadingContent = { Surface(Modifier.size(40.dp), shape = RoundedCornerShape(10.dp), color = Color(event.color).copy(alpha = 0.2f)) { Icon(Icons.Default.AccessTime, null, Modifier.padding(8.dp), tint = Color(event.color)) } },
                            trailingContent = { Text(event.type, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }
        }
    }
    if (showAdd) AddTimetableDialog(onAdd = { vm.addTimetableEvent(it); showAdd = false }, onDismiss = { showAdd = false })
}

@Composable
fun AddTimetableDialog(onAdd: (TimetableEvent) -> Unit, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Class") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Subject") })
                OutlinedTextField(time, { time = it }, label = { Text("Time (e.g. 10:00 AM)") })
                OutlinedTextField(location, { location = it }, label = { Text("Room / Location") })
            }
        },
        confirmButton = { Button({ onAdd(TimetableEvent(id = "tt-${System.currentTimeMillis()}", title = title, time = time, location = location, type = "Lecture", color = 0xFF3B82F6, dayOfWeek = 1)) }) { Text("Save") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun AppointmentScreen(appointments: List<Appointment>, messages: List<ChatMessage>, user: User, vm: CampusOSViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showBook by remember { mutableStateOf(false) }
    
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Appointments & Chat", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        
        TabRow(selectedTabIndex = selectedTab, containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.primary) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Appointments", Modifier.padding(12.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Chat", Modifier.padding(12.dp))
            }
        }
        
        Spacer(Modifier.height(16.dp))

        if (selectedTab == 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Your Appointments", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (user.role == UserRole.STUDENT) {
                    Button({ showBook = true }) { Text("Book") }
                }
            }
            Spacer(Modifier.height(12.dp))
            if (appointments.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No appointments yet", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(appointments) { apt ->
                        Card(shape = RoundedCornerShape(16.dp)) {
                            ListItem(
                                headlineContent = { Text("${apt.facultyName} · ${apt.time}", fontWeight = FontWeight.Bold) },
                                supportingContent = { Text(apt.note) },
                                overlineContent = { Text("Day ${apt.dayOfWeek} · ${apt.status}") },
                                trailingContent = {
                                    if (user.role == UserRole.ADMIN && apt.status == "Pending") {
                                        Row {
                                            IconButton({ vm.updateAppointmentStatus(apt, "Approved") }) { Icon(Icons.Default.Check, null, tint = Color(0xFF008A72)) }
                                            IconButton({ vm.updateAppointmentStatus(apt, "Rejected") }) { Icon(Icons.Default.Close, null, tint = MaterialTheme.colorScheme.error) }
                                        }
                                    } else if (user.role == UserRole.FACULTY && apt.status == "Approved") {
                                        TextButton({ vm.updateAppointmentStatus(apt, "Completed") }) { Text("Mark Done") }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        } else {
            ChatContent(messages, user, vm)
        }
    }
    if (showBook) BookAppointmentDialog(onDismiss = { showBook = false }, onBook = { faculty, day, time, note -> 
        vm.bookAppointment(faculty, day, time, note)
        showBook = false
    })
}

@Composable
fun ChatContent(messages: List<ChatMessage>, user: User, vm: CampusOSViewModel) {
    var text by remember { mutableStateOf("") }
    val recipientId = if (user.role == UserRole.STUDENT) "admin_uid" else "student_uid"

    Column(Modifier.fillMaxSize()) {
        Text("Chat with ${if (user.role == UserRole.STUDENT) "Faculty/Admin" else "Student"}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(messages) { msg ->
                val isMe = msg.senderId == user.id
                Row(Modifier.fillMaxWidth(), horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(msg.text, Modifier.padding(12.dp), color = if (isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        
        Row(Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(text, { text = it }, Modifier.weight(1f), placeholder = { Text("Type a message...") })
            Spacer(Modifier.width(8.dp))
            IconButton({
                if (text.isNotBlank()) {
                    vm.sendMessage(recipientId, text)
                    text = ""
                }
            }) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = MaterialTheme.colorScheme.primary) }
        }
    }
}

@Composable
fun BookAppointmentDialog(onDismiss: () -> Unit, onBook: (User, Int, String, String) -> Unit) {
    var note by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf(1) }
    var selectedTime by remember { mutableStateOf("10:00 AM") }
    // Mock Faculty
    val mockFaculty = User(id = "fac1", name = "Dr. Sarah Johnson", role = UserRole.FACULTY)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Book Appointment") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("With: ${mockFaculty.name}", style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(note, { note = it }, label = { Text("Purpose / Note") })
            }
        },
        confirmButton = { Button({ onBook(mockFaculty, selectedDay, selectedTime, note) }) { Text("Book Now") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun RecruitmentScreen(applications: List<CVApplication>, user: User, vm: CampusOSViewModel) {
    var showUpload by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<CVApplication?>(null) }
    
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Recruitment & AI Ranking", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            if (user.role != UserRole.ADMIN) {
                Button({ showUpload = true }) { Text("Apply") }
            }
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(applications) { app ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    ListItem(
                        headlineContent = { Text(app.candidateName, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Exp: ${app.experience}y · Skills: ${app.skills.joinToString()}") },
                        overlineContent = { Text("Gwen AI Score: ${app.score}/100 · ${app.status}") },
                        trailingContent = {
                            if (user.role == UserRole.ADMIN) {
                                Button({ selectedApp = app }) { Text("Review") }
                            }
                        }
                    )
                }
            }
        }
    }
    if (showUpload) CVUploadDialog({ fileName, content -> vm.uploadCV(fileName, content); showUpload = false }, { showUpload = false })
    if (selectedApp != null) ReviewCVDialog(selectedApp!!, onAction = { status, salary -> vm.updateCVStatus(selectedApp!!, status, salary); selectedApp = null }, onDismiss = { selectedApp = null })
}

@Composable
fun ReviewCVDialog(app: CVApplication, onAction: (String, String) -> Unit, onDismiss: () -> Unit) {
    var salary by remember { mutableStateOf(app.salaryOffer) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Review Candidate") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Name: ${app.candidateName}", fontWeight = FontWeight.Bold)
                Text("AI Reason: ${app.shortlistReason}", style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(salary, { salary = it }, label = { Text("Final Salary Offer") })
            }
        },
        confirmButton = {
            Row {
                TextButton({ onAction("Invited", salary) }) { Text("Approve & Invite", color = Color(0xFF008A72)) }
                TextButton({ onAction("Rejected", "") }) { Text("Reject", color = MaterialTheme.colorScheme.error) }
            }
        },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun CVUploadDialog(onUpload: (String, String) -> Unit, onDismiss: () -> Unit) {
    var content by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Upload CV") },
        text = {
            Column {
                Text("Paste your CV text here for mock parsing (include keywords like Kotlin, Python, and '5 years')")
                OutlinedTextField(content, { content = it }, Modifier.fillMaxWidth().height(200.dp))
            }
        },
        confirmButton = { Button({ onUpload("cv.pdf", content) }) { Text("Submit Application") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun SettingsScreen(vm: CampusOSViewModel) {
    val prefs by vm.preferences.collectAsState()
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text(stringResource(R.string.settings), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Text("Language", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(Modifier.padding(top = 8.dp).horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("en" to "English", "hi" to "Hindi", "ta" to "Tamil", "bn" to "Bengali", "or" to "Odia", "te" to "Telugu").forEach { (code, name) ->
                FilterChip(selected = prefs.language == code, onClick = { vm.updateLanguage(code) }, label = { Text(name) })
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 24.dp))
        ListItem(
            headlineContent = { Text("App Version") },
            supportingContent = { Text("1.0.0 (Production)") }
        )
    }
}

@Composable
fun AppearanceScreen(vm: CampusOSViewModel) {
    val prefs by vm.preferences.collectAsState()
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text(stringResource(R.string.appearance), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        
        ListItem(
            headlineContent = { Text("Dark Mode") },
            trailingContent = { Switch(checked = prefs.isDarkMode, onCheckedChange = { vm.updateDarkMode(it) }) }
        )
        
        HorizontalDivider(Modifier.padding(vertical = 16.dp))
        
        Text("Color Theme", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Row(Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AppTheme.entries.forEach { theme ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        Modifier.size(48.dp).clickable { vm.updateTheme(theme) },
                        shape = RoundedCornerShape(12.dp),
                        color = when(theme) {
                            AppTheme.RED -> Color(0xFFEF4444)
                            AppTheme.BLUE -> Color(0xFF3B82F6)
                            AppTheme.PURPLE -> Color(0xFF8B5CF6)
                        },
                        border = if (prefs.theme == theme) BorderStroke(3.dp, MaterialTheme.colorScheme.primary) else null
                    ) {}
                    Text(theme.name.lowercase().replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        HorizontalDivider(Modifier.padding(vertical = 24.dp))
        
        Text("Text Size", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Slider(
            value = prefs.fontSizeMultiplier,
            onValueChange = { vm.updateFontSize(it) },
            valueRange = 0.8f..1.4f,
            steps = 2
        )
    }
}

@Composable
fun ProfileScreen(user: User, docs: List<PersonalDocumentEntity>, vm: CampusOSViewModel, onUpdate: (User) -> Unit, delete: (PersonalDocumentEntity) -> Unit, onSignOut: () -> Unit) {
    var showEdit by remember { mutableStateOf(false) }
    var showAddAch by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
        Text(stringResource(R.string.profile), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Card(shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(user.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(user.email, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton({ showEdit = true }) { Icon(Icons.Default.Edit, "Edit profile") }
                }
                HorizontalDivider(Modifier.padding(vertical = 14.dp))
                Text("Program: ${user.program}", style = MaterialTheme.typography.bodyMedium)
                Text("Semester: ${user.semester}", style = MaterialTheme.typography.bodyMedium)
                if (user.phone.isNotEmpty()) Text("Phone: ${user.phone}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Achievements", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Showcase your certifications and awards.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        val achievements by vm.achievements.collectAsStateWithLifecycle()
        achievements.forEach { ach ->
            ListItem(
                headlineContent = { Text(ach.title, fontWeight = FontWeight.Bold) },
                supportingContent = { Text("${ach.issuingOrg} · ${ach.date}") },
                leadingContent = { Icon(Icons.Default.EmojiEvents, null, tint = Color(0xFFFFB300)) }
            )
        }
        Button({ showAddAch = true }, Modifier.fillMaxWidth().padding(top = 8.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)) {
            Icon(Icons.Default.Add, null); Text("Add Achievement")
        }

        Spacer(Modifier.height(24.dp))
        Text("Private Documents", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Only you can view or manage these files.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        docs.forEach { doc ->
            ListItem(
                headlineContent = { Text(doc.name) },
                supportingContent = { Text(doc.type) },
                trailingContent = { IconButton({ delete(doc) }) { Icon(Icons.Default.DeleteOutline, null) } },
                leadingContent = { Icon(Icons.Default.Lock, null, tint = MaterialTheme.colorScheme.primary) }
            )
        }
        Button({ onSignOut() }, Modifier.fillMaxWidth().padding(top = 32.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.error)) {
            Icon(Icons.AutoMirrored.Filled.Logout, null); Spacer(Modifier.width(8.dp)); Text(stringResource(R.string.sign_out))
        }
    }
    if (showEdit) EditProfileDialog(user, { onUpdate(it); showEdit = false }, { showEdit = false })
    if (showAddAch) AddAchievementDialog(onAdd = { t, d, dt, o -> vm.addAchievement(t, d, dt, o); showAddAch = false }, onDismiss = { showAddAch = false })
}

@Composable
fun AddAchievementDialog(onAdd: (String, String, String, String) -> Unit, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var org by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Achievement") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Title (e.g. Kotlin Cert)") })
                OutlinedTextField(org, { org = it }, label = { Text("Issuing Organization") })
                OutlinedTextField(date, { date = it }, label = { Text("Date") })
                OutlinedTextField(desc, { desc = it }, label = { Text("Description") })
            }
        },
        confirmButton = { Button({ onAdd(title, desc, date, org) }) { Text("Save") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun EditProfileDialog(user: User, onSave: (User) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf(user.phone) }
    var program by remember { mutableStateOf(user.program) }
    var semester by remember { mutableStateOf(user.semester) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(name, { name = it }, label = { Text("Name") })
                OutlinedTextField(phone, { phone = it }, label = { Text("Phone") })
                OutlinedTextField(program, { program = it }, label = { Text("Program") })
                OutlinedTextField(semester, { semester = it }, label = { Text("Semester") })
            }
        },
        confirmButton = { Button({ onSave(user.copy(name = name, phone = phone, program = program, semester = semester)) }) { Text("Save") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable fun MetricCard(label: String, value: String, change: String, modifier: Modifier) { Card(modifier, shape = RoundedCornerShape(18.dp)) { Column(Modifier.padding(16.dp)) { Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium); Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold); Text(change, color = Color(0xFF008A72), style = MaterialTheme.typography.labelSmall) } } }
@Composable fun NoticeRow(n: Notice) { ListItem(headlineContent = { Text(n.title, fontWeight = FontWeight.SemiBold) }, supportingContent = { Text(n.detail) }, trailingContent = { Text(n.time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }, leadingContent = { Icon(Icons.Default.NotificationsNone, null, tint = MaterialTheme.colorScheme.primary) }) }

@Composable fun HubScreen(items: List<AcademicItem>, docs: List<PersonalDocumentEntity>, user: User, open: (AcademicItem) -> Unit, add: (String) -> Unit, delete: (PersonalDocumentEntity) -> Unit, onAddItem: (AcademicItem) -> Unit = {}) { 
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf<ContentType?>(null) }
    var showAddDoc by remember { mutableStateOf(false) }
    var showAddItem by remember { mutableStateOf(false) }
    
    // Fixed: Ensure filtered items are derived from the latest flow collection
    val filtered = items.filter { (query.isBlank() || it.title.contains(query, true) || it.subtitle.contains(query, true)) && (filter == null || it.type == filter) }
    
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { 
        item { 
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(stringResource(R.string.hub), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("Your semester, organized", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (user.role == UserRole.ADMIN || user.role == UserRole.FACULTY) {
                    IconButton({ showAddItem = true }) { Icon(Icons.Default.AddCircle, "Add item", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp)) }
                }
            }
            OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth().padding(top = 14.dp), placeholder = { Text("Search homework, notes, exams...") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true) 
        }
        item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) { FilterChip(selected = filter == null, onClick = { filter = null }, label = { Text("All") }); ContentType.entries.take(5).forEach { t -> FilterChip(selected = filter == t, onClick = { filter = if (filter == t) null else t }, label = { Text(t.name.lowercase().replace('_', ' ').replaceFirstChar { it.uppercase() }) }) } } }
        item { Row(verticalAlignment = Alignment.CenterVertically) { Text("Upcoming & recent", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)); Text("${filtered.size} items", color = MaterialTheme.colorScheme.onSurfaceVariant) } }
        items(filtered, key = { it.id }) { AcademicCard(it) { open(it) } }
        if (user.role == UserRole.STUDENT) { 
            item { 
                Row(verticalAlignment = Alignment.CenterVertically) { 
                    Text("Private documents", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    IconButton({ showAddDoc = true }) { Icon(Icons.Default.Add, "Add document") } 
                }
                Text("Only you can view or manage these files.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
            items(docs) { doc ->
                ListItem(headlineContent = { Text(doc.name) }, supportingContent = { Text(doc.type) }, trailingContent = { IconButton({ delete(doc) }) { Icon(Icons.Default.DeleteOutline, "Delete") } }, leadingContent = { Icon(Icons.Default.Lock, null, tint = MaterialTheme.colorScheme.primary) })
            }
        } 
    }
    if (showAddDoc) AddDocumentDialog({ add(it); showAddDoc = false }, { showAddDoc = false })
    if (showAddItem) AddAcademicItemDialog(onAddItem = { onAddItem(it); showAddItem = false }, onDismiss = { showAddItem = false })
}

@Composable
fun AddAcademicItemDialog(onAddItem: (AcademicItem) -> Unit, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(ContentType.ASSIGNMENT) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Academic Content") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Title") })
                OutlinedTextField(subtitle, { subtitle = it }, label = { Text("Due Date / Detail") })
                Text("Type", style = MaterialTheme.typography.labelLarge)
                Row(Modifier.horizontalScroll(rememberScrollState())) {
                    ContentType.entries.forEach { type ->
                        FilterChip(selected = selectedType == type, onClick = { selectedType = type }, label = { Text(type.name.lowercase()) })
                        Spacer(Modifier.width(4.dp))
                    }
                }
            }
        },
        confirmButton = { 
            Button({ 
                if (title.isNotBlank()) {
                    onAddItem(AcademicItem(id = "item-${System.currentTimeMillis()}", title = title, subtitle = subtitle, due = "Due today", type = selectedType, icon = "Book", color = 0xFF3B82F6, progress = 0))
                }
            }) { Text("Add") } 
        },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable fun AcademicCard(item: AcademicItem, click: () -> Unit) { Card(onClick = click, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) { Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Surface(Modifier.size(46.dp), shape = RoundedCornerShape(14.dp), color = Color(item.color).copy(alpha = .15f)) { Icon(Icons.Default.AutoStories, null, Modifier.padding(12.dp), tint = Color(item.color)) }; Column(Modifier.padding(start = 14.dp).weight(1f)) { Text(item.title, fontWeight = FontWeight.Bold); Text(item.subtitle, maxLines = 1, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(item.due, style = MaterialTheme.typography.labelMedium, color = Color(item.color), modifier = Modifier.padding(top = 5.dp)) }; if (item.progress > 0) { Text("${item.progress}%", fontWeight = FontWeight.Bold) } else { Icon(Icons.Default.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) } } } }
@Composable fun AddDocumentDialog(add: (String) -> Unit, close: () -> Unit) { var name by remember { mutableStateOf("") }; AlertDialog(onDismissRequest = close, confirmButton = { Button({ if (name.isNotBlank()) add(name) }) { Text("Save") } }, dismissButton = { TextButton(close) { Text("Cancel") } }, title = { Text("Add private document") }, text = { OutlinedTextField(name, { name = it }, label = { Text("Document name") }, singleLine = true) }) }

@Composable
fun AdminDashboard(vm: CampusOSViewModel) {
    var showAddShortage by remember { mutableStateOf(false) }
    var showGenerateTimetable by remember { mutableStateOf(false) }
    val shortages by vm.teacherShortages.collectAsStateWithLifecycle()
    
    Column(Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState())) {
        Text("Admin Control Center", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        
        Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.padding(20.dp)) {
                Text("Teacher Shortage Management", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                if (shortages.isEmpty()) {
                    Text("No shortages reported.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    shortages.forEach { sh ->
                        ListItem(
                            headlineContent = { Text("${sh.subject} (${sh.department})") },
                            supportingContent = { Text("Required: ${sh.requiredCount} · Status: ${sh.status}") },
                            trailingContent = { 
                                if (sh.status == "Open") {
                                    Button({ /* Logic for LinkedIn search simulation */ }) { Text("Search LinkedIn") }
                                }
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
                Button({ showAddShortage = true }, Modifier.fillMaxWidth().padding(top = 12.dp)) { Text("Report New Shortage") }
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Column(Modifier.padding(20.dp)) {
                Text("Timetable Automation", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Generate conflict-free schedules automatically.", style = MaterialTheme.typography.bodySmall)
                Button({ showGenerateTimetable = true }, Modifier.fillMaxWidth().padding(top = 16.dp)) { Text("Auto-Generate Timetable") }
            }
        }
    }
    
    if (showAddShortage) AddShortageDialog(onAdd = { d, s, c -> vm.addShortage(d, s, c); showAddShortage = false }, onDismiss = { showAddShortage = false })
    if (showGenerateTimetable) GenerateTimetableDialog(onGenerate = { subjects, slots, rooms -> 
        vm.generateTimetable(subjects, slots, rooms)
        showGenerateTimetable = false
    }, onDismiss = { showGenerateTimetable = false })
}

@Composable
fun AddShortageDialog(onAdd: (String, String, Int) -> Unit, onDismiss: () -> Unit) {
    var dept by remember { mutableStateOf("") }
    var sub by remember { mutableStateOf("") }
    var count by remember { mutableStateOf("1") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Report Teacher Shortage") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(dept, { dept = it }, label = { Text("Department") })
                OutlinedTextField(sub, { sub = it }, label = { Text("Subject") })
                OutlinedTextField(count, { count = it }, label = { Text("Count") })
            }
        },
        confirmButton = { Button({ onAdd(dept, sub, count.toIntOrNull() ?: 1) }) { Text("Report") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun GenerateTimetableDialog(onGenerate: (List<String>, List<String>, List<String>) -> Unit, onDismiss: () -> Unit) {
    var subjects by remember { mutableStateOf("Kotlin, Java, UI Design, DBMS") }
    var slots by remember { mutableStateOf("09:00 AM, 11:00 AM, 02:00 PM") }
    var rooms by remember { mutableStateOf("Lab 101, Room 302, Seminar Hall") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Auto Timetable Config") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Enter comma-separated values:", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(subjects, { subjects = it }, label = { Text("Subjects") })
                OutlinedTextField(slots, { slots = it }, label = { Text("Time Slots") })
                OutlinedTextField(rooms, { rooms = it }, label = { Text("Rooms") })
            }
        },
        confirmButton = { 
            Button({ 
                onGenerate(
                    subjects.split(",").map { it.trim() },
                    slots.split(",").map { it.trim() },
                    rooms.split(",").map { it.trim() }
                )
            }) { Text("Generate") } 
        },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } }
    )
}


