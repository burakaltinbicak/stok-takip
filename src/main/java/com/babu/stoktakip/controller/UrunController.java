package com.babu.stoktakip.controller;

import com.babu.stoktakip.entity.Urun;
import com.babu.stoktakip.service.UrunService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir Web API olduğunu söyler
@RequestMapping("/api/urunler") // Bu adrese gelen istekleri ben karşılarım (localhost:8080/api/urunler)
@RequiredArgsConstructor // Service'i otomatik bağlar
public class UrunController {

    private final UrunService urunService;

    // 1. GET İsteği: Tüm ürünleri listele
    // Adres: http://localhost:8080/api/urunler
    @GetMapping
    public List<Urun> tumUrunleriGetir() {
        return urunService.tumUrunleriGetir();
    }

    // 2. POST İsteği: Yeni ürün ekle
    // Adres: http://localhost:8080/api/urunler
    @PostMapping
    public Urun urunEkle(@RequestBody Urun urun) {
        // @RequestBody: Gelen isteğin içindeki JSON verisini Urun nesnesine çevirir
        return urunService.urunKaydet(urun);
    }

    // 3. DELETE İsteği: Ürün sil
    // Adres: http://localhost:8080/api/urunler/5 (5 numaralı ürünü sil)
    @DeleteMapping("/{id}")
    public void urunSil(@PathVariable Long id) {
        urunService.urunSil(id);
    }
    // 4. PUT İsteği: Mevcut ürünü güncelle (YENİ EKLİYORUZ)
    // Adres: http://localhost:8080/api/urunler/5
    @PutMapping("/{id}")
    public Urun urunGuncelle(@PathVariable Long id, @RequestBody Urun urun) {
        return urunService.urunGuncelle(id, urun);
    }
}