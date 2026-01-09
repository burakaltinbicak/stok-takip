package com.babu.stoktakip.service;

import com.babu.stoktakip.entity.Urun;
import com.babu.stoktakip.repository.UrunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List; // Dikkat: java.awt.List DEĞİL, java.util.List olacak

@Service // Spring'e diyoruz ki: "Bu sınıf iş mantığını yapan servistir"
@RequiredArgsConstructor // Lombok: Repository'i otomatik olarak buraya bağlar (Enjekte eder)
public class UrunService {

    private final UrunRepository urunRepository;

    // 1. Tüm listeyi getiren metod
    public List<Urun> tumUrunleriGetir() {
        return urunRepository.findAll();
    }

    // 2. Ürün kaydeden metod
    public Urun urunKaydet(Urun urun) {
        // İleride buraya "if (fiyat < 0) hata ver" gibi kurallar yazacağız
        return urunRepository.save(urun);
    }

    // 3. Ürün silen metod
    public void urunSil(Long id) {
        urunRepository.deleteById(id);
    }
    
    // 4. Belirli bir ürünü id ile bulma
    public Urun urunBul(Long id) {
         // Eğer bulamazsa null döner veya hata fırlatırız (Şimdilik basit tutalım)
        return urunRepository.findById(id).orElse(null);
    }
    // 5. Ürün Güncelleme Metodu (YENİ EKLİYORUZ)
    public Urun urunGuncelle(Long id, Urun yeniBilgiler) {
        // Önce veritabanında bu ürün var mı diye bakıyoruz
        Urun mevcutUrun = urunRepository.findById(id).orElse(null);
        
        if (mevcutUrun != null) {
            // Varsa bilgilerini değiştiriyoruz
            mevcutUrun.setAd(yeniBilgiler.getAd());
            mevcutUrun.setAciklama(yeniBilgiler.getAciklama());
            mevcutUrun.setFiyat(yeniBilgiler.getFiyat());
            mevcutUrun.setStokAdedi(yeniBilgiler.getStokAdedi());
            // Ve tekrar kaydediyoruz (Update işlemi yapar)
            return urunRepository.save(mevcutUrun);
        }
        return null; // Ürün yoksa null döner
    }
}