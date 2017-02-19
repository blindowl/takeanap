package takeanap.layout.com.takeanap.domain;

public class Songs {
    private String title;
    private String phrase;
    private String name;
    //private int icon;

    public Songs(){}

    public Songs(String t, String p, String n){
        title = t;
        phrase = p;
        name = n;
        //icon = i;
    }

    public String getTitle(){
        return title;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getName() { return name; }

    //public int getIcon(){ return icon; }


}
