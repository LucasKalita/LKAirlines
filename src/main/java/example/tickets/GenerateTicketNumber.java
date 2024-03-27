package example.tickets;

import java.util.Random;

public class GenerateTicketNumber {

        public static String TicketNumber() {
            Random random = new Random();

            String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            int c = random.nextInt(999);

            char x = abc.charAt(random.nextInt(abc.length() - 1));
            char z = abc.charAt(random.nextInt(abc.length() - 1));

            return String.valueOf(x) + z + "-" + c;
        }
    }

