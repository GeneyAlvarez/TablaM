
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Necho
 */
public class Produccion {
    String raw_producciones;
    String[] producciones;
    static ArrayList Pool;
    static ArrayList Pool2;

    public Produccion(String entrada) {
        this.raw_producciones=entrada;
        this.producciones=entrada.split("\n");
        this.Pool=pool(entrada);
        this.Pool2=pool2(entrada);
    }

    public String getRaw_producciones() {
        return raw_producciones;
    }

    public void setRaw_producciones(String raw_producciones) {
        this.raw_producciones = raw_producciones;
    }

    public String[] getProducciones() {
        return producciones;
    }

    public void setProducciones(String[] producciones) {
        this.producciones = producciones;
    }

    public ArrayList getPool() {
        return Pool;
    }

    public void setPool(ArrayList Pool) {
        Produccion.Pool = Pool;
    }

    public ArrayList getPool2() {
        return Pool2;
    }

    public void setPool2(ArrayList Pool2) {
        Produccion.Pool2 = Pool2;
    }

    public static ArrayList getTerminales(String s){
        ArrayList<String> Salida=new ArrayList<>();
        Salida.add(" :3 ");
        String[] prueba=s.split("\n");
        String prueba2;
        for(int i=0;i<prueba.length;i++){
            prueba2=(prueba[i].split("->"))[1];
            char[] test=prueba2.toCharArray();
            for(int j=0;j<test.length;j++){
                int ascii=(int) test[j];
                if((ascii<55 || ascii>90)&&(test[j]!='|')&&(test[j]!='&')){
                    if(!Salida.contains(""+test[j])){
                        //&& !(65<=ascii) && !(90>=ascii)
                        Salida.add(""+test[j]);
                    }
                }
            }
        }
        Salida.add("$");
        System.out.println("Terminales\n"+Salida);
        return Salida;
    }
    
    //Actualiza los Pools (por revisar)
    public static void pool_change(String Letra){        
     Pool.add(Letra);
     Pool2.remove(Letra);       
    }
    
    //Haya las letras que ya estan usadas para representar un NoTerminal
    public static ArrayList pool(String entrada){
        
        String[] prueba=entrada.split("\n");
        int pruebalong=prueba.length;
        
        ArrayList<String> Salida=new ArrayList<>();
        String muestre="";
        
        System.out.println("NoTerminales usados: ");
        
        for(int i=0;i<pruebalong;i++){
            String temp=prueba[i];
            String[] temp2=temp.split("->");
            temp=temp2[0];
            muestre+=(temp+" ");         
            Salida.add(temp);
        }
        
        System.out.println(muestre);
        return Salida;
    }
    
    //El mismo pool...pero no imprime nada en consola
    public static ArrayList poolrevisado(String entrada){
        
        String[] prueba=entrada.split("\n");
        int pruebalong=prueba.length;
        
        ArrayList<String> Salida=new ArrayList<>();
        String muestre="";
    
        for(int i=0;i<pruebalong;i++){
            String temp=prueba[i];
            String[] temp2=temp.split("->");
            temp=temp2[0];
            muestre+=(temp+" ");         
            Salida.add(temp);
        }

        return Salida;
    }
    
    //Calcula el pool de no terminales sin usar (en caso de que deba crear otra produccion)
    public static ArrayList pool2(String entrada){
        ArrayList<String> x=new ArrayList<>();
        x=poolrevisado(entrada);
        
        ArrayList<String> prov_pool=new ArrayList<>();
        prov_pool.add("A");prov_pool.add("B");prov_pool.add("C");prov_pool.add("D");prov_pool.add("E");prov_pool.add("F");
        prov_pool.add("G");prov_pool.add("H");prov_pool.add("I");prov_pool.add("J");prov_pool.add("K");prov_pool.add("L");
        prov_pool.add("M");prov_pool.add("N");prov_pool.add("Ñ");prov_pool.add("O");prov_pool.add("P");prov_pool.add("Q");
        prov_pool.add("R");prov_pool.add("S");prov_pool.add("T");prov_pool.add("U");prov_pool.add("V");prov_pool.add("W");
        prov_pool.add("X");prov_pool.add("Y");prov_pool.add("Z");
        
        ArrayList<String> Salida=new ArrayList<>();
        String muestre="";
        
        
        System.out.println("\nNoTerminales disponibles: ");
        for(int i=0;i<prov_pool.size();i++){
            boolean sw=x.contains(prov_pool.get(i));      
            if(!sw){
                muestre+=(prov_pool.get(i) +" ");
                Salida.add(prov_pool.get(i));            
            }
        }
        
        System.out.println(muestre);
        return Salida;
    }
      
