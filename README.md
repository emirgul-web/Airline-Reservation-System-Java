# ✈️ Airline Reservation & Management System

![Language](https://img.shields.io/badge/Language-Java-red)
![Concurrency](https://img.shields.io/badge/Concurrency-Multithreading-orange)
![Interface](https://img.shields.io/badge/UI-Swing-blue)

Bu proje, **Java** programlama dili kullanılarak geliştirilmiş, çoklu iş parçacığı (multithreading) ve Nesne Yönelimli Programlama (OOP) prensiplerini temel alan kapsamlı bir havayolu yönetim simülasyonudur.

Sistem, gerçek zamanlı koltuk rezervasyonunu simüle ederken **Race Condition** (Yarış Durumu) gibi eşzamanlılık problemlerini `synchronized` blokları ile çözer.

## 🚀 Proje Özellikleri

Standart otomasyonlardan farklı olarak bu projede şu teknik mimariler kurulmuştur:

* **Eşzamanlı Rezervasyon Motoru:** 90 farklı yolcuyu temsil eden thread'ler aynı anda sisteme giriş yapıp koltuk seçmeye çalışır. Veri tutarlılığı (Data Consistency) %100 sağlanmıştır.
* **OOP Mimarisi:** Uçak, Koltuk ve Yolcu arasındaki ilişkiler **Composition** (Bileşim) ve **Aggregation** (Kümeleme) prensiplerine göre tasarlanmıştır.
* **Asenkron Raporlama:** Raporlama işlemleri ana arayüzü (UI) dondurmadan arka planda (`Runnable`) çalışır.
* **Dinamik Fiyatlandırma:** Business ve Ekonomi sınıfları için ayrı fiyat hesaplama algoritmaları içerir.

## 🧠 Algoritma Mantığı

Sistemin kalbi olan **"Thread-Safe Rezervasyon"** şu şekilde işler:

1.  **Talep:** Birden fazla Thread (Yolcu) aynı anda `X` numaralı koltuğu talep eder.
2.  **Kilit (Lock):** İlk gelen Thread, `reserveSeat()` metodunu kilitler (`synchronized`).
3.  **Kontrol:** Algoritma koltuğun `isBooked` durumunu kontrol eder.
4.  **İşlem:** Eğer boşsa, koltuk o yolcuya atanır ve veritabanı güncellenir. Doluysa, diğer Thread'e hata fırlatılır.


