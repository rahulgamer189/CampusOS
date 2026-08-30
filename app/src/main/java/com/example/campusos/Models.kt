package com.example.campusos

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole { ADMIN, FACULTY, STUDENT }
enum class ContentType { HOMEWORK, ASSIGNMENT, PROJECT, ACTIVITY, NOTE, SYLLABUS, CLASS_TEST, EXAM }

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.STUDENT,
    val program: String = "B.Tech Computer Science",
    val semester: String = "Semester 4",
    val phone: String = "",
    val attendance: String = "92%",
    val cgpa: String = "8.7",
    val department: String = "",
    val skills: List<String> = emptyList(),
    val experience: Int = 0 // Years
)

data class AcademicItem(val id: String, val title: String, val subtitle: String, val due: String, val type: ContentType, val icon: String, val color: Long, val progress: Int = 0, val status: String = "Open")
data class DashboardMetric(val label: String, val value: String, val change: String)
data class Notice(val title: String, val detail: String, val time: String)

@Entity(tableName = "submissions")
data class SubmissionEntity(@PrimaryKey val id: String = "", val itemId: String = "", val studentId: String = "", val text: String = "", val fileName: String? = null, val status: String = "", val updatedAt: Long = 0)

@Entity(tableName = "personal_documents")
data class PersonalDocumentEntity(@PrimaryKey val id: String = "", val studentId: String = "", val name: String = "", val type: String = "", val createdAt: Long = 0)

@Entity(tableName = "cached_academic_items")
data class CachedAcademicItemEntity(@PrimaryKey val id: String = "", val studentId: String = "", val title: String = "", val subtitle: String = "", val due: String = "", val type: String = "", val icon: String = "", val color: Long = 0, val progress: Int = 0, val status: String = "")

@Entity(tableName = "timetable")
data class TimetableEvent(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val time: String = "",
    val location: String = "",
    val type: String = "",
    val color: Long = 0,
    val dayOfWeek: Int = 1
)

@Entity(tableName = "messages")
data class ChatMessage(
    @PrimaryKey val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = 0
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val facultyId: String = "",
    val facultyName: String = "",
    val dayOfWeek: Int = 1,
    val time: String = "",
    val note: String = "",
    val status: String = "Pending", // Pending, Approved, Completed, Cancelled
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "cv_applications")
data class CVApplication(
    @PrimaryKey val id: String = "",
    val candidateName: String = "",
    val candidateEmail: String = "",
    val fileName: String = "",
    val experience: Int = 0,
    val skills: List<String> = emptyList(),
    val education: String = "",
    val score: Int = 0,
    val status: String = "Applied", // Applied, Shortlisted, Interviewed, Rejected, Invited
    val salaryOffer: String = "",
    val shortlistReason: String = "",
    val appliedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "teacher_shortages")
data class TeacherShortage(
    @PrimaryKey val id: String = "",
    val department: String = "",
    val subject: String = "",
    val requiredCount: Int = 1,
    val status: String = "Open", // Open, Resolved
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val issuingOrg: String = "",
    val certificateUrl: String? = null,
    val isPublic: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class AppTheme { RED, BLUE, PURPLE }

data class AppPreferences(
    val theme: AppTheme = AppTheme.BLUE,
    val isDarkMode: Boolean = false,
    val fontSizeMultiplier: Float = 1.0f,
    val language: String = "en"
)
