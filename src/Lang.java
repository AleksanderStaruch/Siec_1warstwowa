import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lang {
    private Perceptron pol;
    private Perceptron eng;
    private Perceptron ger;

    public Lang(double teta,double n) {
        List<String> list= new ArrayList<>();
        list.add("Polish");
        list.add("German");
        list.add("English");

        eng= new Perceptron("English",teta,n);
        ger= new Perceptron("German",teta,n);
        pol= new Perceptron("Polish",teta,n);

        pol.learn(list);
        eng.learn(list);
        ger.learn(list);

        System.out.println(pol.toString());
        System.out.println(eng.toString());
        System.out.println(ger.toString());

        pol.checkDataTestAccuracy(list);
        eng.checkDataTestAccuracy(list);
        ger.checkDataTestAccuracy(list);
    }

    public String checkText(String text){
//        System.out.println(text);
        String r="";
        double netPol=pol.checkTextAccuracy(text);
        double netEng=eng.checkTextAccuracy(text);
        double netGer=ger.checkTextAccuracy(text);

        HashMap<Double,String> map =new HashMap<>();
        map.put(netPol,"POL");
        map.put(netEng,"ENG");
        map.put(netGer,"GER");

        map.forEach((K,V)->System.out.println(K+" "+V));

        List<Double> list =new ArrayList<>();
        list.add(netPol);
        list.add(netEng);
        list.add(netGer);

        list.sort(Double::compareTo);
        list.forEach(System.out::println);

        r+=map.get(list.get(list.size()-1));

//
        return r;
    }
}
