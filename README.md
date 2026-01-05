# 🎓 Öğrenci Ders Kayıt Sistemi (Student Course Registration System)

Bu proje, üniversite akademik süreçlerini simüle eden, **Nesne Yönelimli Programlama (OOP)** prensiplerine dayalı, Java ile geliştirilmiş kapsamlı bir otomasyon sistemidir. Veri kalıcılığı için dosya tabanlı (CSV) bir veritabanı yapısı kullanır.

## 🚀 Proje Hakkında

Bu sistem; öğrencilerin ders seçimi yapmalarını, akademisyenlerin ders açıp not girmelerini ve yöneticilerin kullanıcı yönetimini sağlamalarını amaçlar. Geleneksel kayıt süreçlerinde yaşanan çakışmalar, kontenjan sorunları ve bölüm uyuşmazlıkları gibi problemleri algoritmik kontrollerle çözer.

### 🛠️ Kullanılan Teknolojiler ve Araçlar
* **Dil:** Java (JDK 17+)
* **IDE:** Eclipse / IntelliJ IDEA
* **Test:** JUnit 5 (Birim Testleri)
* **Veri Yönetimi:** CSV Dosya Sistemi (File I/O)
* **Versiyon Kontrol:** Git & GitHub

---

## ✨ Özellikler

Sistem, kullanıcı rollerine göre ayrıştırılmış üç ana modülden oluşur:

### 1. 👨‍💼 Yönetici (Admin) Modülü
* **Kullanıcı Ekleme:** Sisteme yeni Öğrenci, Öğretim Görevlisi veya Yönetici ekleyebilir.
* **Kullanıcı Listeleme:** Sistemdeki tüm kayıtlı kullanıcıları rollerine göre listeler.
* **Rol Yönetimi:** Kullanıcıların bölümlerini ve yetkilerini tanımlar.

### 2. 👩‍🏫 Öğretim Görevlisi (Instructor) Modülü
* **Ders Açma:** Kendi bölümüne özel veya tüm bölümlere açık ("Ortak") dersler oluşturabilir.
* **Ders Yönetimi:** Verdiği dersleri listeler.
* **Öğrenci Listeleme:** Kendi dersine kayıtlı öğrencileri görüntüleyebilir.
* **Not Girişi:** Öğrencilere Vize ve Final notu girebilir (0-100 aralığı kontrolü ve otomatik güncelleme desteği ile).

### 3. 👨‍🎓 Öğrenci (Student) Modülü
* **Ders Kaydı:**
    * ✅ **Bölüm Kontrolü:** Sadece kendi bölümünün veya ortak havuzun derslerini seçebilir.
    * ✅ **Kontenjan Kontrolü:** Dolu derslere kayıt engellenir.
    * ✅ **Çakışma Kontrolü:** Aynı gün ve saatte çakışan dersleri seçemez.
* **Ders Bırakma (Drop):** Kayıtlı olduğu dersi listeden silebilir.
* **Transkript Görüntüleme:** Aldığı dersleri ve harf notlarını listeler.
* **GPA Hesaplama:** Ağırlıklı genel not ortalamasını otomatik görüntüler.
* **Lisansüstü Desteği:** `GraduateStudent` sınıfı ile tez konusu yönetimi ve farklı harç hesaplamaları sunar.

---

## 🏗️ Yazılım Mimarisi (OOP Prensipleri)

Proje, SOLID prensiplerine uygun olarak tasarlanmıştır:

* **Encapsulation (Kapsülleme):** Tüm sınıf değişkenleri `private` tutulmuş, erişim kontrollü getter/setter ve iş mantığı metotları ile sağlanmıştır.
* **Inheritance (Kalıtım):** `GraduateStudent` sınıfı `Student` sınıfından türetilmiştir.
* **Polymorphism (Çok Biçimlilik):** `calculateTuition()` metodu, alt sınıflarda ezilerek (Override) farklı davranışlar sergiler.
* **Abstraction (Soyutlama):** `Registrable` arayüzü (interface) kullanılarak kayıt yeteneği soyutlanmıştır.

---

## 📂 Dosya Yapısı

```bash
DersKayitSistemi/
├── src/
│   └── DersKayit/
│       ├── Main.java               # Uygulamanın giriş noktası (Menüler)
│       ├── Authentication.java     # Giriş ve kayıt işlemleri
│       ├── Student.java            # Öğrenci varlık sınıfı
│       ├── GraduateStudent.java    # Yüksek Lisans öğrencisi
│       ├── Instructor.java         # Öğretim görevlisi sınıfı
│       ├── Course.java             # Ders özelliklerini tutan sınıf
│       ├── GradeRecord.java        # Not hesaplama mantığı
│       ├── Registration.java       # Kayıt kuralları ve kontrolleri
│       ├── RegistrationManager.java # Kayıt verilerini yöneten sınıf
│       ├── GradeManager.java       # Not verilerini yöneten sınıf
│       ├── CourseCatalog.java      # Dersleri yöneten sınıf
│       └── Registrable.java        # Interface
├── users.csv                       # Kullanıcı veritabanı
├── courses.csv                     # Ders listesi veritabanı
├── registrations.csv               # Öğrenci-Ders eşleşmeleri
└── grades.csv                      # Not kayıtları

🧪 Testler (Unit Tests)
Sistemin kararlılığı JUnit 5 kütüphanesi ile test edilmiştir. Aşağıdaki kritik senaryolar başarıyla (Green Bar) geçilmiştir:

testDepartmentMismatch: Bilgisayar Müh. öğrencisinin Psikoloji dersini seçmesi engelleniyor mu? (Başarılı)

testCapacityFull: Kontenjanı 1 olan derse 2. kişi kayıt olmaya çalışınca sistem reddediyor mu? (Başarılı)

testCourseConflict: Pazartesi 10:00'da dersi olan öğrenci, aynı saate başka ders ekleyebiliyor mu? (Engellendi)

testInvalidGradeThrowsException: -5 veya 105 gibi not girişlerinde sistem hata veriyor mu? (Başarılı)

👨‍💻 Geliştirici Hakkında
Ad Soyad: Burç PERÇİN

Arel Üniversitesi Bilgisayar Mühendisliği 3. Sınıf Öğrencisi

Nesne Yönelimli Programlama Dönem Projesi

