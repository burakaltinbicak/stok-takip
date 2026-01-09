package com.babu.stoktakip.config;

import com.babu.stoktakip.entity.Urun;
import com.babu.stoktakip.repository.UrunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Spring'e diyoruz ki: "Bu sınıfı tanı ve başlangıçta çalıştır"
@RequiredArgsConstructor
public class VeriYukleyici implements CommandLineRunner {

    private final UrunRepository urunRepository;

    @Override
    public void run(String... args) throws Exception {
        // Eğer veritabanı boşsa test verilerini ekle
        if (urunRepository.count() == 0) {
            
            Urun urun1 = new Urun();
            urun1.setAd("Çay");
            urun1.setAciklama("Rize Turist Çayı 1kg");
            urun1.setFiyat(120.50);
            urun1.setStokAdedi(50);
            urunRepository.save(urun1);

            Urun urun2 = new Urun();
            urun2.setAd("Kahve");
            urun2.setAciklama("Filtre Kahve 250gr");
            urun2.setFiyat(180.00);
            urun2.setStokAdedi(20);
            urunRepository.save(urun2);

            Urun urun3 = new Urun();
            urun3.setAd("Şeker");
            urun3.setAciklama("Küp Şeker 1kg");
            urun3.setFiyat(45.00);
            urun3.setStokAdedi(100);
            urunRepository.save(urun3);

            System.out.println("--- Test Verileri Yüklendi ---");
        }
    }
}