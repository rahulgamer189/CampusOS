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
public final class CVApplicationDao_Impl implements CVApplicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CVApplication> __insertionAdapterOfCVApplication;

  private final Converters __converters = new Converters();

  public CVApplicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCVApplication = new EntityInsertionAdapter<CVApplication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cv_applications` (`id`,`candidateName`,`candidateEmail`,`fileName`,`experience`,`skills`,`education`,`score`,`status`,`salaryOffer`,`shortlistReason`,`appliedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CVApplication entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCandidateName());
        statement.bindString(3, entity.getCandidateEmail());
        statement.bindString(4, entity.getFileName());
        statement.bindLong(5, entity.getExperience());
        final String _tmp = __converters.fromStringList(entity.getSkills());
        statement.bindString(6, _tmp);
        statement.bindString(7, entity.getEducation());
        statement.bindLong(8, entity.getScore());
        statement.bindString(9, entity.getStatus());
        statement.bindString(10, entity.getSalaryOffer());
        statement.bindString(11, entity.getShortlistReason());
        statement.bindLong(12, entity.getAppliedAt());
      }
    };
  }

  @Override
  public Object upsert(final CVApplication item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCVApplication.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<CVApplication> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCVApplication.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CVApplication>> observeAll() {
    final String _sql = "SELECT * FROM cv_applications ORDER BY appliedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cv_applications"}, new Callable<List<CVApplication>>() {
      @Override
      @NonNull
      public List<CVApplication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCandidateName = CursorUtil.getColumnIndexOrThrow(_cursor, "candidateName");
          final int _cursorIndexOfCandidateEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "candidateEmail");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "experience");
          final int _cursorIndexOfSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "skills");
          final int _cursorIndexOfEducation = CursorUtil.getColumnIndexOrThrow(_cursor, "education");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalaryOffer = CursorUtil.getColumnIndexOrThrow(_cursor, "salaryOffer");
          final int _cursorIndexOfShortlistReason = CursorUtil.getColumnIndexOrThrow(_cursor, "shortlistReason");
          final int _cursorIndexOfAppliedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedAt");
          final List<CVApplication> _result = new ArrayList<CVApplication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CVApplication _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCandidateName;
            _tmpCandidateName = _cursor.getString(_cursorIndexOfCandidateName);
            final String _tmpCandidateEmail;
            _tmpCandidateEmail = _cursor.getString(_cursorIndexOfCandidateEmail);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final int _tmpExperience;
            _tmpExperience = _cursor.getInt(_cursorIndexOfExperience);
            final List<String> _tmpSkills;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkills);
            _tmpSkills = __converters.toStringList(_tmp);
            final String _tmpEducation;
            _tmpEducation = _cursor.getString(_cursorIndexOfEducation);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpSalaryOffer;
            _tmpSalaryOffer = _cursor.getString(_cursorIndexOfSalaryOffer);
            final String _tmpShortlistReason;
            _tmpShortlistReason = _cursor.getString(_cursorIndexOfShortlistReason);
            final long _tmpAppliedAt;
            _tmpAppliedAt = _cursor.getLong(_cursorIndexOfAppliedAt);
            _item = new CVApplication(_tmpId,_tmpCandidateName,_tmpCandidateEmail,_tmpFileName,_tmpExperience,_tmpSkills,_tmpEducation,_tmpScore,_tmpStatus,_tmpSalaryOffer,_tmpShortlistReason,_tmpAppliedAt);
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

  @Override
  public Flow<List<CVApplication>> observeByEmail(final String email) {
    final String _sql = "SELECT * FROM cv_applications WHERE candidateEmail = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cv_applications"}, new Callable<List<CVApplication>>() {
      @Override
      @NonNull
      public List<CVApplication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCandidateName = CursorUtil.getColumnIndexOrThrow(_cursor, "candidateName");
          final int _cursorIndexOfCandidateEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "candidateEmail");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfExperience = CursorUtil.getColumnIndexOrThrow(_cursor, "experience");
          final int _cursorIndexOfSkills = CursorUtil.getColumnIndexOrThrow(_cursor, "skills");
          final int _cursorIndexOfEducation = CursorUtil.getColumnIndexOrThrow(_cursor, "education");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalaryOffer = CursorUtil.getColumnIndexOrThrow(_cursor, "salaryOffer");
          final int _cursorIndexOfShortlistReason = CursorUtil.getColumnIndexOrThrow(_cursor, "shortlistReason");
          final int _cursorIndexOfAppliedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedAt");
          final List<CVApplication> _result = new ArrayList<CVApplication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CVApplication _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCandidateName;
            _tmpCandidateName = _cursor.getString(_cursorIndexOfCandidateName);
            final String _tmpCandidateEmail;
            _tmpCandidateEmail = _cursor.getString(_cursorIndexOfCandidateEmail);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final int _tmpExperience;
            _tmpExperience = _cursor.getInt(_cursorIndexOfExperience);
            final List<String> _tmpSkills;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSkills);
            _tmpSkills = __converters.toStringList(_tmp);
            final String _tmpEducation;
            _tmpEducation = _cursor.getString(_cursorIndexOfEducation);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpSalaryOffer;
            _tmpSalaryOffer = _cursor.getString(_cursorIndexOfSalaryOffer);
            final String _tmpShortlistReason;
            _tmpShortlistReason = _cursor.getString(_cursorIndexOfShortlistReason);
            final long _tmpAppliedAt;
            _tmpAppliedAt = _cursor.getLong(_cursorIndexOfAppliedAt);
            _item = new CVApplication(_tmpId,_tmpCandidateName,_tmpCandidateEmail,_tmpFileName,_tmpExperience,_tmpSkills,_tmpEducation,_tmpScore,_tmpStatus,_tmpSalaryOffer,_tmpShortlistReason,_tmpAppliedAt);
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
