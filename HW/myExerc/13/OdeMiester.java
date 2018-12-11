public class OdeMiester {
    public final int breath = 5;
    public final Grodie gosh = new Grodie(10);
    public final Grodie[] arr = new Grodie[2];

    public void test() {
        final OdeMiester groovy = new OdeMiester();
        //groovy.breath = 5;
        //groovy.gosh = new Grodie();
        groovy.gosh.cow = 10;
        //arr = new Grodie[2];
        groovy.breath = 6;
        arr[0] = groovy;
        OdeMiester radical = new OdeMiester();
        OdeMiester lit = groovy;
        arr[0] =  lit;
        groovy = radical;
        arr[1]= radical;
        arr[1] = lit;
        radical.gosh.cow = 2;
    }
}
