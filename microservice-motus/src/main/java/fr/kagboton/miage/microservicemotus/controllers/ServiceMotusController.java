package fr.kagboton.miage.microservicemotus.controllers;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import modele.Joueur;
import modele.Partie;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/motus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ServiceMotusController {

    private final static FacadeMotus facadeMotus = new FacadeMotusStatic();


    /**
     * Connexion du joueur
     * @param joueur
     * @return
     * @throws PseudoDejaPrisException
     */
    @PostMapping (value = "/joueur", consumes = "application/json")
    public ResponseEntity connexion(
            @RequestBody Joueur joueur
            ) throws PseudoDejaPrisException {
        String nom = joueur.getNom();

        facadeMotus.connexion(nom);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{pseudo}")
                .buildAndExpand(nom)
                .toUri();
        return ResponseEntity.created(location).build();

    }

    /**
     * Deconnexion d'un joueur
     * @param pseudo
     * @return
     * @throws PseudoNonConnecteException
     */
    @DeleteMapping(value = "/joueur/{pseudo}")
    public ResponseEntity deconnexion(
            @PathVariable String pseudo
    ) throws PseudoNonConnecteException {

        facadeMotus.deconnexion(pseudo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/").build().toUri();

        return ResponseEntity.created(location).build();
    }


    /**
     * Liste des dicos
     * @return la liste de tous les dicos
     */
    @GetMapping("/dicos")
    public Collection<String> listeDesDicos(){
        return facadeMotus.getListeDicos();
    }

    /**
     * Création d'une nouvelle partie
     * @param dico
     * @param pseudo
     * @return
     * @throws PseudoNonConnecteException
     */
    @PostMapping(value = "/partie")
    public ResponseEntity nouvellePartie(
            @RequestParam("dico") String dico, @RequestParam("pseudo") String pseudo
            ) throws PseudoNonConnecteException {
        facadeMotus.nouvellePartie(pseudo, dico);
       URI location=  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{pseudo}")
                .buildAndExpand(pseudo)
                .toUri();
        return ResponseEntity.created(location).build();
    }


    /**
     * Récupérer la partie crée par un joueur
     * @param pseudo
     * @return
     * @throws PseudoNonConnecteException
     */
    @GetMapping("partie/{pseudo}")
    public ResponseEntity<Partie> recupererUnePartie(
            @PathVariable String pseudo
    ) throws PseudoNonConnecteException {
        Partie partie = facadeMotus.getPartie(pseudo);
        return ResponseEntity.ok(partie);
    }


    @PutMapping("/partie/{pseudo}")
    public ResponseEntity<String> jouer(
            @RequestParam("mot") String mot, @RequestParam("pseudo") String pseudo
    ) throws MotInexistantException, MaxNbCoupsException, PseudoNonConnecteException {

        String motTape = facadeMotus.jouer(pseudo, mot);

        return ResponseEntity.ok(motTape);

    }




}
