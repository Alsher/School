package base;

import java.util.*;

public class Main
{
    private static HashMap<Integer, Integer> remainingCoinsMap;
    private static ArrayList<Integer> coins = new ArrayList<>();

    public static void main(String[] args)
    {
        remainingCoinsMap = new HashMap<>();
        coins = new ArrayList<>();
        coins.add(200);
        coins.add(100);
        coins.add(50);
        coins.add(20);
        coins.add(10);
        coins.add(5);
        coins.add(2);
        coins.add(1);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Price in ¢: ");
        int price = Integer.parseInt(scanner.next());

        System.out.println("Payed in ¢: ");
        int payed = Integer.parseInt(scanner.next());

        if(payed - price > 0) {
            getRemainingCoins(price, payed);
            List<Integer> keys = new ArrayList<>(remainingCoinsMap.keySet());
            Collections.sort(keys);

            System.out.println("Total return money: " + (payed - price) + "¢");
            for (int key : keys)
                System.out.println("Coin: " + key + "¢ | Amount: " + remainingCoinsMap.get(key) + " | Total value: " + key * remainingCoinsMap.get(key) + "¢");
        }
        else
            System.err.println("You do not have enough money! You have to add at least " + Math.abs(payed - price) + "¢");
    }

    private static void getRemainingCoins(int toBePayed, int money)
    {
        int remaining = money - toBePayed;
        int coinCount = 0;

        while(remaining > 0)
        {
            int coinNumber = 0;
            int coin = coins.get(coinCount);
            while(remaining - coin >= 0)
            {
                remaining -= coin;
                coinNumber++;
                remainingCoinsMap.put(coin, coinNumber);
            }
            coinCount++;
        }
    }
}
