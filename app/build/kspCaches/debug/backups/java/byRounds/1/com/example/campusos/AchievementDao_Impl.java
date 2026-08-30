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
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Achievement> __insertionAdapterOfAchievement;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAchievement = new EntityInsertionAdapter<Achievement>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `achievements` (`id`,`userId`,`title`,`description`,`date`,`issuingOrg`,`certificateUrl`,`isPublic`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Achievement entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        statement.bindString(4, entity.getDescription());
        statement.bindString(5, entity.getDate());
        statement.bindString(6, entity.getIssuingOrg());
        if (entity.getCertificateUrl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCertificateUrl());
        }
        final int _tmp = entity.isPublic() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object upsert(final Achievement item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAchievement.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Achievement> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAchievement.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Achievement>> observeByUser(final String userId) {
    final String _sql = "SELECT * FROM achievements WHERE userId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIssuingOrg = CursorUtil.getColumnIndexOrThrow(_cursor, "issuingOrg");
          final int _cursorIndexOfCertificateUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "certificateUrl");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpIssuingOrg;
            _tmpIssuingOrg = _cursor.getString(_cursorIndexOfIssuingOrg);
            final String _tmpCertificateUrl;
            if (_cursor.isNull(_cursorIndexOfCertificateUrl)) {
              _tmpCertificateUrl = null;
            } else {
              _tmpCertificateUrl = _cursor.getString(_cursorIndexOfCertificateUrl);
            }
            final boolean _tmpIsPublic;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Achievement(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpDate,_tmpIssuingOrg,_tmpCertificateUrl,_tmpIsPublic,_tmpCreatedAt);
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
  public Flow<List<Achievement>> observePublic() {
    final String _sql = "SELECT * FROM achievements WHERE isPublic = 1 ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<Achievement>>() {
      @Override
      @NonNull
      public List<Achievement> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIssuingOrg = CursorUtil.getColumnIndexOrThrow(_cursor, "issuingOrg");
          final int _cursorIndexOfCertificateUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "certificateUrl");
          final int _cursorIndexOfIsPublic = CursorUtil.getColumnIndexOrThrow(_cursor, "isPublic");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Achievement> _result = new ArrayList<Achievement>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Achievement _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpIssuingOrg;
            _tmpIssuingOrg = _cursor.getString(_cursorIndexOfIssuingOrg);
            final String _tmpCertificateUrl;
            if (_cursor.isNull(_cursorIndexOfCertificateUrl)) {
              _tmpCertificateUrl = null;
            } else {
              _tmpCertificateUrl = _cursor.getString(_cursorIndexOfCertificateUrl);
            }
            final boolean _tmpIsPublic;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPublic);
            _tmpIsPublic = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Achievement(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpDate,_tmpIssuingOrg,_tmpCertificateUrl,_tmpIsPublic,_tmpCreatedAt);
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
