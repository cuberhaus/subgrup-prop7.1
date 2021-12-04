L'objectiu d'aquest joc de proves és comprovar que els algorismes responen be tot i tenir un conjunt de dades molt gran.

Per tant aquesta prova es centra en provar MetodeRecomanadorCollaborative i MetodeRecomanadorContentBased

Els fitxers de dades són els d'exemple proveïts pel professorat del conjunt Series en el format més gran (2250 items):
    items.csv, ratings.test.known.csv, ratings.test.unknown.csv

Els resultats confirmen que tot i que el temps d'execució és elevat el funcionament és correcte. El temps elevat és esperable
ja que es fan 2250 queries (en un cas normal només fariem una query).
