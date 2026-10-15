# **[EN]**
Adds the `/bottle` command, allowing players to convert their own experience points into Experience Bottles. This mod is server-side only—players can join with a completely vanilla client!

---

## Commands & Arguments

| Command | Description (EN) |
| :--- | :--- |
| `/experiencebottle <amount>` | Converts your EXP into the specified amount of Experience Bottles. |
| `/experiencebottle all` | Converts all of your current EXP into Experience Bottles until you run out. |

### Alias
The `/experiencebottle` command has several aliases. All of these aliases execute the same command.
* `/expbottle`
* `/bottle`
* `/bt`

### Conversion Mechanic
* **Vanilla-friendly cost:** Each bottle generated consumes a random amount of experience points, consistent with vanilla mechanics (3–11 XP).
* **Safe & Precise:** If your experience points run out, the system automatically and precisely reverts your level to the last known safe state, ensuring that no experience point duplication or excessive XP withdrawal can occur.

---

## Features
* **Server-side Only:** Players can join the server using a vanilla (unmodded) client and use the features immediately.
* **Multi-language Support:** The server automatically detects each player's client language setting (e.g., Japanese, English, etc.) and sends messages in the appropriate language.

---

# **[JA]**
自身の経験値をエンチャントの瓶に変換するコマンド `/bottle` を追加します。プレイヤー側にModやリソースパックの導入は一切不要（サーバーサイドのみ）で動作します。

---

## コマンドと引数の一覧

| コマンド | 説明 |
| :--- | :--- |
| `/bottle <amount>` | 指定した個数分、エンチャントの瓶に変換します。 |
| `/bottle all` | 所持しているすべての経験値を限界までエンチャントの瓶に変換します。 |

### コマンドのエイリアス(短縮形)
`/experiencebottle`コマンドにはいくつかのエイリアスが存在します。どのエイリアスでも同じコマンドを実行します。
* `/expbottle`
* `/bottle`
* `/bt`

### 変換の仕様
* **バニラ準拠のコスト (Vanilla-friendly cost):** 瓶1本を生成するごとに、バニラの仕様（3〜11 XP）に合わせたランダムな経験値コストが消費されます。
* **安全設計 (Safe & Precise):** 経験値が足りなくなった場合、直前の安全なレベル状態に自動で正確に巻き戻されるため、経験値の増殖や過剰な引き抜きは一切発生しません。

---

## 特徴
* **Server-side Only:** プレイヤーはバニラ（Modなし）でサーバーに参加してそのまま利用可能です。
* **Multi-language Support:** プレイヤーのクライアント言語設定（日本語/英語など）を自動検知して、サーバー側から適切な言語でメッセージを送信します。