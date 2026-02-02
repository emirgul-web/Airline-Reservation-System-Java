✈️ Airline Reservation & Management System
Bu proje, Java programlama dili kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP), çoklu iş parçacığı (multithreading) ve birim test (Unit Testing) pratiklerini içeren kapsamlı bir havayolu yönetim simülasyonudur.

🚀 Öne Çıkan Teknik Özellikler
🧵 Çoklu İş Parçacığı (Multithreading) ve Eşzamanlılık
Projede iki ana eşzamanlı çalışma senaryosu uygulanmıştır:


Simültane Rezervasyon Kontrolü: 90 yolcunun (thread) aynı anda rastgele koltuk seçimi yaptığı senaryoda, synchronized yapısı kullanılarak "Race Condition" engellenmiş ve veri tutarlılığı sağlanmıştır.


Asenkron Raporlama: ReportGenerator sınıfı, Runnable arayüzü ile ağır raporlama işlemlerini ana GUI'yi bloklamadan arka planda yürütür.

🏗️ Yazılım Mimarisi (OOP)
Sistem, esnek ve sürdürülebilir bir yapı için Nesne Yönelimli Programlama prensipleri üzerine inşa edilmiştir:
+1


Bileşim (Composition): Plane ve Seat arasındaki ilişki ile koltukların uçağın ayrılmaz bir parçası olması sağlanmıştır.


Kümeleme (Aggregation): Flight ve Plane arasındaki bağımsız varlık ilişkisi kurgulanmıştır.

Merkezi Yönetim: Reservation sınıfı; Flight, Passenger ve Seat nesnelerini bir araya getiren merkezi bir bağlayıcı görevi görür.

🧪 Kalite ve Test (JUnit 5)
Sistemin kritik fonksiyonları 5 ana test senaryosu ile doğrulanmıştır:

Fiyat hesaplama mantığı (Ekonomi vs Business sınıfı farkları).

Şehir bazlı uçuş filtreleme motoru.

Rezervasyon sonrası koltuk sayısının doğru azalışı.

Hata yönetimi (Olmayan koltuk için istisna fırlatılması).

🛠️ Kullanılan Teknolojiler

Dil: Java (JDK 17+) 


Arayüz: Java Swing (Desktop UI) 


Test: JUnit 5 


Veri Depolama: Java Serialization (.dat dosyaları üzerinden kalıcı saklama)
