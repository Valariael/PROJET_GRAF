package fr.femtost.disc.minijaja;

public class NoeudMemoire {

    int taille;
    int adresse;

    boolean disponible;

    NoeudMemoire droit;
    NoeudMemoire gauche;

    NoeudMemoire parent;

    public NoeudMemoire(int adresse, int taille, boolean disponible, NoeudMemoire parent) {
        this.taille = taille;
        this.adresse = adresse;
        this.disponible = disponible;
        this.parent = parent;
        this.droit = null;
        this.gauche = null;
    }

    int allouerMemoire (int tailleAAllouer) {

        if (!disponible) {
            return -1;
        }

        if (this.taille < tailleAAllouer) {
            return -1;
        }

        int retAdresse = -1;

        if (gauche != null) {
            retAdresse = gauche.allouerMemoire(tailleAAllouer);
        }
        if (retAdresse > 0) {
            return retAdresse;
        }

        if (droit != null) {
            retAdresse = droit.allouerMemoire(tailleAAllouer);
        }
        if (retAdresse > 0) {
            return retAdresse;
        }

        if (droit == null && gauche == null) {

            if (this.taille == tailleAAllouer) { // La taille du noeud est celle que l'on souhaite allouer
                this.disponible = false;

                if (parent != null) {  // On lance la propagation à partir du parent, si pas racine
                    parent.propagationTailleDisponile(-this.taille);
                }
                else { // si racine, propagation directe
                    propagationTailleDisponile(-this.taille);
                }

                return this.adresse;
            }

            NoeudMemoire noeudNouveau = new NoeudMemoire(adresse, tailleAAllouer, false, this);
            NoeudMemoire noeudDisponible = new NoeudMemoire(adresse+tailleAAllouer, taille - tailleAAllouer, true, this);
            if (noeudNouveau.taille <= noeudDisponible.taille) {
                gauche = noeudNouveau;
                droit = noeudDisponible;
            } else {
                noeudNouveau.adresse = adresse + noeudDisponible.taille; // Le noeud mémoire dispo est plus petit que la mémoire alloué, donc il vient à gauche
                noeudDisponible.adresse = adresse;
                gauche = noeudDisponible;
                droit = noeudNouveau;
            }
            if (parent != null) {  // On lance la propagation à partir du parent, si pas racine
                parent.propagationTailleDisponile(-noeudNouveau.taille);
            }
            else { // si racine, propagation directe
                propagationTailleDisponile(-noeudNouveau.taille);
            }
            return noeudNouveau.adresse;
        } else {
            return -1; // Pas de mémoire disponible (trop fragmentée)
        }
    }

    void propagationTailleDisponile(int tailleAPropapger) {
        taille += tailleAPropapger;
        if (parent != null) {
            parent.propagationTailleDisponile(tailleAPropapger);
        }
    }

    public void suppressionMemoire(int adresse, NoeudMemoire courant) {
        if (adresse >= taille) {
            return;
        }
        if (adresse < 0) {
            return;
        }
        suppressionMemoireReccursive(adresse, courant);
    }

    public void suppressionMemoireReccursive(int adresse, NoeudMemoire courant) {
        //System.out.println("Adresse : " + courant.adresse + ", Disponible : " + Boolean.toString(courant.disponible));
        if(courant.adresse == adresse && !courant.disponible) {
            courant.disponible = true;
            if (parent != null) {  // On lance la propagation à partir du parent, si pas racine
                parent.propagationTailleDisponile(courant.taille);
            }
            else { // si racine, propagation directe
                propagationTailleDisponile(courant.taille);
            }
            return;
        }
        if (courant.gauche != null) {
            if (courant.adresse + courant.gauche.taille - adresse > 0) {
                suppressionMemoireReccursive(adresse, courant.gauche);
                return;
            }
        }

        if (courant.droit != null) {
            suppressionMemoireReccursive(adresse, courant.droit);
            return;
        }

    }



}
