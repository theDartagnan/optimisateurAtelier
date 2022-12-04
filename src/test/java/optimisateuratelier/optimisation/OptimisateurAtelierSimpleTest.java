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
package optimisateuratelier.optimisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import optimisateuratelier.atelier.AtelierSimple;
import optimisateuratelier.atelier.Chaine;
import optimisateuratelier.atelier.ChaineImpl;
import optimisateuratelier.atelier.PosteImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rémi Venant
 */
public class OptimisateurAtelierSimpleTest {

    private OptimisateurAtelier<AtelierSimple> optimisateurTest;
    private AtelierSimple atelierTest;
    private ResultatOptimisation expectedResult;

    public OptimisateurAtelierSimpleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        System.out.println("COCOCOCOCOCOCO");
        this.generateAtelierTest();
        this.optimisateurTest = new OptimisateurAtelierSimple();
    }

    @AfterEach
    public void tearDown() {
    }

    private void generateAtelierTest() {
        final Chaine c0 = new ChaineImpl(
                new ArrayList<>(Arrays.asList(10, 5, 17, 4, 7).stream()
                        .map(tps -> new PosteImpl(tps))
                        .collect(Collectors.toList())),
                5, 2);
        final Chaine c1 = new ChaineImpl(
                new ArrayList<>(Arrays.asList(10, 6, 4, 1, 10).stream()
                        .map(tps -> new PosteImpl(tps))
                        .collect(Collectors.toList())),
                9, 11);
        this.atelierTest = new AtelierSimple(c0, c1,
                new int[]{3, 5, 6, 2},
                new int[]{1, 2, 8, 12});
        this.expectedResult = new ResultatOptimisation(
                Arrays.asList(0, 1, 1, 0, 0), 49);
    }

    /**
     * Test basic d'un OptimisateurAtelierSimple.
     */
    @Test
    public void testOptimiserAtelier() {
        System.out.println("Test optimisateur atelier simple");
        ResultatOptimisation resultOpti
                = this.optimisateurTest.optimiserAtelier(this.atelierTest);
        assertNotNull(resultOpti,
                "Le résultat de l'opti ne devrait pas être null.");
        assertEquals(this.expectedResult.getTempsTotal(),
                resultOpti.getTempsTotal(),
                "Le temps optimal calculé est incorrect.");
        assertEquals(this.expectedResult.getChaineParPoste(),
                resultOpti.getChaineParPoste(),
                "L'enchaînement des chaînes est incorrect.");
    }

}
