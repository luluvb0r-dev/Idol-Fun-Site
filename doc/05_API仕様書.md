# API仕様書

## 改訂履歴

| Version | Date       | Author | Changes |
|---------|------------|--------|---------|
| 0.1.0   | 2026-03-22 | OpenAI | 初版作成 |

---

## 1. 文書概要
本書は、=LOVE ファンサイト向けの REST API 仕様書である。  
今回は参照系 API のみを対象とする。  
将来的なマルチサイト対応を想定し、全 API は `siteKey` を含む構成とする。

---

## 2. 共通仕様

## 2.1 Base URL
```text
/api/v1
```

## 2.2 Content-Type
```text
application/json
```

## 2.3 共通レスポンス形式
### 正常系
```json
{
  "data": {},
  "meta": {
    "requestId": "8b4c9f40-1fd5-4c31-9f97-2c0b9d728001",
    "version": "v1"
  },
  "errors": []
}
```

### 異常系
```json
{
  "data": null,
  "meta": {
    "requestId": "8b4c9f40-1fd5-4c31-9f97-2c0b9d728001",
    "version": "v1"
  },
  "errors": [
    {
      "code": "RESOURCE_NOT_FOUND",
      "message": "Song not found."
    }
  ]
}
```

## 2.4 ステータスコード
| Code | Meaning |
|------|---------|
| 200  | 正常 |
| 400  | リクエスト不正 |
| 404  | データなし |
| 500  | サーバエラー |

---

## 3. API一覧

| Method | Path | Description |
|--------|------|-------------|
| GET | `/sites/{siteKey}` | サイト情報取得 |
| GET | `/sites/{siteKey}/songs` | 楽曲一覧取得 |
| GET | `/sites/{siteKey}/songs/{songId}` | 楽曲詳細取得 |
| GET | `/sites/{siteKey}/songs/{songId}/calls` | 楽曲コール取得 |
| GET | `/sites/{siteKey}/members` | メンバー一覧取得 |
| GET | `/sites/{siteKey}/members/{memberId}` | メンバー詳細取得 |
| GET | `/sites/{siteKey}/singles` | シングル一覧取得 |

---

## 4. API詳細

## 4.1 サイト情報取得

### Request
```http
GET /api/v1/sites/{siteKey}
```

### Path Parameter
| Name | Type | Required | Description |
|------|------|----------|-------------|
| siteKey | string | Y | サイト識別子 |

### Response Example
```json
{
  "data": {
    "siteKey": "equal-love",
    "siteName": "=LOVE Fan Site",
    "idolName": "=LOVE",
    "theme": {
      "primaryColorHex": "#F8AFCB",
      "secondaryColorHex": "#FFFFFF",
      "accentColorHex": "#F06292"
    }
  },
  "meta": {
    "requestId": "req-001",
    "version": "v1"
  },
  "errors": []
}
```

---

## 4.2 楽曲一覧取得

### Request
```http
GET /api/v1/sites/{siteKey}/songs
```

### Query Parameter
| Name | Type | Required | Description |
|------|------|----------|-------------|
| keyword | string | N | 楽曲名検索 |
| singleId | number | N | シングル絞り込み |
| isTitleTrack | boolean | N | 表題曲絞り込み |
| memberId | number | N | メンバー絞り込み |
| page | number | N | ページ番号 |
| size | number | N | 取得件数 |
| sort | string | N | 並び順。例: `releaseDate,desc` |

### Response Example
```json
{
  "data": {
    "items": [
      {
        "songId": 101,
        "title": "サンプル楽曲",
        "titleKana": "さんぷるがっきょく",
        "single": {
          "singleId": 11,
          "title": "サンプルシングル",
          "releaseDate": "2025-01-01"
        },
        "isTitleTrack": true,
        "originalMembers": [
          {
            "memberId": 1,
            "name": "メンバーA"
          },
          {
            "memberId": 2,
            "name": "メンバーB"
          }
        ],
        "hasCallData": true
      }
    ],
    "pagination": {
      "page": 0,
      "size": 20,
      "totalElements": 1,
      "totalPages": 1
    }
  },
  "meta": {
    "requestId": "req-002",
    "version": "v1"
  },
  "errors": []
}
```

### 実装上の注意
- 一覧で必要な情報は 1 API で完結させる
- フロントがそのままカード描画できる DTO を返す
- 検索対象に `title`, `title_kana`, `title_roman` を将来含められる設計にする

---

## 4.3 楽曲詳細取得

### Request
```http
GET /api/v1/sites/{siteKey}/songs/{songId}
```

### Path Parameter
| Name | Type | Required | Description |
|------|------|----------|-------------|
| siteKey | string | Y | サイト識別子 |
| songId | number | Y | 楽曲ID |

### Response Example
```json
{
  "data": {
    "songId": 101,
    "title": "サンプル楽曲",
    "titleKana": "さんぷるがっきょく",
    "description": "楽曲の簡単な説明",
    "isTitleTrack": true,
    "hasCallData": true,
    "single": {
      "singleId": 11,
      "title": "サンプルシングル",
      "releaseDate": "2025-01-01"
    },
    "originalMembers": [
      {
        "memberId": 1,
        "name": "メンバーA",
        "memberColorHex": "#FF7BAC"
      }
    ]
  },
  "meta": {
    "requestId": "req-003",
    "version": "v1"
  },
  "errors": []
}
```

