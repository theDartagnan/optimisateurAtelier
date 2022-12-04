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

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Rémi Venant
 */
public class AtelierComplexe implements Atelier {

    private final ArrayList<Chaine> chaines;
    private final int[][][] tempsChangement;

    public AtelierComplexe(ArrayList<Chaine> chaines,
            int[][][] tempsChangement) {
        this.chaines = chaines;
        this.tempsChangement = tempsChangement;
        this.verifierCoherenceAtelier();
    }

    @Override
    public int getLongueurChaines() {
        return this.chaines.get(0).getLongueur();
    }

    @Override
    public int getNbChaines() {
        return this.chaines.size();
    }

    @Override
    public Chaine getChaine(int i) {
        return this.chaines.get(i);
    }

    @Override
    public int getTempsChangement(int indiceChaineA, int indiceChaineB, int indicePosteDepart) {
        if (indiceChaineA == indiceChaineB) {
            return 0;
        }
        return this.tempsChangement[indiceChaineA][indiceChaineB][indicePosteDepart];
    }

    private void verifierCoherenceAtelier() {
        if (this.chaines == null || this.chaines.size() < 2
                || this.tempsChangement == null
                || this.tempsChangement.length < 2) {
            throw new IllegalArgumentException("chaines et tempsChangement ne peuvent ni être null ni de taille < 2");
        }
        final int longueurChaines = this.chaines.get(0).getLongueur();
        if (this.chaines.stream().anyMatch(c -> c.getLongueur() != longueurChaines)) {
            throw new IllegalArgumentException("Les chaines doivent être de même longueur");
        }
        final int nbChaines = this.chaines.size();
        final int nbTpsChgtParChaine = longueurChaines - 1;
        if (Arrays.stream(this.tempsChangement)
                .anyMatch(tcChain -> tcChain.length != nbChaines
                || Arrays.stream(tcChain).anyMatch(
                        tcChainChain -> tcChainChain.length != nbTpsChgtParChaine))) {
            throw new IllegalArgumentException("Nombre de temps de changement invalide");
        }

        if (Arrays.stream(this.tempsChangement)
                .flatMapToInt(ctToChains -> Arrays.stream(ctToChains).flatMapToInt(Arrays::stream))
                .anyMatch(tc -> tc < 0)) {
            throw new IllegalArgumentException("Les temps de changement doivent être positifs ou nuls");
        }
    }
}
