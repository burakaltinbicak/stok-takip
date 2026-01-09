package com.babu.stoktakip.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok: Bize otomatik Get/Set metodlarını yazar (Kod kalabalığını önler)
@Entity // JPA: Bu sınıfın bir Veritabanı Tablosu olduğunu söyler
@Table(name = "urunler") // Veritabanında tablonun adı 'urunler' olsun
public class Urun {

    @Id // Bu sütun tablonun Birincil Anahtarıdır (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'yi 1, 2, 3 diye otomatik arttır
    private Long id;

    @Column(nullable = false) // Bu alan boş bırakılamaz
    private String ad; 

    private String aciklama; // İsteğe bağlı açıklama

    private Double fiyat; // 15.50 TL gibi

    private Integer stokAdedi; // 100 adet
}