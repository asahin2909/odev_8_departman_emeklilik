package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

class Personel {
    String ad;
    String soyad;
    String dogumTarihi;

    public Personel(String ad, String soyad, String dogumTarihi) {
        this.ad = ad;
        this.soyad = soyad;
        this.dogumTarihi = dogumTarihi;
    }

    public int emeklilikYasiHesapla() {
        return 0; // Bu metod alt sınıflarda tekrar tanımlanacak
    }

    public boolean emekliOlabilirMi() {
        int yas = mevcutYasiHesapla();
        return yas >= emeklilikYasiHesapla();
    }

    int mevcutYasiHesapla() {
        LocalDate bugun = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dogumTarih = LocalDate.parse(this.dogumTarihi, formatter);
        return Period.between(dogumTarih, bugun).getYears();
    }
}

class Erkek extends Personel {
    public Erkek(String ad, String soyad, String dogumTarihi) {
        super(ad, soyad, dogumTarihi);
    }

    @Override
    public int emeklilikYasiHesapla() {
        return 65;
    }
}

class Kadin extends Personel {
    public Kadin(String ad, String soyad, String dogumTarihi) {
        super(ad, soyad, dogumTarihi);
    }

    @Override
    public int emeklilikYasiHesapla() {
        return 60;
    }
}

class Departman {
    Erkek takimLideri;
    List<Personel> personelListesi;
    List<String> gorevListesi;

    public Departman(Erkek takimLideri) {
        this.takimLideri = takimLideri;
        this.personelListesi = new ArrayList<>();
        this.gorevListesi = new ArrayList<>();
    }

    public void takimLideriniDegistir(Erkek yeniTakimLideri) {
        this.takimLideri = yeniTakimLideri;
    }

    public void personelEkle(Personel personel) {
        this.personelListesi.add(personel);
    }

    public void personelCikar(Personel personel) {
        this.personelListesi.remove(personel);
    }

    public void personelDuzenle(Personel eskiPersonel, Personel yeniPersonel) {
        int index = this.personelListesi.indexOf(eskiPersonel);
        if (index != -1) {
            this.personelListesi.set(index, yeniPersonel);
        }
    }

    public void gorevEkle(String gorev) {
        this.gorevListesi.add(gorev);
    }

    public void goreviTamamlandiOlarakIsaretle(String gorev) {
        this.gorevListesi.remove(gorev);
    }
}

public class Main {
    public static void main(String[] args) {
        Erkek takimLideri = new Erkek("Abdullah", "Şahin", "01/01/1955");
        Departman departman = new Departman(takimLideri);

        Kadin personel1 = new Kadin("Merve", "Şahin", "01/01/1990");
        departman.personelEkle(personel1);

        departman.gorevEkle("Görev 1");
        departman.gorevEkle("Görev 2");

        System.out.println("Takım Lideri: " + departman.takimLideri.ad + " " + departman.takimLideri.soyad +
                ", Mevcut Yaş: " + departman.takimLideri.mevcutYasiHesapla() +
                ", Emeklilik Yaşı: " + departman.takimLideri.emeklilikYasiHesapla() +
                ", Emekli Olabilir Mi?: " + (departman.takimLideri.emekliOlabilirMi() ? "Emekli olabilir" : "Emekli olamaz"));
        System.out.println("Personel Listesi: " + departman.personelListesi.get(0).ad + " " +
                departman.personelListesi.get(0).soyad +
                ", Mevcut Yaş: " + departman.personelListesi.get(0).mevcutYasiHesapla() +
                ", Emeklilik Yaşı: " + departman.personelListesi.get(0).emeklilikYasiHesapla() +
                ", Emekli Olabilir Mi?: " + (departman.personelListesi.get(0).emekliOlabilirMi() ? "Emekli olabilir" : "Emekli olamaz"));
        System.out.println("Görevler: " + departman.gorevListesi);

        departman.goreviTamamlandiOlarakIsaretle("Görev 1");
        System.out.println("Görevler, Görev 1 tamamlandı olarak işaretlendikten sonra: " + departman.gorevListesi);
    }
}