    public static String CommonFactorReplace(String fc,ArrayList prod){
        String Salida="";
        int tam=fc.length();
        String test;   
      
        for(int i=1;i<prod.size();i++){     
            test=(String)prod.get(i);          
            if(test.startsWith(fc)){ 
                if(test.substring(tam).equals("")){
                    Salida+="&|";
                }
                else
                {
                    Salida+=test.substring(tam)+"|";
                }
            }
        }
        
        Salida=Salida.substring(0, Salida.length()-1);
        
            String NewNT=(String)Pool2.get(0);
            pool_change(NewNT);
            Salida=NewNT+"->"+Salida;         
        //-----------------Creado nuvo terminal...next...reemplazar en el viejo    
            String test2="";    
            test2+=(String)prod.get(0)+"->"+fc+NewNT+"|";
            for(int i=1;i<prod.size();i++){     
                test=(String)prod.get(i);
                if(!test.startsWith(fc)){
                    test2+=test+"|";
                }
            }
            test2=test2.substring(0, test2.length()-1);
        
        System.out.println(test2);
        System.out.println(Salida+"\n");      
        return test2+"\n"+Salida;
    }
    
    public static String CommonFactor(ArrayList prod){
        int tam=1000000; // :v
        boolean sw=false;
        String FC="";
        String test;
        
        //busca tamaño minimo de cadenita
        for(int i=1;i<prod.size();i++){
            test=(String)prod.get(i);
            if(test.length()<tam){
                tam=test.length();
            }
        }
        System.out.println("Longitud minima de cadena = "+tam);
        
        //busca el primer factor comun de tamaño tam...sino..hace tam-- y manda again :'v
        for(int k=tam;k>0;k--){
            if(sw==false){
                for(int i=1;i<prod.size();i++){
                    if(sw==false){
                        test=(String)prod.get(i);                     
                            FC=test.substring(0, k);
                            if(i<prod.size()){
                                for(int j=i+1;j<prod.size();j++){
                                    test=(String)prod.get(j);
                                    if(test.startsWith(FC)){
                                        sw=true;
                                        break;
                                    }
                                }
                            }                   
                    }
                }
            }
        }
        
        String result="";
        if(sw){
            System.out.println("Factor comun = "+FC+"\n");
            result=CommonFactorReplace(FC,prod);
        }
        else
        {
            System.out.println("No posee un factor comun\n");
            result+=(String)prod.get(0)+"->";
            for(int i=1;i<prod.size();i++){     
                test=(String)prod.get(i);             
                    result+=test+"|";
                
            }
            result=result.substring(0,result.length()-1);
        }
       
        return result;
    }
    
    public static String FixIt(String P){
        //Factorizo
        String Salida="";
        String SalidaFinal="";
        ArrayList<String> test=new ArrayList<>();
        String[] test2;
        System.out.println("\nFactorizando");
        String[] prods=P.split("\n");
        int tam=prods.length;
        
        for(int i=0;i<tam;i++){
            test2=prods[i].split("->");
            test.add(test2[0]);
            test2=test2[1].split("\\|");
            for(int j=0;j<test2.length;j++){
                test.add(test2[j]);
            }
            System.out.println(test);
            Salida=CommonFactor(test);
            SalidaFinal+=Salida+"\n";
            test2=null;
            test=new ArrayList<>();
        }
        
        System.out.println("End:\n"+SalidaFinal);
        return SalidaFinal;
    }
    
