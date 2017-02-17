package takeanap.layout.com.takeanap.domain;

/**
 * Created by WorkOnly on 16/02/2017.
 */

public class Songs {
    private String nomeMusica;
    private String name;

    public Songs(String m, String n){
        nomeMusica = m;
        name = n;
    }

    public String getNomeMusica(){return nomeMusica;}
    public String getPhoto(){return name;}

}
