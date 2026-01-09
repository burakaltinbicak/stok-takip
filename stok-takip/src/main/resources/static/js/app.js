// --- STOKMASTER PRO - FINAL SÜRÜM (DÜZELTİLMİŞ) ---

let tumUrunler = []; 

document.addEventListener("DOMContentLoaded", function() {
    urunleriGetirVeAnalizEt();
});

// 1. Ürünleri Getir ve İstatistikleri Doldur
function urunleriGetirVeAnalizEt() {
    fetch('/api/urunler')
        .then(response => response.json())
        .then(data => {
            tumUrunler = data; 
            
            // --- DÜZELTME BURADA: DİREKT YAZDIRMA ---
            
            // 1. Toplam Ürün Sayısı
            const totalElem = document.getElementById("totalProducts");
            if (totalElem) {
                totalElem.innerText = data.length; // Animasyonsuz, direkt sonuç
            }
            
            // 2. Kritik Stok Sayısı
            const lowStock = data.filter(u => u.stokAdedi < 30).length;
            const lowStockElem = document.getElementById("lowStockCount");
            if (lowStockElem) {
                lowStockElem.innerText = lowStock; // Animasyonsuz, direkt sonuç
            }

            // 3. Toplam Değer
            let toplamDeger = 0;
            data.forEach(u => toplamDeger += (u.fiyat * u.stokAdedi));
            const formattedDeger = new Intl.NumberFormat('tr-TR', { style: 'currency', currency: 'TRY' }).format(toplamDeger);
            
            const degerElem = document.querySelector(".stat-card:nth-child(2) h3");
            if(degerElem) degerElem.innerText = formattedDeger;

            // Tabloyu Doldur
            tabloyuDoldur(tumUrunler);
        });
}

// 2. Tablo Doldurma
function tabloyuDoldur(urunListesi) {
    const tablo = document.getElementById('urunTablosu');
    if(!tablo) return;
    tablo.innerHTML = "";
    
    if (urunListesi.length === 0) {
        tablo.innerHTML = `<tr><td colspan="5" class="text-center py-4" style="color: var(--text-secondary);">Veri bulunamadı.</td></tr>`;
        return;
    }

    urunListesi.forEach(urun => {
        const isLow = urun.stokAdedi < 30;
        const satir = `
            <tr>
                <td class="product-id">#${urun.id}</td>
                <td>
                    <div class="product-name">${urun.ad}</div>
                    <div class="product-desc">${urun.aciklama || '-'}</div>
                </td>
                <td class="product-price">${urun.fiyat.toFixed(2)} ₺</td>
                <td>
                    <div class="stock-indicator ${isLow ? 'stock-low' : 'stock-high'}">
                        <div class="dot"></div>${urun.stokAdedi} Adet
                    </div>
                </td>
                <td style="text-align: right;">
                    <button class="btn btn-sm btn-outline-warning me-2" onclick="duzenleModalAc(${urun.id})">
                        <i class="bi bi-pencil-fill"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="urunSil(${urun.id})">
                        <i class="bi bi-trash3-fill"></i>
                    </button>
                </td>
            </tr>`;
        tablo.innerHTML += satir;
    });
}

// 3. Arama Fonksiyonu
function aramaYap() {
    const kelime = document.getElementById('aramaKutusu').value.toLowerCase();
    const filtrelenmisUrunler = tumUrunler.filter(urun => 
        urun.ad.toLowerCase().includes(kelime) || 
        (urun.aciklama && urun.aciklama.toLowerCase().includes(kelime))
    );
    tabloyuDoldur(filtrelenmisUrunler);
}

// 4. KAYIT İŞLEMİ (SAYFA YENİLEME MANTIĞI)
function yeniUrunKaydet() {
    const veri = {
        ad: document.getElementById('urunAd').value,
        aciklama: document.getElementById('urunAciklama').value,
        fiyat: Number(document.getElementById('urunFiyat').value),
        stokAdedi: Number(document.getElementById('urunStok').value)
    };

    if(!veri.ad || veri.fiyat < 0) {
        Swal.fire({
            icon: 'error',
            title: 'Hata',
            text: 'Bilgileri kontrol ediniz.',
            background: '#1e293b', color: '#fff'
        });
        return;
    }

    fetch('/api/urunler', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(veri)
    })
    .then(response => {
        if (response.ok) {
            window.location.reload(); // Başarılıysa sayfayı yenile
        } else {
            Swal.fire({ icon: 'error', title: 'Hata', text: 'Sunucu hatası', background: '#1e293b', color: '#fff' });
        }
    });
}

// 5. SİLME İŞLEMİ
function urunSil(id) {
    Swal.fire({
        title: 'Siliniyor...',
        text: "Bu işlem geri alınamaz.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef4444',
        cancelButtonColor: '#64748b',
        confirmButtonText: 'Sil',
        cancelButtonText: 'Vazgeç',
        background: '#1e293b', color: '#fff'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/urunler/${id}`, { method: 'DELETE' })
            .then(response => {
                if(response.ok) window.location.reload();
            });
        }
    })
}

// 6. DÜZENLEME İŞLEMLERİ
function duzenleModalAc(id) {
    const urun = tumUrunler.find(u => u.id === id);
    document.getElementById('duzenleId').value = urun.id;
    document.getElementById('duzenleAd').value = urun.ad;
    document.getElementById('duzenleAciklama').value = urun.aciklama;
    document.getElementById('duzenleFiyat').value = urun.fiyat;
    document.getElementById('duzenleStok').value = urun.stokAdedi;
    
    const modalEl = document.getElementById('urunDuzenleModal');
    const modal = new bootstrap.Modal(modalEl);
    modal.show();
}

function urunGuncelle() {
    const id = document.getElementById('duzenleId').value;
    const veri = {
        ad: document.getElementById('duzenleAd').value,
        aciklama: document.getElementById('duzenleAciklama').value,
        fiyat: Number(document.getElementById('duzenleFiyat').value),
        stokAdedi: Number(document.getElementById('duzenleStok').value)
    };

    fetch(`/api/urunler/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(veri)
    }).then(response => {
        if(response.ok) window.location.reload();
    });
}