    public static ArrayList ConversorArray(String x){
        
        String[] test1=x.split("\n");
        int testlong=test1.length;
        String[] test2;
        ArrayList<Object> Matrix=new ArrayList<>();
                
        for(int i=0;i<testlong;i++){
            ArrayList<String> Prod=new ArrayList<>();
            test2=test1[i].split("->");
            Prod.add(test2[0]);
            test2=(test2[1]).split("\\|");
            
            for(int j=0;j<test2.length;j++){
                Prod.add(test2[j]);
            }
           Matrix.add(Prod);            
        }       
        
        return Matrix;
    }
    
    public static int FindIndex(ArrayList P,String Letra){
        
        ArrayList<String> test=new ArrayList<>();
        
        for(int i=0;i<P.size();i++){
            test=(ArrayList)P.get(i);
            if(test.get(0).equals(Letra)){
                return i;
            }
        }   
        return -1;//en caso de que no este
    }
    
    public static String Prim(ArrayList P,String Letra){
        
        String Salida="";
        //Halla en que fila del la matriz (actually...arraylist de arraylists) esta el NoTerminal con el que trabajare
        int Buscar=FindIndex(P,Letra);
        int Buscar2;
        ArrayList<String> prueba=(ArrayList) P.get(Buscar);
        ArrayList<String> prueba2;
               
        for(int i=1;i<prueba.size();i++){
            char t=(prueba.get(i).toCharArray())[0];
            int ascii=(int) t;          
            //posible loop infinito
            if(65<=ascii && 90>=ascii){
                Salida+=Prim(P,""+t);
                /*if(prueba.get(i).length()>1){
                    char T=(prueba.get(i).toCharArray())[1];
                    Buscar2=FindIndex(P,""+T);
                    prueba2=(ArrayList) P.get(Buscar2);
                    if(prueba2.contains("&")){
                        Salida+=Prim(P,""+T);
                    }                  
                }*/
            }
            else
            {
                Salida+=t+" ";
            }
        }
        
        //Salida=Letra+"= { "+Salida+"}";
        return Salida;
    } 
    
    public static String Primero(String x){
       ArrayList<Object> Matrix;
       Matrix=ConversorArray(x);
       int tam=Matrix.size();
       String NT="";
       String Salida="";
       String Salida2="";
       ArrayList<String> test;
       
       for(int i=0;i<tam;i++){
           test=(ArrayList<String>)Matrix.get(i);
           
           NT=test.get(0);
           Salida=Prim(Matrix,NT);
           
           //System.out.println(NT+" -> { "+Salida+" }");
           Salida2+="Prim("+NT+") = { "+Salida+" }\n";
       }     
        return Salida2;
    }
    
    public static String Sgte(ArrayList P,String Letra,String piv){
        String Salida=" ";
        
        if(Letra.equals(piv)){
            Salida+="$";
        }

        //Halla en que fila del la matriz (actually...arraylist de arraylists) esta el NoTerminal con el que trabajare
        int Buscar=FindIndex(P,Letra);
        ArrayList<String> prueba=(ArrayList) P.get(Buscar);
        String test;char test2;
        String n;int ascii;
        
        for(int i=1;i<prueba.size();i++){
            n=prueba.get(i);
            //System.out.println("\n"+n);
            
            for(int j=0;j<n.length()-1;j++){
                test=n.substring(j,j+1);
                //System.out.println(""+test);
                if(test.equals(Letra)){
                    test2=(n.toCharArray())[j+1];
                    ascii=(int)test2;
                    //System.out.println(test2);
                    if(65<=ascii && 90>=ascii){
                        Salida+=Prim(P,test2+"");
                    }
                    else{
                        Salida+=test2+" ";
                    }
                }
            }
        }
        ArrayList<String> next;
        String m;
        
        for(int k=0;k<P.size();k++){
            if(k!=Buscar){
                next=(ArrayList) P.get(k);
                for(int i=1;i<next.size();i++){
                    m=next.get(i);
                    for(int j=0;j<m.length();j++){
                        test=m.substring(j,j+1);
                        
                        if(test.equals(Letra)){ 
                            if(j+1==m.length()){                                
                                Salida+=Sgte(P,next.get(0),piv);
                            }
                            else
                            {
                               test2=(m.toCharArray())[j+1];
                                ascii=(int)test2;
                                if(65<=ascii && 90>=ascii){
                                    Salida+=Prim(P,test2+"");
                                    if(Prim(P,test2+"").contains("&")){
                                        Salida+=Sgte(P,test2+"",piv);
                                    }
                                }
                                else{
                                    Salida+=test2+" ";
                                } 
                            }
                                                  
                        }                     
                    }                                    
                }              
            }
        }         
        //System.out.println(Salida);
        return Salida;
    }
   
