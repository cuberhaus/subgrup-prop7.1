L'objectiu d'aquest primer joc de proves és múltiple:
    - Comprovar que el càlcul de DGC i IDGC és correcte comparant-ho amb el proveït pel professorat:
        Average DCG: 57.4867
        Average IDCG: 77.3396
        Average NDCG: 0.729098
    - Comprovar que els mètodes recomanadors funcionen i donen resultats plausibles sense errors.

Aquesta prova a més de les classes involucrades en els objectius anteriors (ConjuntRecomanacions,
MetodeRecomanadorCollaborative, MetodeRecomanadorContentBased) també fa us de pràcticament totes les classes del domini,
tot i això el seu funcionament correcte estarà comprovat en els tests dissenyats amb aquest objectiu.

Els fitxers de dades són els d'exemple proveïts pel professorat amb el corresponent arxiu de queries:
    inputqueries.txt, items.csv, ratings.test.known.csv, ratings.test.unknown.csv
Tots del conjunt de dades Movielens.

