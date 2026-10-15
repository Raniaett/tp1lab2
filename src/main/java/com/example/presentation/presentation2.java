package com.example.presentation;


import com.example.dao.IDao;
import com.example.metier.IMetier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class presentation2 {

    public static void main(String[] args) {
        try {
            String[] classNamess = loadClassNames("config.txt");

            IDao dao = (IDao) instantiateClass(classNamess[0]);
            System.out.println(" [ CONTAINER ] DAO instancié : " + dao.getClass().getName());

            IMetier metier = (IMetier) instantiateClass(classNamess[1]);
            System.out.println(" [ CONTAINER ] Métier instancié : " + metier.getClass().getName());

            injectDependency(metier, "setDao", dao);

            double resultat = metier.calcul();
            System.out.println(" Résultats = " + resultat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] loadClassNames(String fileName) throws IOException {
        String[] classNamess = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < 2; i++) {
                classNamess[i] = reader.readLine().trim();
            }
        }
        return classNamess;
    }

    /**
     * Instancie une classe à partir de son nom complet.
     */
    private static Object instantiateClass(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        return constructor.newInstance();
    }


    private static void injectDependency(Object target, String setterName, Object dependency) throws Exception {
        Class<?> targetClass = target.getClass();
        Method setterMethod = targetClass.getMethod(setterName, dependency.getClass().getInterfaces()[0]);
        setterMethod.invoke(target, dependency);
    }
}