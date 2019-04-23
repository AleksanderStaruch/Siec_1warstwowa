import java.io.File;
import java.util.*;

public class Perceptron {
    private Map<String,Double> map;
    private String lang;
    private double[] wagi;
    private double teta;
    private double n;
    private int count=0;

    public Perceptron(String lang, double teta, double n) {
        this.lang = lang;
        this.teta = teta;
        this.n = n;
        map=getMap();
        this.wagi= new double[map.size()];

        for(int i=0;i<wagi.length;i++){
            Random random = new Random();
            double tmp=random.nextDouble()*1000;
            wagi[i]=(double) Math.round(tmp)/10;
        }

    }

    private Map<String,Double> getMap(){
        Map<String,Double> map=new HashMap<>();
        for(char c='A';c<='Z';c++){
            map.put(c+"",0.0);
        }
        return map;
    }

    public String toString() {
        return "Perceptron "+ lang + "\n\r" +
                "wagi=" + Arrays.toString(wagi) +"\n\r" +
                "teta=" + teta +"\n\r" +
                "count=" + count+"\n\r" ;
    }

    private void update(Map<String,Double> map, String h){
        if(map.containsKey(h)){
            double old=map.get(h);
            map.remove(h);
            map.put(h,old+1);
        }
    }

    private double dl(double[] tab){
        double dl=0;
        for(double d:tab){
            dl+=d*d;
        }
        dl=Math.sqrt(dl);
        return dl;
    }

    private void norm(Map<String,Double> map){
        double[] tab = new double[map.size()];
        for(int i=0;i<tab.length;i++){
            tab[i]=map.get((char) (i + 65) + "");
        }

        double dl=dl(tab);
        for(char c='A';c<='Z';c++){
            double old=map.get(c+"");
            map.remove(c+"");
            map.put(c+"",old/dl);
        }
    }

    private void scanner(Map<String,Double> map,Scanner od){
        String linia;
        while (od.hasNext()) {
            linia = od.nextLine();
            for (String l : linia.split("")) {
                l = l.toUpperCase();
                update(map, l);
            }
        }
    }

    private List<File> getFiles(List<String> paths,String p){
        List<File> files= new ArrayList<>();
        for(String path:paths) {
            File folder = new File( p+ path);
            String[] tab = folder.list();
            for (String s : tab) {
                File file = new File(p + path + "//" + s);
                files.add(file);
            }
        }
        File[] tab=new File[files.size()];
        files.toArray(tab);
//        Arrays.sort(tab, Comparator.comparing(File::getName));

        return Arrays.asList(tab);
    }

    public void learn(List<String> paths){
        List<File> files= getFiles(paths,"lang//");
        System.out.println(lang);
        try{
            for(File file:files) {
                Scanner od = new Scanner(file);
                scanner(map, od);
                od.close();
                norm(map);

                int d;
                if(file.getAbsolutePath().contains(lang)){
                    d=1;
                }else{
                    d=0;
                }
                double net = 0;
                for (int i = 0; i < wagi.length; i++) {
                    net += wagi[i] * map.get((char) (i + 65) + "");
                }
                net -= teta;
                int y;
                if (net >= 0) {
                    y = 1;
                } else {
                    y = 0;
                }
//                System.out.println(file.getAbsolutePath()+" "+d+" "+y);
                if ((d - y) != 0) {
                    count++;
                    for (int i = 0; i < wagi.length; i++) {
                        wagi[i] = wagi[i] + n * (d - y) * map.get((char) (i + 65) + "");
                        teta = teta - n * (d - y);
                    }
                }
            }
            //zaokraglanie
            for(int i=0;i<wagi.length;i++){
                double tmp=wagi[i]*10;
                wagi[i]=(double) Math.round(tmp)/10;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void checkDataTestAccuracy(List<String> paths){
        List<File> files= getFiles(paths,"lang.test//");
        System.out.println(lang);
        try{
            for(File file:files) {
                String r=file.getAbsolutePath();
                Scanner od = new Scanner(file);
                scanner(map, od);
                od.close();
                norm(map);

                int d;
                if(file.getAbsolutePath().contains(lang)){
                    d=1;
                }else{
                    d=0;
                }
                double net=0;
                for(int i=0;i<wagi.length;i++){
                    net+=wagi[i]*map.get((char)(i+65)+"");
                }
                net-=teta;
                int y;
                if(net >=0){
                    y=1;
                }else{
                    y=0;
                }

                if(y==d){
                    r+=(" +");
                }else{
                    r+=(" -");
                }
                System.out.println(r);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public double checkTextAccuracy(String text){
        for (String l : text.split("")) {
            l = l.toUpperCase();
            update(map, l);
        }
        norm(map);

        double net=0;
        for(int i=0;i<wagi.length;i++){
            net+=wagi[i]*map.get((char)(i+65)+"");
        }
        net-=teta;

        return net;
    }

}
