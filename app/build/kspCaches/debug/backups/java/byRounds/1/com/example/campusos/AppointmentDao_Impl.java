package com.example.campusos;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppointmentDao_Impl implements AppointmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Appointment> __insertionAdapterOfAppointment;

  public AppointmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppointment = new EntityInsertionAdapter<Appointment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `appointments` (`id`,`studentId`,`studentName`,`facultyId`,`facultyName`,`dayOfWeek`,`time`,`note`,`status`,`timestamp`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Appointment entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getStudentId());
        statement.bindString(3, entity.getStudentName());
        statement.bindString(4, entity.getFacultyId());
        statement.bindString(5, entity.getFacultyName());
        statement.bindLong(6, entity.getDayOfWeek());
        statement.bindString(7, entity.getTime());
        statement.bindString(8, entity.getNote());
        statement.bindString(9, entity.getStatus());
        statement.bindLong(10, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object upsert(final Appointment item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAppointment.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Appointment>> observe(final String uid) {
    final String _sql = "SELECT * FROM appointments WHERE studentId = ? OR facultyId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uid);
    _argIndex = 2;
    _statement.bindString(_argIndex, uid);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"appointments"}, new Callable<List<Appointment>>() {
      @Override
      @NonNull
      public List<Appointment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfStudentName = CursorUtil.getColumnIndexOrThrow(_cursor, "studentName");
          final int _cursorIndexOfFacultyId = CursorUtil.getColumnIndexOrThrow(_cursor, "facultyId");
          final int _cursorIndexOfFacultyName = CursorUtil.getColumnIndexOrThrow(_cursor, "facultyName");
          final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Appointment> _result = new ArrayList<Appointment>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Appointment _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpStudentId;
            _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
            final String _tmpStudentName;
            _tmpStudentName = _cursor.getString(_cursorIndexOfStudentName);
            final String _tmpFacultyId;
            _tmpFacultyId = _cursor.getString(_cursorIndexOfFacultyId);
            final String _tmpFacultyName;
            _tmpFacultyName = _cursor.getString(_cursorIndexOfFacultyName);
            final int _tmpDayOfWeek;
            _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
            final String _tmpTime;
            _tmpTime = _cursor.getString(_cursorIndexOfTime);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new Appointment(_tmpId,_tmpStudentId,_tmpStudentName,_tmpFacultyId,_tmpFacultyName,_tmpDayOfWeek,_tmpTime,_tmpNote,_tmpStatus,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
