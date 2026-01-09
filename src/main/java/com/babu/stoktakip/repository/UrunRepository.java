package com.babu.stoktakip.repository;

import com.babu.stoktakip.entity.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring'e diyoruz ki: "Veritabanı işleri benden sorulur"
public interface UrunRepository extends JpaRepository<Urun, Long> {
    // Buraya hiçbir şey yazmamıza gerek yok!
    // Sadece 'extends JpaRepository' diyerek şu yetenekleri kazandık:
    // .save()   -> Kaydet
    // .findAll() -> Hepsini getir
    // .delete()  -> Sil
    // .findById() -> Bul
}