    public static String Siguiente(String x){
              
       ArrayList<Object> Matrix;
       Matrix=ConversorArray(x);
       int tam=Matrix.size();
       String NT="";
       String Salida="";
       String Salida2="";
       ArrayList<String> test;
       String piv=((ArrayList<String>)Matrix.get(0)).get(0);
       
        for(int i=0;i<tam;i++){
           test=(ArrayList<String>)Matrix.get(i);      
           NT=test.get(0);      
           Salida=Sgte(Matrix,NT,piv);
                Salida2+="Sgte("+NT+") = { "+Salida+" }\n";

        }
                
        return Salida2;
    }  
    
    public static String AlphaBeta(String NT, String a,ArrayList b){
        String Salida="";
        String NuevoNoTerminal="";
        String NewNT=(String)Pool2.get(0);
        pool_change(NewNT);
        String test;
        NuevoNoTerminal+=NewNT+"->"+a+NewNT+"|&";
        
        Salida+=NT+"->";
        
        for(int i=0;i<b.size();i++){
            test=(String)b.get(i);
            Salida+=test+NewNT+"|";
        }
        
        Salida=Salida.substring(0,Salida.length()-1);
        
        return Salida+"\n"+NuevoNoTerminal;
    }
    
    public static String Izquierda(ArrayList prod){
        String NoTerminal=(String)prod.get(0);
        String rec="";
        boolean sw=false;
        String alpha="";
        ArrayList<String> beta=new ArrayList<>();
        String test;
        String Salida="";
        
        for(int i=1;i<prod.size();i++){
            test=(String)prod.get(i);
            if(test.startsWith(NoTerminal)){
                rec=test;
                alpha=test.substring(1);
                sw=true;
            }
            else{
                beta.add(test);
            }
        }
        
        if(sw){
            System.out.println("Alpha= "+alpha);
            System.out.println("Beta= "+beta);
            Salida+=AlphaBeta(NoTerminal,alpha,beta);
            System.out.println("Salida---> "+Salida);
        }
        else
        {
            Salida+=(String)prod.get(0)+"->";
            for(int i=1;i<prod.size();i++){     
                test=(String)prod.get(i);             
                    Salida+=test+"|";
                
            }
            Salida=Salida.substring(0,Salida.length()-1);
            System.out.println("Salida---> \n"+Salida);
        }
        
        return Salida;
    }  
    
     public static String RecIzq(String P){
         String Salida="";
         ArrayList<String> test=new ArrayList<>();
         String[] test2;
         String SalidaFinal="";
         System.out.println("\nSolucionando Recursividad izquierda");
         String[] prods=P.split("\n");
         int tam=prods.length;
         
         for(int i=0;i<tam;i++){
            test2=prods[i].split("->");
            test.add(test2[0]);
            test2=test2[1].split("\\|");
            for(int j=0;j<test2.length;j++){
                test.add(test2[j]);
            }
            System.out.println(test);
            Salida=Izquierda(test);
            SalidaFinal+=Salida+"\n";
            test2=null;
            test=new ArrayList<>();
         }
        
         return SalidaFinal;
     }
       
}
