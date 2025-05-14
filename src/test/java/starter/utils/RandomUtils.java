package starter.utils;

import java.security.SecureRandom;
import java.util.List;

public class RandomUtils{

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomUtils() {}

    public static String randomString(int length){
        StringBuilder sb = new StringBuilder(length);
        
        sb.append("TEST_");
        for(int i = 0; i < length; i++){
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
            
        return sb.toString();
    }

    public static <T> T randomElementFromList(List<T> elements){
        return elements.get(SECURE_RANDOM.nextInt(elements.size()));
    }

    public static double randomDouble(){
        double rangeMin = 10.01;
        double rangeMax = 99.99;

        double randomValue = rangeMin + (rangeMax - rangeMin) * SECURE_RANDOM.nextDouble();

        return (Math.floor(randomValue * 100) / 100);
    }

    public static int randomInt() {
        int rangeMax = 100;
        return SECURE_RANDOM.nextInt(rangeMax);
    }
    
}
