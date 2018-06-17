# ALGRAPH
## INTRODUZIONE
Implementazione visuale dell'algoritmo di Prim, algoritmo Greedy per la ricerca del minimo albero di copertura di un grafo non orientato.

#### CARATTERISTICHE
1. **Mofica** del grafo
  * creazione di un grafo non orientato di 10 nodi con archi di peso casuale
  * creazione di un grafo di un numero specifico di nodi senza archi
  * inserimento di un nodo
  * rimozione di uno specifico nodo compresi tutti gli archi a esso associati
  * inserimento di un arco tra due specifici nodi con peso scelto dall'utente
  * modifica del peso di un arco tra due specifici nodi
  * rimozione di un arco tra due nodi specifici
2. **Esecuzione** dell'algoritmo
  * istantenea (con visualizzazione dell'albero minimo di copertura ottenuto)
  * animata con un esecuzione lenta dei vari passaggi dell'algoritmo commentati da una descrizione testuale e dal cambiamento di colore dei nodi e degli archi del grafo
3. **salvataggio** del grafo ottenuto su un file di testo (presente nella cartella)
4. **apertura** del grafo da file di testo con controllo dei possibili errori

## SCELTE IMPLEMENTATIVE
Il progetto è stato organizzato in package e classi in modo da attenersi il più possibile all'architettura **MVCS**:

* **Model**: gestisce la logica delle diverse strutture dati utilizzate. **GraphModel**, nello specifico, gestione ogni operazione di modica sul grafo
* **View**: contiene le classi per la gestione della parte grafica di ogni oggetto. **GraphView** gestisce graficamente ogni operazione sul grafo
* **Controller**: mettono in comunicazione View e Model, gestiscono i vari componenti dell'interfaccia e utilizzano i Service per fornire funzionalità aggiuntive.
* **Service**: consente di gestire l'esecuzione dell'algoritmo nella classe **algorithmHandler**

### STRUTTURA DATI GRAFO
Il grafo è gestito tra 3 strutture dati principali:

* **currentNodesMap**: contiene i nodi correnti
* **freeSpotsMap**: contiene i nodi non ancora inseriti
* **adjMatrix**: memorizza la matrice di adiacenza del grafo

L'uso della **matrice di adiacenza** è stato preferito perchè consente di rendere efficienti operazioni frequenti nell'esecuzione dell'algoritmo come la rilevazione di un arco tra due nodi e il relativo peso.

### STRUTTURE DATI AUSILIARIE
Nell'esecuzione dell'algoritmo vengono usate due strutture dati ausiliarie:

* **visitedMap**: associa ad ogni nodo nel grafo un valore booleano che indica se tale nodo è stato visitato o meno.
* **priorityMap**: associa ad ogni nodo nel grafo un valore intero che inidica qual è il nodo da estarre

Entrambi le strutture sono controllate graficamente dagli appositi controller nella classe **algorithmHandler**.
Il vettore delle priorità è implementato dal punto di vista logico da un **vettore non ordinato**, questo perchè tale strutture dati è più performante su grafi densi.

### DESCRIZIONE TESTUALE
Questa sezione consente di avere un commento a schermo di tutti i passi che l'algoritmo esegue.
Alla fine viene stampato l'**albero minimo di copertura** ottenuto.

### FILE-PICKER
E' possibile **aprire** un file di testo con un grafo e **salvare** il grafo corrente su un apposito file presente nella cartella del progetto.

#### sintassi del file
* Il file deve avere estensione **.txt**
* deve essere inserita una matrice **quadrata** con pesi compresi tra -30 e 30.
* non sono ammessi archi che partono e arrivano allo stesso nodo

ALGRAPH gestisce gli errori nell'apertura del file.

## Note Finali
Il progetto è stato pensato, realizzato e documentato per essere reso pubblico e open-source, in modo da fornire un valido aiuto nella comprensione dell'algoritmo di Prim su grafi orienati.

### Il team
* Matteo Mele
* Leonardo Pio Palumbo
