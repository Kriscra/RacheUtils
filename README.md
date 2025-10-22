# VizierUtils

VizierUtils; Paper/Spigot 1.21+ sürümleri için hazırlanmış, tek eklenti içinde birden çok yardımcı özelliği barındıran modüler bir yardımcı eklentidir. Her özellik ayrı sınıflara ayrılır ve çalışma zamanında açılıp kapatılabilir.

## Genel Yapı

- **Ana sınıf:** `com.viziercraft.utils.VizierUtilsPlugin`
- **Özellik yöneticisi:** `FeatureManager` config.yml altındaki `features` bölümünü okuyarak modülleri etkinleştirir veya devre dışı bırakır.
- **Komut sistemi:** `/utils` ana komutu; `reload`, `toggle`, `list` ve `info` alt komutları için `CommandManager` kullanır.
- **Depolama:** Oyuncu verileri `playerdata.yml` dosyasına kaydedilir. Ev, geri dönüş, vanish vb. durumlar kalıcıdır.
- **Mesajlar:** `messages.yml` dosyası tüm geri bildirimleri özelleştirmenize imkân tanır.

## Komutlar

| Komut | Açıklama | Yetki |
|-------|----------|-------|
| `/utils reload` | Konfigürasyon ve mesajları yeniden yükler | `vizierutils.reload` |
| `/utils toggle <özellik>` | Belirtilen özelliği aç/kapat | `vizierutils.toggle` |
| `/utils list` | Aktif/pasif özellikleri listeler | `vizierutils.list` |
| `/utils info` | Eklenti bilgilerini görüntüler | `vizierutils.info` |
| `/heal`, `/feed`, `/back`, `/home`, `/sethome`, `/hat`, `/workbench`, `/trash`, `/enderchest` | Oyuncu kolaylık komutları | `vizierutils.<komut>` |
| `/vanish`, `/freeze`, `/invsee`, `/mute`, `/kick`, `/ban`, `/tempban`, `/chatspy`, `/tpa`, `/tpahere`, `/tpaccept`, `/tpdeny` | Yönetim araçları | `vizierutils.<komut>` |
| `/broadcast <mesaj>` | Sunucu genel duyurusu | `vizierutils.broadcast` |

## Modüller

### Oyuncu Kolaylıkları
- Sağlık/açlık yenileme (`HealFeature`, `FeedFeature`)
- Geri dönme, ev sistemi (`BackFeature`, `HomeFeature`, `SetHomeFeature`)
- Kozmetik/yardımcı komutlar (`HatFeature`, `WorkbenchFeature`, `TrashFeature`, `EnderChestFeature`)

### Yönetici Araçları
- Vanish, dondurma, invsee, mute sistemi
- Kick/ban/tempban yönetimi
- ChatSpy ile sohbet gözetleme
- TPA sistemi (`/tpa`, `/tpahere`, `/tpaccept`, `/tpdeny`)

### Dünya & Çevre Kontrolleri
- AntiPhantom, NoCreeperGrief, AntiFireSpread, DisableEndermanGrief
- Hava/zaman kilidi, otomatik gündüz ve temiz hava

### Performans & Bilgi
- ClearLag ile düşen eşyaları periyodik temizleme
- TPS izleme ve konsol uyarısı
- Döngüsel otomatik mesaj yayınlama
- Motd özelleştirme, hoş geldin mesajı ve title desteği

## Konfigürasyon
`config.yml` dosyasında tüm özellikler `features` altından yönetilir ve diğer bölümlerden periyotlar, mesajlar ve hedef dünyalar ayarlanır.

## Mesajlar
Tüm oyuncu ve yönetici geri bildirimleri `messages.yml` içinde bulunur; Türkçe varsayılan metinler renklendirilmiş olarak gelir.

## Bağımlılıklar
- Spigot/Paper API 1.21
- PlaceholderAPI (opsiyonel, mevcutsa otomatik etkinleşir)

Projeyi derlemek için Java 21 ile `mvn package` komutunu kullanabilirsiniz. İlk çalıştırmada `config.yml`, `messages.yml` ve `playerdata.yml` otomatik olarak oluşturulur.