---

## 4.4 楽曲コール取得

### Request
```http
GET /api/v1/sites/{siteKey}/songs/{songId}/calls
```

### Response Example
```json
{
  "data": {
    "songId": 101,
    "title": "サンプル楽曲",
    "blocks": [
      {
        "blockId": 1001,
        "blockType": "VERSE",
        "blockLabel": "Aメロ",
        "orderNo": 1,
        "lines": [
          {
            "lineId": 5001,
            "lineNo": 1,
            "lyrics": "君に会えたその日から",
            "calls": [
              {
                "callId": 7001,
                "callType": "CHANT",
                "callText": "はい！",
                "styleColorHex": "#F06292"
              }
            ]
          },
          {
            "lineId": 5002,
            "lineNo": 2,
            "lyrics": "世界が少し変わった",
            "calls": [
              {
                "callId": 7002,
                "callType": "CLAP",
                "callText": "クラップ×2",
                "styleColorHex": "#7E8CE0"
              }
            ]
          }
        ]
      }
    ]
  },
  "meta": {
    "requestId": "req-004",
    "version": "v1"
  },
  "errors": []
}
```

### callType の定義
| Value | Meaning |
|-------|---------|
| CHANT | 掛け声 |
| CLAP | クラップ |
| MIX | MIX |
| JUMP | ジャンプ |
| OTHER | その他 |

### 実装上の注意
- 歌詞とコールの表示順を厳密に保証する
- フロントは `callType` と `styleColorHex` を利用して描画する
- 色分けだけに依存せずラベルも出せるよう、必ず `callType` を返す

---

## 4.5 メンバー一覧取得

### Request
```http
GET /api/v1/sites/{siteKey}/members
```

### Query Parameter
| Name | Type | Required | Description |
|------|------|----------|-------------|
| status | string | N | ACTIVE など |
| sort | string | N | 並び順。例: `displayOrder,asc` |

### Response Example
```json
{
  "data": {
    "items": [
      {
        "memberId": 1,
        "name": "メンバーA",
        "birthday": "2000-01-01",
        "memberColor": "Pink",
        "memberColorHex": "#F8AFCB",
        "profileImageUrl": "https://example.com/member-a.jpg"
      }
    ]
  },
  "meta": {
    "requestId": "req-005",
    "version": "v1"
  },
  "errors": []
}
```

---

## 4.6 メンバー詳細取得

### Request
```http
GET /api/v1/sites/{siteKey}/members/{memberId}
```

### Response Example
```json
{
  "data": {
    "memberId": 1,
    "name": "メンバーA",
    "birthday": "2000-01-01",
    "memberColor": "Pink",
    "memberColorHex": "#F8AFCB",
    "birthplace": "東京都",
    "generationLabel": "1期",
    "joinedOn": "2017-01-01",
    "shortBio": "明るくやさしい雰囲気が魅力",
    "profileImageUrl": "https://example.com/member-a.jpg"
  },
  "meta": {
    "requestId": "req-006",
    "version": "v1"
  },
  "errors": []
}
```

---

## 4.7 シングル一覧取得

### Request
```http
GET /api/v1/sites/{siteKey}/singles
```

### Response Example
```json
{
  "data": {
    "items": [
      {
        "singleId": 11,
        "title": "サンプルシングル",
        "releaseDate": "2025-01-01",
        "releaseType": "SINGLE",
        "jacketImageUrl": "https://example.com/jacket.jpg"
      }
    ]
  },
  "meta": {
    "requestId": "req-007",
    "version": "v1"
  },
  "errors": []
}
```

---

## 5. バリデーション方針

- `siteKey`: 必須、存在チェック
- `songId`: 数値、存在チェック、該当サイト配下であること
- `memberId`: 数値、存在チェック、該当サイト配下であること
- `page`, `size`: 0 以上の数値
- `sort`: 許可カラムのみ受付

---

## 6. エラーコード案

| Code | Meaning |
|------|---------|
| INVALID_REQUEST | リクエスト不正 |
| SITE_NOT_FOUND | サイト未存在 |
| SONG_NOT_FOUND | 楽曲未存在 |
| MEMBER_NOT_FOUND | メンバー未存在 |
| SINGLE_NOT_FOUND | シングル未存在 |
| INTERNAL_SERVER_ERROR | サーバ内部エラー |

---

## 7. 将来拡張案
今回のスコープ外だが、将来以下を追加しやすいように命名と責務を整理すること。

- 管理用 CRUD API
- サイト作成 API
- 認証 / 認可
- ファン投稿コール API
- テーマ切替 API
- グループ / 期別 API

---

## 8. バージョニング方針

### 現在
- `v1`

### 方針
- 破壊的変更時は `v2` を新設
- 非破壊追加は `v1` 内で後方互換を維持
- 仕様書改訂履歴は必ず更新する
