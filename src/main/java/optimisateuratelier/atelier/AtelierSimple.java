/*
 * Copyright (C) 2022 IUT Laval - Le Mans Université.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package optimisateuratelier.atelier;

import java.util.Arrays;

/**
 *
 * @author Rémi Venant
 */
public class AtelierSimple implements Atelier {

    private final Chaine chaine0;
    private final Chaine chaine1;
    private final int[] tempsChangement0a1;
    private final int[] tempsChangement1a0;

    public AtelierSimple(Chaine chaine0, Chaine chaine1,
            int[] tempsChangement0a1, int[] tempsChangement1a0) {
        this.chaine0 = chaine0;
        this.chaine1 = chaine1;
        this.tempsChangement0a1 = tempsChangement0a1;
        this.tempsChangement1a0 = tempsChangement1a0;
        this.verifierCoherenceAterlier();
    }

    @Override
    public int getLongueurChaines() {
        return this.chaine0.getLongueur();
    }

    @Override
    public int getNbChaines() {
        return 2;
    }

    @Override
    public Chaine getChaine(int i) {
        switch (i) {
            case 0:
                return this.chaine0;
            case 1:
                return this.chaine1;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getTempsChangement(int indiceChaineA, int indiceChaineB, int indicePosteDepart) {
        if (indiceChaineA == 0) {
            if (indiceChaineB == 0) {
                return 0;
            } else {
                return this.tempsChangement0a1[indicePosteDepart];
            }
        }
        if (indiceChaineA == 1) {
            if (indiceChaineB == 1) {
                return 0;
            } else {
                return this.tempsChangement1a0[indicePosteDepart];
            }
        }
        throw new IllegalArgumentException("Mauvais indice de chaine");
    }

    private void verifierCoherenceAterlier() {
        if (this.chaine0.getLongueur() != this.chaine1.getLongueur()) {
            throw new IllegalArgumentException("Les chaines doivent être de même longueur");
        }
        final int tailleTpsChang = this.chaine0.getLongueur() - 1;
        if (this.tempsChangement0a1.length != tailleTpsChang
                || this.tempsChangement1a0.length != tailleTpsChang) {
            throw new IllegalArgumentException("Nombre de temps de changement invalide");
        }
        if (Arrays.stream(this.tempsChangement0a1).anyMatch(c -> c < 0)
                || Arrays.stream(this.tempsChangement1a0).anyMatch(c -> c < 0)) {
            throw new IllegalArgumentException("Les temps de changement doivent être positifs ou nuls");
        }
    }
}
