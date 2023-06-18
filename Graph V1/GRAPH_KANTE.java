public class GRAPH_KANTE
{
    private KNOTEN[] knotenliste;
    private int aktuelleAnzahlKnoten;
    private int[][] matrix;
    private boolean[] besucht;

    public GRAPH_KANTE(int maxAnzKnoten)
    {
        knotenliste = new KNOTEN[maxAnzKnoten];
        matrix = new int[knotenliste.length][knotenliste.length];
        aktuelleAnzahlKnoten = 0;

        MatrixInitialisieren();
    }

    private void MatrixInitialisieren()
    {
        for(int i = 0; i < matrix.length; i++){
            matrix[0][i] = -1;
            for(int n = 0; n < matrix[i].length; n++){
                if(i == n)
                    matrix[i][n]= 0;
                else
                    matrix[i][n] = -1;
            }
        }    
    }

    public void MatrixAusgeben() {
        char aktuellerB = 'A';
        System.out.print("   ");
        for (int i = 0; i < matrix.length; i++) 
            System.out.printf("%-4s", aktuellerB++);
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("%-4s", (char)('A'+i));
            for (int j = 0; j < matrix[i].length; j++) 
                System.out.printf("%-4d", matrix[i][j]);
            System.out.println();
        }
    }

    public int KnotenNrBestimmen(String name) {
        for (int i = 0; i < aktuelleAnzahlKnoten; i++) {
            if (knotenliste[i] != null && knotenliste[i].BezeichnerGeben().equals(name))
                return i;
        }
        return -1;
    }

    public void besuchen(int nummer) {
        // aktuellen Knoten als besucht markieren
        besucht[nummer] = true;
        System.out.println("Knoten " + knotenliste[nummer].BezeichnerGeben() + " wurde besucht");
        // alle unbesuchten Nachbarn besuchen
        for (int i = 0; i < this.aktuelleAnzahlKnoten; i++) {
            if (matrix[nummer][i] > 0) { // nummer und i verbindet eine Kante
                // wenn Nachbar i noch unbesucht
                if (besucht[i] == false) 
                // Nachbar i besuchen
                    besuchen(i);
            }
        }

        System.out.println("Knoten " + knotenliste[nummer].BezeichnerGeben() + " abgearbeitet");
    }

    public boolean Wegesuche(String startKnoten, String zielKnoten)
    {
        besucht = new boolean[knotenliste.length];
        int start = KnotenNrBestimmen(startKnoten);
        int ziel = KnotenNrBestimmen(zielKnoten);
        if(start == ziel){
            System.out.println("Startknoten ist Zielknoten, du kelp!");
            return false;
        }else if(start < 0) {
            System.out.println("Start nicht da!");
            return false;
        }else if(ziel < 0){
            System.out.println("Ziel nicht virtuell materialisiert!:O");  
            return false;
        }else{
            return pfadFinden(start, ziel);
        }
    }

    public boolean pfadFinden(int knotenNr,int zielKnotenNr)
    {
        // aktuellen Knoten als besucht markieren
        boolean zielGefunden = false;
        besucht[knotenNr] = true;
        System.out.println("Knoten " + knotenliste[knotenNr].BezeichnerGeben() + " wurde besucht");
        if (knotenNr == zielKnotenNr) {
            return true;
        }else {
            for (int i = 0; i < this.aktuelleAnzahlKnoten; i++) {
                if (matrix[knotenNr][i] > 0) { // nummer und i verbindet eine Kante
                    // wenn Nachbar i noch unbesucht
                    if (besucht[i] == false) 
                    // Nachbar i besuchen

                        if (pfadFinden(i, zielKnotenNr) == true)//wenn ein nachbar das Ziel gefunden hat dann return true
                        {
                            zielGefunden = true;
                        }
                }
            }
            System.out.println("Knoten " + knotenliste[knotenNr].BezeichnerGeben() + " abgearbeitet");
        }
        return zielGefunden;
    }
    
    public String Wegesuche2(String startKnoten, String zielKnoten)
    {
        besucht = new boolean[knotenliste.length];
        int start = KnotenNrBestimmen(startKnoten);
        int ziel = KnotenNrBestimmen(zielKnoten);
        if(start == ziel){
            System.out.println("Startknoten ist Zielknoten, du kelp!");
            return null;
        }else if(start < 0) {
            System.out.println("Start nicht da!");
            return null;
        }else if(ziel < 0){
            System.out.println("Ziel nicht virtuell materialisiert!:O");  
            return null;
        }else{
            return pfadFinden2(start, ziel, "");
        }
    }
    
    public String pfadFinden2(int knotenNr,int zielKnotenNr, String teilpfad)
    {
        // aktuellen Knoten als besucht markieren
        besucht[knotenNr] = true;
        String neuerTeilpfad = teilpfad + knotenliste[knotenNr].BezeichnerGeben();
        //System.out.println("Knoten " + knotenliste[knotenNr].BezeichnerGeben() + " wurde besucht");
        if (knotenNr == zielKnotenNr) {
            for (int i = 0; i < this.aktuelleAnzahlKnoten; i++)
                besucht[i] = true;
            return neuerTeilpfad;
        }else {
            for (int i = 0; i < this.aktuelleAnzahlKnoten; i++) {
                if (matrix[knotenNr][i] > 0 && besucht[i] == false) { 
                    neuerTeilpfad =  pfadFinden2(i, zielKnotenNr, teilpfad +"->"+ knotenliste[i].BezeichnerGeben());
                }
            }
            //System.out.println("Knoten " + knotenliste[knotenNr].BezeichnerGeben() + " abgearbeitet");
        }
        return neuerTeilpfad;
    }

    public void tiefensucheStarten(String bezeichner)
    {
        besucht = new boolean[aktuelleAnzahlKnoten];
        int startNr = KnotenNrBestimmen(bezeichner);
        if (startNr >= 0) 
            besuchen(startNr);
        else 
            System.out.println("Startknoten nicht vorhanden.");
    }

    public void knotenEinfuegen(String name)
    {
        if (aktuelleAnzahlKnoten < knotenliste.length) {
            knotenliste[aktuelleAnzahlKnoten] = new KNOTEN(name);
            aktuelleAnzahlKnoten++;
        } else 
            System.out.println("Maximale Knotenanzahl erreicht.");
    }

    public void kanteEinfuegen(String von, String nach, int gewicht)
    {
        int nummerVon = KnotenNrBestimmen(von);
        int nummerNach = KnotenNrBestimmen(nach);
        if (nummerVon == -1) 
            System.out.println("Knoten " + von + " nicht vorhanden.");
        else if (nummerNach == -1) 
            System.out.println("Knoten " + nach + " nicht vorhanden.");
        else if (nummerVon == nummerNach) 
            System.out.println("Start- und Zielknoten sind identisch.");
        else {
            if (matrix[nummerVon][nummerNach] < 0) 
                matrix[nummerVon][nummerNach] = gewicht;
            else 
                System.out.println("Kante bereits vorhanden.");
        }
    }

    public void ungerichtetEinfügen(String von, String nach, int gewicht){
        kanteEinfuegen(von, nach,gewicht);
        kanteEinfuegen(nach, von,gewicht);
    }

    public void testdatenEinfuegen()
    {
        knotenEinfuegen("A");
        knotenEinfuegen("FD");
        knotenEinfuegen("HO");
        knotenEinfuegen("LI");
        knotenEinfuegen("M");
        knotenEinfuegen("N");
        knotenEinfuegen("R");
        knotenEinfuegen("RO");        
        knotenEinfuegen("S");
        knotenEinfuegen("UL");
        knotenEinfuegen("WÜ");        

        ungerichtetEinfügen("FD", "WÜ", 98);
        ungerichtetEinfügen("WÜ", "UL", 165);
        ungerichtetEinfügen("WÜ", "N", 104);
        ungerichtetEinfügen("WÜ", "HO", 192);
        ungerichtetEinfügen("HO", "N", 116);
        ungerichtetEinfügen("HO", "R", 166);
        ungerichtetEinfügen("N", "R", 80);
        ungerichtetEinfügen("N", "M", 163);
        ungerichtetEinfügen("R", "M", 117);
        ungerichtetEinfügen("M", "RO", 60);
        ungerichtetEinfügen("M", "A", 64);
        ungerichtetEinfügen("A", "UL", 59);
        ungerichtetEinfügen("UL", "LI", 126);
        ungerichtetEinfügen("S", "UL", 103);

    }
}