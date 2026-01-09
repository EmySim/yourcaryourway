-- DDL SCRIPT (POSTGRESQL) - YOUR CAR YOUR WAY

-- Configuration initiale
SET timezone = 'UTC';

-- =============================================
-- 1. DOMAINE : AUTHENTIFICATION & COMPTE
-- =============================================
CREATE TABLE utilisateur (
    email VARCHAR(255) PRIMARY KEY, 
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    consentement_rgpd BOOLEAN NOT NULL DEFAULT FALSE,
    date_creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    date_derniere_connexion TIMESTAMP WITH TIME ZONE
);

-- =============================================
-- 2. DOMAINE : CATALOGUE & RECHERCHE
-- =============================================
CREATE TABLE categorie_acriss (
    code VARCHAR(4) PRIMARY KEY, 
    description_courte VARCHAR(50) NOT NULL,
    description_longue TEXT
);

CREATE TABLE agence (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    code_postal VARCHAR(20),
    pays VARCHAR(50) NOT NULL,
    telephone VARCHAR(20)
);

CREATE TABLE vehicule (
    id SERIAL PRIMARY KEY,
    marque VARCHAR(50) NOT NULL,
    modele VARCHAR(50) NOT NULL,
    plaque_immatriculation VARCHAR(20) UNIQUE NOT NULL,
    code_acriss VARCHAR(4) NOT NULL REFERENCES categorie_acriss (code),
    annee_fabrication INTEGER,
    couleur VARCHAR(30),
    statut VARCHAR(50) NOT NULL DEFAULT 'Disponible',
    prix_journalier_base NUMERIC(10, 2) NOT NULL
);

-- =============================================
-- 3. DOMAINE : RÉSERVATION & CONTRAT
-- =============================================
CREATE TABLE reservation (
    id BIGSERIAL PRIMARY KEY,
    utilisateur_email VARCHAR(255) NOT NULL REFERENCES utilisateur (email),
    vehicule_id INTEGER NOT NULL REFERENCES vehicule (id),
    agence_depart_id INTEGER NOT NULL REFERENCES agence (id),
    agence_retour_id INTEGER NOT NULL REFERENCES agence (id),
    
    date_debut TIMESTAMP WITH TIME ZONE NOT NULL,
    date_fin TIMESTAMP WITH TIME ZONE NOT NULL,
    
    montant_total NUMERIC(10, 2) NOT NULL,
    statut VARCHAR(50) NOT NULL DEFAULT 'En attente de paiement',
    date_limite_modification TIMESTAMP WITH TIME ZONE
);

-- =============================================
-- 4. DOMAINE : PAIEMENT & FACTURATION
-- =============================================
CREATE TABLE paiement (
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT UNIQUE NOT NULL REFERENCES reservation (id),
    reference_externe VARCHAR(100) UNIQUE NOT NULL, 
    montant NUMERIC(10, 2) NOT NULL,
    devise VARCHAR(3) NOT NULL,
    date_paiement TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    statut VARCHAR(50) NOT NULL DEFAULT 'Validé'
);

CREATE TABLE facture (
    id BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT UNIQUE NOT NULL REFERENCES reservation (id),
    date_emission TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    montant_ht NUMERIC(10, 2) NOT NULL,
    montant_tva NUMERIC(10, 2) NOT NULL,
    montant_total NUMERIC(10, 2) NOT NULL,
    lien_pdf TEXT
);

-- =============================================
-- INDEX DE PERFORMANCE
-- =============================================

CREATE INDEX idx_utilisateur_nom_prenom ON utilisateur (nom, prenom);
CREATE INDEX idx_reservation_period ON reservation (date_debut, date_fin);
CREATE INDEX idx_vehicule_acriss ON vehicule (code_acriss);