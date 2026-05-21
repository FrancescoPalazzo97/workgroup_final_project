INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Il nome della rosa', 'Umberto Eco', 1980, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Il nome della rosa' AND autore = 'Umberto Eco' AND anno_pubblicazione = 1980);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Se questo è un uomo', 'Primo Levi', 1947, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Se questo è un uomo' AND autore = 'Primo Levi' AND anno_pubblicazione = 1947);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'I promessi sposi', 'Alessandro Manzoni', 1827, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'I promessi sposi' AND autore = 'Alessandro Manzoni' AND anno_pubblicazione = 1827);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Divina Commedia', 'Dante Alighieri', 1320, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Divina Commedia' AND autore = 'Dante Alighieri' AND anno_pubblicazione = 1320);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Il gattopardo', 'Giuseppe Tomasi di Lampedusa', 1958, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Il gattopardo' AND autore = 'Giuseppe Tomasi di Lampedusa' AND anno_pubblicazione = 1958);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Cent anni di solitudine', 'Gabriel García Márquez', 1967, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Cent anni di solitudine' AND autore = 'Gabriel García Márquez' AND anno_pubblicazione = 1967);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT '1984', 'George Orwell', 1949, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = '1984' AND autore = 'George Orwell' AND anno_pubblicazione = 1949);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Il maestro e Margherita', 'Mikhail Bulgakov', 1967, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Il maestro e Margherita' AND autore = 'Mikhail Bulgakov' AND anno_pubblicazione = 1967);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Delitto e castigo', 'Fëdor Dostoevskij', 1866, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Delitto e castigo' AND autore = 'Fëdor Dostoevskij' AND anno_pubblicazione = 1866);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Harry Potter e la pietra filosofale', 'J.K. Rowling', 1997, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Harry Potter e la pietra filosofale' AND autore = 'J.K. Rowling' AND anno_pubblicazione = 1997);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Il Signore degli Anelli', 'J.R.R. Tolkien', 1954, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Il Signore degli Anelli' AND autore = 'J.R.R. Tolkien' AND anno_pubblicazione = 1954);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Don Chisciotte', 'Miguel de Cervantes', 1605, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Don Chisciotte' AND autore = 'Miguel de Cervantes' AND anno_pubblicazione = 1605);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Anna Karenina', 'Lev Tolstoj', 1878, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Anna Karenina' AND autore = 'Lev Tolstoj' AND anno_pubblicazione = 1878);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Il processo', 'Franz Kafka', 1925, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Il processo' AND autore = 'Franz Kafka' AND anno_pubblicazione = 1925);

INSERT INTO books (titolo, autore, anno_pubblicazione, disponibile)
SELECT 'Sulla strada', 'Jack Kerouac', 1957, true
WHERE NOT EXISTS (SELECT 1 FROM books WHERE titolo = 'Sulla strada' AND autore = 'Jack Kerouac' AND anno_pubblicazione = 1957);
