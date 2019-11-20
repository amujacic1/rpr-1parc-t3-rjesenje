package ba.unsa.etf.rpr;

import java.util.*;
import java.util.function.Function;

public class Preduzece {
    private int osnovica;
    private ArrayList<RadnoMjesto> radnaMjesta = new ArrayList<RadnoMjesto>();

    public int dajOsnovicu() {
        return osnovica;
    }

    public void postaviOsnovicu(int osnovica) throws NeispravnaOsnovica {
        if(osnovica <= 0) throw new NeispravnaOsnovica("Neispravna osnovica" + Integer.toString(osnovica));
        this.osnovica = osnovica;
    }
    public Preduzece(int osnovica){
        this.osnovica = osnovica;
        if(osnovica <= 0) throw new IllegalArgumentException();
    }
    public Preduzece(){}
    public void novoRadnoMjesto(RadnoMjesto rm){
        radnaMjesta.add(rm);
    }
    public void zaposli(Radnik radnik, String nazivRadnogMjesta){
        for(RadnoMjesto rm : radnaMjesta){
            if(rm.getNaziv().equals(nazivRadnogMjesta) && rm.getRadnik() == null){
                rm.setRadnik(radnik);
                return;
            }
        }
        throw new IllegalStateException("Nijedno radno mjesto tog tipa nije slobodno");
    }
    public void obracunajPlatu(){
        for(RadnoMjesto rm : radnaMjesta){
            if(rm.getRadnik() != null) {
                double plata = osnovica * rm.getKoeficijent();
                rm.getRadnik().dodajPlatu(plata);
            }
        }
    }
    public double iznosPlate(){
        int suma=0;
        for(RadnoMjesto rm : radnaMjesta){
            if(rm.getRadnik() != null){
                suma+=(osnovica * rm.getKoeficijent());
            }
        }
        return suma;
    }
    public Set<Radnik> radnici(){
        TreeSet<Radnik> sortRadnici = new TreeSet<>();
        for(RadnoMjesto rm : radnaMjesta){
            if(rm.getRadnik() != null){
                sortRadnici.add(rm.getRadnik());
            }
        }
        return sortRadnici;
    }
    public Map<RadnoMjesto, Integer> sistematizacija(){
        HashMap<RadnoMjesto, Integer> rezultat = new HashMap<>();
            for (RadnoMjesto rm : radnaMjesta) {
                Integer broj = rezultat.get(rm);
                if (broj == 0)
                    rezultat.put(rm, 1);
                else
                    rezultat.put(rm, broj + 1);
            }
            return rezultat;
        }

    public List<Radnik>  filterRadnici(Function<Radnik,Boolean> comp){
        ArrayList<Radnik> rezultat = new ArrayList<>();
        for(RadnoMjesto rm : radnaMjesta){
            if(rm.getRadnik() != null && comp.apply(rm.getRadnik())){
                rezultat.add(rm.getRadnik());
            }
        }
        return rezultat;
    }
    public List<Radnik> vecaProsjecnaPlata(double vrijednost){
        return filterRadnici((Radnik radnik) -> {return radnik.prosjecnaPlata() > vrijednost;});

    }
}

