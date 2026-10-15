package com.example.metier;

import com.example.dao.IDao;

public class MetierImpl implements IMetier {

    private IDao dao;


    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("Le DAO n'a pas été injecté !");
        }

        double valeurBasee = dao.getValue();

        double resultat = valeurBasee * 2;

        System.out.println(" [ METIER ] Calcul effectué. Résultat ! : " + resultat);
        return resultat;
    }
}